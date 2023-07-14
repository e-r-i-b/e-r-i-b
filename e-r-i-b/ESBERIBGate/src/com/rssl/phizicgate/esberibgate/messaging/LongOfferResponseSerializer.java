package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.ScheduleItem;
import com.rssl.phizic.gate.longoffer.SheduleItemState;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.*;
import com.rssl.phizicgate.esberibgate.types.loans.LoanTransferImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LongOfferCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 * Сериалайзер, для заполнения информации, связанной с длительными поручениями (поручения, доп. информация, графики...)
 */
public class LongOfferResponseSerializer extends BaseResponseSerializer
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final String SUMMA_KIND_CODE = "FIXED_SUMMA_IN_RECIP_CURR"; //Сумма в валюте счета зачисления
	private static final String PERCENT_CODE = "PERCENT_OF_REMAIND";

	private static final String COMMUNAL_PAY = "COMMUNAL_PAY";                 //Оплата коммунальных услуг
	private static final String CREDIT_PAY = "CREDIT_PAY";                     //Оплата кредита
	private static final String INTERNAL_PAY = "INTERNAL_PAY";                 //Перевод внутри структурного подразделения
	private static final String INTERNAL_TO_CARD_PAY = "INTERNAL_TO_CARD_PAY"; //Перевод на счет карты 
	private static final String IN_BRANCH_PAY = "IN_BRANCH_PAY";               //Перевод внутри ОСБ
	private static final String FORM143_PAY = "FORM143_PAY";                   //Перевод в другой территориальный банк
	private static final String FORM365_PAY = "FORM365_PAY";                   //Перевод в другой коммерческий банк
	private static final String IN_BANK_PAY = "IN_BANK_PAY";                   //Перевод в другое ОСБ

	//ключи счета для физиков
	private static final String [] PHIZ_KEYS = {"40817", "423", "426"};

	public LongOfferResponseSerializer()
	{
	}

	/**
	 * заполнение длительного поручения
	 */
	public List<LongOffer> fillLongOffers(IFXRq_Type ifxRq, Client client, IFXRs_Type ifxRs) throws GateLogicException
	{
		if (ifxRs == null)
			return Collections.emptyList();
		
		Status_Type status = ifxRs.getBankAcctInqRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, BankAcctInqRs_Type.class, ifxRq);
		}
		SvcsAcct_Type[] svcsAccts = ifxRs.getBankAcctInqRs().getSvcsAcct();
		if (ArrayUtils.isEmpty(svcsAccts))
			return Collections.emptyList();
		if (svcsAccts == null || svcsAccts.length == 0)
			return Collections.emptyList();

		List<LongOffer> longOffers = new ArrayList<LongOffer>(svcsAccts.length);
		for (SvcsAcct_Type svcsAcct: svcsAccts)
		{
			try
			{
				LongOfferImpl longOffer = new LongOfferImpl();
				longOffer.setExternalId(EntityIdHelper.createLongOfferEntityId(svcsAcct, client.getInternalOwnerId()));
				longOffer.setNumber(String.valueOf(svcsAcct.getSvcAcctId().getSvcAcctNum()));
				longOffers.add(longOffer);
			}              
			catch (Exception e)
			{
				if (svcsAcct != null && svcsAcct.getSvcAcctId() != null)
					log.error("Ошибка при заполнении длительного поручения №" + svcsAcct.getSvcAcctId().getSvcAcctNum(), e);
				else
					log.error("Ошибка при заполнении длительного поручения", e);
			}
		}
		return longOffers;
	}

	/**
	 * Получение типа длительного поручения
	 * Счет и карта могут придти заполненными одновременно, в таком случае считаем, что перевод с карты. 
	 */
	private Class<? extends GateDocument> getType(SvcActInfo_Type svcActInfo)
	{
		Class<? extends GateDocument> type = null;
		String pmtKind = svcActInfo.getPmtKind(); //Вид платежа
		boolean isJur = !isPhiz(svcActInfo.getRecCalcAccount());
		
		if (pmtKind.equals(COMMUNAL_PAY))
			type = AccountPaymentSystemPayment.class;
		if (pmtKind.equals(CREDIT_PAY))
			type = LoanTransfer.class;
		if (pmtKind.equals(INTERNAL_PAY) && svcActInfo.getDepAcctIdFrom()!= null)
			type = ClientAccountsTransfer.class;
		if (pmtKind.equals(INTERNAL_TO_CARD_PAY) && svcActInfo.getDepAcctIdFrom()!= null)
			type = AccountToCardTransfer.class;
		if (pmtKind.equals(INTERNAL_PAY) && svcActInfo.getCardAcctIdFrom() != null)
			type = CardToAccountTransfer.class;
		if (pmtKind.equals(INTERNAL_TO_CARD_PAY) && svcActInfo.getCardAcctIdFrom()!= null)
			type = InternalCardsTransfer.class;
		if (type == null && isJur)
		{
			if ((pmtKind.equals(IN_BRANCH_PAY) || pmtKind.equals(FORM143_PAY) || pmtKind.equals(IN_BANK_PAY))
					&& svcActInfo.getDepAcctIdFrom() != null)
				type = AccountJurIntraBankTransfer.class;
			if ((pmtKind.equals(IN_BRANCH_PAY) || pmtKind.equals(FORM143_PAY) || pmtKind.equals(IN_BANK_PAY))
					&& svcActInfo.getCardAcctIdFrom() != null)
				type = CardJurIntraBankTransfer.class;
			if (pmtKind.equals(FORM365_PAY) && svcActInfo.getDepAcctIdFrom() != null)
				type = AccountJurTransfer.class;
			if (pmtKind.equals(FORM365_PAY) && svcActInfo.getCardAcctIdFrom() != null)
				type = CardJurTransfer.class;
		}
		if (type == null && !isJur)
		{
			if ((pmtKind.equals(IN_BRANCH_PAY) || pmtKind.equals(FORM143_PAY) || pmtKind.equals(IN_BANK_PAY))
					&& svcActInfo.getDepAcctIdFrom() != null)
				type = AccountIntraBankPayment.class;
			if ((pmtKind.equals(IN_BRANCH_PAY) || pmtKind.equals(FORM143_PAY) || pmtKind.equals(IN_BANK_PAY))
					&& svcActInfo.getCardAcctIdFrom() != null)
				type = CardIntraBankPayment.class;
			if (pmtKind.equals(FORM365_PAY) && svcActInfo.getDepAcctIdFrom() != null)
				type = AccountRUSPayment.class;
			if (pmtKind.equals(FORM365_PAY) && svcActInfo.getCardAcctIdFrom() != null)
				type = CardRUSPayment.class;
		}
		return type;
	}

	private boolean isPhiz(String account)
	{
		for (String key: PHIZ_KEYS)
			if (account.startsWith(key))
				return true;
		return false;
	}

	/**
	 * заполнение поручений дополнительной информацией
	 */
	public void fillLongOffers(Client client, List<LongOffer> longOffers, SvcActInfo_Type[] svcActInfo) throws GateException, GateLogicException
	{
		for (int i = 0; i < svcActInfo.length; i++)
		{
			if (svcActInfo[i].getStatus().getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				LongOfferImpl longOffer = (LongOfferImpl) getLongOffer(longOffers, svcActInfo[i].getSvcAct().getSvcAcctId().getSvcAcctNum());
				fillLongOffer(client.getInternalOwnerId(), longOffer, svcActInfo[i]);
			}
		}
	}

	/**
	 * заполнение поручения дополнительной информацией
	 */
	public void fillLongOffer(Long loginId, LongOfferImpl longOffer, SvcActInfo_Type svcActInfo) throws GateLogicException, GateException
	{
		Regular_Type regular = svcActInfo.getRegular();

		longOffer.setSumType(SumTypeWrapper.getSumType(regular.getSummaKindCode()));
		longOffer.setNumber(String.valueOf(svcActInfo.getSvcAct().getSvcAcctId().getSvcAcctNum()));
		longOffer.setStartDate(parseCalendar(regular.getDateBegin()));
		longOffer.setEndDate(parseCalendar(regular.getDateEnd()));
		BankInfo_Type bankInfo = svcActInfo.getSvcAct().getSvcAcctId().getBankInfo();
		longOffer.setOffice(getOffice(bankInfo));

		if (regular.getSummaKindCode().equals(PERCENT_CODE))
			longOffer.setPercent(regular.getPercent());

		// для получения валюты используем не сконвертированный офис
		Currency currency = getCurrency(svcActInfo, loginId);
		Money summ = new Money(regular.getSumma(), currency);

		longOffer.setAmount(summ);
		longOffer.setType(getType(svcActInfo));
		longOffer.setPayDay(regular.getPayDay().getDay());
		longOffer.setPriority(regular.getPriority());
		longOffer.setExecutionEventType(ExecutionEventTypeWrapper.getExecutionEventType(regular.getExeEventCode()));

		Class type = longOffer.getType();

		if (type == AccountPaymentSystemPayment.class)
		{
			if (svcActInfo.getDepAcctIdFrom() != null)
				longOffer.setAccountNumber(svcActInfo.getDepAcctIdFrom().getAcctId());
			if (svcActInfo.getCardAcctIdFrom() != null)
				longOffer.setCardNumber(svcActInfo.getCardAcctIdFrom().getCardNum());
		}
		else if(type.equals(ClientAccountsTransfer.class) || type.equals(AccountToCardTransfer.class)
			|| type.equals(AccountJurIntraBankTransfer.class) || type.equals(AccountJurTransfer.class)
			|| type.equals(AccountIntraBankPayment.class) || type.equals(AccountRUSPayment.class))
		{
			if (svcActInfo.getDepAcctIdFrom() != null)
				longOffer.setAccountNumber(svcActInfo.getDepAcctIdFrom().getAcctId());
		}
		else if(type.equals(CardToAccountTransfer.class) || type.equals(InternalCardsTransfer.class)
				|| type.equals(CardJurIntraBankTransfer.class) || type.equals(CardJurTransfer.class)
				|| type.equals(CardIntraBankPayment.class) || type.equals(CardRUSPayment.class))
		{
			if (svcActInfo.getCardAcctIdFrom() != null)
				longOffer.setCardNumber(svcActInfo.getCardAcctIdFrom().getCardNum());
		}
		else if (type.equals(LoanTransfer.class))
		{
			if (svcActInfo.getDepAcctIdFrom() != null)
			{
				longOffer.setAccountNumber(svcActInfo.getDepAcctIdFrom().getAcctId());
			}
			if (svcActInfo.getCardAcctIdFrom() != null)
			{
				longOffer.setCardNumber(svcActInfo.getCardAcctIdFrom().getCardNum());
			}
		}
	}

	// Получение валюты по информации о длительном поручении
	private Currency getCurrency(SvcActInfo_Type svcActInfo, Long loginId) throws GateLogicException, GateException
	{
		return (svcActInfo.getRegular().getSummaKindCode().equals(SUMMA_KIND_CODE)) ?
			getDestinationAccountCurrency(svcActInfo) : getCurrByChargeOffResource(svcActInfo, loginId);
	}

	// Получение валюты по счету получателя
	private Currency getDestinationAccountCurrency(SvcActInfo_Type svcActInfo) throws GateLogicException, GateException
	{
		return getCurrencyByAccountNumber(svcActInfo.getRecCalcAccount());
	}

	// Получение валюты по счету получателя
	private Currency getChargeOffAccountCurrency(SvcActInfo_Type svcActInfo) throws GateLogicException, GateException
	{
		return getCurrencyByAccountNumber(svcActInfo.getDepAcctIdFrom().getAcctId());
	}

	// Получение валюты по ресурсу списания
	public Currency getCurrByChargeOffResource(SvcActInfo_Type svcActInfo, Long loginId) throws GateLogicException, GateException
	{
	    return (svcActInfo.getDepAcctIdFrom() != null) ?
			    getChargeOffAccountCurrency(svcActInfo) : getChargeOffCardCurrency(svcActInfo, loginId);
	}

	// Получение валюты по карте списания
	private Currency getChargeOffCardCurrency(SvcActInfo_Type svcActInfo, Long loginId) throws GateLogicException, GateException
	{
		String cardNumber = svcActInfo.getCardAcctIdFrom().getCardNum();
		BankInfo_Type bankInfo = svcActInfo.getSvcAct().getSvcAcctId().getBankInfo();

		return getCardCurrency(cardNumber, loginId, super.getOffice(bankInfo));
	}

	// Получение валюты по карте получателя
	private Currency getDestinationCardCurrency(SvcActInfo_Type svcActInfo, Long loginId) throws GateLogicException, GateException
	{
		String cardNumber = svcActInfo.getCardAcctIdFrom().getCardNum();
		BankInfo_Type bankInfo = svcActInfo.getSvcAct().getSvcAcctId().getBankInfo();

		return getCardCurrency(cardNumber, loginId, super.getOffice(bankInfo));
	}

	// Получение валюты по карте
	private Currency getCardCurrency(String cardNumber, Long loginId, Office office) throws GateLogicException, GateException
	{
		try
		{
			ClientImpl fakeClient = new ClientImpl();
			fakeClient.setInternalOwnerId(loginId);

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Card card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(fakeClient, new Pair<String, Office>(cardNumber, office)));

			return card.getCurrency();
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	private LongOffer getLongOffer(List<LongOffer> longOffers, Long number) throws GateException
	{
		LongOffer longOffer = new LongOfferImpl();
		for (LongOffer longOff: longOffers)
		{
			LongOfferCompositeId compositeId = EntityIdHelper.getLongOfferCompositeId(longOff);
			if (number.longValue() == Long.parseLong(compositeId.getEntityId()))
				longOffer = longOff;
		}
		if (longOffer == null)
			throw new GateException("Длительное поручение с number " + number + " не найдено.");

		return longOffer;
	}

	/**
	 * заполнение информации о длительном поручении, включающую платежную информацию
	 * @param longOffer длительное поручение
	 * @param svcActInfo дополнительная информация о длительном поручении
	 * @return информация о длительном поручении, включающая платежную информацию
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public AbstractTransfer getAbstractTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateLogicException, GateException
	{
		if (longOffer.getType().equals(AccountPaymentSystemPayment.class))
		{
			return getPaymentSystemPayment(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(ClientAccountsTransfer.class))
		{
			return getClientAccountTransfer(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(AccountToCardTransfer.class))
		{
			return getAccountToCardTransfer(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(CardToAccountTransfer.class))
		{
			return getCardToAccountTransfet(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(InternalCardsTransfer.class))
		{
			return getInternalCardsTransfet(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(CardJurIntraBankTransfer.class))
		{
			return getCardJurIntraBankTransfer(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(CardJurTransfer.class))
		{
			return getCardJurTransfer(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(AccountJurIntraBankTransfer.class))
		{
			return getAccountJurIntraBankTransfer(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(AccountJurTransfer.class))
		{
			return getAccountJurTransfer(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(CardIntraBankPayment.class))
		{
			return getCardIntraBankPayment(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(CardRUSPayment.class))
		{
			return getCardRUSPayment(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(AccountIntraBankPayment.class))
		{
			return getAccountIntraBankPayment(longOffer, svcActInfo);
		}
		if (longOffer.getType().equals(AccountRUSPayment.class))
		{
			return getAccountRUSPayment(longOffer, svcActInfo);
		}

		return getLoanTransfer(longOffer, svcActInfo);
	}

	private AbstractTransfer getLoanTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		LoanTransferImpl payment = new LoanTransferImpl(longOffer);

		if (svcActInfo.getCardAcctIdFrom() != null)
		{
			payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
			payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		}
		if (svcActInfo.getDepAcctIdFrom() != null)
		{
			payment.setAccountNumber(svcActInfo.getDepAcctIdFrom().getAcctId());
			payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		}

		return payment;
	}

	private AbstractTransfer getAccountRUSPayment(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		AccountRUSPaymentImpl payment = new AccountRUSPaymentImpl(longOffer);

		payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
		payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getAccountIntraBankPayment(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		AccountIntraBankPaymentImpl payment = new AccountIntraBankPaymentImpl(longOffer);

		payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
		payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getCardRUSPayment(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		CardRUSPaymentImpl payment = new CardRUSPaymentImpl(longOffer);

		payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getCardIntraBankPayment(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		CardIntraBankPaymentImpl payment = new CardIntraBankPaymentImpl(longOffer);

		payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getAccountJurTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		AccountJurTransferImpl payment = new AccountJurTransferImpl(longOffer);

		payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
		payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getAccountJurIntraBankTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		AccountJurIntraBankTransferImpl payment = new AccountJurIntraBankTransferImpl(longOffer);

		payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
		payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getCardJurTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		CardJurTransferImpl payment = new CardJurTransferImpl(longOffer);

		payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getCardJurIntraBankTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		CardJurIntraBankTransferImpl payment = new CardJurIntraBankTransferImpl(longOffer);

		payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private AbstractTransfer getInternalCardsTransfet(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		CardsTransferImpl payment = new CardsTransferImpl(longOffer);

		payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setReceiverCard(svcActInfo.getRecCalcAccount());
		payment.setDestinationCurrency(getDestinationAccountCurrency(svcActInfo));

		return payment;
	}

	private AbstractTransfer getCardToAccountTransfet(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		CardToAccountTransferImpl payment = new CardToAccountTransferImpl(longOffer);

		payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setReceiverAccount(svcActInfo.getRecCalcAccount());
		payment.setDestinationCurrency(getDestinationAccountCurrency(svcActInfo));
		payment.setGround(svcActInfo.getPurpose());

		return payment;
	}

	private AbstractTransfer getAccountToCardTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		AccountToCardTransferImpl payment = new AccountToCardTransferImpl(longOffer);

		payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
		payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		payment.setReceiverCard(svcActInfo.getRecCalcAccount());
		payment.setDestinationCurrency(getDestinationCardCurrency(svcActInfo, payment.getInternalOwnerId()));
		payment.setGround(svcActInfo.getPurpose());

		return payment;
	}

	private AbstractTransfer getClientAccountTransfer(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		ClientAccountsTransferImpl payment = new ClientAccountsTransferImpl(longOffer);

		payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
		payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		payment.setReceiverAccount(svcActInfo.getRecCalcAccount());
		payment.setDestinationCurrency(getDestinationAccountCurrency(svcActInfo));
		payment.setGround(svcActInfo.getPurpose());

		return payment;
	}

	private AbstractTransfer getPaymentSystemPayment(LongOffer longOffer, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		PaymentSystemPaymentImpl payment = new PaymentSystemPaymentImpl(longOffer);

		if (svcActInfo.getDepAcctIdFrom() != null)
		{
			payment.setChargeOffAccount(svcActInfo.getDepAcctIdFrom().getAcctId());
			payment.setChargeOffCurrency(getChargeOffAccountCurrency(svcActInfo));
		}
		if (svcActInfo.getCardAcctIdFrom() != null)
		{
			payment.setChargeOffCurrency(getChargeOffCardCurrency(svcActInfo, payment.getInternalOwnerId()));
			payment.setChargeOffCard(svcActInfo.getCardAcctIdFrom().getCardNum());
		}

		payment.setReceiverKPP(svcActInfo.getKPPTo());
		payment.setReceiverName(svcActInfo.getRecipientName());

		setReceiverInfo(payment, svcActInfo);
		return payment;
	}

	private void setReceiverInfo(AbstractRUSPaymentImpl payment, SvcActInfo_Type svcActInfo) throws GateException, GateLogicException
	{
		payment.setReceiverINN(svcActInfo.getRecINN());

		ResidentBank residentBank = new ResidentBank();
		residentBank.setBIC(svcActInfo.getRecBIC());
		residentBank.setAccount(svcActInfo.getRecCorrAccount());
		BackRefBankInfoService backRefBankInfoService = GateSingleton.getFactory().service(BackRefBankInfoService.class);
		//определяем банк получателя платежа по БИК
		ResidentBank recBank = backRefBankInfoService.findByBIC(svcActInfo.getRecBIC());
		residentBank.setName(recBank!=null?recBank.getName():"");

		payment.setReceiverBank(residentBank);
		payment.setReceiverAccount(svcActInfo.getRecCalcAccount());
		payment.setDestinationCurrency(getDestinationAccountCurrency(svcActInfo));
		payment.setGround(svcActInfo.getPurpose());
	}

	/**
	 * Заполнение графика исполнения длительного поручения
	 * @param operations операции
	 * @return график исполнения
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<ScheduleItem> getScheduleReport(ExctractLine_Type[] operations, SvcActInfo_Type svcActInfo, Long loginId) throws GateLogicException, GateException
	{
		List<ScheduleItem> scheduleReport = new ArrayList<ScheduleItem>();
		if (operations == null)
			return scheduleReport;
		for (ExctractLine_Type line: operations)
		{
			LongOfferScheduleItemImpl item = new LongOfferScheduleItemImpl();
			item.setDate(parseCalendar(line.getPayDate()));

			// при запросе графика исполнения длительного поручения берем не сконвертированный офис
			BankInfo_Type bankInfo =  svcActInfo.getSvcAct().getSvcAcctId().getBankInfo();
			Office office = super.getOffice(bankInfo);

			Money amount = (office == null) ? null : new Money(line.getSumma(), getCurrByChargeOffResource(svcActInfo, loginId));
			item.setAmount(amount);

			if (line.getStatus().equals("1"))
				item.setState(SheduleItemState.SUCCESS);
			else
				item.setState(SheduleItemState.FAIL);

			scheduleReport.add(item);
		}
		return scheduleReport;
	}

	/**
	 * Получение офиса для объекта счет, с переконвертированием из старых кодов (CHG027259)
	 * @param bankInfo Информация о банке
	 * @return подразделения банка
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected Office getOffice(BankInfo_Type bankInfo) throws GateLogicException, GateException
	{
		if (bankInfo == null)
			return null;

		BankInfo_Type bankInfoCopy = new BankInfo_Type(bankInfo.getBranchId(),bankInfo.getAgencyId(), bankInfo.getRegionId(),bankInfo.getRbTbBrchId(), bankInfo.getRbBrchId());
		ExtendedCodeGateImpl code = getOfficeCode(bankInfoCopy);
		// конвертируем код подразделения банка
		code.setBranch(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(bankInfoCopy.getRegionId(), bankInfoCopy.getAgencyId()));

		// ищем полное совпадение вплоть до ВСП.
		Office findedOffice = getOffice(code);
		if(findedOffice != null)
			return findedOffice;
		// если не нашли, ищем ОСБ. и возвращаем его в качестве офиса
		code.setOffice(null);
		findedOffice = getOffice(code);
		if (findedOffice != null)
			return findedOffice;
		
		return super.getOffice(bankInfoCopy);
	}
}
