package com.youngsophomore.interfaces;
/*
* изначально этот класс создавался для обработки долгого нажатия на один из ответов и удаление его
* из коллекции. Идея была в том, чтобы использовать этот интерфейс и для обработки долгого
* нажатия на элемент коллекции ответов.
* */
public interface RecyclerViewClickListener {
    void onItemLongClick(int position);
}
