package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VisitorService {
    @Autowired
    VisitorRepository visitorRepository;

    @Transactional
    public Visitor createVisitor(String firstName, String lastName) {
        var visitor = new Visitor();
        visitor.setFirstName(firstName);
        visitor.setLastName(lastName);
        visitor = visitorRepository.save(visitor);
        return visitor;
    }

    @Transactional
    public Optional<Visitor> getVisitor(long id) {
        return visitorRepository.findById(id);
    }
    
}
