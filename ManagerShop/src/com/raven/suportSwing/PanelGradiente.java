/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.suportSwing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import javax.swing.JLayeredPane;

public class PanelGradiente extends JLayeredPane {

    public static enum Gradiente {
        HORIZONTAL, VERTICAL, ESQUINA_1, ESQUINA_2, ESQUINA_3, ESQUINA_4, CIRCULAR, CENTRAL, AQUA;

        private Gradiente() {
        }
    }

    protected Gradiente gradiente = Gradiente.HORIZONTAL;
    protected Color colorPrimario = Color.WHITE;
    protected Color colorSecundario = Color.WHITE;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint gp = getGradientePaint();
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(getForeground());
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.dispose();
        super.paintComponent(g);
    }

    private Paint getGradientePaint() {
        int x1;
        int x2 = getWidth();
        int y1;
        int y2 = getHeight();
        switch (this.gradiente) {
            case HORIZONTAL:
                x1 = getWidth() / 2;
                y1 = 0;
                x2 = getWidth() / 2;
                y2 = getHeight();
                return new GradientPaint(x1, y1, this.colorPrimario, x2, y2, this.colorSecundario);
            case VERTICAL:
                x1 = 0;
                y1 = getHeight() / 2;
                x2 = getWidth();
                y2 = getHeight() / 2;
                return new GradientPaint(x1, y1, this.colorPrimario, x2, y2, this.colorSecundario);
            case ESQUINA_1:
                x1 = 0;
                y1 = 0;
                return new RadialGradientPaint(x1, y1, getWidth(), new float[]{0.0F, 1.0F}, new Color[]{this.colorPrimario, this.colorSecundario});
            case ESQUINA_2:
                x1 = getWidth();
                y1 = 0;
                return new RadialGradientPaint(x1, y1, getWidth(), new float[]{0.0F, 1.0F}, new Color[]{this.colorPrimario, this.colorSecundario});
            case ESQUINA_3:
                x1 = getWidth();
                y1 = getHeight();
                return new RadialGradientPaint(x1, y1, getWidth(), new float[]{0.0F, 1.0F}, new Color[]{this.colorPrimario, this.colorSecundario});
            case ESQUINA_4:
                x1 = 0;
                y1 = getHeight();
                return new RadialGradientPaint(x1, y1, getWidth(), new float[]{0.0F, 1.0F}, new Color[]{this.colorPrimario, this.colorSecundario});
            case CIRCULAR:
                x1 = getWidth() / 2;
                y1 = getHeight() / 2;
                return new RadialGradientPaint(x1, y1, getWidth(), new float[]{0.0F, 0.5F}, new Color[]{this.colorPrimario, this.colorSecundario});
            case CENTRAL:
                x1 = getWidth() / 2;
                y1 = 0;
                x2 = getWidth() / 2;
                y2 = getHeight();
                return new LinearGradientPaint(x1, y1, x2, y2, new float[]{0.0F, 0.5F, 1.0F}, new Color[]{this.colorPrimario, this.colorSecundario, this.colorPrimario});
            case AQUA:
                return new LinearGradientPaint(0.0F, 0.0F, 0.0F, getHeight(), new float[]{0.0F, 0.3F, 0.5F, 1.0F}, new Color[]{this.colorPrimario.brighter().brighter(), this.colorPrimario.brighter(), this.colorSecundario.darker().darker(), this.colorSecundario.darker()});
        }
        return new GradientPaint(0.0F, 0.0F, this.colorPrimario, x2, y2, this.colorSecundario);
    }

    public Color getColorPrimario() {
        return this.colorPrimario;
    }

    public void setColorPrimario(Color colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    public Color getColorSecundario() {
        return this.colorSecundario;
    }

    public void setColorSecundario(Color colorSecundario) {
        this.colorSecundario = colorSecundario;
    }

    public Gradiente getGradiente() {
        return this.gradiente;
    }

    public void setGradiente(Gradiente gradiente) {
        this.gradiente = gradiente;
    }
}
