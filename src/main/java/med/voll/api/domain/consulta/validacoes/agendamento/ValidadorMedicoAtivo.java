package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{

	@Autowired
	private MedicoRepository repository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		Boolean medicoAtivo = repository.existsByIdAndAtivo(dados.idMedico());
		if(!medicoAtivo)
			throw new ValidacaoException("O medico deve estar ativo");
	}
}
