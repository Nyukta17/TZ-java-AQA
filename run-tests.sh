#!/bin/bash

# Script: run-tests.sh
# Description: Запуск автотестов для Report Portal
# Requirements: Java 11+, Maven 3.6+, Chrome Browser

set -e  # Выход при ошибке

echo "========================================="
echo "   Запуск автотестов Report Portal"
echo "========================================="

# Проверка наличия Java
echo "🔍 Проверка Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java не установлена. Установите Java 11+"
    exit 1
fi
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "✅ Java версия: $java_version"

# Проверка наличия Maven
echo "🔍 Проверка Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven не установлен. Установите Maven 3.6+"
    exit 1
fi
mvn_version=$(mvn -v | grep "Apache Maven" | cut -d' ' -f3)
echo "✅ Maven версия: $mvn_version"

# Проверка наличия Chrome
echo "🔍 Проверка Chrome..."
if ! command -v google-chrome &> /dev/null && ! command -v chromium-browser &> /dev/null; then
    echo "⚠️  Chrome/Chromium не найден. Установите Chrome для тестов"
    echo "   sudo apt-get install google-chrome-stable"
else
    echo "✅ Chrome/Chromium найден"
fi

# Очистка и компиляция
echo "🚀 Очистка и компиляция проекта..."
mvn clean compile

echo ""
echo "📋 Доступные тесты:"
echo "   1. Тест 1: Негативный тест на логин"
echo "   2. Тест 2: Позитивный тест на логин"
echo "   3. Тест 3: Создание нового Widget"
echo "   4. Все тесты"
echo ""

read -p "Выберите вариант (1-4): " choice

case $choice in
    1)
        echo "🧪 Запуск Теста 1: Негативный тест на логин..."
        mvn test -Dtest=LoginNegativeTest
        ;;
    2)
        echo "🧪 Запуск Теста 2: Позитивный тест на логин..."
        mvn test -Dtest=LoginPositiveTest
        ;;
    3)
        echo "🧪 Запуск Теста 3: Создание нового Widget..."
        mvn test -Dtest=CreateWidgetTest
        ;;
    4)
        echo "🧪 Запуск всех тестов..."
        mvn test
        ;;
    *)
        echo "❌ Неверный выбор. Выход."
        exit 1
        ;;
esac

echo ""
echo "========================================="
echo "   Тесты завершены!"
echo "========================================="