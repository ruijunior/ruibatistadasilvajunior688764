package br.com.rbsj.seplag.infrastructure.api.album;

import br.com.rbsj.seplag.application.album.create.CreateAlbumCommand;
import br.com.rbsj.seplag.application.album.create.CreateAlbumUseCase;
import br.com.rbsj.seplag.application.album.updatecapa.UpdateAlbumCapaCommand;
import br.com.rbsj.seplag.application.album.updatecapa.UpdateAlbumCapaUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/albuns")
public class AlbumController {

    private final CreateAlbumUseCase createUseCase;
    private final UpdateAlbumCapaUseCase updateCapaUseCase;

    public AlbumController(
            CreateAlbumUseCase createUseCase,
            UpdateAlbumCapaUseCase updateCapaUseCase
    ) {
        this.createUseCase = createUseCase;
        this.updateCapaUseCase = updateCapaUseCase;
    }

    @PostMapping
    public ResponseEntity<AlbumResponse> create(@RequestBody CreateAlbumRequest request) {
        var command = CreateAlbumCommand.with(request.titulo(), request.anoLancamento());
        var output = createUseCase.execute(command);
        
        return ResponseEntity
                .created(URI.create("/api/v1/albuns/" + output.id()))
                .body(new AlbumResponse(
                        output.id(),
                        output.titulo(),
                        output.anoLancamento(),
                        null, // urlCapa ainda não existe na criação
                        output.criadoEm(),
                        output.criadoEm()
                ));
    }

    @PostMapping("/{id}/capa")
    public ResponseEntity<Void> uploadCapa(
            @PathVariable String id,
            @RequestParam String urlCapa
    ) {
        var command = UpdateAlbumCapaCommand.with(id, urlCapa);
        updateCapaUseCase.execute(command);
        
        return ResponseEntity.noContent().build();
    }
}
