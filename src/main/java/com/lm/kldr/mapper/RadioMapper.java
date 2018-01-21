package com.lm.kldr.mapper;

import com.lm.kldr.domain.Radio;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

/**
 * @description
 * @author&date Created by louiemain on 2018/1/21 20:40
 */
public interface RadioMapper {

    @InsertProvider(type = RadioProviderDao.class, method = "add")
    @Options(useGeneratedKeys = true, keyColumn = "rid", keyProperty = "id")
    Integer insertRadio(Radio radio);

    class RadioProviderDao {
        public String add(Radio radio) {
            String a = radio.getA();
            String b = radio.getB();
            String c = radio.getC();
            String d = radio.getD();
            String e = radio.getE();
            a = a.replace("'", "\\\'");
            b = b.replace("'", "\\\'");
            c = c.replace("'", "\\\'");
            d = d.replace("'", "\\\'");
            e = e.replace("'", "\\\'");
            String sql = "insert into radio (A, B, C, D, E) values ('"
                    + a + "', '"
                    + b + "', '"
                    + c + "', '"
                    + d + "', '"
                    + e + "')";
            return sql;
        }
    }

}
