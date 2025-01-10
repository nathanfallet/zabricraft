package dev.zabricraft.di

import dev.kaccelero.commons.auth.HashPasswordUseCase
import dev.kaccelero.commons.auth.IHashPasswordUseCase
import dev.kaccelero.commons.auth.IVerifyPasswordUseCase
import dev.kaccelero.commons.auth.VerifyPasswordUseCase
import dev.kaccelero.commons.repositories.*
import dev.zabricraft.Core
import dev.zabricraft.commands.auth.LoginCommand
import dev.zabricraft.commands.auth.RegisterCommand
import dev.zabricraft.commands.leaderboards.LeaderboardCommand
import dev.zabricraft.commands.players.MoneyCommand
import dev.zabricraft.commands.spawn.SetSpawnCommand
import dev.zabricraft.commands.spawn.SpawnCommand
import dev.zabricraft.database.Database
import dev.zabricraft.database.players.DatabaseZabriPlayersRepository
import dev.zabricraft.events.auth.PlayerAuthentication
import dev.zabricraft.events.core.ServerPing
import dev.zabricraft.events.games.SignChange
import dev.zabricraft.events.players.*
import dev.zabricraft.events.rules.WorldProtection
import dev.zabricraft.models.leaderboards.CreateLeaderboardPayload
import dev.zabricraft.models.leaderboards.Leaderboard
import dev.zabricraft.models.leaderboards.UpdateLeaderboardPayload
import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.games.GamesRepository
import dev.zabricraft.repositories.games.IGamesRepository
import dev.zabricraft.repositories.leaderboards.GenerateLeaderboardUseCasesRepository
import dev.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository
import dev.zabricraft.repositories.leaderboards.ILeaderboardsRepository
import dev.zabricraft.repositories.leaderboards.LeaderboardsRepository
import dev.zabricraft.repositories.messages.IMessagesRepository
import dev.zabricraft.repositories.messages.MessagesRepository
import dev.zabricraft.repositories.players.IZabriPlayersRepository
import dev.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository
import dev.zabricraft.repositories.rules.WorldProtectionRuleUseCaseRepository
import dev.zabricraft.repositories.scoreboards.GenerateScoreboardUseCasesRepository
import dev.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository
import dev.zabricraft.usecases.auth.AuthenticatePlayerUseCase
import dev.zabricraft.usecases.auth.IAuthenticatePlayerUseCase
import dev.zabricraft.usecases.games.*
import dev.zabricraft.usecases.leaderboards.*
import dev.zabricraft.usecases.messages.GetMessageUseCase
import dev.zabricraft.usecases.messages.IGetMessageUseCase
import dev.zabricraft.usecases.messages.ISetMessageUseCase
import dev.zabricraft.usecases.messages.SetMessageUseCase
import dev.zabricraft.usecases.players.*
import dev.zabricraft.usecases.rules.*
import dev.zabricraft.usecases.scoreboards.*
import dev.zabricraft.usecases.spawn.GetSetSpawnUseCase
import dev.zabricraft.usecases.spawn.IGetSetSpawnUseCase
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
            single<IMessagesRepository> { MessagesRepository() }

            single<IGamesRepository> { GamesRepository() }

            single<IGenerateLeaderboardUseCasesRepository> { GenerateLeaderboardUseCasesRepository() }
            single<ILeaderboardsRepository> { LeaderboardsRepository(get(named<Core>())) }

            single<IZabriPlayersRepository> { DatabaseZabriPlayersRepository(get()) }

            single<IWorldProtectionRuleUseCaseRepository> { WorldProtectionRuleUseCaseRepository() }

            single<IGenerateScoreboardUseCasesRepository> { GenerateScoreboardUseCasesRepository() }
        }
        val useCaseModule = module {
            single<IHashPasswordUseCase> { HashPasswordUseCase() }
            single<IVerifyPasswordUseCase> { VerifyPasswordUseCase() }
            single<IAuthenticatePlayerUseCase> { AuthenticatePlayerUseCase(get(), get(), get()) }

            single<IGetMessageUseCase> { GetMessageUseCase(get()) }
            single<ISetMessageUseCase> { SetMessageUseCase(get()) }

            single<IListGameUseCase> { ListGameUseCase(get()) }
            single<IAddGameUseCase> { AddGameUseCase(get()) }
            single<IClearGamesUseCase> { ClearGamesUseCase(get()) }
            single<IGetSignsUseCase> { GetSignsUseCase(get(named<Core>())) }
            single<ISaveSignsUseCase> { SaveSignsUseCase(get(named<Core>())) }
            single<IJoinGameUseCase> { JoinGameUseCase(get()) }
            single<IUpdateGameUseCase> { UpdateGameUseCase(get()) }

            single<IListGenerateLeaderboardUseCase> { ListGenerateLeaderboardUseCase(get()) }
            single<IClearGenerateLeaderboardUseCase> { ClearGenerateLeaderboardUseCase(get()) }
            single<IGetGenerateLeaderboardUseCase> { GetGenerateLeaderboardUseCase(get()) }
            single<ISetGenerateLeaderboardUseCase> { SetGenerateLeaderboardUseCase(get()) }
            single<IClearLeaderboardsUseCase> { ClearLeaderboardsUseCase(get()) }
            single<ISaveLeaderboardsUseCase> { SaveLeaderboardsUseCase(get()) }
            single<IUpdateLeaderboardUseCase> { UpdateLeaderboardUseCase(get()) }
            single<IListModelUseCase<Leaderboard>>(named<Leaderboard>()) {
                ListModelFromRepositoryUseCase(get<ILeaderboardsRepository>())
            }
            single<IGetModelUseCase<Leaderboard, String>>(named<Leaderboard>()) {
                GetModelFromRepositoryUseCase(get<ILeaderboardsRepository>())
            }
            single<ICreateModelUseCase<Leaderboard, CreateLeaderboardPayload>>(named<Leaderboard>()) {
                CreateModelFromRepositoryUseCase(get<ILeaderboardsRepository>())
            }
            single<IUpdateModelUseCase<Leaderboard, String, UpdateLeaderboardPayload>>(named<Leaderboard>()) {
                UpdateModelFromRepositoryUseCase(get<ILeaderboardsRepository>())
            }
            single<IDeleteModelUseCase<Leaderboard, String>>(named<Leaderboard>()) {
                DeleteModelFromRepositoryUseCase(get<ILeaderboardsRepository>())
            }
            single<IGenerateLeaderboardUseCase>(named("money")) { GenerateMoneyLeaderboardUseCase(get()) }
            single<IGenerateLeaderboardUseCase>(named("score")) { GenerateScoreLeaderboardUseCase(get()) }
            single<IGenerateLeaderboardUseCase>(named("victories")) { GenerateVictoriesLeaderboardUseCase(get()) }

            single<IGetZabriPlayersUseCase> { GetZabriPlayersUseCase(get()) }
            single<IGetModelUseCase<ZabriPlayer, UUID>>(named<ZabriPlayer>()) {
                GetModelFromRepositoryUseCase(get<IZabriPlayersRepository>())
            }
            single<ICreateUpdateZabriPlayerUseCase> { CreateUpdateZabriPlayerUseCase(get()) }
            single<IGetCachedZabriPlayerUseCase> { GetCachedZabriPlayerUseCase(get()) }
            single<IUpdateOnlinePlayersUseCase> { UpdateOnlinePlayersUseCase(get(named<Core>()), get(), get()) }
            single<IClearZabriPlayersCacheUseCase> { ClearZabriPlayersCacheUseCase(get()) }
            single<IClearZabriPlayerCacheUseCase> { ClearZabriPlayerCacheUseCase(get()) }

            single<IAddWorldProtectionRuleUseCase> { AddWorldProtectionRuleUseCase(get()) }
            single<IListWorldProtectionRuleUseCase> { ListWorldProtectionRuleUseCase(get()) }
            single<IClearWorldProtectionRuleUseCase> { ClearWorldProtectionRuleUseCase(get()) }
            single<IWorldProtectionRuleUseCase>(named("spawn_protection")) { SpawnProtectionWorldProtectionRuleUseCase() }

            single<IAddGenerateScoreboardUseCase> { AddGenerateScoreboardUseCase(get()) }
            single<IListGenerateScoreboardUseCase> { ListGenerateScoreboardUseCase(get()) }
            single<IClearGenerateScoreboardUseCase> { ClearGenerateScoreboardUseCase(get()) }

            single<IGetSetSpawnUseCase> { GetSetSpawnUseCase(get(named<Core>())) }
        }
        val commandModule = module {
            single { LoginCommand(get(named<ZabriPlayer>()), get()) }
            single { RegisterCommand(get(named<ZabriPlayer>()), get()) }
            single {
                LeaderboardCommand(
                    get(named<Leaderboard>()),
                    get(named<Leaderboard>()),
                    get(named<Leaderboard>()),
                    get(named<Leaderboard>()),
                    get(named<Leaderboard>()),
                    get()
                )
            }
            single { MoneyCommand(get(named<ZabriPlayer>())) }
            single { SpawnCommand(get()) }
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
