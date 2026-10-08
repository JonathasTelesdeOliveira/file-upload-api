package transaction_input_api.jhonatanTeles.dto.in;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExelTradeSpotBinanceIN {

    @ExcelProperty("Tempo")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tempo;

    @ExcelProperty("Par")
    private String par;

    @ExcelProperty("Lado")
    private String lado;

    @ExcelProperty("Preço")
    private String price;

    @ExcelProperty("Executado")
    private String executado;

    @ExcelProperty("Quantidade")
    private String quantidade;

    @ExcelProperty("Taxa")
    private String taxa;

}
