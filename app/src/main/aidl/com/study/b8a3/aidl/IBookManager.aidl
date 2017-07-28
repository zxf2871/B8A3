package com.study.b8a3.aidl;
import com.study.b8a3.aidl.Book;
interface IBookManager {
   Book getBook(int i);
   String addBook(in Book book);
}
