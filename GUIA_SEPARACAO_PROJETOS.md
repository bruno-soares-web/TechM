# Guia de Separação: Frontend e Backend

Este guia explica como separar o projeto TechManage em dois repositórios independentes, seguindo as melhores práticas recomendadas pela documentação Angular e Spring Boot.

## 📋 Visão Geral

**Estrutura Atual (Não Recomendada):**
```
TechManage/
├── src/                    (Backend Java)
├── pom.xml
└── techmanage-frontend/    (Frontend Angular)
```

**Estrutura Recomendada:**
```
Documents/
├── TechManage/             (Backend - Repositório Git separado)
│   ├── src/
│   └── pom.xml
└── techmanage-frontend/    (Frontend - Repositório Git separado)
    ├── src/
    └── package.json
```

## 🚀 Passo a Passo Automatizado

### Opção 1: Executar Script PowerShell (Recomendado)

1. **Abra o PowerShell como Administrador**

2. **Execute o script de separação:**
   ```powershell
   cd C:\Users\000491631\Documents\TechManage
   .\SEPARAR_PROJETOS.ps1
   ```

3. **Aguarde a conclusão** (pode demorar alguns minutos para npm install)

4. **Verifique os resultados**:
   - Frontend copiado para: `C:\Users\000491631\Documents\techmanage-frontend`
   - Git inicializado no frontend
   - Dependências instaladas
   - Primeiro commit criado

### Opção 2: Passo a Passo Manual

Se preferir fazer manualmente, siga estes passos:

#### 1. Copiar Frontend para Fora do Backend

```powershell
# No PowerShell
cd C:\Users\000491631\Documents

# Criar diretório para o frontend
New-Item -ItemType Directory -Force -Path "techmanage-frontend"

# Copiar arquivos (exceto node_modules)
$source = "TechManage\techmanage-frontend"
$dest = "techmanage-frontend"

# Copiar arquivos importantes
Copy-Item -Path "$source\src" -Destination $dest -Recurse -Force
Copy-Item -Path "$source\*.json" -Destination $dest -Force
Copy-Item -Path "$source\*.ts" -Destination $dest -Force
Copy-Item -Path "$source\*.js" -Destination $dest -Force
Copy-Item -Path "$source\*.md" -Destination $dest -Force
Copy-Item -Path "$source\.gitignore" -Destination $dest -Force
Copy-Item -Path "$source\angular.json" -Destination $dest -Force
Copy-Item -Path "$source\tsconfig.*" -Destination $dest -Force
```

#### 2. Configurar Frontend como Projeto Independente

```powershell
cd C:\Users\000491631\Documents\techmanage-frontend

# Inicializar repositório Git
git init

# Instalar dependências
npm install

# Criar primeiro commit
git add .
git commit -m "Initial commit: TechManage Frontend"
```

#### 3. Atualizar Backend

```powershell
cd C:\Users\000491631\Documents\TechManage

# Adicionar frontend ao .gitignore do backend
Add-Content .gitignore "`n# Frontend (agora em repositório separado)`ntechmanage-frontend/"

# Commit no backend
git add .gitignore
git commit -m "chore: remove frontend from backend repository"
```

#### 4. Remover Frontend do Diretório do Backend

```powershell
# CUIDADO: Certifique-se de que a cópia foi feita com sucesso!
cd C:\Users\000491631\Documents\TechManage
Remove-Item -Recurse -Force techmanage-frontend
```

## 📁 Estrutura Final dos Projetos

### Backend (TechManage)

```
TechManage/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── techmanage/
│   │   │           ├── controller/
│   │   │           ├── service/
│   │   │           ├── repository/
│   │   │           ├── entity/
│   │   │           ├── exception/
│   │   │           └── config/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
├── pom.xml
├── .gitignore
└── README.md
```

### Frontend (techmanage-frontend)

```
techmanage-frontend/
├── src/
│   ├── app/
│   │   ├── core/
│   │   ├── features/
│   │   ├── shared/
│   │   ├── app.component.*
│   │   ├── app.module.ts
│   │   └── app-routing.module.ts
│   ├── assets/
│   ├── environments/
│   └── styles.scss
├── node_modules/
├── angular.json
├── package.json
├── tsconfig.json
├── .gitignore
└── README.md
```

## 🔧 Configuração de Comunicação

### Backend - CORS Configuration

O backend já deve estar configurado em `WebConfig.java`:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### Frontend - API Configuration

Verificar `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## 🏃 Como Executar os Projetos

### Backend (Porta 8080)

```bash
cd C:\Users\000491631\Documents\TechManage

# Opção 1: Maven
mvn spring-boot:run

# Opção 2: Maven Wrapper
./mvnw spring-boot:run

# API disponível em: http://localhost:8080
```

### Frontend (Porta 4200)

```bash
cd C:\Users\000491631\Documents\techmanage-frontend

# Instalar dependências (primeira vez)
npm install

# Executar
npm start

# Aplicação disponível em: http://localhost:4200
```

## 📚 Criar Repositórios Git Remotos

### GitHub/GitLab

1. **Criar repositórios no GitHub:**
   - `TechManage-Backend`
   - `TechManage-Frontend`

2. **Conectar Backend:**
   ```bash
   cd C:\Users\000491631\Documents\TechManage
   git remote add origin https://github.com/seu-usuario/TechManage-Backend.git
   git branch -M main
   git push -u origin main
   ```

3. **Conectar Frontend:**
   ```bash
   cd C:\Users\000491631\Documents\techmanage-frontend
   git remote add origin https://github.com/seu-usuario/TechManage-Frontend.git
   git branch -M main
   git push -u origin main
   ```

## ✅ Checklist de Verificação

Após a separação, verifique:

- [ ] Frontend copiado para `Documents/techmanage-frontend`
- [ ] Git inicializado no frontend
- [ ] `npm install` executado com sucesso no frontend
- [ ] `.gitignore` do backend atualizado
- [ ] Frontend removido do diretório do backend
- [ ] Backend executa corretamente (`mvn spring-boot:run`)
- [ ] Frontend executa corretamente (`npm start`)
- [ ] Comunicação entre frontend e backend funcionando
- [ ] Repositórios Git remotos criados e conectados

## 🔄 Vantagens da Nova Estrutura

✅ **Independência:**
- Cada projeto tem seu próprio ciclo de vida
- Versionamento independente
- Deploy independente

✅ **Organização:**
- Código mais organizado
- Mais fácil de navegar
- Responsabilidades bem definidas

✅ **Performance:**
- Builds mais rápidos
- Clone de repositório mais rápido
- CI/CD otimizado

✅ **Escalabilidade:**
- Equipes podem trabalhar separadamente
- Facilita microserviços no futuro
- Melhor para projetos grandes

## 🆘 Troubleshooting

### Erro: "Git not found"
```bash
# Instalar Git: https://git-scm.com/download/win
```

### Erro: "npm not found"
```bash
# Instalar Node.js: https://nodejs.org/
```

### Erro: "CORS" ao conectar frontend e backend
```bash
# Verificar WebConfig.java no backend
# Verificar se ambos estão rodando (8080 e 4200)
```

### Frontend não conecta ao backend
```bash
# Verificar environment.ts
# Verificar se backend está rodando em localhost:8080
# Verificar console do navegador para erros
```

## 📞 Suporte

Para dúvidas sobre:
- **Backend**: Consulte `TechManage/README.md`
- **Frontend**: Consulte `techmanage-frontend/README.md`

---

**Data de Criação:** 10/10/2025
**Versão:** 1.0
