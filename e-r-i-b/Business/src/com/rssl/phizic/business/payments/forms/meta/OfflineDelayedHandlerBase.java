package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.ConvertibleToGateDocumentAdapter;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Calendar;

/**
 * Базовый хэндлер для определения недоступности внешних систем
 * @author Pankin
 * @ created 12.02.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class OfflineDelayedHandlerBase extends BusinessDocumentHandlerBase<BusinessDocument>
{
	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AttributableBase))
			throw new DocumentException("Некорректный тип документа. Ожидается AttributableBase.");
		
		innerProcess(document, stateMachineEvent);

		checkOfflineAttribute((AttributableBase) document);
	}

	protected abstract void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException;

	/**
	 * Проверяем необходимость выполнения документа в офлайн режиме по ресурсам списания/зачисления
	 * @param link - ресурс списания/зачисления
	 * @param document документ, для которого проверяем активность
	 * @return true - документ необходимо выполнять в offline режиме
	 */
	protected boolean checkResource(ExternalResourceLink link, AttributableBase document) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!ESBHelper.isESBSupported(link.getExternalId()))
				return false;

			// проверка на доступность оффлайн-платежей
			if (link instanceof CardLink)
			{
				AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
				return checkSystemByUUID(config.getCardWayAdapterCode(), document);
			}
			else
			{
				return checkSystemByUUID(ESBHelper.parseSystemId(link.getExternalId()), document);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Проверяем необходимость выполнения документа в офлайн режиме по доступности шины
	 * @param document документ, для которого проверяем активность шины
	 * @return true - документ необходимо выполнять в offline режиме
	 */
	protected boolean checkESB(AttributableBase document) throws DocumentException
	{
		return checkSystemByUUID(ExternalSystemHelper.getESBSystemCode(), document);
	}

	/**
	 * Проверяем внешнюю систему, обслуживающую подразделение документа (обычно ЦОД прямой интеграции)
	 * @param document документ, для которого проверяем активность
	 * @return true - документ необходимо выполнять в offline режиме
	 * @throws DocumentException
	 */
	protected boolean checkDocumentOffice(GateExecutableDocument document) throws DocumentException
	{
		return checkSystemByUUID(IDHelper.restoreRouteInfo(document.getOffice().getSynchKey().toString()), document);
	}

	/**
	 * Проверяем внешнюю систему на необходимость выполнения документа в оффлайн режиме
	 * @param UUID код внешней системы
	 * @param document документ, для которого проверяем активность
	 * @return true - документ необходимо выполнять в offline режиме
	 * @throws DocumentException
	 */
	protected boolean checkSystemByUUID(String UUID, AttributableBase document) throws DocumentException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
		try
		{
			Calendar technoBreakToDate = externalSystemGateService.getTechnoBreakToDateWithAllowPayments(UUID);
			if (technoBreakToDate != null)
			{
				document.setAttributeValue(ExtendedAttribute.DATE_TIME_TYPE, BusinessDocumentBase.OFFLINE_DELAYED_TO_DATE_ATTRIBUTE_NAME, technoBreakToDate);
				document.setAttributeValue(ExtendedAttribute.BOOLEAN_TYPE, BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME, true);
				return true;
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}

		return false;
	}

	protected void checkOfflineAttribute(AttributableBase document)
	{
		//если до этого признак не был выставлен, то ставим его в false
		ExtendedAttribute offlineAttribute = document.getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
		if (offlineAttribute == null)
			document.setAttributeValue(ExtendedAttribute.BOOLEAN_TYPE, BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME, false);
	}

	protected GateDocument getGateDocument(BusinessDocument document) throws DocumentException
	{
		try
		{
			return new ConvertibleToGateDocumentAdapter(document).asGateDocument();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
