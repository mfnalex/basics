package com.github.spigotbasics.plugin

import com.github.spigotbasics.core.BasicsPlugin
import com.github.spigotbasics.core.extensions.placeholders
import com.github.spigotbasics.core.module.BasicsModule
import com.github.spigotbasics.plugin.module.ModuleManagerImpl
import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.lang.invoke.MethodHandles
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import kotlin.io.path.extension
import kotlin.reflect.KClass

class BasicsPluginImpl : JavaPlugin(), BasicsPlugin {
    override val availableModules: MutableList<KClass<out BasicsModule>> = ArrayList()
    override val enabledModules: MutableList<BasicsModule> = ArrayList()
    override val moduleFolder = File(dataFolder, "modules")
    override val moduleManager = ModuleManagerImpl(this)

    override fun onLoad() {
        if (!moduleFolder.isDirectory) {
            logger.info("Creating modules folder at ${moduleFolder.absolutePath}")
            moduleFolder.mkdirs()
        }
    }

    override fun onEnable() {
        logger.info(
            "Basics v%version% enabled! This plugin was written by %authors%."
                .placeholders(
                    "version" to description.version,
                    "authors" to "cool people"
                )
        )

        logger.info("Loading modules from modules folder...")
        moduleManager.loadModulesFromFolder(moduleFolder)
        moduleManager.enableAllModules()
    }

    override fun onDisable() {
        logger.info("Disabling modules...")
        moduleManager.disableAllModules()
    }
}