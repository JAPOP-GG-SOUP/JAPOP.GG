package soup.japopgg.crawlPatchNote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatchedAbilityDTO {
    private String patchedAbility;
    private List<String> patchDetails;

    public PatchedAbilityDTO(String patchedAbility, List<String> patchDetails) {
        this.patchedAbility = patchedAbility;
        this.patchDetails = patchDetails;
    }
}
