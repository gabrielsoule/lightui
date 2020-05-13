package com.gabrielsoule.lightui;

import processing.core.PConstants;
import processing.core.PFont;

/**
 * UIComponent that displays text.
 * Like all UIComponents, TextComponent is equipped with a bounding box whose
 * dimensions depend on both the anchors and the parent's dimensions.
 * This bounding box can be used to position the text within a parent component,
 * e.g. a button or a header.
 * As such, the constants TOP, CENTER, and BOTTOM have a second meaning
 * in addition Processing's usual interpretation (when fed to textAlign).
 * They anchor the text to the top, center, or bottom of the component's bounding box, respectively,
 * always placing text inside the bounding box (if possible).
 * For example, the default bounding box for a TextComponent is anchored to the parent's center,
 * and has a width and height of zero.
 * Thus, if text is horizontally aligned to the TOP in this case,
 * text will appear BELOW the parent's center. Take a minute to consider why this makes sense.
 */
public class TextComponent extends UIComponent {

    public static final int TOP = PConstants.TOP;
    public static final int CENTER = PConstants.CENTER;
    public static final int BOTTOM = PConstants.BOTTOM;
    public static final int LEFT = PConstants.LEFT;
    public static final int RIGHT = PConstants.RIGHT;

    private static PFont defaultFont;

    private PFont font;
    private String text;
    private int fontSize = 12;
    private int horizontalAlignment = CENTER;
    private int verticalAlignment = CENTER;
    private int color;
    private float textOffset = 0;

    /**
     * Construct a new text component with a given name
     *
     * @param name name of the component--not the text to be displayed ;)
     */
    public TextComponent(String name) {
        super(name);
        this.setOffsets(0, 0, 0, 0);
        this.setAnchors(0, 1, 0, 1);
        this.color = 0xFFFFFFFF;
    }

    public TextComponent(String name, String text, PFont font, int fontSize) {
        this(name);
        this.font = font;
        this.fontSize = fontSize;
        this.text = text;
    }

    @Override
    public void draw() {
        if(this.font == null)
            p.textFont(defaultFont, fontSize);
        else
            p.textFont(font, fontSize);

        p.textSize(fontSize);
        p.textAlign(horizontalAlignment, verticalAlignment);
        p.textLeading(fontSize);
        p.fill(color);
        int x = 0;
        int y = 0;

        if (horizontalAlignment == LEFT) {
            x = this.rawPosX;
        } else if (horizontalAlignment == CENTER) {
            x = this.rawPosX + (this.width / 2);
        } else if (horizontalAlignment == RIGHT) {
            x = this.rawPosX + this.width;
        }

        if (verticalAlignment == TOP) {
            y = this.rawPosY;
        } else if (verticalAlignment == CENTER) {
            y = this.rawPosY + this.height / 2;
        } else if (verticalAlignment == BOTTOM) {
            y = this.rawPosY + this.height;
        }
//        System.out.println("Drawing text at " + x + " " + y);
//        System.out.println(getParent().height);
        p.text(text, x, y + textOffset * fontSize);
    }

    public PFont getFont() {
        return font;
    }

    public TextComponent setFont(PFont font) {
        this.font = font;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public TextComponent setText(String text) {
        this.text = text;
        return this;
    }

    public int getFontSize() {
        return fontSize;
    }

    public TextComponent setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public int getTextColor(int textColor) {
        return this.color;
    }

    public TextComponent setTextColor(int textColor) {
        this.color = textColor;
        return this;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public TextComponent setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public TextComponent setVerticalAlignment(int verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }


    public TextComponent setTextVerticalOffset(float offsetScale) {
        this.textOffset = offsetScale;
        return this;
    }

    public static PFont getDefaultFont() {
        return defaultFont;
    }

    public static void setDefaultFont(PFont defaultFont) {
        TextComponent.defaultFont = defaultFont;
    }
}
