package com.rain.common.ice.v1.server;

import com.rain.common.ice.v1.impl.IceMessageService;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kunliu
 */
@Slf4j
public abstract class AbstractIceBoxService implements com.zeroc.IceBox.Service {
	protected ObjectAdapter _adapter;

	@Override
	public void start(String name, Communicator communicator, String[] args) {
		_adapter = communicator.createObjectAdapter(name);
		_adapter.add(new IceMessageService(), com.zeroc.Ice.Util.stringToIdentity("message"));
		_adapter.activate();
	}


	@Override
	public void stop() {
		_adapter.destroy();

	}

}
