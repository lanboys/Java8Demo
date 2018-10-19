package com.bing.lan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 蓝兵 on 2018/10/16.
 */

public class CollectorsTest {

    public static void groupingByTest() {
        Map<Boolean, List<Integer>> collectGroup = Stream.of(1, 2, 3, 4)
                .collect(Collectors.groupingBy(it -> it > 3));
        System.out.println("collectGroup : " + collectGroup);
        // 打印结果
        // collectGroup : {false=[1, 2, 3], true=[4]}

        Student stuA = new Student(1, "A", "M", 184);
        Student stuB = new Student(2, "B", "G", 163);
        Student stuC = new Student(3, "B", "M", 175);
        Student stuD = new Student(4, "D", "G", 158);
        Student stuE = new Student(5, "A", "M", 170);
        List<Student> list = new ArrayList<>();
        list.add(stuA);
        list.add(stuB);
        list.add(stuC);
        list.add(stuD);
        list.add(stuE);

        Map<String, List<Student>> collect = list.stream().collect(Collectors.groupingBy(o -> o.getName()));
        System.out.println("collect : " + collectGroup);

    }

    public static void collectorTest() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4);

        Collector<Integer, ?, List<Integer>> collector = Collectors.toList();

        List<Integer> collectList = integerStream.collect(collector);

        System.out.println("collectList: " + collectList);
        // 打印结果
        // collectList: [1, 2, 3, 4]

        Set<Integer> collectSet = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toSet());
        System.out.println("collectSet: " + collectSet);
        // 打印结果
        // collectSet: [1, 2, 3, 4]

        Optional<Integer> collectMaxBy = Stream.of(1, 2, 3, 4)
                .collect(Collectors.maxBy(Comparator.comparingInt(o -> o)));
        System.out.println("collectMaxBy:" + collectMaxBy.get());
        // 打印结果
        // collectMaxBy:4

        Predicate<Integer> predicate = it -> it % 2 == 0;
        Collector<Integer, ?, Map<Boolean, List<Integer>>> mapCollector = Collectors.partitioningBy(predicate);

        Map<Boolean, List<Integer>> collectPartitioningBy = Stream.of(1, 2, 3, 4)
                .collect(mapCollector);

        System.out.println("collectPartitioningBy : " + collectPartitioningBy);
        // 打印结果
        // collectPartitioningBy : {false=[1, 3], true=[2, 4]}

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        StringBuilder sb = new StringBuilder();

        for (Integer it : list) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(it);
        }
        System.out.println(sb.toString());
        // 打印结果
        // 1,2,3,4

        String strJoin = Stream.of("1", "2", "3", "4")
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println("strJoin: " + strJoin);
        // 打印结果
        // strJoin: [1,2,3,4]

        // 分割数据块
        Map<Boolean, List<Integer>> collectParti = Stream.of(1, 2, 3, 4)
                .collect(Collectors.partitioningBy(it -> it % 2 == 0));

        Map<Boolean, Integer> mapSize = new HashMap<>();
        collectParti.entrySet()
                .forEach(entry -> mapSize.put(entry.getKey(), entry.getValue().size()));

        System.out.println("mapSize : " + mapSize);
        // 打印结果
        // mapSize : {false=2, true=2}
    }

    public static <T> Collector<T, ?, List<T>> toList() {

        Supplier<List<T>> supplier = ArrayList::new;
        BiConsumer<List<T>, T> add = List::add;

        supplier = () -> new ArrayList<>();

        add = (ts, t) -> ts.add(t);

        BinaryOperator<List<T>> operator = (left, right) -> {
            left.addAll(right);
            return left;
        };

        return null;

        //return new Collectors.CollectorImpl<>(supplier, add,
        //        operator,
        //        null);
    }
}
