package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosListagemConsulta(
        Long idPaciente,
        Long idMedico,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime data) {
    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData());
    }
}
