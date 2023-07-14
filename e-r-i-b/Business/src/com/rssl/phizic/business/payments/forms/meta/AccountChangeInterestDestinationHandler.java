package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductShortCut;
import com.rssl.phizic.business.promoters.PromoterSessionHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * ��������� ��������� ������� ������ �� ��������� ������� ������ ���������
 * @author Jatsky
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 */

public class AccountChangeInterestDestinationHandler extends AccountOpeniningInitTermsHandler
{
	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "� ����������� ���������� ��������� ��� ������ ��� ������ � ������� ";
	private static final String ACCOUNT_NOT_FOUND = "�� ������ ���� � ������� ";
	//������������� �� ������ ����� �� ��������
	private static final String DEPOSIT_CAPITALIZATION_CHANGED = "���� �������� ����� ������ ����������. ����������, ��������� � ���� ����� ������";
	//����� ��� ������������ ��������� �� ������ �������
	private static final String DEPOSIT_CARD_FOR_PERCENT_IS_CLOSED = "�������� �� ��������� ����� ��� ���������� ��������� ��������������. ����������, ������� ������ ����� ��� ���������� ���������.";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String ACCOUNT_ERROR_STATE = "�� �� ������ ��������� ��������, ������ ��� ��������� ����� ������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountChangeInterestDestinationClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + ((BusinessDocument) document).getId() + " (��������� AccountChangeInterestDestinationClaim)");
		}

		AccountChangeInterestDestinationClaim accountChangeInterestDestinationClaim = (AccountChangeInterestDestinationClaim) document;

		AccountLink accountLink = AccountsUtil.getAccountLink(accountChangeInterestDestinationClaim.getChangePercentDestinationAccountNumber());

		if (accountLink == null)
			throw new DocumentException(ACCOUNT_NOT_FOUND + accountChangeInterestDestinationClaim.getChangePercentDestinationAccountNumber());

		Long depositProductType = accountLink.getAccount().getKind();

		DepositProductShortCut depositProduct;
		try
		{
			depositProduct = depositProductService.findShortByProductId(depositProductType);
			AccountState accountState = accountLink.getAccount().getAccountState();
			if (accountState != AccountState.OPENED && accountState != AccountState.LOST_PASSBOOK && accountState != AccountState.ARRESTED)
				throw new DocumentLogicException (ACCOUNT_ERROR_STATE);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		if (depositProduct == null)
			throw new DocumentException(DEPOSIT_PRODUCT_NOT_FOUND + depositProductType);

		//���� ��� ������ ������� ������ ���������
		if (StringHelper.isNotEmpty(accountChangeInterestDestinationClaim.getInterestDestinationSource()))
		{
			//�������� ����������� ������������� �� ������
			if (!depositProduct.isCapitalization())
				throw new DocumentLogicException(DEPOSIT_CAPITALIZATION_CHANGED);

			//���� "����������� �������� �� ���� ���������� �����"
			if (accountChangeInterestDestinationClaim.getInterestDestinationSource().equals("card"))
			{
				String cardNum = accountChangeInterestDestinationClaim.getPercentCardNumber();
				CardLink cardLink = CardsUtil.getCardLink(cardNum);
				//�������� ��������� �����: ������ ���� ��������
				if (cardLink == null || !CardState.active.equals(cardLink.getCard().getCardState()))
					throw new DocumentLogicException(DEPOSIT_CARD_FOR_PERCENT_IS_CLOSED);
			}
		}

		try
		{
			//���������� ���������� �� ���������� ��� ������
			if (PersonContext.isAvailable())
			{
				String promoterSession = PromoterContext.getShift();
				if (StringHelper.isNotEmpty(promoterSession))
				{
					String promoterId = PromoterContext.getPromoterID();
					if (StringHelper.isNotEmpty(promoterId))
					{
						//���������� �� ������ ����������
						Map<String, Object> promoterSessionInfoMap = PromoterSessionHelper.getPromoterSessionInfoByPromoterID(promoterId);
						String promoterFIO = (String) promoterSessionInfoMap.get(PromoterSessionHelper.PROMOID_FIELD_NAME);
						accountChangeInterestDestinationClaim.setPromoterId(promoterFIO);
					}
				}
				//����� ��� ������� ����������� ������
				String lastLogonCardNumber = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getLastLogonCardNumber();
				accountChangeInterestDestinationClaim.setLogonCardNumber(lastLogonCardNumber);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			throw new DocumentException(e);
		}
	}
}
