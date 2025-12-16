package bankingGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ComplexityGraphPanel extends JPanel {

    private final List<Integer> nSizes;
    private final List<Long> bstTimes;
    private final List<Long> mergeTimes;
    private final List<Long> quickTimes;
    private final List<Long> heapTimes;


    private Timer animationTimer;
    private int animationWidth = 0;

    public ComplexityGraphPanel(List<Integer> n, List<Long> bst, List<Long> merge, List<Long> quick, List<Long> heap) {
        this.nSizes = n;
        this.bstTimes = bst;
        this.mergeTimes = merge;
        this.quickTimes = quick;
        this.heapTimes = heap;
        this.setBackground(Color.WHITE);


        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationWidth += 25; // Speed of animation
                if (animationWidth >= 2000) {
                    animationWidth = 2000;
                    animationTimer.stop();
                }
                repaint();
            }
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int paddingLeft = 60;
        int paddingBottom = 60;
        int paddingRight = 30;
        int paddingTop = 30;

        int width = getWidth() - (paddingLeft + paddingRight);
        int height = getHeight() - (paddingTop + paddingBottom);


        long rawMaxNs = 0;
        for (long t : mergeTimes) rawMaxNs = Math.max(rawMaxNs, t);
        for (long t : quickTimes) rawMaxNs = Math.max(rawMaxNs, t);
        for (long t : heapTimes) rawMaxNs = Math.max(rawMaxNs, t);
        if (rawMaxNs == 0) rawMaxNs = 1;


        double maxMs = rawMaxNs / 1_000_000.0;
        double ceilingMs = Math.ceil(maxMs);
        if (ceilingMs < 1) ceilingMs = 1; // Prevent 0 scale
        long niceMaxNs = (long)(ceilingMs * 1_000_000.0);

        // 3. Max N for X-Axis scaling
        int maxN = nSizes.isEmpty() ? 1000 : nSizes.get(nSizes.size() - 1);


        drawAxesAndGrid(g2, paddingLeft, paddingTop, paddingBottom, width, height, niceMaxNs, maxN);
        drawLegend(g2);


        Shape oldClip = g2.getClip();
        g2.setClip(0, 0, animationWidth, getHeight());

        // Draw Lines (Now passing paddingTop correctly)
        drawDataLine(g2, bstTimes, Color.BLUE, niceMaxNs, maxN, paddingLeft, paddingTop, paddingBottom, width, height);
        drawDataLine(g2, mergeTimes, Color.RED, niceMaxNs, maxN, paddingLeft, paddingTop, paddingBottom, width, height);
        drawDataLine(g2, quickTimes, Color.GREEN, niceMaxNs, maxN, paddingLeft, paddingTop, paddingBottom, width, height);
        drawDataLine(g2, heapTimes, Color.ORANGE, niceMaxNs, maxN, paddingLeft, paddingTop, paddingBottom, width, height);

        g2.setClip(oldClip);
    }

    private void drawAxesAndGrid(Graphics2D g2, int paddingLeft, int paddingTop, int paddingBottom, int w, int h, long maxTimeNs, int maxN) {
        g2.setFont(new Font("Arial", Font.PLAIN, 10));


        int divisions = 10;
        for (int i = 0; i <= divisions; i++) {
            int y = getHeight() - paddingBottom - (i * h / divisions);
            double valMs = ((double)maxTimeNs * i / divisions) / 1_000_000.0;

            g2.setColor(new Color(230, 230, 230));
            g2.drawLine(paddingLeft, y, getWidth() - 30, y);

            g2.setColor(Color.BLACK);
            g2.drawLine(paddingLeft - 5, y, paddingLeft, y);

            String label = String.format("%.1f", valMs);
            int labelWidth = g2.getFontMetrics().stringWidth(label);
            g2.drawString(label, paddingLeft - labelWidth - 8, y + 4);
        }


        int step = 1000;
        for (int val = 0; val <= maxN; val += step) {
            double ratio = (double) val / maxN;
            int x = paddingLeft + (int) (ratio * w);

            g2.setColor(Color.BLACK);
            g2.drawLine(x, getHeight() - paddingBottom, x, getHeight() - paddingBottom + 5);

            String label = String.valueOf(val);
            int labelWidth = g2.getFontMetrics().stringWidth(label);
            g2.drawString(label, x - (labelWidth / 2), getHeight() - paddingBottom + 20);
        }

        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(paddingLeft, getHeight() - paddingBottom, paddingLeft, paddingTop);
        g2.drawLine(paddingLeft, getHeight() - paddingBottom, getWidth() - 30, getHeight() - paddingBottom);

        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.drawString("Time (ms)", 10, paddingTop - 10);
        g2.drawString("Input Size (N)", getWidth() / 2, getHeight() - 10);
    }

    private void drawDataLine(Graphics2D g2, List<Long> times, Color color, long maxTimeNs, int maxN, int pLeft, int pTop, int pBottom, int width, int height) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f));


        int prevX = pLeft;
        int prevY = getHeight() - pBottom;

        for (int i = 0; i < times.size(); i++) {
            int nVal = nSizes.get(i);
            int x = pLeft + (int) (((double) nVal / maxN) * width);

            long tVal = times.get(i);
            int y = getHeight() - pBottom - (int) (((double) tVal / maxTimeNs) * height);

            g2.drawLine(prevX, prevY, x, y);
            prevX = x; prevY = y;
        }
    }

    private void drawLegend(Graphics2D g2) {
        int x = getWidth() - 190; int y = 50;
        g2.setColor(new Color(255, 255, 255, 220)); g2.fillRect(x - 5, y - 15, 180, 90);
        g2.setColor(Color.BLACK); g2.drawRect(x - 5, y - 15, 180, 90);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.BLUE);   g2.drawString("● BST Search (Log n)", x, y);
        g2.setColor(Color.RED);    g2.drawString("● Merge Sort (n Log n)", x, y + 20);
        g2.setColor(Color.GREEN);  g2.drawString("● Quick Sort (n Log n)", x, y + 40);
        g2.setColor(Color.ORANGE); g2.drawString("● Heap Sort (n Log n)", x, y + 60);
    }
}