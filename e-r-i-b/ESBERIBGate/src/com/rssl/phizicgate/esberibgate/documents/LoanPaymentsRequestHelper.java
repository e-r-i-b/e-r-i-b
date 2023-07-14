package com.rssl.phizicgate.esberibgate.documents;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 @author Pankin
 @ created 12.01.2011
 @ $Author$
 @ $Revision$
 */
public class LoanPaymentsRequestHelper extends PaymentsRequestHelper
{
	public LoanPaymentsRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Заполнение информации о владельце продукта
	 * @param owner - клиент владелец продукта
	 * @return CustInfo_Type
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public CustInfo_Type createCustInfo(Client owner) throws GateException
	{
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(new PersonName_Type(owner.getSurName(), owner.getFirstName(), owner.getPatrName(), null));

		if (owner.getDocuments().isEmpty())
			throw new GateException("Не найден документ клиента id=" + owner.getId());

		List<? extends ClientDocument> documents = owner.getDocuments();
		//Сортируем документы клиента.
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);
		if (StringHelper.isEmpty(document.getDocNumber()))
			throw new GateException("Некорректный документ клиента id=" + owner.getId());

		IdentityCard_Type card = new IdentityCard_Type();
		card.setIdSeries(document.getDocSeries());
		card.setIdNum(document.getDocNumber());
		personInfo.setIdentityCard(card);

		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(personInfo);

		return custInfo;
	}

	/**
	 * Заполнение информации об СКС
	 * @param card - карта
	 * @param owner владелец
	 * @param expireDate дата закрытия
	 * @return CardAcctId_Type
	 * @throws GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate) throws GateException, GateLogicException
	{
		try
		{
			BankrollService bankrollService = getFactory().service(BankrollService.class);
			Account cardAccount = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));

			if (cardAccount == null)
			{
				GateLogicException e = new GateLogicException("По данной карте невозможно выполнить операцию. Пожалуйста, выберите другую карту");
				Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
				log.error("Не найден СКС для карты № " + card.getNumber(),e);
				throw e;
			}
			CardAcctId_Type result = createCardAcctId(card, owner, expireDate, false, true);
			result.setAcctId(cardAccount.getNumber());
			result.setAcctCode(cardAccount.getKind());
			result.setAcctSubCode(cardAccount.getSubKind());
			return result;
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}
}
