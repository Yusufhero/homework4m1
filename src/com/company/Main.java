package com.company;

import java.util.Random;

public class Main {

    private static int bossHealth = 700;
    private static int bossDamage = 50;
    private static String bossDefenceType = "";
    private static int[] heroesHealth = {300, 300, 290, 300, 300};
    private static int[] heroesDamage = {30, 40, 30, 20, 0};
    private static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Archer", "Medic"};
    private static int roundCounter = 1;

    public static void main(String[] args) {
        printStatistics();

        while (isGameOver() != true){
            round();
            roundCounter++;
        }
    }

    public static void printStatistics(){
        System.out.println("--------------------");
        System.out.println("Round: " + roundCounter);
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]);
        }
        System.out.println("--------------------");
    }

    public static void bossHits(){
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0){
                if (heroesHealth[i] - bossDamage < 0){
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void medicHeal(){
        int randomHealth = new Random().nextInt(15);
        if (heroesHealth[4] > 0){
            for (int i = 0; i< heroesAttackType.length - 1; i++) {
                if (heroesHealth[i] <= 100){
                    heroesHealth[i] = heroesHealth[i] + randomHealth;
                    System.out.println("Medic heal: " + heroesAttackType[i] + "by" + randomHealth);
                }
            }
        }
    }


    public static void heroesHits(){
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0){
                if (heroesAttackType[i] == bossDefenceType){
                    heroesDamage[i] = 0;
                    System.out.println(heroesAttackType[i] + " damage equals 0");
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            } else {
                if (bossHealth - heroesDamage[i] < 0){
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            }
        }
    }

    // boolean = true/false
    public static boolean isGameOver(){
        if (bossHealth <= 0){
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0){
                allHeroesDead = false;
                break;
            }
        }

        if (allHeroesDead == true){
            System.out.println("Boss won!!!");
        }

        return allHeroesDead;
    }

    public static void changeDefenceType(){
        int randomIndex = new Random().nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose: " + bossDefenceType);
    }

    public static void round(){
        // Проверяем жив ли босс
        if (bossHealth > 0){
            // Применяем случайное сопротивление
            changeDefenceType();
            // Босс наносит урон
            bossHits();
        }
        // Медик хилит
        medicHeal();

        // Герои наносят урон
        heroesHits();

        // Распечатка статистики на текущий раунд
        printStatistics();
    }

}
