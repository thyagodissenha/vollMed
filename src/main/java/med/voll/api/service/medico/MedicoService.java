package med.voll.api.service.medico;

import jakarta.transaction.Transactional;
import med.voll.api.domain.medico.*;
import med.voll.api.repository.medico.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public DadosDetalhamentoMedico cadastrar(DadosCadastroMedico dadosCadastroMedico) {
        Medico medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        return new DadosDetalhamentoMedico(medico);
    }

    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @Transactional
    public DadosDetalhamentoMedico atualizar(DadosAtualizarMedico dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return new DadosDetalhamentoMedico(medico);
    }

    @Transactional
    public void excluir(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }

    public DadosDetalhamentoMedico detalhar(Long id) {
        return new DadosDetalhamentoMedico(medicoRepository.getReferenceById(id));
    }
}
