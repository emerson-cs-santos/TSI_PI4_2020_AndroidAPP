package br.emerson.pi4.Model

data class CriarCadastro(
    var name: String
    ,var email: String
    ,var password: String
    ,var password_confirmar: String
)