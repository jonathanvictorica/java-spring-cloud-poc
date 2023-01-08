package com.jmg.primemusic.suggestion;

import com.jmg.primemusic.suggestion.controller.GetSugerenciaByPopularidadResponse;
import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpotifyTest extends BaseAPI {

    @Test
    @Tag("functional")
    @DisplayName("Testear toda la aplicacion con api gateway")
    void testingAll() {

        given().
                contentType(ContentType.JSON).
                body(
                        new MusicRequest(158L, "Nombre", "Cantante", 1000)
                ).
                when().post("/api/v1/musics");

        given().
                contentType(ContentType.JSON).
                body(
                        new PlayListRequest(365L, "playlistNueva", 1000)
                ).
                when().post("/api/v1/playlists");

        GetSugerenciaByPopularidadResponse responseOnline = given()
                .pathParam("popularidad", "MUY_POPULAR")
                .when().get("/api/v1/suggestions/online/{popularidad}")
                .andReturn().body().as(GetSugerenciaByPopularidadResponse.class);

        GetSugerenciaByPopularidadResponse responseOffline = given()
                .pathParam("popularidad", "MUY_POPULAR")
                .when().get("/api/v1/suggestions/offline/{popularidad}")
                .andReturn().body().as(GetSugerenciaByPopularidadResponse.class);





        assertEquals(responseOnline.getMusics().size(), 1);
        assertEquals(responseOnline.getPlaylist().size(), 1);

        assertEquals(responseOffline.getMusics().size(), 1);
        assertEquals(responseOffline.getPlaylist().size(), 1);

        given()
                .pathParam("id", 158L).
                when().delete("/api/v1/musics/{id}");
        given()
                .pathParam("id", 365L).
                when().delete("/api/v1/playlists/{id}");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicRequest {
        private Long musicId;
        private String name;
        private String singerName;
        private Integer mgCount = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayListRequest {
        private Long playListId;
        private String name;
        private Integer mgCount = 0;
    }
}
