package soup.japopgg.searchSummoner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soup.japopgg.searchSummoner.entity.SummonerInformation;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<SummonerInformation, Integer> {
    Optional<SummonerInformation> findByPuuid(String puuid);
}
