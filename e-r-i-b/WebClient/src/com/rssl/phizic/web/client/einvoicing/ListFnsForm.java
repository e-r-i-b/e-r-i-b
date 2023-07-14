package com.rssl.phizic.web.client.einvoicing;

import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 16.12.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListFnsForm extends ListFormBase
{
	private Map<String, String> linkTaxIndexState = new HashMap<String, String>();    // соответствие статусов документов и налоговых индексов

	public Map<String, String> getLinkTaxIndexState()
	{
		return linkTaxIndexState;
	}

	public void setLinkTaxIndexState(Map<String, String> linkTaxIndexState)
	{
		this.linkTaxIndexState = linkTaxIndexState;
	}
}
