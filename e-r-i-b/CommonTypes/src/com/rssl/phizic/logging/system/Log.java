package com.rssl.phizic.logging.system;

/**
 * @author eMakarov
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public interface Log extends org.apache.commons.logging.Log
{
	LogLevel resolveLogLevelName(String firstchar);

	/**
	 * ѕостроить запись дл€ журнала
	 * @param message сообщение
	 * @return запись
	 */
	Object createEntry(Object message);

	/**
	 * ѕостроить запись дл€ журнала
	 * @param message сообщение
	 * @param t исключение
	 * @return запись
	 */
	Object createEntry(Object message, Throwable t);
}
