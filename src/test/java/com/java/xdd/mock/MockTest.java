package com.java.xdd.mock;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

/**
 * Created by qw on 2017/12/27.
 */
public class MockTest {
    @Test
    public void test() {
        IMocksControl control = EasyMock.createControl();
        control.checkOrder(false);
        System.out.println();
    }

}
