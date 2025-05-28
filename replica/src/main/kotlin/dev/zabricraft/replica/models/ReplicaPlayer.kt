package dev.zabricraft.replica.models

import java.util.*

data class ReplicaPlayer(
    val uuid: UUID,
    var currentGame: Int,
    var buildmode: Boolean,
    var playing: Boolean,
    var finished: Boolean,
    var plot: Int,
    var score: Long,
    var victories: Long,
)
