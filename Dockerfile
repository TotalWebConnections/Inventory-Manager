FROM openjdk:8-alpine

COPY target/uberjar/inventory-manager.jar /inventory-manager/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/inventory-manager/app.jar"]
