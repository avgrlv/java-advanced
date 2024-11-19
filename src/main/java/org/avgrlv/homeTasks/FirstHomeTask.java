package org.avgrlv.homeTasks;

import org.avgrlv.domain.Planet;
import org.avgrlv.domain.Rectangle;
import org.avgrlv.domain.Satellite;
import org.avgrlv.domain.SpaceObject;

import java.util.*;

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
        List<Planet> planets = Arrays.asList(
                new Planet("Меркурий", 3.302e23, List.of()),
                new Planet("Венера", 4.867e24, List.of()),
                new Planet("Земля", 5.972e24, List.of(
                        new Satellite("Луна", 7.348e22, "серый")
                )),
                new Planet("Марс", 6.417e23, List.of(
                        new Satellite("Фобос", 1.072e16, "красный"),
                        new Satellite("Деймос", 1.476e15, "красный")
                )),
                new Planet("Юпитер", 1.899e27, List.of(
                        new Satellite("Ио", 8.94e22, "желтый"),
                        new Satellite("Европа", 4.8e22, "белый"),
                        new Satellite("Ганимед", 1.482e23, "белый"),
                        new Satellite("Каллисто", 1.076e23, "коричневый")
                )),
                new Planet("Сатурн", 5.685e26, List.of(
                        new Satellite("Титан", 1.345e23, "оранжевый"),
                        new Satellite("Рея", 2.307e21, "серый"),
                        new Satellite("Япет", 1.806e21, "черно-белый")
                )),
                new Planet("Уран", 8.683e25, List.of(
                        new Satellite("Титания", 3.53e21, "серый"),
                        new Satellite("Оберон", 3.02e21, "серый"),
                        new Satellite("Умбриэль", 1.17e21, "серый"),
                        new Satellite("Ариэль", 1.35e21, "серый")
                )),
                new Planet("Нептун", 1.024e26, List.of(
                        new Satellite("Тритон", 2.14e22, "розовый"),
                        new Satellite("Протей", 5.0e21, "серый")
                ))
        );

        System.out.println("planetList = " + planets);
        System.out.println("Список планет: ");
        planets.forEach(System.out::println);

//        System.out.println("Список планет по убыванию массы:");
//        planets.stream()
//                .sorted(Comparator.comparingDouble(Planet::getMass).reversed())
//                .forEach(System.out::println);
//
//        System.out.println("Планеты по возрастанию сумманрной массы:");
//        planets.stream()
//                .sorted(Comparator.comparingDouble(p -> p.getSatellites().stream().mapToDouble(SpaceObject::getMass).sum()))
//                .map(SpaceObject::getName)
//                .forEach(System.out::println);
//
//        System.out.println("Извлечь все спутники и отсортирвоать по массе");
//        planets.stream().flatMap(x -> x.getSatellites().stream())
//                .sorted(Comparator.comparingDouble(SpaceObject::getMass).reversed())
//                .forEach(System.out::println);

        System.out.println("Вывод названий планет в параллельном режиме:");
//        planets.parallelStream().forEach(p -> System.out.println(p.getName()));

        planets.parallelStream()
                .peek(x -> System.out.println("параллельно посещаем: " + x.getName()))
                .forEach(x -> System.out.println("терминальное посещение: " + x.getName()));

        System.out.println("=========собераем в один поток ========");
        planets.parallelStream()
                .peek(x -> System.out.println("параллельно посещаем: " + x.getName()))
                .forEachOrdered(x -> System.out.println("терминальное посещение: " + x.getName()));

        System.out.println("-----------------------------------------------------------");
        planets.stream()
                .sorted(Comparator.comparingDouble(Planet::getMass).reversed())
                .parallel() //превращаем в паралелльный стрим
                .peek(x -> System.out.println("параллельно посещаем: " + x.getName()))
                .forEachOrdered(x -> System.out.println("терминальное посещение: " + x.getName()));


        System.out.println("-----------------------------------------------------------");
        System.out.println("---------сортировка в || stream НЕВОЗМОЖНА-----------------");
        System.out.println("-----------------------------------------------------------");
        // сортировка должна происходить до распараллеливания
        planets.stream()
                .parallel()
                .sorted(Comparator.comparing(Planet::getName))
                .forEach(x -> System.out.println(" " + x.getName()));
        System.out.println("Паралелльная сортировка через Arrays.parallelSort()");
        //но можно сделать так || сортировку
        Planet[] array = planets.toArray(new Planet[0]);
        Arrays.parallelSort(array,
                Comparator.comparing(SpaceObject::getMass));
        Arrays.stream(array).forEach(System.out::println);

    }
}
