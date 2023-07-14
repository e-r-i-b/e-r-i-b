package com.rssl.phizic.operations.ext.sbrf.loans;

import com.rssl.phizic.common.types.Money;

/**
 * @author sergunin
 * @ created 25.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class LoanAccountInfo
{
	private String name;
    private Money amount;
    private String nameToPay;

    public LoanAccountInfo() {}

    public LoanAccountInfo(String name, Money amount, String nameToPay)
    {
        this.name = name;
        this.amount = amount;
        this.nameToPay = nameToPay;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Money getAmount()
    {
        return amount;
    }

    public void setAmount(Money amount)
    {
        this.amount = amount;
    }

    public String getNameToPay()
    {
        return nameToPay;
    }

    public void setNameToPay(String nameToPay)
    {
        this.nameToPay = nameToPay;
    }
}
