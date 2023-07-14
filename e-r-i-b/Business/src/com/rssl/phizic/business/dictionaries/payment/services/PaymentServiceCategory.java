package com.rssl.phizic.business.dictionaries.payment.services;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 17.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class PaymentServiceCategory  implements Serializable
{
	//id услуги
	private Long paymentService;
	//категория
	private CategoryServiceType category;

	public Long getPaymentService()
	{
		return paymentService;
	}

	public void setPaymentService(Long paymentService)
	{
		this.paymentService = paymentService;
	}

	public CategoryServiceType getCategory()
	{
		return category;
	}

	public void setCategory(CategoryServiceType category)
	{
		this.category = category;
	}
}
