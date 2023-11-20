package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.zabricraft.models.games.GameState
import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.usecases.core.IGetSetMessageUseCase
import org.bukkit.entity.Player

class JoinGameUseCase(
    private val getSetMessageUseCase: IGetSetMessageUseCase
) : IJoinGameUseCase {

    override fun invoke(input1: IGame, input2: Player, input3: ZabriPlayer) {
        if (input1.state != GameState.WAITING && input1.state != GameState.START_COUNT) {
            input2.sendMessage("§c" + getSetMessageUseCase("chat-game-full"))
            return
        }
        if (input1.maxPlayers == 0 && input1.players.size >= input1.maxPlayers) {
            input2.sendMessage("§c" + getSetMessageUseCase("chat-game-full"))
            return
        }
        input1.join(input2, input3)
        input2.sendMessage("§a" + getSetMessageUseCase("chat-game-join").replace("%d", input1.id.toString()))
    }

}
