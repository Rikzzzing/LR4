package com.example.lr4.data

data class Player(
    var name: String = "",          // имя игрока
    var team: String = "",          // его команда (Красный/Зеленый)
    var isChecked: Boolean = false  // выбран ли игрок (нажат ли чекбокс рядом)
)
