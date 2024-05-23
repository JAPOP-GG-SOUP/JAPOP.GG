package soup.japopgg.crawlPatchNote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class PatchedChampionDTO {
    private String champion;
    private String patchNoteTitle;
    private LocalDate patchDate;
    private List<PatchedAbilityDTO> patchInfo;

    public PatchedChampionDTO(String champion, String patchNoteTitle, LocalDate patchDate, List<PatchedAbilityDTO> patchInfo) {
        this.champion = champion;
        this.patchNoteTitle = patchNoteTitle;
        this.patchDate = patchDate;
        this.patchInfo = patchInfo;
    }
}
