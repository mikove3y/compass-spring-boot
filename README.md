# COMPASS架构

# [详述]

**更新记录：**

| **版本** | **时间** | **变更描述** | **作者** | **评审人** |
| --- | --- | --- | --- | --- |
| V1.0 | 20190408 | 新建 | wanmk |   |


## 1前言

注： compass架构依赖于spring-boot进行构建，提供web、data等各种即插即用的模块，本文档将对各个模块进行讲解.











目录

| 前言 |
| --- |
| 架构 |
| 依赖 |
| 模块 |
| Base模块 |
| Util模块 |
| AutoConfig模块 |
| Web模块 |
| Data模块 |
| Drools模块 |
| Cache模块 |
| Activiti模块 |
| LCN模块 |
| Swagger模块 |
| |












## 2架构

架构模块列表

| 模块 | 描述 |
| --- | --- |
| base | 基础模块包含枚举、vo、entity、常量等 |
| util | 工具模块包含基本的工具类，如时间等 |
| autoconfig | 自动化配置模块包含jwt、constant等 |
| web | Web模块包含日志切面、全局上线文、logback全局日志等 |
| camel | Esb模块包含服务编排等 |
| data | 持久化模块包含mybatis和jpa封装 |
| drools | 规则引擎模块 |
| cache | 缓存模块包含redis以及caffeine二级缓存 |
| activiti | 工作流引擎模块包含工作流和规则引擎 |
| lcn | 分布式事务模块 |
| swagger | 在线api文档模块 |


## 3依赖

| 分组 | 坐标 | 版本 | 描述 |
| --- | --- | --- | --- |
| org.springframework.boot | spring-boot-dependencies | 1.5.15.RELEASE | Spring-boot基础依赖 |
| org.springframework.cloud | spring-cloud-dependencies | Edgware.SR4 | Spring-cloud基础依赖 |
| org.apache.camel | camel-spring-boot-dependencies | 2.21.1  | Camel依赖 |
| javax.persistence | javax.persistence-api | 2.2 | Javax持久化依赖 |
| org.mybatis.spring.boot | mybatis-spring-boot-starter | 1.3.2  | Mybatis依赖 |
| com.github.pagehelper  | pagehelper-spring-boot-starter  | 1.2.5  | Mybatis分页插件依赖 |
| com.baomidou | mybatis-plus-boot-starter | 2.3  | Mybatis增加依赖 |
| io.shardingsphere | sharding-jdbc-orchestration-spring-boot-starter | 3.1.0.M1  | 数据自理依赖 |
| io.shardingsphere  | sharding-jdbc-spring-boot-starter | 3.1.0.M1  | 分库分表依赖 |
| com.codingapi | transaction-springcloud | 4.1.0  | 分布式事务依赖 |
| com.codingapi | tx-plugins-db | 4.1.0 | 分布式事务依赖 |
| com.fasterxml.jackson.datatype | jackson-datatype-jsr310 | 2.9.5  | Jackson依赖 |
| org.apache.commons | commons-lang3 | 3.7 | Apache工具集依赖 |
| commons-beanutils | commons-beanutils | 1.9.3 | Apache bean工具依赖 |
| commons-fileupload | commons-fileupload | 1.3.3 | Apache文件上传依赖 |
| com.esotericsoftware | kryo-shaded | 4.0.2  | Kryo序列化依赖 |
| de.javakaffee | kryo-serializers | 0.45  | Kryo序列化依赖 |
| com.belerweb | pinyin4j | 2.5.0 | 拼音工具依赖 |
| io.jsonwebtoken | jjwt | 0.9.0 | Jwt-token依赖 |
| org.apache.shiro | shiro-spring | 1.4.0 | Shiro鉴权依赖 |
| joda-time | joda-time | 2.9.9 | 日期工具依赖 |
| com.alibaba | druid-spring-boot-starter | 1.1.10 | 数据库连接池依赖 |
| io.springfox | springfox-swagger2 | 2.9.2 | Swagger依赖 |
| com.github.xiaoymin  | swagger-bootstrap-ui | 1.9.0  | Swagger-ui增加依赖 |
| com.google.zxing | core | 3.3.3 | Zxing码依赖 |
| javax.ws.rs | javax.ws.rs-api | 2.1 | 参数校验依赖 |
| com.google.guava | guava | 27.0.1-jre  |   |
| net.sourceforge.nekohtml | nekohtml | 1.9.22  | Webjars依赖 |
| net.logstash.logback  | logstash-logback-encoder | 5.3  | Logstash-logback依赖 |
| org.kie | kie-spring | 7.6.0.Final | Drools依赖 |
| org.activiti | activiti-spring | 5.22.0 | Activiti依赖 |
| org.activiti | activiti-modeler | 5.22.0 |
| org.activiti  | activiti-diagram-rest | 5.22.0 |
| com.fasterxml.uuid | java-uuid-generator | 3.1.5 | Uuid生成工具依赖 |




