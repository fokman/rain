// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// **********************************************************************

#pragma once
[["java:package:com.rain"]]
module service
{

interface Hello
{
    idempotent void sayHello();
    void shutdown();
};

};
