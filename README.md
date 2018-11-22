<!DOCTYPE html><html><head><meta charset="utf-8"><title>README.md</title><style></style></head><body id="preview">
<h1><a id="Distributed_Database_0"></a>Distributed Database</h1>
<p>APS para a Matéria de Sistemas Distribuídos</p>
<h2><a id="Instrues_de_como_executar_4"></a>Instruções de como executar:</h2>
<p>Inicie os servidores abaixo de preferência mas não necessariamente na ordem:</p>
<h5><a id="brcomtracunapp_6"></a>br.com.tracun.app</h5>
<ul>
<li>Server1.java</li>
<li>Server2.java</li>
<li>Server3.java</li>
<li>DBController.java</li>
</ul>
<h5><a id="brcomtracunuserInterface_11"></a>br.com.tracun.userInterface</h5>
<ul>
<li>MenuInterface.java</li>
</ul>
<h2><a id="Definio_dos_dados_14"></a>Definição dos dados:</h2>
<p>Cada dado será salvo como objeto.<br>
São eles:</p>
<p>REQUEST - Contém a requisição do cliente para o servidor, tendo os seguintes atributos:</p>
<ul>
<li>serialVersionUID - Long</li>
<li>key - String</li>
<li>Operation - String</li>
<li>Obj - Object</li>
<li>objList - ArrayList</li>
<li>toServerPort - int</li>
<li>isBackup - boolean</li>
<li>folder - String</li>
</ul>
<p>METODOS</p>
<p>RESPONSE - Contém a resposta do servidor após o processamento da Request, tendo os seguintes atributos:</p>
<ul>
<li>String - status</li>
<li>Object - obj</li>
<li>serialVersionUID – Long</li>
</ul>
<p>METODOS</p>
<ul>
<li>public Response(status) – Status da operação solicitada</li>
<li>public Response(status, obj) – Status da operação solicitado e objeto contendo a consulta de todos dados solicitados.</li>
</ul>
<p>USER - Objeto será salvo no server 1 e espelhado no 2 e terá os seguintes atributos:</p>
<ul>
<li>key - String</li>
<li>User - String</li>
<li>password - String</li>
<li>creationDate - Date</li>
<li>addrCliente- InetAddress</li>
<li>serialVersionUID - Long</li>
</ul>
<p>NOTE - Objeto será salvo no server 2 e espelhado no 3 e terá os seguintes atributos:</p>
<ul>
<li>key - Int</li>
<li>data - String</li>
<li>creationDate - Date</li>
<li>addrCliente- InetAddress</li>
<li>serialVersionUID – Long</li>
</ul>
<p>CONTACT - Objeto será salvo no server 3 e espelhado no 1 e terá os seguintes atributos:</p>
<ul>
<li>key - Int</li>
<li>name – String</li>
<li>phone – Int</li>
<li>mail(opcional) - String</li>
<li>creationDate - Date</li>
<li>addrCliente- InetAddress</li>
<li>serialVersionUID – Long</li>
</ul>
<p>O objeto USER terá sua Key caracterizado pela conteúdo do campo user mais o final “.SER”, sendo que o mesmo não poderá ser repetido.<br>
O objeto NOTE terá sua Key caracterizado pela conteúdo do campo key mais o final “.SER”, sendo que o mesmo não poderá ser repetido.<br>
O objeto CONTACT terá sua Key caracterizado pela conteúdo do campo key mais o final “.SER”, sendo que o mesmo não poderá ser repetido.</p>
<h2><a id="Operaes_possveis_69"></a>Operações possíveis:</h2>
<ul>
<li>CREATE – Cria um novo objeto</li>
<li>UPDATE – Atualiza um objeto já existente</li>
<li>DELETE – Deleta um objeto existente</li>
<li>CONSULT – Consulta um objeto específico</li>
<li>CONSULTALL – Consulta todos objetos de um determinado tipo (Note, User ou Contact)</li>
</ul>
<h2><a id="Cdigos_de_erro_76"></a>Códigos de erro:</h2>
<ul>
<li>“200 - OK” - A operação foi realizada com sucesso</li>
<li>“400 - BAD REQUEST” - O cliente não fez uma solicitação válida</li>
<li>“404 - NOT FOUND” - O servidor não pode encontrar o arquivo especificado</li>
<li>“408 - EMPTY DATA OR KEY” - Não há dados suficientes para efetivar a solicitação</li>
<li>“409 - KEY CONFLICT” - Já existe um arquivo com o mesmo nome</li>
<li>“500 - INTERNAL SERVER ERROR” - Ocorreu um erro interno no servidor</li>
<li>“503 - SERVICE UNAVAILABLE” - Os servidores não estão disponíveis</li>
</ul>

</body></html>