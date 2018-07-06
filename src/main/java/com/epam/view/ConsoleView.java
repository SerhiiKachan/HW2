package com.epam.view;

import com.epam.Printable;
import com.epam.annotations.AuthorInfo;
import com.epam.annotations.BookInfo;
import com.epam.constants.AllConstants;
import com.epam.models.Book;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {

    private Map<String, String> menu;
    private Map<String, Printable> menuMethods;
    private Class<Book> cl;
    private Book book;
    private static Scanner input = new Scanner(System.in);

    public ConsoleView() {
        this.menu = new LinkedHashMap<>();
        this.book = new Book();
        this.menuMethods = new LinkedHashMap<>();
        this.cl = Book.class;
        menu.put("1", "  1 - Task2 & Task3 ---- Get all annotated fields and its values");
        menu.put("2", "  2 - Task4 ---- Invoke 3 methods with different parameters and return types");
        menu.put("3", "  3 - Task5 ---- Set value into field without knowing it`s type");
        menu.put("4", "  4 - Task6 ---- Invoke myMethod(String a, int ... args) and myMethod(String ... args)");
        menu.put("5", "  5 - Task7 ---- Get all information about class");
        menu.put("0", "  0 - exit");
        menuMethods.put("1", this::getAnnotatedFields);
        menuMethods.put("2", this::invokeThreeDifferentMethods);
        menuMethods.put("3", this::setValueIntoFieldWithUnknownType);
        menuMethods.put("4", this::invokeMyMethods);
        menuMethods.put("5", this::getAllInfoAboutClass);
    }

    private void getAnnotatedFields() {
        Field[] fields = cl.getDeclaredFields();
        System.out.println("Annotated fields in the class and annotations' values:");
        for (Field field : fields) {
            if (field.isAnnotationPresent(AuthorInfo.class)) {
                System.out.println(String.format(AllConstants.ANNOTATED_FIELD, field.getName()));
                System.out.println(String.format("Values: name = %s, label = %s",
                        field.getAnnotation(AuthorInfo.class).name(),
                        field.getAnnotation(AuthorInfo.class).label()));
            }
            if (field.isAnnotationPresent(BookInfo.class)) {
                System.out.println(String.format(AllConstants.ANNOTATED_FIELD, field.getName()));
                System.out.println(String.format("Values: bookName: %s ", field.getAnnotation(BookInfo.class).bookName()));
            }
        }
    }

    private void invokeThreeDifferentMethods() {
        try {
            Method setPagesAmount = cl.getDeclaredMethod("setPagesAmount", int.class);
            Method setName = cl.getDeclaredMethod("setName", String.class);
            Method getName = cl.getDeclaredMethod("getName");
            setPagesAmount.invoke(book, 5);
            setName.invoke(book, "1984");
            getName.invoke(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValueIntoFieldWithUnknownType() throws IllegalAccessException {
        Field field = null;
        try {
            field = cl.getDeclaredField("pagesAmount");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        System.out.println("Last pagesAmount value was: " + book.getPagesAmount());
        if (field.getType() == String.class) {
            field.set(book, "200");
        } else if ((field.getType() == Boolean.class) || (field.getType() == boolean.class)) {
            field.set(book, true);
        } else if ((field.getType() == Double.class) || (field.getType() == double.class)) {
            field.set(book, 200.0);
        } else if ((field.getType() == Float.class) || (field.getType() == float.class)) {
            field.set(book, 200f);
        } else if ((field.getType() == Integer.class) || (field.getType() == int.class)) {
            field.set(book, 200);
        } else if ((field.getType() == Character.class) || (field.getType() == char.class)) {
            field.set(book, '2');
        }
        System.out.println("New set pagesAmount value is: " + book.getPagesAmount());
    }

    private void invokeMyMethods() {
        try {
            int[] array = {1, 3, 6, 24};
            Method myMethod2 = cl.getMethod("myMethod", String.class, int[].class);
            myMethod2.invoke(book, "string", array);
            Method myMethod1 = cl.getMethod("myMethod", String[].class);
            myMethod1.invoke(book, (Object) new String[]{"str1", "str2", "str3", "str4", "str5"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllInfoAboutClass() {
        getInfoAboutFields();
        System.out.println(AllConstants.SEPARATION);
        getInfoAboutConstructors();
        System.out.println(AllConstants.SEPARATION);
        getInfoAboutMethods();
    }

    private void getInfoAboutFields() {
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class fieldType = field.getType();
            int i = field.getModifiers();
            String modifier = Modifier.toString(i);
            System.out.println(String.format("field: '%s', type: %s , modifier: %s", field.getName(),fieldType.getSimpleName(), modifier));
        }
    }

    private void getInfoAboutConstructors() {
        Constructor[] constructors = cl.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(String.format("Constructor name: %s", constructor.getName()));
            Parameter[] parameters = constructor.getParameters();
            System.out.println("Parameters of constructor:");
            if (parameters.length == 0) {
                System.out.println("This constructor is without parameters");
            } else {
                for (Parameter parameter : parameters) {
                    System.out.println(parameter.getName() + ": parameter type: " + parameter.getType().getSimpleName());
                }
            }
            System.out.println();
        }
    }

    private void getInfoAboutMethods() {
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            int i = method.getModifiers();
            String modifier = Modifier.toString(i);
            System.out.println(String.format("Method name: %s, return type: %s, modifier: %s", method.getName(), method.getReturnType().getSimpleName(), modifier));
            System.out.println("parameters:");
            Parameter[] parameters = method.getParameters();
            if (parameters.length == 0) {
                System.out.println("This method is without parameters");
            } else {
                for (Parameter parameter : parameters) {
                    System.out.println(String.format("parameter type: %s", parameter.getType().getSimpleName()));
                }
            }
            System.out.println();
        }
    }

    private void openMenu() {
        for (String option : menu.values()) {
            System.out.println(option);
        }
    }

    public void show() {
        String selectedOption;
        do {
            System.out.println("Select option:");
            openMenu();
            selectedOption = input.next();
            try {
                menuMethods.get(selectedOption).print();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } while (!selectedOption.equals("0"));
    }
}

