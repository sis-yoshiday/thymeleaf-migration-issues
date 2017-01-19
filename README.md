- select thymeleaf version

```groovy
// thymeleaf2.x
ext['thymeleaf.version'] = '2.1.5.RELEASE'
// thymeleaf3.x
//ext['thymeleaf.version'] = '3.0.3.RELEASE'
```

- do test

```bash
./gradlew clean test
```

- see results/*.txt