## 4模块

  4.1Base模块

注:Base模块提供基础的实体、vo、枚举、常量、注解、异常封装类，作为所有模块的基础支持。

注解：

| 类名 | 功能描述 |
| --- | --- |
| ApiModel | Api在线文档模型注解 |
| ApiModelProperty | Api在线文档模型属性注解 |
| LogicDelete | 逻辑删除注解 |

常量&amp;枚举：

注：基类业务枚举code为int型，基类业务枚举2code为string型

| 类名 | 功能描述 |
| --- | --- |
| BaseBizeStatusEnum | 基类业务枚举【是否、文件类型】 |
| BaseBizStatusEnumDeserializer | 基类业务枚举json反序列化 |
| BaseBizStatusEnumDeserializer2 | 基类业务枚举2json反序列化 |
| BaseBizStatusEnumSerializer | 基类业务枚举json序列化 |
| BaseBizStatusEnumSerializer2 | 基类业务枚举2json序列化 |
| BaseConstant | 基类常量【相应码、请求头参】 |
| IBaseBizStatusEnum | 基类业务枚举接口 |
| IBaseBizStatusEnum2 | 基类业务枚举2接口 |

实体:

| 类名 | 功能描述 |
| --- | --- |
| BaseAttachmentEntity | 基类附件实体 |
| BaseEntity | 基类实体 |
| BaseLevelTreeEntity | 基类树形实体 |

异常:

| 类名 | 功能描述 |
| --- | --- |
| BaseException | 基类运行时异常-统一异常 |

工具:

| 类名 | 功能描述 |
| --- | --- |
| LevelTreeUtil | 层级树工具类-格式化输出树形结构数据 |
| RegUtil | 正则工具类-匹配文件类型 |

VO:

| 类名 | 功能描述 |
| --- | --- |
| AppPage | APP分页响应vo |
| BaseDataX | 基类请求数据转换类 |
| BaseErroVo | 基类错误信息vo |
| BaseLevelTreeResponseVo | 基类层级树响应vo |
| BaseLogVo | 基类日志vo |
| BaseRequestAppPageVo | 基类APP分页请求vo |
| BaseRequestPcPageVo | 基类PC分页请求vo |
| BaseResponseVo | 基类响应vo |
| BaseSubject | 基类用户信息vo |
| BaseVueTreeVo | 基类vue树响应vo |
| PcPage | PC分页响应vo |


  4.2Util模块

注:Util模块提供一些通用的工具，如：动画、加密、数据复制、日期、编解码、地图转换、异常、json、随机数、序列化、guid、url、条码等。

Uil:

| 类名 | 功能描述 |
| --- | --- |
| AnimatedGifUtil | GIF动画工具类 |
| ASEUtil | ASE加密工具类 |
| Base32 | Base32编解码工具类 |
| Base64 | Base64编解码工具类 |
| Blowfish | Blowfish加密算法工具类 |
| CharsetUtil | 字符编码工具类 |
| DataXUtil | 数据交换工具类 |
| DESUtil | DES加密工具类 |
| EarthMapUtil | 地图经纬度工具类 |
| ExceptionUtil | 异常输出工具类 |
| JacksonUtil | Json工具类 |
| Md5Util | MD5加密工具类 |
| RadomUtil | 随机工具类 |
| SerializeUtil | 序列化工具类 |
| SnowflakeIdWorker | Twitter\_Snowflake算法工具类-生成guid |
| UrlUtil | URL工具类 |
| ZxingUtil | 条码工具类 |


  4.3AutoConfig模块

