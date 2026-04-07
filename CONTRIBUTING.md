# 🤝 Contributing Guide — GameHub

Thank you for your interest in contributing to GameHub! This document explains how to participate in the project in an organized and effective way.

---

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Ways to Contribute](#ways-to-contribute)
- [Development Environment Setup](#development-environment-setup)
- [Git Workflow](#git-workflow)
- [Commit Conventions](#commit-conventions)
- [Code Style](#code-style)
- [Pull Requests](#pull-requests)
- [Reporting Bugs](#reporting-bugs)
- [Proposing New Features](#proposing-new-features)

---

## 📜 Code of Conduct

By participating in this project, you agree to maintain a respectful and inclusive environment. All contributors are expected to:

- Be respectful of others' opinions and experience.
- Accept constructive criticism with a positive attitude.
- Focus on what is best for the community and the project.

---

## ❓ Ways to Contribute

There are several ways to contribute to GameHub:

- 🐛 **Report bugs** — Open an issue describing the problem.
- 💡 **Propose features** — Open an issue with your proposal.
- 🔧 **Resolve issues** — Browse open issues and comment that you'll be working on one.
- 📝 **Improve documentation** — Corrections, translations, or new sections.
- ✅ **Write tests** — Increase the project's test coverage.

---

## ⚙️ Development Environment Setup

### Requirements

- Python 3.14+
- [uv](https://github.com/astral-sh/uv) (Astral package manager)
- PostgreSQL running locally or via Docker
- Git

### Installing `uv`

```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

### Setup Steps

```bash
# 1. Fork the repository from GitHub

# 2. Clone your fork locally
git clone https://github.com/YOUR_USERNAME/gamehub.git
cd gamehub

# 3. Add the original repository as upstream remote
git remote add upstream https://github.com/sebas679og/gamehub.git

# 4. Create the virtual environment and install dependencies
uv sync

# 5. Set up the development environment with dev dependencies
uv sync --group dev

# 6. Copy environment variables
cp .env.example .env
# Edit .env with your local configuration

# 7. Run database migrations
uv run alembic upgrade head

# 8. Verify everything works
uv run pytest test/
uv run uvicorn app.main:app --reload
```

---

## 🌿 Git Workflow

1. **Sync your fork** before starting any work:

```bash
git checkout main
git fetch upstream
git merge upstream/main
```

2. **Create a branch** with a descriptive name and update the branch with the latest changes from dev:

```bash
# Format: type/short-description
git checkout -b feat/matchmaking-engine
git checkout -b fix/jwt-token-expiration
git checkout -b docs/update-contributing
git checkout -b refactor/tournament-use-cases

# Update your branch with the latest changes from dev
git fetch upstream
git rebase upstream/dev
```

3. **Work on your branch**, making atomic and descriptive commits.

4. **Push your branch** to your fork:

```bash
git push origin feat/matchmaking-engine
```

5. **Open a Pull Request** targeting the `dev` branch of the original repository.

---

## ✍️ Commit Conventions

We use [Conventional Commits](https://www.conventionalcommits.org/en/). The format is:

```
type(optional scope): short description in imperative mood
```

### Allowed Types

| Type | Usage |
|------|-------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation changes |
| `style` | Formatting, whitespace (no logic change) |
| `refactor` | Refactoring without new feature or fix |
| `test` | Adding or fixing tests |
| `chore` | Maintenance tasks, configuration |

### Examples

```
feat(tournaments): add tournament creation endpoint
fix(auth): fix expired JWT token validation
docs(readme): update installation instructions
test(matches): add tests for result update use case
refactor(ranking): extract ranking calculation to domain service
chore: update dependencies
```

---

## Versioning
The project follows [Semantic Versioning](https://semver.org/). Before opening a PR, update the version in `pyproject.toml` according to the type of change:

| Change type | Bump | Example |
|---|---|---|
| Bug fix | Patch | `0.1.0 → 0.1.1` |
| New feature | Minor | `0.1.0 → 0.2.0` |
| Breaking change | Major | `0.1.0 → 1.0.0` |

Update the version manually in `pyproject.toml`:
```toml
[project]
version = "0.2.0"
```

Then commit the change:
```bash
git add pyproject.toml
git commit -m "chore: bump version to 0.2.0"
```
> [!IMPORTANT]
> This ensures that Docker images published to the registry always reflect the correct version of the service.

---

## 🎨 Code Style

GameHub uses **Ruff** for linting and formatting. The project follows these conventions:

- Run `ruff check app/ --fix` to apply linter rules.
- Run `ruff check app/` before committing.
- Run `ruff format app/` to auto-format code.
- Run `type check app/` to check type hints.
- Follow **PEP 8** naming conventions.
- Write variable names, functions, classes, and comments in **English**.
- Use **type hints** on all function signatures.
- Keep functions small with a single responsibility.
- Follow the **Hexagonal Architecture** layer rules:
  - The `domain/` layer must have **zero** external dependencies (no FastAPI, no SQLAlchemy).
  - The `application/` layer may only depend on `domain/` ports.
  - The `adapters/` layer may depend on all layers.

---

## 🔍 Pull Requests

Before opening a PR, make sure that:

- [ ] Your branch is up to date with `dev`.
- [ ] The code passes existing tests (`uv run pytest test/`).
- [ ] You added tests for any new feature or bug fix.
- [ ] The code passes the linter (`ruff check app/`).
- [ ] the code passes type checks (`type check app/`).
- [ ] You updated the documentation if necessary.
- [ ] Your changes respect the Hexagonal Architecture layer boundaries.

### PR Template

When opening a PR, please include:

```
## Description
Brief description of the changes made.

## Type of change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation

## Related issue
Closes #ISSUE_NUMBER

## How was it tested?
Describe the steps to verify the changes.

## Checklist
- [ ] My code follows the project style
- [ ] I have added tests
- [ ] All tests pass
- [ ] I have updated the documentation
- [ ] My changes respect the hexagonal architecture boundaries
```

---

## 🐛 Reporting Bugs

When reporting a bug, include:

1. **Clear description** of the problem.
2. **Steps to reproduce** (detailed and in order).
3. **Expected behavior** vs **actual behavior**.
4. **Screenshots or logs** if applicable.
5. **Environment**: OS, Python version, `uv` version, etc.

Use the issue template available on GitHub.

---

## 💡 Proposing New Features

Before implementing a new feature:

1. Open an **issue** with the `enhancement` label.
2. Describe the problem it solves and the proposed solution.
3. Wait for feedback from the team before starting development.

This avoids duplicate work and ensures the feature aligns with the project's vision.

---

Have questions? Open an issue with the `question` label or contact the maintainer team. Welcome to GameHub! 🎮