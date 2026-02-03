package br.com.rbsj.seplag.infrastructure.api.album;

import br.com.rbsj.seplag.application.album.create.CreateAlbumCommand;
import br.com.rbsj.seplag.application.album.create.CreateAlbumUseCase;
import br.com.rbsj.seplag.application.album.retrieve.list.AlbumListOutput;
import br.com.rbsj.seplag.application.album.retrieve.list.ListAlbunsQuery;
import br.com.rbsj.seplag.application.album.retrieve.list.ListAlbunsUseCase;
import br.com.rbsj.seplag.application.album.updatecapa.UpdateAlbumCapaCommand;
import br.com.rbsj.seplag.application.album.updatecapa.UpdateAlbumCapaUseCase;
import br.com.rbsj.seplag.infrastructure.api.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/albuns")
public class AlbumController {

    private final CreateAlbumUseCase createUseCase;
    private final UpdateAlbumCapaUseCase updateCapaUseCase;
    private final ListAlbunsUseCase listUseCase;

    public AlbumController(
            CreateAlbumUseCase createUseCase,
            UpdateAlbumCapaUseCase updateCapaUseCase,
            ListAlbunsUseCase listUseCase
    ) {
        this.createUseCase = createUseCase;
        this.updateCapaUseCase = updateCapaUseCase;
        this.listUseCase = listUseCase;
    }

    @PostMapping
    public ResponseEntity<AlbumResponse> create(@RequestBody CreateAlbumRequest request) {
        var command = CreateAlbumCommand.with(request.titulo(), request.anoLancamento(), request.artistas());
        var output = createUseCase.execute(command);
        
        return ResponseEntity
                .created(URI.create("/api/v1/albuns/" + output.id()))
                .body(new AlbumResponse(
                        output.id(),
                        output.titulo(),
                        output.anoLancamento(),
                        null,
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

    @GetMapping
    public ResponseEntity<PaginatedResponse<AlbumListOutput>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "titulo") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        var query = new ListAlbunsQuery(page, perPage, search, sort, dir);
        var output = listUseCase.execute(query);

        var response = new PaginatedResponse<>(
                output.currentPage(),
                output.perPage(),
                output.total(),
                output.items()
        );

        return ResponseEntity.ok(response);
    }
}
