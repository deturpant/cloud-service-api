FROM adoptopenjdk/openjdk17:alpine-jre

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл собранного приложения внутрь контейнера
COPY build/libs/*.jar app.jar

# Команда для запуска Spring Boot приложения при старте контейнера
CMD ["java", "-jar", "app.jar"]
