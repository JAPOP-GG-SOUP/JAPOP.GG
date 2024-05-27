package soup.japopgg.champions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import soup.japopgg.champions.dto.ChampionDto;
import soup.japopgg.champions.service.ChampionService;

@Controller
@RequiredArgsConstructor
public class ChampionController {
    private final ChampionService championService;

    @RequestMapping("/champions")
    public String mainChmpPage(){
        return "mainChampionPage";
    }

    @RequestMapping("/champions/{championName}")
    @ResponseBody
    public Mono<ChampionDto> printChampionVerJson(@PathVariable("championName") String championName){
        return championService.championInfo(championName);
    }
}
