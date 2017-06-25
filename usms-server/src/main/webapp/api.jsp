<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>《统一用户管理系统API文档_v20170608_1》</title>
</head>
<body style="display:none">
<xmp theme="cerulean">
# 统一用户管理系统API文档
--------

## 1. 总体设计
### 1.1 框架选型
&nbsp;&nbsp;&nbsp;&nbsp;OAuth是一个关于授权（authorization）的开放网络标准，在全世界得到广泛应用，目前的版本是2.0版。它允许子系统通过令牌代替用户密码，访问用户存放在资源服务器的资源。

&nbsp;&nbsp;&nbsp;&nbsp;本系统基于**Apache OLTU**开发，**Apache OLUT**实现了**OAuth2.0**的规范，是一种可靠的授权解决方案。

### 1.2 名词解释

| 名词        | 解释                              |
|------------|----------------------------------------------------------------------------------------------------------------------|
| USMS       | Unified User Management System，统一用户管理子系统，包含与用户相关的信息（如基础资料、角色、权限、组织机构）的管理。 |
| 接入子系统   | 指接入统一用户管理的其他各应用子系统。简称“子系统”。                                                             |
| 认证服务器   | Authorization Server，统一用户管理系统中用于处理认证的服务器。                                                     |
| 资源服务器   | Resource Server，统一用户管理系统提供的资源服务，调用资源服务时，需要提交用户认证信息（access_Token）。              |
| Access Token | 令牌，在OAuth2协议中，子系统使用令牌代替用户密码作为认证信息，来获取资源服务。                                       |

### 1.3 逻辑设计
#### 1.3.1 OAuth2认证过程

![usms](./static/doc/usms.jpg)

1. 浏览器从子系统跳转到USMS，请求用户认证与授权。
2. 用户认证通过，浏览器跳回子系统，并带回临时授权码code。
3. 子系统从服务端，带着code去USMS换取访问令牌accessToken。
4. USMS各种验证通过后，返回给子系统令牌accessToke。
5. 子系统使用Access Token去请求数据服务。
6. USMS验证令牌后，返回所请求的数据。

#### 1.3.2 SSO过程
待定

## 2. 接口服务

### 2.1 公共部分

#### 2.1.1 返回状态码说明
请求结果首先通过状态码进行判断成功或失败，然后再根据具体内容解析json值。

| 状态码     | 说明                                    |
|------------|-----------------------------------------|
| 200        | 操作成功                                |
| 302        | 回调成功                                |
| 400        | 请求参数不完整或参数错误                 |
| 401        | 未授权，code/accessToken错误或已经失效。 |
| 403        | 禁止，例如要求post，却使用了get请求。    |
| 404        | 无法找到，没有该接口                     |

#### 2.1.2 错误返回说明

(1) 输出参数

| 参数名            | 说明             |
|-------------------|-----------------|
| error_description | 错误描述，中文。 |
| error             | 错误描述，英文。 |

(2) 输出样例

```
{
    "error_description": "错误的授权码。",
    "error": "invalid_grant"
}
```

(3) 备注

首先判断返回值，如果返回值是40X，则说明有错误。此时解析错误返回数据，查看具体是什么错误。


### 2.2 认证服务
#### 2.2.1 重定向到 authorize

(1) 接口说明

用户访问接入子系统时，接入子系统首先判断当前用户是否已经登录，若未登录则引导用户登录，登录成功后返回临时授权码，并跳转到子系统的指定回调页面。
若用户已登录，直接返回临时授权码，并跳转到子系统的指定回调页面。

(2) 接口地址

http://192.168.200.209:8080/usms/authorize

(3) 输入参数

| 参数名          | 是否必填 | 说明                         |
|----------------|--------|------------------------------|
| client_id      |   是    | 填子系统在 USMS 注册的client_id |
| response_type  |   是    | code: 授权码模式，token: 简化模式 |
| redirect_uri   |   是    | 子系统的回调地址                 |

##### 2.2.1.1 授权码模式

(1) 输入样例

```
http://192.168.200.209:8080/usms/authorize?client_id=0a626c2d-d0ac-45bd-9918-e66a4117cf4d&response_type=code&redirect_uri=http://www.baidu.com
```

