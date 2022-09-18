import javax.swing.JFrame

class GameFrame : JFrame() {

    init {
        add(GamePanel())
        title = ("Snake")
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false
        pack()
        isVisible = true
        setLocationRelativeTo(null)
    }
}