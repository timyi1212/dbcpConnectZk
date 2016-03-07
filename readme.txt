使用dbcp第三方数据源
通常情况下，dbcp的配置通过spring进行管理
但是比如username、password、url、driver等敏感信息需要统一配置
针对于spring-datasource.xml进行配置
删除username、password、url、driver的property
添加connectString 对于zookeeper的url进行管理
添加appname作为uuid，对于appname进行管理
拼接字符串
/dbcp/$appname/usrname
/dbcp/$appname/passwd
/dbcp/$appname/url
/dbcp/$appname/driver

重写dbcp主类BasicDatasource
添加zookeeper工具方法
添加init方法