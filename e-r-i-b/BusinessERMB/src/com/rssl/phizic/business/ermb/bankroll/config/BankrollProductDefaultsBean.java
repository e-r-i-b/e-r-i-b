package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.*;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "bankroll-product-defaults")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductDefaultsBean
{
	@XmlElement(name = "card", required = true)
	private ProductDefaultParameters cardDefault;

	@XmlElement(name = "deposit", required = true)
	private ProductDefaultParameters depositDefault;

	@XmlElement(name = "loan", required = true)
	private ProductDefaultParameters loanDefault;

	@XmlElement(name = "new-product", required = true)
	private ProductDefaultParameters newProductDefault;

	@XmlType(name = "ProductDefaultParametersType")
	@XmlAccessorType(XmlAccessType.NONE)
	static class ProductDefaultParameters
	{
		@XmlElement(name = "visibility", required = true)
		private boolean visibility;

		@XmlElement(name = "notification", required = true)
		private boolean notification;

		ProductDefaultParameters()
		{
		}

		ProductDefaultParameters(boolean visibility, boolean notification)
		{
			this.visibility = visibility;
			this.notification = notification;
		}

		boolean isVisibility()
		{
			return visibility;
		}

		void setVisibility(boolean visibility)
		{
			this.visibility = visibility;
		}

		boolean isNotification()
		{
			return notification;
		}

		void setNotification(boolean notification)
		{
			this.notification = notification;
		}
	}

	ProductDefaultParameters getCardDefault()
	{
		return cardDefault;
	}

	void setCardDefault(ProductDefaultParameters cardDefault)
	{
		this.cardDefault = cardDefault;
	}

	ProductDefaultParameters getDepositDefault()
	{
		return depositDefault;
	}

	void setDepositDefault(ProductDefaultParameters depositDefault)
	{
		this.depositDefault = depositDefault;
	}

	ProductDefaultParameters getLoanDefault()
	{
		return loanDefault;
	}

	void setLoanDefault(ProductDefaultParameters loanDefault)
	{
		this.loanDefault = loanDefault;
	}

	ProductDefaultParameters getNewProductDefault()
	{
		return newProductDefault;
	}

	void setNewProductDefault(ProductDefaultParameters newProductDefault)
	{
		this.newProductDefault = newProductDefault;
	}
}
