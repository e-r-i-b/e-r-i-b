package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.MockHelper;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 13.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * В дополнении к AccountListSource добавлена работа с AccountInfo.
 */
public class AccountListSourceWithInfo extends AccountListSource
{
    public AccountListSourceWithInfo(EntityListDefinition definition)
    {
        super(definition);
    }

    public AccountListSourceWithInfo(EntityListDefinition definition, AccountFilter accountFilter)
    {
        super(definition, accountFilter);
    }

    public AccountListSourceWithInfo(EntityListDefinition definition, Map parameters) throws BusinessException
    {
	    super(definition, parameters);
    }
	
	protected void addInfo(EntityListBuilder builder, AccountLink accountLink)
	{
		Account account = accountLink.getAccount();
		if(!MockHelper.isMockObject(account))
		{
			builder.appentField("contractNumber",          account.getAgreementNumber());
			builder.appentField("interestTransferCard",    account.getInterestTransferCard());
			builder.appentField("interestTransferAccount", account.getInterestTransferAccount());
		}
	}
}
