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
    limite_alerta DECIMAL(10,2) DEFAULT NULL,
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

CREATE TABLE IF NOT EXISTS pagos_programados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cuenta_origen_id INT NOT NULL,
    cuenta_destino_id INT NOT NULL,
    monto DOUBLE NOT NULL,
    fecha_ejecucion TIMESTAMP NOT NULL,
    ejecutado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cuenta_origen_id) REFERENCES cuentas_bancarias(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES cuentas_bancarias(id)
);

CREATE TABLE IF NOT EXISTS tarjetas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    numero VARCHAR(25) NOT NULL,
    fecha_vencimiento VARCHAR(10) NOT NULL,
    cvv VARCHAR(5) NOT NULL,
    cupo_total DOUBLE NOT NULL,
    cupo_disponible DOUBLE NOT NULL,
    deuda_actual DOUBLE NOT NULL DEFAULT 0,
    activa BOOLEAN NOT NULL DEFAULT FALSE,
    bloqueada BOOLEAN NOT NULL DEFAULT FALSE,
    cuenta_asociada_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (cuenta_asociada_id) REFERENCES cuentas_bancarias(id)
);


CREATE TABLE IF NOT EXISTS inversiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cuenta_id INT NOT NULL,
    monto DOUBLE NOT NULL,
    plazo_meses INT NOT NULL,
    interes DOUBLE NOT NULL,
    fecha_inicio DATE NOT NULL,
    finalizada BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas_bancarias(id)
);