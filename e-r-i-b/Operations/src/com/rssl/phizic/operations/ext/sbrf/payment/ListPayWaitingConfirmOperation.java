package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.operations.person.PersonOperationBase;

import java.util.Calendar;

/**
 * Операция получения списка операций для подтверждения
 * @author niculichev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListPayWaitingConfirmOperation extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private final static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private ActivePerson person;

	public void initialize(Long personId) throws BusinessException
	{
		PersonLoginSource source = new PersonLoginSource(personId);
		person = source.getPerson();
		// Проверка возможности работы с пользователем
		PersonOperationBase.checkRestriction(getRestriction(), person);
	}

	public ActivePerson getPerson() throws BusinessException
	{
		return person;
	}

	public Long getLoginId()
	{
		return person.getLogin().getId();
	}

	/**
	 * @return Дата, раньше которой не требуется показывать неподтвержденные платежи.
	 */
	@QueryParameter
	public Calendar getNotConfirmDocumentsDate()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -ConfigFactory.getConfig(PaymentsConfig.class).getClearNotConfirmDocumentsPeriod());
			return calendar;

		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки clearNotConfirmDocumentsPeriod", e);
			return null;
		}
	}

	/**
	 * @return Дата, раньше которой не требуется показывать платежи, ожидающие дополнительного подтверждения.
	 */
	@QueryParameter
	public Calendar getWaitConfirmDocumentsDate()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -ConfigFactory.getConfig(PaymentsConfig.class).getClearWaitConfirmDocumentsPeriod());
			return calendar;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки clearWaitConfirmDocumentsPeriod", e);
			return null;
		}
	}
}
