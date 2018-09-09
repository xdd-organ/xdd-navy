/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package activemq.mqtt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.ByteSequenceData;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Priority dispatch policy that sends a message to every subscription that
 * matches the message in consumer priority order.
 * 
 * @org.apache.xbean.XBean
 * 
 */
public class PriorityDispatchPolicy extends SimpleDispatchPolicy {
	
	private static final Logger logger = LoggerFactory.getLogger(PriorityDispatchPolicy.class);

    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {
        //指定特定后缀名的topic进入自定义分发策略
        //消息格式   clientId@消息内容@ptp
		logger.info("topName:" + node.getMessage().getDestination().getQualifiedName());
        if(!node.getMessage().getDestination().getQualifiedName().endsWith("@ptp"))
            return super.dispatch(node, msgContext, consumers);

        String _clientId = "";
        //获取消息内容
        ByteSequence sequence = node.getMessage().getContent();
        //byte[] message = sequence.getData();
		byte[] message = ByteSequenceData.toByteArray(sequence);
        String strMessage = new String(message, 0, message.length, "utf-8");

		logger.info("messageBody:" + strMessage + ",length:" + strMessage.length());
        //format：clientID@messageBody
        if (!"".equals(strMessage) && strMessage.indexOf("@")>-1) 
            _clientId = strMessage.substring(0, strMessage.indexOf("@"));
        if ("".equals(_clientId))
            return super.dispatch(node, msgContext, consumers);

        strMessage = strMessage.substring(strMessage.indexOf("@") + 1, strMessage.length());
		logger.info("messageBody2:" + strMessage + ",length2:" + strMessage.length());
		byte[] rsp = strMessage.getBytes("utf-8");
		logger.info("rsp:" + rsp.length);
        node.getMessage().setContent(new ByteSequence(rsp, 0, rsp.length));

        ActiveMQDestination _destination = node.getMessage().getDestination();

        int count = 0;
        for (Subscription sub : consumers) {
			
            // Don't deliver to browsers
            if (sub.getConsumerInfo().isBrowser()) {
                continue;
            }
            // Only dispatch to interested subscriptions
            if (!sub.matches(node, msgContext)) {
                sub.unmatched(node);
                continue;
            }
			logger.info("ClientId:" + sub.getContext().getClientId());
			logger.info("destinationName:" + _destination.getQualifiedName());
            if (_clientId != null && _destination.isTopic() && _clientId.equals(sub.getContext().getClientId())
                    && _destination.getQualifiedName().endsWith("@ptp")) {
                //把消息推送给满足要求的subscription
                sub.add(node);
                count++;
            } else {
                sub.unmatched(node);
            }
        }

        return count > 0;
    }

}
