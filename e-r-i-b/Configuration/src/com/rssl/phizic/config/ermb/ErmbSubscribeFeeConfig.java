package com.rssl.phizic.config.ermb;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Конфиг для настроек ФПП для списания абонентской платы ЕРМБ
 * @author Rtischeva
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbSubscribeFeeConfig extends Config
{
	//время выгрузки ФПП
	private List<String> fppUnloadTime;

	//количество процессов выгрузки ФПП
	private int fppProcTotalCount;

	//директория выгрузки ФПП
	private File fppPath;

	//Время ожидания ответа от СОС после чего профиль будет переведен состояние недоплачено
	private int fppWaitingDaysNumber;

	//настройка "Код типа транзации" (Transaction Code)
	private String transactionCode;

	//настройка "максимальное количество транзакций в одном ФПП-файле"
	private int maxTransactionCount;

	public ErmbSubscribeFeeConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	public void doRefresh() throws ConfigurationException
	{
		fppUnloadTime = Collections.unmodifiableList(Arrays.asList(getProperty(SubscribeFeeConstants.FPP_UNLOAD_TIME).split(";")));
		fppProcTotalCount = getIntProperty(SubscribeFeeConstants.FPP_PROC_TOTAL_COUNT);
		fppPath = new File(getProperty(SubscribeFeeConstants.FPP_PATH));
		fppWaitingDaysNumber = getIntProperty(SubscribeFeeConstants.FPP_WAITING_DAYS_NUMBER);
		transactionCode = getProperty(SubscribeFeeConstants.TRANSACTION_CODE);
		maxTransactionCount = getIntProperty(SubscribeFeeConstants.MAX_TRANSACTION_COUNT);
	}

	public List<String> getFppUnloadTime()
	{
		return fppUnloadTime;
	}

	public int getFppProcTotalCount()
	{
		return fppProcTotalCount;
	}

	public File getFppPath()
	{
		return fppPath;
	}

	public int getFppWaitingDaysNumber()
	{
		return fppWaitingDaysNumber;
	}

	public String getTransactionCode()
	{
		return transactionCode;
	}

	public int getMaxTransactionCount()
	{
		return maxTransactionCount;
	}
}
