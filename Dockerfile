
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21-al2023 AS builder
WORKDIR /build

# Install minimal tools
RUN yum install -y tar gzip unzip && yum clean all

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# ---- Runtime stage ----
#FROM eclipse-temurin:21-jre-alpine
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

