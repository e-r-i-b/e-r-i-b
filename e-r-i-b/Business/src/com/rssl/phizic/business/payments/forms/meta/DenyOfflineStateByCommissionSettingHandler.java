package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author vagin
 * @ created 18.03.14
 * @ $Author$
 * @ $Revision$
 * ������� ������������ � ��������� �������� ����:
 * 1) ��������� ��������(�����������) ������������� ������� ��������(��� ������� � ��������� �� � � ������ �������)
 * 2) � (��������������� ����� ��� ������� �������� ��������� � ������ OFFLINE_DELAYED)
 */
public class DenyOfflineStateByCommissionSettingHandler extends ReceptionTimeDelayedStateHandler
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AttributableBase))
			throw new DocumentException("������� ������������ ������. ��������� AttributableBase.");

		BackRefCommissionTBSettingService commissionSettingService = GateSingleton.getFactory().service(BackRefCommissionTBSettingService.class);
		ConvertableToGateDocument convertable = (ConvertableToGateDocument) document;
		try
		{
			GateDocument gateDocument = convertable.asGateDocument();
			//���� ������������� ������������ ������ ������� �������� � ��� ��� ������� ���� ���������, � ������� ������� ��������� ����������� ��������.
			if(commissionSettingService.isCalcCommissionSupport(gateDocument))
			{
		        super.process(document,stateMachineEvent);
				//���� ������� �������� ��������� � ������ OFFLINE_DELAYED
				ExtendedAttribute offlineAttribute = ((AttributableBase) document).getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
				if (offlineAttribute != null && (Boolean) offlineAttribute.getValue())
					throw new DocumentLogicException(DEFAULT_ERROR_MESSAGE);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
