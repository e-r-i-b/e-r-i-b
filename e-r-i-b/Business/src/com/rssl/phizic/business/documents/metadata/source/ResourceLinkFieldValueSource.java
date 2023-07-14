package com.rssl.phizic.business.documents.metadata.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Ресурс линков счетов списания/зачисления
 *
 * @author khudyakov
 * @ created 09.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ResourceLinkFieldValueSource implements FieldValuesSource
{
	public static final String FROM_RESOURCE_ATTRIBUTE_NAME             = "fromResource";
	public static final String TO_RESOURCE_ATTRIBUTE_NAME               = "toResource";

	private Map<String, String> source = new HashMap<String, String>();


	public ResourceLinkFieldValueSource(BusinessDocument document) throws BusinessException
	{
		try
		{
			PaymentAbilityERL fromResourceLink = document.getChargeOffResourceLink();
			if (fromResourceLink != null)
			{
				source.put(FROM_RESOURCE_ATTRIBUTE_NAME, fromResourceLink.getCode());
			}

			ExternalResourceLink toResourceLink = document.getDestinationResourceLink();
			if (toResourceLink != null)
			{
				source.put(TO_RESOURCE_ATTRIBUTE_NAME, toResourceLink.getCode());
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getValue(String name)
	{
		return source.get(name);
	}

	public Map<String, String> getAllValues()
	{
		return Collections.unmodifiableMap(source);
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return false;
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
