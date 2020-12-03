package br.emerson.pi4.Model

data class PedidoItens(
    var product_id: Int
    ,var produto: String
    ,var quantidade: Int
    ,var valor: String
    ,var sutotal: String
)