import java.util.ArrayDeque;
import java.util.Deque;

public class CollectionApp {
    public static void main(String[] args) {

        Deque<String> states = new ArrayDeque<>();

        // стандартное добавление элементов
        states.add("Германия");
        states.add("Франция");
        // добавляем элемент в самое начало
        states.push("Великобритания");

        // получаем первый элемент без удаления
        String sFirst = states.getFirst();
        String sLast = states.getLast();
        System.out.println("sFirst " + sFirst);
        System.out.println("sLast " + sLast);

        while (states.peek() != null) {
        // извлечение c начала
            System.out.println(states.pop());
        }
        System.out.printf("Размер очереди: %d \n", states.size());

        ArrayDeque<Person> people = new ArrayDeque<>();
        people.addFirst(new Person("Tom"));
        people.addLast(new Person("Nick"));
        for (Person p : people) {
            System.out.println(p.getName());
        }
    }
}

class Person {
    private String name;
    public Person(String value) {
        name = value;
    }
    String getName() {
        return name;
    }
}