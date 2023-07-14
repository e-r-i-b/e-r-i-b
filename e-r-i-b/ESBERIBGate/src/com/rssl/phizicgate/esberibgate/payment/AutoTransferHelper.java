package com.rssl.phizicgate.esberibgate.payment;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoPaymentTemplate_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.BankAcctRec_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type;

/**
 * Хелпер для работы с автопереводами
 *
 * @author khudyakov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */
public class AutoTransferHelper extends AutoSubscriptionHelper
{
	public AutoTransferHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Заполнить структуру AutoPaymentTemplate_Type
	 * @param autoPaymentTemplate_type структура
	 * @param payment платеж
	 */
	@Override
	protected void fillAutoPaymentTemplate_Type(AutoPaymentTemplate_Type autoPaymentTemplate_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		Client owner = getBusinessOwner(payment);
		Card card = getCard(owner, payment.getChargeOffCard(), payment.getOffice());

		BankAcctRec_Type bankAcctRec_type = new BankAcctRec_Type();

		CardAcctId_Type fromCardAcctId_Type = new CardAcctId_Type();
		fillInternalCardAcctId_Type(fromCardAcctId_Type, owner, card);
		bankAcctRec_type.setCardAcctId(fromCardAcctId_Type);
		autoPaymentTemplate_type.setBankAcctRec(new BankAcctRec_Type[]{bankAcctRec_type});

		Class<? extends GateDocument> type = payment.getType();
		if (InternalCardsTransferLongOffer.class.isAssignableFrom(type))
		{
			CardAcctId_Type toCardAcctId_Type = new CardAcctId_Type();
			fillInternalCardAcctId_Type(toCardAcctId_Type, owner, getCard(owner, payment.getReceiverCard(), payment.getOffice()));
			autoPaymentTemplate_type.setCardAcctTo(toCardAcctId_Type);
		}

		if (ExternalCardsTransferToOurBankLongOffer.class.isAssignableFrom(type) || ExternalCardsTransferToOtherBankLongOffer.class.isAssignableFrom(type))
		{
			CardAcctId_Type toCardAcctId_Type = new CardAcctId_Type();
			fillExternalCardAcctId_Type(toCardAcctId_Type, getCard(owner, payment.getReceiverCard(), payment.getOffice()));
			autoPaymentTemplate_type.setCardAcctTo(toCardAcctId_Type);
		}
	}

	/**
	 * Заполнение СКС карты списания.
	 * @param cardAcctId_type - структука карты списания
	 * @param card - карта списания.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void fillInternalCardPrimaryAccount(CardAcctId_Type cardAcctId_type, Card card) throws GateLogicException, GateException
	{
		if (StringHelper.isNotEmpty(cardAcctId_type.getAcctId()))
		{
			return;
		}

		try
		{
			//если СКС не доступен, делаем отдельным запросом.
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Account primaryAccount = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));

			cardAcctId_type.setAcctId(primaryAccount == null ? null : primaryAccount.getNumber());
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}
}
