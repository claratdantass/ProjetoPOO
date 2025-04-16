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
    

    private List<Gato> gatos;

    public MainController() {
        this.gatoService = new GatoService();
        this.gatosObservable = FXCollections.observableArrayList();
        this.gatos = gatoService.obterTodosGatos();
        atualizarListaGatos();
    }
    
    public ObservableList<Gato> getGatosObservable() {
        return gatosObservable;
    }
    
    public void atualizarListaGatos() {
        gatosObservable.clear();
        gatosObservable.addAll(gatoService.obterTodosGatos());
    }
    
    public void adicionarGato(String nome, String corPelagem, String sexo, boolean adotado, boolean castrado, String local, String descricao, String caminhoImagem) {
        Gato gato = new Gato();
        gato.setNome(nome);
        gato.setLocal(local);
        gato.setDescricao(descricao);
        gato.setCaminhoImagem(caminhoImagem);
        
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
    
    public ObservableList<Gato> buscarGatosPorNome(String nome) {
        ObservableList<Gato> resultado = FXCollections.observableArrayList();
        resultado.addAll(gatoService.buscarPorNome(nome));
        return resultado;
    }
    
    public ObservableList<Gato> buscarGatosPorLocal(String local) {
        ObservableList<Gato> resultado = FXCollections.observableArrayList();
        resultado.addAll(gatoService.buscarPorLocal(local));
        return resultado;
    }

    public List<Gato> buscarGatosPorFiltros(String nome, boolean sexoMacho, boolean sexoFemea, String descricao) {
        List<Gato> gatosFiltrados = new ArrayList<>();
        for (Gato gato : gatoService.obterTodosGatos()) {
            boolean nomeMatch = nome.isEmpty() || gato.getNome().toLowerCase().contains(nome.toLowerCase());
            boolean sexoMatch = (sexoMacho && gato.getSexo().equals("Macho")) || (sexoFemea && gato.getSexo().equals("Fêmea"));
            boolean descricaoMatch = descricao.isEmpty() || gato.getDescricao().toLowerCase().contains(descricao.toLowerCase());

            if (nomeMatch && sexoMatch && descricaoMatch) {
                gatosFiltrados.add(gato);
            }
        }
        return gatosFiltrados;
    }

    
}
