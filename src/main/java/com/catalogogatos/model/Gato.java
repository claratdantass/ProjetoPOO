package com.catalogogatos.model;

import java.io.Serializable;

public class Gato extends Animal implements Adotavel{
    private static final long serialVersionUID = 1L;
    
    private String local;
    private String caminhoImagem;
    private String corPelagem;
    private boolean adotado;  
    private boolean castrado; 
    private int idade;
    private String saude;
    private boolean vacinado;
    private byte[] imagem;
    private boolean disponivelParaAdocao;

    public Gato() {
        super();
    }

    public Gato(Long id, String nome, String sexo, String descricao, String local, String caminhoImagem, String corPelagem, boolean adotado, boolean castrado) {
        super(id, nome, sexo, descricao);
        this.local = local;
        this.caminhoImagem = caminhoImagem;
        this.corPelagem = corPelagem;
        this.adotado = adotado;
        this.castrado = castrado;
    }


    @Override
    public void emitirSom() {
        System.out.println("Miau!");
    }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getCaminhoImagem() { return caminhoImagem; }
    public void setCaminhoImagem(String caminhoImagem) { this.caminhoImagem = caminhoImagem; }

    public String getCorPelagem() { return corPelagem; }
    public void setCorPelagem(String corPelagem) { this.corPelagem = corPelagem; }

    public boolean isAdotado() { return adotado; }
    public void setAdotado(boolean adotado) { this.adotado = adotado; }

    public boolean isCastrado() { return castrado; }
    public void setCastrado(boolean castrado) { this.castrado = castrado; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getSaude() { return saude; }
    public void setSaude(String saude) { this.saude = saude; }

    public boolean isVacinado() { return vacinado; }
    public void setVacinado(boolean vacinado) { this.vacinado = vacinado; }

    public byte[] getImagem() { return imagem; }
    public void setImagem(byte[] imagem) { this.imagem = imagem; }

    public boolean isDisponivelParaAdocao() { return disponivelParaAdocao; }
    public void setDisponivelParaAdocao(boolean disponivelParaAdocao) { this.disponivelParaAdocao = disponivelParaAdocao; }

    @Override
    public String toString() {
        return "Gato [id=" + getId() + ", nome=" + getNome() + ", local=" + local + ", sexo=" + getSexo() + ", corPelagem=" + corPelagem + ", adotado=" + adotado + ", castrado=" + castrado + "]";
    }

    @Override
    public void adotar() {
        this.adotado = true;
        this.disponivelParaAdocao = false;
    }

}