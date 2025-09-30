## 🚀 MotoSecurityX — Challenge_java (2TDS 2025)

Spring Boot 3 + Thymeleaf + JPA/Hibernate + Flyway

Aplicação web para controle e monitoramento de motos e pátios, com autenticação e autorização baseadas em perfis de usuário.

O projeto aplica Camadas de Serviço + Repositórios (DDD básico), validações com Bean Validation, migrações com Flyway e boas práticas de Clean Code.

## 👥 Integrantes do Grupo

Caio Henrique – RM: 554600
Carlos Eduardo – RM: 555223
Antônio Lino – RM: 554518

## 🎯 Objetivo e Domínio

O domínio simula operações da Mottu:

  Usuários: administradores e operadores autenticados via Spring Security

  Pátios: locais físicos que armazenam motos, com capacidade definida

  Motos: possuem Placa (única), Modelo, disponibilidade e vínculo com um pátio

  Movimentações: registram transferências de motos entre pátios (fluxo de negócio)

  **Regras:** 

    - Placa única (constraint UNIQUE no banco)

    - Usuários e papéis definidos via Flyway (ADMIN / OPERADOR)

    - Moto só pode ser movida para pátio com capacidade disponível

    - Operador: acesso apenas leitura

    Admin: CRUD completo + movimentação
  
  **Benefício de negócio:** 
    Gestão de ativos e visibilidade de onde cada moto está estacionada, além de segurança de acesso

## 🧭 Arquitetura (Camadas)

**📂 src/main/java/br/com/motosecurityx/**

  config/ → Configuração de segurança (Spring Security).

  domain/ → Entidades do domínio (Moto, Patio, Movimentacao).

  repository/ → Interfaces JPA (MotoRepository, PatioRepository, MovimentacaoRepository).

  service/ → Regras de negócio (MotoService, PatioService).

  web/ → Controladores Spring MVC (Controllers REST + Views Thymeleaf).

**📂 src/main/resources/**

  db/migration/ → Scripts Flyway (criação de tabelas + seed de usuários/roles).

  templates/ → Views Thymeleaf (motos, patios, login, erro).

  static/ → CSS/JS/Imagens.

**Princípios aplicados:**

- Separação de responsabilidades: Controller → Service → Repository

- Inversão de Dependência: regras de negócio dependem de interfaces

- Regras de negócio concentradas no Service

- Validações via Bean Validation nas Entidades

- Views server-side com Thymeleaf (sem expor lógica ao cliente)

## 🧩 Modelagem de Domínio (DDD)

- Entidades:

    Moto: placa, modelo, disponível, pátio atual

    Patio: nome, capacidade

    Movimentacao: moto, pátio origem, pátio destino, data/hora

- Regras de Negócio (Services):

    MotoService.moverMoto(motoId, patioId)

      valida capacidade do pátio destino,

      atualiza pátio da moto,

      cria registro de movimentação

✅ Status atual: 

  CRUD completo de Motos e Pátios (com validações e mensagens de erro)

  Autenticação via JDBC (usuários seed no banco, ADMIN / OPERADOR)

  Controle de acesso diferenciado (Spring Security)

  Template Login com feedback (logout, acesso negado)

  Template de Erro (error.html + 404.html) customizado

  Fluxo de Movimentação de Motos implementado com histórico

🧱 Backlog de evolução futura: incluir entidade extra (ex.: Ocorrencia ou Manutencao) para enriquecer o domínio.

## 🔧 Requisitos

- Java 17

- Maven 3.9+ (wrapper já incluído no projeto: ./mvnw)

- Spring Boot 3.5.x

- Banco H2 em memória (dev)

- Flyway para migrations

## ▶️ Como executar localmente

- Na raiz do repositório:

  # compilar
    
    ./mvnw clean package

  # rodar aplicação
    
    ./mvnw spring-boot:run

  # Aplicação disponível em:
    👉 http://localhost:8081

  # Console H2:
    👉 http://localhost:8081/h2-console

    (JDBC URL: jdbc:h2:mem:motosecurityx, user: sa, sem senha)

  **Usuários seedados (via Flyway):**
  - admin / 123 → ADMIN

  - operador / 123 → OPERADOR

## 🌐 Funcionalidades (exemplos)
  
### Login

  Página: /login

  Redireciona para /home após sucesso.

  Logout via POST /logout.

### Pátios

  Listar: /patios

  Criar: /patios/new (apenas ADMIN)

  Editar: /patios/edit/{id} (ADMIN)

  Excluir: botão em /patios (ADMIN)

### Motos
  
  Listar: /motos

  Criar: /motos/new (ADMIN)

  Editar: /motos/edit/{id} (ADMIN)

  Excluir: botão em /motos (ADMIN)

  Mover entre pátios: /fluxos/mover/{id} (ADMIN)

## 🗃️ Persistência & Migrations

- Banco H2 em memória (desenvolvimento).

- Migrations Flyway em /resources/db/migration:

    V1__create_tables.sql

    V2__seed_data.sql

    V3__security_seed_roles.sql

    V4__security_seed_users.sql

## 🧼 Clean Code

- SRP/DRY aplicados em Services e Controllers

- Controllers finos → só coordenam requisição/resposta.

- Nomes claros, métodos curtos, responsabilidades bem separadas.

- Validações centralizadas com Bean Validation.

## 📋 Testes

- Testes manuais: via navegação em Views (Thymeleaf)

- Testes de autenticação com diferentes perfis (ADMIN vs OPERADOR)

- Testes de regra de negócio:

    Moto não pode ser movida se pátio cheio

    Moto exige placa válida (padrão Mercosul)

(Futuramente: adicionar testes unitários com JUnit + MockMvc)

## 📄 Licença

Uso educacional/acadêmico.

## 🌟 Propósito

“Código limpo sempre parece que foi escrito por alguém que se importa.” — Uncle Bob
