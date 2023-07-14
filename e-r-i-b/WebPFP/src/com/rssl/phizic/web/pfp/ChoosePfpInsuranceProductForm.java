package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;

import java.util.List;

/**
 * @author mihaylov
 * @ created 31.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ChoosePfpInsuranceProductForm extends ChoosePfpProductForm
{
	private List<InsuranceProduct> insuranceProductList;
	private List<PensionProduct> pensionProductList;

	public List<InsuranceProduct> getInsuranceProductList()
	{
		return insuranceProductList;
	}

	public void setInsuranceProductList(List<InsuranceProduct> insuranceProductList)
	{
		this.insuranceProductList = insuranceProductList;
	}

	public List<PensionProduct> getPensionProductList()
	{
		return pensionProductList;
	}

	public void setPensionProductList(List<PensionProduct> pensionProductList)
	{
		this.pensionProductList = pensionProductList;
	}
}
