# 统一用户管理系统
### unified user management system

> 基于 Spring + Spring MVC + Spring Data JPA + Redis 的统一用户管理系统

# 1 总体设计
## 1.1 框架选型
> OAuth是一个关于授权（authorization）的开放网络标准，在全世界得到广泛应用，目前的版本是2.0版。它允许子系统通过令牌代替用户密码，访问用户存放在资源服务器的资源。
  本系统基于Apache OLTU开发，Apache OLUT实现了OAuth2.0的规范，是一种可靠的授权解决方案。

## 1.2 名词解释
| 名词        |  解释          |
| ------------- |:-------------:|
| USMS      | unified user management system，统一用户管理子系统，包含与用户相关的信息（如基础资料、角色、权限、组织机构）的管理。 |
| 接入子系统 | 指接入统一用户管理的其他各应用子系统。简称“子系统”。 |
| 认证服务器 | Authorization server，统一用户管理系统中用于处理认证的服务器。 |
| 资源服务器 | Resource server，统一用户管理系统提供的资源服务，调用资源服务时，需要提交用户认证信息（access_Token）。 |
| Access_Token | 令牌，在OAuth2协议中，子系统使用令牌代替用户密码作为认证信息，来获取资源服务。 |

## 1.3 常量编码与命名约定
接入的应用、角色、权限、操作的名称均使用常量定义，且命名符合ClassName命名法。

# 1.4 逻辑设计
# 1.4.1 OAuth2认证过程
![usms](https://raw.githubusercontent.com/vancook/MarkdownPhotos/master/res/usms.jpg)
(1) 浏览器从子系统跳转到USMS，请求用户认证与授权。  
(2) 用户认证通过，浏览器跳回子系统，并带回临时授权码code。  
(3) 子系统从服务端，带着code去USMS换取访问令牌accessToken。  
(4) USMS各种验证通过后，返回给子系统令牌accessToke。  
(5)	子系统使用accessToken去请求数据服务。  
(6)	USMS验证令牌后，返回所请求的数据。  





