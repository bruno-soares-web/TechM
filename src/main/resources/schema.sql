-- Schema SQL para criação das tabelas
-- Este arquivo é opcional, pois o JPA já cria as tabelas automaticamente

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    CONSTRAINT chk_user_type CHECK (user_type IN ('ADMIN', 'EDITOR', 'VIEWER'))
);

-- Índices para melhor performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_user_type ON users(user_type);