注:AutoConfig模块提供一些常用的自动化配置，如：jwt、常量、异构系统、异步任务、jpa扩展、定时任务、yml等

自动化配置:

| 类名 | 功能描述 |
| --- | --- |
| ArchModuleAutoConfiguration | 异构系统自动化配置 |
| AsyncTaskAutoConfiguration | 异步任务自动化配置 |
| ConstantAutoConfiguration | 常量自动化配置 |
| JpaExtProperties | JPA扩展 |
| JwtAutoConfiguration | JWT自动化配置 |
| SchedulingTaskAutoConfiguration | 定时任务自动化配置 |
| YamlPropertySourceFactory | Yml自定义工厂 |


  4.4Web模块

注:Web模块提供web相关的功能，如：日志切面、api版本管理、全局上下文、feign+robbin远程调用、统一异常处理、logback统一追溯ID、request请求封装

Web:

| 类名 | 功能描述 |
| --- | --- |
| BaseMvcConfig | MVC配置 |
| BaseController | 基类controller类 |
| UniversalEnumConverterFactory | 全局IbaseBizStatusEnum转换工厂 |

日志切面:

| 类名 | 功能描述 |
| --- | --- |
| BaseApiAspect | 异构系统自动化配置 |

Api版本管理:

| 类名 | 功能描述 |
| --- | --- |
| ApiVersionRequestHandlerMapping | Api版本管理请求处理器映射 |

全局上下文:

| 类名 | 功能描述 |
| --- | --- |
| AppContext | Spring全局上下文-bean获取 |
| GlobalContext | 自定义全局上下文reques/response/BaseSubject获取 |

Feign+Robbin远程调用:

| 类名 | 功能描述 |
| --- | --- |
| FeignConfig | Feign配置类 |
| RibboConfig | Robbin配置类 |

统一异常处理:

| 类名 | 功能描述 |
| --- | --- |
| BaseExceptionHandler | 统一异常封装 |

Logback统一追溯ID:

| 类名 | 功能描述 |
| --- | --- |
| MessageIdFilter | Logback统一追溯ID过滤器 |

Request请求封装:

| 类名 | 功能描述 |
| --- | --- |
| CustomHttpServletRequestWrapper | Request请求包装类 |


  4.5Camel模块

注:Camel模块提供ESB服务编排功能，Camel的功能很强大需要深度挖掘，暂时未在项目上使用。

参考:

配置:

| 类名 | 功能描述 |
| --- | --- |
| CamelConfig | Camel配置类 |

路由:

| 类名 | 功能描述 |
| --- | --- |
| BaseRouter | 基类路由 |

处理器:

| 类名 | 功能描述 |
| --- | --- |
| BaseErrorProcessor | 错误处理器 |
| DefaultProcessor | 默认处理器 |




  4.6Data模块

注:Data模块提供orm功能，封装了mybatis+jpa，mybatis负责复杂查询，jpa负责建表、单表增删改查等功能。

自动化配置:

| 类名 | 功能描述 |
| --- | --- |
| JpaDbEnumTypeHandler | JPA枚举处理器 |
| MybatisDbEnumTypeHandler | Mybatis枚举处理器 |
| MySQL5InnoDBDialectWithoutFK | JPA-Mysql方言去除外键封装 |
| Oracle10gDialectWithoutFK | JPA-Oracle方言去除外键封装 |
| PostgreSQL9DialectWithoutFK | JPA-PostgreSql方言去除外键封装 |
| BaseEntityRepository | JPA 基类实例仓库接口 |
| BaseEntityRepositoryFactoryBean | JPA 基类实例仓库工厂 |
| BaseEntityRepositoryImpl | JPA 基类实例仓库接口实现类 |
| BaseEntityServiceImpl | 基类实例服务接口实现类 |
| IBaseEntityService | 基类实例服务接口 |
| PageTransformUtil | 分页查询数据转换工具 |


  4.7Drools模块

注:Drools模块提供规则引擎服务。暂时未有业务模块使用该模块。

