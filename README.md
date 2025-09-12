# FitLog API

API RESTful para gerenciamento e acompanhamento de treinos de academia, construída com foco em arquitetura limpa e boas práticas de mercado.

 - Principais Funcionalidades:
   
Gestão de Exercícios: Catálogo com exercícios globais e customizados por usuário.

Criação de Fichas de Treino: Montagem de planos de treino (WorkoutRoutine) reutilizáveis.

Registro de Sessões: Histórico detalhado de cada treino realizado, incluindo séries, repetições e cargas.

 - Arquitetura Profissional:

Camadas bem definidas (Controller, Service, Repository).

Padrão DTO com Java Records para um contrato de API seguro e estável.

Mapeamento automático com MapStruct para código limpo e performático.

Banco de dados versionado com Flyway.

Ambiente de desenvolvimento containerizado com Docker.

 - Tecnologias Utilizadas:
   
Back-end: Java 21, Spring Boot 3, Spring Data JPA, Spring Security (futuro), MapStruct, Lombok

Banco de Dados: MySQL 8.0, Flyway

Ferramentas: Maven, Docker, Git

 - Autor:
   
Matheus Plaza

GitHub: @matheus-plaza

LinkedIn: matheus-plaza
