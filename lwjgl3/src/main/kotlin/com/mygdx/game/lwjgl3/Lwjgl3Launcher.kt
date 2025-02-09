package com.mygdx.game.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.game.CubeExample

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration().apply {
            setTitle("Cube Example")
            setWindowedMode(800, 600)
            useVsync(true)
            setResizable(false)
        }
        Lwjgl3Application(CubeExample(), config)
    }
}

