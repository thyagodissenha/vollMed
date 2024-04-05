package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;
	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadorCancelamento;
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente()))
			throw new ValidacaoException("Id do paciente informado não existe");		
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
			throw new ValidacaoException("Id do medico informado não existe");
		
		validadoresAgendamento.forEach(v -> v.validar(dados));
		
		Medico medico = escolherMedico(dados);
		if(medico == null)
			throw new ValidacaoException("Não existe médico disponível nessa dara");
		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());		
		Consulta consulta = new Consulta(null, medico, paciente, dados.data(), null);
		
		consultaRepository.save(consulta);		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null)
			return medicoRepository.getReferenceById(dados.idMedico());
		if(dados.especialidade() == null)
			throw new ValidacaoException("Especialidade é obrigatorio quando o médico não for escolhido");
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}

	public void cancelar(@Valid DadosCancelamentoConsulta dados) {
		if(!consultaRepository.existsById(dados.idConsulta()))
			throw new ValidacaoException("Id  da consulta informado não existe");
		
		validadorCancelamento.forEach(v -> v.validar(dados));
		
		Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());		
	}
}