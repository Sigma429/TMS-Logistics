# **1**、TMS-Logistics

## 1.1、概述

TMS-Logistics是一个基于Spring Cloud微服务架构体系的、具备完整物流业务闭环的生产级物流项目系统。

## 1.2、特性

- 取派件、分段运输转发等核心环节接入高可用任务调度，可定制调度策略，目前支持成本优先、距离优先俩种方式。
- 多类型（干线，支线，接驳线）、网状线路，支持多维度配置线路，有力缓解疫情等突发原因导致的运输任务积压。
- 分布式仓储(Redis缓存方式）配合集装箱模式灵活转运订单。
- MongoDB存储订单信息流，实时追踪订单状态。
- 司机、快递员等多端点位采集位置上报，运输轨迹一目了然。
- 公交车模式运输任务，预规划车次计划的基础上可插拔配置不同车型车辆与多样化司机作息安排。
- 小程序下单、多途径支付、取消，拒收等履约方式、预约取件时间等机制，一切从用户角度出发。
- 根据地图坐标范围、工作负载，行政机构范围等多特征点智能分配取件、派件作业任务，整体提升运作效率。
- 线上线下结合，多场景、多系统角色支撑的生产级系统业务编排。

## 1.3、产品说明

TMS-Logistics类似顺丰速运，是向C端用户提供快递服务的系统。竞品有：顺丰、中通、圆通、京东快递等。
项目产品主要有4端产品：

- 用户端：基于微信小程序开发，外部客户使用，可以寄件、查询物流信息等。
- 快递员端：基于安卓开发的手机APP，公司内部的快递员使用，可以接收取派件任务等。
- 司机端：基于安卓开发的手机APP，公司内部的司机使用，可以接收运输任务、上报位置信息等。
- 后台系统管理：基于vue开发，PC端使用，公司内部管理员用户使用，可以进行基础数据维护、订单管理、运单管理等。

# 2、物流行业系统

从广度上来说，物流系统可以理解为由多个子系统组成，这里我们以一般综合型物流系统举例，在整体框架上可以分为仓储系统WMS、运配系统TMS、单据系统OMS和计费系统BMS。

这四大系统本质上解决了物流行业的四大核心问题：怎么存放、怎么运送、怎么跟进、怎么结算。

TMS-Logistics，是TMS运配系统，本质上解决的是怎样运送的问题。

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666843467766-309e2f8f-1fc0-4f05-ba11-3142f542929c.png#averageHue=%23e1deb0&clientId=u5ccd6243-bb68-4&from=paste&height=1303&id=uf6361701&name=image.png&originHeight=1955&originWidth=2700&originalType=binary&ratio=1&rotation=0&showTitle=false&size=2507860&status=done&style=shadow&taskId=u55fe548e-896b-41e8-9420-51863c19e41&title=&width=1800)

# 3、系统架构和技术架构

## 3.1、系统架构

![](https://cdn.nlark.com/yuque/0/2022/jpeg/27683667/1665997407045-5b9ee5fe-d30b-4cec-a5a9-599f5a8ee6bd.jpeg)

## 3.2、技术架构

下图展现了TMS-Logistics使用的主要的技术：

![](https://cdn.nlark.com/yuque/0/2022/jpeg/27683667/1665997873162-a4c9ea61-f71e-4111-862e-efa01fd35e7b.jpeg)

# 4、功能演示

## 4.1、需求文档

下面将演示四端的主要功能，更多的功能具体查看各端的需求文档

| 用户端  | [https://share.lanhuapp.com/#/invite?sid=qx01hbI7](https://share.lanhuapp.com/#/invite?sid=qx01hbI7)      密码: UxGE |
|------|--------------------------------------------------------------------------------------------------------------------|
| 快递员端 | [https://share.lanhuapp.com/#/invite?sid=qxe42Dya](https://share.lanhuapp.com/#/invite?sid=qxe42Dya)     密码: Nomz  |
| 司机端  | [https://share.lanhuapp.com/#/invite?sid=qX0NEmro](https://share.lanhuapp.com/#/invite?sid=qX0NEmro)   密码: yrzZ    |
| 管理端  | [https://share.lanhuapp.com/#/invite?sid=qX0axVem](https://share.lanhuapp.com/#/invite?sid=qX0axVem)    密码: fh3i   |

## 4.2、功能架构

![](https://cdn.nlark.com/yuque/0/2022/jpeg/27683667/1672389615739-d11e9b27-5241-49e4-bd52-2374f32a38be.jpeg)

## 4.3、业务功能流程

![](https://cdn.nlark.com/yuque/0/2022/jpeg/27683667/1667813584952-24320691-7837-4c72-97c1-2d99b7da71fe.jpeg)

:::info
流程说明：

- 用户在**【用户端】**下单后，生成订单
- 系统会根据订单生成**【取件任务】**，快递员上门取件后成功后生成**【运单】**
- 用户对订单进行支付，会产生**【交易单】**
- 快件开始运输，会经历起始营业部、分拣中心、转运中心、分拣中心、终点营业部之间的转运运输，在此期间会有多个**【运输任务】**
- 到达终点网点后，系统会生成**【派件任务】**，快递员进行派件作业
- 最后，用户将进行签收或拒收操作
  :::

## 4.4、用户端

功能演示操作视频列表：

| 下单操作 | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/0c8fc60a-2cf5-4140-9592-124cb3352fd0.mp4) |
|------|----------------------------------------------------------------------------------------------------|
| 取消订单 | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/efd2553b-69ab-4ec1-ad71-f0fd27c84165.mp4) |
| 地址簿  | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/1fcbdd1e-70bc-461c-9b0e-60ec75edbabb.mp4) |

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666145111077-c453ef4e-3165-4085-8b70-f38c82be3d1a.png#averageHue=%23fffdfc&clientId=u20e031de-c504-4&errorMessage=unknown%20error&from=paste&height=554&id=u47bf6dc7&name=image.png&originHeight=914&originWidth=422&originalType=binary&ratio=1&rotation=0&showTitle=true&size=65584&status=error&style=shadow&taskId=u1b0ff756-a55e-439f-b7f0-c33bbaee655&title=%E7%94%A8%E6%88%B7%E4%B8%8B%E5%8D%95&width=255.75756097518683 "用户下单")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666145242663-e0a704ba-dcfa-4f99-a25f-1b7a3ec5f119.png#averageHue=%23bcbbbb&clientId=u20e031de-c504-4&errorMessage=unknown%20error&from=paste&height=554&id=u8fda641e&name=image.png&originHeight=914&originWidth=422&originalType=binary&ratio=1&rotation=0&showTitle=true&size=73294&status=error&style=shadow&taskId=u46922d9f-554f-42dd-a6ff-3207fab138a&title=%E4%BC%B0%E7%AE%97%E8%BF%90%E8%B4%B9&width=255.75756097518683 "估算运费")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666145268551-72b01391-9f65-4a87-90c5-23cb3ffecaa6.png#averageHue=%23f4f4f5&clientId=u20e031de-c504-4&errorMessage=unknown%20error&from=paste&height=554&id=u6c53f021&name=image.png&originHeight=914&originWidth=422&originalType=binary&ratio=1&rotation=0&showTitle=true&size=72828&status=error&style=shadow&taskId=uf6899e53-240b-405b-8861-5841a9d082e&title=%E4%B8%8B%E5%8D%95%E6%88%90%E5%8A%9F&width=255.75756097518683 "下单成功")

## 4.5、快递员端

功能演示操作视频列表：

| 派件操作流程   | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/7bb3000d-69b8-473f-9d6b-d391b8c28a9f.mp4)                                  |
|----------|-------------------------------------------------------------------------------------------------------------------------------------|
| 取件操作流程   | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/7767cda8-8e83-4c5c-a976-634815ec0a10.mp4)                                  |
| 全部取派操作流程 | [点击查看](https://outin-ffd84744973f11eb806300163e038793.oss-cn-beijing.aliyuncs.com/sv/605f258-1844feb861d/605f258-1844feb861d.mp4)   |
| 搜索操作流程   | [点击查看](https://outin-ffd84744973f11eb806300163e038793.oss-cn-beijing.aliyuncs.com/sv/60a0b1bf-1845000a4d0/60a0b1bf-1845000a4d0.mp4) |
| 消息操作流程   | [点击查看](https://outin-ffd84744973f11eb806300163e038793.oss-cn-beijing.aliyuncs.com/sv/38c12638-18450c563db/38c12638-18450c563db.mp4) |

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666147847509-b0623617-3ea2-4293-b9a0-7ba0dc51e076.png#averageHue=%23f6f4f4&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=552&id=uc1e98e30&name=image.png&originHeight=1624&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=179302&status=error&style=shadow&taskId=u7bfb3afe-8511-47ff-969f-9af839c481e&title=%E5%8F%96%E4%BB%B6%E4%BB%BB%E5%8A%A1%E5%88%97%E8%A1%A8&width=255 "取件任务列表")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666147783779-ced836d1-62c2-4fac-aa26-9cf6e5180138.png#averageHue=%23f7f3f3&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=773&id=uab55be5a&name=image.png&originHeight=2274&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=171292&status=error&style=shadow&taskId=uc986bcae-248b-461d-9573-315d5d79d5c&title=%E5%8E%BB%E5%8F%96%E4%BB%B6&width=255 "去取件")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666148205970-20236142-d3aa-455a-8b18-0b5438d6b4e5.png#averageHue=%23eae9e9&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=552&id=u72db6544&name=image.png&originHeight=1624&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=132740&status=error&style=shadow&taskId=u9f79792c-f3a3-45c0-ba55-6a34aeef709&title=%E6%89%AB%E6%8F%8F%E6%94%AF%E4%BB%98&width=255 "扫描支付")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666148251950-460e944c-26a7-47d2-819f-6bb03022d98b.png#averageHue=%23f3f3f3&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=552&id=u5644a74f&name=image.png&originHeight=1624&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=51074&status=error&style=shadow&taskId=u5d19cb93-7812-4d20-b6fe-267addd175e&title=%E5%8F%96%E4%BB%B6%E6%88%90%E5%8A%9F&width=255 "取件成功")

## 4.6、司机端

[点击查看演示视频](https://outin-ffd84744973f11eb806300163e038793.oss-cn-beijing.aliyuncs.com/sv/4ffdd092-184501a12ff/4ffdd092-184501a12ff.mp4)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666148843502-dbfb5e33-0aec-43be-992a-1005cf84d63d.png#averageHue=%23f6f4f3&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=552&id=u881fa848&name=image.png&originHeight=1624&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=166160&status=error&style=shadow&taskId=ue9c50999-5066-4605-ae28-ffcfe21a3d0&title=%E5%8F%B8%E6%9C%BA%E8%BF%90%E8%BE%93%E4%BB%BB%E5%8A%A1&width=255 "司机运输任务")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666148927871-7a8bfa02-3a6f-4c9a-86f8-ae2355750ee1.png#averageHue=%23f7f6f5&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=579&id=u27edc02a&name=image.png&originHeight=1702&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=135650&status=error&style=shadow&taskId=ud2af17ef-d255-45ae-bce5-ff942ef9026&title=%E4%BB%BB%E5%8A%A1%E8%AF%A6%E6%83%85&width=255 "任务详情")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666149092451-d7eb65a3-f0dc-4a9e-b1b3-147a265d126f.png#averageHue=%23f0e9e7&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=560&id=u9bc1359c&name=image.png&originHeight=1624&originWidth=740&originalType=url&ratio=1&rotation=0&showTitle=true&size=235207&status=error&style=shadow&taskId=ub2c27ca7-b533-492d-b89b-be154210f4e&title=%E6%8F%90%E8%B4%A7%E6%88%90%E5%8A%9F%EF%BC%88%E8%BF%90%E8%BE%93%E5%BC%80%E5%A7%8B%EF%BC%89&width=255 "提货成功（运输开始）")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666149148556-d9866b4c-746e-470d-a536-ef4e8298a9d8.png#averageHue=%23f7f1f1&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=552&id=u7b37a577&name=image.png&originHeight=1624&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=73287&status=error&style=shadow&taskId=u7273f8ff-3deb-42bb-8c28-a50be982a13&title=%E5%88%B0%E8%BE%BE%E7%9B%AE%E7%9A%84%E5%9C%B0&width=255 "到达目的地")
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27683667/1666149017437-ff6a070b-8562-4daa-8876-67e72b0f8554.png#averageHue=%23f8f8f7&clientId=u75175024-45c1-4&errorMessage=unknown%20error&from=paste&height=552&id=uea82a8a5&name=image.png&originHeight=1624&originWidth=750&originalType=url&ratio=1&rotation=0&showTitle=true&size=76515&status=error&style=shadow&taskId=u230efa33-9f41-4d76-895d-a914367c161&title=%E5%A6%82%E6%9E%9C%E6%9C%89%E5%BC%82%E5%B8%B8%E5%8F%AF%E4%BB%A5%E8%BF%9B%E8%A1%8C%E4%B8%8A%E6%8A%A5&width=255 "如果有异常可以进行上报")

## 4.7、后台管理系统

功能演示操作视频列表：

| 建立机构   | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/44443260-b57f-41f8-a1f2-22c44b1c16c1.mp4) |
|--------|----------------------------------------------------------------------------------------------------|
| 新建员工   | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ab24e727-9c1f-458c-a8c3-b2d3cbfce46d.mp4) |
| 绘制作业范围 | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/9a1e3679-38eb-4585-b41b-7d9277dc1da0.mp4) |
| 新建线路   | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/cd62d82c-7910-4df0-835b-08854ecb0e79.mp4) |
| 启用车辆   | [点击查看](https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/5d8adc94-201f-43ee-8ef1-7906e5d8f272.mp4) |

![QQ图片20230114151916.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673680992033-4e0d2021-7318-4667-8243-b980711e2cdf.png#averageHue=%23fdf8f4&clientId=u0b008fb9-82fc-4&from=paste&height=1479&id=u593de249&name=QQ%E5%9B%BE%E7%89%8720230114151916.png&originHeight=1972&originWidth=1899&originalType=binary&ratio=1&rotation=0&showTitle=true&size=348248&status=done&style=shadow&taskId=u9e60a9a6-8cd7-41d5-b1fa-6a3aa140b15&title=%E5%B7%A5%E4%BD%9C%E5%8F%B0&width=1424 "工作台")![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681344577-4f59ebcc-4b21-4076-ba64-ef1bee8bb22c.png#averageHue=%23fdfcfb&clientId=u5467c59d-305d-4&from=paste&height=619&id=u98bf5c5a&name=image.png&originHeight=825&originWidth=1898&originalType=binary&ratio=1&rotation=0&showTitle=true&size=100076&status=done&style=shadow&taskId=ue1626a0e-ae64-4e02-89ff-f1e6071ec26&title=%E8%BD%A6%E8%BE%86%E7%AE%A1%E7%90%86&width=1424 "车辆管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681441709-cd4f06cb-b65f-45c0-a9e4-da18b0da2a0d.png#averageHue=%23fdfcfb&clientId=u5467c59d-305d-4&from=paste&height=911&id=uacf9df64&name=image.png&originHeight=1214&originWidth=1890&originalType=binary&ratio=1&rotation=0&showTitle=true&size=181877&status=done&style=shadow&taskId=u4bcde4ca-2ef2-49f2-8c66-2f44ee7899f&title=%E8%AE%A2%E5%8D%95%E7%AE%A1%E7%90%86&width=1418 "订单管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681517364-16df33a4-9a48-405d-be13-2c9214ee8028.png#averageHue=%23fdfcfb&clientId=u5467c59d-305d-4&from=paste&height=573&id=ue03a6758&name=image.png&originHeight=764&originWidth=1879&originalType=binary&ratio=1&rotation=0&showTitle=true&size=99026&status=done&style=shadow&taskId=ue56fece5-ed2e-43a5-b1b4-4b203d1fa4a&title=%E5%8F%B8%E6%9C%BA%E7%AE%A1%E7%90%86&width=1409 "司机管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681584167-010c4de4-a89a-4db1-8913-c77b7bfb302c.png#averageHue=%23fdfaf9&clientId=u5467c59d-305d-4&from=paste&height=639&id=u51c29d53&name=image.png&originHeight=852&originWidth=1878&originalType=binary&ratio=1&rotation=0&showTitle=true&size=114462&status=done&style=shadow&taskId=u2b9845f8-ca99-4f52-af48-4e542ceb079&title=%E6%8E%92%E7%8F%AD%E7%AE%A1%E7%90%86&width=1409 "排班管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681630034-08d98831-7274-4cab-a0e8-826ca34e62bf.png#averageHue=%23fdfcfb&clientId=u5467c59d-305d-4&from=paste&height=662&id=u6392d187&name=image.png&originHeight=883&originWidth=1880&originalType=binary&ratio=1&rotation=0&showTitle=true&size=144461&status=done&style=shadow&taskId=u94d03047-1ff2-4f90-b6c3-6aa48155b22&title=%E8%BF%90%E8%BE%93%E4%BB%BB%E5%8A%A1%E7%AE%A1%E7%90%86&width=1410 "运输任务管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681710302-70138a00-4dc0-44ba-90d4-fcb2d7226ed4.png#averageHue=%23fdfbfb&clientId=u5467c59d-305d-4&from=paste&height=691&id=uf3e2975c&name=image.png&originHeight=922&originWidth=1891&originalType=binary&ratio=1&rotation=0&showTitle=true&size=137537&status=done&style=shadow&taskId=ude666272-5dc4-4ad3-89c1-77249b710df&title=%E7%BA%BF%E8%B7%AF%E7%AE%A1%E7%90%86&width=1418 "线路管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681799739-d8cccc7a-2d0f-4d99-9ed5-4a2e5eadeffc.png#averageHue=%23ecb68e&clientId=u5467c59d-305d-4&from=paste&height=755&id=u1adb8b6b&name=image.png&originHeight=1007&originWidth=1876&originalType=binary&ratio=1&rotation=0&showTitle=true&size=135586&status=done&style=shadow&taskId=u9067930d-fe64-4eaa-bb81-69fdb22bd30&title=%E8%BF%90%E5%8D%95%E7%AE%A1%E7%90%86&width=1407 "运单管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681882850-4e26eb0c-532c-499e-8d70-56b1b05c4fe4.png#averageHue=%23e9c391&clientId=u5467c59d-305d-4&from=paste&height=610&id=ub3b6b4e8&name=image.png&originHeight=813&originWidth=1883&originalType=binary&ratio=1&rotation=0&showTitle=true&size=98741&status=done&style=shadow&taskId=ub02572a2-fb9c-4aac-84b6-fd4987c2e52&title=%E6%9C%BA%E6%9E%84%E7%AE%A1%E7%90%86&width=1412 "机构管理")
![image.png](https://cdn.nlark.com/yuque/0/2023/png/28217986/1673681922716-5a9002a3-4cf4-4048-b9ae-85d76445bf68.png#averageHue=%23ea9972&clientId=u5467c59d-305d-4&from=paste&height=416&id=u19319be1&name=image.png&originHeight=554&originWidth=1882&originalType=binary&ratio=1&rotation=0&showTitle=true&size=60460&status=done&style=shadow&taskId=u09f7e073-d4ae-4f1a-8e7f-295f355a012&title=%E8%BF%90%E8%B4%B9%E7%AE%A1%E7%90%86&width=1412 "运费管理")

# 5、服务列表：

| 服务名称          | 版本        | 备注                                                        |
|---------------|-----------|-----------------------------------------------------------|
| MySQL         | 8.0.29    | 业务数据存储                                                    |
| Redis         | 7.0.4     | 用作缓存以及分布式锁                                                |
| RabbitMQ      | 3.9.17    | 需要集成官方的延迟队列插件                                             |
| Nacos         | v2.1.0    | 配置中心与注册中心解决方案                                             |
| Neo4j         | 4.4.5     | 基于图数据库计算运输路线                                              |
| xxl-job       | 2.3.0     | 分布式定时任务框架                                                 |
| MongoDB       | 4.4       | 存储轨迹、作业范围等数据                                              |
| Seata         | 1.5.2     | 分布式事务解决方案                                                 |
| Elasticsearch | 7.17.5    | 分布式全文索引解决方案                                               |
| Skywalking    | 9.1.0     | 链路追踪解决方案                                                  |
| Graylog       | 4.3       | 分布式日志解决方案                                                 |
| Leaf          | 1.0.1     | 美团点评分布式ID生成系统                                             |
| EagleMap      | 1.0       | 黑马程序员自研开源项目 https://eaglemap.itheima.net/                 |
| 权限管家          | 1.0.7     | 黑马程序员自研开源项目 https://gitee.com/itcastopen/itcast-authority |
| nexus         | 2.15.1    | maven私服                                                   |
| gogs          | 0.12      | git代码管理                                                   |
| Jenkins       | lts-jdk11 | 持续集成                                                      |

# 6、文档

[🎏简介 (itheima.net)](https://sl-express.itheima.net/#/zh-cn/)

[《神领物流-讲义-v1.5》](https://www.yuque.com/zhangzhijun-91vgw/xze2gr?#)密码：kzcd

# 7、记录

## 后端

- Day1
    1. 项目框架整体搭建
- Day2
    1. 项目服务整体搭建
- Day3
    1. 项目依赖和结构配置
- Day4
    1. 虚拟机重新配置
    2. 熟悉使用权限管家
    3. gateway路由转发和鉴权功能理解
- Day5
    1. 双token三验证模式，关于token的无状态性，有安全隐患（异地登录），一端刷新refresh_token,另一端第三次校验token时token无效，需要重新登录
    2. 支付模块初步搭建
    3. domain模块搭建
- Day6
    1. api模块搭建
- Day7
    1. @EnumValue与通用枚举类结合,可减少魔法值的使用
    2. 支付模块搭建完成，未测试
    3. 网关路由转发和鉴权流程复习
- Day8
    1. 双token三验证复习，用户端登录功能完成，未测试
- Day9
    1. 分布式锁的两种实现方案及笔记复习
    2. 交易模块初步搭建
    3. 快递员模块完成
- Day10
    1. 微服务工程结构规范化
    2. 数据校验和自定义异常
    3. 配置文件阅读
    4. 支付渠道代码阅读完毕
    5. 开始阅读扫码支付代码
- Day11
    1. 幂等性
    2. 支付方式使用工厂模式(反射+注解)
    3. 理解xxl-job原理及使用
    4. 查询支付状态和退款状态接口，通过xxl-job任务调度定时调用，通过mq将不符合条件的数据通知给其他系统
    5. trade模块已完成
- Day12
    1. 运费模块已完成
    2. 运费模板的选择使用责任链模式（运费的计算通过模板进行）
- Day13
    1. 路线规划模块初步搭建
- Day14
    1. mq模块error三种情况：发送到交换机，但队列未绑定、发送到mq服务器，但交换机不存在、未发送到mq服务器
    2. 发送消息的三种error情况，用日志、数据持久化(后续通过xxl-job重新发送消息)和retry重试机制，消费消息error用ErrorMessageConfig类处理
    3. TMS-logistics-mq模块已完成，TMS-logistics-base模块初步搭建
    4. transport模块通过mq和IService封装实现机构同步，实现OrganService接口完成机构管理
- Day15
    1. 完成路线管理和路线成本接口
    2. 接入EagleMap API获取时间、距离
    3. web-courier模块和work-service模块初步搭建
- Day16
    1. 智能调度与整体核心业务理解
    2. 订单转运单(取件成功消息消费、美团leaf生成运单号、业务实现)
    3. 理解美团leaf，号段模式，双buffer优化，IdService
    4. 完善运单服务,使用redis的list结构合并运单
- Day17
    1. base模块代码理解, 线路 -> 车次 -> 车辆 -> 司机/货物 -> 排班 -> 计划服务
    2. base模块代码阅读完毕
- Day18
    1. 过滤器属于servlet级别，拦截器属于spring级别，执行顺序：过滤器 -> 拦截器
    2. 白名单登录接口生成token，随后header的AUTHORIZATION设置为token，网关模块的过滤器放行白名单，检验token，鉴权后，向下游微服务的header中添加userInfo和token
       ，通过路由转发的过滤器将AddRequestHeader=X-Request-From
       设置为sl-express-gateway，下游微服务的user拦截器检验userInfo和X-Request-From后，将userInfo存储到ThreadLocal中，同时token拦截器将AuthTemplate实例存放到AuthTemplateThreadLocal
    3. common模块代码阅读完毕
    4. 完成DispatchJob类任务调度将合并运单生成运输任务,TransportTaskMQListener类创建运输任务,生成司机作业单,理解司机入库、出库和回车登记逻辑
    5. driver模块代码阅读完毕,**理解车辆计划业务流程**
- Day19
    1. MongoDB实现作业范围的存储，理解service-scope模块代码
- Day20
    1. 理解取派件逻辑
    2. courier模块和dispatch模块完毕
    3. 物流信息代码理解，解决数据量大（mongodb）和并发高（多级缓存）问题。记录物流信息（mq）（数据实时性要求不高，并发量大，流量削峰）

# 8、进度

| common              | OK |
|---------------------|----|
| gateway             | OK |
| mq                  | OK |
| ms-base             | OK |
| ms-carriage         | OK |
| ms-courier          | OK |
| ms-dispatch-service | OK |
| ms-driver           | OK |
| ms-oms              |    |
| ms-search           |    |
| ms-service-scope    | OK |
| ms-sms              |    |
| ms-track            |    |
| ms-trade            | OK |
| ms-transport        | OK |
| ms-transport-info   |    |
| ms-user             | OK |
| ms-web-courier      |    |
| ms-web-customer     |    |
| ms-web-driver       |    |
| ms-web-manager      |    |
| ms-work             |    |
| parent              | OK |
| pay                 | OK |
| xxl-job             | OK |

