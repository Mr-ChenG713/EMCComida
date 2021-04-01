package com.chengbo.emccomida.Model

class Prato {

    var id:String?=null
    var Nome:String?=null
    var Quantidade:String?=null
    var Preco:String?=null
    var Descricao:String?=null
    var ImageURL:String?=null
    var Restauranteid:String?=null

    constructor()
    constructor(
        id: String?,
        Nome: String?,
        Quantidade: String?,
        Preco: String?,
        Descricao: String?,
        ImageURL: String?,
        Restauranteid: String?
    ) {
        this.id = id
        this.Nome = Nome
        this.Quantidade = Quantidade
        this.Preco = Preco
        this.Descricao = Descricao
        this.ImageURL = ImageURL
        this.Restauranteid = Restauranteid
    }
}