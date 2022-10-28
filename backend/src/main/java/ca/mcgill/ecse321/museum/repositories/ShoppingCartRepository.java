package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
