package transaction_input_api.jhonatanTeles.convert;


import lombok.Builder;
import org.springframework.stereotype.Component;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;
import transaction_input_api.jhonatanTeles.dto.in.CsvTradeSpotBinanceIN;
import transaction_input_api.jhonatanTeles.enums.Lado;
import transaction_input_api.jhonatanTeles.exeptions.LocalDateTimeExeption;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Component
public class Csvconvert {

    public List<TransacaoKafkaDTO> in_p_transacaoKafkaDTOList(List<CsvTradeSpotBinanceIN> csvIn) {
        return csvIn.stream()
                .map(this::in_p_transacaoKafkaDTO)
                .collect(Collectors.toList());
    }

    public LocalDateTime parselocaldatetime(String dateIn) {
        if(dateIn == null ||  dateIn.isEmpty()) return null;
        String data = dateIn.trim().replaceAll("\\s+", " ");
        DateTimeFormatter format= new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM-dd")
                                .optionalStart()
                                .appendPattern("[' ']['T']")
                                .appendPattern("HH:mm:ss")
                                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                                .optionalEnd()
                                .toFormatter();
        try{
            return LocalDateTime.parse(data, format) ;
        } catch (LocalDateTimeExeption e) {
            throw new LocalDateTimeExeption("Erro ao realizar o parseTimestamp: " + e);
        }
    }
    public TransacaoKafkaDTO in_p_transacaoKafkaDTO(CsvTradeSpotBinanceIN csvIn){
        return TransacaoKafkaDTO.builder()
                .dataSped(parselocaldatetime(csvIn.getTempo() != null ? csvIn.getTempo() : null))
                .symbol(csvIn.getPar())
                .lado(parseLado(csvIn.getLado()))
                .price(parsebigdecimal(csvIn.getPrice()))
                .quantycoinExe(parsebigdecimal(csvIn.getExecutado()))
                .quantityDol(parsebigdecimal(csvIn.getQuantidade()))
                .symbol_quantity_Dol(parsetexto(csvIn.getQuantidade()))
                .taxa(parsebigdecimal(csvIn.getTaxa()))
                .symbol_taxa(parsetexto(csvIn.getTaxa()))
                .build();
   }

   private BigDecimal parsebigdecimal(String valor) {
       if (valor == null || valor.trim().isEmpty()) return BigDecimal.ZERO;
//       String numeros = valor.replace(",", ".").replaceAll("[^0-9.-]", "");
       String numeros = valor.replace(",", ".").replaceAll("[^0-9.-]", "");


       BigDecimal val = new BigDecimal(numeros.split("\\s+")[0]);
       return (val.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : val;
   }
    private String parsetexto(String entrada){
        if(entrada == null || entrada.trim().isEmpty()) {
            return null;
        }
        // Remove tudo o que NÃO for letra (de a-z ou A-Z), converte para maiúsculo e remove espaços
        String textoExtraido = entrada.replaceAll("[^a-zA-Z]", "").toUpperCase().trim();
        return textoExtraido.isEmpty() ? null : textoExtraido;
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