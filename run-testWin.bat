@echo off
chcp 65001 > nul
cls

echo =========================================
echo     Запуск автотестов Report Portal
echo =========================================
echo.

REM Проверка наличия Java
echo [1] Проверка Java...
where java >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ Java не установлена. Установите Java 11+
    echo Скачайте с: https://adoptium.net/
    pause
    exit /b 1
)
java -version 2>&1 | findstr "version"
echo ✅ Java найдена
echo.

REM Проверка наличия Maven
echo [2] Проверка Maven...
where mvn >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ Maven не установлен. Установите Maven 3.6+
    echo Скачайте с: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
mvn -v | findstr "Apache Maven"
echo ✅ Maven найден
echo.

REM Проверка наличия Chrome
echo [3] Проверка Chrome...
reg query "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\chrome.exe" >nul 2>nul
if %ERRORLEVEL% equ 0 (
    echo ✅ Chrome найден
) else (
    reg query "HKEY_CURRENT_USER\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\chrome.exe" >nul 2>nul
    if %ERRORLEVEL% equ 0 (
        echo ✅ Chrome найден
    ) else (
        echo ⚠️  Chrome не найден. Установите Chrome для тестов
        echo    Скачайте с: https://www.google.com/chrome/
    )
)
echo.

REM Очистка и компиляция
echo [4] Очистка и компиляция проекта...
call mvn clean compile -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Ошибка компиляции проекта
    pause
    exit /b 1
)
echo ✅ Проект скомпилирован успешно
echo.

echo 📋 ДОСТУПНЫЕ ТЕСТЫ:
echo    1. Тест 1: Негативный тест на логин
echo    2. Тест 2: Позитивный тест на логин
echo    3. Тест 3: Создание нового Widget
echo    4. Все тесты
echo.

set /p choice="Выберите вариант (1-4): "

if "%choice%"=="1" (
    echo 🧪 Запуск Теста 1: Негативный тест на логин...
    call mvn test -Dtest=LoginNegativeTest
    goto :results
)

if "%choice%"=="2" (
    echo 🧪 Запуск Теста 2: Позитивный тест на логин...
    call mvn test -Dtest=LoginPositiveTest
    goto :results
)

if "%choice%"=="3" (
    echo 🧪 Запуск Теста 3: Создание нового Widget...
    call mvn test -Dtest=CreateWidgetTest
    goto :results
)

if "%choice%"=="4" (
    echo 🧪 Запуск всех тестов...
    call mvn test
    goto :results
)

echo ❌ Неверный выбор. Выход.
pause
exit /b 1

:results
echo.
echo =========================================
if %ERRORLEVEL% equ 0 (
    echo ✅ ТЕСТЫ УСПЕШНО ВЫПОЛНЕНЫ!
) else (
    echo ❌ ТЕСТЫ ЗАВЕРШИЛИСЬ С ОШИБКОЙ!
)
echo =========================================
echo.
echo 📊 Результаты тестов: target/surefire-reports/
echo 📸 Скриншоты ошибок: screenshots/
echo.

pause