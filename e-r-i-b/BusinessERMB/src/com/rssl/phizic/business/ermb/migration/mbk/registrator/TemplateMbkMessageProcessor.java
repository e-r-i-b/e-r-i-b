package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.person.Person;

import java.util.List;

/**
 * ���������� ��������� �� ���������� ��� �������� �������� �� � ����
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class TemplateMbkMessageProcessor extends MbkMessageProcessorBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public ProcessResult process(MBKRegistration mbkMessage) throws SystemException, BusinessLogicException
	{
		ErmbProfileImpl profile = findByFioDulDrPilotTb(new MigrationClient(mbkMessage));
		if (profile == null)
		{
			return createErrorResult(mbkMessage, false, "������ �� ������/������. �� ������ ������� ������� �� �����, ��������� ��� ���������");
		}

		BillingServiceProvider provider = providerService.findByMobileBankCode(mbkMessage.getPp());
		if (provider == null)
		{
			return createErrorResult(mbkMessage, false, "������ �� ������/������. �� ������ ��������� ���������� �������");
		}

		updateTemplate(profile.getPerson(), provider, mbkMessage.getPaymentCardNumber(), mbkMessage.getIpList());

		return createSuccessResult(mbkMessage);
	}

	protected abstract void updateTemplate(Person person, BillingServiceProvider provider, String paymentCardNumber, List<String> payerCodes) throws BusinessException, BusinessLogicException;
}
