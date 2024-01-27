package com.github.spigotbasics.core

import com.github.spigotbasics.core.command.BasicsCommandManager
import com.github.spigotbasics.core.config.CoreConfigManager
import com.github.spigotbasics.core.config.CoreMessages
import com.github.spigotbasics.core.messages.AudienceProvider
import com.github.spigotbasics.core.messages.MessageFactory
import com.github.spigotbasics.core.messages.TagResolverFactory
import com.github.spigotbasics.core.module.manager.ModuleManager
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Represents the Basics Bukkit Plugin instance.
 */
interface BasicsPlugin: Plugin {

    val audienceProvider: AudienceProvider

    /**
     * The folder where the plugin's modules are stored.
     */
    val moduleFolder: File

    /**
     * Manager responsible for handling the various modules of the plugin.
     */
    val moduleManager: ModuleManager

    /**
     * Factory for creating and getting tag resolvers for MiniMessage
     */
    val tagResolverFactory: TagResolverFactory

    /**
     * Message factory
     */
    val messageFactory: MessageFactory

    /**
     * Manager for handling configuration and message files
     */
    val coreConfigManager: CoreConfigManager

    /**
     * Messages used by the plugin itself or by more than one module.
     */
    val messages: CoreMessages

    /**
     * Creates and returns an instance of [BasicsCommandManager].
     *
     * @return The created instance of [BasicsCommandManager].
     */
    fun createCommandManager(): BasicsCommandManager

    /**
     * Reloads the core configuration settings of the plugin.
     */
    fun reloadCoreConfig()
}