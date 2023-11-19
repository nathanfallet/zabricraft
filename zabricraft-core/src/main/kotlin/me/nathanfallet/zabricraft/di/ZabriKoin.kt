package me.nathanfallet.zabricraft.di

import me.nathanfallet.usecases.models.get.GetModelFromRepositoryUseCase
import me.nathanfallet.usecases.models.get.IGetModelUseCase
import me.nathanfallet.zabricraft.Core
import me.nathanfallet.zabricraft.commands.auth.LoginCommand
import me.nathanfallet.zabricraft.commands.auth.RegisterCommand
import me.nathanfallet.zabricraft.commands.spawn.SetSpawnCommand
import me.nathanfallet.zabricraft.commands.spawn.SpawnCommand
import me.nathanfallet.zabricraft.database.Database
import me.nathanfallet.zabricraft.database.players.DatabaseZabriPlayersRepository
import me.nathanfallet.zabricraft.events.auth.PlayerAuthentication
import me.nathanfallet.zabricraft.events.players.PlayerJoin
import me.nathanfallet.zabricraft.events.players.PlayerQuit
import me.nathanfallet.zabricraft.events.players.PlayerRespawn
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import me.nathanfallet.zabricraft.usecases.auth.*
import me.nathanfallet.zabricraft.usecases.core.GetSetMessageUseCase
import me.nathanfallet.zabricraft.usecases.core.IGetSetMessageUseCase
import me.nathanfallet.zabricraft.usecases.games.*
import me.nathanfallet.zabricraft.usecases.leaderboards.GetLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.IGetLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.ISaveLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.SaveLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.players.*
import me.nathanfallet.zabricraft.usecases.spawn.GetSetSpawnUseCase
import me.nathanfallet.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*

object ZabriKoin {

    fun start(plugin: JavaPlugin) = startKoin {
        val pluginModule = module {
            single(named<Core>()) { plugin }
        }
        val databaseModule = module {
            single {
                Database(
                    plugin.config.getString("database.protocol") ?: "h2",
                    plugin.config.getString("database.host") ?: "",
                    plugin.config.getString("database.name") ?: "",
                    plugin.config.getString("database.user") ?: "",
                    plugin.config.getString("database.password") ?: "",
                    plugin.config.getInt("database.port")
                )
            }
        }
        val repositoryModule = module {
            single<IZabriPlayersRepository> { DatabaseZabriPlayersRepository(get()) }
        }
        val useCaseModule = module {
            single<IHashPasswordUseCase> { HashPasswordUseCase() }
            single<IVerifyPasswordUseCase> { VerifyPasswordUseCase() }
            single<IAuthenticatePlayerUseCase> { AuthenticatePlayerUseCase(get(), get(), get()) }

            single<IGetSetMessageUseCase> { GetSetMessageUseCase() }

            single<IGetAddGamesUseCase> { GetAddGamesUseCase() }
            single<IGetSignsUseCase> { GetSignsUseCase(get(named<Core>())) }
            single<ISaveSignsUseCase> { SaveSignsUseCase(get(named<Core>())) }
            single<IJoinGameUseCase> { JoinGameUseCase(get()) }
            single<IUpdateGameUseCase> { UpdateGameUseCase(get(), get()) }

            single<IGetLeaderboardsUseCase> { GetLeaderboardsUseCase(get(named<Core>())) }
            single<ISaveLeaderboardsUseCase> { SaveLeaderboardsUseCase(get(named<Core>()), get()) }

            single<IGetBukkitPlayerByNameUseCase> { GetBukkitPlayerByNameUseCase() }
            single<IGetBukkitPlayerUseCase> { GetBukkitPlayerUseCase() }
            single<IGetZabriPlayersUseCase> { GetZabriPlayersUseCase(get()) }
            single<IGetModelUseCase<ZabriPlayer, UUID>>(named<ZabriPlayer>()) {
                GetModelFromRepositoryUseCase(get<IZabriPlayersRepository>())
            }
            single<ICreateUpdateZabriPlayerUseCase> { CreateUpdateZabriPlayerUseCase(get()) }
            single<IGetCachedZabriPlayerUseCase> { GetCachedZabriPlayerUseCase(get()) }
            single<IUpdateOnlinePlayersUseCase> { UpdateOnlinePlayersUseCase(get(named<Core>()), get()) }
            single<IClearZabriPlayersCacheUseCase> { ClearZabriPlayersCacheUseCase(get()) }
            single<IClearZabriPlayerCacheUseCase> { ClearZabriPlayerCacheUseCase(get()) }

            single<IGetSetSpawnUseCase> { GetSetSpawnUseCase(get(named<Core>())) }
        }
        val commandModule = module {
            single { LoginCommand(get(named<ZabriPlayer>()), get()) }
            single { RegisterCommand(get(named<ZabriPlayer>()), get()) }
            single { SpawnCommand(get(), get()) }
            single { SetSpawnCommand(get()) }
        }
        val eventModule = module {
            single { PlayerAuthentication(get()) }
            single { PlayerJoin(get(), get()) }
            single { PlayerQuit(get()) }
            single { PlayerRespawn(get()) }
        }

        modules(
            pluginModule,
            databaseModule,
            repositoryModule,
            useCaseModule,
            commandModule,
            eventModule
        )
    }

    fun stop() = stopKoin()

}
