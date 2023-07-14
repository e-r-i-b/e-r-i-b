package com.rssl.phizic.web.client.ext.sbrf;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author kligina
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ChangeProductDisplayForm extends ActionFormBase
{
	private String id;
	private String productType;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}
	
}
