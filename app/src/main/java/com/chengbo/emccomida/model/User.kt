package com.chengbo.emccomida.model

class User {

    lateinit var uid: String
    lateinit var nome: String
    lateinit var mail: String
    lateinit var contatotele: String
    lateinit var morada: String
    lateinit var codigopostal: String
    lateinit var cidade: String

    constructor(
        uid: String,
        nome: String,
        mail: String,
        contatotele: String,
        morada: String,
        codigopostal: String,
        cidade: String
    ) {
        this.uid = uid
        this.nome = nome
        this.mail = mail
        this.contatotele = contatotele
        this.morada = morada
        this.codigopostal = codigopostal
        this.cidade = cidade
    }

    constructor()
}