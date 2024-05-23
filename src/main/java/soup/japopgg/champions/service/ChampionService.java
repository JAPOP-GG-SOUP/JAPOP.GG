package soup.japopgg.champions.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import soup.japopgg.champions.dto.ChampionDto;

@Service
@RequiredArgsConstructor
public class ChampionService {
    private final WebClient webClient;

    public Mono<String> championInfo(String championName){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.
                        path("{championName}.json")
                        .build(championName))
                .retrieve()
                .bodyToMono(String.class);
    }
}
