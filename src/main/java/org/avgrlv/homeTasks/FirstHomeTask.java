package org.avgrlv.homeTasks;

import org.avgrlv.domain.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class FirstHomeTask {
    public static void main(String[] args) {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(2, 5));
        rectangles.add(new Rectangle(10, 53));
        rectangles.add(new Rectangle(2.6, 3.5));
        rectangles.add(new Rectangle(7, 3));
        rectangles.add(new Rectangle(1, 9));
        System.out.println("rectangles: " + rectangles);
    }
}
