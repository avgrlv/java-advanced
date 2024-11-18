package org.avgrlv.homeTasks;

import org.avgrlv.domain.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FirstHomeTask {
    public static void main(String[] args) {
        System.out.println("-----------Task 1----------");
        task1();
        System.out.println("-----------Task 2----------");
        task2();
    }

    public static void task1() {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(2, 5));
        rectangles.add(new Rectangle(10, 53));
        rectangles.add(new Rectangle(2.6, 3.5));
        rectangles.add(new Rectangle(7, 3));
        rectangles.add(new Rectangle(7, 3));
        rectangles.add(new Rectangle(3, 7));
        rectangles.add(new Rectangle(3, 7));
        rectangles.add(new Rectangle(1, 9));
        rectangles.add(new Rectangle(1, 9));
        System.out.println("rectangles: " + rectangles);
        System.out.println("Кол-во прямоульников: " + rectangles.size());
        long uniqueAmount = rectangles.stream().distinct().count();
        System.out.printf("Количество уникальных прямоугольников: %s%n", uniqueAmount);
        double summaryArea = Optional.ofNullable(rectangles.stream().map(Rectangle::getArea).reduce(Double::sum))
                .map(Optional::get)
                .orElse(0.0);
        System.out.printf("Суммарная площадь всех прямоугольников: %s%n", summaryArea);
    }

    private static void task2() {

    }
}
