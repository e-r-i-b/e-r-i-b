package com.rssl.phizic.web.common.mobile.accounts;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.web.common.client.accounts.ShowAccountInfoForm;

import java.util.List;

/**
 * Детальная информация по вкладу
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountInfoMobileForm extends ShowAccountInfoForm
{
    //in
    private String accountName;

    private List<AutoSubscriptionLink> moneyBoxes;

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public List<AutoSubscriptionLink> getMoneyBoxes()
    {
        return moneyBoxes;
    }

    public void setMoneyBoxes(List<AutoSubscriptionLink> moneyBoxes)
    {
        this.moneyBoxes = moneyBoxes;
    }
}