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

public final class MessageServiceHolder extends Ice.ObjectHolderBase<MessageService>
{
    public
    MessageServiceHolder()
    {
    }

    public
    MessageServiceHolder(MessageService value)
    {
        this.value = value;
    }

    public void
    patch(Ice.Object v)
    {
        if(v == null || v instanceof MessageService)
        {
            value = (MessageService)v;
        }
        else
        {
            IceInternal.Ex.throwUOE(type(), v);
        }
    }

    public String
    type()
    {
        return _MessageServiceDisp.ice_staticId();
    }
}
