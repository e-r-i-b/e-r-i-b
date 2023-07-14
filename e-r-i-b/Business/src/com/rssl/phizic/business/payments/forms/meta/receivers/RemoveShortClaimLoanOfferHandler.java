package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.notifications.service.NotificationService;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Moshenko
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 * ������� ���� ���������� ����������� �� �������(�� ShortLoanClaim), � ��� ����������.
 */
public class RemoveShortClaimLoanOfferHandler extends BusinessDocumentHandlerBase
{
	private static final NotificationService notificationService = new NotificationService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		try
		{
			if (gateDocument instanceof ShortLoanClaim)
			{
				// ������� ����������� �� ������� (���� ����)
				ShortLoanClaim shortLoanClaim = (ShortLoanClaim) document;
				String offerIdAsString = shortLoanClaim.getLoanOfferId();
				if (StringHelper.isNotEmpty(offerIdAsString))
				{
					OfferId offerId = OfferId.fromString(offerIdAsString);
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					personData.updateLoanOfferAsUsed(offerId);
				}
			}
		}
		catch (Exception e)
		{
			// ��� ����� ���������� ����� ������ � ���
			// �.�. ������ ������� ����������. � ��� ���� ���������� �������.
			log.error("������ ��� �������� ������� � ��������������� "+ gateDocument.getId() + "� ��������� ��������", e);
		}
	}
}
