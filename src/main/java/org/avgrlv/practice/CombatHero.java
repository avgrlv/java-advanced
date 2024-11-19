package org.avgrlv.practice;

import org.avgrlv.domain.Arena;
import org.avgrlv.domain.Hero;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CombatHero {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Hero> heroes = List.of(
                new Hero("Warrior", 13, 3),
                new Hero("Mage", 17, 0),
                new Hero("Warlock", 9, 6),
                new Hero("Priest", 6, 17)
        );

        Arena arena1 = new Arena("Центральная");
//        arena1.putHeroes(heroes);
        arena1.putHero(heroes.get(0));
        arena1.putHero(heroes.get(3));

        Arena arena2 = new Arena("Тёмная");
//        arena2.putHeroes(heroes);
        arena2.putHero(heroes.get(1));
        arena2.putHero(heroes.get(2));


        ExecutorService executorService = Executors.newFixedThreadPool(heroes.size());
        Future<Hero> winner1 = executorService.submit(arena1::call);
        Future<Hero> winner2 = executorService.submit(arena2::call);

        Thread.sleep(10000);
        if (winner1.isDone()) {
            System.out.println("Победитель арены {%s} становится %s".formatted(arena1.getName(), winner1.get()));
            System.out.println("Состояние на арене {%s}: %s".formatted(arena2.getName(), arena2.toString()));
        }
        if (winner2.isDone()) {
            System.out.println("Победитель арены {%s} становится %s".formatted(arena2.getName(), winner2.get()));
            System.out.println("Состояние на арене {%s}: %s".formatted(arena1.getName(), arena1.toString()));

        }
        executorService.shutdown();
    }
}
