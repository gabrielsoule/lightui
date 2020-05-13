package com.gabrielsoule.lightui;

import java.util.ArrayList;

/**
 * Manages a set of Toggles, ensuring that only one can be active at a time.
 * A ToggleGroup does not draw anything, so its dimensions do not matter,
 * and it can be added as a child of any component in the hierarchy.
 *
 * Alternatively, since a ToggleGroup is essentially an empty UIComponent,
 * a ToggleGroup and its anchors/offsets can be used as a container for a list of toggles
 * (most likely the toggles that form the group).
 *
 * It is the programmer's responsibility to ensure that one and only one toggle in
 * the group is switched on from the beginning.
 */
public class ToggleGroup extends UIComponent{
    private ArrayList<ToggleComponent> toggles;
    private ToggleComponent currentlyToggled;
    public ToggleGroup(String name) {
        super(name);
        this.toggles = new ArrayList<>();
    }

    public void addToggle(ToggleComponent toggle) {
        toggle.setToggleGroup(this);
        this.toggles.add(toggle);
        if(toggle.isToggled()) {
            if(currentlyToggled == null) {
                currentlyToggled = toggle;
            } else { //this shouldn't happen, if the programmer is being a good boy
                currentlyToggled.setToggled(false);
                currentlyToggled = toggle;
            }
        }
    }

    /**
     * Called when a managed toggle wants to be toggled on or off;
     * Returns true if the toggle is allowed to proceed with its desired action,
     * false otherwise. (false generally when the toggle wants to be toggled off and it is the active toggle)
     * @param toggle
     * @return NYI
     */
    boolean itemToggled(ToggleComponent toggle) {
        if(toggle == currentlyToggled) {
            return false;
        }
        else {
            for(ToggleComponent t : toggles) {
                if (t != toggle) {
                    t.setToggled(false);
                }
            }

            currentlyToggled = toggle;

            return true;
        }
    }
}
