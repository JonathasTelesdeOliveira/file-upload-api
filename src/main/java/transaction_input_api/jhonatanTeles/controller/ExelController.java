package transaction_input_api.jhonatanTeles.controller;


import transaction_input_api.jhonatanTeles.busines.UploadExelFilesService;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/exel")
@Tag(name = "Binance", description = "Gerenciamento da carteira cripto")
@AllArgsConstructor
@RestController
public class ExelController {

    private final UploadExelFilesService uploadService;


    @PostMapping(value = "/Enviar-Exel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Inserir Excel", description = "Lê arquivo Exel ou XLSX de transações da Binance e envia para o Kafka")
    @ApiResponse(responseCode = "200", description = "Arquivo Excel recebido e encaminhado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Arquivo vazio, com formato incorreto ou extensão inválida.")
    @ApiResponse(responseCode = "413", description = "O arquivo excede o tamanho máximo permitido.")
    @ApiResponse(responseCode = "503", description = "Serviço de mensageria (Kafka) indisponível.")
    @ApiResponse(responseCode = "500", description = "Erro interno ao processar o arquivo Excel.")
    public ResponseEntity<String> UploadExcelFile(@RequestParam("file") MultipartFile file) {
        uploadService.uploadExelFile(file);
        return ResponseEntity.ok("Arquivo CSV recebido e encaminhado com sucesso.");
    }
}


