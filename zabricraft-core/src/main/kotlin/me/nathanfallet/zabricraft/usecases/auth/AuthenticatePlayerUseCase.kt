package me.nathanfallet.zabricraft.usecases.auth

import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository

class AuthenticatePlayerUseCase(
    private val repository: IZabriPlayersRepository,
    private val hashPasswordUseCase: IHashPasswordUseCase,
    private val verifyPasswordUseCase: IVerifyPasswordUseCase
) : IAuthenticatePlayerUseCase {

    override fun invoke(input1: ZabriPlayer, input2: String): Boolean {
        val authenticated = if (input1.password.isEmpty()) {
            repository.update(input1.id, UpdateZabriPlayerPayload(password = hashPasswordUseCase(input2)))
        } else verifyPasswordUseCase(input2, input1.password)

        return if (authenticated) {
            repository.updateCached(
                input1.id, CachedPlayer(
                    true,
                    input1.loginAt,
                    input1.scoreboard
                )
            )
            true
        } else false
    }

}
