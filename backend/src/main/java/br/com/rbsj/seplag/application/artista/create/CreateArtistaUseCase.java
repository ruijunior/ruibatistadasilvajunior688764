package br.com.rbsj.seplag.application.artista.create;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaGateway;

import java.util.Objects;

public class CreateArtistaUseCase {

    private final ArtistaGateway gateway;

    public CreateArtistaUseCase(ArtistaGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public CreateArtistaOutput execute(CreateArtistaCommand command) {
        var artista = Artista.newArtista(command.nome(), command.tipo());
        artista.validate();
        
        var created = this.gateway.create(artista);
        
        return CreateArtistaOutput.from(created);
    }
}
