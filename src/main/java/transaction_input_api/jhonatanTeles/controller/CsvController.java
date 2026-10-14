package transaction_input_api.jhonatanTeles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import transaction_input_api.jhonatanTeles.busines.UploadCsvFilesService;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;

import java.util.List;
@RequestMapping("/csv")
@Tag(name = "Binance", description = "Gerenciamento da carteira cripto")
@AllArgsConstructor
@RestController
public class CsvController {
    private final UploadCsvFilesService csvFilesService;

    @PostMapping(value = "/Enviar-Csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Inserir CSV", description = "Lê arquivo CSV de transações da Binance e envia para o Kafka")
    @ApiResponse(responseCode = "200", description = "Arquivo CSV recebido e encaminhado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Arquivo vazio, com formato incorreto ou extensão inválida.")
    @ApiResponse(responseCode = "413", description = "O arquivo excede o tamanho máximo permitido.")
    @ApiResponse(responseCode = "503", description = "Serviço de mensageria (Kafka) indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno ao processar o arquivo CSV.")
    public ResponseEntity<String> UploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {
        csvFilesService.uploadCSVFile(file, model);
        return ResponseEntity.ok("Arquivo Csv recebido e encaminhado com sucesso.");
    }
}
