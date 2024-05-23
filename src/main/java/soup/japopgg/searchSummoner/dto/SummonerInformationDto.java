package soup.japopgg.searchSummoner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
// 소환사 계정과 관련된 모든 정보를 모으는 엔티티 (새롭게 만듬)
public class SummonerInformationDto {

    private String puuid;
    private String id;
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private long summonerLevel;
    private String gameName;
    private String tagLine;
}
