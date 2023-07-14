package com.rssl.phizic.business.locale;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.LocaleState;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author koptyaev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ReuseOfLocalVariable","MagicNumber"})
public class EribLocaleTest extends BusinessTestCaseBase
{
	private static final MultiInstanceEribLocaleService service = new MultiInstanceEribLocaleService();

	private ERIBLocale getNewLocale(String id)
	{
		ERIBLocale locale = new ERIBLocale();
		locale.setId(id);
		locale.setName("Русский");
		locale.setState(LocaleState.ENABLED);
		locale.setEribAvailable(true);
		locale.setAtmApiAvailable(true);
		locale.setMapiAvailable(true);
		locale.setWebApiAvailable(true);
		locale.setErmbAvailable(true);
		locale.setImageId(1L);
		return locale;
	}

	/**
	 * Тест
	 * @throws Exception
	 */
	public void test() throws Exception
	{
		try
		{
			ERIBLocale locale = getNewLocale("RU");

			ERIBLocale l = service.getById("RU", null);
			assertTrue("В справочнике есть заданная запись", l== null);
			assertTrue("Справочник не пуст", service.getAll(null).isEmpty());
			ERIBLocale addedLocale = service.addOrUpdate(locale,null);
			assertTrue("Не удалось добавить запись",addedLocale!=null);
			l = service.getById("RU",null);
			assertTrue("Искомая запись не найдена", l!= null);
			assertTrue("Справочник пуст", !service.getAll(null).isEmpty());
			service.delete(addedLocale,null);
			l = service.getById("RU",null);
			assertTrue("В справочнике есть заданная запись", l== null);
			assertTrue("Справочник не пуст", service.getAll(null).isEmpty());
		}
		catch(Exception ex)
		{
			throw new GateException(ex);
		}
	}
}
