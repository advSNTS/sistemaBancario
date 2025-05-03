CREATE TABLE IF NOT EXISTS usuarios (
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
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS prestamos (
   id INT AUTO_INCREMENT PRIMARY KEY,
   usuario_id INT NOT NULL,
   monto DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    tasa_interes DECIMAL(5,2) NOT NULL CHECK (tasa_interes > 0),
    plazo_meses INT NOT NULL CHECK (plazo_meses > 0),
    saldo_pendiente DECIMAL(10,2) NOT NULL CHECK (saldo_pendiente >= 0),
    fecha_aprobacion DATE NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transferencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cuenta_origen_id INT NOT NULL,
    cuenta_destino_id INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    fecha TIMESTAMP NOT NULL,
    FOREIGN KEY (cuenta_origen_id) REFERENCES cuentas_bancarias(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES cuentas_bancarias(id)
);

