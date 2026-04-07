FROM python:3.14-slim AS builder

ARG UID=1010
ARG GID=1010

RUN groupadd -g "${GID}" gamehub \
    && useradd --create-home --no-log-init -u "${UID}" -g gamehub gamehub

COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /usr/local/bin/

WORKDIR /opt/app

COPY --chown=gamehub:gamehub pyproject.toml uv.lock* ./
RUN uv sync --frozen --no-install-project --no-dev

COPY --chown=gamehub:gamehub . .
RUN uv sync --frozen --no-dev


FROM python:3.14-slim AS runtime

ARG BUILD_DATE=unknown
ARG BUILD_VERSION=unknown
ARG IMAGE_DESCRIPTION=unknown
ARG IMAGE_NAME="GameHub"
ARG UID=1010
ARG GID=1010

LABEL gamehub.build-date=$BUILD_DATE \
      gamehub.name=$IMAGE_NAME \
      gamehub.description=$IMAGE_DESCRIPTION \
      gamehub.base.image="python:3.14-slim" \
      gamehub.version=$BUILD_VERSION \
      maintainer="GameHub"

RUN groupadd -g "${GID}" gamehub \
    && useradd --create-home --no-log-init -u "${UID}" -g gamehub gamehub

WORKDIR /opt/app

COPY --from=builder --chown=gamehub:gamehub /opt/app/.venv /opt/app/.venv
COPY --from=builder --chown=gamehub:gamehub /opt/app /opt/app

RUN mkdir -p /opt/app/logs && chown -R gamehub:gamehub /opt/app/logs

ENV PATH="/opt/app/.venv/bin:$PATH" \
    PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1

USER gamehub

EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]