package med.voll.api.service.consulta;

import java.time.LocalDateTime;
import java.util.List;

import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.repository.consulta.ConsultaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.repository.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.repository.paciente.PacienteRepository;

@Service
public class ConsultaService {

	private final ConsultaRepository consultaRepository;
	private final MedicoRepository medicoRepository;
	private final PacienteRepository pacienteRepository;
	private final List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;
	private final List<ValidadorCancelamentoDeConsulta> validadorCancelamento;

	public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
						   PacienteRepository pacienteRepository, List<ValidadorAgendamentoDeConsulta> validadoresAgendamento,
						   List<ValidadorCancelamentoDeConsulta> validadorCancelamento){
		this.consultaRepository = consultaRepository;
		this.medicoRepository = medicoRepository;
		this.pacienteRepository = pacienteRepository;
		this.validadoresAgendamento = validadoresAgendamento;
		this.validadorCancelamento = validadorCancelamento;
	}
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente()))
			throw new ValidacaoException("Id do paciente informado não existe");		
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
			throw new ValidacaoException("Id do medico informado não existe");
		
		validadoresAgendamento.forEach(v -> v.validar(dados));
		
		Medico medico = escolherMedico(dados);
		if(medico == null)
			throw new ValidacaoException("Não existe médico disponível nessa data");
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
			throw new ValidacaoException("Id da consulta informado não existe");
		
		validadorCancelamento.forEach(v -> v.validar(dados));
		
		Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());		
	}

	public Page<DadosListagemConsulta> listar(Pageable paginacao) {
		return consultaRepository.findAllBy(paginacao).map(DadosListagemConsulta::new);
	}

	public DadosDetalhamentoConsulta detalhar(@Valid Long id) {
		return new DadosDetalhamentoConsulta(consultaRepository.getReferenceById(id));
	}
}
