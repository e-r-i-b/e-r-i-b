package com.rssl.ikfl.crediting;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterNameGenerator;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������ ������ �� ������, ��� �����
 * � ������ ������������� ������ ����������:
 * ����� ����� ���� (2 �������)
 * ����� ������ ���������
 * �������� �������� (����� CounterService)
 * ����� ������ ���������� 27 ��������.
 */
public class ClaimNumberGenerator
{
	private final Counter counter = Counter.createExtendedCounter("CLAIM_NUMBER", 999999999999L, CounterNameGenerator.SIMPLE);
	private final CounterService counterService = new CounterService();

	/**
	 * ��������� �����
	 * @return ������ ������
	 */
	public String execute() throws BusinessException
	{
		Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
		long time = Calendar.getInstance().getTimeInMillis();
		Long count = null;
		try
		{
			count = counterService.getNext(counter);
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
		return StringHelper.appendLeadingZeros(String.valueOf(nodeNumber), 2) + String.valueOf(time) + StringHelper.appendLeadingZeros(String.valueOf(count), 12);
	}

	/**
	 * ���������� operationUID ������ � ��������������� ��������� ��������: "������������ �������� ��������" + "�����" + "����� �����"
	 * @return
	 * @throws BusinessException
	 */
	public String getInverseClaimNumber() throws BusinessException
	{
		try
		{
			Long invertedCount = inverseValue(counterService.getNext(counter));
			Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			long time = Calendar.getInstance().getTimeInMillis();
			return StringHelper.appendEndingZeros(String.valueOf(invertedCount), 12) +
					String.valueOf(time) +
					StringHelper.appendLeadingZeros(String.valueOf(nodeNumber), 2);
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	private static Long inverseValue(Long value)
	{
        Long result = 0L;
        while(value > 0) {
            result = result * 10 + value % 10;
            value /= 10;
        }
        return result;
    }
}
