### elasticdump迁移数据
####  elasticdump迁移数据

```
    elasticdump迁移数据
    迁移三步走
    ./elasticdump --input=http://"elastic:Jyadmin666666!"@192.168.2.211:9401/es_t_bp_item_core_sj --output=http://"elastic:123456"@127.0.0.1:9200/es_t_bp_item_core_sj --type=settings
    ./elasticdump --input=http://"elastic:Jyadmin666666!"@192.168.2.211:9401/es_t_bp_item_core_sj --output=http://"elastic:123456"@127.0.0.1:9200/es_t_bp_item_core_sj --type=mapping
    ./elasticdump --input=http://"elastic:Jyadmin666666!"@192.168.2.211:9401/es_t_bp_item_core_sj --output=http://"elastic:123456"@127.0.0.1:9200/es_t_bp_item_core_sj --type=data

```