(2) 输出样例

```
HTTP/1.1 302 Found
Location: http://www.baidu.com/?code=27893fa79e38e449baf09fc2b066f1cc
```

重定向到 http://www.baidu.com，其中 code=27893fa79e38e449baf09fc2b066f1cc 即为临时授权码。

(3) 备注

该接口请求在浏览器端完成，code对用户可见，code仅可使用一次。

##### 2.2.1.2 简化模式模式

(1) 输入样例

```
http://192.168.200.209:8080/usms/authorize?client_id=0a626c2d-d0ac-45bd-9918-e66a4117cf4d&response_type=token&redirect_uri=http://www.baidu.com
```

(2) 输出样例

```
HTTP/1.1 302 Found
Location: http://www.baidu.com/#access_token=27893fa79e38e449baf09fc2b066f1cc&expires_in=3600
```

重定向到 http://www.baidu.com

其中 access_token=27893fa79e38e449baf09fc2b066f1cc 即为访问令牌。
其中 expires_in=3600 为访问令牌有效时间。

(3) 备注

该接口请求在浏览器端完成，assess_token对用户可见，请在前端调用需要访问的api。


#### 2.1.2 请求access_token

(1) 接口说明

子系统使用临时授权码code，去换取accessToken，授权码的有效时间为5分钟。

(2) 接口地址

http://192.168.200.209:8080/usms/accessToken

(3) 输入参数

| 参数名        | 是否必填    | 说明     |
|---------------|-------------|----------|
| client_id     | 是           | 填子系统在USMS注册的 client_id    |
| client_secret | 是           | USMS给子系统生成的KEY             |
| grant_type    | 是           | 固定值：authorization_code        |
| code          | 是           | 上个步骤获取的临时授权码         |
| redirect_uri  | 是           | 与第一步请求的 redirect_uri 相同  |

(4) 输入样例

```
POST /token HTTP/1.1
Host: 192.168.200.209
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=http://www.baidu.com
```

(5) 输出参数

| 参数名        | 说明              |
|---------------|------------------|
| access_token  | 获取到的访问令牌  |
| expires_in    | 失效时间，单位秒  |

(6) 输出样例

```
{
    "access_token": "223ae05dfbb0794396fb60a0960c197e",
    "expires_in": "3600"
}
```

(7) 备注

该请求在服务端只能通过post提交，获取到access token后，子系统可将其保存，做后续使用。

#### 2.1.3 登出全部系统

(1) 接口说明

用户退出，该用户登入的所有系统

(2) 接口地址

重定向至 http://192.168.200.209:8080/usms/v1/openapi/loginOut ，进行退出

(3) 输入参数

