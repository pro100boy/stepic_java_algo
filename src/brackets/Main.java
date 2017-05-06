package brackets;

import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

    public static void main(String[] args) {

        String s = "{{[()";//new Scanner(System.in).nextLine();
        int strLen = s.length();

        Deque<String> stackOpen = new ArrayDeque<>();
        Deque<String> stackClose = new ArrayDeque<>();

        boolean isFailure = false;

        for (int i = 0; i < strLen; i++) {
            char ch = s.charAt(i);

            // загоняем символ со своим индексом
            if (ch == '[' || ch == '(' || ch == '{') {
                String str = ch + ">" + (i + 1);
                stackOpen.push(str);
            } else {
                // если попался любой символ кроме закрывающей скобки (открывающая уже обработана выше)
                if (ch != ']' && ch != ')' && ch != '}') continue;

                // первой встретилась закрывающая скобка
                String str = ch + ">" + (i + 1);
                stackClose.push(str);

                if (stackOpen.isEmpty()) {
                    isFailure = true;
                    break;
                }

                char top = stackOpen.pop().split(">")[0].charAt(0);
                if ((top == '[' && ch != ']') || (top == '(' && ch != ')') || (top == '{' && ch != '}')) {
                    isFailure = true;
                    break;
                } else {stackClose.pop();}
            }
        }

        if (!isFailure && stackOpen.isEmpty())
            System.out.println("Success");
        else if (stackClose.peekFirst() != null) System.out.println(stackClose.peekFirst().split(">")[1]);
        else if (stackOpen.peekFirst() != null) System.out.println(stackOpen.peekFirst().split(">")[1]);
    }
}
