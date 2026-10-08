package transaction_input_api.jhonatanTeles.controller;


import transaction_input_api.jhonatanTeles.busines.UploadFilesService;
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

    private final UploadFilesService uploadService;


    @PostMapping(value = "/Enviar-Exel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "buscar", description = "buscar Lista de códogo de Cripto")
    @ApiResponse(responseCode = "200", description = "busca com sucesso!")
    @ApiResponse(responseCode = "400", description = "erro de requisisão.")
    @ApiResponse(responseCode = "401", description = "Lista não cadastrado")
    public ResponseEntity<List<TransacaoKafkaDTO>> UploadExcelFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadService.uploadExelFile(file));
    }
}


