# Leafage Gateway

<p align="center">
 <img src="https://img.shields.io/badge/Spring%20Cloud-2023.0.0-green.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-3.2.0-green.svg" alt="Downloads">
 <img src="https://sonarcloud.io/api/project_badges/measure?project=little3201_leafage-gateway&metric=alert_status" alt="quality"/>
 <img src="https://sonarcloud.io/api/project_badges/measure?project=little3201_leafage-gateway&metric=coverage" alt="coverage" />
</p>

leafage-gateway 是leafage的网关服务、所有后台服务接口都只能通过网关进行访问，它包含两部分：

查看当前依赖最新版本[leafage-starter-parent](https://github.com/little3201/leafage-starter-parent)

了解更过关于 [leafage-basic](https://github.com/little3201/leafage-basic) 更多信息；

- 路由网关： 基于spring cloud gateway，进行api请求的转发，目前会路由到leafage-basic模块的两个后台服务，assets和hypervisor;

#### 目标功能

- [x] api路由

#### 核心依赖

|               依赖               |           说明            |
|:-------------------------------:|:-------------------------:|
|      Spring Cloud Gateway       |          基础框架          |
|         Spring Security         |          安全认证          |
