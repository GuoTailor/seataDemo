package com.example.server1.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * Created by gyh on 2022/6/21
 */
@LocalTCC
public interface TccActionTwo {
    @TwoPhaseBusinessAction(name = "FeignTccActionTwo", commitMethod = "commit", rollbackMethod = "rollback")
    Integer prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "id") Integer id);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
