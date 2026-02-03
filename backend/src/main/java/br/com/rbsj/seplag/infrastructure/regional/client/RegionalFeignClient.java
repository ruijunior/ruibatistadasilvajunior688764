package br.com.rbsj.seplag.infrastructure.regional.client;

import br.com.rbsj.seplag.application.regional.sync.RegionalExternaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "regional-api",
        url = "${api.regional.url}"
)
public interface RegionalFeignClient {

    @GetMapping("/api/regionais")
    List<RegionalExternaDTO> getRegionais();
}
