package com.rssl.phizic.business.loans;

/**
 * Общая информация для описания кредитных продуктов.
 * Преобразования для отображения списка КП и калькулятор.
 *
 * @author gladishev
 * @ created 17.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanGlobal
{
	private String key;
	private String listTransformation;
    private String calculatorTransformation;

    public String getKey()
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
}
