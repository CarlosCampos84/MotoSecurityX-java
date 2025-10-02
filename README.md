## 🚀 MotoSecurityX — Challenge_java (2TDS 2025)

Spring Boot 3 + Thymeleaf + JPA/Hibernate + Flyway + Spring Security

Aplicação web para controle e monitoramento de motos e pátios, com autenticação baseada em usuários/roles, validações de regras de negócio e histórico de movimentações.

## 👥 Integrantes do Grupo

Caio Henrique – RM: 554600
Carlos Eduardo – RM: 555223
Antônio Lino – RM: 554518

## 🎯 Objetivo e Domínio

O domínio simula operações da Mottu:

  Usuários: administradores e operadores (seedados via Flyway)

  Pátios: locais físicos que armazenam motos, com capacidade definida

  Motos: possuem Placa única, Modelo, status de disponibilidade e vínculo com pátio

  Movimentações: registram transferências de motos entre pátios

  **Regras:** 

    - Placa única (constraint UNIQUE no banco)

    - Usuários e papéis definidos via Flyway (ROLE_ADMIN / ROLE_OPERADOR)

    - Moto só pode ser movida para pátio com capacidade disponível

    - Operador: acesso apenas leitura

    - Admin: CRUD completo + movimentação
  
  **Benefício de negócio:** 
    Gestão de ativos e visibilidade de onde cada moto está estacionada, com maior segurança de acesso.

## 🧭 Arquitetura (Camadas)

**📂 src/main/java/br/com/motosecurityx/**

  config/ → Configuração de segurança (SecurityConfig), reset e normalização de senhas (DevPasswordReset, DevPasswordNormalizer)

  domain/ → Entidades de domínio (Moto, Patio, Movimentacao, Funcionario, Alocacao)

  repository/ → Interfaces JPA (MotoRepository, PatioRepository, MovimentacaoRepository, etc.)

  service/ → Regras de negócio (MotoServiceImpl, PatioServiceImpl, etc.)

  web/ → Controladores MVC (MotoController, PatioController, AlocacaoController, PageController)

**📂 src/main/resources/**

  db/migration/ → Scripts Flyway (V1__create_tables.sql até V11__normalize_passwords.sql)

  templates/ → iews Thymeleaf (login.html, home.html, motos/, patios/, alocacoes/, fragments/)

  static/ → CSS (css/app.css)

## 🧩 Modelagem de Domínio (DDD)

- Entidades principais:

    Moto: placa, modelo, disponível, pátio atual

    Patio: nome, capacidade

    Movimentacao: moto, pátio origem, pátio destino, data/hora

    Usuario → username, senha (bcrypt), role (ADMIN ou OPERADOR)

    Funcionario / Alocacao → suporte a controle de funcionários vinculados a pátios

- Regras implementadas:

    MotoService.moverMoto() → valida capacidade do pátio destino, atualiza vínculo e gera movimentação

    Usuários ADMIN têm permissões CRUD, OPERADOR apenas leitura

✅ Status atual: 

  Login/Logout com Spring Security (usuário seedado no banco)

  Perfis de acesso: ADMIN e OPERADOR

  CRUD completo de Motos e Pátios com validações

  Controle de movimentação de motos entre pátios

  Views Thymeleaf organizadas com fragments (_header.html, _footer.html)

  Templates de erro customizados (404.html, error.html)

  Flyway controlando todas as migrations até V11__normalize_passwords.sql

## 🔧 Requisitos

- Java 17

- Maven 3.9+ (wrapper incluído ./mvnw)

- Spring Boot 3.5.x

- Banco H2 em memória (dev)

- Flyway para migrations

## ▶️ Como executar localmente

- Na raiz do repositório:

  # compilar
    
    ./mvnw clean package

  # rodar aplicação
    
    ./mvnw spring-boot:run

  # Acesso:

    Aplicação: 👉 http://localhost:8081

    Console H2: 👉 http://localhost:8081/h2-console

      JDBC URL: jdbc:h2:mem:motosecurityx

      User: sa

      Sem senha

  # Usuários disponíveis (seedados via Flyway):

    admin / admin123 → ROLE_ADMIN

    operador / oper123 → ROLE_OPERADOR

## 🌐 Funcionalidades (exemplos)
  
### Login

  Página: /login

  Redireciona para /home após autenticação

  Feedback de erro (Credenciais inválidas)

  Logout via POST /logout.

### Pátios

  Listar: /patios

  Criar/Editar/Excluir: ADMIN

  Visualização: OPERADOR

### Motos
  
  Listar: /motos

  Criar/Editar/Excluir: ADMIN

  Mover entre pátios: /fluxos/mover/{id} (ADMIN)

### 🔄 Movimentações

  Histórico de transferências de motos

  Vincula pátio origem, destino e data/hora

## 🗃️ Migrations Flyway

  Scripts em /resources/db/migration:

    V1__create_tables.sql

    V2__seed_data.sql

    V3__security_seed_roles.sql

    V4__security_seed_users.sql

    V5__alter_moto_add_disponivel.sql

    V6__create_table_movimentacao.sql

    V7__alter_patio_add_capacidade.sql

    V8__bcrypt_usuarios.sql

    V9__create_funcionario_and_alocacao.sql

    V10__fix_password_prefix.sql

    V11__normalize_passwords.sql

## 🧼 Clean Code

- Controllers finos, apenas coordenam request/response

- Services concentram regras de negócio

- Reutilização via interfaces de repositório JPA

- Validações centralizadas com Bean Validation

- Fragments Thymeleaf para reaproveitar layout

## 📋 Testes

- Testes manuais: via navegação (Thymeleaf)

- Autenticação testada com ADMIN e OPERADOR

- Regras de negócio validadas:

    Não mover moto se pátio cheio

    Moto exige placa válida

(Futuramente: adicionar testes unitários com JUnit + MockMvc)

# 🎬 Vídeo 

Link:  
"https://youtu.be/l2A_7gutvWo?si=zm9AVyZDQIb9V8Kj"

## 📄 Licença

Uso educacional/acadêmico.

## 🌟 Propósito

“Código limpo sempre parece que foi escrito por alguém que se importa.” — Uncle Bob
