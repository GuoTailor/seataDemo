package com.example.server2.service;

import com.example.server2.model.Order;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * Created by gyh on 2022/6/21
 */
@LocalTCC
public interface TccActionOne {
    @TwoPhaseBusinessAction(name = "FeignTccActionOne", commitMethod = "commit", rollbackMethod = "rollback")
    Integer prepare(@BusinessActionContextParameter(paramName = "order") Order order);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
