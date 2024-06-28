package soup.japopgg.crawlPatchNote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soup.japopgg.crawlPatchNote.entity.PatchNote;

import java.util.List;

public interface PatchNoteRepository extends JpaRepository<PatchNote, Long> {
    void deleteByPatchNoteTitle(String patchNoteTitle);
    List<PatchNote> findAllByOrderByPatchDateDesc();
    PatchNote findFirstByOrderByPatchDateDesc();
}
