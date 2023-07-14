package com.rssl.phizic.web.common.socialApi.accounts;

import com.rssl.phizic.web.common.client.accounts.ShowAccountInfoForm;

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

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }
}