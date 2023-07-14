package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;

/**
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefReceiverInfoServiceImpl extends AbstractService implements BackRefReceiverInfoService
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public BackRefReceiverInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public BusinessRecipientInfo getRecipientInfo(String pointCode, String serviceCode) throws GateException, GateLogicException
	{
		try
		{
			BillingServiceProvider recipient = (BillingServiceProvider) providerService.findBySynchKey(serviceCode + "@" + pointCode);
			if (recipient == null)
				return null;

			BusinessRecipientInfoImpl info = new BusinessRecipientInfoImpl();

			info.setMaxCommissionAmount(recipient.getMaxComissionAmount());
			info.setMinCommissionAmount(recipient.getMinComissionAmount());
			info.setCommissionRate(recipient.getComissionRate());
			info.setDeptAvailable(recipient.isDeptAvailable());
			info.setPropsOnline(recipient.isPropsOnline());

			//Recipient
			info.setSynchKey(recipient.getSynchKey());
			info.setService(recipient.getService());
			info.setName(recipient.getName());
			info.setDescription(recipient.getDescription());
			info.setMain(recipient.isMain());

			//RecipientInfo
			info.setINN(recipient.getINN());
			info.setKPP(recipient.getKPP());
			info.setAccount(recipient.getAccount());
			info.setBank(recipient.getBank());
			info.setTransitAccount(recipient.getTransitAccount());

			return info;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
