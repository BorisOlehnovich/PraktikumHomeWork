import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StepTracker stepTracker = new StepTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int menu = scanner.nextInt();

            if (menu == 0) {
                break;
            } else if (menu == 1) {
                System.out.println("Выберите месяц от 1 до 12:");
                int month = scanner.nextInt();
                System.out.println("выберите день от 1-30:");
                int day = scanner.nextInt();
                System.out.println("Введите пройденное количество шагов:");
                int steps = scanner.nextInt();
                if (steps > 0) {
                    stepTracker.addSteps(month - 1, day - 1, steps);
                }
            } else if (menu == 2) {
                System.out.println("Выберите номер месяца, статистика за который вас интересует (1-12).");
                int month = scanner.nextInt();
                stepTracker.printStepsInMonth(month - 1);
                stepTracker.printAllMaxAverageStepsInMonth(month - 1);

            } else if (menu == 3) {
                System.out.println("Введите новую цель по шагам в течении дня:");
                int newInput = scanner.nextInt();
                if (newInput > 0) {
                    stepTracker.setStepsPerDay(newInput);
                    System.out.println("Теперь ваша цель " + stepTracker.getStepsPerDay() + " шагов в день");
                } else {
                    System.out.println("Введите число больше 0");
                }
            } else {
                System.out.println("Введите корректный номер из предложенных!");
            }
        }
        System.out.println("Программа завершена.");
    }

    private static void printMenu() {
        System.out.println("1 - Ввести количество шагов за определенный день.");
        System.out.println("2 - Напечатать статистику за определенный месяц.");
        System.out.println("3 - Изменить цель по количеству шагов в день.");
        System.out.println("0 - Выйти из приложения");
    }
}
