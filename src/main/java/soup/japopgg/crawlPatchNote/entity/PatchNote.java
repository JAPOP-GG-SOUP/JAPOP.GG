package soup.japopgg.crawlPatchNote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name="PatchNote")
public class PatchNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String patchNotePath;
    @Column(nullable = false)
    private String patchNoteTitle;
    @Column(nullable = false)
    private LocalDate patchDate;

    @Builder
    public PatchNote(String patchNotePath,
                     String patchNoteTitle,
                     LocalDate patchDate){
        this.patchNotePath=patchNotePath;
        this.patchNoteTitle=patchNoteTitle;
        this.patchDate=patchDate;
    }

    public void updatePatchNote(String patchNotePath,
                                String patchNoteTitle,
                                LocalDate patchDate){
        this.patchNotePath=patchNotePath;
        this.patchNoteTitle=patchNoteTitle;
        this.patchDate=patchDate;
    }
}
