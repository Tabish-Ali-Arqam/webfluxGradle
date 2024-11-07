package webflux.Gradle.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class CoinGeckoController {

    private final CoinGeckoService coinGeckoService;

    @Autowired
    public CoinGeckoController(CoinGeckoService coinGeckoService) {
        this.coinGeckoService = coinGeckoService;
    }
    @GetMapping("/coins")
    public Mono<ResponseEntity<List>> getCoins(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "250") int perPage) {

        // Call the CoinGecko service to get the data with the specified or default page and perPage values
        return coinGeckoService.getDataFromApi(page, perPage)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    // Handle rate limit and other errors
                    if (e.getMessage().contains("Rate limit exceeded")) {
                        return Mono.just(ResponseEntity.status(429).body(List.of("Rate limit exceeded, please try again later.")));
                    } else {
                        return Mono.just(ResponseEntity.status(500).body(List.of("Error fetching data: " + e.getMessage())));
                    }
                });
    }



}
