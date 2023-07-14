package com.rssl.phizicgate.esberibgate.payment.recipients;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl;
import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.documents.senders.DocumentSenderBase;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 01.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class BillingPaymentDocumentService extends DocumentSenderBase implements DocumentService, CommissionCalculator
{
	protected static final String PITER_TB = "55";

	protected static final long CORRECT_STATUS_CODE = 0;
	protected static final long CONTROVERSIAL_STATUS_CODE = -777;

	protected BillingRequestHelper billingRequestHelper;
	protected BillingEinvoicingRequestHelper billingEinvoicingRequestHelper;
	protected BillingExtendRequestHelper billingExtendRequestHelper;
	protected BillingResponseSerializer billingResponceSerializer;

	public BillingPaymentDocumentService(GateFactory factory)
	{
		super(factory);
		billingRequestHelper = new BillingRequestHelper(factory);
		billingExtendRequestHelper = new BillingExtendRequestHelper(factory);
		billingEinvoicingRequestHelper = new BillingEinvoicingRequestHelper(factory);
		billingResponceSerializer = new BillingResponseSerializer();
	}

	@Override
	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		return createRequest(transfer, getHelper(transfer));
	}

	private BillingRequestHelper getHelper(GateDocument transfer)
	{
		return isEnvoicing(transfer) ? billingEinvoicingRequestHelper : billingRequestHelper;

	}

	private boolean isEnvoicing(GateDocument transfer)
	{
		return transfer.getType() == CardPaymentSystemPayment.class && ((CardPaymentSystemPayment)transfer).isEinvoicing();
	}

	@Override
	protected IFXRq_Type createRequest(GateDocument transfer, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (transfer.getType() != CardPaymentSystemPayment.class && transfer.getType() != CardPaymentSystemPaymentLongOffer.class)
		{
			throw new GateException("Ожидается PaymentSystemPayment");
		}
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) transfer;
		BillingPayExecRq_Type request = ((BillingRequestHelper)requestHelper).createBillingPayExecRq(payment);
		//заполянем источник списания
		Client owner = getBusinessOwner(transfer);
		Card paymentCard = getCard(owner, payment.getChargeOffCard(), transfer.getOffice());
		CardAcctId_Type cardAcctId_type = createCardAcctId(paymentCard, owner, payment.getChargeOffCardExpireDate());
		billingRequestHelper.fillCardAccIdAdditionalInfo(cardAcctId_type, paymentCard, false);
		request.setCardAcctId(cardAcctId_type);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setBillingPayExecRq(request);
		return ifxRq;
	}

	@Override
	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, billingExtendRequestHelper);
	}

	/**
	 * Заполнить стpуктуру информация по карте.
	 * Согласно спецификации проводка билингового платежа должна содержать информацию
	 * по клиенту (тип адреса и адрес регистрации)
	 * @param card карта
	 * @param owner владелец
	 * @param expireDate дата закрытия
	 * @return CardAcctId_Type
	 */
	protected CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate) throws GateLogicException, GateException
	{
		CardAcctId_Type acctId = super.createCardAcctId(card, owner, expireDate);
		PersonInfo_Type personInfo = acctId.getCustInfo().getPersonInfo();
		//заполняем информацию о клеинте(адрес регистрации и тип адреса)
		personInfo.setContactInfo(PaymentsRequestHelper.createClientRegistrationAddressContactInfo(owner));
		return acctId;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		BillingPayExecRs_Type responce = ifxRs.getBillingPayExecRs();
		Status_Type statusType = responce.getStatus();
		long statusCode = statusType.getStatusCode();

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (responce.getAuthorizationCode() != null)
			payment.setAuthorizeCode(String.valueOf(responce.getAuthorizationCode()));
		if (!StringHelper.isEmpty(responce.getAuthorizationDtTm()))
			payment.setAuthorizeDate(XMLDatatypeHelper.parseDateTime(responce.getAuthorizationDtTm()));

		if(statusCode == -105)
		{
			//Возникновение таймаута
			throwTimeoutException(statusType, BillingPayExecRs_Type.class);
		}

		if (statusCode != 0)
		{
			//Все ошибки пользовательские.
			throwGateLogicException(statusType, BillingPayExecRs_Type.class);
		}
		payment.setExternalId(responce.getRqUID());
	}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		//комисии устанавливаем при последнем запросе подготовки платежа
		//тут делать нечего
	}

	@Override
	protected IFXRq_Type createPrepareRequest(GateDocument transfer) throws GateLogicException, GateException
	{
		if (!CardPaymentSystemPayment.class.isAssignableFrom(transfer.getType()))
			throw new GateException("Ожидается CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) transfer;
		IFXRq_Type ifxRq = new IFXRq_Type();

		//критерием вызова BillingPayInqRq является отсутствие в платеже кода биллинга(не путать с кодом получателя).
		if(StringHelper.isEmpty(payment.getBillingCode()))
		{
			ifxRq.setBillingPayInqRq(createBillingPayInqRq(payment));
			return ifxRq;
		}
		else
		{
			BillingPayPrepRq_Type billingPayPrepRq = createBillingPayPrepRq(payment);
			Card paymentCard = getCardOfflineSupported(getBusinessOwner(transfer), payment.getChargeOffCard(), transfer.getOffice());
			CardAcctId_Type cardAcctId = new CardAcctId_Type();
			getHelper(transfer).fillCardAccIdAdditionalInfo(cardAcctId, paymentCard, true);
			billingPayPrepRq.setCardAcctId(cardAcctId);

			ifxRq.setBillingPayPrepRq(billingPayPrepRq);
			return ifxRq;
		}
	}

	public void processPrepareResponse(GateDocument transfer, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		if (!CardPaymentSystemPayment.class.isAssignableFrom(transfer.getType()))
			throw new GateException("Ожидается CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) transfer;
		try
		{
			//критерием вызова BillingPayInqRq является отсутствие в платеже кода биллинга(не путать с кодом получателя).
			if(StringHelper.isEmpty(payment.getBillingCode()))
				processInqResponce(ifxRs.getBillingPayInqRs(), payment);
			else
				processPrepResponce(ifxRs.getBillingPayPrepRs(), payment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		catch (DocumentLogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	protected BillingPayPrepRq_Type createBillingPayPrepRq(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		return getHelper(payment).createBillingPayPrepRq(payment);
	}

	protected BillingPayInqRq_Type createBillingPayInqRq(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		return billingRequestHelper.createBillingPayInqRq(payment);
	}

	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Проверка обновлений документа не поддерживается ");
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(AbstractPaymentSystemPayment.class.isAssignableFrom(document.getType()))
		{
			if (StringHelper.isEmpty(((AbstractPaymentSystemPayment) document).getReceiverName()))
				throw new GateLogicException("Пожалуйста, введите название организации, которой Вы хотите перевести деньги.");

		}
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() == WithdrawDocument.class && isEnvoicing(((WithdrawDocument)document).getTransferPayment()))
		{
			IFXRq_Type recallRequest = billingEinvoicingRequestHelper.createRecallRequest((WithdrawDocument) document);
			IFXRs_Type ifxRs = doRequest(recallRequest);
			BillingPayCanRs_Type responce = ifxRs.getBillingPayCanRs();
			Status_Type statusType = responce.getStatus();
			long statusCode = statusType.getStatusCode();

			if(statusCode == -105)
			{
				//Возникновение таймаута
				throwTimeoutException(statusType, BillingPayExecRs_Type.class);
			}

			if (statusCode != 0)
			{
				//Все ошибки пользовательские.
				throwGateLogicException(statusType, BillingPayExecRs_Type.class);
			}
			((WithdrawDocument)document).setExternalId(responce.getRqUID());
		}
		else
			throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	protected void processInqResponce(BillingPayInqRs_Type responce, CardPaymentSystemPayment payment) throws GateLogicException, DocumentLogicException, DocumentException, GateException
	{
		Status_Type statusType = responce.getStatus();
		long statusCode = statusType.getStatusCode();
		if (!(statusCode == CORRECT_STATUS_CODE || statusCode == CONTROVERSIAL_STATUS_CODE))
			throwGateLogicException(statusType, BillingPayInqRs_Type.class);

		payment.setBillingCode(responce.getSystemId());
		updatePaymentByRecipientRec(responce.getRecipientRec(), payment, statusType, true);
	}

	protected void processPrepResponce(BillingPayPrepRs_Type responce, CardPaymentSystemPayment payment) throws GateLogicException, DocumentLogicException, DocumentException, GateException
	{
		Status_Type statusType = responce.getStatus();
		long statusCode = statusType.getStatusCode();
		if (!(statusCode == CORRECT_STATUS_CODE || statusCode == CONTROVERSIAL_STATUS_CODE))
			throwGateLogicException(statusType, BillingPayPrepRs_Type.class);

		payment.setBillingCode(responce.getSystemId());
		updatePaymentByRecipientRec(responce.getRecipientRec(), payment, statusType, false);

		if (responce.getCommission() != null && responce.getCommissionCur() != null && responce.getMadeOperationId() != null)
		{
			//пришли поля, свидетельствующие об окончании подготовки платежа
			//устанавливаем идентифкатор платежа в биллинге
			payment.setIdFromPaymentSystem(responce.getMadeOperationId());
			//должно быть поле главной суммы
			if (payment.getChargeOffAmount() == null && payment.getDestinationAmount() == null && BillingPaymentHelper.getMainSumField(payment) == null)
				throw new GateException("Для получателя " + payment.getReceiverName() + " не пришло ни одного поля главной суммы");
			//устанавливаем комиссию
			if(!payment.isTemplate())
				payment.setCommission(billingResponceSerializer.createMoney(responce.getCommission(), responce.getCommissionCur()));
		}
		else if (payment.isEinvoicing())
		{
			throw new GateException("При подготовке платежа в адрес поставщика einvoicing не получены ключенвые поля [commission, madeOperationId]");
		}
		else
		{
			payment.setCommission(null);
			payment.setIdFromPaymentSystem(null);
		}
		for(Field extendedField:payment.getExtendedFields())
		{
			if(extendedField.isMainSum() && !extendedField.isEditable())
			{
				BigDecimal mainSumValue = new BigDecimal(extendedField.getValue().toString());
				if(BigDecimal.valueOf(0L).compareTo(mainSumValue)==1)
				{
					String mainSum = new DecimalFormat("#.##").format(mainSumValue.abs());
					throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность (переплата составляет " + mainSum + " р.).");
				}
			}
		}
	}

	protected void updatePaymentByRecipientRec(RecipientRec_Type recipientRec, CardPaymentSystemPayment payment, Status_Type statusType, boolean isInc) throws DocumentLogicException, DocumentException, GateLogicException, GateException
	{
		// обновляем поля в платеже пересчитанными данными
		List<Field> recipientFields = billingResponceSerializer.fillFields(recipientRec);
		// Если получили ошибку -777, и поставщик не запросил новые поля, это ошибка.
		if (statusType.getStatusCode() == CONTROVERSIAL_STATUS_CODE && !isNewFields(payment, recipientFields))
		{
			throwGateLogicException(statusType, RecipientRec_Type.class);
		}

		if (!payment.isEinvoicing()){
			payment.setReceiverPointCode(recipientRec.getCodeRecipientBS());
			payment.setReceiverName(recipientRec.getName());
		}

		payment.setService(new ServiceImpl(recipientRec.getCodeService(), recipientRec.getNameService()));
		payment.setReceiverINN(recipientRec.getTaxId());

		BackRefBankInfoService refBankInfoService = getFactory().service(BackRefBankInfoService.class);
		ResidentBank residentBank = refBankInfoService.findByBIC(recipientRec.getBIC());
		if (residentBank == null)
		{
			residentBank = new ResidentBank();
			residentBank.setBIC(recipientRec.getBIC());
			residentBank.setAccount(recipientRec.getCorrAccount());
		}
		payment.setReceiverBank(residentBank);

		payment.setReceiverKPP(recipientRec.getKPP());
		payment.setReceiverAccount(recipientRec.getAcctId());
		if(!payment.isEinvoicing() && !StringHelper.isEmpty(recipientRec.getNameOnBill()))
			payment.setReceiverNameForBill(recipientRec.getNameOnBill());

		Boolean notVisibleBankDetails = recipientRec.getNotVisibleBankDetails();
		payment.setNotVisibleBankDetails(notVisibleBankDetails == null ? false : notVisibleBankDetails);

		payment.setReceiverPhone(recipientRec.getPhoneToClient());

		Code oldCode = payment.getReceiverOfficeCode();
		BankInfo_Type bankInfo = recipientRec.getBankInfo();
		// если запрос подготовки(BillingPayPrepRq) платежа, то в случае если не пришел agencyId оставляем какой был
		payment.setReceiverOfficeCode(!isInc ? getOfficeCode(bankInfo, new ExtendedCodeGateImpl(oldCode))
				: BillingResponseSerializer.getOfficeCode(bankInfo));

		setExtendedFields(payment, recipientFields);
	}


	private void setExtendedFields(CardPaymentSystemPayment payment, List<Field> extendedFields) throws GateException, GateLogicException
	{
		try
		{
			// костыль в рамках запроса CHG047421
			// 1) т.к. нахомся в этом сендере, значит платеж через шину
			// 2) на копию можно не проверять, т.к. отличие копии от некопии в том, что в платеж копируются хвосты, ифом ниже ненужные хвосты удаляются, если они есть
			// 3) если такие поля(isVisible = false, isEditable = true) заданы в анкете поставщика,
			// то по текущей реализации они затираются дефолтным значением (см ExtendedFieldBuilderHelper.adaptFields)
			//платежи РБЦ не должны проходить по этому костылю
			Code code = payment.getReceiverOfficeCode();
			if (!BillingPaymentHelper.isRBCBilling(payment) && code != null && PITER_TB.equals(new ExtendedCodeGateImpl(code).getRegion()))
			{
				List<Field> editableInvisibleExtFieldList = getExtendedFieldWithEditableInvisible(extendedFields);
				if(!CollectionUtils.isEmpty(editableInvisibleExtFieldList))
				{
					// сначала сеттим значения поелй с признаками isVisible = false, isEditable = true
					// чтоб при следующем сетте, им подставились новые значения
					payment.setExtendedFields(editableInvisibleExtFieldList);
				}
			}

			// дальше сетим как есть, чтоб никто не заметил
			payment.setExtendedFields(extendedFields);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private List<Field> getExtendedFieldWithEditableInvisible(List<Field> extendedFields)
	{
		List<Field> result = new ArrayList<Field>();
		if(CollectionUtils.isEmpty(extendedFields))
			return result;

		for(Field field : extendedFields)
		{
			if(field.isEditable() && !field.isVisible())
				result.add(field);
		}

		return result;
	}

	private boolean isNewFields(CardPaymentSystemPayment payment, List<Field> recipientFields) throws DocumentException
	{
		List<Field> extendedFields = payment.getExtendedFields();

		if (CollectionUtils.isEmpty(extendedFields))
		{
			// Если поля в платеже и в ответе шины пусты, значит новых нет
			if (CollectionUtils.isEmpty(recipientFields))
				return false;
			else
				return true;
		}

		// Собираем externalId полей из платежа и из ответа шины
		List<String> paymentFieldsExternalIds = new ArrayList<String>(extendedFields.size());
		for (Field extendedField : extendedFields)
		{
			paymentFieldsExternalIds.add(extendedField.getExternalId());
		}
		List<String> recipientFieldsExternalIds = new ArrayList<String>(recipientFields.size());
		for (Field recipientField : recipientFields)
		{
			recipientFieldsExternalIds.add(recipientField.getExternalId());
		}

		// Если в ответе шины есть хотя бы одно поле с externalId таким, которого нет среди полей платежа,
		// значит нам вернулись новые поля
		for (String recipientFieldsExternalId : recipientFieldsExternalIds)
		{
			if (!paymentFieldsExternalIds.contains(recipientFieldsExternalId))
				return true;
		}

		return false;
	}

	/**
	 * получить код офиса по структуре BankInfo_Type(особенность в том, что в случае если ОСБ не пришел,
	 * нужно оставить старый код)
	 * @param bankInfo  BankInfo_Type
	 * @param oldCode старый код офиса
	 * @return код офиса
	 */
	private ExtendedCodeGateImpl getOfficeCode(BankInfo_Type bankInfo, ExtendedCodeGateImpl oldCode)
	{
		// если офис не пришел возващаем старый(см CHG032525: Подразделение для расчетов при уточнении поставщика (шинный биллинг))
		if (bankInfo == null)
			return oldCode;

		return new ExtendedCodeGateImpl(
				bankInfo.getRegionId(),
				StringHelper.isEmpty(bankInfo.getAgencyId()) ? oldCode.getBranch() : bankInfo.getAgencyId(),
				bankInfo.getBranchId());
	}
}
