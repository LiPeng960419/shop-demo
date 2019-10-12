package com.coship.pay.txmanager;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/11/18
 */

@Service
@Slf4j
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService{

    @Override
    public String httpGet(String url) {
        log.info("httpGet-start");
        String res = HttpUtils.get(url);
        log.info("httpGet-end");
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        log.info("httpPost-start");
        String res = HttpUtils.post(url,params);
        log.info("httpPost-end");
        return res;
    }
    
}