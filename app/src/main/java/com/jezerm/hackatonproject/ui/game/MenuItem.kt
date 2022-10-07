package com.jezerm.hackatonproject.ui.game

class MenuItem(val name: String, var situations: ArrayList<GameSituation> = ArrayList()) {
    companion object {
        val defaultMenus = ArrayList<MenuItem>()

        init {
            defaultMenus.add(
                MenuItem(
                    "Historia 1",
                    arrayListOf(
                        GameSituation(
                            "Estás en el colegio y un hombre te queda viendo fijamente. ¿Qué haces?",
                            "Preguntarle qué está haciendo",
                            "Ir a hablarle a un profesor/a",
                            CorrectSide.RIGHT
                        ),
                        GameSituation(
                            "Ves que una persona adulta besa o toca a una niña de forma morbosa. ¿Los ignoras?",
                            "No",
                            "Sí",
                            CorrectSide.LEFT
                        ),
                        GameSituation(
                            "Acabas de salir del colegio. Alguien desconocido te ofrece dinero a cambio de acompañarlo a su hogar.",
                            "Aceptas",
                            "Le dices que no debes hablar con desconocidos",
                            CorrectSide.RIGHT
                        )
                    )
                ),
            )
            defaultMenus.add(
                MenuItem(
                    "Historia 2",
                    arrayListOf(

                    )
                )
            )
        }
    }
}