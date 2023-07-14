package com.rssl.auth.csamapi.operations.locale;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.utils.store.StoreManager;


/**
 * Операция смены локали
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLocaleOperation implements Operation
{
	private ERIBLocale locale;
	/**
	 * инициализирует операцию
	 * @param localeId идентификатор локали
	 * @throws FrontLogicException
	 * @throws FrontException
	 */
	public void initialize(String localeId) throws FrontLogicException, FrontException
	{
		locale = LocaleHelper.getLocale(localeId);
		if(locale == null)
			throw new FrontLogicException("Локаль с id="+localeId+" не найдена.");
	}

	public void execute() throws FrontLogicException, FrontException
	{
		StoreManager.getCurrentStore().save(MultiLocaleContext.LOCALE_KEY, locale.getId());
	}
}
