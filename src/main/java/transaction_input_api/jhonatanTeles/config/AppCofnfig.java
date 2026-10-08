package transaction_input_api.jhonatanTeles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

public class AppCofnfig {
    @Configuration
    public class AppConfig {

        @Bean
        public RestClient restClient() {
            return RestClient.builder()
                    .baseUrl("https://api.binance.com") // Ajuste para o domínio base da API que você está chamando
                    .build();
        }
    }
}



