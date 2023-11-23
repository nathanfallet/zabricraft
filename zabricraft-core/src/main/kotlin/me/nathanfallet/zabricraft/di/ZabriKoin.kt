package me.nathanfallet.zabricraft.di

import me.nathanfallet.usecases.models.create.CreateModelFromRepositoryUseCase
import me.nathanfallet.usecases.models.create.ICreateModelUseCase
import me.nathanfallet.usecases.models.delete.DeleteModelFromRepositoryUseCase
import me.nathanfallet.usecases.models.delete.IDeleteModelUseCase
import me.nathanfallet.usecases.models.get.GetModelFromRepositoryUseCase
import me.nathanfallet.usecases.models.get.IGetModelUseCase
import me.nathanfallet.usecases.models.list.IListModelUseCase
import me.nathanfallet.usecases.models.list.ListModelFromRepositoryUseCase
import me.nathanfallet.usecases.models.update.IUpdateModelUseCase
import me.nathanfallet.usecases.models.update.UpdateModelFromRepositoryUseCase
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
import me.nathanfallet.zabricraft.models.leaderboards.CreateLeaderboardPayload
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.models.leaderboards.UpdateLeaderboardPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.core.IMessagesRepository
import me.nathanfallet.zabricraft.repositories.core.MessagesRepository
import me.nathanfallet.zabricraft.repositories.games.GamesRepository
import me.nathanfallet.zabricraft.repositories.games.IGamesRepository
import me.nathanfallet.zabricraft.repositories.leaderboards.GenerateLeaderboardUseCasesRepository
import me.nathanfallet.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository
import me.nathanfallet.zabricraft.repositories.leaderboards.ILeaderboardsRepository
import me.nathanfallet.zabricraft.repositories.leaderboards.LeaderboardsRepository
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import me.nathanfallet.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository
import me.nathanfallet.zabricraft.repositories.rules.WorldProtectionRuleUseCaseRepository
import me.nathanfallet.zabricraft.repositories.scoreboards.GenerateScoreboardUseCasesRepository
import me.nathanfallet.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository
import me.nathanfallet.zabricraft.usecases.auth.*
import me.nathanfallet.zabricraft.usecases.core.GetMessageUseCase
import me.nathanfallet.zabricraft.usecases.core.IGetMessageUseCase
import me.nathanfallet.zabricraft.usecases.core.ISetMessageUseCase
import me.nathanfallet.zabricraft.usecases.core.SetMessageUseCase
import me.nathanfallet.zabricraft.usecases.games.*
import me.nathanfallet.zabricraft.usecases.leaderboards.*
import me.nathanfallet.zabricraft.usecases.players.*
import me.nathanfallet.zabricraft.usecases.rules.*
import me.nathanfallet.zabricraft.usecases.scoreboards.*
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