| 参数名        |   说明   |
|---------------|-----------|
| access_token  | 访问令牌  |
| client_id     | 填子系统在USMS注册的 client_id |
| redirect_uri  | 重定向到登入页面之后，登入页面需要跳转的地址 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/loginOut?access_token=ACCESS_TOKEN&client_id=CLIENT_ID&redirect_uri=REDIRECT_URI
```

(5) 输出结果
```
重定向到登入页面，输入用户名和密码之后，重定向至redirect_uri参数所填写的页面。
```

#### 2.1.4 判断accessToken是否有效

(1) 接口说明

判断 Access Token 是否有效

(2) 接口地址

http://192.168.200.209:8080/usms/checkAccessToken

(3) 输入参数

| 参数名        | 是否必填      | 说明     |
|---------------|--------------|----------|
| access_token  | 是           | 访问令牌  |

(4) 输入样例

```
http://192.168.200.209:8080/usms/checkAccessToken?access_token=ACCESS_TOKEN
```

(5) 输出参数

| 状态码   | 说明      |
|----------|-----------|
| 200      | 成功      |
| 400      | 失败      |

## 2.3 资源服务

### 2.3.1 用户具体信息，根据access_token查询

(1) 接口说明

返回当前用户的具体信息，根据access_token查询。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/user

(3) 输入参数

| 参数名       | 是否必填   | 说明     |
|--------------|-------------|----------|
|access_token  |是           | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/user?access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名      | 说明        |
|-------------|-------------|
| id          | 用户ID      |
| loginName	  |  登录名     |
| staff       |	关联员工的具体信息，具体参数详见 2.3.2 节 输出参数 |
| application | 当前接入系统的应用信息，里面包含着操作信息 |
| operations  | 该用户所拥有的操作信息，具体参数详见 2.3.10 节 输出参数 |
| institutions | 该用户所属的组织机构信息，具体参数详见 2.3.14 节 输出参数 |


(6) 输出样例

```
{
    "id": 22,
    "loginName": "admin",
    "staff": {
        "name": "陈楠",
        "tel": "0591-87666633",
        "mobile": "13806028806",
        "zipCode": "350000",
        "email": "cn@evecom.net",
        "extranetEmail": "test@qq.com",
        "aliasNames": "陈楠",
        "sex": 1,
        "professionalTitle": "主任工程师",
        "officalPost": "架构师",
        "officalDuty": "",
        "employeeType": "",
        "employeeNo": "10000000",
        "citizenIdNumber": "",
        "birthday": 1496374828,
        "adminDivisionCode": "350100",
        "adminDivision": "福州",
        "curResidence": "福州",
        "remarks": "",
        "pictureUrl": ""
    },
    "institutions": [
        {
            "adminDivision": "福州",
            "adminDivisionCode": "350100",
            "id": 1,
            "label": "研发中心",
            "name": "yfzx",
            "orgCode": "1000",
            "parentId": 0,
            "socialCreditUnicode": "rr",
            "type": 1
        },
        {
            "adminDivision": "福州",
            "adminDivisionCode": "350100",
            "id": 2,
            "label": "研发一部",
            "name": "yfyb",
            "orgCode": "1001",
            "parentId": 1,
            "socialCreditUnicode": "45",
            "type": 1
        }
    ],
    "application" : {
        "id": 10
        "label": "综治网格",
        "name": "zzwg",
        "clientId": "",
        "clientSecret": "",
        "operations": [{
            "iconPath": "2",
            "id": 1,
            "label": "增加",
            "name": "add",
            "optType": 2,
            "parentId": 0,
            "resUrl": "2"
        }]
    }
}
```

### 2.3.2 用户列表，根据管辖区域编码查询，需access_token鉴权

(1) 接口说明

返回用户列表，根据管辖区域编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填   | 说明      |
|--------------|-----------|-----------|
|grid          |是         | 网格编码，注意该输入参数对应数据库中的 **name** 字段 |
|access_token  |是         | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?grid=GRID_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名       |   说明       |
|------------------|---------------|
| id                 | 用户ID        |
| loginName          | 登录名            |
| name               | 姓名              |
| tel                | 固定电话          |
| zipCode            | 邮编              |
| mobile             | 手机电话          |
| email              | 内网邮箱          |
| extranetEmail      | 外网邮箱          |
| aliasNames         | 别名              |
| sex                | 性别              |
| professionalTitle  | 职称              |
| officalPost        | 职责              |
| officalDuty        | 职务              |
| employeeType       | 员工类型          |
| employeeNo         | 员工工号          |
| birthday           | 出生日期，注意：这里返回的日期为数值类型的时间戳，需要通过代码转化为日期类型 |
| adminDivisionCode  | 居住地行政区划编号 |
| adminDivision      | 居住地行政区划    |
| curResidence       | 现居住地址        |
| remarks            | 备注说明          |
| citizenIdNumber    | 身份证号码        |
| pictureUrl         | 照片路径          |

(6) 输出样例

```
{
    "users": [
        {
            "id": "",
            "loginName": "",
            "name": "",
            "tel": "",
            "zipCode": "",
            "mobile": "",
            "email": "",
            "extranetEmail": "",
            "aliasNames": "",
            "sex": "",
            "professionalTitle": "",
            "officalPost": "",
            "officalDuty": "",
            "employeeType": "",
            "employeeNo": "",
            "birthday": "",
            "adminDivisionCode": "",
            "adminDivision": "",
            "curResidence": "",
            "remarks": "",
            "citizenIdNumber": "",
            "pictureUrl": ""
        }
    ]
}
```

### 2.3.3 用户列表，根据组织机构编码查询，需access_token鉴权

(1) 接口说明

返回用户列表，根据组织机构编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
|institution   |是           | 组织机构编码，注意该输入参数对应表：**usms_institution** 的 **name** 字段 |
|access_token  |是           | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?institution=INSTITUTION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.2 节 输出参数

(6) 输出样例
同 2.3.2 节 输出样例

### 2.3.4 用户列表，根据应用编码查询，需access_token鉴权
(1) 接口说明


返回用户列表，根据应用编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填    | 说明    |
|-------------|-------------|----------|
|operation    | 是          | 应用编码，注意该输入参数对应表：**usms_operations** 的 **name** 字段  |
|access_token | 是          | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?operation=OPERATION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.2 节 输出参数

(6) 输出样例
同 2.3.2 节 输出样例

### 2.3.5 用户列表，根据权限编码查询，需access_token鉴权
(1) 接口说明

返回用户列表，根据权限编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
|privilege     |是           | 操作编码，注意该输入参数对应表：**usms_privileges** 的 **name** 字段  |
|access_token  |是           | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?privilege=PRIVILEGE_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.2 节 输出参数

(6) 输出样例
同 2.3.2 节 输出样例

### 2.3.6 用户列表，根据操作编码查询，需access_token鉴权

(1) 接口说明

返回用户列表，根据操作编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
|operation     | 是          | 网格编码，注意该输入参数对应表：**usms_operations** 的 **name** 字段  |
|access_token  | 是          | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?operation=OPERATION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.2 节 输出参数

(6) 输出样例
同 2.3.2 节 输出样例

### 2.3.7 用户列表，根据角色编码查询，需access_token鉴权
(1) 接口说明


返回用户列表，根据角色编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
|role          |是           | 网格编码，注意该输入参数对应表：**usms_roles** 的 **name** 字段 |
|access_token  |是           | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?role=ROLE_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.2 节 输出参数

(6) 输出样例
同 2.3.2 节 输出样例

### 2.3.8 用户列表，根据职务查询，需access_token鉴权

(1) 接口说明

返回用户列表，根据职务查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|---------|
| offical_post |是           | 职务名称 |
| access_token |是           | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/users?offical_post=OFFICAL_POST&access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.2 节 输出参数

(6) 输出样例
同 2.3.2 节 输出样例

### 2.3.9 权限列表，根据应用编码查询，需access_token鉴权

(1) 接口说明

返回权限列表，根据应用编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/privileges

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
| application  | 是      | 操作编码，注意该输入参数对应表：**usms_applications** 的 **name** 字段|
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/privileges?application=APPLICATION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名      | 说明    |
|------------|----------|
| id       | 权限id           |
| label    | 权限名称中文     |
| name     | 权限名称编码     |

(6) 输出样例

```
{
    "privileges: [
    {
      "id": "",
      "label": "",
      "name": "",
    }
  ]
}
```

### 2.3.10 操作列表，根据应用编码查询，需access_token鉴权


(1) 接口说明

返回操作列表，根据应用编码查询，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/operations

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
| application  | 是      | 操作编码，注意该输入参数对应表：**usms_applications** 的 **name** 字段 |
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/privileges?application=APPLICATION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名      | 说明    |
|------------|----------|
| id         | 操作id                |
| parentId   | 父亲ID                |
| label      | 操作名称_中文          |
| name       | 操作名称_英文          |
| resUrl    | 操作URL               |
| iconPath  | 操作图标路径            |
| optType   | 操作类型 (1:菜单 2:按钮 3: 系统)   |

(6) 输出样例

```
{
    "operations": [
        {
            "id": "",
            "parentId": "",
            "label": "",
            "name": "",
            "resUrl": "",
            "iconPath": "",
            "optType": ""
        }
    ]
}
```

### 2.3.11 判断是否拥有该角色，根据access_token，角色编码查询

(1) 接口说明

判断是否拥有该角色，根据access_token，角色编码查询

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/role/exist

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
| role  | 是      | 角色编码，注意该输入参数对应表：**usms_roles** 的 **name** 字段 |
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/role/exist?role=ROLE_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名     | 说明    |
|------------|----------|
| result     |     返回结果，true为是，false为否  |


(6) 输出样例

```
{ "result": true }
```

### 2.3.12 判断是否拥有该权限，根据access_token，权限编码查询

(1) 接口说明

判断是否拥有该权限，根据access_token，权限编码查询

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/privilege/exist

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|---------|
| privilege    | 是          | 权限编码，注意该输入参数对应表：**usms_privileges** 的 **name** 字段 |
| access_token | 是          | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/privilege/exist?privilege=PRIVILEGE_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名     | 说明    |
|-----------|----------|
| result    |     返回结果，true为是，false为否  |


(6) 输出样例

```
{ "result": true }
```


### 2.3.13 判断是否拥有该操作，根据access_token，操作编码查询

(1) 接口说明

判断是否允许该操作，根据access_token，操作编码查询

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/operation/exist

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
| operation  | 是      | 操作编码，注意该输入参数对应表：**usms_privileges** 的 **name** 字段 |
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/operation/exist?operation=OPERATION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名       | 说明    |
|-------------|--------|
| result      |   返回结果，true为是，false为否  |


(6) 输出样例

```
{ "result": true }
```

### 2.3.14 所有组织机构列表，需access_token鉴权

(1) 接口说明

返回所有组织机构列表，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/institutions/all

(3) 输入参数

| 参数名       | 是否必填 | 说明    |
|--------------|---------|----------|
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/institutions/all?access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名                | 说明                |
|-----------------------|---------------------|
| id                    | 机构id              |
| label                 | 中文名称            |
| name                  | 英文名称            |
| parentId              | 父机构ID            |
| type                  | 类型 1:机构 2:部门  |
| adminDivisionCode     | 行政区划代码        |
| adminDivision         | 行政区划            |
| socialCreditUnicode   | 社会信用统一代码    |
| orgCode               | 组织机构代码        |

(6) 输出样例

```
{
    "institutions": [
        {
            "id": "",
            "label": "消防网格",
            "name": "net.evecom.gsmp.fr",
            "treeLevel": "2",
            "parentId": "",
            "type": "1",
            "manualSn": "2",
            "adminDivisionCode": "",
            "adminDivision": "",
            "socialCreditUnicode": "",
            "orgCode": ""
        }
    ]
}
```

### 2.3.15 组织机构列表，根据access_token查询

(1) 接口说明

返回当前用户所属的组织机构列表，根据access_token查询。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/institutions

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/institutions?access_token=ACCESS_TOKEN
```

