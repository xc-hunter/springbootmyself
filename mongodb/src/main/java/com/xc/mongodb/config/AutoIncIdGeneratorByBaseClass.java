package com.xc.mongodb.config;

import com.xc.mongodb.entiry.IncrIdEntity;
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


// 分别基于注解标识或者抽象基类的方式实现了自增id的分配
// 推荐使用基于抽象基类的方式
// 基于注解的方式，需要进行反射操作
@Component
public class AutoIncIdGeneratorByBaseClass extends AbstractMongoEventListener {

    private static final String SEQUENCE="sequence";
    @Autowired
    MongoTemplate mongo;
    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object source=event.getSource();
        //采用自增id
        if(source instanceof IncrIdEntity){
            IncrIdEntity entity=(IncrIdEntity) source;
            //未设置id，即采用自增id
            if(entity.getId()==null){
                //获取并设置id
                //设置自增ID,Sequence中采用@Document注解的collection值
                //即Sequence中colname与集合名对应
                //可以实现几个相似类存储到同一个集合中，且id分配相同
                //Todo：由于相似类存储到同一个集合中，注意ID获取并发安全
                Document as=source.getClass().getAnnotation(Document.class);
                String colname=as.collection();
                Number id=this.getNextAutoId(colname);
                entity.setId(id);
            }
        }
    }
    // 此处控制并发获取id
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
            //如果值不存在就执行插入,所以就不需要进行存不存在判断，即上方的第一次存入判断
            options.upsert(true);
            options.returnNew(true);
            synchronized (this){
                Sequence seq=mongo.findAndModify(query,update,options,Sequence.class);
                return seq.getColid();
            }
    }
}
