import java.util.HashMap;

public class StepTracker {
    private int stepsPerDay;
    private HashMap<Integer,MonthData> monthToData;


    public StepTracker() {
        stepsPerDay = 10000;
        monthToData = new HashMap<>();

        for (int i = 0; i < 12; i++) {
            monthToData.put(i, new MonthData());
        }
    }

    class MonthData {
        HashMap<Integer, Integer> dates = new HashMap<>();

        public MonthData() {
            for (int i = 0; i < 30; i++) {
                dates.put(i, 0);
            }
        }
    }

    public void addSteps (int month, int day, int steps) {
        monthToData.get(month).dates.put(day, steps);
    }

    public void printStepsInMonth (int month){
        MonthData monthData = monthToData.get(month);
        for (int i = 0; i < 30; i++) {
            System.out.print((i+1) + " день: " + monthData.dates.get(i) + ", ");
        }
        System.out.println();
    }

    public void printAllMaxAverageStepsInMonth (int month) {
        MonthData monthData = monthToData.get(month);
        int stepsCount = 0;
        int max = 0;
        int maxSeries = 0;
        int currentMaxSeries = 0;
        for (int i = 0; i < 30; i++) {
            stepsCount += monthData.dates.get(i);
            if (monthData.dates.get(i) > max) {
                max = monthData.dates.get(i);
            }
            if (monthData.dates.get(i) >= stepsPerDay) {
                maxSeries++;
            } else {
                if (currentMaxSeries < maxSeries) {
                    currentMaxSeries = maxSeries;
                }
                maxSeries = 0;
            }
        }
        System.out.println("Общее количество пройденных шагов в этом месяце = " + stepsCount);
        System.out.println("Cреднее количество шагов в день в этом месяце = " + (stepsCount/30));
        System.out.println("Максимальное количество пройденных шагов = " + max);
        System.out.println("Пройденное количество километров за месяц = " + Converter.convertToKilometer(stepsCount));
        System.out.println("Количество затраченных каллорий за месяц = " + Converter.convertToCallory(stepsCount));
        System.out.println("Самая длинная серия из пройденных шагов за день выше целевого значения = " + currentMaxSeries);
    }

    public int getStepsPerDay() {
        return stepsPerDay;
    }

    public void setStepsPerDay(int stepsPerDay) {
        this.stepsPerDay = stepsPerDay;
    }

    public HashMap<Integer, MonthData> getMonthToData() {
        return monthToData;
    }

}
