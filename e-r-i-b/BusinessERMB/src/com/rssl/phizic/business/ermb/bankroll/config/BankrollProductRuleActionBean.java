package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.*;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "Action")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductRuleActionBean
{
	@XmlElement(name = "tariff", required = true)
	private Tariff tariff;

	static class Tariff
	{
		@XmlElement(name = "id")
		private Long id;

		@XmlElement(name = "code")
		private String code;

		Tariff()
		{
		}

		Tariff(String code)
		{
			this.code = code;
		}

		Tariff(Long id)
		{
			this.id = id;
		}

		Long getId()
		{
			return id;
		}

		void setId(Long id)
		{
			this.id = id;
		}

		String getCode()
		{
			return code;
		}

		void setCode(String code)
		{
			this.code = code;
		}
	}

	@XmlElement(name = "bankroll-product-defaults", required = true)
	private BankrollProductDefaultsBean productDefaults;

	Tariff getTariff()
	{
		return tariff;
	}

	void setTariff(Tariff tariff)
	{
		this.tariff = tariff;
	}

	BankrollProductDefaultsBean getProductDefaults()
	{
		return productDefaults;
	}

	void setProductDefaults(BankrollProductDefaultsBean productDefaults)
	{
		this.productDefaults = productDefaults;
	}
}
