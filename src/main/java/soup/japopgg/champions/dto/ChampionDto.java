package soup.japopgg.champions.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ChampionDto {
    private Map<String, ChampionDataDto> data;
}
