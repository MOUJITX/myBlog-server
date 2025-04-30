### update dependencies

```bash
mvn clean install
```

### local env

```bash
# application-local.properties
DATABASE_URL=${ secrets.DATABASE_URL }
DATABASE_USERNAME=${ secrets.DATABASE_USERNAME }
DATABASE_PASSWORD=${ secrets.DATABASE_PASSWORD }
OSS_ACCESS_KEY=${ secrets.OSS_ACCESS_KEY }
OSS_SECRET_KEY=${ secrets.OSS_SECRET_KEY }
OSS_BUCKET=${ secrets.OSS_BUCKET }
OSS_URL=${ secrets.OSS_URL }
OSS_FOLDER=${secrets.OSS_FOLDER }
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
