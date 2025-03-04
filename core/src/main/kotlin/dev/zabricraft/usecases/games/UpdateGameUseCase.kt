package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.GameState
import dev.zabricraft.models.games.IGame
import dev.zabricraft.usecases.messages.IGetMessageUseCase
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Sign

class UpdateGameUseCase(
    private val getMessageUseCase: IGetMessageUseCase,
) : IUpdateGameUseCase {

    private val countdown = 60

    override fun invoke(input: IGame) {
        val players = input.players
        if (input.state == GameState.IN_GAME) {
            input.mainHandler()
        } else {
            if (players.size >= input.minPlayers && input.state == GameState.WAITING) {
                input.state = GameState.START_COUNT
                input.currentCountValue = countdown + 1
            }
            if (input.state == GameState.START_COUNT && players.size < input.minPlayers) {
                input.state = GameState.WAITING
                input.currentCountValue = 0
            }
            if (input.state == GameState.START_COUNT) {
                input.currentCountValue -= 1
                if (input.currentCountValue == 0) {
                    input.start()
                } else if (
                    input.currentCountValue == 60 ||
                    input.currentCountValue == 30 ||
                    input.currentCountValue == 20 ||
                    input.currentCountValue == 10 ||
                    input.currentCountValue <= 5
                ) {
                    players.forEach {
                        Bukkit.getPlayer(it)?.sendMessage(
                            "§e" + getMessageUseCase("chat-start-count").replace(
                                "%d",
                                input.currentCountValue.toString()
                            )
                        )
                    }
                }
            }
        }
        val i = input.signs.iterator()
        while (i.hasNext()) {
            val b = i.next().block
            if (b.type == Material.OAK_WALL_SIGN) {
                val s = b.state as Sign
                s.setLine(0, "§4[${input.name}]")
                s.setLine(1, getMessageUseCase("sign-line-game").replace("%d", input.id.toString()))
                s.setLine(2, "${players.size}/${input.maxPlayers}")
                s.setLine(3, getMessageUseCase(input.state.key).replace("%d", input.currentCountValue.toString()))
                s.update()
            } else {
                i.remove()
            }
        }
    }

}
