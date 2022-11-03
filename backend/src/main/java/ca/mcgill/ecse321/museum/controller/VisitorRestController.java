package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.VisitorDto;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.service.VisitorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class VisitorRestController {
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private ModelMapper modelMapper;

    private VisitorDto convertToDto(Visitor visitor) {
        return modelMapper.map(visitor, VisitorDto.class);
    }

    private Optional<Visitor> convertToEntity(VisitorDto visitorDto) {
        return visitorService.getVisitor(visitorDto.getId());
    }

    @PostMapping(value = {"/visitors/{firstName}_{lastName}/"})
    public VisitorDto createPerson(@PathVariable String firstName, String lastName) throws IllegalArgumentException {
        var visitor = visitorService.createVisitor(firstName, lastName);
        return convertToDto(visitor);
    }
}
