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
-- Insertar cada propiedad solo si no existe (lookup owner por email)
INSERT INTO properties (title, description, price, address, city, operation_type, property_type, bedrooms, bathrooms, area_sq_meters, contact_name, contact_phone, google_maps_url, owner_id)
SELECT 'Moderno Apartamento en Miraflores', 'Luminoso apartamento con vista al mar, acabados de lujo y excelente ubicación. Ideal para parejas o ejecutivos.', 2100000.00, 'Av. Larco 123, Miraflores', 'Lima', 'SALE', 'APARTMENT', 2, 2, 95, 'Ana García', '+51987654321', 'https://maps.app.goo.gl/abcdef123456', (SELECT id FROM users WHERE email = 'ana.garcia@example.com')
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title = 'Moderno Apartamento en Miraflores');

INSERT INTO properties (title, description, price, address, city, operation_type, property_type, bedrooms, bathrooms, area_sq_meters, contact_name, contact_phone, google_maps_url, owner_id)
SELECT 'Casa Familiar con Jardín en La Molina', 'Amplia casa de dos pisos con un hermoso jardín trasero, perfecta para una familia. Zona tranquila y segura.', 4500.00, 'Calle Las Palmeras 456, La Molina', 'Lima', 'RENTAL', 'HOUSE', 4, 3, 220, 'Carlos Rodriguez', '+51912345678', 'https://maps.app.goo.gl/ghijkl789012', (SELECT id FROM users WHERE email = 'carlos.r@example.com')
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title = 'Casa Familiar con Jardín en La Molina');

INSERT INTO properties (title, description, price, address, city, operation_type, property_type, bedrooms, bathrooms, area_sq_meters, contact_name, contact_phone, google_maps_url, owner_id)
SELECT 'Oficina Céntrica en San Isidro', 'Oficina bien iluminada en el corazón del centro financiero de San Isidro. Lista para ocupar.', 3800.00, 'Av. Javier Prado Este 789, San Isidro', 'Lima', 'RENTAL', 'OFFICE', 0, 1, 60, 'Ana García', '+51987654321', 'https://maps.app.goo.gl/mnopqr345678', (SELECT id FROM users WHERE email = 'ana.garcia@example.com')
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title = 'Oficina Céntrica en San Isidro');

INSERT INTO properties (title, description, price, address, city, operation_type, property_type, bedrooms, bathrooms, area_sq_meters, contact_name, contact_phone, google_maps_url, owner_id)
SELECT 'Loft de Diseño en Barranco', 'Espectacular loft con diseño industrial, techos altos y a pasos de los mejores restaurantes y galerías de arte.', 1850000.00, 'Jr. Batalla de Junín 101, Barranco', 'Lima', 'SALE', 'APARTMENT', 1, 2, 110, 'Sofia Lopez', '+51998877665', 'https://maps.app.goo.gl/stuvwxyz901234', (SELECT id FROM users WHERE email = 'sofia.lopez@email.com')
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title = 'Loft de Diseño en Barranco');

-- Imágenes: insertar individualmente referenciando la propiedad por título
INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'), 'https://placehold.co/800x600/007bff/white?text=Sala', 'Vista de la sala de estar'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/007bff/white?text=Sala');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'), 'https://placehold.co/800x600/28a745/white?text=Dormitorio', 'Dormitorio principal'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/28a745/white?text=Dormitorio');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'), 'https://placehold.co/800x600/dc3545/white?text=Cocina', 'Cocina moderna'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/dc3545/white?text=Cocina');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Casa Familiar con Jardín en La Molina'), 'https://placehold.co/800x600/ffc107/black?text=Fachada', 'Fachada de la casa'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/ffc107/black?text=Fachada');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Casa Familiar con Jardín en La Molina'), 'https://placehold.co/800x600/17a2b8/white?text=Jardín', 'Jardín trasero'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/17a2b8/white?text=Jardín');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Oficina Céntrica en San Isidro'), 'https://placehold.co/800x600/6c757d/white?text=Oficina', 'Espacio de trabajo principal'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/6c757d/white?text=Oficina');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Loft de Diseño en Barranco'), 'https://placehold.co/800x600/343a40/white?text=Loft', 'Vista general del loft'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/343a40/white?text=Loft');

