package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.GameState
import dev.zabricraft.models.games.IGame
import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.usecases.messages.IGetMessageUseCase
import org.bukkit.entity.Player

class JoinGameUseCase(
    private val listGameUseCase: IListGameUseCase,
    private val getMessageUseCase: IGetMessageUseCase,
) : IJoinGameUseCase {

    override fun invoke(input1: IGame, input2: Player, input3: ZabriPlayer) {
        if (listGameUseCase().any { game -> game.allPlayers.any { it == input2.uniqueId } }) {
            input2.sendMessage("§c" + getMessageUseCase("chat-already-game"))
            return
        }
        if (input1.state != GameState.WAITING && input1.state != GameState.START_COUNT) {
            input2.sendMessage("§c" + getMessageUseCase("chat-game-full"))
            return
        }
        if (input1.maxPlayers == 0 && input1.players.size >= input1.maxPlayers) {
            input2.sendMessage("§c" + getMessageUseCase("chat-game-full"))
            return
        }
        input1.join(input2, input3)
        input2.sendMessage("§a" + getMessageUseCase("chat-game-join").replace("%d", input1.id.toString()))
    }

}