(5) 输出参数
同 2.3.14 输出参数

(6) 输出样例
同 2.3.14 输出样例

### 2.3.16 组织机构信息，根据组织机构编码查询，需access_token鉴权

(1) 接口说明

组织机构信息，根据组织机构编码查询，需access_token鉴权

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/institutions

(3) 输入参数

| 参数名       | 是否必填     | 说明    |
|--------------|-------------|----------|
| institution  | 是      | 组织机构编码，注意该输入参数对应表：**usms_institutions** 的 **name** 字段 |
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/institution?institution=INSTITUTION_NAME&access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名               |       说明                   |
|-----------------------|------------------------------|
| id                    | 机构id                       |
| label                 | 中文名称                     |
| name                  | 英文名称                     |
| parentId              | 父机构ID                     |
| type                  | 类型 1:机构 2:部门           |
| adminDivisionCode     | 行政区划代码                 |
| adminDivision         | 行政区划                     |
| socialCreditUnicode   | 社会信用统一代码             |
| orgCode               | 组织机构代码                 |

(6) 输出样例

```
{
    "id": 1,
    "label": "消防网格",
    "name": "net.evecom.gsmp.fr",
    "treeLevel": "2",
    "parentId": "",
    "type": "1",
    "manualSn": "2",
    "adminDivisionCode": "",
    "adminDivision": "",
    "socialCreditUnicode": "",
    "orgCode": ""
}
```

