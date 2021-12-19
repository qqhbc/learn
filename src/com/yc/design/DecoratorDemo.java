package com.yc.design;

interface VisualComponent{
    void draw();
    void resize();
}
class TextView implements VisualComponent{

    @Override
    public void draw() {
        System.out.println("view draw");
    }

    @Override
    public void resize() {
        System.out.println("view resize");
    }
}
class Decorator implements VisualComponent {
    private final VisualComponent visualComponent;
    public Decorator(VisualComponent visualComponent){
        this.visualComponent = visualComponent;
    }

    @Override
    public void draw() {
        visualComponent.draw();
    }

    @Override
    public void resize() {
        visualComponent.resize();
    }
}
class BorderDecorator extends Decorator{
    private final int width;

    public BorderDecorator(VisualComponent visualComponent, int borderWidth) {
        super(visualComponent);
        this.width = borderWidth;
    }

    private void drawBorder(int width){
        System.out.println("decorator border width " + width);
    }

    @Override
    public void draw() {
        super.draw();
        drawBorder(width);
    }
}
public class DecoratorDemo {
    public static void main(String[] args) {
        TextView view = new TextView();
        BorderDecorator borderDecorator = new BorderDecorator(view, 1);
        borderDecorator.draw();
    }
}
