package soup.japopgg.champions.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChampionDataDto {
    private String id;
    private String key;
    private String name;
    private List<ChampionSpellsDto> spells;
    private ChampionPassiveDto passive;
}
