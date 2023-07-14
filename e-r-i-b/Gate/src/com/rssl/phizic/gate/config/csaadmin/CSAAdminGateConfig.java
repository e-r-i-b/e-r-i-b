package com.rssl.phizic.gate.config.csaadmin;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Параметры для доступа к приложению ЦСА Админ
 */

public abstract class CSAAdminGateConfig extends Config
{
	protected CSAAdminGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return урл на котором развернуты вебсервисы ЦСА Админ
	 */
	public abstract String getListenerUrl();

	/**
	 * @return таймаут на соединение к ЦСА Админ
	 */
	public abstract int getListenerTimeout();

	/**
	 * @return режим работы (true -- многоблочный режим)
	 */
	public abstract boolean isMultiBlockMode();

	/**
	 * @return урл страницы логина на ЦСА Админ
	 */
	public abstract String getLoginUrl();

	/**
	 * @return имя инстанса БД, в котором хранятся справочники в многоблочном режиме.
	 */
	public abstract String getDictionaryInstance();

	/**
	 * @return режим работы ФОС(true - многоблочный)
	 */
	public abstract boolean isMailMultiBlockMode();
}
