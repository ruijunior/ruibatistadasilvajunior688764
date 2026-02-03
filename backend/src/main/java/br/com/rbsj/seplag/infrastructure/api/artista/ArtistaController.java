package br.com.rbsj.seplag.infrastructure.api.artista;

import br.com.rbsj.seplag.application.artista.create.CreateArtistaCommand;
import br.com.rbsj.seplag.application.artista.create.CreateArtistaUseCase;
import br.com.rbsj.seplag.application.artista.retrieve.get.GetArtistaByIdQuery;
import br.com.rbsj.seplag.application.artista.retrieve.get.GetArtistaByIdUseCase;
import br.com.rbsj.seplag.application.artista.retrieve.list.ListArtistasQuery;
import br.com.rbsj.seplag.application.artista.retrieve.list.ListArtistasUseCase;
import br.com.rbsj.seplag.application.artista.update.UpdateArtistaCommand;
import br.com.rbsj.seplag.application.artista.update.UpdateArtistaUseCase;
import br.com.rbsj.seplag.infrastructure.api.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/artistas")
public class ArtistaController {

    private final CreateArtistaUseCase createUseCase;
    private final UpdateArtistaUseCase updateUseCase;
    private final GetArtistaByIdUseCase getByIdUseCase;
    private final ListArtistasUseCase listUseCase;

    public ArtistaController(
            CreateArtistaUseCase createUseCase,
            UpdateArtistaUseCase updateUseCase,
            GetArtistaByIdUseCase getByIdUseCase,
            ListArtistasUseCase listUseCase
    ) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
    }

    @PostMapping
    public ResponseEntity<ArtistaResponse> create(@RequestBody CreateArtistaRequest request) {
        var command = CreateArtistaCommand.with(request.nome(), request.tipo());
        var output = createUseCase.execute(command);
        
        return ResponseEntity
                .created(URI.create("/api/v1/artistas/" + output.id()))
                .body(new ArtistaResponse(
                        output.id(),
                        output.nome(),
                        output.tipo(),
                        output.criadoEm(),
                        output.criadoEm()
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponse> update(
            @PathVariable String id,
            @RequestBody UpdateArtistaRequest request
    ) {
        var command = UpdateArtistaCommand.with(id, request.nome(), request.tipo());
        var output = updateUseCase.execute(command);
        
        return ResponseEntity.ok(new ArtistaResponse(
                output.id(),
                output.nome(),
                output.tipo(),
                null,
                output.atualizadoEm()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponse> getById(@PathVariable String id) {
        var query = GetArtistaByIdQuery.with(id);
        var output = getByIdUseCase.execute(query);
        var response = ArtistaResponse.from(output);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ArtistaListResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        var query = ListArtistasQuery.with(page, perPage, search, sort, dir);
        var output = listUseCase.execute(query);
        
        var items = output.items().stream()
                .map(ArtistaListResponse::from)
                .toList();
        
        var response = new PaginatedResponse<>(
                output.currentPage(),
                output.perPage(),
                output.total(),
                items
        );
        
        return ResponseEntity.ok(response);
    }
}
