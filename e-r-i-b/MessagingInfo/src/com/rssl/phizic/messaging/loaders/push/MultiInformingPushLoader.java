package com.rssl.phizic.messaging.loaders.push;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * @author basharin
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */

public class MultiInformingPushLoader extends MultiTemplateLoader
{

	public MultiInformingPushLoader()
	{
		super(new TemplateLoader[]{new ShortMessageInformingPushLoader(), new FullMessageInformingPushLoader(), new InformingSmsForPushLoader()});
	}
}
