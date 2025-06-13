# 项目描述
本项目采用[领域驱动](https://zh.wikipedia.org/wiki/%E9%A0%98%E5%9F%9F%E9%A9%85%E5%8B%95%E8%A8%AD%E8%A8%88)设计

## 各模块的定义
- `embymand-bot`: telegram bot 实例，用户与 embymand 交互的入口。
- `embymand-service`: 应用层，提供业务逻辑。
- `embymand-core`: 领域层，每个模块都对应一个领域。此部分不包含任何业务逻辑，只包含领域对象。
- `embymand-infra`: 基础设施层，提供基础服务。

## 数据库设计
- user: 用户表
- integral: 积分表，当前积分状态
- integral_history: 积分变更历史记录
