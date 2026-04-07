from sqlalchemy.ext.asyncio import create_async_engine, async_sessionmaker

from app.adapters.persistence.database import Base


def build_db_url(container) -> str:
    return (
        f"postgresql+asyncpg://{container.username}:{container.password}"
        f"@{container.get_container_host_ip()}:{container.get_exposed_port(5432)}"
        f"/{container.dbname}"
    )


async def create_engine_and_session(db_url: str):
    engine = create_async_engine(db_url, echo=True)
    async_session = async_sessionmaker(bind=engine, expire_on_commit=False)

    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)

    return engine, async_session