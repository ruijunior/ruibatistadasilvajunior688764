package br.com.rbsj.seplag.infrastructure.api.regional;

import br.com.rbsj.seplag.application.regional.sync.SyncRegionaisOutput;

public record SyncRegionaisResponse(
        int inseridas,
        int inativadas,
        int recriadas
) {
    public static SyncRegionaisResponse from(SyncRegionaisOutput output) {
        return new SyncRegionaisResponse(
                output.inseridas(),
                output.inativadas(),
                output.recriadas()
        );
    }
}
