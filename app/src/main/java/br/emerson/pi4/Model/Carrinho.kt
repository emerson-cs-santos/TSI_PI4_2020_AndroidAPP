package br.emerson.pi4.Model

data class Carrinho(
    var imagem: String
    ,var titulo: String
    ,var produto_id: Int
    ,var plataforma: String
    ,var preco: String
    ,var subtotal: String
    ,var quantidade: String
)