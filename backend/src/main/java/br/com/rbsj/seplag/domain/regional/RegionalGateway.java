package br.com.rbsj.seplag.domain.regional;

import java.util.List;

public interface RegionalGateway {

    Regional create(Regional regional);

    Regional findByNome(String nome);

    Regional findByIdExterno(Integer idExterno);

    List<Regional> findAll();

    Regional update(Regional regional);

    void saveAll(List<Regional> regionais);
}
