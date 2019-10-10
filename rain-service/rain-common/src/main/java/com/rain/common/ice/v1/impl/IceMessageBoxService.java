package com.rain.common.ice.v1.impl;

import com.rain.common.ice.v1.server.AbstractIceBoxService;
import com.rain.common.servcie.StartupUtils;

import Ice.Communicator;
import Ice.Identity;
import Ice.Object;
import Ice.ObjectAdapter;

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
