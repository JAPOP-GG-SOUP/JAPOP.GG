package soup.japopgg.searchSummoner.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
// 소환사 이름, 태그를 통해 얻는 응답 body값
public class AccountDto {

    private String puuid;
    private String gameName;
    private String tagLine;
}
