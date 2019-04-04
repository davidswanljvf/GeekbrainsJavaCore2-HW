package ru.geekbrains.homework1;

import ru.geekbrains.homework1.animal.Cat;
import ru.geekbrains.homework1.course.Course;
import ru.geekbrains.homework1.course.Cross;
import ru.geekbrains.homework1.course.Wall;
import ru.geekbrains.homework1.course.Water;
import ru.geekbrains.homework1.animal.Dog;
import ru.geekbrains.homework1.animal.Human;
import ru.geekbrains.homework1.animal.Robot;
import ru.geekbrains.homework1.enums.Color;

/**
 * Класс для запуска приложения - симулятор кросса
 */
public class Application {

    public static void main(String[] args) {
        Team team = new Team(
                new Cat("Барсик", Color.BLACK, 1, 100, 5),
                new Cat("Барсик", Color.BLACK, 1, 80, 7),
                new Dog("Бобик", Color.BROWN, 2, 90, 8, 18),
                new Human("Олег",Color.WHITE,23,100,3,100),
                new Robot("Bot",Color.RED,23,100,3)

        );

        Course course = new Course(
                new Cross(50),
                new Wall(2),
                new Cross(90),
                new Water(19)
        );

        course.doIt(team);
    }
}
