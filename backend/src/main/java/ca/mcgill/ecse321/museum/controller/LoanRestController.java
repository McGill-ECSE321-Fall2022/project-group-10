// /* (C)2022 */
// package ca.mcgill.ecse321.museum.controller;

// import java.util.Optional;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RestController;

// import ca.mcgill.ecse321.museum.dto.LoanDto;
// import ca.mcgill.ecse321.museum.model.Loan;
// import ca.mcgill.ecse321.museum.service.LoanService;

// @CrossOrigin(origins = "*")
// @RestController
// public class LoanRestController {
    
//     @Autowired
//     private LoanService loanService;
//     @Autowired
//     private ModelMapper modelMapper;

//     private LoanDto convertToDto(Loan loan) {
//         return modelMapper.map(loan, LoanDto.class);
//     }

//     private Loan convertToEntity(LoanDto loanDto) {
//         return loanService.getLoan(loanDto.getId());
//     }

//     @PostMapping(value = {"/loans"})
//     public LoanDto createArtwork(@RequestBody L body) {
//         var artwork = artworkService.createArtwork(
//                 body.getTitle(),
//                 body.getAuthor(),
//                 body.getCreationDate(),
//                 body.getDescription(),
//                 body.getImageLink(),
//                 body.getPrice(),
//                 body.isAvailable()
//         );
//         return convertToDto(artwork);
//     }

//     @GetMapping(value = {"/artworks/{id}"})
//     public ArtworkDto getArtwork(@PathVariable long id) throws IllegalArgumentException {
//         var artwork = artworkService.getArtwork(id);
//         return convertToDto(artwork);
//     }


// }
