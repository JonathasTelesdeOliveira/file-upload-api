package transaction_input_api.jhonatanTeles.busines;


import com.alibaba.excel.EasyExcel;

import transaction_input_api.jhonatanTeles.convert.Excelconvert;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;
import transaction_input_api.jhonatanTeles.dto.in.ExelTradeSpotBinanceIN;
import transaction_input_api.jhonatanTeles.exeptions.ConflictExeption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


@Service
public class UploadFilesService {
    private final Excelconvert excelconvert;
    TransactionService transactionService;

    public UploadFilesService(Excelconvert excelconvert,
                              TransactionService transactionService) {
        this.excelconvert = excelconvert;
        this.transactionService = transactionService;
    }

    private boolean isExcelFile(String filename, String contentType) {
        String lowerCaseName = filename.toLowerCase();
        // Validação por Extensão
        boolean validExtension = lowerCaseName.endsWith(".xlsx") || lowerCaseName.endsWith(".xls") || lowerCaseName.endsWith(".csv");

        // Validação opcional por Content-Type (se enviado pelo navegador/cliente)
        boolean validContentType = contentType == null ||
                contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") || // .xlsx
                contentType.equals("application/vnd.ms-excel") ||                                           // .xls
                contentType.equals("text/csv");                                                            // .csv
        return validExtension && validContentType;
    }

    public List<TransacaoKafkaDTO> uploadExelFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Erro ao carregar o arquivo Excel/XLS: o arquivo está vazio ou ausente.");}
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();

        // 2. Validação da extensão do arquivo
        if (filename == null || !isExcelFile(filename, contentType)) {
            throw new IllegalArgumentException("Formato de arquivo inválido. Por favor, envie um arquivo Excel (.xlsx ou .xls).");
        }

        try (InputStream inputStream = file.getInputStream()){
            List<ExelTradeSpotBinanceIN> millisteradianIN =
                    EasyExcel.read(inputStream)
                            .head(ExelTradeSpotBinanceIN.class)
                            .headRowNumber(10)
                            .sheet()
                            .doReadSync();


            List<TransacaoKafkaDTO> tList = excelconvert.ExelTradeList_p_TransKafkaDTOList(millisteradianIN);

            if(tList == null || tList.isEmpty()){
                throw new RuntimeException("Erro ao Converter o arquivo Excel/XLS: o arquivo está vazio ou ausente.");
            }
            transactionService.updadeTransaction(tList);
            return tList;
        }  catch (IOException e) {
        String causa = (e.getCause() != null) ? e.getCause().getMessage() : "Sem causa raiz";
        throw new ConflictExeption("Erro ao tentar ler carteira: " + e.getMessage() + " | Causa: " + causa);
        } catch (Exception e) {
            e.printStackTrace();
        throw new ConflictExeption("Erro ao tentar ler o arquivo de carteira: "+ e.getMessage());
        }
    }
}










