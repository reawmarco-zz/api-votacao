package br.com.servico.votacao.repository;

import br.com.servico.votacao.entity.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Integer> {

    Boolean existsByOidAssociadoAndOidPauta(Integer oidAssociado, Integer oidPauta);
}
