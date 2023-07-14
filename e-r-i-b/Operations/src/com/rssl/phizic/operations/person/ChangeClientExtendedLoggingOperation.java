package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.cache.ExtendedLogClearCacheEvent;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.extendedLogging.ClientExtendedLoggingEntry;
import com.rssl.phizic.logging.extendedLogging.ClientExtendedLoggingService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.access.PersonLoginSource;

import java.util.Calendar;

/**
 * Операция управления расширенным логированием для клиента
 * @author gladishev
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ChangeClientExtendedLoggingOperation extends OperationBase<UserRestriction>
{
	private CommonLogin login;
	private ClientExtendedLoggingEntry extendedLoggingEntry;

	/**
	 * инициализация операции
	 * @param loginSource - информация о логине
	 */
	public void initialize(PersonLoginSource loginSource) throws BusinessException
	{
		ActivePerson person = loginSource.getPerson();
		PersonOperationBase.checkRestriction(getRestriction(), person);
		this.login = loginSource.getLogin();
		this.extendedLoggingEntry = ClientExtendedLoggingService.getInstance().getValidEntry(login.getId());
	}

	/**
	 * Включить расширенное логирование
	 * @param endDate - дата и время окончания расширенного логирования
	 */
	public void extendedLoggingOn(Calendar endDate) throws BusinessException
	{
		if (extendedLoggingEntry == null)
		{
			extendedLoggingEntry = new ClientExtendedLoggingEntry();
			extendedLoggingEntry.setLoginId(login.getId());
		}

		extendedLoggingEntry.setStartDate(Calendar.getInstance());
		extendedLoggingEntry.setEndDate(endDate);

		try
		{
			ClientExtendedLoggingService.getInstance().addOrUpdate(extendedLoggingEntry);
			sendClearCacheEvent();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Отключить расширенное логирование
	 */
	public void extendedLoggingOff() throws BusinessException
	{
		try
		{
			ClientExtendedLoggingService.getInstance().remove(extendedLoggingEntry);
			sendClearCacheEvent();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void sendClearCacheEvent() throws Exception
	{
		EventSender.getInstance().sendEvent(new ExtendedLogClearCacheEvent(login.getId()));
	}

	/**
	 * @return время действия расширенного логирования по-умолчанию
	 */
	public int getDefaultLoggingHours() throws BusinessException
	{
		return ConfigFactory.getConfig(BusinessSettingsConfig.class).getDefaultExtendedLoggingTime();
	}

	/**
	 * @return запись расширенного логирования
	 */
	public ClientExtendedLoggingEntry getExtendedLoggingEntry()
	{
		return extendedLoggingEntry;
	}
}
