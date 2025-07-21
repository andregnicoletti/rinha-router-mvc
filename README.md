# Rinha de Backend 2025 – Router MVC

Projeto participante da [Rinha de Backend 2025](https://github.com/andregnicoletti/rinha-router-mvc), implementado com **Spring Boot**, **PostgreSQL** e **Nginx**.\
O objetivo é ser simples, performático e fácil de escalar durante a competição.

---

## 🚀 Tecnologias & Stack

- **Java 21** / Spring Boot (MVC)
- **PostgreSQL 16**
- **Nginx (reverse proxy)**
- **Docker Compose**
- **Linux Alpine**

---

## 🛠️ Como rodar

### 1. Clone o repositório

```bash
git clone https://github.com/andregnicoletti/rinha-router-mvc.git
cd rinha-router-mvc
```

### 2. Configure o `.env`

Exemplo de arquivo `.env`:

```env
POSTGRES_USER=rinha
POSTGRES_PASSWORD=rinha
POSTGRES_DB=rinha-mvc
POSTGRES_HOST=db-rinha

SPRING_DATASOURCE_URL=jdbc:postgresql://${POSTGRES_HOST}:5432/${POSTGRES_DB}

PAYMENT_SERVICE_URL_DEFAULT=http://payment-processor-default:8080/payments
PAYMENT_SERVICE_URL_FALLBACK=http://payment-processor-fallback:8080/payments

SERVER_PORT=8080
```

### 3. Suba a stack

```bash
docker compose up --build
```

A aplicação principal ficará disponível via **Nginx** na porta **9999**.

---

## ⚙️ Estrutura do Docker Compose

- **nginx**: reverse proxy para balanceamento/roteamento
- **db-rinha**: banco PostgreSQL persistente
- **api-1** e **api-2**: instâncias da API Spring Boot

Recursos já limitados para o desafio (CPU e memória), compatível com os requisitos da competição.

---

## 📒 Endpoints Principais

| Método | Rota                        | Descrição                           |
| ------ | --------------------------- | ----------------------------------- |
| POST   | `/clientes/{id}/transacoes` | Processa transações para um cliente |
| GET    | `/clientes/{id}/extrato`    | Retorna extrato do cliente          |

> Detalhes/exemplos podem ser encontrados na documentação oficial da [Rinha de Backend](https://github.com/arenapr-rinha-de-backend-2025).

---

## 💡 Observações

- Rede `payment-processor` deve estar criada previamente (ou ajuste para não ser externa).
- Volume `pgdata` é utilizado para persistência do banco de dados.
- Configuração do Nginx pode ser customizada via `nginx.conf`.

---

## 👨‍💻 Autor

Feito por [André Nicoletti](https://github.com/andregnicoletti)\
Contato: [LinkedIn](https://www.linkedin.com/in/andre-nicoletti-dev/)\
Dúvidas, sugestões ou PRs são bem-vindos!

---
