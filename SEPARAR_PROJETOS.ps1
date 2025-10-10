# Script para separar Frontend e Backend do TechManage
# Execute este script no PowerShell como Administrador

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  SEPARACAO DE FRONTEND E BACKEND" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Define os caminhos
$backendDir = "C:\Users\000491631\Documents\TechManage"
$frontendSourceDir = "$backendDir\techmanage-frontend"
$frontendTargetDir = "C:\Users\000491631\Documents\techmanage-frontend"

# Verifica se o diretorio de origem existe
if (-Not (Test-Path $frontendSourceDir)) {
    Write-Host "ERRO: Diretorio do frontend nao encontrado em: $frontendSourceDir" -ForegroundColor Red
    exit 1
}

Write-Host "1. Criando novo diretorio para o frontend..." -ForegroundColor Yellow
New-Item -ItemType Directory -Force -Path $frontendTargetDir | Out-Null

Write-Host "2. Copiando arquivos do frontend (exceto node_modules)..." -ForegroundColor Yellow
# Copia tudo exceto node_modules e .angular/cache
$excludeDirs = @('node_modules', '.angular', 'dist')
Get-ChildItem -Path $frontendSourceDir -Exclude $excludeDirs | ForEach-Object {
    Copy-Item -Path $_.FullName -Destination $frontendTargetDir -Recurse -Force
    Write-Host "   Copiado: $($_.Name)" -ForegroundColor Green
}

Write-Host ""
Write-Host "3. Inicializando repositorio Git no frontend..." -ForegroundColor Yellow
Set-Location $frontendTargetDir
git init
Write-Host "   Git inicializado!" -ForegroundColor Green

Write-Host ""
Write-Host "4. Instalando dependencias do frontend..." -ForegroundColor Yellow
Write-Host "   (Isso pode demorar alguns minutos...)" -ForegroundColor Gray
npm install

Write-Host ""
Write-Host "5. Criando primeiro commit no frontend..." -ForegroundColor Yellow
git add .
git commit -m "Initial commit: TechManage Frontend (Angular)

- Projeto Angular 18
- Angular Material configurado
- Componentes de gerenciamento de usuarios
- Mascara de telefone (ngx-mask)
- Paginacao e busca implementadas"

Write-Host ""
Write-Host "6. Atualizando .gitignore do backend..." -ForegroundColor Yellow
Set-Location $backendDir
$gitignoreBackend = Get-Content "$backendDir\.gitignore" -Raw

# Adiciona exclusao do frontend se nao existir
if ($gitignoreBackend -notmatch "techmanage-frontend") {
    Add-Content -Path "$backendDir\.gitignore" -Value "`n# Frontend (agora em repositorio separado)`ntechmanage-frontend/"
    Write-Host "   .gitignore do backend atualizado!" -ForegroundColor Green
}

Write-Host ""
Write-Host "7. Removendo frontend do diretorio do backend..." -ForegroundColor Yellow
Write-Host "   ATENCAO: Esta acao sera feita MANUALMENTE por seguranca!" -ForegroundColor Red
Write-Host "   Execute: Remove-Item -Recurse -Force '$frontendSourceDir'" -ForegroundColor Yellow

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  SEPARACAO CONCLUIDA!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "PROXIMOS PASSOS:" -ForegroundColor Cyan
Write-Host ""
Write-Host "1. Backend (Spring Boot):" -ForegroundColor Yellow
Write-Host "   Localizacao: $backendDir"
Write-Host "   Para executar: mvn spring-boot:run"
Write-Host ""
Write-Host "2. Frontend (Angular):" -ForegroundColor Yellow
Write-Host "   Localizacao: $frontendTargetDir"
Write-Host "   Para executar: npm start"
Write-Host "   URL: http://localhost:4200"
Write-Host ""
Write-Host "3. Para remover o frontend do backend:" -ForegroundColor Yellow
Write-Host "   cd '$backendDir'"
Write-Host "   Remove-Item -Recurse -Force techmanage-frontend"
Write-Host ""
Write-Host "4. Criar repositorios Git remotos (GitHub):" -ForegroundColor Yellow
Write-Host "   - TechManage-Backend"
Write-Host "   - TechManage-Frontend"
Write-Host ""
