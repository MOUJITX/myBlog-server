### update dependencies

```bash
mvn clean install
```

### local env

```bash
# application-local.properties
DATABASE_URL=${DATABASE_URL}
DATABASE_USERNAME=${DATABASE_USERNAME}
DATABASE_PASSWORD=${DATABASE_PASSWORD}
OSS_ACCESS_KEY=${OSS_ACCESS_KEY}
OSS_SECRET_KEY=${OSS_SECRET_KEY}
OSS_BUCKET=${OSS_BUCKET}
OSS_URL=${OSS_URL}
OSS_FOLDER=${OSS_FOLDER}
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
