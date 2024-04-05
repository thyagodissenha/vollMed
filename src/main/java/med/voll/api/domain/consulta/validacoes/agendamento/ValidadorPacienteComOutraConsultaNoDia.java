package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteComOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta{

	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosAgendamentoConsulta dados) {
		Boolean consulta =  repository.existsByPacienteIdAndDataAndMotivoCancelamentoIsNull(dados.idPaciente(), dados.data());
		if(consulta)
			throw new ValidacaoException("Paciente já possui consulta para esse dia");
	}
}
