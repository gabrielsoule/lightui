package com.gabrielsoule.lightui;

import processing.core.PApplet;

/**
 * A single Canvas instance is the root of all UI elements.
 * Its width and height are always equal to the width and height of the screen.
 */
public class Canvas extends UIComponent {

    public boolean debug = false;
    private boolean currentlyResizing;
    int waitFrames = 0;

    public Canvas(PApplet app) {
        super("Canvas");
        this.p = app;
        this.width = p.width;
        this.height = p.height;
        this.canvas = this;
        this.rawPosX = 0;
        this.rawPosY = 0;
    }

    @Override
    public void draw() {
        if (p.width != this.width || p.height != this.height) {
            this.currentlyResizing = true;
            this.width = p.width;
            this.height = p.height;
            for (UIComponent child : this.getChildren()) {
                child.recalculate();
            }
        }
        for (UIComponent child : this.getChildren()) {
            child.internalDraw();
        }

        try {
            p.loadPixels();
            for (UIComponent child : this.getChildren()) {
                child.internalDrawPixels();
            }
            p.updatePixels();
        } catch (ArrayIndexOutOfBoundsException ignored) {
            System.out.println("[WARN] Out of bounds exception when loading/updating pixels");
        }
        currentlyResizing = false;
    }

    /**
     * Returns true if the window is currently being resized this frame,
     * by the user dragging one of the window edges around.
     */
    public boolean isCurrentlyResizing() {
        return currentlyResizing;
    }

    @Override
    void setPixel(int x, int y, int color) {
        if (x < 0 || x >= p.width || y < 0 || y >= p.height) {
            return;
        }

        p.pixels[y * p.width + x] = color;
    }
}
