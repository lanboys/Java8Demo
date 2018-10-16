package com.bing.lan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 蓝兵 on 2018/10/16.
 */

public class CollectorsTest {

    public static void toListTest() {
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
