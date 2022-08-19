package com.xc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xc
 * @date 2021.06.11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheBean {
    private Long markId;
    private String description;
}
