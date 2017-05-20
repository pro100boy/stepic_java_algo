// C# console realization
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleRopeString
{
    class RopeString
    {
        #region Private members

        private bool _isLeaf; // Является ли узел листом
        private string _value; // Строка, хранимая в узле
        private RopeString _leftChild; // Левый сын
        private RopeString _rightChild; // Правый сын
        private int _offset = 0; // Смещение относительно начала строки
        private int _length; // Длина подстроки
        public int _posToInsert = 0; // Позиция для вставки

        #endregion

        #region Ctr

        public RopeString()
            : this("")
        {
        }

        public RopeString(string value)
        {
            _value = value;
            _isLeaf = true;
            _length = value.Length;
        }

        private RopeString(RopeString left, RopeString right)
        {
            _leftChild = left;
            _rightChild = right;
            _isLeaf = false;
            _length = GetLength(left) + GetLength(right);
        }

        #endregion

        #region Public fields

        // Возвращает результат в узле
        public string Value
        {
            get { return ToString(); }
        }

        // Возвращает длину результата
        public int Length
        {
            get { return _length; }
        }

        #endregion

        #region Private methods

        // Вспомогательная функция для получения результата, избавляющая от проверки на null
        private static string GetValue(RopeString rope)
        {
            return rope == null ? "" : rope.Value;
        }

        // Вспомогательная функция для получения длины, избавляющая от проверки на null
        private static int GetLength(RopeString rope)
        {
            return rope == null ? 0 : rope.Length;
        }

        /// <summary>Разбивает строку на две части </summary>
        private void Split(int leftLength, out RopeString left, out RopeString right)
        {
            leftLength = Math.Min(leftLength, this.Length);
            // Тривиальные случаи
            if (leftLength == 0)
            {
                left = new RopeString();
                right = this;
            }
            else if (leftLength >= Length)
            {
                left = this;
                right = new RopeString();
            }
            else
            {
                if (_isLeaf) // Базовый случай - лист
                {
                    left = new RopeString(this.Value);
                    left._length = leftLength;
                    right = new RopeString(this.Value);
                    right._offset = leftLength;
                    right._length = this.Length - leftLength;
                }
                else
                {
                    // Определяем, в каком сыне находится граница разбиения
                    if (leftLength <= GetLength(_leftChild))
                    {
                        RopeString leftLeft, leftRight;
                        _leftChild.Split(leftLength, out leftLeft, out leftRight);
                        left = leftLeft;
                        right = leftRight + _rightChild;
                    }
                    else
                    {
                        RopeString rightLeft, rightRight;
                        _rightChild.Split(leftLength - GetLength(_leftChild), out rightLeft, out rightRight);
                        left = _leftChild + rightLeft;
                        right = rightRight;
                    }
                }
            }
        }

        #endregion

        #region Public methods

        // Приведение к обычной строке
        public override string ToString()
        {
            return _isLeaf ? _value.Substring(_offset, _length) : GetValue(_leftChild) + GetValue(_rightChild);
        }

        /// <summary>Конкатенация строк</summary>
        public static RopeString operator +(RopeString lhs, RopeString rhs)
        {
            return new RopeString(lhs, rhs);
        }

        /// <summary>Обращение к отдельным символам строки</summary>
        public char this[int index]
        {
            get
            {
                if (index < 0 || index >= Length)
                    throw new IndexOutOfRangeException("Индекс находился вне границ строки");
                if (!_isLeaf)
                {
                    return index < GetLength(_leftChild)
                        ? _leftChild[index]
                        : _rightChild[index - GetLength(_leftChild)];
                }
                else
                    return _value[index];
            }
        }

        /// <summary>Выделение подстроки от startIndex до конца строки</summary>
        public RopeString Substring(int startIndex)
        {
            return Substring(startIndex, Length - startIndex);
        }

        /// <summary>Выделение подстроки, начиная со startIndex длины length</summary>
        public RopeString Substring(int startIndex, int length)
        {
            RopeString left, mid, right;
            this.Split(startIndex, out left, out right);
            right.Split(length, out mid, out right);
            return mid;
        }

        /// <summary>Удаление из строки подстроки от startIndex до конца</summary>
        public RopeString Erase(int startIndex)
        {
            return Erase(startIndex, Length - startIndex);
        }


        /// <summary>Удаление из строки подстроки от startIndex длины length</summary>
        public RopeString Erase(int startIndex, int length)
        {
            RopeString left, mid, right;
            this.Split(startIndex, out left, out right);
            right.Split(length, out mid, out right);
            return left + right;
        }

        /// <summary>Вставляет переданную строку в нужную позицию</summary>
        public RopeString Insert(int position, RopeString rope)
        {
            RopeString left, right;
            this.Split(position, out left, out right);
            return left + rope + right;
        }

        #endregion

    }

    public class MainClass
    {
        private static char[] sep = new char[] { ' ' };
        static void Main(string[] args)
        {
            string initialString = Console.ReadLine();
            RopeString rope = new RopeString(initialString);
            int linesCnt = int.Parse(Console.ReadLine());

            for (int i = 0; i < linesCnt; i++)
            {
                string[] tmp = Console.ReadLine().Split(sep);
                int from = int.Parse(tmp[0]);
                int to = int.Parse(tmp[1]);
                int pos = int.Parse(tmp[2]);

                RopeString fragment = rope.Substring(from, to - from + 1);
                RopeString cuttedStr = rope.Erase(from, to - from + 1);

                if (pos == 0)
                {
                    rope = fragment + cuttedStr;
                }
                else if (pos == cuttedStr.Length)
                {
                    rope = cuttedStr + fragment;
                }
                else {
                    // вставить после k-го символа оставшейся строки
                    // оставшаяся строка = cuttedStr
                    rope = cuttedStr.Insert(pos, fragment);
                }

               //Console.WriteLine(rope.Value);
            }


            Console.WriteLine(rope.Value);
            //Console.ReadLine();
        }
    }
}
