package com.bing.lan;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by 蓝兵 on 2018/10/15.
 * <p>
 * <p>  https://blog.csdn.net/io_field/article/details/54971761
 * <p>
 * Stream的操作分类
 * 刚才提到的Stream的操作有Intermediate、Terminal和Short-circuiting：
 * <p>
 * Intermediate：map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 skip、 parallel、 sequential、 unordered
 * <p>
 * Terminal：forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、iterator
 * <p>
 * Short-circuiting：
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
 */

public class StreamTest {

    private static void generatorTest() {
        Stream<Double> generateA = Stream.generate(new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        });

        Stream<Double> generateB = Stream.generate(() -> Math.random());
        Stream<Double> generateC = Stream.generate(Math::random);

        // 不调用 limit 将一直打印下去
        generateA = generateA.limit(5);
        generateA.forEach(System.out::println);

        Stream<Integer> iterate = Stream.iterate(1, item -> item + 1);
        iterate = iterate.limit(10);
        iterate.forEach(System.out::println);
        // 打印结果：1，2，3，4，5，6，7，8，9，10
    }

    private static void intermediateTest() {
        //将两个Stream连接在一起，合成一个Stream
        Stream.concat(Stream.of(1, 2, 3), Stream.of(4, 5))
                .forEach(integer -> System.out.print(integer + "  "));
        // 打印结果
        // 1  2  3  4  5
        System.out.println("main(): ----------");

        //去除掉原Stream中重复的元素，生成的新Stream中没有没有重复的元素
        Stream.of(1, 2, 3, 1, 2, 3)
                .distinct()
                .forEach(System.out::println);
        // 打印结果：1，2，3
        System.out.println("main(): ----------");

        //对原Stream按照指定条件过滤，新的Stream中，只包含满足条件的元素
        Stream.of(1, 2, 3, 4, 5)
                .filter(item -> item > 3)
                .forEach(System.out::println);
        // 打印结果：4，5
        System.out.println("main(): ----------");

        //使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素
        Stream.of("a", "b", "hello")
                .map(item -> item.toUpperCase())
                .forEach(System.out::println);
        // 打印结果
        // A, B, HELLO
        System.out.println("main(): ----------");

        //将原Stream中的每一个元素通过转换函数转换
        Stream.of(1, 2, 3)
                .flatMap(integer -> Stream.of(integer * 10, 110))
                .forEach(System.out::println);

        System.out.println("----等价于----");

        Stream.of(10, 110, 20, 110, 30, 110)
                .forEach(System.out::println);
        // 打印结果
        // 10, 110, 20, 110, 30, 110

        System.out.println("main(): ----------");

        //生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），
        //新Stream每个元素被消费的时候都会执行给定的消费函数，并且消费函数优先执行
        Stream.of(1, 2, 3, 4, 5)
                .peek(integer -> System.out.println("accept:" + integer))
                .forEach(System.out::println);
        // 打印结果
        // accept:1
        //  1
        //  accept:2
        //  2
        //  accept:3
        //  3
        //  accept:4
        //  4
        //  accept:5
        //  5
        System.out.println("main(): ----------");

        //过滤掉原Stream中的前N个元素，返回剩下的元素所组成的新Stream。如果原Stream的元素个数大于N，将返回原
        //Stream的后（原Stream长度-N）个元素所组成的新Stream；如果原Stream的元素个数小于或等于N，将返回一个空Stream
        Stream.of(1, 2, 3, 4, 5)
                .skip(2)
                .forEach(System.out::println);
        // 打印结果
        // 3,4,5

        System.out.println("main(): ----------");

        //对原Stream进行排序，返回一个有序列的新Stream。sorterd有两种变体sorted()，sorted(Comparator)，前者
        //将默认使用Object.equals(Object)进行排序，而后者接受一个自定义排序规则函数(Comparator)，可按照意愿排序
        Stream.of(5, 4, 3, 2, 1)
                .sorted()
                .forEach(System.out::println);

        System.out.println("--------");

        Stream.of(1, 2, 3, 4, 5)
                .sorted()
                .forEach(System.out::println);
        // 打印结果
        // 1，2，3,4,5
    }

    private static void terminalTest() {
        Stream.of(5, 1, 4, 4, 3, 2, 1)
                .forEachOrdered(System.out::println);
        // 打印结果
        // 1，2，3,4,5
        System.out.println("main(): ----------");

        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5)
                .max((o1, o2) -> o1 - o2);
        System.out.println("max:" + max.get());
        // 打印结果：max:5

        Optional<Integer> min = Stream.of(1, 2, 3, 4, 5)
                .min((o1, o2) -> o1 - o2);
        System.out.println("min:" + min.get());
        // 打印结果：min:1
    }

    private static void shortCircuitingTest() {
        boolean allMatch = Stream.of(1, 2, 3, 4)
                .allMatch(integer -> integer > 0);
        System.out.println("allMatch: " + allMatch);
        // 打印结果：allMatch: true

        boolean allMatch_ = Stream.of(1, 2, 3, 4)
                .allMatch(integer -> integer > 1);
        System.out.println("allMatch_: " + allMatch_);
        // 打印结果：allMatch: false

        System.out.println("main(): ----------");

        boolean noneMatch = Stream.of(1, 2, 3, 4, 5)
                .noneMatch(integer -> integer > 10);
        System.out.println("noneMatch:" + noneMatch);
        // 打印结果 noneMatch:true

        boolean noneMatch_ = Stream.of(1, 2, 3, 4, 5)
                .noneMatch(integer -> integer > 3);
        System.out.println("noneMatch_:" + noneMatch_);
        // 打印结果 noneMatch_:false

        System.out.println("main(): ----------");

        Optional<Integer> any = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).findAny();
        System.out.println("any:" + any.get());

        System.out.println("main(): ----------");

        Optional<Integer> first = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).findFirst();
        System.out.println("first:" + first.get());

        System.out.println("main(): ----------");

        Stream.of(1, 2, 3, 4, 5)
                .limit(2)
                .forEach(System.out::println);
        // 打印结果
        // 1,2
    }
}
