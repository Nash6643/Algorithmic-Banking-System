package bankingGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ModernButton extends JButton {

    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;
    private boolean isHovered = false;

    public ModernButton(String text, Color baseColor) {
        this(text, baseColor, Color.WHITE);
    }

    public ModernButton(String text, Color baseColor, Color textColor) {
        super(text);
        this.normalColor = baseColor;
        this.hoverColor = baseColor.brighter();
        this.pressedColor = baseColor.darker();

        setBackground(baseColor);
        setForeground(textColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                setBackground(normalColor);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(isHovered ? hoverColor : normalColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Subtle drop shadow on hover
        if (isHovered) {
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
        }

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - (isHovered ? 2 : 0), getHeight() - (isHovered ? 2 : 0), 15, 15);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 30));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }
}