Exemplo de conexão com Redis Sentinel cache (Jedis)

# IntelliJ IDEA Plugin
 - [Spring Boot Helper](https://plugins.jetbrains.com/plugin/18622-spring-boot-helper)
 - [Redis Client plugin](https://plugins.jetbrains.com/plugin/19360-redis-client)

### Cachable object json

```shell
curl --request GET \
  --url http://localhost:8080/customers
```

### Cachable Collection json

```shell
curl --request GET \
  --url http://localhost:8080/customers
```

### Cache evict

```shell
curl --request POST \
  --url http://localhost:8080/customers \
  --header 'Content-Type: application/json' \
  --data '{ "name": "Rafaela Borges",
		"document": "85762588548" }'
```
