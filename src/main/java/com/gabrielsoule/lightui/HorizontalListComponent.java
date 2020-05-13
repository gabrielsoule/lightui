package com.gabrielsoule.lightui;


import processing.core.PConstants;

import java.util.List;

/**
 * Represents a vertical list of components.
 *
 * A list does not draw anything to the screen on its own (aside from its children, of course).
 * If you wish to add a background to your list, add it as a child to a UIComponent that renders something
 * (e.g. a PanelComponent), and have the list stretch to fit its parent fully. Alternatively, you can
 * override the draw() method of the VerticaListComponent and draw things there.
 *
 * By default, all children of this component are
 * considered elements of the list and will be treated as such.
 * If you wish to add a child to this component that is not to be
 * part of the list, consider wrapping the list within an empty
 * UIComponent and adding children to the wrapper component.
 *
 * By default, elements will have their horizontal anchors to the left
 * and right edges of the list, thus they will stretch to fit the list's width.
 * Their vertical offsets determine the height, which is kept constant
 * (vertical anchors are both set to a single point).
 *
 * A spacing value can be supplied, which determines the space between
 * each element in a list. If granular, per-element control over spacing is desired,
 * wrap your list elements inside empty UIComponents and control spacing via the
 * wrapped component's offset to its parent.
 */
public class HorizontalListComponent extends UIComponent {

    public static final int STRETCH_ELEMENTS = 0;
    public static final int STACK_ELEMENTS = 1;
    public static final int LEFT = PConstants.LEFT;
    public static final int CENTER = PConstants.CENTER;
    public static final int RIGHT = PConstants.RIGHT;

    private UIComponent container;
    private int elementBehavior;
    private int elementAlignment;
    private int spacing;

    public HorizontalListComponent(String name) {
        this(name, STACK_ELEMENTS, LEFT);
    }


    public HorizontalListComponent(String name, int elementBehavior) {
        this(name, elementBehavior, LEFT);
    }

    public HorizontalListComponent(String name, int elementBehavior, int elementAlignment) {
        super(name);
        this.elementBehavior = elementBehavior;
        this.elementAlignment = elementAlignment;
        this.container = this.addChild(new UIComponent(this.getName() + "-item-container")
                .setOffsets(0, 0, 0, 0));
        switch (elementAlignment){
            case LEFT:
                this.container.setAnchors(0, 0, 0, 1);
                break;
            case RIGHT:
                this.container.setAnchors(1, 1, 0, 1);
                break;
            case CENTER:
                this.container.setAnchors(0.5f, 0.5f, 0, 1);
                break;
        }

//        container.setDebug(true);
    }

    /**
     * Recompute the positions and heights of all elements in the list.
     */
    private void recalculateContainer() {
        int containerWidth = spacing;
        for(UIComponent child : this.container.getChildren()) {
            int elementWidth = -child.getOffsetLeft() + child.getOffsetRight();
            child.setOffsetLeft(containerWidth);
            child.setOffsetRight(containerWidth + elementWidth);
            containerWidth += child.width + spacing;
        }

        if(elementAlignment == LEFT) {
            container.setOffsetRight(containerWidth);
        } else if(elementAlignment == RIGHT) {
            container.setOffsetLeft(-containerWidth);
        } else {
            container.setOffsetLeft(-containerWidth / 2);
            container.setOffsetRight(containerWidth / 2);
        }

    }

    @Override
    public void draw() {

    }

    public UIComponent addElement(UIComponent element) {
        return this.insertElement(this.getElements().size(), element);
    }

    public UIComponent insertElement(int index, UIComponent element) {
        element.setAnchorMinX(0);
        element.setAnchorMaxX(0);
        element.setAnchorMinY(0);
        element.setAnchorMaxY(1);
        this.container.insertChild(index, element);
        this.recalculateContainer();
        return element;
    }
//        this.container.insertChild(element, index);
//        element.setAnchorMinX(0);
//        element.setAnchorMaxX(1);
//        element.setAnchorMinY(0);
//        element.setAnchorMaxY(0);
//        int elementHeight = -element.getOffsetTop() + element.getOffsetBottom();
//        element.setOffsetTop(container.height + spacing);
//        element.setOffsetBottom(container.height + spacing + elementHeight);
//        for(int i = index + 1; i < container.getChildren().size(); i++) {
//            UIComponent child = container.getChildren().get(i);
//            child.setOffsetTop(child.getOffsetTop() + elementHeight);
//            child.setOffsetBottom(child.getOffsetBottom() + elementHeight);
//        }
//
//        if(elementAlignment == TOP) {
//            container.setOffsetBottom(container.getOffsetBottom() + elementHeight + spacing);
//        } else if(elementAlignment == BOTTOM) {
//            container.setOffsetTop(container.getOffsetTop() - (elementHeight + spacing));
//        } else {
//            int containerOffset = container.getOffsetTop() + container.getOffsetBottom() + elementHeight + spacing;
//            container.setOffsetTop(containerOffset / 2);
//            container.setOffsetBottom(containerOffset / 2);
//        }
//
//        return element;
//    }

    public HorizontalListComponent setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    public UIComponent getContainer() {
        return container;
    }

    public List<UIComponent> getElements() {
        return container.getChildren();
    }
}
