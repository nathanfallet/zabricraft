package me.nathanfallet.zabricraft.repositories.core

interface IMessagesRepository {

    fun get(key: String): String?
    fun set(key: String, value: String)

}
