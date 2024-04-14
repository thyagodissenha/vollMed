package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.repository.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{

	private final PacienteRepository pacienteRepository;

	public ValidadorPacienteAtivo(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}
	
	public void validar(DadosAgendamentoConsulta dados) {

		Paciente paciente = pacienteRepository.findByIdAndAtivo(dados.idPaciente());
		if(paciente == null)
			throw new ValidacaoException("Paciente deve estar ativo para marcar consulta");		
	}
}
