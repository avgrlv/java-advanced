package org.avgrlv.domain;

import java.util.Objects;

public class Rectangle {
    private double weight, height;

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public double getArea() {
        return weight * height;
    }

    public double getPerimeter() {
        return 2 * weight + 2 * height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(this.weight, rectangle.weight) == 0
                && Double.compare(height, rectangle.height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, height);
    }

    @Override
    public String toString() {
        return "Rectangle [weight=" + weight + ", height=" + height + "]";
    }
}
