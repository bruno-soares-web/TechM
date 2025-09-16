@echo off
echo ========================================
echo     TechManage API - Starting Server
echo ========================================
echo.

echo Configurando JAVA_HOME para JDK 8...
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_202
echo JAVA_HOME: %JAVA_HOME%
echo.

echo Iniciando aplicacao Spring Boot...
echo URL da aplicacao: http://localhost:8080
echo Console H2: http://localhost:8080/h2-console
echo.

mvn spring-boot:run -Dmaven.test.skip=true -Dmaven.compiler.fork=true -Dmaven.compiler.executable="C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe"

pause