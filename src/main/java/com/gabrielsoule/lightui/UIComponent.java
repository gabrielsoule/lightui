package com.gabrielsoule.lightui;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class UIComponent {

    public PApplet p;
    Canvas canvas;
    public int width;
    public int height;
    private ArrayList<UIComponent> children = new ArrayList<>();
    boolean visible = true;
    boolean destroyed = false;
    private boolean recalculateFlag = false; //set to TRUE when an element should be recalculated

    int offsetTop = 0;
    int offsetLeft = 0;
    int offsetRight = 0;
    int offsetBottom = 0;
    //the ACTUAL pixel offset from Processing's origin of this UI component
    //cached for efficiency; recalculated when window is resized or element is moved
    public int rawPosX;
    public int rawPosY;
    private String name;
    private UIComponent parent;
    private boolean debug = false;
    private float anchorMinX = 0;
    private float anchorMaxX = 1;
    private float anchorMinY = 0;
    private float anchorMaxY = 1;

    public UIComponent(String name) {
        this.name = name;
    }

    private UIComponent() {

    }

    public void draw() {}

    public void drawPixels() { }

    public void onPostResize() {}

    /** NVM THIS DOESNT WORK FUCK
     * When a UIComponent is immediately constructed, it might not know its width and height,
     * since that is often determined based on the parent (depending on anchor configuration).
     * This method is called after a new UIComponent has been assigned a parent, before the first invocation to draw().
     * It is only called once for any UIComponent.
     * Override it to do any initial setup that requires knowledge of the UIComponent's dimensions.
     */
    @Deprecated
    public void setup() {

    }

    void internalDraw() {

        if(this.recalculateFlag) {
            this.recalculate();
            this.recalculateFlag = false;
        }
        this.draw();
        if (canvas.debug || this.debug) {
            int rectSize = 8;
            int s = 150;
            int anchorMinXPos = (int) (parent.rawPosX + anchorMinX * parent.width);
            int anchorMaxXPos = (int) (parent.rawPosX + anchorMaxX * parent.width);
            int anchorMinYPos = (int) (parent.rawPosY + anchorMinY * parent.height);
            int anchorMaxYPos = (int) (parent.rawPosY + anchorMaxY * parent.height);
            p.rectMode(p.CORNER);
            p.noStroke();
            p.strokeWeight(0);
            p.fill(0, s, 255);
//            p.rect(this.rawPosX - rectSize, this.rawPosY - rectSize, rectSize, rectSize);;
            p.rect(anchorMinXPos - rectSize, anchorMinYPos - rectSize, rectSize, rectSize);
            p.fill(120, s, 255);
//            p.rect(this.rawPosX - rectSize, this.rawPosY + height, rectSize, rectSize);
            p.rect(anchorMinXPos - rectSize, anchorMaxYPos, rectSize, rectSize);
            p.fill(210, s, 225);
//            p.rect(this.rawPosX + width, this.rawPosY - rectSize, rectSize, rectSize);
            p.rect(anchorMaxXPos, anchorMinYPos - rectSize, rectSize, rectSize);
            p.fill(60, s, 255);
//            p.rect(this.rawPosX + width, this.rawPosY + height, rectSize, rectSize);
            p.rect(anchorMaxXPos, anchorMaxYPos, rectSize, rectSize);
            p.stroke(0, 0, 255);
            p.strokeWeight(1);
            p.line(rawPosX, rawPosY, rawPosX + width, rawPosY);
            p.line(rawPosX + width, rawPosY, rawPosX + width, rawPosY + height);
            p.line(rawPosX, rawPosY, rawPosX, rawPosY + height);
            p.line(rawPosX, rawPosY + height, rawPosX + width, rawPosY + height);
            p.stroke(0, 0, 0);
//            p.line(rawPosX + 1, rawPosY + 1, rawPosX + width - 1, rawPosY + 1);
//            p.line(rawPosX + width - 1, rawPosY + 1, rawPosX + width - 1, rawPosY + height - 1);
//            p.line(rawPosX + 1, rawPosY + 1, rawPosX + 1, rawPosY + height - 1);
//            p.line(rawPosX + 1, rawPosY + height - 1, rawPosX + width - 1, rawPosY + height - 1);

        }
        for (UIComponent child : children) {
            if(child.isVisible()) child.internalDraw();
        }

        for (UIComponent child : children) {
            if(child.isVisible()) child.drawChildren();
        }

    }

    private void drawChildren() {

    }


    void internalDrawPixels() {

        this.drawPixels();

        for (UIComponent child : children) {
            if(child.isVisible()) child.internalDrawPixels();
        }

//        if(canvas.debug) {
//            for(int x = -3; x < 4; x++) {
//                for(int y = -3; y < 4; y++) {
//                    setPixel(x, y, canvas.p.color(0, 255, 255));
//                }
//            }
//        }
    }

    public UIComponent setOffsets(int left, int right, int top, int bottom) {
        this.offsetLeft = left;
        this.offsetRight = right;
        this.offsetTop = top;
        this.offsetBottom = bottom;
        if (this.parent != null) {
            this.queueRecalculation();
        }
        return this;
    }

    public UIComponent setAnchors(float minX, float maxX, float minY, float maxY) {
        this.anchorMinX = minX;
        this.anchorMaxX = maxX;
        this.anchorMinY = minY;
        this.anchorMaxY = maxY;
        if (this.parent != null) {
            this.queueRecalculation();
        }
        return this;
    }


    public void hide() {
        this.visible = false;
    }

    public void show() {
        this.visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    //    /**
//     * Enable this disabled com.gabrielsoule.lightui.UIComponent, making it visible
//     * and usable. It will now be
//     */
//    public void enable() {
//
//    }
//
//    /**
//     * Disable this com.gabrielsoule.lightui.UIComponent and hide it from view.
//     * While disabled this component will not be updated.
//     * The com.gabrielsoule.lightui.UIComponent can be re-enabled later with the same
//     * state it had when it was first disabled.
//     */
//    public void disable() {
//
//    }

    public boolean isEnabled() {
        return visible;
    }

    public void destroy() {
        this.parent.children.remove(this);
        this.internalDestroy();
    }

    //the object that is destroyed must do one extra thing:
    //remove itself from the parent's list of children
    //we do not want this object's children to do that, since
    //we do not wish to modify our list while iterating through it
    //thus an internal method for destroy
    private void internalDestroy() {
        this.destroyed = true;
        for (UIComponent child : this.children) {
            child.internalDestroy();
        }
    }

    void setPixel(int x, int y, int color) {
        if (this.visible) {
            canvas.setPixel(rawPosX + x, rawPosY + y, color);
        }
    }

    /**
     * Adds a new UIComponent as a child of this UIComponent. The child component to be added should
     * not be a child of any other existing UIComponent.
     *
     * @param child a newly created UIComponent
     * @return the newly added child component, for method chaining
     * @throws IllegalArgumentException if the child already has a parent
     */
    public UIComponent addChild(UIComponent child) {
        return this.insertChild(this.getChildren().size(), child);
    }

    public UIComponent insertChild(int index, UIComponent child) {
        System.out.println("Setting parent of " + child.name + " to " + this.name);
        if (child.parent != null) {
            throw new IllegalArgumentException
                    (String.format("Component \"%s\" cannot be added as a child of component \"%s\" because it already has a parent \"%s\"", child.getName(), this.name, (child).getName()));
        }

        this.children.add(index, child);
        child.parent = this;
        child.canvas = this.canvas;
        child.p = this.p;
        child.recalculate();
        child.setup();
        return child;
    }

    public UIComponent getChildByName(String name) {
        for (UIComponent child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }

        return null;
    }

    public UIComponent getChildByNameRecursive(String name) {
        if (this.getChildren().size() != 0) {
            for (UIComponent child : this.getChildren()) {
                if (child.name.equals(name)) {
                    return child;
                } else {
                    UIComponent result = child.getChildByNameRecursive(name);
                    if (result != null) return result;
                }
            }

        }
        return null;
    }

    public List<UIComponent> getChildrenByName(String name) {
        ArrayList<UIComponent> list = new ArrayList<>();
        for (UIComponent child : children) {
            if (child.getName().equals(name)) {
                list.add(child);
            }
        }

        return list;
    }

    /**
     * Marks this
     */
    void queueRecalculation() {
        this.recalculateFlag = true;
    }

    /**sp
     * Immediately recalculates this UIComponent's width and height based on the parent's width and height, and then
     * invokes recalculate() for each of its children. Used when UI/window is resized/initialized.
     * Ideally use queueRecalculation() INSTEAD of this method, to minimize calls, unless you know what you're doing.
     */
    void recalculate() {
        if(this.canvas == null) this.canvas = this.parent.canvas;
//        this.width = (int) (parent.width * (1 - (anchorMinX + anchorMaxX)) - offsetLeft + offsetRight);
//        this.height = (int) (parent.height * (1 - (anchorMinY + anchorMaxY)) - offsetTop + offsetBottom);
        this.width = (int) ((parent.width * anchorMaxX + offsetRight) - (parent.width * anchorMinX + offsetLeft));
        this.height = (int) ((parent.height * anchorMaxY + offsetBottom) - (parent.height * anchorMinY + offsetTop));
        if (this.width < 0) this.width = 0;
        if (this.height < 0) this.height = 0;
        this.rawPosX = (int) (parent.rawPosX + (parent.width * anchorMinX) + offsetLeft);
        this.rawPosY = (int) (parent.rawPosY + (parent.height * anchorMinY) + offsetTop);
        this.onPostResize();
//        System.out.println(
//                String.format("Recalculation successful for component \"%s\": rawPosX = %s, rawPosY = %s, width = %s, height = %s",
//                        name, rawPosX, rawPosY, width, height));
        for (UIComponent child : this.children) {
            child.parent = this;
            child.p = this.p;
            child.canvas = this.canvas;
            child.recalculate();
        }
    }

    public List<UIComponent> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int getOffsetTop() {
        return offsetTop;
    }

    public UIComponent setOffsetTop(int offsetTop) {
        this.offsetTop = offsetTop;
        this.queueRecalculation();
        return this;
    }

    public int getOffsetLeft() {
        return offsetLeft;
    }

    public UIComponent setOffsetLeft(int offsetLeft) {
        this.offsetLeft = offsetLeft;
        this.queueRecalculation();
        return this;
    }

    public int getOffsetRight() {
        return offsetRight;
    }

    public UIComponent setOffsetRight(int offsetRight) {
        this.offsetRight = offsetRight;
        this.queueRecalculation();
        return this;
    }

    public int getOffsetBottom() {
        return offsetBottom;
    }

    public UIComponent setOffsetBottom(int offsetBottom) {
        this.offsetBottom = offsetBottom;
        this.queueRecalculation();
        return this;
    }

    public float getAnchorMinX() {
        return anchorMinX;
    }

    public UIComponent setAnchorMinX(float anchorMinX) {
        this.anchorMinX = anchorMinX;
        this.queueRecalculation();
        return this;
    }

    public float getAnchorMaxX() {
        return anchorMaxX;
    }

    public UIComponent setAnchorMaxX(float anchorMaxX) {
        this.anchorMaxX = anchorMaxX;
        this.queueRecalculation();
        return this;
    }

    public float getAnchorMinY() {
        return anchorMinY;
    }

    public UIComponent setAnchorMinY(float anchorMinY) {
        this.anchorMinY = anchorMinY;
        this.queueRecalculation();
        return this;
    }

    public float getAnchorMaxY() {
        return anchorMaxY;
    }

    public UIComponent setAnchorMaxY(float anchorMaxY) {
        this.anchorMaxY = anchorMaxY;
        this.queueRecalculation();
        return this;
    }

    public UIComponent getParent() {
        return parent;
    }

    public boolean debug() {
        return debug;
    }

    public UIComponent setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

}
