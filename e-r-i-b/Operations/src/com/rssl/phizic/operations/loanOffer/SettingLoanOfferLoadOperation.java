package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanOffer.SettingLoanOfferLoad;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanOfferLoadOperation   extends SettingLoadOperation
{
	public void init() throws BusinessException, BusinessLogicException
	{
			// если в бд уже есть запись настроек, то будем править ее (у нас может быть только одна запись)
			List<SettingLoanOfferLoad> settings = simpleService.getAll(SettingLoanOfferLoad.class);
			if (settings == null || settings.isEmpty())
				setting = new SettingLoanOfferLoad();
			else
				setting = settings.get(0);
	}

}
