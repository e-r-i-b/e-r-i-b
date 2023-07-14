package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bannedAccount.AccountBanType;
import com.rssl.phizic.business.bannedAccount.BannedAccount;
import com.rssl.phizic.business.bannedAccount.BannedAccountService;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author: vagin
 * @ created: 03.02.2012
 * @ $Author$
 * @ $Revision$
 * Проверка счета и БИКа по заведенным в базе маскам запрещенных счетов для переводов.
 */
public class BannedAccountListValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_BIC = "receiverBIC";
	public static final String FIELD_RECEIVER_ACCOUNT = "receiverAccount";
	public static final String BAN_TYPE ="banType";
	public static final BannedAccountService bannedAccountService = new BannedAccountService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String bic = (String)retrieveFieldValue(FIELD_BIC, values);
		String account = (String)retrieveFieldValue(FIELD_RECEIVER_ACCOUNT, values);
		AccountBanType banType = AccountBanType.valueOf((getParameter(BAN_TYPE)));
		try
		{
			List<BannedAccount> masks = bannedAccountService.getMaskByNumber(account, banType);
			if (!masks.isEmpty())
			{
				List<String> maskBicList = new ArrayList<String>();
				for(BannedAccount mask:masks)
				{
					maskBicList = mask.getBICListAsList();
					//если задана маска счета без указания биков - запрет на все
					if(maskBicList.size() == 0)
						return false;
					for(String bicMask : maskBicList)
					{
						if(bicMask.equals(bic))
							return false;
					}
				}
			}
			return true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
