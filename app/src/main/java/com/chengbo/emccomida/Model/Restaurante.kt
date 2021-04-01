package com.chengbo.emccomida.Model

class Restaurante {

    var uid:String?=null
    var nome:String?=null
    var mail:String?=null
    var contatotele:String?=null
    var morada:String?=null
    var codigopostal:String?=null
    var cidade:String?=null

    constructor()
    constructor(
        uid: String?,
        nome: String?,
        mail: String?,
        contatotele: String?,
        morada: String?,
        codigopostal: String?,
        cidade: String?
    ) {
        this.uid = uid
        this.nome = nome
        this.mail = mail
        this.contatotele = contatotele
        this.morada = morada
        this.codigopostal = codigopostal
        this.cidade = cidade
    }


}