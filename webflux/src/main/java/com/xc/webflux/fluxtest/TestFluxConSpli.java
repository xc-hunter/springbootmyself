package com.xc.webflux.fluxtest;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

public class TestFluxConSpli {
    public static void main(String[] args) {
        Flux<String> fl1=Flux.just("xx1","xx2","xx3");
        Flux<String> fl2=Flux.just("cc1","cc2","cc3");
        Flux<Tuple2<String,String>> fl3=Flux.zip(fl1,fl2);
        fl3.map(s->s.getT1()+s.getT2()).subscribe(System.out::print);
        System.out.println();
        Flux<String> fl4=Flux.zip(fl1,fl2,(a,b)->a+":::"+b);
        fl4.subscribe(System.out::print);
    }

}
