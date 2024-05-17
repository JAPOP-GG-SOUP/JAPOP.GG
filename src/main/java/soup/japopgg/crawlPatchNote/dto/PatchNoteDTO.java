package soup.japopgg.crawlPatchNote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PatchNoteDTO {
    private String patchNotePath;
    private String patchNoteTitle;
    private LocalDate patchDate;

    public PatchNoteDTO(String patchNotePath, String patchNoteTitle, LocalDate patchDate){
        this.patchNotePath=patchNotePath;
        this.patchNoteTitle=patchNoteTitle;
        this.patchDate=patchDate;
    }
}
