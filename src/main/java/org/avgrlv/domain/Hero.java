package org.avgrlv.domain;

public class Hero {
    private String name;
    private int health;
    private int damage;
    private int healingPower;
    private boolean isAlive = true;

    public Hero(String name, int power, int healingPower) {
        this.name = name;
        this.health = 100;
        this.damage = power;
        this.healingPower = healingPower;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return this.health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void attack(int damage) {
        if (this.health > 0) {
            String msg = this.name + " получает урон в размере "
                    + damage + " Кол-во здоровья: " + this.health;
            if (this.health - damage < 0) {
                msg = this.name + " получает КРИТИЧЕСКИЙ урон в размере " + damage;
            }
            this.health -= damage;
            System.out.println(msg);
        }

        if (this.health <= 0) {
            this.isAlive = false;
            System.out.println("Ваш герой " + this.name + " погиб!");
        }
    }

    public void healing() {
        if (this.isAlive && this.health < 100) {
            System.out.println("Герой " + this.name + " исцелился " + "Кол-во здоровья: " + this.health);
            this.health += healingPower;
        } else
            System.out.println("Герой " + this.name + " мёртв. Нельзя исцелить цель");
    }

    @Override
    public String toString() {
        return "Герой { Имя = " + name + '\'' +
                ", кол-во жизни =" + health +
                '}';
    }

    public void hitting(Hero target) {
        System.out.println("Герой " + this.name + " пытается нанести урон по " + target.getName());
        if (Math.random() <= 0.1)
            target.healing();
        else
            target.attack(this.damage);

    }
}
