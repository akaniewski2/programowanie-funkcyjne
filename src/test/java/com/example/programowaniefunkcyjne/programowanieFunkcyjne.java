package com.example.programowaniefunkcyjne;


import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class programowanieFunkcyjne {

    @Test
    //Function
    public void function() {
        Function<Integer, String> function  = x -> x + x + "=test"  ;
        System.out.println(function.apply(77));

    }

    @Test
    //zwraca true,false
    public void predicate()  {
        Predicate<Student> predicate = p->p.getAge()>21;
        boolean test1=predicate.test(new Student("Przemek","Bykowski",34));
        boolean test2=predicate.test(new Student("Karolina","Nowak",20));

        System.out.println(test1);
        System.out.println(test2);
    }

    //Threads
    @Test
    public void classicThread() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1");
            }
        });

        t1.start();
    }
    @Test
    public void functionalThread() {

        Thread t1 = new Thread(()->
        { //klamerki jesli wiecej niz jeden wiersz
            System.out.println("t1a");
            System.out.println("t1b");
        }
        );

        Thread t2 = new Thread(()->System.out.println("t2") );

        t1.start();
        t2.start();
    }

    //ReferenceMethod
    @Test
    public void Consumer() {
        List<String> names = Arrays.asList("Przemek","Dorota","Jacek","Paweł","Marysia","Mirosław","Ala");

        //full
        names.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        //short
        System.out.println("------------------");
        names.forEach((String s)->System.out.println(s));

        //Shorter
        System.out.println("------------------");
        names.forEach((name)->System.out.println(name));

        //superShort
        System.out.println("------------------");
        names.forEach(name->System.out.println(name));

        //superSuperShort
        System.out.println("------------------");
        names.forEach(System.out::println);

    }

    @Test
    public void example() {
        List<String> names = Arrays.asList("Przemek","Dorota","Jacek","Paweł","Marysia","Mirosław","Ala");
        long counter=0;

        for (String name:names) {
            if (name.length()>6){
                counter++;
            }
        }

        System.out.println(counter);

        long counter2 = names.stream().filter(name -> name.length() > 6).count();
        System.out.println(counter2);

    }



    @Test
    void mapExample() {
        List<String> names = Arrays.asList("Przemek","Dorota","Jacek","Paweł","Marysia","Mirosław","Ala");
        names.stream()
                .map(String::length)
                .forEach(System.out::println);

        System.out.println(names);
    }

    @Test
    public void limitAndSort(){
        List<String> names = Arrays.asList("Przemek","Dorota","Jacek","Paweł","Marysia","Mirosław","Ala");

        List<String> collect = names.stream()
                .filter(s -> s.length() > 5)
                //.limit(3)
                .map(String::toUpperCase)
                //.sorted()
                .sorted((s1, s2) ->s2.length()-s1.length() )
                .collect(Collectors.toList());


        System.out.println(collect);


         String  collect2 = names.stream().collect(Collectors.joining("+"));

        System.out.println(collect2);


    }


    @Test
    public void lambdaExample() {
        List<Integer> numbers =Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        System.out.println(numbers);

        final Integer[] integer={2};

        //deklaracja
        Stream<Integer> integerStream = numbers.stream()
                .map(number -> number * integer[0])
                ;


        System.out.println(integerStream); //<-java.util.stream.ReferencePipeline$3@14cd1699

        integer[0]=10;  //lazy initialization -> podtawiane w momencie wywoływania

        //lazy initialization
        //wywołanie
        integerStream.forEach(System.out::println);

        List<Integer> collect = numbers.stream()
                .map(number -> number * integer[0])
                .collect(Collectors.toList());

        System.out.println(collect);
    }


    @Test
    public void statistics() {
        List<Integer> numbers =Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        OptionalInt optionalInt = numbers.stream()
                .mapToInt(x->x)
                .max()
                //.min()
                 ;

        System.out.println(optionalInt.getAsInt());

        IntSummaryStatistics intSummaryStatistics = numbers.stream()
                .mapToInt(x -> x)
                .summaryStatistics();

        System.out.println(intSummaryStatistics);

    }
}
