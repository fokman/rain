package com.rain.common.ice.v1.impl;

import Ice.Current;
import com.rain.common.core.SoaManager;
import com.rain.common.ice.v1.message.Context;
import com.rain.common.ice.v1.message._MessageServiceDisp;

public class IceMessageService extends _MessageServiceDisp {

    private static final long serialVersionUID = 1L;
    private static final IceServiceRegister register = IceServiceRegister.getInstance();

    @Override
    public String doInvoke(Context context, Current __current) {
        return SoaManager.getInstance().Invoke(context);
    }


}
