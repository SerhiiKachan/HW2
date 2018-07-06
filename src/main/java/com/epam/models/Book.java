package com.epam.models;

import com.epam.annotations.AuthorInfo;
import com.epam.annotations.BookInfo;

public class Book<T> {

    private T object;
    @AuthorInfo(name = "George Orwell", label = "AuthorName")
    private String name;
    private int pagesAmount;
    @BookInfo(bookName = "1984")
    private Author author;

    public Book() {
    }

    public Book(T object, String name, int pagesAmount, Author author) {
        this.object = object;
        this.name = name;
        this.pagesAmount = pagesAmount;
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getName() {
        System.out.println("getName() has been invoked");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName() has been invoked, " + "bookName: " + name);
        this.name = name;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public void setPagesAmount(int pagesAmount) {
        System.out.println("setPagesAmount() has been invoked, " + "pages: " + pagesAmount);
        this.pagesAmount = pagesAmount;
    }

    public void myMethod(String line, int... args) {
        System.out.println("myMethod(String line, int ... args) has been invoked and it takes this parameters:");
        System.out.println(line);
        for (int number : args) {
            System.out.println(number);
        }
    }

    public void myMethod(String... args) {
        System.out.println("myMethod(String ... args) has been invoked and it takes these strings:");
        for (String string : args) {
            System.out.println(string);
        }
    }
}

