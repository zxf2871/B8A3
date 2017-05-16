package com.study.b8a3.aidl;
import com.study.b8a3.aidl.Book;
interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
}
