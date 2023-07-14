package com.rssl.phizic;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */

import java.util.HashMap;
import java.util.Map;

/**
 * @see RequestCoordinatorSingleton#RequestCoordinatorSingleton()
 */
@SuppressWarnings("UnusedDeclaration")
public class PhizMBRequestCoordinatorImpl implements PhizMBRequestCoordinator
{
	private final Map<String, RequestProcessor> processors = new HashMap<String, RequestProcessor>();

	/**
	 * ctor
	 */
	public PhizMBRequestCoordinatorImpl()
	{
		register(new RemoveErmbPhoneRequestProcessor());
		register(new ErmbInfoRequestProcessor());
	}

	private void register(RequestProcessor processor)
	{
		processors.put(processor.getRequestName(), processor);
	}

	public RequestProcessor getProcessor(Request request)
	{
		return processors.get(request.getName());
	}
}
