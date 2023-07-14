package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentFieldsIndicateException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.payments.CreateP2PAutoSubcriptionCommissionCalculator;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateFieldIncorrectException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Krenev
 * @ created 11.05.2007
 * @ $Author$
 * @ $Revision$
 */
public class DefaultCommissionSaveHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof ConvertableToGateDocument))
			throw new DocumentException("document должен реализовывать ConvertableToGateDocument");

		ExtendedAttribute offlineAttribute = ((AttributableBase) document).getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
		if (offlineAttribute != null && (Boolean) offlineAttribute.getValue())
			return;

		ConvertableToGateDocument convertable = (ConvertableToGateDocument) document;
		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);

		BackRefCommissionTBSettingService commissionSettingService = GateSingleton.getFactory().service(BackRefCommissionTBSettingService.class);
		try
		{
			//если подразделение поддерживает онлайн рассчет комиссии в цод для данного типа документа, механизм другой. CommissionCalculator не используем.
			if(commissionSettingService.isCalcCommissionSupport(convertable.asGateDocument()))
		    	return;
			documentService.calcCommission(convertable.asGateDocument());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e.getMessage(), e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e.getMessage(), e);
		}
		catch (GateFieldIncorrectException e)
		{
			throw new DocumentFieldsIndicateException(e.getFieldMessages(), e.isError(), e.getMessage());
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e.getMessage(), e);
		}
	}
}
