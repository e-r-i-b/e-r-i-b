package com.rssl.phizic.web.atm.ext.sbrf.accounts;

import com.rssl.phizic.gate.bankroll.TransactionBase;

import java.util.List;

/**
 * @author sergunin
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountAbstractForm extends ShowAccountInfoForm
{
    private List<TransactionBase> transactions;

    public List<TransactionBase> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<TransactionBase> transactions)
    {
        this.transactions = transactions;
    }
}