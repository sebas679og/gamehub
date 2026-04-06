import asyncio
from logging.config import fileConfig

from sqlalchemy import pool

from alembic import context
from sqlalchemy.ext.asyncio import create_async_engine

from app.adapters.persistence.database import Base
from app.core.config import Settings

settings = Settings()

config = context.config

config.set_main_option("sqlalchemy.url", settings.gamehub_datasource_url)

if config.config_file_name is not None:
    fileConfig(config.config_file_name)

target_metadata = Base.metadata

def run_migrations_offline() -> None:
    """Run migrations in 'offline' mode."""
    url = config.get_main_option("sqlalchemy.url")
    context.configure(
        url=url,
        target_metadata=target_metadata,
        literal_binds=True,
        dialect_opts={"paramstyle": "named"},
    )

    with context.begin_transaction():
        context.run_migrations()


async def run_migrations_online_async() -> None:
    """Run migrations in 'online' mode using AsyncEngine."""
    connectable = create_async_engine(
        settings.gamehub_datasource_url,
        poolclass=pool.NullPool,
    )

    async with connectable.begin() as conn:
        await conn.run_sync(lambda connection: context.configure(
            connection=connection,
            target_metadata=target_metadata
        ))

        await conn.run_sync(lambda connection: context.run_migrations())

    await connectable.dispose()

def run_migrations_online() -> None:
    """Wrapper to run async from Alembic."""
    asyncio.run(run_migrations_online_async())

if context.is_offline_mode():
    run_migrations_offline()
else:
    run_migrations_online()
