package com.rssl.phizic.business.ermb.sms.module.loader;

import com.rssl.phizic.business.ermb.sms.context.SmsPersonDataProvider;
import com.rssl.phizic.business.ermb.sms.messaging.ErmbSmsChannel;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.module.loader.BasicModuleLoader;

/**
 * @author Erkin
 * @ created 17.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загрузчик модуля СМС-канала
 */
public class SmsModuleLoader extends BasicModuleLoader
{
	/**
	 * ctor
	 * @param module - модуль СМС-канала
	 */
	public SmsModuleLoader(ErmbSmsChannel module)
	{
		super(module);
	}

	@Override
	protected void doStart()
	{
		super.doStart();

		PersonContext.setPersonDataProvider(SmsPersonDataProvider.getInstance(), getModule().getApplication());
	}

	@Override
	protected void doStop()
	{
		PersonContext.setPersonDataProvider(null, getModule().getApplication());

		super.doStop();
	}
}
