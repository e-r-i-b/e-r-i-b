package com.rssl.phizic.business.basket.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.basket.AccountingEntityType;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import java.util.*;

/**
 * @author tisov
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг, вычитывающий данные из файла настроек корзины услуг
 */
public class BasketConfig extends Config
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String BASKET_PREFIX = "com.rssl.phizic.basket.";

	private static final String BUTTON_NAME_KEY_POSTFIX = "button";
	private static final String ACCOUNT_NAME_KEY_POSTFIX = "account";

	private static final char SEPARATOR = ',';

	private Map<String, List> codes = new TreeMap();
	private Map<String, ServiceCategory> serviceCategories = new TreeMap();

	public BasketConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Прочитать из файла настроек категорию услуг по её коду
	 * @param code - код категории
	 * @return - сама категория
	 * @throws BasketReadCodeException - бросаем, если по этому коду не удалось найти одной из записей
	 */
	public ServiceCategory readCategoryByCode(String code) throws BasketReadCodeException
	{
		if (StringHelper.isEmpty(code))
		{
			throw new IllegalArgumentException("Код категории не может быть null");
		}

		String buttonKey = BASKET_PREFIX + code + '.' + BUTTON_NAME_KEY_POSTFIX;
		String accountKey = BASKET_PREFIX + code + '.' + ACCOUNT_NAME_KEY_POSTFIX;
		String buttonName = getProperty(buttonKey);
		String accountName = getProperty(accountKey);

		if (StringHelper.isEmpty(buttonName) || StringHelper.isEmpty(accountName))
			return null;

		return new ServiceCategory(code, buttonName, accountName);
	}

	/**
	 * Прочитать из файла настроек список кодов категорий услуг объекта учёта по ключу(внутреннему) объекта
	 * @param key - внутренний ключ объекта (ключ объекта в контексте файла настроек и данного конфига)
	 * @return - список кодов категорий услуг объекта
	 * @throws BasketReadObjectException - бросаем если не нашли записи по данному ключу в файле с настройками
	 */
	private List<String> readCodesByKey(String key) throws BasketReadObjectException
	{
		String codeSequence = getProperty(key);
		if (StringHelper.isEmpty(codeSequence))
		{
			throw new BasketReadObjectException("В файле настроек " + getReader().getFileName() + " не найдено значение по ключу " + key);
		}
		else
		{
			return Arrays.asList(StringUtils.split(codeSequence, SEPARATOR));
		}
	}

	@Override protected void doRefresh() throws ConfigurationException
	{
		for (String key:codes.keySet())
		{
			List<String> codesList = new ArrayList();
			try
			{
				codesList = readCodesByKey(key);
				codes.put(key, codesList);
			}
			catch (BasketReadObjectException e)
			{
				log.error(e.getMessage(), e);
			}
			for (String code: codesList)
			{
				addCategory(code);
			}
		}
	}

	/**
	 * Получить список кодов категорий услуг, привязанных к объекту учёта, из конфига по внешнему ключу объекта
	 * @param outerKey - внешний ключ объекта учёта (по которому объект учёта известен вне данного конфига)
	 * @return - список кодов категорий услуг объекта
	 * @throws BusinessException - бросаем, если не нашли такого объекта в конфиге (предполагаем что введён некорректный ключ или объект забыли описать в файле настроек)
	 */
	private List<String> getCodesByKey(AccountingEntityType outerKey) throws BusinessException
	{
		String innerKey = BASKET_PREFIX + outerKey.name().toLowerCase();
		if (!codes.containsKey(innerKey))
		{
			try
			{
				List<String> codesList = readCodesByKey(innerKey);
				codes.put(innerKey, codesList);
				for (String code:codesList)
				{
					if (!serviceCategories.keySet().contains(code))
					{
						addCategory(code);
					}
				}
			}
			catch (BasketReadObjectException e)
			{
				log.error("Не удалось найти запись по запрошенному ключу " + outerKey, e);
				throw new BusinessException(e);
			}
		}
		return codes.get(innerKey);
	}

	private void addCategory(String code)
	{
		try
		{
			ServiceCategory category = readCategoryByCode(code);
			if (category != null)
				serviceCategories.put(code, category);
		}
		catch (BasketReadCodeException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Получить список категорий услуг, привязанных к данному объекту учёта, по его внешнему ключу
	 * @param outerKey - внешний ключ объекта
	 * @return - список категорий услуг
	 * @throws BusinessException - пробрасываем дальше из метода getCodesByKey()
	 */
	public List<ServiceCategory> getServiceCategoryList(AccountingEntityType outerKey) throws BusinessException
	{
		List<String> codes = getCodesByKey(outerKey);
		List<ServiceCategory> result = new ArrayList<ServiceCategory>();
		for (String code:codes)
		{
			ServiceCategory serviceCategory = serviceCategories.get(code);
			if (serviceCategory != null)
				result.add(serviceCategory);
		}
		return result;
	}

	public Collection<ServiceCategory> getAllCategories()
	{
		return serviceCategories.values();
	}
}
