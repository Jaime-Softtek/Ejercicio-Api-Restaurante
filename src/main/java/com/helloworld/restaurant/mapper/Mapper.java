package com.helloworld.restaurant.mapper;

public interface Mapper<INPUT, OUTPUT> {
    OUTPUT map(INPUT input);
}
