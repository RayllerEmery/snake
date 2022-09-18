import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.random.Random

class GamePanel(
    private val x: Array<Int> = Array(GAME_UNITS) {i -> i},
    private val Y: Array<Int> = Array(GAME_UNITS) {i -> i},
    private var bodyParts: Int = 6,
    private var applesEaten: Int = 0,
    private var appleX: Int? = null,
    private var appleY: Int? = null,
    private var direction: Char = 'R',
    private var running: Boolean = false,
    private var timer: Timer? = null,
    private var random: Random? = null
) : JPanel(), ActionListener {

    init {
        preferredSize = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)
        background = Color.BLACK
        isFocusable = true
        addKeyListener(MyKeyAdapter)
        startGame()
    }

    private fun startGame() {
        newApple()
        running = true
        timer = Timer(DELAY, this)
        timer?.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(g)
    }

    fun draw(g: Graphics) {
        for (i in 0 until SCREEN_HEIGHT / UNIT_SIZE) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT)
        }
    }

    fun newApple() {

    }

    fun move() {

    }

    fun checkApple() {

    }

    fun checkCollisions() {

    }

    fun gameOver(g: Graphics) {

    }

    override fun actionPerformed(e: ActionEvent?) {

    }

    object MyKeyAdapter : KeyAdapter() {
        override fun keyPressed(e: KeyEvent?) {
            super.keyPressed(e)
        }
    }

    companion object {
        const val SCREEN_WIDTH = 600
        const val SCREEN_HEIGHT = 600
        const val UNIT_SIZE = 25
        const val GAME_UNITS = (SCREEN_WIDTH * SCREEN_WIDTH)/ UNIT_SIZE
        const val DELAY = 75
    }
}
