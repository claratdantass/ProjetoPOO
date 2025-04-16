package com.catalogogatos.service;

import com.catalogogatos.model.Gato;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GatoService {
    private static final String ARQUIVO_GATOS = "gatos.dat";
    private static Long proximoId = 1L;
    private List<Gato> gatos;
    
    public GatoService() {
        this.gatos = carregarGatos();
        if (this.gatos.size() > 0) {
            proximoId = this.gatos.stream()
                    .mapToLong(Gato::getId)
                    .max()
                    .getAsLong() + 1;
        }
    }
    

    public Gato adicionarGato(Gato gato) {
        gato.setId(proximoId++);
        gatos.add(gato);
        salvarGatos();
        return gato;
    }
    

    public List<Gato> obterTodosGatos() {
        return new ArrayList<>(gatos);
    }
    

    public Optional<Gato> buscarPorId(Long id) {
        return gatos.stream()
                .filter(gato -> gato.getId().equals(id))
                .findFirst();
    }
    

    public boolean atualizarGato(Gato gato) {
        for (int i = 0; i < gatos.size(); i++) {
            if (gatos.get(i).getId().equals(gato.getId())) {
                gatos.set(i, gato);
                salvarGatos();
                return true;
            }
        }
        return false;
    }
    

    public boolean excluirGato(Long id) {
        boolean removido = gatos.removeIf(gato -> gato.getId().equals(id));
        if (removido) {
            salvarGatos();
        }
        return removido;
    }
    

    public List<Gato> buscarPorNome(String nome) {
        return gatos.stream()
                .filter(gato -> gato.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
    

    public List<Gato> buscarPorLocal(String local) {
        return gatos.stream()
                .filter(gato -> gato.getLocal().toLowerCase().contains(local.toLowerCase()))
                .collect(Collectors.toList());
    }
    

    private void salvarGatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_GATOS))) {
            oos.writeObject(gatos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar gatos: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Gato> carregarGatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_GATOS))) {
            return (List<Gato>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de gatos não encontrado. Criando nova lista.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar gatos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}