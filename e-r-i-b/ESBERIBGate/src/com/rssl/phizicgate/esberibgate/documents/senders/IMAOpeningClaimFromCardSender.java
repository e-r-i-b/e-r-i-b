package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.claims.IMAOpeningClaim;
import com.rssl.phizic.gate.claims.IMAOpeningFromCardClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @author Ismagilova
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Открытие ОМС с переводом на него денежных средств с карты
 */

public class IMAOpeningClaimFromCardSender extends IMAOpeningClaimFromAccountSender
{
	public IMAOpeningClaimFromCardSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	/**
	 * сформировать запрос на исполнение документа.
	 * Заполянеяем заголовки и общие поля.
	 * @param document данные о документе
	 * @return запрос
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		AbstractTransfer transfer = (AbstractTransfer) document;

		CardToNewIMAAddRq_Type cardToNewRq = new CardToNewIMAAddRq_Type();

		cardToNewRq.setRqUID(PaymentsRequestHelper.generateUUID());
		cardToNewRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		cardToNewRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		cardToNewRq.setSPName(SPName_Type.BP_ERIB);
		cardToNewRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
		cardToNewRq.setXferInfo(createBodyXferIMAInfo(transfer));
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setCardToNewIMAAddRq(cardToNewRq);
		return ifxRq;
	}

	private XferIMAInfo_Type createBodyXferIMAInfo(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningFromCardClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningFromCardClaim.");

		IMAOpeningFromCardClaim imaOpeningClaim = (IMAOpeningFromCardClaim) document;
		XferIMAInfo_Type xferIMAInfo = new XferIMAInfo_Type();
		Client owner = getBusinessOwner(imaOpeningClaim);

		Card card = getCard(owner, imaOpeningClaim.getChargeOffCard(), document.getOffice());
		CardAcctId_Type cardAcctId = paymentsRequestHelper.createCardAcctId(card, owner, imaOpeningClaim.getChargeOffCardExpireDate(), false, false);

		xferIMAInfo.setCardAcctIdFrom(cardAcctId);
		xferIMAInfo.setAgreemtInfo(getAgreementInfo(imaOpeningClaim));
		xferIMAInfo.setPurpose(imaOpeningClaim.getGround());
		xferIMAInfo.setCurAmt(imaOpeningClaim.getDestinationAmount().getDecimal());
		xferIMAInfo.setAcctCur(imaOpeningClaim.getDestinationAmount().getCurrency().getCode());
		xferIMAInfo.setCurAmtConv(getCurAmtConvType(imaOpeningClaim));
		xferIMAInfo.setExecute(true);

		return xferIMAInfo;
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return true;
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getCardToNewIMAAddRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return  isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getCardToNewIMAAddRs().getRqUID();
	}

	protected String getSystemId(IMAOpeningClaim claim) throws GateLogicException, GateException
	{
		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
		String cardExternalId = backRefBankrollService.findCardExternalId(claim.getInternalOwnerId(), claim.getChargeOffAccount());
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		return compositeId.getSystemIdActiveSystem();
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TCIO;
	}

	protected String parseAccountNumber(IFXRs_Type ifxRs)
	{
		AgreemtInfoResponse_Type agreemtInfoResponse = isXferOperStatusInfoRs(ifxRs) ? 
				ifxRs.getXferOperStatusInfoRs().getTCIO().getAgreemtInfo() : ifxRs.getCardToNewIMAAddRs().getAgreemtInfo();

		if (agreemtInfoResponse == null)
			return null;

		IMAInfoResponse_Type imaInfoResponse = agreemtInfoResponse.getIMAInfo();
		if (imaInfoResponse == null)
			return null;
		return imaInfoResponse.getAcctId();
	}

	protected void fillClaimData(GateDocument document, IFXRs_Type ifxRs)
	{
		//Установка счета получателя
		super.fillClaimData(document, ifxRs);
		//Установка кода авторизации
		IMAOpeningFromCardClaim claim = (IMAOpeningFromCardClaim) document;
	    claim.setAuthorizeCode(getAuthorizeCode(ifxRs));
	}

	protected String getAuthorizeCode(IFXRs_Type ifxRs)
	{
		long authorizationCode;
		if (isXferOperStatusInfoRs(ifxRs))
			authorizationCode = ifxRs.getXferOperStatusInfoRs().getTCIO().getCardAuthorization().getAuthorizationCode();
		else
			authorizationCode = ifxRs.getCardToNewIMAAddRs().getCardAuthorization().getAuthorizationCode();
		return String.valueOf(authorizationCode);
	}

	protected Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningFromCardClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningFromCardClaim.");

		IMAOpeningFromCardClaim imaOpeningClaim = (IMAOpeningFromCardClaim) document;
		return imaOpeningClaim.getDestinationAmount().getCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningFromCardClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningFromCardClaim.");

		IMAOpeningFromCardClaim imaOpeningClaim = (IMAOpeningFromCardClaim) document;
		return imaOpeningClaim.getChargeOffAmount().getCurrency();
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getCardToNewIMAAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		return parseCalendar(ifxRs.getCardToNewIMAAddRs().getRqTm());
	}
}
