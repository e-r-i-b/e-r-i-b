package com.rssl.phizic.web.struts;

import org.apache.struts.util.MessageResources;

/**
 * @author Mescheryakova
 * @ created 07.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileMultiPropertyMessageResourcesFactory extends MultiPropertyMessageResourcesFactory
{
	public MessageResources createResources(String config)
	{
		return new MobileMultiPropertyMessageResources(this, config, this.returnNull);
	}
}
