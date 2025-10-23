package com.trainee.backend_project.init;

import com.trainee.backend_project.model.*;
import com.trainee.backend_project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private AmenityRepository amenityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    @Autowired
    private PropertyAmenityRepository propertyAmenityRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        insertAmenities();
        insertUsers();
        insertProperties();
        insertImages();
        insertPropertyAmenities();
        insertFavorites();
        insertMessages();
    }

    private void insertAmenities() {
        List<String> names = Arrays.asList("Piscina", "Gimnasio", "Estacionamiento", "Balcón", "Seguridad 24h", "Admite Mascotas");
        for (String n : names) {
            Optional<Amenity> a = amenityRepository.findByName(n);
            if (a.isEmpty()) {
                Amenity amenity = new Amenity();
                amenity.setName(n);
                amenityRepository.save(amenity);
            }
        }
    }

    private void insertUsers() {
        insertUserIfNotExists("Ana García", "ana.garcia@example.com", "hashed_password_123", "+51987654321");
        insertUserIfNotExists("Carlos Rodriguez", "carlos.r@example.com", "hashed_password_123", "+51912345678");
        insertUserIfNotExists("Sofia Lopez", "sofia.lopez@email.com", "hashed_password_123", "+51998877665");
    }

    private void insertUserIfNotExists(String name, String email, String password, String phone) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User u = new User();
            u.setName(name);
            u.setEmail(email);
            u.setPassword(password);
            u.setPhoneNumber(phone);
            u.setCreatedAt(OffsetDateTime.now());
            userRepository.save(u);
        }
    }

    private void insertProperties() {
        insertPropertyIfNotExists("Moderno Apartamento en Miraflores", "Luminoso apartamento con vista al mar, acabados de lujo y excelente ubicación. Ideal para parejas o ejecutivos.", new BigDecimal("2100000.00"), "Av. Larco 123, Miraflores", "Lima", Property.OperationType.SALE, Property.PropertyType.APARTMENT, 2, 2, 95, "Ana García", "+51987654321", "https://maps.app.goo.gl/abcdef123456", "ana.garcia@example.com");
        insertPropertyIfNotExists("Casa Familiar con Jardín en La Molina", "Amplia casa de dos pisos con un hermoso jardín trasero, perfecta para una familia. Zona tranquila y segura.", new BigDecimal("4500.00"), "Calle Las Palmeras 456, La Molina", "Lima", Property.OperationType.RENTAL, Property.PropertyType.HOUSE, 4, 3, 220, "Carlos Rodriguez", "+51912345678", "https://maps.app.goo.gl/ghijkl789012", "carlos.r@example.com");
        insertPropertyIfNotExists("Oficina Céntrica en San Isidro", "Oficina bien iluminada en el corazón del centro financiero de San Isidro. Lista para ocupar.", new BigDecimal("3800.00"), "Av. Javier Prado Este 789, San Isidro", "Lima", Property.OperationType.RENTAL, Property.PropertyType.OFFICE, 0, 1, 60, "Ana García", "+51987654321", "https://maps.app.goo.gl/mnopqr345678", "ana.garcia@example.com");
        insertPropertyIfNotExists("Loft de Diseño en Barranco", "Espectacular loft con diseño industrial, techos altos y a pasos de los mejores restaurantes y galerías de arte.", new BigDecimal("1850000.00"), "Jr. Batalla de Junín 101, Barranco", "Lima", Property.OperationType.SALE, Property.PropertyType.APARTMENT, 1, 2, 110, "Sofia Lopez", "+51998877665", "https://maps.app.goo.gl/stuvwxyz901234", "sofia.lopez@email.com");
    }

    private void insertPropertyIfNotExists(String title, String description, BigDecimal price, String address, String city, Property.OperationType operationType, Property.PropertyType propertyType, Integer bedrooms, Integer bathrooms, Integer area, String contactName, String contactPhone, String mapsUrl, String ownerEmail) {
        if (propertyRepository.findByTitle(title).isEmpty()) {
            Property p = new Property();
            p.setTitle(title);
            p.setDescription(description);
            p.setPrice(price);
            p.setAddress(address);
            p.setCity(city);
            p.setOperationType(operationType);
            p.setPropertyType(propertyType);
            p.setBedrooms(bedrooms);
            p.setBathrooms(bathrooms);
            p.setAreaSqMeters(area);
            p.setContactName(contactName);
            p.setContactPhone(contactPhone);
            p.setGoogleMapsUrl(mapsUrl);
            Optional<User> owner = userRepository.findByEmail(ownerEmail);
            owner.ifPresent(p::setOwner);
            propertyRepository.save(p);
        }
    }

    private void insertImages() {
        insertImageIfNotExists("Moderno Apartamento en Miraflores", "https://placehold.co/800x600/007bff/white?text=Sala", "Vista de la sala de estar");
        insertImageIfNotExists("Moderno Apartamento en Miraflores", "https://placehold.co/800x600/28a745/white?text=Dormitorio", "Dormitorio principal");
        insertImageIfNotExists("Moderno Apartamento en Miraflores", "https://placehold.co/800x600/dc3545/white?text=Cocina", "Cocina moderna");
        insertImageIfNotExists("Casa Familiar con Jardín en La Molina", "https://placehold.co/800x600/ffc107/black?text=Fachada", "Fachada de la casa");
        insertImageIfNotExists("Casa Familiar con Jardín en La Molina", "https://placehold.co/800x600/17a2b8/white?text=Jardín", "Jardín trasero");
        insertImageIfNotExists("Oficina Céntrica en San Isidro", "https://placehold.co/800x600/6c757d/white?text=Oficina", "Espacio de trabajo principal");
        insertImageIfNotExists("Loft de Diseño en Barranco", "https://placehold.co/800x600/343a40/white?text=Loft", "Vista general del loft");
        insertImageIfNotExists("Loft de Diseño en Barranco", "https://placehold.co/800x600/fd7e14/white?text=Balcón", "Balcón con vista a la calle");
    }

    private void insertImageIfNotExists(String propertyTitle, String imageUrl, String desc) {
        if (propertyImageRepository.findByImageUrl(imageUrl).isEmpty()) {
            Optional<Property> prop = propertyRepository.findByTitle(propertyTitle);
            if (prop.isPresent()) {
                PropertyImage img = new PropertyImage();
                img.setProperty(prop.get());
                img.setImageUrl(imageUrl);
                img.setDescription(desc);
                propertyImageRepository.save(img);
            }
        }
    }

    private void insertPropertyAmenities() {
        insertPropertyAmenityIfNotExists("Moderno Apartamento en Miraflores", "Piscina");
        insertPropertyAmenityIfNotExists("Moderno Apartamento en Miraflores", "Gimnasio");
        insertPropertyAmenityIfNotExists("Moderno Apartamento en Miraflores", "Estacionamiento");
        insertPropertyAmenityIfNotExists("Casa Familiar con Jardín en La Molina", "Estacionamiento");
        insertPropertyAmenityIfNotExists("Casa Familiar con Jardín en La Molina", "Admite Mascotas");
        insertPropertyAmenityIfNotExists("Oficina Céntrica en San Isidro", "Estacionamiento");
        insertPropertyAmenityIfNotExists("Oficina Céntrica en San Isidro", "Seguridad 24h");
        insertPropertyAmenityIfNotExists("Loft de Diseño en Barranco", "Balcón");
    }

    private void insertPropertyAmenityIfNotExists(String propertyTitle, String amenityName) {
        Optional<Property> prop = propertyRepository.findByTitle(propertyTitle);
        Optional<Amenity> amen = amenityRepository.findByName(amenityName);
        if (prop.isPresent() && amen.isPresent()) {
            Property property = prop.get();
            Amenity amenity = amen.get();
            boolean exists = propertyAmenityRepository.existsById(new PropertyAmenityId(property.getId(), amenity.getId()));
            if (!exists) {
                PropertyAmenity pa = new PropertyAmenity(property, amenity);
                propertyAmenityRepository.save(pa);
            }
        }
    }

    private void insertFavorites() {
        insertFavoriteIfNotExists("carlos.r@example.com", "Moderno Apartamento en Miraflores");
        insertFavoriteIfNotExists("sofia.lopez@email.com", "Moderno Apartamento en Miraflores");
        insertFavoriteIfNotExists("ana.garcia@example.com", "Casa Familiar con Jardín en La Molina");
    }

    private void insertFavoriteIfNotExists(String userEmail, String propertyTitle) {
        Optional<User> u = userRepository.findByEmail(userEmail);
        Optional<Property> p = propertyRepository.findByTitle(propertyTitle);
        if (u.isPresent() && p.isPresent()) {
            FavoriteId id = new FavoriteId(u.get().getId(), p.get().getId());
            if (favoriteRepository.findById(id).isEmpty()) {
                Favorite f = new Favorite();
                f.setUser(u.get());
                f.setProperty(p.get());
                favoriteRepository.save(f);
            }
        }
    }

    private void insertMessages() {
        insertMessageIfNotExists("Moderno Apartamento en Miraflores", "carlos.r@example.com", "ana.garcia@example.com", "Hola Ana, estoy interesado en el apartamento de Miraflores. Sigue disponible?");
        insertMessageIfNotExists("Moderno Apartamento en Miraflores", "ana.garcia@example.com", "carlos.r@example.com", "Hola Carlos! Sí, aún está disponible. Te gustaría coordinar una visita?");
        insertMessageIfNotExists("Moderno Apartamento en Miraflores", "carlos.r@example.com", "ana.garcia@example.com", "Perfecto, sí. Tienes tiempo este sábado por la mañana?");
    }

    private void insertMessageIfNotExists(String propertyTitle, String senderEmail, String receiverEmail, String content) {
        Optional<Property> p = propertyRepository.findByTitle(propertyTitle);
        Optional<User> sender = userRepository.findByEmail(senderEmail);
        Optional<User> receiver = userRepository.findByEmail(receiverEmail);
        if (p.isPresent() && sender.isPresent() && receiver.isPresent()) {
            boolean exists = messageRepository.existsByMessageContent(content);
            if (!exists) {
                Message m = new Message();
                m.setProperty(p.get());
                m.setSender(sender.get());
                m.setReceiver(receiver.get());
                m.setMessageContent(content);
                m.setSentAt(OffsetDateTime.now());
                messageRepository.save(m);
            }
        }
    }
}
