package com.coship.pay.txmanager;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/11/18
 */
@Service
@Slf4j
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService{


    @Value("${tm.manager.url}")
    private String url;

    @Override
    public String getTxUrl() {
        log.info("load tm.manager.url ");
        return url;
    }
    
}