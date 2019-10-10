package com.rain.service;// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// **********************************************************************

import Demo.*;

public class HelloI extends _HelloDisp
{
    public HelloI(String name)
    {
        _name = name;
    }

    @Override
    public void
    sayHello(Ice.Current current)
    {
        System.out.println(_name + " says Hello World!");
    }

    @Override
    public void
    shutdown(Ice.Current current)
    {
        System.out.println(_name + " shutting down...");
        current.adapter.getCommunicator().shutdown();
    }

    private final String _name;
}
