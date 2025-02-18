package com.github.spigotbasics.modules.basicsworld

import com.github.spigotbasics.core.module.AbstractBasicsModule
import com.github.spigotbasics.core.module.loader.ModuleInstantiationContext
import org.bukkit.permissions.Permission

class BasicsWorldModule(context: ModuleInstantiationContext) : AbstractBasicsModule(context) {
    private val permission = permissionManager.createSimplePermission("basics.world", "Allows to switch worlds using /world")
    val worldPermissions = mutableMapOf<String, Permission>()

    fun msgAlreadyInWorld(world: String) = messages.getMessage("already-in-world").tagUnparsed("world", world)

    fun msgSuccess(world: String) = messages.getMessage("world-teleported").tagUnparsed("world", world)

    fun msgUnsuccessful(world: String) = messages.getMessage("teleport-unsuccessful").tagUnparsed("world", world)

    fun msgStartingTeleport(world: String) =
        messages.getMessage(
            "starting-teleport",
        ).tagUnparsed("world", world)

    override fun onEnable() {
        createCommand("world", permission)
            .usage("<world>")
            .description("Teleport to another world")
            .executor(WorldCommand(this))
            .register()

        server.worlds.forEach {
            getWorldPermission(it.name)
        }
    }

    override fun reloadConfig() {
        config.reload()
        messages.reload()
    }

    fun getWorldPermission(name: String): Permission {
        return worldPermissions.computeIfAbsent(name.lowercase()) {
            permissionManager.createSimplePermission(
                "basics.world.${name.lowercase()}",
                "Allows to switch to world '$name' using /world $name",
            )
        }
    }
}
