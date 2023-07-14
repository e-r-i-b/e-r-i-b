package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.claims.AccountOpeningFromCardClaim;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 03.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ ����� �������� � ������������� ���������� ����� (���)
 */
public class AccountOpeningClaimFromCardSender extends AccountOpeningClaimFromAccountSender
{
	public AccountOpeningClaimFromCardSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	/**
	 * ������������ ������ �� ���������� ���������.
	 * ��� �������� ������� - DepToNewDepAddRq
	 * @param document ������ � ���������
	 * @return ������
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		AbstractTransfer transfer = (AbstractTransfer) document;

		CardToNewDepAddRq_Type cardToNewRq = new CardToNewDepAddRq_Type();

		cardToNewRq.setRqUID(PaymentsRequestHelper.generateUUID());
		cardToNewRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		cardToNewRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		cardToNewRq.setSPName(SPName_Type.BP_ERIB);
		cardToNewRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
		cardToNewRq.setXferInfo(createBody(transfer, true));
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setCardToNewDepAddRq(cardToNewRq);
		return ifxRq;
	}

	protected void setFromSourceInfo(AbstractTransfer transfer, XferInfo_Type xFerInfo) throws GateException, GateLogicException
	{
		AccountOpeningFromCardClaim cardTransfer = (AccountOpeningFromCardClaim)transfer;
		Client owner = getBusinessOwner(transfer);
		Card card = getCard(owner, cardTransfer.getChargeOffCard(), transfer.getOffice());
		CardAcctId_Type cardAcctId = paymentsRequestHelper.createCardAcctId(card, owner, cardTransfer.getChargeOffCardExpireDate(), false, false);
		xFerInfo.setCardAcctIdFrom(cardAcctId);
	}

	protected String parseAccountNumber(IFXRs_Type ifxRs)
	{
		return  isXferOperStatusInfoRs(ifxRs) ?
				ifxRs.getXferOperStatusInfoRs().getTCDO().getAgreemtInfo().getDepInfo().getAcctId() :
				ifxRs.getCardToNewDepAddRs().getAgreemtInfo().getDepInfo().getAcctId();
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getCardToNewDepAddRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getCardToNewDepAddRs().getRqUID();
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		// ��������� ������ ����� �������� � ����� ����������
		return false;
	}

	protected void fillCommissionWriteDownOperation(AbstractTransfer transfer, XferInfo_Type xferInfo)
	{
		//��� �������� ����� � ����� ������� ������� �� ������������.
	}

	protected void fillCommissions(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		//��� �������� ����� � ����� ������� ������� �� ������������.
	}

	@Override
	protected Class getMainInfoClass()
	{
		return CardToNewDepAddRs_Type.class;
	}

	protected void fillClaimData(GateDocument document, IFXRs_Type ifxRs)
	{
		//��������� ������ ��������� ������
		super.fillClaimData(document, ifxRs);
		//��������� ���� �����������
		AccountOpeningFromCardClaim claim = (AccountOpeningFromCardClaim) document;
		claim.setAuthorizeCode(getAuthorizeCode(ifxRs));
	}

	protected String getAuthorizeCode(IFXRs_Type ifxRs)
	{
		long authorizationCode;
		if (isXferOperStatusInfoRs(ifxRs))
			authorizationCode = ifxRs.getXferOperStatusInfoRs().getTCDO().getCardAuthorization().getAuthorizationCode();
		else
			authorizationCode = ifxRs.getCardToNewDepAddRs().getCardAuthorization().getAuthorizationCode();
		return String.valueOf(authorizationCode);
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getCardToNewDepAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		return parseCalendar(ifxRs.getCardToNewDepAddRs().getRqTm());
	}

	protected OperName_Type getOperName()
	{
		return OperName_Type.TCDO;
	}

	protected String getSystemId(AccountOpeningClaim claim) throws GateLogicException, GateException
	{
		AccountOpeningFromCardClaim cardTransfer = (AccountOpeningFromCardClaim)claim;
		Client owner = getBusinessOwner(cardTransfer);
		Card card = getCard(owner, cardTransfer.getChargeOffCard(), claim.getOffice());
		CardAcctId_Type cardAcctId = paymentsRequestHelper.createCardAcctId(card, owner, cardTransfer.getChargeOffCardExpireDate(), false, false);
		return cardAcctId.getSystemId();
	}

}
