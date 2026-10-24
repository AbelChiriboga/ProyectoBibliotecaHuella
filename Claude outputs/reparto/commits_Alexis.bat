@echo off
chcp 65001 >nul
rem ============================================================
rem  Commits de Alexis (30 archivos, un commit por archivo)
rem  1) Cambia las dos rutas de abajo.
rem  2) Antes de correrlo, revisa que git tenga TU nombre y correo:
rem       git config user.name   /   git config user.email
rem  3) Doble clic a este archivo.
rem ============================================================
set "ORIGEN=D:\ruta\del\proyecto\BibliotecaHuella"
set "DESTINO=D:\ruta\del\repo\nuevo"

cd /d "%DESTINO%" || (echo No existe la carpeta DESTINO & pause & exit /b)
git pull --rebase

call :subir "src\biometria\LectorBase.java" "feat(biometria): agregar LectorBase"
call :subir "src\biometria\LectorSimulado.java" "feat(biometria): agregar LectorSimulado"
call :subir "src\biometria\LectorZKTeco.java" "feat(biometria): agregar LectorZKTeco"
call :subir "src\biometria\FabricaLector.java" "feat(biometria): agregar FabricaLector"
call :subir "src\biometria\MonitorLector.java" "feat(biometria): agregar MonitorLector"
call :subir "src\dao\AccesoBD.java" "feat(dao): agregar AccesoBD"
call :subir "src\dao\ConsultaDAO.java" "feat(dao): agregar ConsultaDAO"
call :subir "src\dao\CrudDAO.java" "feat(dao): agregar CrudDAO"
call :subir "src\dao\BaseDAO.java" "feat(dao): agregar BaseDAO"
call :subir "src\dao\CarreraDAO.java" "feat(dao): agregar CarreraDAO"
call :subir "src\dao\AutorDAO.java" "feat(dao): agregar AutorDAO"
call :subir "src\dao\CategoriaDAO.java" "feat(dao): agregar CategoriaDAO"
call :subir "src\dao\LibroDAO.java" "feat(dao): agregar LibroDAO"
call :subir "src\dao\EjemplarDAO.java" "feat(dao): agregar EjemplarDAO"
call :subir "src\dao\EstudianteDAO.java" "feat(dao): agregar EstudianteDAO"
call :subir "src\dao\HuellaDAO.java" "feat(dao): agregar HuellaDAO"
call :subir "src\dao\UsuarioDAO.java" "feat(dao): agregar UsuarioDAO"
call :subir "src\dao\PrestamoDAO.java" "feat(dao): agregar PrestamoDAO"
call :subir "src\dao\MultaDAO.java" "feat(dao): agregar MultaDAO"
call :subir "src\dao\ParametroDAO.java" "feat(dao): agregar ParametroDAO"
call :subir "src\dao\RegistroAutenticacionDAO.java" "feat(dao): agregar RegistroAutenticacionDAO"
call :subir "src\dao\IndicadorDAO.java" "feat(dao): agregar IndicadorDAO"
call :subir "src\dao\ReporteDAO.java" "feat(dao): agregar ReporteDAO"
call :subir "src\imagenes\Atrasado.png" "feat(imagenes): agregar icono Atrasado.png"
call :subir "src\imagenes\Bibliotecarios.png" "feat(imagenes): agregar icono Bibliotecarios.png"
call :subir "src\imagenes\Bitacora.png" "feat(imagenes): agregar icono Bitacora.png"
call :subir "src\imagenes\Buscar.png" "feat(imagenes): agregar icono Buscar.png"
call :subir "src\imagenes\Cerrar.png" "feat(imagenes): agregar icono Cerrar.png"
call :subir "src\imagenes\Devolver.png" "feat(imagenes): agregar icono Devolver.png"
call :subir "src\imagenes\Estudiantes.png" "feat(imagenes): agregar icono Estudiantes.png"

git pull --rebase
git push
echo.
echo Listo: 30 commits de Alexis.
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
