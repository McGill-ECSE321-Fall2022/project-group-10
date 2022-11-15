/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import java.util.List;
import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.ShoppingCart;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.ShoppingCartRepository;
import ca.mcgill.ecse321.museum.service.LoanService;

@Service
public class ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Transactional
    public ShoppingCart createShoppingCart(Visitor customer) {
        var shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        //shoppingCart.setArtworks(artworks);
        //shoppingCart.setArtwork(artwork);
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

    // @Transactional
    // public ShoppingCart loanAllArtworks(ShoppingCart shoppingCart, List<Date> startDates, List<Date> endDates){
    //     LoanService loanService = new LoanService();
    //     List<Artwork> artworks = shoppingCart.getArtworks();
    //     long customerID = shoppingCart.getCustomer().getId();
    //     float price;
    //     float totalPrice=0;
    //     long artworkID;

    //     for(Artwork artwork : artworks){
    //         loanService.createLoan(totalPrice, false, startDate, endDate, artwork, customerID, (long) 0);
    //         price = artwork.getPrice();
    //         totalPrice += price;
    //         artworkID = artwork.getId();
    //         artworksID.add(artworkID);
    //     }
    //     artworks.clear();
    //     shoppingCart.setArtworks(artworks);
    //     return shoppingCartRepository.save(shoppingCart);
    // }

    @Transactional public void setArtworks(ShoppingCart shoppingCart, List<Artwork> artworks) { shoppingCart.setArtworks(artworks); }

    @Transactional public ShoppingCart getShoppingCart(long id) { return shoppingCartRepository.findById(id).orElse(null); }

    @Transactional public List<ShoppingCart> getAllShoppingCarts() { return shoppingCartRepository.findAll(); }

}
