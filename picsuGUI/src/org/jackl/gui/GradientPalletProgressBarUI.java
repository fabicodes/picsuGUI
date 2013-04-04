/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackl.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Ripped from:
 * http://stackoverflow.com/questions/12524121/jprogressbar-how-to-change-colour-based-on-progress/12525351#12525351
 * Added Stuff for Vertical ProgressBars and added some more makeGradientPallet
 * methods and the matching constructors
 *
 * @author Fabian
 */
class GradientPalletProgressBarUI extends BasicProgressBarUI {

    private final int[] pallet;

    public GradientPalletProgressBarUI() {
        super();
        this.pallet = makeGradientPallet();
    }

    public GradientPalletProgressBarUI(Color[] colors, float[] dist) {
        super();
        this.pallet = makeGradientPallet(colors, dist);
    }

    public GradientPalletProgressBarUI(Color[] colors) {
        super();
        this.pallet = makeGradientPallet(colors);
    }

    private static int[] makeGradientPallet(Color[] colors, float[] dist) {
        BufferedImage image = new BufferedImage(100, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        Point2D start = new Point2D.Float(0f, 0f);
        Point2D end = new Point2D.Float(99f, 0f);
        if (colors.length != dist.length) {
            float[] defdist = {0.0f, 0.5f, 1.0f};
            Color[] defcolors = {Color.RED, Color.YELLOW, Color.GREEN};
            g2.setPaint(new LinearGradientPaint(start, end, defdist, defcolors));
        } else {
            g2.setPaint(new LinearGradientPaint(start, end, dist, colors));
        }
        g2.fillRect(0, 0, 100, 1);
        g2.dispose();

        int width = image.getWidth(null);
        int[] pallet = new int[width];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, 1, pallet, 0, width);
        try {
            pg.grabPixels();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallet;
    }

    private static int[] makeGradientPallet(Color[] colors) {
        BufferedImage image = new BufferedImage(100, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        Point2D start = new Point2D.Float(0f, 0f);
        Point2D end = new Point2D.Float(99f, 0f);
        float[] dist = new float[colors.length];
        for (int i = 1; i < colors.length; i++) {
            dist[i] = (i) * (1.0f / (colors.length - 1));
        }
        g2.setPaint(new LinearGradientPaint(start, end, dist, colors));
        g2.fillRect(0, 0, 100, 1);
        g2.dispose();

        int width = image.getWidth(null);
        int[] pallet = new int[width];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, 1, pallet, 0, width);
        try {
            pg.grabPixels();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallet;
    }

    private static int[] makeGradientPallet() {
        BufferedImage image = new BufferedImage(100, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        Point2D start = new Point2D.Float(0f, 0f);
        Point2D end = new Point2D.Float(99f, 0f);
        float[] dist = {0.0f, 0.5f, 1.0f};
        Color[] colors = {Color.RED, Color.YELLOW, Color.GREEN};
        g2.setPaint(new LinearGradientPaint(start, end, dist, colors));
        g2.fillRect(0, 0, 100, 1);
        g2.dispose();

        int width = image.getWidth(null);
        int[] pallet = new int[width];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, 1, pallet, 0, width);
        try {
            pg.grabPixels();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallet;
    }

    private static Color getColorFromPallet(int[] pallet, float x) {
        if (x < 0.0 || x > 1.0) {
            throw new IllegalArgumentException("Parameter outside of expected range");
        }
        int i = (int) (pallet.length * x);
        int max = pallet.length - 1;
        int index = i < 0 ? 0 : i > max ? max : i;
        int pix = pallet[index] & 0x00ffffff | (0x64 << 24);
        return new Color(pix, true);
    }

    @Override
    public void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        int cellLength = getCellLength();
        int cellSpacing = getCellSpacing();
        // amount of progress to draw
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);
        System.out.println("Top: " + b.top);
        System.out.println("Bottom: " + b.bottom);
        System.out.println("barRectWidth: " + barRectWidth);
        System.out.println("progressBarWidth: " + progressBar.getWidth());
        System.out.println("barRectHeight: " + barRectHeight);
        System.out.println("progressBarHeight: " + progressBar.getHeight());
        System.out.println("amountFull: " + amountFull);
        System.out.println("barRectHeight - amountFull: " + (barRectHeight - amountFull));
        System.out.println("amountFull - barRectHeight: " + (amountFull - barRectHeight));
        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            // draw the cells
            float x = amountFull / (float) barRectWidth;
            g.setColor(getColorFromPallet(pallet, x));
            g.fillRect(b.left, b.top, amountFull, barRectHeight);

        } else { // VERTICAL
            float x = amountFull / (float) barRectHeight;
            g.setColor(getColorFromPallet(pallet, x));
            g.fillRect(b.left, barRectHeight + b.bottom, barRectWidth, amountFull * -1);
        }
        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
        }
    }
}
