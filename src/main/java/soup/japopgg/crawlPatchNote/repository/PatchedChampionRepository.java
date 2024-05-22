package soup.japopgg.crawlPatchNote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soup.japopgg.crawlPatchNote.entity.PatchedChampion;

import java.util.List;

public interface PatchedChampionRepository extends JpaRepository<PatchedChampion,Long> {
    void deleteByPatchNoteTitleAndChampion(String patchNoteTitle,String champion);
    List<PatchedChampion> findAllByPatchNoteTitle(String patchNoteTitle);
}
