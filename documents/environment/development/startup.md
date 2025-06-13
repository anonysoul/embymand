# 开发环境准备

## 启动依赖的服务
本项目在开发环境下依赖以下服务：postgres & emby-server，其都已在 docker-compose.yml 中配置好，启动命令如下：
```bash
docker-compose up -d
```

## 创建 bot
在 telegram 中找到 @BotFather，点击 `/newbot` 按钮，按照提示创建一个 bot，记下该 bot 的 token。

## 生成 emby api key
在浏览器中访问 http://localhost:8096 创建并登录用户后，访问 http://localhost:8096/web/index.html#!/apikeys ，点击 `Generate API Key`/`新建 API 密钥` 按钮，填写应用名称（名称随便填写），生成一个 API Key，记下该 API Key（32位的字符串）。

## 为 emby 安装统计插件
访问 [Playback Reporting](http://localhost:8096/web/index.html#!/plugins/install?name=Playback%20Reporting) 并安装此插件。

## 修改项目配置
开打项目配置文件：`embymand-bot/src/main/resources/application.yaml`，配置好其中的 `bot` `proxy` `emby` 的相关配置。

## 启动项目
运行 `embymand-bot` 项目，启动成功后，即可使用 bot 了。
