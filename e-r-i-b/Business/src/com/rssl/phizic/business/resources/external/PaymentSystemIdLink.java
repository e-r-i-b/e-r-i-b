package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.documents.ResourceType;

/**
 * @author Gainanov
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemIdLink extends ExternalResourceLinkBase
{
	public static final String CODE_PREFIX = "payment-system-id";
	private Billing billing;

	public String getValue()
	{
		return getExternalId();
	}

	public Billing getBilling()
	{
		return billing;
	}

	public void setBilling(Billing billing)
	{
		this.billing = billing;
	}

	public void reset()
	{
		//nothing to do
	}

	public int compareTo(Object o)
	{
		return getExternalId().compareTo( ((PaymentSystemIdLink)o).getExternalId());
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$paymentSystemName:" + this.getId();
	}
}
