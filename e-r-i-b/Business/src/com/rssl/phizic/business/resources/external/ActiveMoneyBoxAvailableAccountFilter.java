package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.PropertyConfig;

import java.util.regex.Pattern;

/**
 * @author vagin
 * @ created 02.09.14
 * @ $Author$
 * @ $Revision$
 *  лиент может выбрать любой счет или вклад, дл€ которых разрешено создание копилок.
 * ¬ качестве счетов Ц копилок могут выступать: вкладные  (с возможностью пополнени€) и сберегательные счета  лиента.
 */
public class ActiveMoneyBoxAvailableAccountFilter implements AccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		Account account = accountLink.getAccount();
		if(account.getCreditAllowed())
			return (account.getAccountState() != AccountState.CLOSED) && (account.getAccountState() != AccountState.ARRESTED) && isAvailable(account.getKind(), account.getSubKind());
		return false;
	}

	private boolean isAvailable(Long kind, Long subKind)
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		String settings = config.getProperty("com.rssl.iccs.moneybox.disallowedAccountKinds");
		String regexp = settings.replace(";","|").replace("?",".").replace("*",".*");
		String accountKey = kind + "/" + subKind;
		return !Pattern.compile(regexp).matcher(accountKey).matches();
	}
}
