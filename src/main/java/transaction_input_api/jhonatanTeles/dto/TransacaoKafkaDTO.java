package transaction_input_api.jhonatanTeles.dto;

import lombok.*;
import transaction_input_api.jhonatanTeles.enums.Lado;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransacaoKafkaDTO {
    private Long id;
    private LocalDateTime dataSped;
    private Integer tradeId;
    private String symbol;
    private Lado lado;
    private BigDecimal quantycoinExe;
    private BigDecimal quantityDol;
    private String symbol_quantity_Dol;
    private BigDecimal price;
    private BigDecimal taxa;
    private String symbol_taxa;
    private BigDecimal valorBrl;
    private LocalDateTime dataSpedUSDTBRL;
}