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
 * ������, ������������ ������ �� ����� �������� ������� �����
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
	 * ��������� �� ����� �������� ��������� ����� �� � ����
	 * @param code - ��� ���������
	 * @return - ���� ���������
	 * @throws BasketReadCodeException - �������, ���� �� ����� ���� �� ������� ����� ����� �� �������
	 */
	public ServiceCategory readCategoryByCode(String code) throws BasketReadCodeException
	{
		if (StringHelper.isEmpty(code))
		{
			throw new IllegalArgumentException("��� ��������� �� ����� ���� null");
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
	 * ��������� �� ����� �������� ������ ����� ��������� ����� ������� ����� �� �����(�����������) �������
	 * @param key - ���������� ���� ������� (���� ������� � ��������� ����� �������� � ������� �������)
	 * @return - ������ ����� ��������� ����� �������
	 * @throws BasketReadObjectException - ������� ���� �� ����� ������ �� ������� ����� � ����� � �����������
	 */
	private List<String> readCodesByKey(String key) throws BasketReadObjectException
	{
		String codeSequence = getProperty(key);
		if (StringHelper.isEmpty(codeSequence))
		{
			throw new BasketReadObjectException("� ����� �������� " + getReader().getFileName() + " �� ������� �������� �� ����� " + key);
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
	 * �������� ������ ����� ��������� �����, ����������� � ������� �����, �� ������� �� �������� ����� �������
	 * @param outerKey - ������� ���� ������� ����� (�� �������� ������ ����� �������� ��� ������� �������)
	 * @return - ������ ����� ��������� ����� �������
	 * @throws BusinessException - �������, ���� �� ����� ������ ������� � ������� (������������ ��� ����� ������������ ���� ��� ������ ������ ������� � ����� ��������)
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
				log.error("�� ������� ����� ������ �� ������������ ����� " + outerKey, e);
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
	 * �������� ������ ��������� �����, ����������� � ������� ������� �����, �� ��� �������� �����
	 * @param outerKey - ������� ���� �������
	 * @return - ������ ��������� �����
	 * @throws BusinessException - ������������ ������ �� ������ getCodesByKey()
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
