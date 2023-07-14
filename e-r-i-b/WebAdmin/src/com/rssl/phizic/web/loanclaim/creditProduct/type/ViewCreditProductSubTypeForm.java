package com.rssl.phizic.web.loanclaim.creditProduct.type;

import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import java.util.Set;

/**
 * @author Moshenko
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 * Форма просмотра суб кодов типа кредитного продукта.
 */
public class ViewCreditProductSubTypeForm   extends ActionFormBase
{
	protected Long id;
	private List<String> tbNumbers;
	private Set<CreditSubProductType> creditSubProductTypes;

	public List<String> getTbNumbers()
	{
		return tbNumbers;
	}

	public void setTbNumbers(List<String> tbNumbers)
	{
		this.tbNumbers = tbNumbers;
	}

	public Set<CreditSubProductType> getCreditSubProductTypes()
	{
		return creditSubProductTypes;
	}

	public void setCreditSubProductTypes(Set<CreditSubProductType> creditSubProductTypes)
	{
		this.creditSubProductTypes = creditSubProductTypes;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
