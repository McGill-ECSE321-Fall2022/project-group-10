/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ShoppingCart;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Transactional
    public ShoppingCart createShoppingCart(List<Visitor> customers){
        var shoppingCart = new ShoppingCart();
        shoppingCart.setCustomers(customers);
        //shoppingCart.setArtworks(artworks);
        //shoppingCart.setArtwork(artwork);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart createShoppingCart(Visitor customer){
        var shoppingCart = new ShoppingCart();
        List<Visitor> customers = List.of(customer);
        shoppingCart.setCustomers(customers);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart addArtworkToCart(ShoppingCart shoppingCart, Artwork artwork){
        List<Artwork> artworks = shoppingCart.getArtworks();
        artworks.add(artwork);
        shoppingCart.setArtworks(artworks);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart removeArtworkFromCart(ShoppingCart shoppingCart, Artwork artwork){
        List<Artwork> artworks = shoppingCart.getArtworks();
        artworks.remove(artwork);
        shoppingCart.setArtworks(artworks);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart clearCart(ShoppingCart shoppingCart){
        List<Artwork> artworks = shoppingCart.getArtworks();
        artworks.clear();
        shoppingCart.setArtworks(artworks);
        return shoppingCartRepository.save(shoppingCart);
    }

    /*
    @Transactional
    public ShoppingCart loanAllArtworks(ShoppingCart shoppingCart){
        List<Artwork> artworks = shoppingCart.getArtworks();
        for(Artwork artwork : artworks){
            artwork.s
        }
        artworks.clear();
        shoppingCart.setArtworks(artworks);
        return shoppingCartRepository.save(shoppingCart);
    }
    */

    @Transactional public void setArtworks(ShoppingCart shoppingCart, List<Artwork> artworks) { shoppingCart.setArtworks(artworks); }

    @Transactional public ShoppingCart getShoppingCart(long id) { return shoppingCartRepository.findById(id).orElse(null); }

    @Transactional public List<ShoppingCart> getAllShoppingCarts() { return shoppingCartRepository.findAll(); }

}
