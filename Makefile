.PHONY: run dev pg_up pg_down pg_logs package run_jar zip clean

run:
	@echo "Running application..."
	./mvnw spring-boot:run

dev:
	@echo "Running application in dev profile..."
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

pg_up:
	@echo "Starting postgres container..."
	docker compose up -d

pg_down:
	@echo "Stopping postgres container..."
	docker compose down

pg_logs:
	docker compose logs -f

package:
	@echo "Packaging application (runnable jar)"
	./mvnw package

run_jar:
	@echo "Running packaged jar: target/smessenger.jar"
	java -jar target/smessenger.jar

zip:
	zip -r smessenger_arch.zip . -x "target/*" ".git/*" ".git*" ".vscode"

clean:
	@echo "Cleaning build artifacts..."
	./mvnw clean
