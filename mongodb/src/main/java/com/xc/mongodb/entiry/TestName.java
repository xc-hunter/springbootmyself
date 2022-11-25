package com.xc.mongodb.entiry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
// 默认的collection为testName
public class TestName {

    //此处采用MongoDB默认的ObjectId
    @Id
    private String id;
    private String testName;
    private String testPass;

    private long testLength;
}
//db.testName.find()
//        [
//        {
//        _id: ObjectId("638037bff8cd5921735dfbac"),
//        testName: 'testname',
//        testPass: 'testpass',
//        testLength: Long("1"),
//        _class: 'com.xc.mongodb.entiry.TestName'
//        }
//        ]
