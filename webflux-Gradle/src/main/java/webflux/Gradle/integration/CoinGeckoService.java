package webflux.Gradle.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
public class CoinGeckoService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CoinGeckoService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    private static final String BASE_URL = "https://api.coingecko.com/api/v3";
    private static final String COINS_ENDPOINT = "/coins/markets";


    public Mono<List> getDataFromApi(int page, int perPage) {
        WebClient webClient = webClientBuilder.baseUrl(BASE_URL).build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(COINS_ENDPOINT)
                        .queryParam("vs_currency", "usd")
                        .queryParam("order", "market_cap_desc")
                        .queryParam("per_page", perPage)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .onStatus(status -> status.value() == 429, clientResponse -> {
                    System.out.println("Rate limit exceeded, retrying...");
                    return Mono.error(new RuntimeException("Rate limit exceeded"));
                })
                .bodyToMono(List.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
                .doOnError(e -> System.out.println("Error fetching data: " + e.getMessage()));
    }


    public Mono<List<Object>> getAllDataFromApi(int totalRecords, int perPage) {
        int totalPages = (int) Math.ceil((double) totalRecords / perPage);


        Flux<Object> dataFlux = Flux.range(1, totalPages)
                .flatMap(page -> getDataFromApi(page, perPage));

        return dataFlux.collectList();
    }
}
