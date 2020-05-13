package com.gabrielsoule.lightui;

import processing.core.PGraphics;

/**
 * A UIComponent that draws to a PGraphics, which is then drawn on the screen.
 * It also includes a second PGraphics object which can be used as a mask.
 * This component is very useful for making elements with special/expensive effects,
 * since PGraphics buffers are stateful and do not have to be re-drawn every frame.
 * Both PGraphics objects have width and height equal to the component's bounding box.
 * Since PGraphics cannot be resized, a new instance has to be created if this component is resized.
 * This is expensive, and often the window is resized many times per second (if the user drags an edge slowly).
 * Thus GraphicsComponents try and wait until resizing is probably done, before re-creating PGraphics.
 */
public class GraphicsComponent extends UIComponent{

    public boolean useGraphics = true;
    public boolean useMask = true;

    PGraphics graphics;
    PGraphics mask;


    //I hate this part.
    boolean recreateGraphicsFlag = false;
    boolean recreateGraphicsQueuedFlag = false;
    int recalculateTimestamp;
    int recreateGraphicsDelay = 400;
    private boolean firstRecalculate = true;

    boolean needToSetup = true;

    public GraphicsComponent(String name) {
        super(name);
    }

//    @Override
//    public void setup() {
//        this.graphics = p.createGraphics(width, height);
//        this.mask = p.createGraphics(width, height);
//        System.out.println(this.width);
//        System.out.println(this.height);
//        graphics.beginDraw();
//        createGraphics(graphics);
//        graphics.endDraw();
//        mask.beginDraw();
//        createMask(mask);
//        mask.endDraw();
//        graphics.mask(mask);
//
//        //slightly wasteful, but no other way to detect whether overriding methods
//        if(!useGraphics) this.graphics = null;
//        if(!useMask) this.mask = null;
//    }

    @Override
    public void draw() {

        if(recreateGraphicsFlag && p.millis() - recalculateTimestamp > recreateGraphicsDelay) {
            if(recreateGraphicsQueuedFlag) {
                this.recreateAllGraphics();
            }

            this.recreateGraphicsQueuedFlag = false;
            this.recreateGraphicsFlag  = false;
        }

        if(graphics == null) return;

        graphics.beginDraw();
        this.drawGraphics(graphics);
        graphics.endDraw();
        if(useMask) {
            mask.beginDraw();
            this.drawMask(mask);
            mask.endDraw();
            graphics.mask(mask);
        }
        p.image(graphics, this.rawPosX, this.rawPosY);
    }

    private void recreateAllGraphics() {
        this.graphics = null;
        this.mask = null;

        if(useGraphics) {
            this.graphics = p.createGraphics(width+1, height+1);
            graphics.beginDraw();
            createGraphics(graphics);
            graphics.endDraw();
        }
        if(useMask) {
            this.mask = p.createGraphics(width+1, height+1);
            mask.beginDraw();
            createMask(mask);
            mask.endDraw();
            graphics.mask(mask);
        }
    }

    public void createGraphics(PGraphics graphics) {

    }

    public void createMask(PGraphics graphics) {

    }

    /**
     * Invoked every frame. Override this method if your
     * want to change your graphics every frame to create a dynamic effect.
     * If your graphics are static, you only need to override the createGraphics method.
     * @param graphics the PGraphics instance used by the GraphicsComponent.
     */
    public void drawGraphics(PGraphics graphics) {}

    /**
     * Invoked every frame. Override this method if your
     * want to change your mask every frame to create a dynamic effect.
     * If your mask is static, you only need to override the createMask method.
     * @param graphics the PGraphics instance used as a mask by the GraphicsComponent.
     */
    public void drawMask(PGraphics graphics) {}

    /**
     * Manually set the PGraphics used by this component. Useful for displaying a static image
     * that has already been defined somewhere.
     * Since graphics cannot be resized,
     * ONLY CALL THIS METHOD OF A COMPONENT THAT NEVER CHANGES SIZE,
     * i.e. anchored at a single point, or anchored to a parent that never changes size.
     * Otherwise, your graphics will disappear on resize.
     * It is wise to ensure that the graphics supplied has the same dimensions as the component,
     * otherwise undefined behaviour might occur!
     * @param graphics an existing PGraphics to be used for this component
     */
    public GraphicsComponent setGraphics(PGraphics graphics) {
        this.useGraphics = true;
        this.graphics = graphics;
        return this;
    }

    /**
     * Manually set the PGraphics used by this component. Useful for displaying a static image
     * that has already been defined somewhere.
     * Since graphics cannot be resized,
     * ONLY CALL THIS METHOD FOR COMPONENTS THAT NEVER CHANGE SIZE.
     * Otherwise, your graphics will disappear on resize.
     *  It is wise to ensure that the graphics supplied has the same dimensions as the component.
     * @param mask an existing PGraphics to be used as this component's mask
     */
    public GraphicsComponent setMask(PGraphics mask) {
        this.useMask = true;
        this.mask = mask;
        return this;
    }

    @Override
    public void recalculate() {
        int oldWidth = this.width;
        int oldHeight = this.height;
        System.out.println("Recalculate");
        super.recalculate();
//        if(this.firstRecalculate) {
//            this.firstRecalculate = false;
//            return;
//        }

        if(p != null && this.width > 0 && this.height > 0 && (this.width != oldWidth || this.height != oldHeight)) {
            if(!this.recreateGraphicsFlag) {
                this.recreateAllGraphics();
                this.recreateGraphicsFlag = true;
            } else {
                this.recreateGraphicsQueuedFlag = true;
            }

            this.recalculateTimestamp = p.millis();

        }
    }

}
