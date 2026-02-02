package br.com.rbsj.seplag.application.artista.update;

import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.artista.ArtistaID;

import java.util.Objects;

public class UpdateArtistaUseCase {

    private final ArtistaGateway gateway;

    public UpdateArtistaUseCase(ArtistaGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public UpdateArtistaOutput execute(UpdateArtistaCommand command) {
        var artistaId = ArtistaID.from(command.id());
        
        var artista = this.gateway.findById(artistaId)
                .orElseThrow(() -> new IllegalArgumentException("Artista não encontrado: " + command.id()));
        
        artista.update(command.nome(), command.tipo());
        artista.validate();
        
        var updated = this.gateway.update(artista);
        
        return UpdateArtistaOutput.from(updated);
    }
}
