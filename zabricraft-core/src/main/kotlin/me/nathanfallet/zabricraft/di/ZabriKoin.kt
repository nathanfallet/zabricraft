package me.nathanfallet.zabricraft.di

import me.nathanfallet.usecases.models.get.GetModelFromRepositoryUseCase
import me.nathanfallet.usecases.models.get.IGetModelUseCase
import me.nathanfallet.zabricraft.Core
import me.nathanfallet.zabricraft.commands.auth.LoginCommand
import me.nathanfallet.zabricraft.commands.auth.RegisterCommand
import me.nathanfallet.zabricraft.commands.leaderboards.LeaderboardCommand
import me.nathanfallet.zabricraft.commands.players.MoneyCommand
import me.nathanfallet.zabricraft.commands.spawn.SetSpawnCommand
import me.nathanfallet.zabricraft.commands.spawn.SpawnCommand
import me.nathanfallet.zabricraft.database.Database
import me.nathanfallet.zabricraft.database.players.DatabaseZabriPlayersRepository
import me.nathanfallet.zabricraft.events.auth.PlayerAuthentication
import me.nathanfallet.zabricraft.events.core.ServerPing
import me.nathanfallet.zabricraft.events.games.SignChange
import me.nathanfallet.zabricraft.events.players.*
import me.nathanfallet.zabricraft.events.rules.WorldProtection
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import me.nathanfallet.zabricraft.usecases.auth.*
import me.nathanfallet.zabricraft.usecases.core.GetSetMessageUseCase
import me.nathanfallet.zabricraft.usecases.core.IGetSetMessageUseCase
import me.nathanfallet.zabricraft.usecases.games.*
import me.nathanfallet.zabricraft.usecases.leaderboards.*
import me.nathanfallet.zabricraft.usecases.players.*
import me.nathanfallet.zabricraft.usecases.rules.GetWorldProtectionRulesUseCase
import me.nathanfallet.zabricraft.usecases.rules.IGetWorldProtectionRulesUseCase
import me.nathanfallet.zabricraft.usecases.rules.IWorldProtectionRuleUseCase
import me.nathanfallet.zabricraft.usecases.rules.SpawnProtectionWorldProtectionRuleUseCase
import me.nathanfallet.zabricraft.usecases.scoreboards.GetGenerateScoreboardsUseCase
import me.nathanfallet.zabricraft.usecases.scoreboards.IGetGenerateScoreboardsUseCase
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

            single<IGetGenerateLeaderboardsUseCase> { GetGenerateLeaderboardsUseCase() }
            single<IGetLeaderboardsUseCase> { GetLeaderboardsUseCase(get(named<Core>())) }
            single<ISaveLeaderboardsUseCase> { SaveLeaderboardsUseCase(get(named<Core>()), get()) }
            single<IUpdateLeaderboardUseCase> { UpdateLeaderboardUseCase(get()) }
            single<IGenerateLeaderboardUseCase>(named("money")) { GenerateMoneyLeaderboardUseCase(get()) }
            single<IGenerateLeaderboardUseCase>(named("score")) { GenerateScoreLeaderboardUseCase(get()) }
            single<IGenerateLeaderboardUseCase>(named("victories")) { GenerateVictoriesLeaderboardUseCase(get()) }

            single<IGetBukkitPlayerByNameUseCase> { GetBukkitPlayerByNameUseCase() }
            single<IGetBukkitPlayerUseCase> { GetBukkitPlayerUseCase() }
            single<IGetZabriPlayersUseCase> { GetZabriPlayersUseCase(get()) }
            single<IGetModelUseCase<ZabriPlayer, UUID>>(named<ZabriPlayer>()) {
                GetModelFromRepositoryUseCase(get<IZabriPlayersRepository>())
            }
            single<ICreateUpdateZabriPlayerUseCase> { CreateUpdateZabriPlayerUseCase(get()) }
            single<IGetCachedZabriPlayerUseCase> { GetCachedZabriPlayerUseCase(get()) }
            single<IUpdateOnlinePlayersUseCase> { UpdateOnlinePlayersUseCase(get(named<Core>()), get(), get()) }
            single<IClearZabriPlayersCacheUseCase> { ClearZabriPlayersCacheUseCase(get()) }
            single<IClearZabriPlayerCacheUseCase> { ClearZabriPlayerCacheUseCase(get()) }

            single<IGetWorldProtectionRulesUseCase> { GetWorldProtectionRulesUseCase() }
            single<IWorldProtectionRuleUseCase>(named("spawn_protection")) { SpawnProtectionWorldProtectionRuleUseCase() }

            single<IGetGenerateScoreboardsUseCase> { GetGenerateScoreboardsUseCase() }

            single<IGetSetSpawnUseCase> { GetSetSpawnUseCase(get(named<Core>())) }
        }
        val commandModule = module {
            single { LoginCommand(get(named<ZabriPlayer>()), get()) }
            single { RegisterCommand(get(named<ZabriPlayer>()), get()) }
            single { LeaderboardCommand(get(), get()) }
            single { MoneyCommand(get(named<ZabriPlayer>())) }
            single { SpawnCommand(get(), get()) }
            single { SetSpawnCommand(get()) }
        }
        val eventModule = module {
            single { PlayerAuthentication(get()) }
            single { PlayerChat() }
            single { PlayerInteract(get(), get(), get(named<ZabriPlayer>())) }
            single { PlayerJoin(get(), get()) }
            single { PlayerQuit(get()) }
            single { PlayerRespawn(get()) }
            single { ServerPing(get(named<Core>()), get(), get()) }
            single { SignChange(get()) }
            single { WorldProtection(get()) }
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
