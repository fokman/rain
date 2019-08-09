package com.rain.common.ice.impl;

import com.rain.common.ice.server.AbstractIceBoxService;
import com.rain.common.servcie.StartupUtils;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;


public class IceMessageBoxService extends AbstractIceBoxService {

    private static final IceMessageService messageService = new IceMessageService();

    public void start(String name, Communicator communicator, String[] args) {
        StartupUtils.init();
        super.start(name, communicator, args);
    }

    protected void addMyIceServiceObjFacets(ObjectAdapter adapter, Identity id) {
        adapter.addFacet(messageService, id, "test");
    }

    public Object createMyIceServiceObj(String[] arg0) {
        return messageService;
    }


}
