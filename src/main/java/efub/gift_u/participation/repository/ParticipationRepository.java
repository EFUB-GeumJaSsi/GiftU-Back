package efub.gift_u.participation.repository;


import efub.gift_u.funding.domain.Funding;
import efub.gift_u.participation.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends  JpaRepository<Participation , Long>{
   
    List<Participation> findByFunding(Funding funding);
}
