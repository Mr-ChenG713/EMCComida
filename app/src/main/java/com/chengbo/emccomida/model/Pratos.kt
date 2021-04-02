package com.chengbo.emccomida.model

class Pratos {

    lateinit var id: String
    lateinit var nome: String
    lateinit var preco: String
    lateinit var quantidade: String
    lateinit var descricao: String



    constructor()
    constructor(id: String, nome: String, preco: String, quantidade: String, descricao: String) {
        this.id = id
        this.nome = nome
        this.preco = preco
        this.quantidade = quantidade
        this.descricao = descricao
    }
}