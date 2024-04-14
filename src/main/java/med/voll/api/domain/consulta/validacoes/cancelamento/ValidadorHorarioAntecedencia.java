package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.repository.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

	private final ConsultaRepository consultaRepository;

	public ValidadorHorarioAntecedencia(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}

	public void validar(DadosCancelamentoConsulta dados) {
		Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
		LocalDateTime agora = LocalDateTime.now();
		Long diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();
		if(diferencaEmHoras < 24) {
			throw new ValidacaoException("Consulta deve ser cancelada com antecedencia minima de 24 horas");
		}
	}
}
