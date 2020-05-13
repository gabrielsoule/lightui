package com.gabrielsoule.lightui;

/**
 * A component that responds to mouse interactions.
 * This is a low-level class that can be used to make buttons,
 * toggles, sliders, et cetera.
 *
 */
public class InteractableComponent extends UIComponent{

    public boolean mouseHover; //true when the cursor is hovering over the component
    public boolean mouseClicked; //true during the frame that the mouse is first clicked
    public boolean mouseHeld; //true while the mouse is held down over the component
    public boolean mouseReleased; //true during the frame that a: the mouse is released over the component, or b: the mouse is dragged outside the component

    public enum InteractionState {
        INACTIVE, HOVER, PRESSED
    }

    private InteractionState state = InteractionState.INACTIVE;

    int mouseClickTimestamp;
    int mouseReleaseTimestamp;
    int mouseHoverEnterTimestamp;
    int mouseHoverExitTimestamp;


    public InteractableComponent(String name) {
        super(name);
    }

    @Override
    public void draw() {
        //mouseX, mouseY, mousePressed
        if(p.mouseX >= this.rawPosX && p.mouseX <= this.rawPosX + this.width
        && p.mouseY >= this.rawPosY && p.mouseY <= this.rawPosY + this.height) {
            if(p.mousePressed) {
                if(!mouseHeld && !mouseClicked) {
                    this.mouseClicked = true;
                    this.mouseHover = false;
                    this.onMouseClick();
                    this.state = InteractionState.PRESSED;
                } else if(mouseClicked  && !mouseHeld) {
                    this.mouseClicked = false;
                    this.mouseHeld = true;
                    this.state = InteractionState.PRESSED;
                }
            } else {
                if(this.mouseHeld) {
                    this.mouseHeld = false;
                    this.onMouseRelease();
                    this.state = InteractionState.INACTIVE;
                }
                if(!this.mouseHover) {
                    this.onMouseHoverEnter();
                    this.mouseHover = true;
                    this.state = InteractionState.HOVER;
                }
            }
        } else {
            if(this.mouseHeld) {
                this.mouseHeld = false;
                this.onMouseDragExit();
            }
            else if(this.mouseHover) {
                this.mouseHover = false;
                this.onMouseHoverExit();
            }

            this.state = InteractionState.INACTIVE;
        }
    }

    public void onMouseClick() {

    }

    public void onMouseRelease() {

    }

    public void onMouseHoverEnter() {

    }

    public void onMouseDragExit() {

    }

    public void onMouseHoverExit()  {

    }


    public InteractionState getState() {
        return state;
    }


}
