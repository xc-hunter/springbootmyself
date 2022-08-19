package com.xc.webflux.fluxtest;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestFluxCreate {
    public static void main(String[] args) {
        Flux<String> fruitFlux=Flux.just("Apple","Orange","Grape","xc","x","c");
        fruitFlux.filter(s->s.length()>2)
                .map(s->"pre"+s+"pos")
                .subscribe(s-> System.out.print(s));
        System.out.println();
        System.out.println("---------");
        String[] strs=new String[]{"xc1","xc2","xc3","xc4","xc1","xc2","xc3"};
        Flux<String> fs=Flux.fromArray(strs);
        fs.subscribe(s-> System.out.print(s));
        System.out.println();
        System.out.println("---------");
        fs.distinct().subscribe(s-> System.out.print(s));
        System.out.println();
        System.out.println("----------");
        List<String> fls=new ArrayList<>();
        fls.add("x1");
        fls.add("x2");
        fls.add("x3");
        fls.add("x4");
        Flux<String> flss=Flux.fromIterable(fls);
        flss.subscribe(System.out::print);
        System.out.println();
        System.out.println("------");
        Stream<String> sss=Stream.of("xx1","xx2","xx3","xx4");
        Flux<String> fes=Flux.fromStream(sss);
        Flux<String> fes2=Flux.fromStream(Stream.of("1","2","3","4"));
        Flux<Integer> fi=Flux.range(1,10);
        fes.mergeWith(fes2);
    }
}
