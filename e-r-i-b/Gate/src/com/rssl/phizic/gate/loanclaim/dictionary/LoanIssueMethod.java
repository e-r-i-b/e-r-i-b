package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Puzikov
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * «апись справочника " ак вы хотите получить кредит"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "creditMethodObtaining")
public class LoanIssueMethod extends AbstractDictionaryEntry
{
	/**
	 * “ип продукта получени€ кредита
	 */
	public enum LoanProductType
	{
		CARD,
		DEPOSIT,
		CURRENT_ACCOUNT
	}
	/**
	 *  признак, доступен к использованию в за€вке (never null)
	 */
	@XmlElement(name = "availableInClaim", required = true)
	private boolean availableInClaim;

	/**
	 * кредит будет выдан на новый ресурс (карту, счет, вклад)
	 */
	@XmlElement(name = "newProductForLoan", required = true)
	private boolean newProductForLoan;

	/**
	 * “ип продукта получени€ кредита
	 */
	@XmlElement(name = "productForLoan", required = true)
	private LoanProductType productForLoan;

	public boolean isAvailableInClaim()
	{
		return availableInClaim;
	}

	public void setAvailableInClaim(boolean availableInClaim)
	{
		this.availableInClaim = availableInClaim;
	}

	public boolean isNewProductForLoan()
	{
		return newProductForLoan;
	}

	public void setNewProductForLoan(boolean newProductForLoan)
	{
		this.newProductForLoan = newProductForLoan;
	}

	public LoanProductType getProductForLoan()
	{
		return productForLoan;
	}

	public void setProductForLoan(LoanProductType productForLoan)
	{
		this.productForLoan = productForLoan;
	}
}
