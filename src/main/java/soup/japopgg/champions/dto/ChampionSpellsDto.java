package soup.japopgg.champions.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChampionSpellsDto {
    private String id;
    private String name;
    private String description;
    private String tooltip;
    private int maxrank;
    private List<Integer> cooldown;
    private List<Integer> cost;
    private String costType;
}
