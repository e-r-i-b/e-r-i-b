package com.rssl.phizic.business.sms.locale;

import com.rssl.phizic.business.sms.SMSRefusingResource;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author koptyaev
 * @ created 11.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledSMSRefusingResource extends SMSRefusingResource
{
	private Set<SmsResourceResources> resources;

	@Override
	public String getText()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getText();
		return super.getText();
	}
}
