package com.xc.mongodb.entiry;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * 用户信息，使用基类标识
 */
@Data
@Document(collection = "profile")
public class Profile extends IncrIdEntity{
    /**
     * 昵称
     */
    private String nockname;
    /**
     * 性别
     */
    private Integer gender;

    private String exet;

    private Map<String,Object> message;
}
