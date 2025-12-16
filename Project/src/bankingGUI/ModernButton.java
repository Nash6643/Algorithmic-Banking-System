package bankingGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernButton extends JButton {

    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;

    public ModernButton(String text, Color baseColor) {
        super(text);
        this.normalColor = baseColor;
        this.hoverColor = baseColor.brighter();
        this.pressedColor = baseColor.darker();

        setBackground(baseColor);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();


        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2.setColor(getBackground());

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);


        super.paintComponent(g2);

        g2.dispose();
    }


    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
        g2.dispose();
    }
}