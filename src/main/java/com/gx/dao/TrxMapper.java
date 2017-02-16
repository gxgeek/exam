package com.gx.dao;

import com.gx.model.BuyPO;
import com.gx.model.Trx;
import com.baomidou.mybatisplus.mapper.AutoMapper;

import java.util.List;
import java.util.Map;

/**
 *
 * Trx 表数据库控制层接口
 *
 */
public interface TrxMapper extends AutoMapper<Trx> {


    List<Map<String,Object>> selectAccountByUid(Integer uid);

    int insertList(List<BuyPO> list, Integer uid);
}