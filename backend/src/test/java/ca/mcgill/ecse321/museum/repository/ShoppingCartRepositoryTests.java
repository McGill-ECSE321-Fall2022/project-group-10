package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.museum.model.ShoppingCart;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Visitor;

@SpringBootTest
public class ShoppingCartRepositoryTests {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ArtworkRepository artworkRepository;

    @Autowired
    VisitorRepository visitorRepository;

    @AfterEach
    public void clearDatabase() {
        // Clear database rows after each test
        shoppingCartRepository.deleteAll();
        visitorRepository.deleteAll();
        artworkRepository.deleteAll();
    }

    /*
     * Test that a shopping cart can be created and saved to the database and that it can be retrieved from the database
     */
    @Test
    @Transactional
    public void testPersistAndLoadShoppingCart() {

        // Create all objects
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Artwork> artworks = new ArrayList<>();
        List<Visitor> customers = new ArrayList<>();

        // Create attributes
        Visitor customer1 = new Visitor();
        Visitor customer2 = new Visitor();
        Artwork artwork1 = new Artwork();
        Artwork artwork2 = new Artwork();
        customers.add(customer1);
        customers.add(customer2);
        artworks.add(artwork1);
        artworks.add(artwork2);

        // Set attributes
        shoppingCart.setCustomers(customers);
        shoppingCart.setArtworks(artworks);
        
        // Save all objects
        shoppingCartRepository.save(shoppingCart);
        visitorRepository.save(customer1);
        visitorRepository.save(customer2);
        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        Long shoppingCartId = shoppingCart.getId();

        // Read shopping cart from database
        shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElse(null);

        // Assert that shopping cart has correct attributes
        assertNotNull(shoppingCart);
        assertEquals(shoppingCartId, shoppingCart.getId());
        assertEquals(customers, shoppingCart.getCustomers());
        assertEquals(artworks, shoppingCart.getArtworks());
    }
}
