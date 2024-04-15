package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
		Long id,
		Long idMedico,
		Long idPaciente,
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		LocalDateTime data) {
	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
	}
}
