package com.rssl.phizic.web.struts;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * @author Evgrafov
 * @ created 23.03.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 994 $
 */
public class MultiPropertyMessageResourcesFactory extends MessageResourcesFactory
{
	/**
	 * Create and return a newly instansiated <code>MessageResources</code>. This method must be implemented by
	 * concrete subclasses.
	 *
	 * @param config Configuration parameter(s) for the requested bundle
	 */
	public MessageResources createResources(String config)
	{
		return new MultiPropertyMessageResources(this, config, this.returnNull);
	}
}