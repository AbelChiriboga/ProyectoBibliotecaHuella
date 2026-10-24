@echo off
chcp 65001 >nul
rem ============================================================
rem  Commits de Vinicio (30 archivos, un commit por archivo)
rem  1) Cambia las dos rutas de abajo.
rem  2) Antes de correrlo, revisa que git tenga TU nombre y correo:
rem       git config user.name   /   git config user.email
rem  3) Doble clic a este archivo.
rem ============================================================
set "ORIGEN=D:\ruta\del\proyecto\BibliotecaHuella"
set "DESTINO=D:\ruta\del\repo\nuevo"

cd /d "%DESTINO%" || (echo No existe la carpeta DESTINO & pause & exit /b)
git pull --rebase

call :subir ".gitignore" "chore: agregar .gitignore"
call :subir "README.md" "docs: agregar README"
call :subir "build.xml" "build: agregar build.xml"
call :subir "manifest.mf" "build: agregar manifest.mf"
call :subir "nbproject\project.properties" "build(nbproject): agregar project.properties"
call :subir "nbproject\project.xml" "build(nbproject): agregar project.xml"
call :subir "nbproject\build-impl.xml" "build(nbproject): agregar build-impl.xml"
call :subir "nbproject\genfiles.properties" "build(nbproject): agregar genfiles.properties"
call :subir "src\conexion\Conexion.java" "feat(conexion): agregar Conexion"
call :subir "src\excepciones\BibliotecaException.java" "feat(excepciones): agregar BibliotecaException"
call :subir "src\excepciones\Reaccion.java" "feat(excepciones): agregar Reaccion"
call :subir "src\excepciones\DatosException.java" "feat(excepciones): agregar DatosException"
call :subir "src\excepciones\ValidacionException.java" "feat(excepciones): agregar ValidacionException"
call :subir "src\excepciones\NegocioException.java" "feat(excepciones): agregar NegocioException"
call :subir "src\excepciones\LectorException.java" "feat(excepciones): agregar LectorException"
call :subir "src\excepciones\SinDedoException.java" "feat(excepciones): agregar SinDedoException"
call :subir "src\excepciones\HuellaNoReconocidaException.java" "feat(excepciones): agregar HuellaNoReconocidaException"
call :subir "src\excepciones\HuellaDuplicadaException.java" "feat(excepciones): agregar HuellaDuplicadaException"
call :subir "src\util\Validaciones.java" "feat(util): agregar Validaciones"
call :subir "src\util\Seguridad.java" "feat(util): agregar Seguridad"
call :subir "src\util\Formatos.java" "feat(util): agregar Formatos"
call :subir "src\util\Mensajes.java" "feat(util): agregar Mensajes"
call :subir "src\util\Estilos.java" "feat(util): agregar Estilos"
call :subir "src\util\Eventos.java" "feat(util): agregar Eventos"
call :subir "src\util\Arrastre.java" "feat(util): agregar Arrastre"
call :subir "src\util\Navegacion.java" "feat(util): agregar Navegacion"
call :subir "src\util\Tareas.java" "feat(util): agregar Tareas"
call :subir "src\util\TipoEntrada.java" "feat(util): agregar TipoEntrada"
call :subir "src\util\FiltroEntrada.java" "feat(util): agregar FiltroEntrada"
call :subir "src\util\Enter.java" "feat(util): agregar Enter"

git pull --rebase
git push
echo.
echo Listo: 30 commits de Vinicio.
pause
exit /b

:subir
if not exist "%ORIGEN%\%~1" (echo FALTA %~1 & exit /b)
if not exist "%~dp1" mkdir "%~dp1"
copy /Y "%ORIGEN%\%~1" "%~1" >nul
git add -f "%~1"
git commit -q -m "%~2"
echo   %~2
exit /b
