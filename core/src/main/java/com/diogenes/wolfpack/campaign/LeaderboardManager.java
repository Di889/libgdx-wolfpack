package com.diogenes.wolfpack.campaign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;

public class LeaderboardManager {

    private static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxcwrd1DVAExW9zmpkNx-3aCkZwvTuDAgWyyhIxhXavZT65HEelqExc85YPLqOInlly/exec";

    public void enviarPontuacao(String playerName, int survivedDays, boolean hasWon) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(SCRIPT_URL);
        request.setHeader("Content-Type", "application/json");

        String jsonPayload = String.format(
            "{\"name\":\"%s\", \"days\":%d, \"isVictory\":%b}",
            playerName, survivedDays, hasWon
        );
        request.setContent(jsonPayload);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("NET_SUCCESS", "Dados salvos na planilha. Status: " + httpResponse.getStatus().getStatusCode());
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("NET_ERROR", "Falha ao enviar dados para o servidor: " + t.getMessage());
            }

            @Override
            public void cancelled() {
                Gdx.app.log("NET_CANCEL", "Requisição cancelada.");
            }
        });
    }
}
