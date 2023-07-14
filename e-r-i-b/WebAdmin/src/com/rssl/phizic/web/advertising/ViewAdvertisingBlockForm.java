package com.rssl.phizic.web.advertising;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.advertising.AdvertisingBlock;

/**
 * @author lukina
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ViewAdvertisingBlockForm extends EditFormBase
{
	private AdvertisingBlock advertisingBlock;

	public AdvertisingBlock getAdvertisingBlock()
	{
		return advertisingBlock;
	}

	public void setAdvertisingBlock(AdvertisingBlock advertisingBlock)
	{
		this.advertisingBlock = advertisingBlock;
	}
}
