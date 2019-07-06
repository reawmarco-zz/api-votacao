package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.entity.Pauta;
import br.com.servico.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PautaService {

    private final PautaRepository repository;

    @Autowired
    public PautaService(PautaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PautaDTO salvar(PautaDTO dto) {
        return PautaDTO.toDTO(repository.save(PautaDTO.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public PautaDTO buscarPautaPeloOID(Integer oid) {
        Pauta pauta = repository.getOne(oid);

        if (Optional.ofNullable(pauta).isPresent()) {
            return PautaDTO.toDTO(pauta);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean isPautaValida(Integer oid) {
        return repository.existsByOid(oid);
    }
}