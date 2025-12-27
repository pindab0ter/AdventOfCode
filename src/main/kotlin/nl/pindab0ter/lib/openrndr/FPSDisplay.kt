package nl.pindab0ter.lib.openrndr

import org.openrndr.Extension
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.FontMap
import org.openrndr.draw.isolated
import org.openrndr.draw.loadFont
import org.openrndr.math.Matrix44
import kotlin.math.roundToInt

class FPSDisplay : Extension {
    override var enabled: Boolean = true

    var frames = 0
    var startTime: Double = 0.0
    lateinit var font: FontMap

    override fun setup(program: Program) {
        startTime = program.seconds
        font = program.loadFont("/Users/hans/Library/Fonts/PragmataProR_0830.ttf", 12.0)
    }

    override fun afterDraw(drawer: Drawer, program: Program) {
        frames++

        drawer.isolated {
            drawer.fontMap = font
            drawer.view = Matrix44.Companion.IDENTITY
            drawer.ortho()

            drawer.fill = ColorRGBa.Companion.GREEN
            drawer.text("${(frames / (program.seconds - startTime)).roundToInt()}", 4.0, 4 + font.height)
        }
    }
}
