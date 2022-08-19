package com.xc.mongodb.entiry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//sequence为其他实体生成自增ID
@Data
@Document(collection = "sequence")
public class Sequence {
    //其自己的ID此处采用MongoDB默认的ObjectId
    @Id
    private String id;
    //collection_name
    private String colname;
    //collection_id
    private long colid;

}
