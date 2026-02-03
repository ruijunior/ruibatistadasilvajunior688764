package br.com.rbsj.seplag.infrastructure.regional;

import br.com.rbsj.seplag.application.regional.sync.RegionalExternaDTO;
import br.com.rbsj.seplag.application.regional.sync.RegionalExternaGateway;
import br.com.rbsj.seplag.infrastructure.regional.client.RegionalFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegionalApiGateway implements RegionalExternaGateway {

    private final RegionalFeignClient feignClient;

    public RegionalApiGateway(RegionalFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public List<RegionalExternaDTO> fetchRegionaisFromExternalAPI() {
        return feignClient.getRegionais();
    }
}
