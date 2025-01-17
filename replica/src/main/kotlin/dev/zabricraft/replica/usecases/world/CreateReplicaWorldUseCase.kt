package dev.zabricraft.replica.usecases.world

import dev.zabricraft.replica.Replica
import org.bukkit.*
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import java.util.*

class CreateReplicaWorldUseCase : ICreateReplicaWorldUseCase, ChunkGenerator() {

    override fun invoke() {
        val worldCreator = WorldCreator(Replica.WORLD_NAME)
        worldCreator.type(WorldType.FLAT)
        worldCreator.generator(this)
        worldCreator.createWorld()
        val world = Bukkit.getWorld(Replica.WORLD_NAME)
        world?.setSpawnLocation(-1000, 0, 0)
        world?.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world?.difficulty = Difficulty.PEACEFUL
        world?.time = 0
    }

    override fun getDefaultPopulators(world: World): List<BlockPopulator> = emptyList()

    override fun canSpawn(world: World, x: Int, z: Int): Boolean = true

    override fun generateChunkData(
        world: World,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        biome: BiomeGrid,
    ): ChunkData {
        val chunk = createChunkData(world)
        if (chunkX >= 0 && chunkX % Replica.DISTANCE == 0 && chunkZ % 2 == 0 && chunkZ >= 0 && chunkZ < 40) {
            for (x in 2..15) {
                for (z in 3..14) {
                    chunk.setBlock(x, 63, z, Material.OAK_PLANKS)
                    chunk.setBlock(x, 64, z, Material.OAK_PLANKS)
                    if (z == 3 || z == 14 || x == 2) {
                        chunk.setBlock(x, 65, z, Material.OAK_FENCE)
                    }
                }
            }
            for (y in 0..10) {
                for (z in 4..13) {
                    chunk.setBlock(14, 64 + y, z, Material.OAK_PLANKS)
                    chunk.setBlock(15, 64 + y, z, Material.OAK_PLANKS)
                }
            }
        }
        return chunk
    }

}
