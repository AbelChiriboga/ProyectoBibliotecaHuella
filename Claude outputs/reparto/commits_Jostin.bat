@echo off
chcp 65001 >nul
rem ============================================================
rem  Commits de Jostin (30 archivos, un commit por archivo)
rem  1) Cambia las dos rutas de abajo.
rem  2) Antes de correrlo, revisa que git tenga TU nombre y correo:
rem       git config user.name   /   git config user.email
rem  3) Doble clic a este archivo.
rem ============================================================
set "ORIGEN=D:\ruta\del\proyecto\BibliotecaHuella"
set "DESTINO=D:\ruta\del\repo\nuevo"

cd /d "%DESTINO%" || (echo No existe la carpeta DESTINO & pause & exit /b)
git pull --rebase

call :subir "src\servicio\AutorService.java" "feat(servicio): agregar AutorService"
call :subir "src\servicio\CategoriaService.java" "feat(servicio): agregar CategoriaService"
call :subir "src\servicio\LibroService.java" "feat(servicio): agregar LibroService"
call :subir "src\servicio\EjemplarService.java" "feat(servicio): agregar EjemplarService"
call :subir "src\servicio\EjemplaresDeLibro.java" "feat(servicio): agregar EjemplaresDeLibro"
call :subir "src\servicio\EjemplaresGuardados.java" "feat(servicio): agregar EjemplaresGuardados"
call :subir "src\servicio\EjemplaresPendientes.java" "feat(servicio): agregar EjemplaresPendientes"
call :subir "src\servicio\EstudianteService.java" "feat(servicio): agregar EstudianteService"
call :subir "src\servicio\AccionRetiro.java" "feat(servicio): agregar AccionRetiro"
call :subir "src\servicio\FiltroEstudiante.java" "feat(servicio): agregar FiltroEstudiante"
call :subir "src\servicio\SituacionEstudiante.java" "feat(servicio): agregar SituacionEstudiante"
call :subir "src\servicio\UsuarioService.java" "feat(servicio): agregar UsuarioService"
call :subir "src\servicio\AutenticacionService.java" "feat(servicio): agregar AutenticacionService"
call :subir "src\servicio\BiometriaService.java" "feat(servicio): agregar BiometriaService"
call :subir "src\servicio\FichaEstudiante.java" "feat(servicio): agregar FichaEstudiante"
call :subir "src\servicio\AtencionConHuella.java" "feat(servicio): agregar AtencionConHuella"
call :subir "src\servicio\PrestamoService.java" "feat(servicio): agregar PrestamoService"
call :subir "src\servicio\ResumenDevolucion.java" "feat(servicio): agregar ResumenDevolucion"
call :subir "src\servicio\DevolucionService.java" "feat(servicio): agregar DevolucionService"
call :subir "src\servicio\TipoAtraso.java" "feat(servicio): agregar TipoAtraso"
call :subir "src\servicio\AtrasoService.java" "feat(servicio): agregar AtrasoService"
call :subir "src\servicio\ParametroEditable.java" "feat(servicio): agregar ParametroEditable"
call :subir "src\servicio\ParametroService.java" "feat(servicio): agregar ParametroService"
call :subir "src\servicio\BitacoraService.java" "feat(servicio): agregar BitacoraService"
call :subir "src\servicio\ResumenBitacora.java" "feat(servicio): agregar ResumenBitacora"
call :subir "src\servicio\IndicadorService.java" "feat(servicio): agregar IndicadorService"
call :subir "src\servicio\reglas\ReglaPrestamo.java" "feat(reglas): agregar ReglaPrestamo"
call :subir "src\servicio\reglas\EstudianteActivo.java" "feat(reglas): agregar EstudianteActivo"
call :subir "src\servicio\reglas\LimitePrestamos.java" "feat(reglas): agregar LimitePrestamos"
call :subir "src\servicio\reglas\SinMultasPendientes.java" "feat(reglas): agregar SinMultasPendientes"

git pull --rebase
git push
echo.
echo Listo: 30 commits de Jostin.
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
