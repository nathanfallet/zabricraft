package dev.zabricraft.repositories.messages

class MessagesRepository : IMessagesRepository {

    private val messages: MutableMap<String, String> = mutableMapOf()

    override fun get(key: String): String? {
        return messages[key]
    }

    override fun set(key: String, value: String) {
        messages[key] = value
    }

}
