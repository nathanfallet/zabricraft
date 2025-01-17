package dev.zabricraft.replica.di

import dev.zabricraft.replica.Replica
import dev.zabricraft.replica.commands.ReplicaCommand
import dev.zabricraft.replica.events.*
import dev.zabricraft.replica.usecases.games.HandleBlockUpdateUseCase
import dev.zabricraft.replica.usecases.games.IHandleBlockUpdateUseCase
import dev.zabricraft.replica.usecases.players.IResetReplicaPlayerUseCase
import dev.zabricraft.replica.usecases.players.ResetReplicaPlayerUseCase
import dev.zabricraft.replica.usecases.world.CreateReplicaWorldUseCase
import dev.zabricraft.replica.usecases.world.ICreateReplicaWorldUseCase
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ReplicaKoin {

    fun start(plugin: JavaPlugin) {
        val pluginModule = module {
            single(named<Replica>()) { plugin }
        }
        val useCaseModule = module {
            single<IHandleBlockUpdateUseCase> { HandleBlockUpdateUseCase() }
            single<IResetReplicaPlayerUseCase> { ResetReplicaPlayerUseCase(get()) }
            single<ICreateReplicaWorldUseCase> { CreateReplicaWorldUseCase() }
        }
        val commandModule = module {
            single { ReplicaCommand(get(), get()) }
        }
        val eventModule = module {
            single { BlockUpdate(get()) }
            single { EntityDamage() }
            single { PlayerCommandPreprocess(get()) }
            single { PlayerJoin() }
            single { PlayerQuit() }
            single { PlayerRespawn() }
        }

        loadKoinModules(
            listOf(
                pluginModule,
                useCaseModule,
                commandModule,
                eventModule
            )
        )
    }

}
