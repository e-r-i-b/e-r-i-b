package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.utils.EntityCompositeId;

/**
 * @author gladishev
 * @ created 30.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanCompositeId extends EntityCompositeId
{
	private static final String PRODUCT_TYPE_DELIMETER = "|";

	private String productType;

	public LoanCompositeId(String id)
	{
		super(id);
		String[] strings = getEntityId().split("\\|");
		productType = strings[1];
		entityId = strings[0];
	}

	LoanCompositeId(String entityId, String systemId, String rbBrchId, Long loginId, String productType)
	{
		super(entityId, systemId, rbBrchId, loginId);
		this.productType = productType;
	}

	public String getProductType()
	{
		return productType;
	}

	public String toString()
	{
		StringBuilder compositeId = new StringBuilder();
		compositeId.append(entityId).append(PRODUCT_TYPE_DELIMETER);
		compositeId.append(productType).append(ID_DELIMETER);
		compositeId.append(getSystemId()).append(ID_DELIMETER);
		compositeId.append(getRbBrchId()).append(ID_DELIMETER);
		compositeId.append(getLoginId());
		return compositeId.toString();
	}
}
