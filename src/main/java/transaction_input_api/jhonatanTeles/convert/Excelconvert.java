package transaction_input_api.jhonatanTeles.convert;

import lombok.Builder;
import org.springframework.stereotype.Component;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;
import transaction_input_api.jhonatanTeles.dto.in.ExelTradeSpotBinanceIN;
import transaction_input_api.jhonatanTeles.enums.Lado;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Component
public class Excelconvert {

    private BigDecimal parsebigdecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        String blocoNumero = valor.trim().split("\\s+")[0];
        String numeroLimpo = blocoNumero.replace(",", ".").replaceAll("[^0-9.-]", "");

        if (numeroLimpo.isEmpty() || numeroLimpo.equals("-")) {
            return BigDecimal.ZERO;
        }
        // Retorna direto. Não precisa do operador ternário no final!
        BigDecimal val =  new BigDecimal(numeroLimpo);
        return (val.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : val;
    }
    private String parsetexto(String entrada){
        if(entrada == null || entrada.trim().isEmpty()) return "";
        return entrada.replaceAll("[^A-Z]", "");
    }

     public List<TransacaoKafkaDTO> ExelTradeList_p_TransKafkaDTOList(List<ExelTradeSpotBinanceIN> exelIN) {
        return exelIN.stream()
                .map(this::ExelTrade_p_TransKafkaDTO)
                .collect(Collectors.toList());
    }

    public TransacaoKafkaDTO ExelTrade_p_TransKafkaDTO(ExelTradeSpotBinanceIN exelIN){
        return TransacaoKafkaDTO.builder()
                .dataSped(exelIN.getTempo() != null ? exelIN.getTempo() : null)
                .symbol(exelIN.getPar())
                .lado(parseLado(exelIN.getLado()))
                .price(parsebigdecimal(exelIN.getPrice()))
                .quantycoinExe(parsebigdecimal(exelIN.getExecutado()))
                .quantityDol(parsebigdecimal(exelIN.getQuantidade()))
                .symbol_quantity_Dol(parsetexto(exelIN.getQuantidade()))
                .taxa(parsebigdecimal(exelIN.getTaxa()))
                .symbol_taxa(parsetexto(exelIN.getTaxa()))
                .valorBrl(null)
                .dataSpedUSDTBRL(null)
                .build();
    }

    private Lado parseLado(String lado) {
        if (lado == null || lado.trim().isEmpty()) {
            return null;
        }
        try {
            return Lado.valueOf(lado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }



}
