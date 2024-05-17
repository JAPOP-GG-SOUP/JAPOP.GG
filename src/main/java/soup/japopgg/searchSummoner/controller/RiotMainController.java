package soup.japopgg.searchSummoner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import soup.japopgg.searchSummoner.dto.AccountDto;
import soup.japopgg.searchSummoner.dto.ResponseDto;
import soup.japopgg.searchSummoner.dto.StatusDto;
import soup.japopgg.searchSummoner.dto.SummonerDto;
import soup.japopgg.searchSummoner.service.RiotHttpClientService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
public class RiotMainController {

    private final Logger logger = LoggerFactory.getLogger(RiotMainController.class);
    /*
     ## 유효한 모든 Data Dragon 버전
      https://ddragon.leagueoflegends.com/api/versions.json
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model, @RequestParam HashMap<String, String> paramMap)
            throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {

        ResponseDto responseDto = null;
        SummonerDto summonerDto = new SummonerDto();
        AccountDto accountDto = new AccountDto();
        StatusDto status = new StatusDto();

        //-------------------------------------------
        // 파라미터 초기화
        //-------------------------------------------
        paramMap.put("start", "0");  // 매치목록 5개씩 처리하기 위한 변수
        paramMap.put("count", "5");  // 매치목록 5개씩 처리하기 위한 변수
        // 키에 대한 값을 확인하여, 값이 null이면 빈 문자열을 할당
        paramMap.put("name", paramMap.get("name") != null ? paramMap.get("name") : "");
        paramMap.put("tag", paramMap.get("tag") != null ? paramMap.get("tag") : "");

        //-------------------------------------------
        // 소환사명으로 소환사정보 검색
        //-------------------------------------------
        // "".equals(paramMap.get("name")) == false || "".equals(paramMap.get("tag")) == false
        if(!paramMap.get("name").isEmpty() || !paramMap.get("tag").isEmpty()) {

            // name와 tag가 빈 문자열이 아닌 경우, URL 인코딩 후 RiotHttpClientService를 호출하여 소환사 정보를 가져옴
            String name = URLEncoder.encode(paramMap.get("name"), "UTF-8");
            String tag = URLEncoder.encode(paramMap.get("tag"), "UTF-8");

            // 소환사명, 태그로 puuid GET
            responseDto = new RiotHttpClientService().getAccountsByNameAndTag(name, tag);
            status = responseDto.getStatus();

            // true->응답 정상, false->응답 오류
            if(responseDto.isOK()) {

                ObjectMapper mapper = new ObjectMapper();
                accountDto = mapper.readValue(responseDto.getResponseBody(), AccountDto.class);
            }

            // puuid로 소환사 정보 GET
            responseDto = new RiotHttpClientService().getSummonersByPuuid(accountDto.getPuuid());
            status = responseDto.getStatus();

            if(responseDto.isOK()) {

                ObjectMapper mapper = new ObjectMapper();
                summonerDto = mapper.readValue(responseDto.getResponseBody(), SummonerDto.class);
            }
        }
        model.addAttribute("status", status);
        model.addAttribute("account", accountDto);
        model.addAttribute("summoner", summonerDto);
        model.addAttribute("paramMap", paramMap);

        return "main";
    }
}
