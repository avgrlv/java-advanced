package org.avgrlv.domain;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Arena implements Callable {
    private String name;

    @Override
    public String toString() {
        return "Арена { " +
                "name='" + name + '\'' +
                ", heroes=" + heroes +
                '}';
    }

    private List<Hero> heroes = new ArrayList<>();

    public Arena(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void putHero(Hero hero) {
        this.heroes.add(hero);
    }

    public void putHeroes(List<Hero> heroes) {
        this.heroes.addAll(heroes);
    }

    public boolean hasWinner() {
        return heroes.stream().filter(Hero::isAlive).count() == 1;
    }

    public Hero getWinner() {
        if (!hasWinner())
            return null;
        Map<Boolean, List<Hero>> winners = heroes.stream()
                .collect(Collectors.partitioningBy(Hero::isAlive));
        return winners.get(true).get(0);
    }

    public void fight() {
        try {
            this.heroes.stream()
                    .filter(Hero::isAlive)
                    .parallel()
                    .forEach(h -> {
                        if (!h.isAlive()) {
                            int i = 1;
                        }
                        Hero target = heroes.stream()
                                .filter(hh -> !Objects.equals(hh.getName(), h.getName()))
                                .findFirst()
                                .get();
                        h.hitting(target);
                    });
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Hero call() throws Exception {
        while (!this.hasWinner()) {
            this.fight();
        }
        return this.getWinner();
    }
}
