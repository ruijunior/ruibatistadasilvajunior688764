package br.com.rbsj.seplag.application.regional.retrieve.list;

import br.com.rbsj.seplag.domain.regional.RegionalGateway;

import java.util.List;
import java.util.Objects;

public class ListRegionaisUseCase {

    private final RegionalGateway gateway;

    public ListRegionaisUseCase(RegionalGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public List<RegionalListOutput> execute() {
        return this.gateway.findAll()
                .stream()
                .map(RegionalListOutput::from)
                .toList();
    }
}
