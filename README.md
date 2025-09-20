# FitLog API 📋💪

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen)

**FitLog API** é uma API RESTful robusta para o gerenciamento e acompanhamento de treinos de academia. O projeto foi construído com foco em **arquitetura limpa** e boas práticas de desenvolvimento de software, visando performance, manutenibilidade e escalabilidade.

## Funcionalidades

-   **Biblioteca de Exercícios Flexível:** Acesso a uma vasta lista de exercícios pré-cadastrados e a liberdade para o usuário cadastrar seus próprios exercícios personalizados.
  
-   **Registro Detalhado de Treinos:** Ferramenta para registrar detalhadamente cada etapa do treino. Para cada exercício, é possível anotar as séries, repetições e até notas subjetivas, como o nível de esforço ou percepção de dificuldade.
  
-   **Gerenciamento Completo de Rotinas:** Permite a criação, edição e armazenamento de múltiplos planos de treino, organizando todo o ciclo de treinos do usuário de forma inteligente.
  
-   **Análise de Performance e Evolução:** A plataforma contará com um dashboard interativo para transformar os dados registrados em insights visuais, facilitando a análise de progresso.

## Arquitetura e Boas Práticas

A qualidade do código e da estrutura é um pilar deste projeto:

-   **Arquitetura em Camadas:** Divisão clara de responsabilidades entre as camadas de `Controller`, `Service` e `Repository`.
-   **Padrão DTO com Records:** Utilização de Java Records como Data Transfer Objects (DTOs) para criar um contrato de API seguro, imutável e estável.
-   **Mapeamento com MapStruct:** Mapeamento automático e performático entre DTOs e Entidades, resultando em um código mais limpo e livre de boilerplate.
-   **Database Versioning com Flyway:** Versionamento do banco de dados para garantir migrações consistentes e seguras entre diferentes ambientes.
-   **Ambiente Containerizado:** Uso de Docker e Docker Compose para criar um ambiente de desenvolvimento padronizado e isolado.

## Tecnologias Utilizadas

| Categoria       | Tecnologia                                                               |
| --------------- | ------------------------------------------------------------------------ |
| **Back-end** | Java 21, Spring Boot 3, Spring Data JPA, MapStruct, Lombok               |
| **Banco de Dados**| MySQL 8.0, Flyway (Migrations)                                           |
| **Build** | Maven (Build), Docker (Containerização), Git (Versionamento)             |

## Próximos Passos

-   [ ] Desenvolvimento do dashboard de análise de performance.
-   [ ] Implementação de **Spring Security** para autenticação (JWT) e autorização.
-   [ ] Testes unitários e de integração.
-   [ ] Documentação com swagger

## Autor

**Matheus Plaza**

-   **GitHub:** [@matheus-plaza](https://github.com/matheus-plaza)
-   **LinkedIn:** [matheus-plaza](https://www.linkedin.com/in/matheus-plaza-3424aa267)
