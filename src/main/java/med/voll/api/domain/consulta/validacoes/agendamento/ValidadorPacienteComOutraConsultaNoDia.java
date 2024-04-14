package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteComOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta{

	private final ConsultaRepository consultaRepository;

	public ValidadorPacienteComOutraConsultaNoDia(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}

	@Override
	public void validar(DadosAgendamentoConsulta dados) {
		Boolean consulta =  consultaRepository.existsByPacienteIdAndDataAndMotivoCancelamentoIsNull(dados.idPaciente(), dados.data());
		if(consulta)
			throw new ValidacaoException("Paciente já possui consulta para esse dia");
	}
}
