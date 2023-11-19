package me.nathanfallet.zabricraft.models.games

enum class GameState(
    val key: String
) {

    WAITING("state-waiting"), START_COUNT("state-start-count"), IN_GAME("state-in-game"), FINISHED("state-finished")

}
