package br.emerson.pi4.Model

data class AtualizarSenha(
    var senhaAtual: String
    ,var senhaNova: String
    ,var senhaConfirmar: String
)