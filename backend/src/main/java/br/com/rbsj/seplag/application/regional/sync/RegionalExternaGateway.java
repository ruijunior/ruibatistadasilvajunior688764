package br.com.rbsj.seplag.application.regional.sync;

import java.util.List;

public interface RegionalExternaGateway {
    
    List<RegionalExternaDTO> fetchRegionaisFromExternalAPI();
}
