package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * �������� ������� ������� � ����� � �������� ������
 * @author Jatsky
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PFRHistoryFullOperation extends EditDocumentOperationBase implements Serializable
{

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();

		try
		{
				List<? extends PaymentAbilityERL> list = PersonContext.getPersonDataProvider().getPersonData().getAccounts(new PensionPlusNotClosedNotBlockedAccountFilter());
				result.addAll(list);
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("�� �������� �������� ���������� �� ������", ex);
			getMessageCollector().addInactiveSystemError("���� ������ � ����� �������� ����������. " + ex.getMessage());
		}

		try
		{
				List<? extends PaymentAbilityERL> list = PersonContext.getPersonDataProvider().getPersonData().getCards(new MaestroSocialNotClosedNotBlockedCardWithPrimaryAccountFilter());
				result.addAll(list);
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("�� �������� �������� ���������� �� ������", ex);
			getMessageCollector().addInactiveSystemError("���� ����� �������� ����������. " + ex.getMessage());
		}
		return result;
	}

}
