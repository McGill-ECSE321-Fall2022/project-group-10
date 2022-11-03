package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.VisitorDto;
import ca.mcgill.ecse321.museum.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class VisitorRestController {
    @Autowired
    private VisitorService visitorService;

//    @PostMapping(value = { "/persons/{firstName}_{lastName}/"})
//    public VisitorDto createPerson(@PathVariable String firstName, String lastName ) throws IllegalArgumentException {
//        var visitor = visitorService.createVisitor(firstName, lastName);
//    }
}
