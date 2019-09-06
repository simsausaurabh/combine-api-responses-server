package com.webengage.predict.application.database.dao;

import com.webengage.predict.application.database.bo.UrlBo;

import javax.ws.rs.core.Response;
import java.util.List;

public interface UrlDao {

    UrlBo findById(Integer id);
    List<UrlBo> findAll();
    Response insert(UrlBo urlBo);
}