配置:

| 类名 | 功能描述 |
| --- | --- |
| DroolsConfig | 规则引擎配置类 |


  4.8Cache模块

注:Cache模块提供缓存服务，封装了redis和caffeine一二级缓存。

参考：

配置:

| 类名 | 功能描述 |
| --- | --- |
| RedisConfig | 缓存配置类 |

Cache管理器:

| 类名 | 功能描述 |
| --- | --- |
| LayeringCache | 缓存包装类 |
| LayeringCacheManager | 缓存管理器 |
| FirstCacheSetting | 一级缓存设置 |
| SecondaryCacheSetting | 二级缓存设置 |

Cache监听:

| 类名 | 功能描述 |
| --- | --- |
| RedisMessageListener | Redis缓存消息监听器 |
| RedisPublisher | Redis缓存消息发布器 |

工具:

| 类名 | 功能描述 |
| --- | --- |
| RedisUtil | Redis工具类 |


  4.9Activiti模块

注:Activiti模块提供流程流程以及规则服务，并升级了activiti 5.22.0版本的drools依赖，自定义stencilset用户活动UserTask增加actioncodepackage配置用于适配流程动作节点。流程模块采用前后端完全分离策略。

配置:

| 类名 | 功能描述 |
| --- | --- |
| ActivitiConfig | Activiti配置 |

Cache管理器:

| 类名 | 功能描述 |
| --- | --- |
| AutoCompleteFirstTaskEventListener | 自动跳过第一个任务节点监听器 |
| AutoCompleteCmd | 自动完成任务CMD |
| JumpTaskCmd | 自动跳过任务CMD |

Cache监听:

| 类名 | 功能描述 |
| --- | --- |
| CustomActivityBehaviorFactory | 自定义行为工厂-升级drools |
| CustomBusinessRuleTaskActivityBehavior | 自定义行为-升级drools |
| RulesAgendaFilter | 规则引擎rule分组过滤器 |
| RulesDeployer | 规则引擎部署器 |
| RulesHelper | 规则引擎帮助类 |

工具:

| 类名 | 功能描述 |
| --- | --- |
| stencilset.json | 自定义流程配置文件 |
| CustomBpmnJsonConverter | 自定义json转换器 |
| CustomUserTaskJsonConverter | 自定义UserTask json转换器 |


  4.10LCN模块

注:LCN模块提供分布式事务控制支持

参考: https://github.com/codingapi/tx-lcn.git

http://www.txlcn.org/zh-cn/

配置:

| 类名 | 功能描述 |
| --- | --- |
| TxManagerHttpRequestServiceImpl | LCN事务TX-Manager请求封装 |
| TxManagerTxUrlServiceImpl | LCN事务TX-Manager地址获取封装 |



  4.11Swagger模块

注:Swagger模块参考https://github.com/xiaoymin/Swagger-Bootstrap-UI.git 做了部分优。，

优化项目: bycdao-ui/cdao/swaggerbootstrapui.js

1、去掉拼装url ，灵活支持swagger配置的地址；

SwaggerBootstrapUi.prototype.buildCurl

2、扩展支持自定义host,去掉过滤和截取url；

SwaggerBootstrapUi.prototype.createApiInfoInstance





## 5优化

注:当前架构存在代码分割不太合理的地方需要重新进行排版，camel模块可以进行深度挖掘进行服务编排由于项目工期问题搁置了；

需要调整代码的模块：

| 模块 | 描述 |
| --- | --- |
| Base |1、Base模块可以拆分Entity到Data模块去 2、请求或者响应对象包含以下IbaseBizStatusEnum基类业务枚举的字段不能为集合类型，不然报错 3、BaseSubject增加扩展字段方便以后扩展，不能包含太多特定业务信息在其中|
| Data | 1、增加ActiveMode功能增强Entity功能 2、JPA逻辑删除功能不完善，未测试|
| Camel | 1、需要深度挖掘Camel潜力做更多的尝试 |




现有架构依赖于spring-boot 1.5.15发行版，spring-cloud Edgware.SR4版，后续需要升级spring-boot依赖到2.0版本时，Activiti模块需要重构。