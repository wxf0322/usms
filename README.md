# 统一用户管理系统

> 基于 Spring + Spring MVC + Spring Data JPA + Apache Oltu 的OAuth2.0服务器

# 1 总体设计
# 1.1 逻辑设计
# 1.1.1 OAuth2认证过程
![usms](https://raw.githubusercontent.com/vancook/MarkdownPhotos/master/res/usms.jpg)
>(1) 浏览器从子系统跳转到USMS，请求用户认证与授权。  
(2) 用户认证通过，浏览器跳回子系统，并带回临时授权码code。  
(3) 子系统从服务端，带着code去USMS换取访问令牌accessToken。  
(4) USMS各种验证通过后，返回给子系统令牌accessToke。  
(5)	子系统使用accessToken去请求数据服务。  
(6)	USMS验证令牌后，返回所请求的数据。  
