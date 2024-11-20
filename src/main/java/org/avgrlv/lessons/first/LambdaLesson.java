package org.avgrlv.lessons.first;

import org.avgrlv.domain.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LambdaLesson {
    public static void main(String[] args) {
        example1();
        example2();
        example3();
    }

    private static void example3() {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(2, 5));
        rectangles.add(new Rectangle(10, 53));
        rectangles.add(new Rectangle(2.6, 3.5));
        rectangles.add(new Rectangle(7, 3));
        rectangles.add(new Rectangle(1, 9));
        System.out.println("rectangles: " + rectangles);
        rectangles.sort((r1, r2) -> {
            if (r1.getWeight() > r2.getWeight())
                return 1;
            if (r1.getWeight() < r2.getWeight()) return -1;
            return 0;
        });
        System.out.println("rectangles sorts by weight: " + rectangles);
        rectangles.sort(Comparator.comparingDouble(Rectangle::getHeight));
        System.out.println("rectangles sorts by height: " + rectangles);
        rectangles.sort(Comparator.comparingDouble(r -> r.getHeight() * r.getWeight()));
        System.out.println("rectangles sorts by area: " + rectangles);

        // Rectangle::getPerimeter ссылка на метод
        rectangles.sort(Comparator.comparingDouble(Rectangle::getPerimeter));
        System.out.println("rectangles sorts by perimeter: " + rectangles);
        rectangles.sort(Comparator.comparingDouble(Rectangle::getPerimeter).reversed());
        System.out.println("rectangles sorts by area (desc): " + rectangles);

        // отсортирвоать по строковому представлению
        rectangles.sort(Comparator.comparing(Rectangle::toString));
        System.out.println("rectangles sorts by string: " + rectangles);

    }

    private static void example2() {
        double a = -12.3, b = 16.3;
        printOperation(a, b, Double::sum);
        printOperation(a, b, (x, y) -> x - y);
        printOperation(a, b, Math::pow);
    }

    private static void example1() {
        doSomething(new ExA(101));
        doSomething(new ExA(202));

        // <--
        doSomething(new Executable() {
            @Override
            public void execute() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Hi " + i);
                }
            }

            @Override
            public String toString() {
                return "Object anonymous class Executable {}";
            }
        });
        // --> это всё можно в лямбду обернуть

        doSomething(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.print("Bye " + i);
            }

            System.out.println("*");
        });
    }

    public static void doSomething(Executable ex) {
        System.out.println("do something");
        System.out.println(ex);
        ex.execute();
    }

    public static void printOperation(double a, double b, ICalculator calc) {
        System.out.println("--------------------------------------------");
        System.out.println("a: " + a + "\nb: " + b);
        System.out.println("Result: " + calc.calculate(a, b));
    }
}


/**
 * Интерфейс с 1 методом называется **функциональным**
 */
interface Executable {
    void execute();
}

class ExA implements Executable {
    int id;

    public ExA(int id) {
        this.id = id;
    }

    @Override
    public void execute() {
        System.out.println("executing {ExA} id = " + id);
    }

    @Override
    public String toString() {
        return "ExA{" +
                "id=" + id +
                '}';
    }
}

interface ICalculator {
    double calculate(double a, double b);
}