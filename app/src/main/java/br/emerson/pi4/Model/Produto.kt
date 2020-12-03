package br.emerson.pi4.Model

data class Produto(
    var id: Int
    ,var name: String
    ,var image: String
    ,var desc: String
    ,var price: String
    ,var discount: String
    ,var stock: Int
    ,var sold: Int
    ,var home: String
    ,var created_at: String
    ,var updated_at: String?
    ,var deleted_at: String?
    ,var category_id: Int
    ,var categoryName: String
)