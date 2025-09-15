-- Dados iniciais opcionais para teste
-- Apenas alguns usuários de exemplo para demonstração

INSERT INTO users (full_name, email, phone, birth_date, user_type) VALUES
('Admin do Sistema', 'admin@techmanage.com', '+5511999999999', '1985-01-15', 'ADMIN'),
('Editor Principal', 'editor@techmanage.com', '+5511888888888', '1990-03-20', 'EDITOR'),
('Visualizador Teste', 'viewer@techmanage.com', '+5511777777777', '1995-07-10', 'VIEWER')
ON DUPLICATE KEY UPDATE id=id;