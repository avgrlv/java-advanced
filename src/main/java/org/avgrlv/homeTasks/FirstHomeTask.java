package org.avgrlv.homeTasks;

import org.avgrlv.domain.Planet;
import org.avgrlv.domain.Rectangle;
import org.avgrlv.domain.Satellite;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FirstHomeTask {
    public static void main(String[] args) {
        System.out.println("-----------Task 1----------");
//        task1();
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

        double totalArea = rectangles.stream().mapToDouble(Rectangle::getArea).sum();

        System.out.printf("Суммарная площадь всех прямоугольников: %s%n", summaryArea);
    }

    private static void task2() {
        List<Planet> planets = new ArrayList<>();
        planets.add(new Planet("земля", 24421.3, List.of(new Satellite("луна", 5544, "Белый"))));
        planets.add(new Planet("Юпитер", 905000));
        planets.add(new Planet("марс", 50324));
        planets.add(new Planet("Венера", 532534));
        planets.add(new Planet("Сатурн", 55000));
        planets.add(new Planet("Плутон", 23000));

        System.out.println("planetList = " + planets);
        System.out.println("Список планет: ");
        planets.stream().forEach(System.out::println);
        System.out.println("список планет по убыванию массы:");
        planets.stream().sorted(Comparator.comparingDouble(Planet::getMass).reversed())
                .forEach(System.out::println);
    }
}
