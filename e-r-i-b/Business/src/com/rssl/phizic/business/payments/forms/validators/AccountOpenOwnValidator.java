package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Calendar;

/**
 * Проверяет, открыт ли счет и является ли клиент владельцем этого счета.
 @author Pankin
 @ created 13.12.2010
 @ $Author$
 @ $Revision$
 */
public class AccountOpenOwnValidator extends AccountOpenValidator
{
	private BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected boolean checkResource(boolean findCards, Card card, Account account, ActivePerson person) throws TemporalDocumentException
	{
		if (!findCards && account == null)
			return false;

		Client owner = null;
		try
		{
			owner = findCards ? card.getCardClient() : GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(account));
		}
		catch (IKFLException ex)
		{
			String message1 = "Ошибка при получении информации о владельце счета/карты с идентификатором" + (findCards ? card.getNumber() : account.getNumber());
			log.error(message1, ex);
			throw new TemporalDocumentException(message1, ex);
		}
		if (owner == null)
			return false;

		// Имя, серия, номер и тип документа клиента, а также дата рождения в анкете должны совпадать с
		// аналогичными данными, полученными из бэк-офиса для владельца счета
		String clientFullName = owner.getFullName();
		String personFullName = person.getFullName();
		if (clientFullName == null || !personFullName.replace(" ", "").equalsIgnoreCase(clientFullName.replace(" ", "")))
		{
			currentMessage.set("ФИО клиента не совпадает с ФИО владельца счета/карты " + (findCards ? card.getNumber() : account.getNumber()));
			return false;
		}

		if (owner.getBirthDay() != null)
		{
			Calendar personBirthDay = (Calendar) person.getBirthDay().clone();
			Calendar ownerBirthDay = (Calendar) owner.getBirthDay().clone();

			boolean equalsBirthDay = (personBirthDay.get(Calendar.DATE) == ownerBirthDay.get(Calendar.DATE)
					&& personBirthDay.get(Calendar.MONTH) == ownerBirthDay.get(Calendar.MONTH)
					&& personBirthDay.get(Calendar.YEAR) == ownerBirthDay.get(Calendar.YEAR));

			if (!equalsBirthDay)
			{
				currentMessage.set("Дата рождения владельца счета/карты и клиента не совпадает.");
				return false;
			}
		}

		for (ClientDocument clientDocument : owner.getDocuments())
		{
			for (PersonDocument personDocument : person.getPersonDocuments())
			{
				if (personDocument.getDocumentType() == PersonDocumentType.valueOf(clientDocument.getDocumentType()) &&
						((personDocument.getDocumentSeries() + personDocument.getDocumentNumber()).replaceAll(" ", "")).equalsIgnoreCase(
								(clientDocument.getDocSeries() + clientDocument.getDocNumber()).replaceAll(" ", "")))
				{
					return true;
				}
			}
		}
		currentMessage.set("Документ владельца счета/карты " + (findCards ? card.getNumber() : account.getNumber()) + "не найден среди документов клиента.");
		return false;
	}
}
