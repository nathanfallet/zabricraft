package me.nathanfallet.zabricraft.usecases.auth

import dev.kaccelero.commons.auth.IHashPasswordUseCase
import dev.kaccelero.commons.auth.IVerifyPasswordUseCase
import dev.kaccelero.commons.auth.VerifyPasswordPayload
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.models.players.PlayerScoreboard
import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticatePlayerUseCaseTest {

    @Test
    fun testLogin() {
        val repository = mockk<IZabriPlayersRepository>()
        val verifyPasswordUseCase = mockk<IVerifyPasswordUseCase>()
        val useCase = AuthenticatePlayerUseCase(repository, mockk(), verifyPasswordUseCase)
        val uuid = UUID.randomUUID()
        val loginAt = Clock.System.now()
        val scoreboard = PlayerScoreboard("Scoreboard")
        every { verifyPasswordUseCase(VerifyPasswordPayload("password", "hash")) } returns true
        every { repository.updateCached(uuid, CachedPlayer(true, loginAt, scoreboard)) } returns Unit
        assertEquals(
            true, useCase(
                ZabriPlayer(
                    uuid,
                    "name",
                    "hash",
                    0,
                    0,
                    0,
                    admin = false,
                    authenticated = false,
                    loginAt,
                    scoreboard
                ), "password"
            )
        )
        verify { repository.updateCached(uuid, CachedPlayer(true, loginAt, scoreboard)) }
    }

    @Test
    fun testLoginRegister() {
        val repository = mockk<IZabriPlayersRepository>()
        val hashPasswordUseCase = mockk<IHashPasswordUseCase>()
        val useCase = AuthenticatePlayerUseCase(repository, hashPasswordUseCase, mockk())
        val uuid = UUID.randomUUID()
        val loginAt = Clock.System.now()
        val scoreboard = PlayerScoreboard("Scoreboard")
        every { hashPasswordUseCase("password") } returns "hash"
        every { repository.update(uuid, UpdateZabriPlayerPayload(password = "hash")) } returns true
        every { repository.updateCached(uuid, CachedPlayer(true, loginAt, scoreboard)) } returns Unit
        assertEquals(
            true, useCase(
                ZabriPlayer(
                    uuid,
                    "name",
                    "",
                    0,
                    0,
                    0,
                    admin = false,
                    authenticated = false,
                    loginAt,
                    scoreboard
                ), "password"
            )
        )
        verify { repository.update(uuid, UpdateZabriPlayerPayload(password = "hash")) }
        verify { repository.updateCached(uuid, CachedPlayer(true, loginAt, scoreboard)) }
    }

}
