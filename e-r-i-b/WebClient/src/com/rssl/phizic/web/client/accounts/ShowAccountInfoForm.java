package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.web.common.EditFormBase;


/** Created by IntelliJ IDEA. User: Novikov Date: 23.12.2006 Time: 14:06:52 */
public class ShowAccountInfoForm extends EditFormBase
{
    private Account account;

	public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
