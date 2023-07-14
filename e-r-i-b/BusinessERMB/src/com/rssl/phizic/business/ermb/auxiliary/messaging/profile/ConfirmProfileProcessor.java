package com.rssl.phizic.business.ermb.auxiliary.messaging.profile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbUpdateHandler;
import com.rssl.phizic.common.type.ErmbUpdateProfileInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.synchronization.types.ConfirmProfilesRq;

import java.text.ParseException;
import java.util.List;

/**
 * @author Erkin
 * @ created 09.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmProfileProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	public ConfirmProfileProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		ConfirmProfilesRq confirmProfilesRq = xmlRequest.getConfirmProfilesRq();
		SOSMessageHelper messageHelper = new SOSMessageHelper();

		try
		{
			List<ErmbUpdateProfileInfo> profileInfoMsg = messageHelper.getErmbUpdateProfileInfoList(confirmProfilesRq.getUpdatedProfiles());
			// 2. ��������� � ���� �������������� �������
			ErmbUpdateHandler.updateConfirmProfiles(profileInfoMsg);
		}
		catch (ParseException e)
		{
			log.error("������ ������� xml-��������� � ��������������� ����������� ���� ��������", e);
		}
		catch (BusinessException e)
		{
			log.error("������ ���������� ������ ���� ��������", e);
		}
		catch (GateException e)
		{
			log.error("������ ���������� ������ ���� ��������", e);
		}
	}
}
