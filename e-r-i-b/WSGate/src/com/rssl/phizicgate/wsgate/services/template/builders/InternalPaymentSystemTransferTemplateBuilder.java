package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.business.documents.templates.impl.activity.FailTransferTemplateInformer;
import com.rssl.phizic.business.documents.templates.impl.activity.InternalPaymentSystemTransferTemplateInformer;
import com.rssl.phizic.business.dictionaries.providers.wrappers.ServiceProviderShortcut;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;

/**
 * ������ �������� ����������� ���������� �����
 *
 * @author khudyakov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public class InternalPaymentSystemTransferTemplateBuilder<T extends PaymentSystemTransferTemplate> extends PaymentSystemTransferTemplateBuilderBase<T>
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private ServiceProviderShortcut shortcut;

	@Override
	protected void setBaseData(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		//���������� ����� ������
		super.setBaseData(template, generated);

		//���������� ���������� �����
		shortcut = getProviderShortcut(generated);

		template.setMultiBlockReceiverPointCode(generated.getMultiBlockReceiverPointCode());
		template.setReceiverInternalId(getReceiverId(shortcut, generated));
	}

	private ServiceProviderShortcut getProviderShortcut(GateTemplate generated) throws GateException
	{
		try
		{
			String uuid = generated.getMultiBlockReceiverPointCode();
			if (StringHelper.isEmpty(uuid))
			{
				return null;
			}

			return serviceProviderService.getShortcutByUUID(uuid);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	@Override
	protected ActivityInfo getActivityInfo(T template)
	{
		try
		{
			return new InternalPaymentSystemTransferTemplateInformer(template, shortcut).inform();
		}
		catch (Exception e)
		{
			log.error("��� ������� ���������� ������� id = " + template.getId() + " ��������� ������.", e);
			return new FailTransferTemplateInformer().inform();
		}
	}

	private Long getReceiverId(ServiceProviderShortcut shortcut, GateTemplate generated) throws GateException
	{
		if (shortcut == null)
		{
			ApplicationAutoRefreshConfig applicationConfig = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
			log.error(String.format(Constants.NOT_FOUND_PROVIDER_ERROR_MESSAGE, generated.getMultiBlockReceiverPointCode(), applicationConfig.getNodeNumber(), generated.getId()));

			return -1L;
		}
		return shortcut.getId();
	}
}
