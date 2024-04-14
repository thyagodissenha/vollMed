FROM amazoncorretto:17.0.10
LABEL authors="https://www.linkedin.com/in/thyago-taborda-dissenha/"
COPY target/*.jar vollMed-app.jar
ENTRYPOINT ["java", "-jar", "/vollMed-app.jar"]