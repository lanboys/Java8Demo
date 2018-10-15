package com.bing.lan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by 蓝兵 on 2018/10/15.
 */

public class Test {

    public static void main(String[] args) {

    }

    private static void test2() {
        Student stuA = new Student(1, "A", "M", 184);
        Student stuB = new Student(2, "B", "G", 163);
        Student stuC = new Student(3, "C", "M", 175);
        Student stuD = new Student(4, "D", "G", 158);
        Student stuE = new Student(5, "E", "M", 170);
        List<Student> list = new ArrayList<>();
        list.add(stuA);
        list.add(stuB);
        list.add(stuC);
        list.add(stuD);
        list.add(stuE);

        Iterator<Student> iterator = list.iterator();
        while (iterator.hasNext()) {
            Student stu = iterator.next();
            if (stu.getSex().equals("G")) {
                System.out.println(stu.toString());
            }
        }

        list.stream()
                .filter(student -> student.getSex().equals("G"))
                .forEach(student -> System.out.println(student.toString()));

        list.stream().filter(new Predicate<Student>() {
            @Override
            public boolean test(Student student) {
                return student.getSex().equals("G");
            }
        }).forEach(new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                System.out.println(student.toString() + "--");
            }
        });
    }

    private static void test1() {
        // 1. Individual values
        Stream<String> stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        Collector<String, ?, List<String>> toList = Collectors.toList();
        List<String> list1 = stream.collect(toList);
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();
    }
}
