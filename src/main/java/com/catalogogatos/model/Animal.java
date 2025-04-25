package com.catalogogatos.model;

public abstract class Animal {
    private Long id;
    private String nome;
    private String sexo;
    private String descricao;

    public Animal() {}

    public Animal(Long id, String nome, String sexo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.descricao = descricao;
    }

    public abstract void emitirSom();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}