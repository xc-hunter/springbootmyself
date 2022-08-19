package com.xc.mongodb.config;

import com.xc.mongodb.entiry.AutoIncId;
import com.xc.mongodb.entiry.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Component
public class AutoIncIdGeneratorByAnnotation extends AbstractMongoEventListener {

    private static final String SEQUENCE="sequence";
    @Autowired
    MongoTemplate mongo;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        final Object source=event.getSource();
        if(source!=null){
            ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                //dowith在fieldCallback中，会对所有域进行遍历
                @Override
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    //将第一个字段设置为可读写，主要针对private字段
                    ReflectionUtils.makeAccessible(field);
                    //判断是否添加了标识注解
                    if(field.isAnnotationPresent(AutoIncId.class)
                        &&field.get(source) instanceof Number
                        &&field.getLong(source)==0
                        ){
                        //设置自增ID,Sequence中采用@Document注解的collection值
                        //即Sequence中colname与集合名对应
                        //目前可以实现几个相似类存储到同一个集合中，且id分配相同
                        Document as=source.getClass().getAnnotation(Document.class);
                        String colname=as.collection();
                        field.set(source,getNextAutoId(colname));
                    }
                }
            });
        }
    }
    // 此处可能需要加锁
    private Long getNextAutoId(String colname){
        Query query=new Query(Criteria.where("colname").is(colname));
        /**第一次存入，需要生成记录
        if(!mongo.exists(query,"sequence")){
            Sequence s=new Sequence();
            s.setColname(colname);
            s.setColid(1);
            mongo.insert(s);
            return 1L;
        }else{*/
            //此处采用查找并更新，使用了Query、Update、FindAndModifyOptions
            //Update指定更新键和更新策略
            Update update=new Update();
            update.inc("colid",1);
            //选项，定义为更新，并返回新值
            FindAndModifyOptions options=new FindAndModifyOptions();
            options.upsert(true);
            options.returnNew(true);
            Sequence seq=mongo.findAndModify(query,update,options,Sequence.class);
            return seq.getColid();

    }
}
