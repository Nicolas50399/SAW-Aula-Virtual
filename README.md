# TP Seguridad en Aplicaciones Web - 2C2025

Aplicación de aula virtual de un centro educativo (similar al campus de la UTN).

Roles que intervienen:
* Estudiantes: Visualizan sus cursos y el material de los mismos.
* Profesores: Cargan alumnos a cursos, junto con el material de los mismos.
* Administradores: Gestionan el sistema (mayor privilegio).

## Vulnerailidad 1: Identification & Authentication failures
La aplicación expone accidentalmente un endpoint de debugging sin autenticación (/actuator/env) que devuelve variables de entorno bajo el contenido de un archivo de configuración (application.properties).

En ese archivo aparecen, entre otros, propiedades de configuración criptográfica (algoritmo de hash, salt utilizado) y rutas/URIs a backups de usuarios.

## Vulnerabilidad 2: Cryptographic Failures
Usando la información divulgada se accede (lectura) a un backup de la base de datos de usuarios y se recuperan usuarios y contraseñas hasheadas.

Habiendo obtenido anteriormente el algoritmo de hash y SALT utilizado, es posible realizar ataques offline (diccionario/fuerza bruta o, en caso de cifrado reversible, descifrado) sobre las credenciales de acceso obtenidas.

Finalmente, se obtiene el texto descifrado y se logra un login exitoso en el sistema.

## Vulnerailidad 3: Broken access control
El control de visibilidad de secciones sensibles se implementa exclusivamente en el frontend (mediante JavaScript).

El usuario (no admin) autenticado obtenido anteriormente puede modificar el código cliente o las cookies para mostrar secciones restringidas y acceder a funcionalidades no autorizadas.

## Vulnerabilidad 4: SQL Injections
Se descubre que en los formularios ocultos no se valida/sanea la entrada, permitiendo inyecciones SQL que permiten el acceso a la BD relacional utilizada en producción.

## Linea de tiempo de vulnerabilidades
![vulnerabilidades](./images/vulnerabilidades.png)
