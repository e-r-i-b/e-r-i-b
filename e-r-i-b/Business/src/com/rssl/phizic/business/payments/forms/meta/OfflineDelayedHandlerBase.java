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
 * ������� ������� ��� ����������� ������������� ������� ������
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
			throw new DocumentException("������������ ��� ���������. ��������� AttributableBase.");
		
		innerProcess(document, stateMachineEvent);

		checkOfflineAttribute((AttributableBase) document);
	}

	protected abstract void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException;

	/**
	 * ��������� ������������� ���������� ��������� � ������ ������ �� �������� ��������/����������
	 * @param link - ������ ��������/����������
	 * @param document ��������, ��� �������� ��������� ����������
	 * @return true - �������� ���������� ��������� � offline ������
	 */
	protected boolean checkResource(ExternalResourceLink link, AttributableBase document) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!ESBHelper.isESBSupported(link.getExternalId()))
				return false;

			// �������� �� ����������� �������-��������
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
	 * ��������� ������������� ���������� ��������� � ������ ������ �� ����������� ����
	 * @param document ��������, ��� �������� ��������� ���������� ����
	 * @return true - �������� ���������� ��������� � offline ������
	 */
	protected boolean checkESB(AttributableBase document) throws DocumentException
	{
		return checkSystemByUUID(ExternalSystemHelper.getESBSystemCode(), document);
	}

	/**
	 * ��������� ������� �������, ������������� ������������� ��������� (������ ��� ������ ����������)
	 * @param document ��������, ��� �������� ��������� ����������
	 * @return true - �������� ���������� ��������� � offline ������
	 * @throws DocumentException
	 */
	protected boolean checkDocumentOffice(GateExecutableDocument document) throws DocumentException
	{
		return checkSystemByUUID(IDHelper.restoreRouteInfo(document.getOffice().getSynchKey().toString()), document);
	}

	/**
	 * ��������� ������� ������� �� ������������� ���������� ��������� � ������� ������
	 * @param UUID ��� ������� �������
	 * @param document ��������, ��� �������� ��������� ����������
	 * @return true - �������� ���������� ��������� � offline ������
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
		//���� �� ����� ������� �� ��� ���������, �� ������ ��� � false
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
