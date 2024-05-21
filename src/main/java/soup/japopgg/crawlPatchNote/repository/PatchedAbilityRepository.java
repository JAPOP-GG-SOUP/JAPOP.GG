package soup.japopgg.crawlPatchNote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soup.japopgg.crawlPatchNote.entity.PatchedAbility;

public interface PatchedAbilityRepository extends JpaRepository<PatchedAbility,Long> {
}
