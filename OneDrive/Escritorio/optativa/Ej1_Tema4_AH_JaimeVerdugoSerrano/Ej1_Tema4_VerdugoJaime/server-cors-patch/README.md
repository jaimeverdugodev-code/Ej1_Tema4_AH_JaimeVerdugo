Instrucciones para habilitar CORS en tu Spring Boot (código de ejemplo)

Estos archivos son ejemplos que puedes copiar al proyecto Spring Boot que desplegaste en Render.

Pasos:
1. Abre tu proyecto Spring Boot (el repositorio que Render usa).
2. Copia uno de los archivos Java de este directorio a `src/main/java/<tu/package>/`.
3. Ajusta la declaración de `package` en la parte superior del archivo a tu paquete real (p. ej. `com.tuempresa.camisas`).
4. Si usas Spring Security, copia `SecurityConfig.java`; si no, copia `WebConfig.java`.
5. Haz commit y push al repositorio; Render redeplegará.

Notas de seguridad:
- En desarrollo puedes usar `http://localhost:8100` como origen permitido.
- En producción evita `*` y especifica dominios concretos.
