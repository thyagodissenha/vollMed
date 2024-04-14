package med.voll.api.service.paciente;

import jakarta.transaction.Transactional;
import med.voll.api.domain.paciente.*;
import med.voll.api.repository.paciente.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public DadosDetalhamentoPaciente cadastrar(DadosCadastroPaciente dados) {
        Paciente paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        return new DadosDetalhamentoPaciente(paciente);
    }

    public Page<DadosListagemPaciente> listar(Pageable paginacao) {
        return pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @Transactional
    public DadosDetalhamentoPaciente atualizar(DadosAtualizarPaciente dados) {
        Paciente paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return new DadosDetalhamentoPaciente(paciente);
    }

    @Transactional
    public void excluir(Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }

    public DadosDetalhamentoPaciente detalhar(Long id) {
        return new DadosDetalhamentoPaciente(pacienteRepository.getReferenceById(id));
    }
}
