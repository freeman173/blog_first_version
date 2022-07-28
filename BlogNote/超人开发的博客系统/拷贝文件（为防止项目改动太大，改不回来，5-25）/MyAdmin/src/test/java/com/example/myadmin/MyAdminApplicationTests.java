package com.example.myadmin;

import com.example.myadmin.bo.OneBo;
import com.example.myadmin.mapper.BarDataMapper;
import com.example.myadmin.mapper.OneMapper;
import com.example.myadmin.pojo.Article;
import com.example.myadmin.vo.BarDataVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyAdminApplicationTests {

    @Autowired
    private BarDataMapper barDataMapper;
    @Autowired
    private OneMapper oneMapper;
    @Test
    void contextLoads() {


        List<BarDataVo> result=barDataMapper.getBarData();
        List<OneBo> reviewResult=oneMapper.getData();

        for (BarDataVo barDataVo:result){
            for (OneBo oneBo:reviewResult){

                if(barDataVo.getId().equals(oneBo.getId())){
                    barDataVo.setReviewCounts(oneBo.getReviewCounts());
                }

            }
            if(barDataVo.getReviewCounts()==null){
                barDataVo.setReviewCounts(0);
            }

        }


        System.out.println("ok");

    }

}
