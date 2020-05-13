package com.gabrielsoule.lightui;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public class Main extends PApplet {
    Canvas canvas;
    PGraphics rect;
    PGraphics maskImage;

    public static void main(String[] args) {
        PApplet.main("com.gabrielsoule.lightui.Main");
    }

    @Override
    public void settings() {
        size(1920, 1080);
    }

    @Override
    public void setup() {
        surface.setResizable(true);
        frameRate(45);
        int a = CENTER;
        this.canvas = new Canvas(this);
        colorMode(HSB, 360, 255, 255, 255);

        VerticalListComponent list = new VerticalListComponent("List", VerticalListComponent.STACK_ELEMENTS, VerticalListComponent.TOP);
        list.setAnchors(0, 0.2f, 0, 1);
        list.setSpacing(5);
//        canvas.addChild(list);

        class TestToggle extends ToggleComponent {

            final RectComponent buttonPanel;

            public TestToggle(String name) {
                super(name);
                buttonPanel = (RectComponent) this.addChild(new RectComponent("ButtonPanel"));
                buttonPanel.setStrokeWeight(3);

            }


            @Override
            public void onMouseHoverEnter() {
                buttonPanel.setFillColor(color(0, 100, 255));
                System.out.println("Hover Enter");
            }

            @Override
            public void onToggleEnable() {
                System.out.println("Enable");
                buttonPanel.setStrokeColor(color(200, 255, 255));
            }

            @Override
            public void onToggleDisable() {
                System.out.println("Disable");
                buttonPanel.setStrokeColor(color(0, 255, 255));
            }

            @Override
            public void onMouseHoverExit() {
                System.out.println("Hover exit");
                buttonPanel.setFillColor(color(0, 0, 255));

            }
        }

        ToggleGroup group = (ToggleGroup) list.addChild(new ToggleGroup("ToggleGroup"));

        TestToggle toggle1 = new TestToggle("Toggle");
        toggle1.setOffsets(0, 0, 0, 50);
        group.addToggle(toggle1);
        list.insertElement(0, toggle1);

        TestToggle toggle2 = new TestToggle("Toggle");
        toggle2.setOffsets(0, 0, 0, 50);
        group.addToggle(toggle2);
        list.insertElement(0, toggle2);

        TestToggle toggle3 = new TestToggle("Toggle");
        toggle3.setOffsets(0, 0, 0, 50);
        group.addToggle(toggle3);
        list.insertElement(0, toggle3);

//        UIComponent test1 = canvas.addChild(new RectComponent("rect", color(0, 255, 255)) {
//            @Override
//            public void setup() {
//                System.out.println("Setup");
//                System.out.println(this.width);
//                System.out.println(this.height);
//                System.out.println("Setup done");
//            }
//
//            @Override
//            public void draw() {
//                super.draw();
//                System.out.println(this.width);
//                System.out.println(this.height);
//            }
//        })
//                .setAnchors(0, 1, 0.33f, 0.66f)
//                .setOffsets(50, -50, 15, -15);
//        System.out.println(test1.width);
//        System.out.println(test1.height);

        VerticalListComponent list2 = (VerticalListComponent) canvas.addChild(new VerticalListComponent("Test45", VerticalListComponent.STRETCH_ELEMENTS))
                .setAnchors(0, 0.2f, 0.5f, 1);
        list2.setDebug(false);
        list2.addElement(new RectComponent("fsdfs", 0xFFFFFFFF))
                .setDebug(true);
        list2.addElement(new RectComponent("fsdfs", color(50, 255, 255)))
                .setDebug(true);
        list2.addElement(new RectComponent("fsdfs", color(190, 255, 255)))
                .setDebug(true);
        list2.addElement(new RectComponent("fsdfs", color(100, 255, 255)))
                .setDebug(true);
        list2.addElement(new RectComponent("fsdfs", color(100, 55, 255)))
                .setDebug(true);

        HorizontalListComponent h = (HorizontalListComponent) canvas.addChild(new HorizontalListComponent("horiz", HorizontalListComponent.STACK_ELEMENTS, HorizontalListComponent.RIGHT));
        h.setAnchors(0.5f, 0.5f, 0.5f, 0.5f);
        h.setOffsets(-300, 300, -50, 50);
        h.setDebug(true);
        h.setSpacing(30);

        h.addElement(new RectComponent("r", 0xFFFFFFFF, 0, 0, 0)
        .setOffsets(-20, 20, 0, 0));
        h.addElement(new RectComponent("r", 0xFFFFFFFF, 0, 0, 0)
                .setOffsets(-20, 20, 0, 0));
        h.addElement(new RectComponent("r", 0xFFFFFFFF, 0, 0, 0)
                .setOffsets(-20, 20, 0, 0));


//
//
//        list.insert(0, new TestToggle("Toggle").setOffsets(0, 0, 0, 50));
//
//        list.insert(1, new TestToggle("Toggle").setOffsets(0, 0, 0, 50));
//
//        list.insert(2, new TestToggle("Toggle").setOffsets(0, 0, 0, 50));


    }

    public void _test() {
        //        canvas.addChild(new UIComponent("Test") {
//            @Override
//            void draw() {
//                p.fill(p.color(0, 255, 255, 100));
//                p.rect(this.rawPosX, this.rawPosY, this.width, this.height);
//            }
//        }
//                .setAnchors(0, 1, 0, 1)
//                .setOffsets(100, -100, 100, -100))
//
//                .addChild(new UIComponent("Test2") {
//                    @Override
//                    void draw() {
//                        p.fill(p.color(80, 255, 255));
//                        p.rect(this.rawPosX, this.rawPosY, this.width, this.height);
//                    }
//                }
//                        .setAnchors(0.5f, 0.5f, 0.5f, 0.5f)
//                        .setOffsets(-200, 200, -200, 200));
        int strokeWeight = 3;
        PFont fontBold = createFont("Proxima Nova Bold.otf", 100);
        PFont fontRegular = createFont("ProximaNova-Regular.otf", 100);
        TextComponent text = new TextComponent("test");
        text.setFontSize(color(0, 0, 255));
        text.setFontSize(50);
        text.setFont(fontBold);
        int radius = 2;
        text.setText("Test");
//        canvas.addChild(text);
//        for (int i = 0; i < 3; i++) {
//            canvas.addChild(new UIComponent("Menu") {
//                @Override
//                public void draw() {
//                    p.fill(260, 255, 100);
//                    p.strokeWeight(strokeWeight);
//                    p.stroke(260, 255, 255);
//                    p.rect(this.rawPosX, this.rawPosY, this.width, this.height, radius);
//                }
//            }
//                    .setAnchors(i / 3f, (i + 1) / 3f, 0, 1)
//                    .setOffsets(30, -30, 30, -30));
//        }
////
//        int padding = 15;
//        int h = 60;
//        for (UIComponent menu : canvas.getChildrenByName("Menu")) {
//            for (int i = 0; i < 8; i++) {
//                UIComponent item = menu.addChild(new UIComponent("Item") {
//                    @Override
//                    public void draw() {
//                        p.fill(260, 100, 100);
//                        p.stroke(260, 100, 255);
//                        p.strokeWeight(0);
//                        p.noStroke();
//                        p.rect(this.rawPosX, this.rawPosY, this.width, this.height, radius);
//                    }
//                }
//                .setAnchors(0, 1, 0, 0)
//                .setOffsets(padding, -padding, ((i + 1) * padding + i * h), ((i + 1) * padding + (i + 1) * h)));
//
//                TextComponent t = (TextComponent) item.addChild(new TextComponent("text"));
//
//                t.setFont(fontBold)
//                        .setTextSize(25)
//                        .setTextColor(color(260, 100, 255))
//                        .setHorizontalAlignment(TextComponent.LEFT)
//                        .setVerticalAlignment(TextComponent.CENTER)
//                        .setText("Information information")
//                        .setTextOffset(-3)
//                        .setAnchors(0, 0, 0, 1)
//                        .setOffsets(10, 0, 0, 0);
//            }
//        }

        int fillColor = color(260, 0, 0);
        int strokeColor = color(0, 255, 255);


        UIComponent panel = new RectComponent("popup")
                .setFillColor(fillColor)
                .setStrokeColor(strokeColor)
                .setStrokeWeight(strokeWeight)
                .setCornerRadius(radius)
                .setAnchors(0.5f, 0.5f, 0.5f, 0.5f)
                .setOffsets(-400, 400, -200, 200);

        UIComponent header = panel.addChild(new RectComponent("header")
                .setFillColor(fillColor)
                .setStrokeColor(strokeColor)
                .setStrokeWeight(strokeWeight)
                .setCornerRadius(radius)
                .setAnchors(0.5f, 0.5f, 0, 0)
                .setOffsets(-100, 100, -30, 30));

        header.addChild(new TextComponent("headerText")
                .setFontSize(30)
                .setTextVerticalOffset(-0.1f)
                .setTextColor(strokeColor)
                .setFont(fontBold)
                .setText("ERROR"));

        panel.addChild(new TextComponent("bodyText")
                .setFont(fontRegular)
                .setFontSize(30)
                .setTextColor(strokeColor)
                .setText("Failed to connect to Open Pixel Control server. \n Ensure that the Fadecandy daemon is running on \n the controller device, and try again."));

//        canvas.addChild(panel);

//        canvas.addChild(new RectComponent("Left").setFillColor(color(0, 255, 255)))
//              .setAnchors(0, 0, 0, 1)
//              .setOffsets(20, 220, 20, -20);
//        canvas.addChild(new RectComponent("Right"){
//            @Override
//            public void onPostResize() {
//                this.setAnchorMinX((this.getParent().getChildByName("Left").getOffsetRight() / (float) this.getParent().width()));
////                System.out.println(this.getParent().getChildByName("Left").getOffsetRight());
////                System.out.println(this.getParent().width());
////                System.out.println(this.getAnchorMinX());
//            }
//        }
//                .setFillColor(color(100, 255, 255, 50)))
//                .setAnchors(0, 1, 0, 1)
//                .setOffsets(20, -20, 20, -20);

//        canvas.addChild(new UIComponent("test111") {
//            @Override
//            public void drawPixels() {
//                for(int j = this.rawPosY; j < this.rawPosY + this.height; j++)
//                 {
//                     for(int i = this.rawPosX; i < this.rawPosX + this.width; i++) {
//                        p.pixels[p.width * j + i] = p.color(0, 255, 255);
//                    }
//                }
//            }
//        })
//                .setOffsets(-100, 100, -100, 100)
//                .setAnchors(0.5f, 0.5f, 0.5f, 0.5f)
//                .setDebug(true);

//        VerticalListComponent list = (VerticalListComponent)
//                canvas.addChild(new VerticalListComponent("List", VerticalListComponent.STACK_ELEMENTS, VerticalListComponent.TOP){
//                    @Override
//                    public void draw() {
//                        fill(100, 255, 255);
//                        noStroke();
//                        rect(this.rawPosX, this.rawPosY, this.width, this.height);
//                    }
//                });
//        list.setAnchors(0, 0, 0, 1);
//        list.setOffsets(40, 300, 40, -40);
////        list.setDebug(true);
//        list.setSpacing(10);

        class TestButton extends InteractableComponent {

            final RectComponent buttonPanel;

            public TestButton(String name) {
                super(name);
                buttonPanel = (RectComponent) this.addChild(new RectComponent("ButtonPanel"));
            }

            @Override
            public void onMouseClick() {
                System.out.println("Click");
                buttonPanel.setFillColor(color(0, 200, 255));

            }

            @Override
            public void onMouseRelease() {
                System.out.println("Release");
            }

            @Override
            public void onMouseHoverEnter() {
                buttonPanel.setFillColor(color(0, 100, 255));
                System.out.println("Hover Enter");
            }

            @Override
            public void onMouseDragExit() {
                System.out.println("Drag exit");
                buttonPanel.setFillColor(color(0, 0, 255));

            }

            @Override
            public void onMouseHoverExit() {
                System.out.println("Hover exit");
                buttonPanel.setFillColor(color(0, 0, 255));

            }
        }

//        canvas.addChild(new TestButton("Button") {
//
//        })
//                .setAnchors(0.5f, 0.5f, 0.5f, 0.5f)
//                .setOffsets(-100, 100, -100, 100)
//                .setDebug(true);

        class TestToggle extends ToggleComponent {

            final RectComponent buttonPanel;

            public TestToggle(String name) {
                super(name);
                buttonPanel = (RectComponent) this.addChild(new RectComponent("ButtonPanel"));
                buttonPanel.setStrokeWeight(6);

            }


            @Override
            public void onMouseHoverEnter() {
                buttonPanel.setFillColor(color(0, 100, 255));
                System.out.println("Hover Enter");
            }

            @Override
            public void onToggleEnable() {
                buttonPanel.setStrokeColor(color(200, 255, 255));
            }

            @Override
            public void onToggleDisable() {
                buttonPanel.setStrokeColor(color(0, 255, 255));
            }

            @Override
            public void onMouseHoverExit() {
                System.out.println("Hover exit");
                buttonPanel.setFillColor(color(0, 0, 255));

            }
        }

        canvas.addChild(new TestToggle("Button"))
                .setAnchors(0.5f, 0.5f, 0.5f, 0.5f)
                .setOffsets(-100, 100, -100, 100)
                .setDebug(true);


    }

    @Override
    public void draw() {
        background(0);
        canvas.draw();
//        rect.beginDraw();
//        rect.beginDraw();
//        rect.loadPixels();
//        for(int i = 0; i < rect.pixels.length; i++) {
//            rect.pixels[i] = color(0, 0, ((int) random(0, 2)) * 255);
//        }
//        rect.updatePixels();
//        rect.endDraw();
//        rect.mask(maskImage);
////        image(rect, 10, 10);
////        image(maskImage, 700, 500);
//        fill(0);
//        strokeWeight(10);
//        stroke(color(0, 0, 255, 255));
//        rect(1, 1, 100, 100);

    }

    @Override
    public void keyPressed() {
        VerticalListComponent list = (VerticalListComponent) canvas.getChildByName("List");
//        list.insertElement(list.getContainer().getChildren().size(), new RectComponent("item")
//                .setFillColor(color(random(0, 360), 255, 255))
//                .setStrokeWeight(0)
//                .setStrokeColor(color(200, 255, 255))
//                .setOffsets(10, -10, 0, (int) random(40, 90))
//                .setDebug(true));
////        list.getContainer().setDebug(true);
//        canvas.addChild(new GraphicsComponent("Test"){
//            @Override
//            public void createGraphics(PGraphics graphics) {
//                graphics.rectMode(CORNER);
//                graphics.noStroke();
//                graphics.fill(p.color(255, 255, 255));
//                graphics.rect(0, 0, width, height);
//
//            }
//
////            @Override
//            public void createMask(PGraphics mask) {
//                mask.noFill();
//                mask.stroke(p.color(0, 0, 255, 200));
//                mask.strokeWeight(3);
//                mask.rect(1,1, width - 3, height - 3, 60);
//            }
//        })
//                .setOffsets(200, -200, 200, -200)
//                .setAnchors(0f, 1f, 0f, 1f).setDebug(false);
    }

    @Override
    public void stop() {
    }
}
