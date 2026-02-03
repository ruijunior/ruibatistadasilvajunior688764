package br.com.rbsj.seplag.infrastructure.api.regional;

import br.com.rbsj.seplag.application.regional.retrieve.list.ListRegionaisUseCase;
import br.com.rbsj.seplag.application.regional.retrieve.list.RegionalListOutput;
import br.com.rbsj.seplag.application.regional.sync.SyncRegionaisUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regionais")
public class RegionalController {

    private final ListRegionaisUseCase listUseCase;
    private final SyncRegionaisUseCase syncUseCase;

    public RegionalController(
            ListRegionaisUseCase listUseCase,
            SyncRegionaisUseCase syncUseCase
    ) {
        this.listUseCase = listUseCase;
        this.syncUseCase = syncUseCase;
    }

    @GetMapping
    public ResponseEntity<List<RegionalListOutput>> list() {
        var output = listUseCase.execute();
        return ResponseEntity.ok(output);
    }

    @PostMapping("/sync")
    public ResponseEntity<SyncRegionaisResponse> sync() {
        var output = syncUseCase.execute();
        var response = SyncRegionaisResponse.from(output);
        return ResponseEntity.ok(response);
    }
}
