package transaction_input_api.jhonatanTeles.busines;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import transaction_input_api.jhonatanTeles.convert.Csvconvert;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;
import transaction_input_api.jhonatanTeles.dto.in.CsvTradeSpotBinanceIN;
import transaction_input_api.jhonatanTeles.exeptions.ConflictException;

import java.io.*;
import java.util.List;

@Service
public class UploadCsvFilesService {
    private final Csvconvert csvconvert;
    TransactionService transactionService;

    public UploadCsvFilesService(Csvconvert csvconvert, TransactionService transactionService) {
        this.csvconvert = csvconvert;
        this.transactionService = transactionService;
    }

    public void uploadCSVFile(MultipartFile file, Model model) {
        if (file.isEmpty()) {
            throw new ConflictException("O arquivo CSV enviado está vazio ou ausente.");        }

        try (InputStream inputStream = file.getInputStream();
             BOMInputStream bomInputStream = BOMInputStream.builder()
                     .setInputStream(inputStream)
                     .get();

             Reader reader = new BufferedReader(new InputStreamReader(bomInputStream, java.nio.charset.StandardCharsets.UTF_8))) {
            CsvToBean<CsvTradeSpotBinanceIN> csvToBean =
                    new CsvToBeanBuilder<CsvTradeSpotBinanceIN>(reader)
                    .withType(CsvTradeSpotBinanceIN.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CsvTradeSpotBinanceIN> csvIn = csvToBean.parse();
            if (csvIn.isEmpty()){
                throw new ConflictException("Dados inexistentes: o arquivo CSV está vazio.");
            }
            List<TransacaoKafkaDTO> tradeSpotCsv = csvconvert.in_p_transacaoKafkaDTOList(csvIn);
            if (tradeSpotCsv == null || tradeSpotCsv.isEmpty()){
                throw new ConflictException("Erro na conversão dos dados do CSV.");
            }
            transactionService.updadeTransaction(tradeSpotCsv);
        } catch (IOException e) {
            String causa = (e.getCause() != null) ? e.getCause().getMessage() : "Sem causa raiz";
            throw new ConflictException("Erro ao tentar ler arquivo CSV: " + e.getMessage() + " | Causa: " + causa);
        }
    }
}