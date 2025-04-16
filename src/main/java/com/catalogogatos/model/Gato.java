package com.catalogogatos.model;

import java.io.Serializable;

public class Gato implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nome;
    private String local;
    private String descricao;
    private String caminhoImagem;
    private String sexo;
    private String corPelagem;
    private boolean adotado;  
    private boolean castrado; 

    public Gato() {
    }

    public Gato(Long id, String nome, String local, String descricao, String caminhoImagem, String sexo, String corPelagem, boolean adotado, boolean castrado) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.descricao = descricao;
        this.caminhoImagem = caminhoImagem;
        this.sexo = sexo;
        this.corPelagem = corPelagem;
        this.adotado = adotado;
        this.castrado = castrado;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorPelagem() { 
        return corPelagem;
    }

    public void setCorPelagem(String corPelagem) { 
        this.corPelagem = corPelagem;
    }

    public boolean isAdotado() { 
        return adotado;
    }

    public void setAdotado(boolean adotado) { 
        this.adotado = adotado;
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) { 
        this.castrado = castrado;
    }

    @Override
    public String toString() {
        return "Gato [id=" + id + ", nome=" + nome + ", local=" + local + ", sexo=" + sexo + ", corPelagem=" + corPelagem + ", adotado=" + adotado + ", castrado=" + castrado + "]";
    }
}