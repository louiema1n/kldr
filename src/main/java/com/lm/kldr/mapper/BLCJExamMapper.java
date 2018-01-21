package com.lm.kldr.mapper;

import com.lm.kldr.domain.BLCJExam;
import org.apache.ibatis.annotations.InsertProvider;

/**
 * @description
 * @author&date Created by louiemain on 2018/1/21 20:40
 */
public interface BLCJExamMapper {

    @InsertProvider(type = BLCJExamProviderDao.class, method = "add")
    Integer insert(BLCJExam blcjExam);

    class BLCJExamProviderDao {
        public String add(BLCJExam blcjExam) {
            String sql = "insert into blcjexam (name, catalog, type, eid, commons, anser, analysis, rid) values ('"
                    + blcjExam.getName() + "', '"
                    + blcjExam.getCatalog() + "', '"
                    + blcjExam.getType() + "', '"
                    + blcjExam.getEid() + "', '"
                    + blcjExam.getCommons() + "', '"
                    + blcjExam.getAnser() + "', '"
                    + blcjExam.getAnalysis() + "', "
                    + blcjExam.getRid() + ")";
            return sql;
        }
    }

}
