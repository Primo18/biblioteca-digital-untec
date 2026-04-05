-- ============================================
-- Esquema de base de datos: Biblioteca Digital UNTEC
-- Base de datos: H2 (uso educativo)
-- ============================================

CREATE TABLE IF NOT EXISTS usuarios (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    rol        VARCHAR(20)  NOT NULL DEFAULT 'ESTUDIANTE' -- ADMIN o ESTUDIANTE
);

CREATE TABLE IF NOT EXISTS libros (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    titulo       VARCHAR(200) NOT NULL,
    autor        VARCHAR(100) NOT NULL,
    isbn         VARCHAR(20)  NOT NULL UNIQUE,
    anio         INT,
    disponible   BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS prestamos (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id      INT NOT NULL,
    libro_id        INT NOT NULL,
    fecha_prestamo  DATE NOT NULL,
    fecha_devolucion DATE,
    devuelto        BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (libro_id)   REFERENCES libros(id)
);

-- Datos iniciales
INSERT INTO usuarios (nombre, email, password, rol) VALUES
    ('Administrador', 'admin@untec.edu', 'admin123', 'ADMIN'),
    ('Juan Estudiante', 'juan@untec.edu', 'juan123', 'ESTUDIANTE');

INSERT INTO libros (titulo, autor, isbn, anio, disponible) VALUES
    ('Clean Code', 'Robert C. Martin', '9780132350884', 2008, TRUE),
    ('The Pragmatic Programmer', 'David Thomas', '9780135957059', 2019, TRUE),
    ('Design Patterns', 'Gang of Four', '9780201633610', 1994, TRUE),
    ('Java: The Complete Reference', 'Herbert Schildt', '9781260440232', 2019, TRUE),
    ('Introduction to Algorithms', 'Cormen et al.', '9780262033848', 2009, FALSE);
