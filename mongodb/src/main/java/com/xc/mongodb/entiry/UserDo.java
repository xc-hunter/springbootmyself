package com.xc.mongodb.entiry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "user")
public class UserDo {
    @Id
    @AutoIncId
    private long id;

    private String username;

    private String password;

    private Date createTime;
}
