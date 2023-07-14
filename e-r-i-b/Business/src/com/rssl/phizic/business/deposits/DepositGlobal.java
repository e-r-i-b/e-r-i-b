package com.rssl.phizic.business.deposits;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.utils.xml.XmlFileReader;

/**
 * Общая информация для описания депозитных продуктов.
 * Преобразования для отображения списка ДП и калькулятор.
 *
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositGlobal implements MultiBlockDictionaryRecord
{
	private String key;
	private String listTransformation;
    private String calculatorTransformation;
	private String adminListTransformation;
	private String adminEditTransformation;
	private String defaultDetailsTransformation;
	private String mobileListTransformation;
	private String mobileDetailsTransformation;
	private String visibilityDetailsTransformation;
	private String depositPercentRateTransformation;

	String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getListTransformation()
	{
		return listTransformation;
	}

	public void setListTransformation(String listTransformation)
	{
		this.listTransformation = listTransformation;
	}

    public String getCalculatorTransformation()
    {
        return calculatorTransformation;
    }

    public void setCalculatorTransformation(String calculatorTransformation)
    {
        this.calculatorTransformation = calculatorTransformation;
    }

	public String getAdminListTransformation()
	{
		return adminListTransformation;
	}

	public void setAdminListTransformation(String adminListTransformation)
	{
		this.adminListTransformation = adminListTransformation;
	}

	public String getAdminEditTransformation()
	{
		return adminEditTransformation;
	}

	public void setAdminEditTransformation(String adminEditTransformation)
	{
		this.adminEditTransformation = adminEditTransformation;
	}

	public String getDefaultDetailsTransformation()
	{
		return defaultDetailsTransformation;
	}

	public void setDefaultDetailsTransformation(String defaultDetailsTransformation)
	{
		this.defaultDetailsTransformation = defaultDetailsTransformation;
	}

	public String getMobileListTransformation()
	{
		return mobileListTransformation;
	}

	public void setMobileListTransformation(String mobileListTransformation)
	{
		this.mobileListTransformation = mobileListTransformation;
	}

	public String getMobileDetailsTransformation()
	{
		return mobileDetailsTransformation;
	}

	public void setMobileDetailsTransformation(String mobileDetailsTransformation)
	{
		this.mobileDetailsTransformation = mobileDetailsTransformation;
	}

	public String getVisibilityDetailsTransformation()
	{
		return visibilityDetailsTransformation;
	}

	public void setVisibilityDetailsTransformation(String visibilityDetailsTransformation)
	{
		this.visibilityDetailsTransformation = visibilityDetailsTransformation;
	}

	public void setDepositPercentRateTransformation(String depositPercentRateTransformation)
	{
		this.depositPercentRateTransformation = depositPercentRateTransformation;
	}

	public String getDepositPercentRateTransformation()
	{
		return depositPercentRateTransformation;
	}

	public String getMultiBlockRecordId()
	{
		return key;
	}
}