package transaction_input_api.jhonatanTeles.dto.in;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsvTradeSpotBinanceIN {

    @CsvBindByName(column = "Tempo")
    private String tempo;

    @CsvBindByName(column = "Par")
    private String par;

    @CsvBindByName(column = "Lado")
    private String lado;

    @CsvBindByName(column = "Preço")
    private String price;

    @CsvBindByName(column = "Executado")
    private String executado;

    @CsvBindByName(column = "Quantidade")
    private String quantidade;

    @CsvBindByName(column = "Taxa")
    private String taxa;
}

