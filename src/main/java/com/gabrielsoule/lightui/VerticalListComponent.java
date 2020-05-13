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
public class VerticalListComponent extends UIComponent {

    public static final int STRETCH_ELEMENTS = 0;
    public static final int STACK_ELEMENTS = 1;
    public static final int TOP = PConstants.TOP;
    public static final int CENTER = PConstants.CENTER;
    public static final int BOTTOM = PConstants.BOTTOM;

    private UIComponent container;
    private int elementBehavior;
    private int elementAlignment;
    private int spacing;

    public VerticalListComponent(String name) {
        this(name, STACK_ELEMENTS, TOP);
    }


    public VerticalListComponent(String name, int elementBehavior) {
        this(name, elementBehavior, TOP);
    }
     
    public VerticalListComponent(String name, int elementBehavior, int elementAlignment) {
        super(name);
        this.elementBehavior = elementBehavior;
        this.elementAlignment = elementAlignment;
        this.container = this.addChild(new UIComponent(this.getName() + "-item-container")
                .setOffsets(0, 0, 0, 0));
        if(this.elementBehavior == STACK_ELEMENTS) {
            switch (elementAlignment){
                case TOP:
                    this.container.setAnchors(0, 1, 0, 0);
                    break;
                case BOTTOM:
                    this.container.setAnchors(0, 1, 1f, 1f);
                    break;
                case CENTER:
                    this.container.setAnchors(0, 1, 0.5f, 0.5f);
                    break;
            }
        } else {
            this.container.setAnchors(0, 1, 0, 1);
        }

//        container.setDebug(true);
    }

    /**
     * Recompute the positions and heights of all elements in the list.
     */
    private void recalculateContainer() {
        if(this.elementBehavior == STACK_ELEMENTS) {
            int containerHeight = spacing;
            for(UIComponent child : this.container.getChildren()) {
                int elementHeight = -child.getOffsetTop() + child.getOffsetBottom();
                child.setOffsetTop(containerHeight);
                child.setOffsetBottom(containerHeight + elementHeight);
                containerHeight += child.height + spacing;
            }
            if(elementAlignment == TOP) {
                container.setOffsetBottom(containerHeight);
            } else if(elementAlignment == BOTTOM) {
                container.setOffsetTop(-containerHeight);
            } else {
                container.setOffsetTop(-containerHeight / 2);
                container.setOffsetBottom(containerHeight / 2);
            }
        } else if(this.elementBehavior == STRETCH_ELEMENTS) {
            //TODO implement spacing for stretched elements properly
//            int totalSpacing = (this.getElements().size() + 1) * spacing;
            for(int i = 0; i < this.getContainer().getChildren().size(); i++) {
                UIComponent element = this.getContainer().getChildren().get(i);
                element.setOffsetTop(0);
                element.setOffsetBottom(0);
                element.setAnchorMinX(0);
                element.setAnchorMaxX(1);
                element.setAnchorMinY(i / (float) this.getElements().size());
                element.setAnchorMaxY((i + 1) / (float) this.getElements().size());
            }
        } else {
            throw new IllegalStateException("elementBehavior of VerticalListComponent must either be STACK_ELEMENTS or STRETCH_ELEMENTS!");
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
        element.setAnchorMaxX(1);
        element.setAnchorMinY(0);
        element.setAnchorMaxY(0);
        this.container.insertChild(index, element);
        this.recalculateContainer();
        return element;
    }

//    public UIComponent _addChild(UIComponent element) {
//
//
//
//
//
//        if(this.elementBehavior == STACK_ELEMENTS) {
//            int listHeight = this.getChildren().size() == 0 ? 0 : this.spacing;
//            for(UIComponent child : this.getChildren()) {
//                listHeight = child.height + this.spacing;
//            }
//            int elementHeight = -element.getOffsetTop() + element.getOffsetBottom();
//            element.setAnchorMinX(0);
//            element.setAnchorMaxX(1);
//            if(this.elementAlignment == CENTER) {
//                int offset = listHeight / 2;
//                for(UIComponent child : this.getChildren()) {
//
//                }
//            }
//            else if(this.elementAlignment == TOP || this.elementAlignment == BOTTOM) {
//                if(this.elementAlignment == TOP) {
//                    element.setAnchorMinY(0);
//                    element.setAnchorMaxY(0);
//                    element.setOffsetTop(listHeight);
//                    element.setOffsetBottom(listHeight + elementHeight);
//                } else {
//                    element.setAnchorMinY(1);
//                    element.setAnchorMaxY(1);
//                    element.setOffsetBottom(-listHeight);
//                    element.setOffsetTop(-listHeight - elementHeight);
//                }
//            }
//        }
//
//        super.addChild(element);
//        return element;
//    }

    public VerticalListComponent setSpacing(int spacing) {
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
