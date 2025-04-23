package com.catalogogatos.test;

import com.catalogogatos.model.Gato;
import com.catalogogatos.service.GatoService;

public class TestGatoService {
    public static void main(String[] args) {
        GatoService service = new GatoService();

        Gato gato = new Gato();
        gato.setNome("Frajola");
        gato.setDescricao("Gato preto e branco, brincalhão");
        gato.setSexo("Macho");
        gato.setCastrado(true);
        gato.setAdotado(false);

        Gato inserted = service.adicionarGato(gato);
        System.out.println("Inserted cat: " + inserted);

        System.out.println("All cats in database:");
        for (Gato g : service.obterTodosGatos()) {
            System.out.println(g);
        }
    }
}