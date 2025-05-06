DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100),
                          correo VARCHAR(100),
                          clave VARCHAR(100),
                          pregunta_secreta VARCHAR(255),
                          respuesta_secreta VARCHAR(255),
                          cedula VARCHAR(50)
);
