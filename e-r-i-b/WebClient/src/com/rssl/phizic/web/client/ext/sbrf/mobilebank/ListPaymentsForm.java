package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

/**
 * @author Erkin
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class ListPaymentsForm extends MobileBankFormBase
{
	private String selectedRecipientCode;

	private String selectedPayerCode;

	///////////////////////////////////////////////////////////////////////////

	public String getSelectedRecipientCode()
	{
		return selectedRecipientCode;
	}

	public void setSelectedRecipientCode(String selectedRecipientCode)
	{
		this.selectedRecipientCode = selectedRecipientCode;
	}

	public String getSelectedPayerCode()
	{
		return selectedPayerCode;
	}

	public void setSelectedPayerCode(String selectedPayerCode)
	{
		this.selectedPayerCode = selectedPayerCode;
	}
}