// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.2
//
// <auto-generated>
//
// Generated from file `Message.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.rain.common.ice.v1.message;

public abstract class Callback_MessageService_doInvoke
    extends IceInternal.TwowayCallback implements Ice.TwowayCallbackArg1<String>
{
    public final void __completed(Ice.AsyncResult __result)
    {
        MessageServicePrxHelper.__doInvoke_completed(this, __result);
    }
}
