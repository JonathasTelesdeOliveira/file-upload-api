package transaction_input_api.jhonatanTeles.busines;
import lombok.extern.slf4j.Slf4j;
import transaction_input_api.jhonatanTeles.dto.TransacaoKafkaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final KafkaTemplate<String, List<TransacaoKafkaDTO>> kafkaTemplate;
    @Autowired
    public TransactionService(KafkaTemplate<String, List<TransacaoKafkaDTO>> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void updadeTransaction(List<TransacaoKafkaDTO> tList) {
        // Dispara a mensagem para processamento assíncrono do BRL
        System.out.println(">>> Enviando lote no Kafka com tamanho: " + tList.size());

        kafkaTemplate.send("transacoes-pendentes", tList)
            .whenComplete((result, ex) -> {
                if (ex != null) {log.error("Erro ao enviar mensagem para Kafka", ex);return;}
                log.info("Mensagem enviada! topic={}, partition={}, offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            }
        );
    }
}








