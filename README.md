# OAuth2
Spring Boot + Security + OAuth2 + MySQL 实现4种OAuth2授权模式
使用数据库持久化 token令牌
采用OAuth2标准建表(以oauth开头的表)
建表sql在 schema.sql文件中
更改授权模式进行测试, 只需要改动 oauth2-client 中的OAuth2ProtectedResourceDetails实例实现即可
oauth2-oauthorization-server 完全支持4种授权模式, 无需更改

## 客户端 oauth2-client
## 授权服务器 oauth2-oauthorization-server
## 资源服务器 oauth2-resource-server
