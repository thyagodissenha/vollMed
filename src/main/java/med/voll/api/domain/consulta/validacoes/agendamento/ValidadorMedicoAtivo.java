package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.repository.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{

	private final MedicoRepository medicoRepository;

	public ValidadorMedicoAtivo(MedicoRepository medicoRepository) {
		this.medicoRepository = medicoRepository;
	}
	
	public void validar(DadosAgendamentoConsulta dados) {
		Boolean medicoAtivo = medicoRepository.existsByIdAndAtivo(dados.idMedico());
		if(!medicoAtivo)
			throw new ValidacaoException("O medico deve estar ativo");
	}
}