### 2.3.17 管辖区域列表，根据access_token查询

(1) 接口说明

返回当前用户所属的管辖区域列表，根据access_token查询

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/grids

(3) 输入参数

| 参数名       | 是否必填  | 说明    |
|--------------|----------|---------|
| access_token | 是       | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/grids?access_token=ACCESS_TOKEN
```

(5) 输出参数

| 参数名                | 说明                     |
|-----------------------|---------------------------|
| id                    | 网格id                    |
| code                  | 网格编码                  |
| lvl                   | 网格层级                  |
| name                  | 网格名称                  |
| gridType              | 网格类型                  |
| descripiton           | 描述                      |
| dutyPhone             | 值班电话                  |
| photoUrl              | 网格图片                  |
| memberNum             | 网格员数量                |
| householdeNum         | 网格户数                  |
| area                  | 网格面积（平方米）        |
| parentId              | 上级网格ID                |
| adminDivisionCode     | 隶属行政区划编码          |
| adminDivision         | 隶属行政区划              |
| manualSn              | 手动排序                  |
| geoOutlineId          | 轮廓ID                    |
| enabled               | 可用状态（0: 冻结 1: 正常）|

(6) 输出样例

```
{
    "grids": [
        {
            "id": "",
            "code": "消防网格",
            "lvl": "net.evecom.gsmp.fr",
            "name": "2",
            "gridType": "",
            "description": "1",
            "dutyPhone": "",
            "photoUrl": "",
            "memberNum": "",
            "householdeNum": "",
            "area": "",
            "parentId": "2",
            "adminDivisionCode": "",
            "adminDivision": "",
            "manualSn": "",
            "geoOutlineId": "",
            "enabled": ""
        }
    ]
}
```

 ### 2.3.18 返回管辖区域列表，需access_token鉴权

(1) 接口说明

返回所有的管辖区域列表，根据access_token查询

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/grids/all

(3) 输入参数

| 参数名       | 是否必填  | 说明    |
|--------------|----------|---------|
| access_token | 是       | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/grids/all?access_token=ACCESS_TOKEN
```