INSERT INTO property_images (property_id, image_url, description)
SELECT (SELECT id FROM properties WHERE title = 'Loft de Diseño en Barranco'), 'https://placehold.co/800x600/fd7e14/white?text=Balcón', 'Balcón con vista a la calle'
WHERE NOT EXISTS (SELECT 1 FROM property_images WHERE image_url = 'https://placehold.co/800x600/fd7e14/white?text=Balcón');

-- Relacionar propiedades con amenities usando lookups y evitando duplicados
INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Moderno Apartamento en Miraflores' AND a.name = 'Piscina'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Moderno Apartamento en Miraflores' AND a.name = 'Gimnasio'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Moderno Apartamento en Miraflores' AND a.name = 'Estacionamiento'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Casa Familiar con Jardín en La Molina' AND a.name = 'Estacionamiento'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Casa Familiar con Jardín en La Molina' AND a.name = 'Admite Mascotas'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Oficina Céntrica en San Isidro' AND a.name = 'Estacionamiento'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Oficina Céntrica en San Isidro' AND a.name = 'Seguridad 24h'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

INSERT INTO property_amenities (property_id, amenity_id)
SELECT p.id, a.id FROM properties p, amenities a
WHERE p.title = 'Loft de Diseño en Barranco' AND a.name = 'Balcón'
AND NOT EXISTS (SELECT 1 FROM property_amenities pa WHERE pa.property_id = p.id AND pa.amenity_id = a.id);

-- Favoritos: insertar usando lookups de usuario y propiedad
INSERT INTO favorites (user_id, property_id)
SELECT (SELECT id FROM users WHERE email = 'carlos.r@example.com'), (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores')
WHERE NOT EXISTS (SELECT 1 FROM favorites f WHERE f.user_id = (SELECT id FROM users WHERE email = 'carlos.r@example.com') AND f.property_id = (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'));

INSERT INTO favorites (user_id, property_id)
SELECT (SELECT id FROM users WHERE email = 'sofia.lopez@email.com'), (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores')
WHERE NOT EXISTS (SELECT 1 FROM favorites f WHERE f.user_id = (SELECT id FROM users WHERE email = 'sofia.lopez@email.com') AND f.property_id = (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'));

INSERT INTO favorites (user_id, property_id)
SELECT (SELECT id FROM users WHERE email = 'ana.garcia@example.com'), (SELECT id FROM properties WHERE title = 'Casa Familiar con Jardín en La Molina')
WHERE NOT EXISTS (SELECT 1 FROM favorites f WHERE f.user_id = (SELECT id FROM users WHERE email = 'ana.garcia@example.com') AND f.property_id = (SELECT id FROM properties WHERE title = 'Casa Familiar con Jardín en La Molina'));

-- Mensajes: insertar con lookups y comprobación por contenido
INSERT INTO messages (property_id, sender_id, receiver_id, message_content)
SELECT (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'), (SELECT id FROM users WHERE email = 'carlos.r@example.com'), (SELECT id FROM users WHERE email = 'ana.garcia@example.com'), 'Hola Ana, estoy interesado en el apartamento de Miraflores. Sigue disponible?'
WHERE NOT EXISTS (SELECT 1 FROM messages WHERE message_content = 'Hola Ana, estoy interesado en el apartamento de Miraflores. Sigue disponible?');

INSERT INTO messages (property_id, sender_id, receiver_id, message_content)
SELECT (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'), (SELECT id FROM users WHERE email = 'ana.garcia@example.com'), (SELECT id FROM users WHERE email = 'carlos.r@example.com'), 'Hola Carlos! Sí, aún está disponible. Te gustaría coordinar una visita?'
WHERE NOT EXISTS (SELECT 1 FROM messages WHERE message_content = 'Hola Carlos! Sí, aún está disponible. Te gustaría coordinar una visita?');

INSERT INTO messages (property_id, sender_id, receiver_id, message_content)
SELECT (SELECT id FROM properties WHERE title = 'Moderno Apartamento en Miraflores'), (SELECT id FROM users WHERE email = 'carlos.r@example.com'), (SELECT id FROM users WHERE email = 'ana.garcia@example.com'), 'Perfecto, sí. Tienes tiempo este sábado por la mañana?'
WHERE NOT EXISTS (SELECT 1 FROM messages WHERE message_content = 'Perfecto, sí. Tienes tiempo este sábado por la mañana?');
