# Distributed Database

APS para a Matéria de Sistemas Distribuídos

## Instruções de como executar:
Inicie os servidores abaixo de preferência mas não necessariamente na ordem:
##### br.com.tracun.app
- Server1.java
- Server2.java
- Server3.java
- DBController.java
##### br.com.tracun.userInterface
- MenuInterface.java

## Definição dos dados:

Cada dado será salvo como objeto.
São eles:

 REQUEST - Contém a requisição do cliente para o servidor, tendo os seguintes atributos:
- serialVersionUID - Long
- key - String
- Operation - String
- Obj - Object
- objList - ArrayList
- toServerPort - int
- isBackup - boolean
- folder - String
MÉTODOS - 
- public Request(operacao,  obj) – Operação a ser efetuada(ver operações aceitas) e objeto a ser processado.

RESPONSE - Contém a resposta do servidor após o processamento da Request, tendo os seguintes atributos:
- String - status
- Object - obj
- serialVersionUID – Long
MÉTODOS - 
- public Response(status) – Status da operação solicitada
- public Response(status, obj) – Status da operação solicitado e objeto contendo a consulta de todos dados solicitados.

USER - Objeto será salvo no server 1 e espelhado no 2 e terá os seguintes atributos: 
- key - String
- User - String
- password - String
- creationDate - Date
- addrCliente- InetAddress
- serialVersionUID - Long

NOTE - Objeto será salvo no server 2 e espelhado no 3 e terá os seguintes atributos:
- key - Int
- data - String
- creationDate - Date
- addrCliente- InetAddress
- serialVersionUID – Long

CONTACT - Objeto será salvo no server 3 e espelhado no 1 e terá os seguintes atributos:
- key - Int
- name – String
- phone – Int
- mail(opcional) - String
- creationDate - Date
- addrCliente- InetAddress
- serialVersionUID – Long

O objeto USER terá sua Key caracterizado pela conteúdo do campo user mais o final ".SER", sendo que o mesmo não poderá ser repetido.
O objeto NOTE terá sua Key caracterizado pela conteúdo do campo key mais o final ".SER", sendo que o mesmo não poderá ser repetido.
O objeto CONTACT terá sua Key caracterizado pela conteúdo do campo key mais o final ".SER", sendo que o mesmo não poderá ser repetido.

## Operações possíveis:
- CREATE – Cria um novo objeto 
- UPDATE – Atualiza um objeto já existente
- DELETE – Deleta um objeto existente
- CONSULT – Consulta um objeto específico
- CONSULTALL – Consulta todos objetos de um determinado tipo (Note, User ou Contact)

## Códigos de erro: 
- "200 - OK" - A operação foi realizada com sucesso
- "400 - BAD REQUEST" - O cliente não fez uma solicitação válida
- "404 - NOT FOUND" - O servidor não pode encontrar o arquivo especificado
- "408 - EMPTY DATA OR KEY" - Não há dados suficientes para efetivar a solicitação
- "409 - KEY CONFLICT" - Já existe um arquivo com o mesmo nome
- "500 - INTERNAL SERVER ERROR" - Ocorreu um erro interno no servidor
- "503 - SERVICE UNAVAILABLE" - Os servidores não estão disponíveis
