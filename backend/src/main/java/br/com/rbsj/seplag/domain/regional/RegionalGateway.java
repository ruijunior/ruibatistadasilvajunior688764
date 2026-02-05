package br.com.rbsj.seplag.domain.regional;

import java.util.List;

public interface RegionalGateway {

    Regional create(Regional regional);

    List<Regional> findAll();

    Regional update(Regional regional);

    void saveAll(List<Regional> regionais);
}
