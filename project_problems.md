# 29.07.23, 01.08.23
Проблема: неправильная регистрация свайпа по кнопке в MotionLayout

Идеи:
+ ~~Попробовать разные комбинации limitBoundsTo и touchRegionId~~
+ ~~Нужно больше информации. Реализовать программно захваты жестов и посмотреть на лог вывода.~~ Реализовал обработку жестов через GestureDetector, onTouchEvent() в активности и onTouch() в обработчике самой кнопки. Оказалось, что жесты захватываются только через последний, отсюда следующая идея.
+ ~~Реализовать субкласс MotionLayout и переопределить onInterceptTouchEvent(), посмотреть на лог вывода.~~

Решение:
С помощью комбинации методов onInterceptTouchEvent() и onTouchEvent(), переопределённых в MyMotionLayout, я перехватываю событие DOWN и обрабатываю его на уровне viewgroup. Поэтому MotionLayout запускает анимацию.

# 04.08.23
Проблема: после перехвата событий перестала работать кнопка переключения языка.

Идеи:
+ ~~В MyMotionLayout определять, на какой view было осуществлено нажатие.~~

Решение: путём получения и сравнения координат проверяется попадание события в область view и в MotionEvent.ACTION_DOWN возвращается false.

# 14.08.23
Проблема: при возвращении в меню из активности настроек MotionLayout остаётся в конечном состоянии

Идеи:
+ ~~Переходить в base_state либо в onResume(), либо при запуске активности настроек~~

Решение: выбрал переход в onResume(), он лучше выглядит

# 13.09.23
Проблема: при программном добавлении EditText на экран он не отображается

Решение: переставить вызов метода addView() выше и остановиться на одном способе установки процентного
соотношения для высоты etNewAnswer

# 16.09.23
Проблема: при реализации обработки нажатия кнопок на AlertDialog с помощью общего кастомного интерфейса
приложение выдавало ClassCastException на моменте инициализации слушателя в методе onAttach().

Решение: 1. передавать в show() childFragmentManager; 2. заменить инициализацию слушателя контекстом
на инициализацию родительским фрагментом, так как именно он является хостом диалогового окна.

# 18.09.23
Проблема: при добавлении нового ответа на вопрос диалоговое окно о наличии другого правильного ответа перекрывает
окно с вопросом о правильности ответа.

Идеи: как-то дожидаться от пользователя ответа на вопрос о правильности ответа.

Решение: переместить часть с проверкой наличия правильного ответа из обработчика кнопки
в реализацию слушателя диалога.

# 20.09.23
Проблема: ripple-эффект при долгом нажатии на ответ появляется только у textview, но не у его родителя

Идеи:
+ ~~переставить xml-атрибут в родителя~~

Решение: переставить атрибут android:background="?attr/selectableItemBackground" в FrameLayout

# 19.01.24
Проблема: не работает кнопка назад на тулбаре и эмуляторе в настройках

Идеи:
+ Указать в файле манифеста родительские активности

Решение: событие нажатия перехватывал метод OnOptionsItemSelected(), который в любом случае возвращал
true и не пропускал событие в OnSupportNavigateUp(). Надо было возвращать true только в блоке обработки
кнопки с информацией.

# 21.01.24
Проблема: при выходе из приложения не сохраняются добавленные коллекции слов/фраз и т.д.

Решение: переместить хранение коллекций из CollectionStorage в SharedPreferences (или использовать)
CollectionStorage как "надстройку", которая оборачивает использование SharedPreferences для удобства.

# 22.01.24
Проблема: при сохранении коллекции фраз имя не отображается в спиннере

Решение: в методe onResume() класса PhrasesSettingsActivity чистить коллекцию названий и заполнять её
заново.

# 26.01.24
Проблема: текст коллекции фраз не записывается в файл, так как файл не создаётся

Решение: нужно было getFilesDir() заменить на getExternalFilesDir(null), чтобы получить доступ именно
к той директории, которой я хотел.

# 30.01.34
Проблема: приложение падает, когда пытаюсь использовать новый LongClickSpinner

Идеи:
+ Заменить Spinner на LongClickSpinner везде, где он используется

Решение: решил не ломать голову над удалением по долгому нажатию на сам элемент спиннера

# 08.02.24
Проблема: на экранах меньшего размера правая часть интерфейса выходит за границы

Решение: добавить в TextView атрибут app:layout_constrainedWidth="true" в связке с android:layout_width="wrap_content"

Проблема: на Xiaomi Redmi Note 7 не влезают все настройки и текст наслаивается друг на друга

Решение: поменять ограничения на TextView и NumberPicker

# 07.07.24
Проблема: при установке visibility="gone" в комбинации с android:layout_width="0dp",
android:layout_height="0dp" и app:layout_constraintDimensionRatio="1:1" размер остальных ImageButton
подстраивается и отличается от предыдущего ряда, который заполнился кнопками меньшего размера. Т. о.
достигается цель красивого выравнивания кнопок по центру, но не достигается равный размер

Идеи:
+ ~~Устанавливать разный app:layout_constraintHeight_max в зависимости от экрана~~
+ ~~Указать ширину кнопки в процентах~~

Решение: пока не протестировано полностью, но добавление app:layout_constraintWidth_percent="0.145"
выглядит хорошо

# 08.07.24
Проблема: не триггерится onTouchListener у CardView

Решение: перевесил его на ImageButton, который находится внутри CardView

# 10.07.24
Проблема: при использовании вертикальных и горизонтальных цепей для табличного отображения одни
элементы выталкивают другие наверх.

Решение: Сдвинул верхнюю guideline на 0.15

Проблема: При отображении на планшете рисунки фигур не скейлятся под большую кнопку

Решение: установить android:scaleType="fitCenter" и android:padding="@dimen/s_training_set_padding"

# 30.09.24
Проблема: в WordsTrainingActivity первый setContentView не отображает R.layout.pretrain_sequence_layout.
Если поменять их местами, ничего не меняется

Решение: просто внимательнее посмотреть в конец onCreate(), куда я в самом начале скопировал старый
setContentView().

# 07.10.24
Проблема: после смены фраз местами нулевая тень устанавливается только на кнопке, выбранной первой

Решение: поменять логику выделения выбранной фразы на изменение цвета