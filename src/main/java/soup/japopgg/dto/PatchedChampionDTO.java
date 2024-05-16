package soup.japopgg.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatchedChampionDTO {
    private String champion;
    private String patchNoteTitle;
    private List<PatchedAbilityDTO> patchInfo;

    public PatchedChampionDTO(String champion, String patchNoteTitle, List<PatchedAbilityDTO> patchInfo) {
        this.champion = champion;
        this.patchNoteTitle = patchNoteTitle;
        this.patchInfo = patchInfo;
    }
}
