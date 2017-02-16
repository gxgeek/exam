package com.gx.dao;

import com.gx.model.Content;
import com.baomidou.mybatisplus.mapper.AutoMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * Content 表数据库控制层接口
 *
 */
public interface ContentMapper extends AutoMapper<Content> {


    List<Map<String,Object>> selectProduct(@Param("id") Integer id,@Param("type")  String type);

    Map<String,Object> selectProductByUid(@Param("id") Long id,@Param("uid") Integer uid);
}