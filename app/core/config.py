from pathlib import Path

from pydantic_settings import BaseSettings, SettingsConfigDict

BASE_DIR = Path(__file__).resolve().parent.parent.parent

class Settings(BaseSettings):

    model_config = SettingsConfigDict(
        env_file=BASE_DIR / ".env",
        env_file_encoding="utf-8"
    )

    gamehub_datasource_username: str
    gamehub_datasource_password: str
    gamehub_datasource_database: str
    gamehub_datasource_host: str = "localhost"
    gamehub_datasource_port: int = 5432

    @property
    def gamehub_datasource_url(self) -> str:
        return (f"postgresql+asyncpg://{self.gamehub_datasource_username}:"
                f"{self.gamehub_datasource_password}@{self.gamehub_datasource_host}:"
                f"{self.gamehub_datasource_port}/{self.gamehub_datasource_database}")

settings = Settings()  # type: ignore