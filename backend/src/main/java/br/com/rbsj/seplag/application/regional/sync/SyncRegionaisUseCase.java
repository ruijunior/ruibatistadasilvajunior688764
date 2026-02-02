package br.com.rbsj.seplag.application.regional.sync;

import br.com.rbsj.seplag.domain.regional.Regional;
import br.com.rbsj.seplag.domain.regional.RegionalGateway;

import java.util.*;
import java.util.stream.Collectors;

public class SyncRegionaisUseCase {

    private final RegionalGateway regionalGateway;
    private final RegionalExternaGateway regionalExternaGateway;

    public SyncRegionaisUseCase(
            RegionalGateway regionalGateway,
            RegionalExternaGateway regionalExternaGateway
    ) {
        this.regionalGateway = Objects.requireNonNull(regionalGateway);
        this.regionalExternaGateway = Objects.requireNonNull(regionalExternaGateway);
    }

    public SyncRegionaisOutput execute() {
        var regionaisExternas = regionalExternaGateway.fetchRegionaisFromExternalAPI();
        var regionaisInternas = regionalGateway.findAll();

        var idsExternos = regionaisExternas.stream()
                .map(RegionalExternaDTO::id)
                .collect(Collectors.toSet());

        var regionaisInternasPorIdExterno = regionaisInternas.stream()
                .filter(r -> r.getIdExterno() != null)
                .collect(Collectors.toMap(
                        Regional::getIdExterno,
                        r -> r,
                        (r1, r2) -> r1
                ));

        var regionaisParaSalvar = new ArrayList<Regional>();
        int inseridas = 0;
        int inativadas = 0;
        int recriadas = 0;

        for (var regionalExterna : regionaisExternas) {
            var regionalInterna = regionaisInternasPorIdExterno.get(regionalExterna.id());

            if (regionalInterna == null) {
                var novaRegional = Regional.newRegional(regionalExterna.id(), regionalExterna.nome());
                regionaisParaSalvar.add(novaRegional);
                inseridas++;
            } else if (!regionalInterna.getNome().equals(regionalExterna.nome())) {
                regionalInterna.desativar();
                regionaisParaSalvar.add(regionalInterna);
                
                var novaRegional = Regional.newRegional(regionalExterna.id(), regionalExterna.nome());
                regionaisParaSalvar.add(novaRegional);
                recriadas++;
            }
        }

        for (var regionalInterna : regionaisInternas) {
            Integer idExterno = regionalInterna.getIdExterno();
            if (idExterno != null && !idsExternos.contains(idExterno) && regionalInterna.isAtivo()) {
                regionalInterna.desativar();
                regionaisParaSalvar.add(regionalInterna);
                inativadas++;
            }
        }

        if (!regionaisParaSalvar.isEmpty()) {
            regionalGateway.saveAll(regionaisParaSalvar);
        }

        return new SyncRegionaisOutput(inseridas, inativadas, recriadas);
    }
}
