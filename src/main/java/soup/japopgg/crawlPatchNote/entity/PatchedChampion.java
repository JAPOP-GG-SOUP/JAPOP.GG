package soup.japopgg.crawlPatchNote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name="PatchedChampion")
public class PatchedChampion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String champion;
    @Column(nullable = false)
    private String patchNoteTitle;
    @Column(nullable = false)
    private LocalDate patchDate;

    //PatchedAbility와 다대일 양방향 매핑됨
    //이 엔티티에서 FK 관리
    @OneToMany(mappedBy = "patchedChampion", cascade = CascadeType.ALL)
    private List<PatchedAbility> patchInfo;

    @Builder
    public PatchedChampion(String champion,
                           String patchNoteTitle,
                           LocalDate patchDate,
                           List<PatchedAbility> patchInfo){
        this.champion=champion;
        this.patchNoteTitle=patchNoteTitle;
        this.patchDate=patchDate;
        this.patchInfo=patchInfo;
    }

    public void updatePatchedChampion(String champion,
                                String patchNoteTitle,
                                LocalDate patchDate,
                                List<PatchedAbility> patchInfo){
        this.champion=champion;
        this.patchNoteTitle=patchNoteTitle;
        this.patchDate=patchDate;
        this.patchInfo=patchInfo;
    }
}
