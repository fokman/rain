package com.rain.common.ice.impl;

import com.rain.common.core.SoaManager;
import com.rain.common.ice.message.MessageService;
import com.rain.common.ice.message.MsgRequest;
import com.zeroc.Ice.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IceMessageService implements MessageService {

    @Override
    public String doInvoke(MsgRequest msgRequest, Current __current) {
        return SoaManager.getInstance().Invoke(msgRequest);
    }
}
