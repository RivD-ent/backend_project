-- ####################################################################
-- ## Script de Datos de Prueba para el Proyecto "Urbania" (PostgreSQL)
-- ####################################################################

-- 1. Insertar Amenities (Catálogo)
INSERT INTO amenities (name) VALUES
('Piscina'),
('Gimnasio'),
('Estacionamiento'),
('Balcón'),
('Seguridad 24h'),
('Admite Mascotas')
ON CONFLICT (name) DO NOTHING;

-- 2. Insertar Usuarios
INSERT INTO users (name, email, password, phone_number) VALUES
('Ana García', 'ana.garcia@example.com', 'hashed_password_123', '+51987654321'),
('Carlos Rodriguez', 'carlos.r@example.com', 'hashed_password_123', '+51912345678'),
('Sofia Lopez', 'sofia.lopez@email.com', 'hashed_password_123', '+51998877665')
ON CONFLICT (email) DO NOTHING;

-- 3. Insertar Propiedades (Asociadas a los usuarios)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM properties WHERE title = 'Moderno Apartamento en Miraflores') THEN
        INSERT INTO properties (title, description, price, address, city, operation_type, property_type, bedrooms, bathrooms, area_sq_meters, contact_name, contact_phone, google_maps_url, owner_id) VALUES
        ('Moderno Apartamento en Miraflores', 'Luminoso apartamento con vista al mar, acabados de lujo y excelente ubicación. Ideal para parejas o ejecutivos.', 2100000.00, 'Av. Larco 123, Miraflores', 'Lima', 'SALE', 'APARTMENT', 2, 2, 95, 'Ana García', '+51987654321', 'https://maps.app.goo.gl/abcdef123456', 1),
        ('Casa Familiar con Jardín en La Molina', 'Amplia casa de dos pisos con un hermoso jardín trasero, perfecta para una familia. Zona tranquila y segura.', 4500.00, 'Calle Las Palmeras 456, La Molina', 'Lima', 'RENTAL', 'HOUSE', 4, 3, 220, 'Carlos Rodriguez', '+51912345678', 'https://maps.app.goo.gl/ghijkl789012', 2),
        ('Oficina Céntrica en San Isidro', 'Oficina bien iluminada en el corazón del centro financiero de San Isidro. Lista para ocupar.', 3800.00, 'Av. Javier Prado Este 789, San Isidro', 'Lima', 'RENTAL', 'OFFICE', 0, 1, 60, 'Ana García', '+51987654321', 'https://maps.app.goo.gl/mnopqr345678', 1),
        ('Loft de Diseño en Barranco', 'Espectacular loft con diseño industrial, techos altos y a pasos de los mejores restaurantes y galerías de arte.', 1850000.00, 'Jr. Batalla de Junín 101, Barranco', 'Lima', 'SALE', 'APARTMENT', 1, 2, 110, 'Sofia Lopez', '+51998877665', 'https://maps.app.goo.gl/stuvwxyz901234', 3);
    END IF;
END $$;

-- 4. Insertar Imágenes para las Propiedades
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/007bff/white?text=Sala') THEN
        INSERT INTO property_images (property_id, image_url, description) VALUES
        (1, 'https://placehold.co/800x600/007bff/white?text=Sala', 'Vista de la sala de estar'),
        (1, 'https://placehold.co/800x600/28a745/white?text=Dormitorio', 'Dormitorio principal'),
        (1, 'https://placehold.co/800x600/dc3545/white?text=Cocina', 'Cocina moderna'),
        (2, 'https://placehold.co/800x600/ffc107/black?text=Fachada', 'Fachada de la casa'),
        (2, 'https://placehold.co/800x600/17a2b8/white?text=Jardín', 'Jardín trasero'),
        (3, 'https://placehold.co/800x600/6c757d/white?text=Oficina', 'Espacio de trabajo principal'),
        (4, 'https://placehold.co/800x600/343a40/white?text=Loft', 'Vista general del loft'),
        (4, 'https://placehold.co/800x600/fd7e14/white?text=Balcón', 'Balcón con vista a la calle');
    END IF;
END $$;

-- 5. Relacionar Propiedades con Amenities
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM property_amenities WHERE property_id = 1 AND amenity_id = 1) THEN
        INSERT INTO property_amenities (property_id, amenity_id) VALUES
        (1, 1),
        (1, 2),
        (1, 3),
        (2, 3),
        (2, 6),
        (3, 3),
        (3, 5),
        (4, 4);
    END IF;
END $$;

-- 6. Insertar Favoritos
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM favorites WHERE user_id = 2 AND property_id = 1) THEN
        INSERT INTO favorites (user_id, property_id) VALUES
        (2, 1),
        (3, 1),
        (1, 2);
    END IF;
END $$;

-- 7. Insertar Mensajes (Simulando una conversación)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM messages WHERE message_content = 'Hola Ana, estoy interesado en el apartamento de Miraflores. Sigue disponible?') THEN
        INSERT INTO messages (property_id, sender_id, receiver_id, message_content) VALUES
        (1, 2, 1, 'Hola Ana, estoy interesado en el apartamento de Miraflores. Sigue disponible?'),
        (1, 1, 2, 'Hola Carlos! Sí, aún está disponible. Te gustaría coordinar una visita?'),
        (1, 2, 1, 'Perfecto, sí. Tienes tiempo este sábado por la mañana?');
    END IF;
END $$;
