package com.gabrielsoule.lightui;

import processing.core.PConstants;

/**
 * Encapsulates a Processing rectangle inside a UIComponent
 * Good for UI backgrounds, buttons, etc
 * A border color and border thickness can also be specified.
 * Supports transparency.
 */
public class RectComponent extends UIComponent {

    public static final int OUTER = -1;
    public static final int CENTER = 0;
    public static final int INNER = 1;

    private int color = 0xFFFFFFFF;
    private int strokeColor = 0xFFFFFFFF;
    private int strokeWidth = 0;
    private int cornerRadius = 0;
    private int strokeOffset;
    private int strokeAnchor = CENTER;

    public RectComponent(String name) {
        super(name);
        this.setOffsets(0, 0, 0, 0);
        this.setAnchors(0, 1, 0, 1);
    }

    public RectComponent(String name, int fillColor) {
        this(name, fillColor, 0x00FFFFFF, 0);
    }

    public RectComponent(String name, int fillColor, int strokeColor, int strokeWidth) {
        super(name);
        this.setFillColor(fillColor);
        this.setStrokeColor(strokeColor);
        this.setStrokeWeight(strokeWidth);
        this.setOffsets(0, 0, 0, 0);
        this.setAnchors(0, 1, 0, 1);
    }

    public RectComponent(String name, int color, int strokeColor, int strokeWidth, int cornerRadius) {
        this(name, color, strokeColor, strokeWidth);
        this.cornerRadius = cornerRadius;
    }

    public int getFillColor() {
        return color;
    }

    public RectComponent setFillColor(int color) {
        this.color = color;
        return this;
    }

    public int getCornerRadius() {
        return this.cornerRadius;
    }

    public RectComponent setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public RectComponent setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public RectComponent setStrokeWeight(int strokeWeight) {
        this.strokeWidth = strokeWeight;
        recalculateStrokeOffset();
        return this;
    }

    public RectComponent setStrokeAnchor(int anchor) {
        this.strokeAnchor = anchor;
        recalculateStrokeOffset();
        return this;
    }

    private void recalculateStrokeOffset() {
        switch (this.strokeAnchor){
            case OUTER:
                this.strokeOffset = -this.strokeWidth  / 2;
                break;
            case CENTER:
                this.strokeOffset = 0;
                break;
            case INNER:
                this.strokeOffset = this.strokeWidth / 2;
                break;
            default:
                throw new IllegalArgumentException("Stroke anchor of RectComponent must be OUTER, CENTER, or INNER!");
        }
    }

    @Override
    public void draw() {
        p.rectMode(PConstants.CORNER);
        p.fill(color);
        p.stroke(strokeColor);
        if(strokeWidth == 0 ) p.noStroke();
        p.strokeWeight(strokeWidth);
        p.rect(rawPosX + strokeOffset,
                rawPosY + strokeOffset,
                width - strokeOffset * 2 ,
                height - strokeOffset * 2 , cornerRadius);
    }
}
