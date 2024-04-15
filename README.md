Projeto Api VollMed

Projeto iniciado no estudo da Alura nos cursos:

 - Spring Boot 3: desenvolva uma API Rest em Java
 - Spring Boot 3: aplique boas práticas e proteja uma API Rest
 - Spring Boot 3: documente, teste e prepare uma API para o deploy

Endpoint da aplicação

 base_url/api/swagger-ui/index.html

Melhorias:

 - Adição do docker-compose para o container do banco de dados Postgres
 - Adição do Dockerfile e docker-compose para criar container da aplicação
 - Alteração de Field injection para Constructor injection
 - Atualização do Spring Boot 3.0 para 3.2
 - Organização do projeto pela estrutura Package by layer
 - Alteração do arquivo application.properties para application.yml

Para iniciar o banco de dados no docker
    
 1 - Entrar na pasta db_docker
 2A - No windows, executar o comando docker-compose up -d
 2B - No linux, executar o comando docker compose up -d

Para iniciar a aplicação no docker

 1 - Entrar na pasta raiz
 2A - No windows, executar o comando docker-compose up --build -d
 2B - No linus, eecutar o comando docker compose up --build -d