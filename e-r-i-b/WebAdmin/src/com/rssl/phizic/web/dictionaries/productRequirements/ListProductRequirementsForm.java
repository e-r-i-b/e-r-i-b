package com.rssl.phizic.web.dictionaries.productRequirements;

import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirementType;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lepihina
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListProductRequirementsForm extends ListFormBase
{
	private List<ProductRequirementType> productRequirementTypes = new ArrayList<ProductRequirementType>();
	private List<DepositProduct> accountTypes = new ArrayList<DepositProduct>();

	public void setProductRequirementTypes(List<ProductRequirementType> productRequirementTypes)
	{
		this.productRequirementTypes = productRequirementTypes;
	}

	public List<ProductRequirementType> getProductRequirementTypes()
	{
		return productRequirementTypes;
	}

	public List<DepositProduct> getAccountTypes()
	{
		return accountTypes;
	}

	public void setAccountTypes(List<DepositProduct> accountTypes)
	{
		this.accountTypes = accountTypes;
	}
}
