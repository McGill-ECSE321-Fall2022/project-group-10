package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
