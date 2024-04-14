package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta{

	private final ConsultaRepository consultaRepository;

	public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}
	
	public void validar(DadosAgendamentoConsulta dados) {
		Boolean medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
		if(medicoPossuiOutraConsultaNoMesmoHorario)
			throw new ValidacaoException("Médico já possui consulta agendada nesse mesmo horário");
	}
}
