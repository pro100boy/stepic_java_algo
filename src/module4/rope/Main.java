package module4.rope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class RopeString{
    private boolean _isLeaf; // Является ли узел листом
    private String _value; // Строка, хранимая в узле
    private RopeString _leftChild; // Левый сын
    private RopeString _rightChild; // Правый сын
    private int _offset = 0; // Смещение относительно начала строки
    private int _length; // Длина подстроки

    public RopeString()
    {
        this("");
    }

    public RopeString(String value)
    {
        _value = value;
        _isLeaf = true;
        _length = value.length();
    }

    private RopeString(RopeString left, RopeString right)
    {
        _leftChild = left;
        _rightChild = right;
        _isLeaf = false;
        _length = GetLength(left) + GetLength(right);
    }
    // Возвращает результат в узле
    public String getValue() {
        return _value;
    }

    // Возвращает длину результата
    public int getLength() {
        return _length;
    }

    // Вспомогательная функция для получения результата, избавляющая от проверки на null
    private static String GetValue(RopeString rope)
    {
        return rope == null ? "" : rope.getValue();
    }

    // Вспомогательная функция для получения длины, избавляющая от проверки на null
    private static int GetLength(RopeString rope)
    {
        return rope == null ? 0 : rope.getLength();
    }
}


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String string = "abcdef";// reader.readLine();
        /*int cnt = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cnt; i++) {
            int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
            char[] string2 = new char[string.length];
            System.arraycopy(string, 0, string2, 0, tmp[0]);
            Arrays.asList(string2).stream().forEach(System.out::println);
        }*/
        StringBuilder stringBuilder = new StringBuilder(string);
        String substr = stringBuilder.substring(0, 2);
        stringBuilder.insert(3, substr);
        stringBuilder.delete(0, 2);
        //stringBuilder.replace(1, 1, substr);
        System.out.println(stringBuilder);

    }
}
