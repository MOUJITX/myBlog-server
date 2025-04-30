### update dependencies

```bash
mvn clean install
```

### local env

```bash
# application-local.properties
spring.datasource.url=${ DATABASE_URL }
spring.datasource.username=${ DATABASE_USERNAME }
spring.datasource.password=${ DATABASE_PASSWORD }
oss.qiniu.accessKey=${ OSS_ACCESS_KEY }
oss.qiniu.accessSecretKey=${ OSS_SECRET_KEY }
oss.qiniu.bucket=${ OSS_BUCKET }
oss.qiniu.imageUrl=${ OSS_URL }
oss.qiniu.folder=${ OSS_FOLDER }
```

### run local

```bash
export SPRING_PROFILES_ACTIVE=local
mvn spring-boot:run
```

### clean

```bash
mvn clean
```

### package

```bash
mvn clean package
```
