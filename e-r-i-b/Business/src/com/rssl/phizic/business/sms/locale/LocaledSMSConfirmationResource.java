package com.rssl.phizic.business.sms.locale;

import com.rssl.phizic.business.sms.SMSConfirmationResource;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author koptyaev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledSMSConfirmationResource extends SMSConfirmationResource
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
