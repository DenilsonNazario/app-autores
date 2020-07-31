1- Baixar o projeto. (https://gitlab.com/DenilsonNazario/gerenciador-de-autores.git)

2- Importar para o Eclipse. Selecionando a op��o "Import Existing Maven Projects"

3- Aguardar o maven baixar as dependencias.

5- Abri o pacote com.gerenciador.apirestgerenciador, e da um run na classe de inicializa��o do spring.

Dificuldades: 
Fazer o relacionamento muitos para muitos com o spring. (Falhei)

Oque foi feito:
validado os requisitos de cadastro de autor e tambem de atualiza��o.
Como nao consegui fazer o relacionamento correto entre as entidades, nao foi possivel vincular uma obra a um autor
no ao inserir uma nova obra.
implementa��es: 
basic authentication, usuarios fixos no c�digo (usuario: admin senha: password).
Alguns casos de testes com o Junit.
Padrao de projeto MVC.
filtragem (fintro de obra por nome e descri��o)
swagger e Delpoy no heroku.

uris:
http://localhost:8080/swagger-ui.html (Ao rodar o projeto, podemos accessar esse link e fazer requisi��es)
https://api-rest-gerenciador.herokuapp.com/swagger-ui.html (fiz o deploy, porem deu um erro no servidor e nao consegui resolver a tempo.)

Agredecimentos:
Agrede�o a chande de participar desse desafio, aprendi muito sobre o spring. Muito Obg pela oportunidade, infelizmente nao tive
muito tempo para desenvolver, mas fiz o maximo que p�de.