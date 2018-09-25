package com.zhiyou100.dao;

import com.zhiyou100.entity.RealCheckEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealCheckDao {
     int insert(RealCheckEntity realCheckEntity);

    List<RealCheckEntity> unRealCheckedList();

    int updateStatusById(@Param("status") int status, @Param("id")int id);

    RealCheckEntity findById(int id);
}
