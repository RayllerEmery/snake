import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.random.Random

open class GamePanel(
    private val x: Array<Int> = Array(GAME_UNITS) {i -> i},
    private val y: Array<Int> = Array(GAME_UNITS) {i -> i},
    private var bodyParts: Int = 6,
    private var applesEaten: Int = 0,
    private var appleX: Int = SCREEN_WIDTH,
    private var appleY: Int = SCREEN_HEIGHT,
    private var running: Boolean = false,
) : JPanel(), ActionListener {

    private lateinit var timer: Timer

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
        timer.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(g)
    }

    private fun draw(g: Graphics) {

        if (running){
            for (i in 0 until SCREEN_HEIGHT / UNIT_SIZE) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT)
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE)
            }
            g.color = Color.RED
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE)

            for (i in 0 until bodyParts) {
                if (i == 0) {
                    g.color = Color.GREEN
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE)
                } else {
                    g.color = Color(45, 180, 0)
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE)
                }
            }
        } else {
            gameOver(g)
        }
    }

    private fun newApple() {
        appleX = Random.nextInt(SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE
        appleY = Random.nextInt(SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE
    }

    fun move() {
        for (i in bodyParts downTo 1) {
            x[i] = x[i - 1]
            y[i] = y[i - 1]
        }

        when (direction) {
            'U' -> y[0] = y[0] - UNIT_SIZE
            'D' -> y[0] = y[0] + UNIT_SIZE
            'L' -> x[0] = x[0] - UNIT_SIZE
            'R' -> x[0] = x[0] + UNIT_SIZE
        }
    }

    fun checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts ++
            applesEaten ++
            newApple()
        }
    }

    fun checkCollisions() {
        for (i in bodyParts downTo 1) {
            if ((x[0] == x[i]) && (y[0] == y[i])) running = false
        }
        if (x[0] < 0) running = false

        if (x[0] > SCREEN_WIDTH) running = false

        if (y[0] > SCREEN_HEIGHT) running = false

        if (y[0] < 0) running = false

        if(!running) timer.stop()
    }

    fun gameOver(g: Graphics) {
        g.color = Color.RED
        g.font = Font("Ink Free", Font.BOLD, 75)
        val metrics = getFontMetrics(g.font)
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2)
        g.drawString("Score $applesEaten", (SCREEN_WIDTH - metrics.stringWidth("Score $applesEaten"))/2, SCREEN_HEIGHT/2 + (metrics.font.size * 2))
    }

    override fun actionPerformed(e: ActionEvent?) {
        if(running) {
            move()
            checkApple()
            checkCollisions()
        }
        repaint()
    }

    object MyKeyAdapter : KeyAdapter() {
        override fun keyPressed(e: KeyEvent?) {
            when (e?.keyCode) {
                KeyEvent.VK_LEFT -> { if (direction != 'R') direction = 'L'}
                KeyEvent.VK_RIGHT -> { if (direction != 'L') direction = 'R'}
                KeyEvent.VK_UP -> { if (direction != 'D') direction = 'U'}
                KeyEvent.VK_DOWN -> { if (direction != 'U') direction = 'D'}
            }
        }
    }

    companion object {
        const val SCREEN_WIDTH = 600
        const val SCREEN_HEIGHT = 600
        const val UNIT_SIZE = 25
        const val GAME_UNITS = (SCREEN_WIDTH * SCREEN_WIDTH)/ UNIT_SIZE
        const val DELAY = 75

        var direction: Char = 'R'

    }
}

