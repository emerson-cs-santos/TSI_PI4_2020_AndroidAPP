package br.emerson.pi4.Model

data class Resposta(
    var success: String
    ,var message: String
    ,var token: String?
    ,var user_id: Int?
)