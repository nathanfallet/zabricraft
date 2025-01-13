package dev.zabricraft.replica.models

data class Picture(
    val name: String,
    var blocks: MutableMap<Pair<Int, Int>, Int>,
) {

    constructor(name: String) : this(name, mutableMapOf())

    val colors: List<Int>
        get() = blocks.values.distinct()

}
