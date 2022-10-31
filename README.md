# Desafio Itaú (Todo List Api)
Desafio Tecnológico Itau
<div align="center">

### Projeto desafio para empresa Itaú utilizando tecnologia Spring Boot 2.x, Docker e Ferramentas de Observabilidade

</div>

<p align="center">
<img src="https://img.shields.io/badge/Jaeger%20-%23F7DF1E.svg?&style=for-the-badge&color=E34F26" />&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Phometheus%20-%23F7DF1E.svg?&style=for-the-badge&color=5BA8EE" />&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Grafana%20-%23F7DF1E.svg?&style=for-the-badge&color=F7DF1E" />&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Mysql%20-%23F7DF1E.svg?&style=for-the-badge&color=7044A3" />&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Java%20-%23F7DF1E.svg?&style=for-the-badge&color=F7DF1E" />&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Git HUB%20-%23F7DF1E.svg?&style=for-the-badge&color=000" />&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Spring%20-%23F7DF1E.svg?&style=for-the-badge&color=008000" />&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/Docker %20-%23F7DF1E.svg?&style=for-the-badge&color=5BA8EE" />&nbsp;&nbsp;
</p>

<p align="center">
	<a href="https://github.com/JesseSBezerra"><img src="https://img.icons8.com/bubbles/50/000000/github.png" alt="GitHub"/></a>
	<a href="https://www.linkedin.com/in/jesse-bezerra-239187a0/"><img src="https://img.icons8.com/bubbles/50/000000/linkedin.png" alt="LinkedIn"/></a>
</p>

![Logo do Markdown](https://github.com/JesseSBezerra/itau--todo-list-api-/blob/master/TodoListApi.gif?raw=true)
	
<h3 align="center"> Usuários de Acesso: </h3>
<p> Usuario: admin | senha: 12345 | super </p>
<p> Usuario: registration | senha: 12345 | normal </p>
<p> Usuario: consultant | senha: 12345 | normal </p>
<p> Usuario: seller | senha: 12345 | normal </p>
<p> Usuario: user | senha: 12345 | normal </p>

<h3 align="center"> Endpoints: </h3>
<p> Autenticação: localhost:8765/todo-list-api/api/user/auth | method: POST </p>
<p> Criação de Task: localhost:8765/todo-list-api/api/task | method: POST </p>
<p> Todas as Tasks do Usuário (Ordena por Status) : localhost:8765/todo-list-api/api/task | method: GET </p>
<p> Todas Tasks do Usuário (Filtra por Status) : localhost:8765/todo-list-api/api/task/status/?status=COMPLETED | method: GET </p>
<p> Update: localhost:8765/todo-list-api/api/task/{id} | method: PUT </p>
<p> Delete: localhost:8765/todo-list-api/api/task/{id} | method: DELETE </p>
<p> Actuator : http://localhost:8000/actuator/health | method: GET </p>

<h3> Links das Ferramentas: </h3>
<p> host do grafana: http://localhost:3000/d/JZ177yNVz/task-manager?orgId=1&refresh=10s </p>
<p> host do prometheus: http://localhost:9090/graph?g0.expr=http_server_requests_seconds_count&g0.tab=1&g0.stacked=0&g0.show_exemplars=0&g0.range_input=1h </p>
<p> host do jaeger: http://localhost:16686/search </p>  

#
#### Esse é um sistema desenvolvido utilizando os conceitos de microserviços abrangindo algumas das boas praticas de mercado.
#### Tecnologias Utilizadas:
<li> Spring Boot </li>
<li> Spring Security </li>
<li> Spring Cloud </li>
<li> Spring Eureka </li>
<li> Spring Gateway </li>
<li> Java 11 </li>
<li> JUnit5 </li>
<li> Mockito </li>
<li> JavaScript </li>
<li> Mysql </li>
<li> Grafana </li>
<li> Prometheus </li>
<li> Jaeger </li>
</br>
  <li> Como solicitado a base já vai com um cadastro de usuários pré definido.  
</br>
<li> Endpoints: </li>
O arquivo contendo todos as chamadas realizadas e uma documentação básica poderá ser visualizado no postman e se encontra em: 
https://github.com/JesseSBezerra/itau--todo-list-api-/blob/master/Projeto%20Ita%C3%BA%20Todo%20List%20Api.postman_collection.json
<p> OBS: para funcionar é só importar o arquivo acima no postman </p>

Obs: Após o start do projeto no docker compose demora uns 2 ou 3 minutos para conseguir logar na aplicação
Obs: Cadastrei algumas tasks na base afim de facilitar os testes.<p> O viculo do usuário ficou implicito na aplicação, buscando o usuário por meio da sessão bem como os endpoins de consulta.</p> <p>A aplicação tem uma cobertura de 100% em todas as classes e a nível de metodo e linha, possui uma cobertura de 72%.
Para mudar o usuário logado no postman, basta clicar na coleção e ir em "Pré-request Script", deixei o usuário e senha fixo nesse arquivo.</p>

```shell
pm.sendRequest({
    url: 'localhost:8000/api/user' + '/auth',
    method: 'POST',
    header: {
        'content-type': 'application/json'
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({ 
            username: 'admin', 
            password: '12345' })
    }
}, function (err, res) {
    pm.environment.set("auth", "Bearer " + res.json().token);
});
```

<p> Por ter uma maior familiaridade optei por utilizar uma base relacional, bem como se em algum momento fossemos pensar em performance, caberia alguns indices nessa tabela. Exemplo do campo TASK_STATUS.</p>
<p> No arquivo docker-compose deixei a aplicação rodando em 3 nós diferentes, sendo balanceados pelo eureka e acessados pelo api gateway </>

#

Geração do token:

```shell
curl --location --request POST 'localhost:8765/todo-list-api/api/user/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"admin"
    ,"password":"12345"
}'
```
Listando todas as Tasks:

```shell
curl --location --request GET 'localhost:8765/todo-list-api/api/task' \
--header 'Authorization;'
```
Listando Tasks por status

```shell
curl --location --request GET 'localhost:8765/todo-list-api/api/task/status/?status=COMPLETED' \
--header 'Authorization;'
```

Criando task

```shell
curl --location --request POST 'localhost:8765/todo-list-api/api/task' \
--header 'Authorization;' \
--header 'Content-Type: application/json' \
--data-raw '{
     "description": "task de testes"
    ,"summary": "teste"
    ,"status": "PENDING"
    ,"updatedAt":null
    ,"createdAt":null
}'
```

Atualizando a task

```shell
curl --location --request PUT 'localhost:8765/todo-list-api/api/task/19' \
--header 'Authorization;' \
--header 'Content-Type: application/json' \
--data-raw '{
      "user": {
            "id": 3,
            "userName": "consultant"
        },
        "description": "Atividade de consultoria 08",
        "summary": "teste",
        "updatedAt": null,
        "createdAt": "2022-10-30T19:01:35",
        "status": "COMPLETED"
}'
```

Removendo a Task
```shell
curl --location --request DELETE 'localhost:8765/todo-list-api/api/task/19' \
--header 'Authorization;'
```
O token será gerado nesse padrão, sendo necessário concatenar com padrão: Bearer + valor re retorno do token
```shell
{
    "login": "admin",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NzE4OTQ3Mn0.bm6kWx8O4DTPXZwq_ezBYDDQ935RnSoiDKUZzayPUodySFscaeUYN1WFmwooFMTsRrn3Le4wFJ8jccfRvvU5YQ"
}
```
