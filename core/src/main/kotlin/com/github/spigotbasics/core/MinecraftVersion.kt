package com.github.spigotbasics.core

import com.github.spigotbasics.core.logger.BasicsLoggerFactory
import java.util.logging.Level

/**
 * Represents a Minecraft version
 *
 * @property major Major version - 1 for 1.20.4
 * @property minor Minor version - 20 for 1.20.4
 * @property patch Patch version - 4 for 1.20.4, or 0 if not specified
 */
data class MinecraftVersion(
    val major: Int,
    val minor: Int,
    val patch: Int = 0
) {
    companion object {

        val `1_20_4` = MinecraftVersion(1, 20, 4)
        val v1_20_4 = this.`1_20_4`

        private val logger = BasicsLoggerFactory.getCoreLogger(MinecraftVersion::class)

        fun fromBukkitVersion(version: String): MinecraftVersion {
            try {
                val split = version.split("-")[0].split(".")
                val major = split[0].toInt()
                val minor = split[1].toInt()
                val patch = if (split.size > 2) split[2].toInt() else 0
                return MinecraftVersion(major, minor, patch)
            } catch (e: Exception) {
                logger.log(Level.SEVERE, "Failed to parse Bukkit version $version", e)
                return UNKNOWN
            }
        }

        val UNKNOWN = MinecraftVersion(0 ,0 ,0)
        val CURRENT = fromBukkitVersion(org.bukkit.Bukkit.getBukkitVersion())

        fun current(): MinecraftVersion {
            return CURRENT
        }
    }

    operator fun compareTo(other: MinecraftVersion): Int {
        if (major > other.major) {
            return 1
        } else if (major < other.major) {
            return -1
        } else {
            if (minor > other.minor) {
                return 1
            } else if (minor < other.minor) {
                return -1
            } else {
                if (patch > other.patch) {
                    return 1
                } else if (patch < other.patch) {
                    return -1
                } else {
                    return 0
                }
            }
        }
    }

    override fun toString(): String {
        return "$major.$minor.$patch"
    }

}