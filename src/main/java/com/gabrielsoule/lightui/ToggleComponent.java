package com.gabrielsoule.lightui;

/**
 * Subclass of InteractableComponent that maintains a toggle state.
 * Overrides and replaces onMouseRelease() from its superclass
 * with onToggleEnable() and onToggleDisable().
 * I love that word, "toggle". It's so much fun.
 */
public class ToggleComponent extends InteractableComponent {

    private boolean toggled;
    private ToggleGroup toggleGroup;

    public ToggleComponent(String name) {
        super(name);
        toggled = false;
        this.setToggled(false);
    }

    public boolean isToggled() {
        return toggled;
    }

    public ToggleComponent setToggled(boolean state) {
        if(this.toggled == state) return this;


        this.toggled = state;
        if(state) this.onToggleEnable();
        else this.onToggleDisable();

        return this;
    }

    public void onToggleEnable() {

    }

    public void onToggleDisable()  {

    }

    @Override
    public void onMouseClick() {
        if(this.toggleGroup != null) {
            //ask the toggle group nicely if we can toggle ourselves
            //this will be false if and only if we are already toggled on,
            //but we ask anyway (maybe down the line I will add more complex behavior to ToggleGroups)
            boolean allowed = toggleGroup.itemToggled(this);
            if(allowed) {
                this.setToggled(!this.isToggled());

            }
        }

    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
    }
}
