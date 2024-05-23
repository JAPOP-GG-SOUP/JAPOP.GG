package soup.japopgg.searchSummoner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soup.japopgg.searchSummoner.dto.ResponseDto;
import soup.japopgg.searchSummoner.exception.BusinessException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class RiotHttpClientService {
    private static final Logger logger = LoggerFactory.getLogger(RiotHttpClientService.class);

    // 소환사명, 태그로 소환사 puuid GET
    // https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%EC%8B%9C%EC%9E%91%EC%A0%90/kr1?api_key=RGAPI-ee9cf463-d7c3-44a5-a2f2-dca0de098fa3
    public ResponseDto getAccountsByNameAndTag(String name, String tag) {

        StringBuilder bPuuid = new StringBuilder("/riot/account/v1/accounts/by-riot-id").append('/').append(name).append('/').append(tag);

        return getRiotResponse(RiotConstant.API_SERVER_ASIA, bPuuid.toString());
    }

    // 소환사 puuid로 소환사 정보 GET
    // https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/CQE4CT5hCDFUEb3X1SZqWeu6WB1pTuPMWDfCBemjnmvDozlGw4WZWZOIKTCPhzRULTRoOssiHZJSeg?api_key=RGAPI-ee9cf463-d7c3-44a5-a2f2-dca0de098fa3
    public ResponseDto getSummonersByPuuid(String puuid) {

        StringBuilder bInformation = new StringBuilder("/lol/summoner/v4/summoners/by-puuid/").append(puuid);

        return getRiotResponse(RiotConstant.API_SERVER_KR, bInformation.toString());
    }

    /**
     * Riot 서버에 연결하여 정보조회
     */
    public ResponseDto getRiotResponse(String server, String requestURL) {

        ResponseDto responseDto = null;
        StringBuilder httpURL = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 서버 + URI + parameter + api key 주소 생성
            httpURL.append(server)
                    .append(requestURL)
                    .append(requestURL.contains("?") ? "&" : "?")
                    .append("api_key=").append(RiotConstant.API_KEY);

            // 요청
            HttpRequest httpReqeust = HttpRequest.newBuilder(URI.create(httpURL.toString())).build();
            // 응답
            HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(httpReqeust, HttpResponse.BodyHandlers.ofString());

            // httpResponse 내용물 : responseCode(200), headers, body(puuid, gameName, tagLine)
            // 정상 (= 200) 이 아니면 오류 발생
            if (httpResponse.statusCode() != 200) {
                responseDto = mapper.readValue(httpResponse.body(), ResponseDto.class);
                throw new BusinessException(responseDto.getStatus().getMessage());
            } else {
                // 반환 객체에 저장
                responseDto = new ResponseDto(httpResponse.body());
            }
        }
        catch (BusinessException ex) {

            logger.error(ex.getMessage());
        } catch (Exception ex) { // ConnectException ... etc

            ex.printStackTrace();
            responseDto = new ResponseDto(500, ex.getCause() + " " + (ex.getMessage() != null ? ex.getMessage() : ""));
            logger.error("Exception: {}", ex.getMessage(), ex);
        }

        return responseDto;
    }
}
