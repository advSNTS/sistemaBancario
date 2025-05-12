DROP TABLE IF EXISTS cuentas_bancarias;
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

CREATE TABLE IF NOT EXISTS cuentas_bancarias (
                                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                                 usuario_id INT NOT NULL,
                                                 tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('Ahorro', 'Corriente')),
    saldo DECIMAL(10,2) NOT NULL DEFAULT 0,
    limite_alerta DECIMAL(10,2) DEFAULT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
    );

