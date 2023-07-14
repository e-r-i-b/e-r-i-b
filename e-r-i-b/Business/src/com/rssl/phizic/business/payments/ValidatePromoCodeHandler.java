package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.clientPromoCodes.CloseReason;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * �������� �� ���������� �����-����
 * @author EgorovaA
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 */
public class ValidatePromoCodeHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + (document).getId() + " (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim claim = (AccountOpeningClaim) document;

		String promoCodeSegment = claim.getSegment();
		if (StringHelper.isEmpty(promoCodeSegment))
			return;

		try
		{
			Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
			ClientPromoCodeService clientPromoCodesService = new ClientPromoCodeService();
			List<ClientPromoCode> clientPromoCodes = clientPromoCodesService.getAllClientPromoCodes(loginId);

			for (ClientPromoCode promoCode : clientPromoCodes)
			{
				PromoCodeDeposit promoCodeDeposit = promoCode.getPromoCodeDeposit();
				if (Long.valueOf(promoCodeSegment).equals(promoCodeDeposit.getCodeS()))
				{
					//���� �����-��� �� ��������, ���������� ������
					if (promoCode.getCloseReason() != null)
						throw new BusinessLogicException(getErrorMessage(promoCode));
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	private String getErrorMessage(ClientPromoCode promoCode)
	{
		CloseReason closeReason = promoCode.getCloseReason();
		if (closeReason.equals(CloseReason.ACTION_PROMO_CODE_EXPIRED))
			return "����� ���� �������� �����-����. ����������, ������������ � ��������� ������ � �������� ����� ������ �� �����";
		if (closeReason.equals(CloseReason.REACH_USE_LIMIT))
			return "������� ���������� ���, ������� ����� ���� ����������� �����-���. ����������, ������������ � ��������� ������ � �������� ����� ������ �� �����";
		if (closeReason.equals(CloseReason.DELETED_BY_CLIENT))
			return "�� ������� �����-���, �������� ��������������� ������� ������. ����������, ������������ � ��������� ������ � �������� ����� ������ �� �����";
		return null;
	}
}
