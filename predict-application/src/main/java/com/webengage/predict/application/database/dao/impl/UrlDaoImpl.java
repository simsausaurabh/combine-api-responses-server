package com.webengage.predict.application.database.dao.impl;

import com.webengage.predict.api.exception.PredictApiException;
import com.webengage.predict.api.model.ResponseStatus;
import com.webengage.predict.api.response.PredictResponseDto;
import com.webengage.predict.application.database.bo.UrlBo;
import com.webengage.predict.application.database.dao.UrlDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

// This annotation provides log() method to log without explicitly creating instance of Logger for the class.
@Slf4j
public class UrlDaoImpl extends AbstractDAO<UrlBo> implements UrlDao {

    @Inject
    public UrlDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public UrlBo findById(Integer id) {
        return get(id);
    }

    @Override
    public List<UrlBo> findAll() {
        return list(namedQuery("Url.findAll"));
    }

    @Override
    public Response insert(UrlBo urls) {
        UrlBo data = null;
        Map<String, Object> responseData = new HashMap<>();
        try {
            data = persist(urls);
            responseData.put("id", data.getId());
        } catch (Exception e) {
             if(((ConstraintViolationException) e).getErrorCode() == 1062) PredictApiException.INTERNAL_SERVER_ERROR.throwException("Duplicate URLs");
             PredictApiException.INTERNAL_SERVER_ERROR.throwException("Issue in obtaining data");
        }
        return  Response.ok(PredictResponseDto.<Map>build(ResponseStatus.STATUS_SUCCESS, null, responseData)).build();
    }
}
