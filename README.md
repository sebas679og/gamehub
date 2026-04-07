# 🎮 GameHub

**Backend for an online video game tournament management platform.**

GameHub is a REST API built with **FastAPI** that allows players and organizers to create, manage, and participate in video game tournaments — including automatic matchmaking, a basic chat system, and real-time rankings.

---

## 📋 Table of Contents
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Environment Variables](#environment-variables)
- [Running the Project](#running-the-project)
- [Docker Registry](#docker-registry)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Python 3.14 |
| Framework | FastAPI |
| Package Manager | [uv](https://github.com/astral-sh/uv) (Astral) |
| Database | PostgreSQL |
| Authentication | JWT (JSON Web Tokens) |
| API Docs | Swagger UI / ReDoc (FastAPI built-in) |
| Architecture | Hexagonal (Ports & Adapters) |

---

## 🏛 Architecture

GameHub follows **Hexagonal Architecture** (also known as Ports & Adapters), keeping the domain and business logic completely decoupled from frameworks, databases, and external services.

```
┌─────────────────────────────────────────┐
│              Adapters (In)              │
│         FastAPI Controllers / Routes    │
└────────────────────┬────────────────────┘
                     │ Ports (In)
          ┌──────────▼──────────┐
          │    Application      │
          │   (Use Cases)       │
          └──────────┬──────────┘
                     │ Ports (Out)
┌────────────────────▼────────────────────┐
│             Adapters (Out)              │
│      PostgreSQL Repositories / Email    │
└─────────────────────────────────────────┘
```

- **Domain** — Entities, value objects, and business rules. Zero external dependencies.
- **Application** — Use cases that orchestrate the domain. Depend only on port interfaces.
- **Adapters (In)** — FastAPI routers that receive HTTP requests and call use cases.
- **Adapters (Out)** — SQLAlchemy/asyncpg repositories that implement persistence ports.

---

## ✅ Prerequisites

- Python 3.14+
- [uv](https://github.com/astral-sh/uv) (Astral package manager)
- PostgreSQL running locally or via Docker
- Git

Install `uv` if you don't have it:

```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

---

## 🚀 Installation

```bash
# 1. Clone the repository
git clone https://github.com/sebas679og/gamehub.git
cd gamehub

# 2. Create virtual environment and install dependencies with uv
uv sync

# 3. Copy the environment variables file
cp .env.example .env

# 4. Edit .env with your local configuration (see next section)

# 5. Run database migrations
uv run alembic upgrade head
```

---

## 🔐 Environment Variables

Copy the template file and fill in the values:

```bash
cp .env.template .env
```
> [!NOTE]
> Never commit your `.env` file. It is already included in `.gitignore`.

---

## ▶️ Running the Project

```bash
# Development (with hot-reload)
uv run uvicorn app.main:app --reload

# Production
uv run uvicorn app.main:app --port 8000

# Set up the development environment with dev dependencies
uv sync --group dev

# Run tests
uv run pytest

# run tests with debugging
uv run pytest --capture=no

# Lint and format
ruff check app/ --fix
ruff format app/
ruff check app/

# type check
ty check app/
```

---

## Docker Registry

A Docker image is available on Docker Hub for quick deployment and testing:

**GameHub Docker Image:** [sebas679og/gamehub](https://hub.docker.com/r/sebas679og/gamehub)
```bash
docker pull sebas679og/gamehub:latest
```

### Required Environment Variables

| Variable | Description |
|---|---|
| `GAMEHUB_DATASOURCE_USERNAME` | Database user |
| `GAMEHUB_DATASOURCE_PASSWORD` | Database password |
| `GAMEHUB_DATASOURCE_DATABASE` | Database name |
| `GAMEHUB_DATASOURCE_HOST` | Database host (e.g. `postgres-gamehub`) |
| `GAMEHUB_DATASOURCE_PORT` | Database port (default: `5432`) |

### Quick Start
```bash
docker run -d \
  -e GAMEHUB_DATASOURCE_USERNAME=your_user \
  -e GAMEHUB_DATASOURCE_PASSWORD=your_password \
  -e GAMEHUB_DATASOURCE_DATABASE=gamehub \
  -e GAMEHUB_DATASOURCE_HOST=postgres-gamehub \
  -e GAMEHUB_DATASOURCE_PORT=5432 \
  -p 8000:8000 \
  sebas679og/gamehub:latest
```

---

## 📖 API Documentation

Once the server is running, interactive documentation is available at:

| Interface | URL |
|-----------|-----|
| Swagger UI | `http://localhost:8000/docs` |
| ReDoc | `http://localhost:8000/redoc` |
| OpenAPI JSON | `http://localhost:8000/openapi.json` |

---

## 📁 Project Structure

```
gamehub/
├── app/
│   ├── main.py                        # FastAPI app entry point
│   │
│   ├── core/                          # ② Config — application configuration
│   │   ├── config.py                  # Load settings from .env
│   │   └── logger.py                  # Logging configuration
│   │
│   ├── domain/                        # ① DOMAIN — pure business logic
│   │   ├── models/                    # Entities and value objects
│   │   │   
│   │   ├── enums/                     # Role, Status, Result
│   │   └── ports/                     # Abstract interfaces (contracts)
│   │       ├── repositories/
│   │       └── services/
│   │
│   ├── application/                   # ② APPLICATION — use cases
│   │
│   └── adapters/                      # ③ ADAPTERS — infrastructure
│       ├── api/                       # In — FastAPI routers
│       │   ├── v1/                    # Versioned API
│       │   └── schemas/               # Pydantic request/response models
│       └── persistence/               # Out — PostgreSQL repositories
│           ├── models/                # SQLAlchemy ORM models
│           ├── repositories/          # Concrete implementations
│           └── database.py            # DB connection and session
│
├── tests/
│   ├── config/                        # Test configuration reutilized across test suites
│   ├── unit/                          # Unit tests for domain and application layers
│   ├── integration/                   # Integration tests for API endpoints and database interactions
│   ├── conftest.py                    # Pytest configuration and fixtures
│   └── test_context.py                # Test context manager for setup/teardown
│
├── alembic/                           # Database migrations
├── .env.example
├── .gitignore
├── pyproject.toml                     # Project config (uv / ruff / ty)
├── CONTRIBUTING.md
├── LICENSE
└── README.md
```

---

## 🤝 Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](./CONTRIBUTING.md) before getting started.

---

## 📄 License

This project is licensed under the [MIT License](./LICENSE).
