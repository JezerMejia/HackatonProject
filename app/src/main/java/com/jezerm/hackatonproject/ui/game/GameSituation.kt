package com.jezerm.hackatonproject.ui.game

enum class CorrectSide {
    LEFT,
    RIGHT
}

class GameSituation(
    var situation: String,
    var leftMessage: String,
    var rightMessage: String,
    var correctAnswer: CorrectSide,
) {
}