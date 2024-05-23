package soup.japopgg.champions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final String DDRAGON_URL = "http://ddragon.leagueoflegends.com/cdn/14.10.1/data/ko_KR/champion/";
    ///{championId}.json"

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl(DDRAGON_URL)
                .build();
    }
}
