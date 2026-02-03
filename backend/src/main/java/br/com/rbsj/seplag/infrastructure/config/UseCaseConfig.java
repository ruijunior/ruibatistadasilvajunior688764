package br.com.rbsj.seplag.infrastructure.config;

import br.com.rbsj.seplag.application.album.create.CreateAlbumUseCase;
import br.com.rbsj.seplag.application.album.retrieve.list.ListAlbunsUseCase;
import br.com.rbsj.seplag.application.album.updatecapa.UpdateAlbumCapaUseCase;
import br.com.rbsj.seplag.application.album.upload.GeneratePresignedUrlUseCase;
import br.com.rbsj.seplag.application.artista.create.CreateArtistaUseCase;
import br.com.rbsj.seplag.application.artista.retrieve.get.GetArtistaByIdUseCase;
import br.com.rbsj.seplag.application.artista.retrieve.list.ListArtistasUseCase;
import br.com.rbsj.seplag.application.artista.update.UpdateArtistaUseCase;
import br.com.rbsj.seplag.application.regional.retrieve.list.ListRegionaisUseCase;
import br.com.rbsj.seplag.application.regional.sync.RegionalExternaGateway;
import br.com.rbsj.seplag.application.regional.sync.SyncRegionaisUseCase;
import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.regional.RegionalGateway;
import br.com.rbsj.seplag.domain.storage.StorageGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final ArtistaGateway artistaGateway;
    private final AlbumGateway albumGateway;
    private final RegionalGateway regionalGateway;
    private final RegionalExternaGateway regionalExternaGateway;
    private final StorageGateway storageGateway;

    public UseCaseConfig(
            ArtistaGateway artistaGateway,
            AlbumGateway albumGateway,
            RegionalGateway regionalGateway,
            RegionalExternaGateway regionalExternaGateway,
            StorageGateway storageGateway
    ) {
        this.artistaGateway = artistaGateway;
        this.albumGateway = albumGateway;
        this.regionalGateway = regionalGateway;
        this.regionalExternaGateway = regionalExternaGateway;
        this.storageGateway = storageGateway;
    }

    @Bean
    public CreateArtistaUseCase createArtistaUseCase() {
        return new CreateArtistaUseCase(artistaGateway);
    }

    @Bean
    public UpdateArtistaUseCase updateArtistaUseCase() {
        return new UpdateArtistaUseCase(artistaGateway);
    }

    @Bean
    public GetArtistaByIdUseCase getArtistaByIdUseCase() {
        return new GetArtistaByIdUseCase(artistaGateway);
    }

    @Bean
    public ListArtistasUseCase listArtistasUseCase() {
        return new ListArtistasUseCase(artistaGateway);
    }

    @Bean
    public CreateAlbumUseCase createAlbumUseCase() {
        return new CreateAlbumUseCase(albumGateway);
    }

    @Bean
    public UpdateAlbumCapaUseCase updateAlbumCapaUseCase() {
        return new UpdateAlbumCapaUseCase(albumGateway);
    }

    @Bean
    public ListAlbunsUseCase listAlbunsUseCase() {
        return new ListAlbunsUseCase(albumGateway, storageGateway);
    }
    
    @Bean
    public br.com.rbsj.seplag.application.album.retrieve.get.GetAlbumByIdUseCase getAlbumByIdUseCase() {
        return new br.com.rbsj.seplag.application.album.retrieve.get.GetAlbumByIdUseCase(albumGateway, storageGateway);
    }
    
    @Bean
    public GeneratePresignedUrlUseCase generatePresignedUrlUseCase() {
        return new GeneratePresignedUrlUseCase(storageGateway);
    }

    @Bean
    public ListRegionaisUseCase listRegionaisUseCase() {
        return new ListRegionaisUseCase(regionalGateway);
    }

    @Bean
    public SyncRegionaisUseCase syncRegionaisUseCase() {
        return new SyncRegionaisUseCase(regionalGateway, regionalExternaGateway);
    }
}
