package soup.japopgg.searchSummoner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import soup.japopgg.searchSummoner.dto.SummonerInformationDto;
import soup.japopgg.searchSummoner.service.SummonerInformationService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
public class RiotMainController {

    private final Logger logger = LoggerFactory.getLogger(RiotMainController.class);
    private final SummonerInformationService summonerInformationService;

    public RiotMainController(SummonerInformationService summonerInformationService) {
        this.summonerInformationService = summonerInformationService;
    }

    // 유효한 모든 Data Dragon 버전 : https://ddragon.leagueoflegends.com/api/versions.json
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model, @RequestParam HashMap<String, String> paramMap)
            throws UnsupportedEncodingException, JsonProcessingException {

        SummonerInformationDto summonerInformationDto = null;
        String puuid = null;

        // 파라미터 초기화
        paramMap.put("start", "0");  // 매치목록 5개씩 처리하기 위한 변수
        paramMap.put("count", "5");  // 매치목록 5개씩 처리하기 위한 변수
        paramMap.put("name", paramMap.get("name") != null ? paramMap.get("name") : "");
        paramMap.put("tag", paramMap.get("tag") != null ? paramMap.get("tag") : "");

        // 소환사명으로 소환사정보 검색
        if(!paramMap.get("name").isEmpty() && !paramMap.get("tag").isEmpty()) {

            // name와 tag가 빈 문자열이 아닌 경우, URL 인코딩 후 RiotHttpClientService를 호출하여 소환사 정보를 가져옴
            String name = URLEncoder.encode(paramMap.get("name"), "UTF-8");
            String tag = URLEncoder.encode(paramMap.get("tag"), "UTF-8");

            puuid = summonerInformationService.saveSummonerInformation(name, tag);
        }
        // 첫 화면에서 puuid null이기 때문에 NPE 처리 안해주면 오류남
        if (puuid != null) {
            summonerInformationDto = summonerInformationService.getSummonerInformation(puuid);
        }
        // summonerInformationDto가 null이면 기본값 설정
        if (summonerInformationDto == null) {
            summonerInformationDto = new SummonerInformationDto();
        }

        model.addAttribute("paramMap", paramMap);
        model.addAttribute("summonerInformation", summonerInformationDto);

        return "main";
    }
}
