package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

/**
 * ������� ����������� ����������� ����� �������� � ������ "��������� ����"
 * @author niculichev
 * @ created 04.02.2012
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
//��� ����������� ������� ���� ��������������������, ��� ������� ������� �� �����
public class CheckChargeOffCardToMobilBankHandler extends BusinessDocumentHandlerBase<AbstractLongOfferDocument>
{
	protected static final String PROHIBITION_MESSAGE_PARAM_NAME        = "prohibition-message";
	protected static final String WARNING_MESSAGE_PARAM_NAME            = "warning-message";

	public void process(AbstractLongOfferDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		PaymentAbilityERL resourceLink = document.getChargeOffResourceLink();
		if (resourceLink == null)
		{
		    log.error("�� ������ ����-��-�������� �������� ��� ������� � id:" + document.getId());
			throw new DocumentLogicException("�� �� ������ ��������� ������ ��������, ��� ��� ���������� �� ����� �� ������� � �������.");
		}

		if (!(resourceLink instanceof CardLink))
		{
			throw new DocumentException("�������� �������� �� �������� ������");
		}

		if (MobileBankManager.hasAnyMB(resourceLink.getNumber()))
		{
			document.setConnectChargeOffToMobilBank(Boolean.TRUE);
			return;
		}

		if (isNeedShowWarning(document))
		{
			showMessage(document, stateMachineEvent);
			return;
		}

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (Application.PhizIA == applicationConfig.getLoginContextApplication())
		{
			throw new DocumentLogicException(getParameter(PROHIBITION_MESSAGE_PARAM_NAME));
		}
	}

	protected void showMessage(AbstractLongOfferDocument document, StateMachineEvent stateMachineEvent)
	{
		String message = getParameter(WARNING_MESSAGE_PARAM_NAME);
		if (StringHelper.isNotEmpty(message))
		{
			stateMachineEvent.addMessage(message);
		}
	}

	private boolean isNeedShowWarning(AbstractLongOfferDocument document) throws DocumentException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (Application.PhizIC == applicationConfig.getLoginContextApplication())
		{
			return true;
		}

		if (PersonContext.isAvailable())
		{
			return PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD;
		}

		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			return document.getOwner().getLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
