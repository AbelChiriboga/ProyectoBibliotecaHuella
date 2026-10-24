@echo off
chcp 65001 >nul
rem ============================================================
rem  Commits de Damian (30 archivos, un commit por archivo)
rem  1) Cambia las dos rutas de abajo.
rem  2) Antes de correrlo, revisa que git tenga TU nombre y correo:
rem       git config user.name   /   git config user.email
rem  3) Doble clic a este archivo.
rem ============================================================
set "ORIGEN=D:\ruta\del\proyecto\BibliotecaHuella"
set "DESTINO=D:\ruta\del\repo\nuevo"

cd /d "%DESTINO%" || (echo No existe la carpeta DESTINO & pause & exit /b)
git pull --rebase

call :subir "src\modelo\ValorBD.java" "feat(modelo): agregar ValorBD"
call :subir "src\modelo\Entidad.java" "feat(modelo): agregar Entidad"
call :subir "src\modelo\Persona.java" "feat(modelo): agregar Persona"
call :subir "src\modelo\Rol.java" "feat(modelo): agregar Rol"
call :subir "src\modelo\Usuario.java" "feat(modelo): agregar Usuario"
call :subir "src\modelo\Administrador.java" "feat(modelo): agregar Administrador"
call :subir "src\modelo\Bibliotecario.java" "feat(modelo): agregar Bibliotecario"
call :subir "src\modelo\Carrera.java" "feat(modelo): agregar Carrera"
call :subir "src\modelo\EstadoEstudiante.java" "feat(modelo): agregar EstadoEstudiante"
call :subir "src\modelo\Dedo.java" "feat(modelo): agregar Dedo"
call :subir "src\modelo\Huella.java" "feat(modelo): agregar Huella"
call :subir "src\modelo\Estudiante.java" "feat(modelo): agregar Estudiante"
call :subir "src\modelo\Autor.java" "feat(modelo): agregar Autor"
call :subir "src\modelo\Categoria.java" "feat(modelo): agregar Categoria"
call :subir "src\modelo\Libro.java" "feat(modelo): agregar Libro"
call :subir "src\modelo\EstadoEjemplar.java" "feat(modelo): agregar EstadoEjemplar"
call :subir "src\modelo\Ejemplar.java" "feat(modelo): agregar Ejemplar"
call :subir "src\modelo\EstadoPrestamo.java" "feat(modelo): agregar EstadoPrestamo"
call :subir "src\modelo\Multa.java" "feat(modelo): agregar Multa"
call :subir "src\modelo\Prestamo.java" "feat(modelo): agregar Prestamo"
call :subir "src\modelo\Parametro.java" "feat(modelo): agregar Parametro"
call :subir "src\modelo\OperacionAutenticacion.java" "feat(modelo): agregar OperacionAutenticacion"
call :subir "src\modelo\ResultadoAutenticacion.java" "feat(modelo): agregar ResultadoAutenticacion"
call :subir "src\modelo\RegistroAutenticacion.java" "feat(modelo): agregar RegistroAutenticacion"
call :subir "src\modelo\Indicador.java" "feat(modelo): agregar Indicador"
call :subir "src\biometria\EstadoLector.java" "feat(biometria): agregar EstadoLector"
call :subir "src\biometria\AvisoCaptura.java" "feat(biometria): agregar AvisoCaptura"
call :subir "src\biometria\Coincidencia.java" "feat(biometria): agregar Coincidencia"
call :subir "src\biometria\LectorBiometrico.java" "feat(biometria): agregar LectorBiometrico"
call :subir "src\biblioteca\Main.java" "feat(biblioteca): agregar Main"

git pull --rebase
git push
echo.
echo Listo: 30 commits de Damian.
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