(5) 输出参数

同 2.3.17 输出参数

(6) 输出样例

同 2.3.17 输出样例


### 2.3.19 角色列表，需access_token鉴权

(1) 接口说明

返回所有角色的列表，需access_token鉴权。

(2) 接口地址

http://192.168.200.209:8080/usms/v1/openapi/roles

(3) 输入参数

| 参数名       | 是否必填 | 说明    |
|--------------|---------|---------|
| access_token | 是      | 访问令牌 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/openapi/roles?access_token=ACCESS_TOKEN
```

### 2.3.20 用户列表，根据组织编码集合

(1) 接口说明

根据组织编码集合获取用户信息

(2) 接口地址

http://192.168.200.209:8080/usms/v1/internalapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明     |
|--------------|-------------|----------|
|institutions  |是           | 组织机构编码集合，用 "," 连接 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/internalapi/users?institutions=yfzx,yfwb
```

(5) 输出参数

同 2.3.2 节 输出参数

(6) 输出样例

同 2.3.2 节 输出样例

### 2.3.21 用户列表，角色编码集合

(1) 接口说明

根据角色编码集合获取用户信息

(2) 接口地址

http://192.168.200.209:8080/usms/v1/internalapi/users

(3) 输入参数

| 参数名 | 是否必填  | 说明     |
|--------|-----------|----------|
| roles  | 是        | 角色编码集合，用 "," 连接 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/internalapi/users?roles=admin,guest
```

(5) 输出参数

同 2.3.2 节 输出参数

(6) 输出样例

同 2.3.2 节 输出样例


### 2.3.22 用户列表，根据登入名集合

(1) 接口说明

根据登入名集合获取用户信息

(2) 接口地址

http://192.168.200.209:8080/usms/v1/internalapi/users

(3) 输入参数

| 参数名       | 是否必填     | 说明     |
|-------------|------------|----------|
|login_names  |是          | 登入名集合，用 "," 连接 |

(4) 输入样例

```
http://192.168.200.209:8080/usms/v1/internalapi/users?login_names=admin,vance
```

(5) 输出参数

同 2.3.2 节 输出参数

(6) 输出样例

同 2.3.2 节 输出样例

</xmp>

<script src="${pageContext.request.contextPath}/static/plugins/strapdown/strapdown.js"></script>

</body>
</html>