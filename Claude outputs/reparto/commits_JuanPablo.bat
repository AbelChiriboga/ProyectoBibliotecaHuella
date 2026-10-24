@echo off
chcp 65001 >nul
rem ============================================================
rem  Commits de Juan Pablo (30 archivos, un commit por archivo)
rem  1) Cambia las dos rutas de abajo.
rem  2) Antes de correrlo, revisa que git tenga TU nombre y correo:
rem       git config user.name   /   git config user.email
rem  3) Doble clic a este archivo.
rem ============================================================
set "ORIGEN=D:\ruta\del\proyecto\BibliotecaHuella"
set "DESTINO=D:\ruta\del\repo\nuevo"

cd /d "%DESTINO%" || (echo No existe la carpeta DESTINO & pause & exit /b)
git pull --rebase

call :subir "src\imagenes\Exit.png" "feat(imagenes): agregar icono Exit.png"
call :subir "src\imagenes\Historial.png" "feat(imagenes): agregar icono Historial.png"
call :subir "src\imagenes\Invetario.png" "feat(imagenes): agregar icono Invetario.png"
call :subir "src\imagenes\Parametros.png" "feat(imagenes): agregar icono Parametros.png"
call :subir "src\imagenes\Prestamo.png" "feat(imagenes): agregar icono Prestamo.png"
call :subir "src\imagenes\Regresar.png" "feat(imagenes): agregar icono Regresar.png"
call :subir "src\imagenes\filtro.png" "feat(imagenes): agregar icono filtro.png"
call :subir "src\imagenes\libros.png" "feat(imagenes): agregar icono libros.png"
call :subir "src\imagenes\logo.png" "feat(imagenes): agregar icono logo.png"
call :subir "src\componentes\PanelGrupo.java" "feat(componentes): agregar PanelGrupo"
call :subir "src\componentes\PanelScroll.java" "feat(componentes): agregar PanelScroll"
call :subir "src\componentes\PanelFormulario.java" "feat(componentes): agregar PanelFormulario"
call :subir "src\componentes\Encabezado.java" "feat(componentes): agregar Encabezado"
call :subir "src\componentes\PiePagina.java" "feat(componentes): agregar PiePagina"
call :subir "src\componentes\VentanaBase.java" "feat(componentes): agregar VentanaBase"
call :subir "src\componentes\BotonSistema.java" "feat(componentes): agregar BotonSistema"
call :subir "src\componentes\Aviso.java" "feat(componentes): agregar Aviso"
call :subir "src\componentes\Opcion.java" "feat(componentes): agregar Opcion"
call :subir "src\componentes\Combos.java" "feat(componentes): agregar Combos"
call :subir "src\componentes\Tablas.java" "feat(componentes): agregar Tablas"
call :subir "src\componentes\TablaObjetos.java" "feat(componentes): agregar TablaObjetos"
call :subir "src\componentes\BarraBusqueda.java" "feat(componentes): agregar BarraBusqueda"
call :subir "src\componentes\ControlCrud.java" "feat(componentes): agregar ControlCrud"
call :subir "src\componentes\Formulario.java" "feat(componentes): agregar Formulario"
call :subir "src\componentes\TarjetaIndicador.java" "feat(componentes): agregar TarjetaIndicador"
call :subir "src\componentes\GraficoBarras.java" "feat(componentes): agregar GraficoBarras"
call :subir "src\componentes\PanelHuella.java" "feat(componentes): agregar PanelHuella"
call :subir "src\servicio\Sesion.java" "feat(servicio): agregar Sesion"
call :subir "src\servicio\ServicioCrud.java" "feat(servicio): agregar ServicioCrud"
call :subir "src\servicio\CedulaUnica.java" "feat(servicio): agregar CedulaUnica"

git pull --rebase
git push
echo.
echo Listo: 30 commits de Juan Pablo.
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
