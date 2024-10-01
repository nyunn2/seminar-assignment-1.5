package com.example.mytictactoe

data class GameResult(
    val board: Array<Char>,
    val winner: Char // 'X', 'O', ' ' 중 하나
)