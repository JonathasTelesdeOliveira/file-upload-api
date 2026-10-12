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
    @Operation(summary = "Inserir", description = "Ler aquivo CVS")
    @ApiResponse(responseCode = "200", description = "leitura com sucesso!")
    @ApiResponse(responseCode = "400", description = "erro de requisisão.")
    @ApiResponse(responseCode = "401", description = "arquivo não enviado")
    public ResponseEntity<List<TransacaoKafkaDTO>> UploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        return ResponseEntity.ok(csvFilesService.uploadCSVFile(file, model));
    }
}
