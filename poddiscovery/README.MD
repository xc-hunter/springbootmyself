## Actuator

## 部署说明
```shell
gradle clean assemble
```

1. 打包

```docker
docker build --platform linux/amd64 -t registry.cn-hangzhou.aliyuncs.com/kalman/xc-pod-disvovery:latest .
```

2. 打tag

```docker
docker tag registry.cn-hangzhou.aliyuncs.com/kalman/xc-pod-disvovery:latest registry.cn-hangzhou.aliyuncs.com/kalman/xc-pod-disvovery:111Release
```

3. 推送

```docker
docker push registry.cn-hangzhou.aliyuncs.com/kalman/xc-pod-disvovery:latest
docker push registry.cn-hangzhou.aliyuncs.com/kalman/xc-pod-disvovery:111Release
```

