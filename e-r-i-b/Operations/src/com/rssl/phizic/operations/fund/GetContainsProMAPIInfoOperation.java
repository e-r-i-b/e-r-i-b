package com.rssl.phizic.operations.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundRequestService;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.operations.OperationBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения информации о наличии мАпи про-версии
 */
public class GetContainsProMAPIInfoOperation extends OperationBase
{
	private static FundRequestService fundRequestService = new FundRequestService();

	private Map<String, Boolean> infoMap;
	private List<String> phones;

	/**
	 * Инициализация операции
	 * @param phonesValue список телефонов
	 * @throws BusinessLogicException
	 */
	public void initialize(String phonesValue) throws BusinessLogicException
	{
		String[] phonesArray = phonesValue.split(Constants.INITIATOR_PHONES_DELIMITER);

		infoMap = new HashMap<String, Boolean>(phonesArray.length);
		phones = Arrays.asList(phonesArray);
	}

	/**
	 * Выполнить операцию получения информации о наличии мАпи про-версии
	 */
	public void execute() throws BusinessException, BusinessLogicException
	{
		for (String phone : phones)
		{
			infoMap.put(phone, fundRequestService.isContainsPro(phone));
		}
	}

	/**
	 * @return информация о наличии мАпи про-версии
	 */
	public Map<String, Boolean> getInfoMap()
	{
		return infoMap;
	}

	/**
	 * @return список контактов
	 */
	public List<String> getPhones()
	{
		return phones;
	}
}
