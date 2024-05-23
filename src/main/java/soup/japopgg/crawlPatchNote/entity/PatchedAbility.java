package soup.japopgg.crawlPatchNote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="PatchedAbility")
public class PatchedAbility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String patchedAbility;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String patchDetails;

    //PatchedChampion와 다대일 양방향 매핑됨
    //이 엔티티에서 PatchedChampion 참조
    @ManyToOne
    @JoinColumn(name="patchedChampion")
    private PatchedChampion patchedChampion;

    @Builder
    public PatchedAbility(String patchedAbility, String patchDetails, PatchedChampion patchedChampion){
        this.patchedAbility=patchedAbility;
        this.patchDetails=patchDetails;
        this.patchedChampion=patchedChampion;
    }

    public void updatePatchedAbility(String patchedAbility, String patchDetails, PatchedChampion patchedChampion){
        this.patchedAbility=patchedAbility;
        this.patchDetails=patchDetails;
        this.patchedChampion=patchedChampion;
    }
}
