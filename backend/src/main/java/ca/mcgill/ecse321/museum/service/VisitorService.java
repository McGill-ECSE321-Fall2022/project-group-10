package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse321.museum.exception.ServiceLayerException;


@Service
public class VisitorService {
    @Autowired 
    VisitorRepository visitorRepository;

    /**
     * Create a new visitor
     * @param firstName first name of the visitor
     * @param lastName last name of the visitor
     * @param email email of the visitor
     * @param password password of the visitor
     * @return the created visitor
     */
    @Transactional
    public Visitor createVisitor(String firstName, String lastName, String email, String password) {
        var visitor = new Visitor();

        if (visitorRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        visitor.setFirstName(firstName);
        visitor.setLastName(lastName);
        visitor.setEmail(email);
        visitor.setPassword(password);
        return visitorRepository.save(visitor);
    }

    /**
     * Edit a visitor
     * @param visitorId id of the visitor to edit
     * @param firstName first name of the visitor
     * @param lastName last name of the visitor
     * @param email email of the visitor
     * @param password password of the visitor
     * @return the edited visitor
     */
    @Transactional
    public Visitor editVisitor(long id, String firstName, String lastName, String email, String password) {
        var visitor = visitorRepository.findById(id).orElse(null);

        if (visitor == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "Visitor not found");

        if (visitor.getEmail() != email && visitorRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        visitor.setFirstName(firstName);
        visitor.setLastName(lastName);
        visitor.setEmail(email);
        visitor.setPassword(password);
        return visitorRepository.save(visitor);
    }

    /**
     * Get a visitor by id
     * @param id id of the visitor
     * @return the visitor
     */
    @Transactional
    public Visitor getVisitor(long id) {
        Visitor visitor = visitorRepository.findById(id).orElse(null);

        if (visitor == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such visitor");

        return visitor;    
    }

    /**
     * Get all visitors
     * @return list of all visitors
     */
    @Transactional
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    /**
     * Deactivate a visitor
     * @param id id of the visitor to deactivate
     */
    @Transactional
    public void deactivateVisitor(long id) {
        Visitor visitor = visitorRepository.findById(id).orElse(null);

        if (visitor == null) { throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such visitor"); }

        visitor.setActive(false);
        visitorRepository.save(visitor);
    }
}
