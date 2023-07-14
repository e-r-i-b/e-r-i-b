package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationShortcut;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ListPaymentsOperation;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lukina
 * @ created 14.03.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class GetAllProfileAction extends ListPaymentsAction
{
	protected boolean updateFormData(ListPaymentsForm form, ListPaymentsOperation operation, HttpServletRequest request)
	{
		try
		{
			List<RegistrationShortcut> registrationShortcuts = operation.getRegistrationShortcuts();
			if (!registrationShortcuts.isEmpty())
			{
				List<RegistrationProfile> registrationProfiles = new LinkedList<RegistrationProfile>();
				for(RegistrationShortcut registrationShortcut : registrationShortcuts)
				{
					String phoneCode = CardsUtil.getMobileBankPhoneCode(registrationShortcut.getPhoneNumber());
					String cardCode = CardsUtil.getMobileBankCardCode(registrationShortcut.getCardNumber());
					registrationProfiles.add(operation.getRegistrationProfile(phoneCode, cardCode));
				}
				form.setRegistrationProfiles(registrationProfiles);
			}
			else
			{
				return false;
			}
		}
		catch (BusinessException e)
		{
			saveError(request, e);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		return true;
	}
}
