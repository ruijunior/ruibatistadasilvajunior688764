package br.com.rbsj.seplag.application.artista.retrieve.get;

import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.artista.ArtistaID;

import java.util.Objects;

public class GetArtistaByIdUseCase {

    private final ArtistaGateway gateway;

    public GetArtistaByIdUseCase(ArtistaGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public GetArtistaOutput execute(GetArtistaByIdQuery query) {
        var artistaId = ArtistaID.from(query.id());

        var artista = this.gateway.findById(artistaId)
                .orElseThrow(() -> new IllegalArgumentException("Artista não encontrado: " + query.id()));

        return GetArtistaOutput.from(artista);
    }
}
