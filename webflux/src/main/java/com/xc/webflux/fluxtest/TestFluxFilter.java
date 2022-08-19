package com.xc.webflux.fluxtest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class TestFluxFilter {
    public static void main(String[] args) {
        Flux<Player> fp=Flux.just("xc,1","xx,2","cc,3")
                .flatMap(s-> Mono.just(s).map(ss->{
                    String[] sss=ss.split(",");
                    return new Player(sss[0],sss[1]);
                })).subscribeOn(Schedulers.parallel());
        Flux<Integer> xcFlux=Flux.just(1,2,3,4);
        Flux<List<Integer>> xclFlux=xcFlux.buffer(2);
    }
    public  static class Player{
        private String name;
        private String id;

        public Player(String name, String id) {
            this.name = name;
            this.id = id;
        }
    }
}
