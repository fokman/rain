package com.rain.common.ice.v1.impl;

import com.rain.common.core.SoaManager;
import com.rain.common.ice.v1.message.MessageService;
import com.rain.common.ice.v1.message.MsgRequest;
import com.zeroc.Ice.Current;

/**
 * @author kunliu
 */
public class IceMessageService implements MessageService {

    @Override
    public String doInvoke(MsgRequest context, Current current) {
        return SoaManager.getInstance().Invoke(context);
    }
}
