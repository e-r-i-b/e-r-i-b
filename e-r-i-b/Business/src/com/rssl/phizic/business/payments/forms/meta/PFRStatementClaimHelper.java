package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.documents.PFRStatementClaimSendMethod;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 21.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хелпер для работы с заявкой на получение выписки из ПФР
 */
public class PFRStatementClaimHelper
{
	private static final String PASSPORT_WAY_UNAVAILABLE_MESSAGE_TEXT = "Для заявки на выписку из ПФР требуются паспортные данные.\n" +
			"А они не известны";

	private static final String REPEAT_REQUEST_BY_SNILS = "Ваши паспортные данные в \"Сбербанк Онлайн\" " +
		"отличаются от сведений, указанных в системе информационного обмена Пенсионного фонда. " +
		"Повторите запрос по страховому номеру индивидуального лицевого счета.";

	private static final String ENTER_SNILS_AND_REPEAT_REQUEST = "Ваши паспортные данные в \"Сбербанк Онлайн\" " +
		"отличаются от сведений, указанных в системе информационного обмена Пенсионного фонда. " +
		"Введите страховой номер индивидуального лицевого счета и повторите запрос.";

	/**
	 * Подготавливает заявку к отправке в шлюз
	 * @param claim - заявка
	 * @param sendMethod - метод, которым нужно отправить заявку
	 */
	public static void prepareClaim(PFRStatementClaim claim, PFRStatementClaimSendMethod sendMethod) throws DocumentException, DocumentLogicException
	{
		try
		{
			BusinessDocumentOwner documentOwner = claim.getOwner();
			if (documentOwner == null)
				throw new DocumentException("Не найден владелец заявки " + claim.getId());
			ActivePerson person = documentOwner.getPerson();

			claim.setSendMethod(sendMethod);

			claim.setFirstName(person.getFirstName());
			claim.setSurName(person.getSurName());
			claim.setPatrName(person.getPatrName());
			claim.setBirthDay(person.getBirthDay());

			// В зависимости от выбранного метода устанавливаем параметры заявки
			switch (sendMethod)
			{
				case USING_PASPORT_WAY:
					PersonDocument passportWay = PersonHelper.getPersonPassportWay(person);
					if (passportWay == null)
						throw new DocumentLogicException(PASSPORT_WAY_UNAVAILABLE_MESSAGE_TEXT);
					claim.setDocNumber(passportWay.getDocumentNumber());
					claim.setDocSeries(passportWay.getDocumentSeries());
					claim.setDocIssueDate(passportWay.getDocumentIssueDate());
					claim.setDocIssueBy(passportWay.getDocumentIssueBy());
					claim.setDocIssueByCode(passportWay.getDocumentIssueByCode());
					claim.setSNILS(null);
					break;

				case USING_SNILS:
					claim.setDocNumber(null);
					claim.setDocSeries(null);
					claim.setDocIssueDate(null);
					claim.setDocIssueBy(null);
					claim.setDocIssueByCode(null);
					claim.setSNILS(person.getSNILS());
					break;
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Возвращает текст проблемы с заявкой
	 * @param claim - заявка
	 * @return текст проблемы для показа пользователю
	 *  или null, если проблем нет
	 */
	public static String getClaimErrorText(PFRStatementClaim claim)
	{
		StatementStatus statementStatus = claim.isReady();
		if (statementStatus == null)
			return null;
		
		switch (statementStatus)
		{
			case READY:
			case AVAILABLE:
			case NOT_YET_AVAILABLE:
				return null;

			case UNAVAILABLE_DUE_INFO_REQUIRED:
			{
				if (StringHelper.isEmpty(claim.getSNILS()))
					return ENTER_SNILS_AND_REPEAT_REQUEST;
				else
					return REPEAT_REQUEST_BY_SNILS;
			}
			case UNAVAILABLE_DUE_UNKNOWN_PERSON:
			case UNAVAILABLE_DUE_FAIL:
				return claim.getReadyDescription();

			default:
				throw new IllegalStateException("Неожиданный статус заявки: " + statementStatus);
		}
	}
}
