package com.github.spigotbasics.modules.basicshomes.commands

import com.github.spigotbasics.common.Either
import com.github.spigotbasics.core.Spiper
import com.github.spigotbasics.core.command.BasicsCommandContext
import com.github.spigotbasics.core.command.BasicsCommandExecutor
import com.github.spigotbasics.core.command.CommandResult
import com.github.spigotbasics.core.exceptions.WorldNotLoadedException
import com.github.spigotbasics.core.extensions.partialMatches
import com.github.spigotbasics.core.model.toLocation
import com.github.spigotbasics.modules.basicshomes.BasicsHomesModule
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

class HomeCommand(private val module: BasicsHomesModule) : BasicsCommandExecutor(module) {
    private val messages = module.messages

    override fun execute(context: BasicsCommandContext): CommandResult {
        val result = module.parseHomeCmd(context)
        if (result is Either.Right) {
            return if (result.value) CommandResult.SUCCESS else CommandResult.USAGE
        }

        val home = (result as Either.Left).value
        val player = requirePlayerOrMustSpecifyPlayerFromConsole(context.sender)

        try {
            Spiper.teleportAsync(player, home.location.toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN)
            messages.homeTeleported(home).sendToSender(player)
        } catch (e: WorldNotLoadedException) {
            messages.homeWorldNotLoaded(home.location.world).sendToSender(player)
        }
        return CommandResult.SUCCESS
    }

    override fun tabComplete(context: BasicsCommandContext): MutableList<String> {
        if (context.sender !is Player) {
            return mutableListOf()
        }
        val player = context.sender as Player
        val homeList = module.getHomeList(player.uniqueId)
        return homeList.listHomeNames().partialMatches(context.args[0])
    }
}
