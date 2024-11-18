package org.avgrlv.lessons;

import org.avgrlv.domain.Person;
import org.avgrlv.domain.Rectangle;
import org.avgrlv.domain.interfaces.Checker;

import java.util.*;
import java.util.stream.Stream;

public class StreamApiLesson {
    public static void main(String[] args) {
        List<Person> people = new java.util.ArrayList<>(List.of(
                new Person("Андрей", 15, "питонист"),
                new Person("Сергей", 22, "программист"),
                new Person("Вася", 45, "сварщик"),
                new Person("Петя", 45, "токарь"),
                new Person("Артём", 26, "программист")));
        System.out.println("---------Practice 1---------");
        practice1(people);
        System.out.println("---------Practice 2---------");
        people.add(new Person("Артём", 26, "препод"));
        practice2(people);
        System.out.println("---------Practice 3---------");
        practice3(people);
        System.out.println("---------Practice 4---------");
        practice4(people);
    }

    private static void practice4(List<Person> people) {
        int oldest = people.stream().mapToInt(Person::getAge).max().getAsInt();
        //Профессии всех людей с максимальным возрастом
        List<String> professions = people.stream()
                .filter(p -> p.getAge() == oldest)
                .map(Person::getProfession)
                .toList();
        System.out.println(professions);
    }

    private static void practice3(List<Person> people) {
        List<String> professions = people.stream().distinct().map(Person::getProfession).sorted().toList();
        System.out.println(professions);
        Optional<Person> older = people.stream().distinct().max(Comparator.comparingInt(Person::getAge));
        older.ifPresent(person -> System.out.println(person.getProfession()));
    }

    private static void practice2(List<Person> people) {
        // Определение equals && hashCode
        people.stream().distinct().forEach(System.out::println);
    }

    private static void practice1(List<Person> people) {
        List<Person> filtered = people.stream()
                .filter(p -> p.getAge() > 18 && !Objects.equals(p.getProfession(), "программист"))
                .toList();

        System.out.println(filtered);

    }

    private static void example2() {
        Stream<Rectangle> rectangleStream = Stream.of(new Rectangle(4,3),
                new Rectangle(5,8),
                new Rectangle(6,9),
                new Rectangle(30,10),
                new Rectangle(8,11));
    }

    private static void example1() {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(2, 5));
        rectangles.add(new Rectangle(10, 53));
        rectangles.add(new Rectangle(2.6, 3.5));
        rectangles.add(new Rectangle(7, 3));
        rectangles.add(new Rectangle(1, 9));
        System.out.println("rectangles: " + rectangles);

        rectangles.stream()
                .filter(r -> r.getPerimeter() <= 30)
                .forEach(s -> System.out.println("r = " + s));
        System.out.println("Меньше 10 по габаритам");
        rectangles.stream()
                .filter(r -> r.getWeight() < 10 && r.getHeight() < 10)
                .forEach(s -> System.out.println("r = " + s));

    }

    private static void example0() {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(2, 5));
        rectangles.add(new Rectangle(10, 53));
        rectangles.add(new Rectangle(2.6, 3.5));
        rectangles.add(new Rectangle(7, 3));
        rectangles.add(new Rectangle(1, 9));
        System.out.println("rectangles: " + rectangles);

        List<Rectangle> smallRectangles = filterRectangles(rectangles, r -> r.getPerimeter() <= 30);
        System.out.println("smallRectangles: " + smallRectangles);

        for (Rectangle rectangle : smallRectangles) {
            System.out.println("rect= " + rectangle);
        }

        List<Rectangle> less10Rectangles = filterRectangles(rectangles, r -> r.getHeight() < 10 && r.getWeight() < 10);
        System.out.println("less10Rectangles: " + less10Rectangles);

        for (Rectangle rectangle : less10Rectangles) {
            System.out.println("rect= " + rectangle);
        }
    }

    private static List<Rectangle> filterRectangles(Collection<Rectangle> rectangles, Checker<Rectangle> checker) {
        List<Rectangle> filteredRectangles = new ArrayList<>();
        for (Rectangle rectangle : rectangles) {
            if (checker.check(rectangle))
                filteredRectangles.add(rectangle);
        }
        return filteredRectangles;
    }
}