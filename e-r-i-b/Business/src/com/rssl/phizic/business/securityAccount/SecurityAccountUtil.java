package com.rssl.phizic.business.securityAccount;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author lukina
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SecurityAccountUtil
{
	/**
	 * Вычисляем размер вклада, оформленного сертификатом
	 * @param account   - сберегатеьный сертификат
	 * @return  размер вклада, оформленного сертификатом
	 */
	public static Money getSecurityAmount(SecurityAccount account)
	{
		if (account.getNominalAmount() == null || account.getIncomeAmt() == null)
				return null;
		if (account.getTermStartDt().compareTo(DateHelper.getCurrentDate()) <= 0){
			return account.getNominalAmount().add(account.getIncomeAmt());
		}
		return account.getNominalAmount();
	}
}
