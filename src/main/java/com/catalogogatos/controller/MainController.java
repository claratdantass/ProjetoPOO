package com.catalogogatos.controller;

import java.util.List;
import java.util.ArrayList;
import com.catalogogatos.model.Gato;
import com.catalogogatos.service.GatoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainController {
    private final GatoService gatoService;
    private final ObservableList<Gato> gatosObservable;

    public MainController() {
        this.gatoService = new GatoService();
        this.gatosObservable = FXCollections.observableArrayList();
        atualizarListaGatos();
    }

    public List<Gato> getGatosObservable() {
        List<Gato> lista = gatoService.obterTodosGatos();
        System.out.println("Gatos do banco: " + lista);
        return lista;
    }

    public void atualizarListaGatos() {
        gatosObservable.clear();
        gatosObservable.addAll(gatoService.obterTodosGatos());
    }

    public void adicionarGato(Gato gato) {
        gatoService.adicionarGato(gato);
        atualizarListaGatos();
    }

    public Gato buscarGatoPorId(Long id) {
        return gatoService.buscarPorId(id).orElse(null);
    }

    public void atualizarGato(Gato gato) {
        gatoService.atualizarGato(gato);
        atualizarListaGatos();
    }

    public void excluirGato(Long id) {
        gatoService.excluirGato(id);
        atualizarListaGatos();
    }

    public List<Gato> buscarGatosPorNome(String nome) {
        return gatoService.buscarPorNome(nome);
    }

    public List<Gato> buscarGatos(String nome, String local) {
        List<Gato> resultado = new ArrayList<>();
        for (Gato gato : gatoService.obterTodosGatos()) {
            boolean nomeMatch = nome.isEmpty() || gato.getNome().toLowerCase().contains(nome.toLowerCase());
            boolean localMatch = local.isEmpty() || gato.getLocal().toLowerCase().contains(local.toLowerCase());
            if (nomeMatch && localMatch) {
                resultado.add(gato);
            }
        }
        return resultado;
    }

    public List<Gato> buscarGatosPorFiltros(String nome, boolean sexoMacho, boolean sexoFemea, String descricao) {
        List<Gato> gatosFiltrados = new ArrayList<>();
        for (Gato gato : gatoService.obterTodosGatos()) {
            boolean nomeMatch = nome.isEmpty() || gato.getNome().toLowerCase().contains(nome.toLowerCase());
            boolean sexoMatch = (!sexoMacho && !sexoFemea) ||
                                (sexoMacho && gato.getSexo().equalsIgnoreCase("Macho")) ||
                                (sexoFemea && gato.getSexo().equalsIgnoreCase("Fêmea"));
            boolean descricaoMatch = descricao.isEmpty() || gato.getDescricao().toLowerCase().contains(descricao.toLowerCase());

            if (nomeMatch && sexoMatch && descricaoMatch) {
                gatosFiltrados.add(gato);
            }
        }
        return gatosFiltrados;
    }
}