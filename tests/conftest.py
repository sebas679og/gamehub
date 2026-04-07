import pytest
import pytest_asyncio
from testcontainers.postgres import PostgresContainer

from tests.config.db_helpers import build_db_url, create_engine_and_session


@pytest.fixture(scope="session")
def postgres_container():
    with PostgresContainer("postgres:17.8") as container:
        yield container

@pytest_asyncio.fixture(scope="function")
async def db_engine(postgres_container):
    db_url = build_db_url(postgres_container)
    engine, async_session = await create_engine_and_session(db_url)

    yield engine, async_session

    await engine.dispose()
