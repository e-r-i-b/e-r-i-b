package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * ����������� ���.
 *
 * @author bogdanov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */

public final class RrnGenerator
{

	private static final Counter REQUEST_INDEX_COUNTER = Counter.createSimpleCounter("LIGHT_ESB_REQUEST_INDEX", 99999L);

	private static final Counter AUTOPAYMENT_REQUEST_INDEX_COUNTER = Counter.createSimpleCounter("AUTOPAYMENT_REQUEST_INDEX", 99999L);

	private static final CounterService counterService = new CounterService();

	private static final int STAN_NODE_NUMBER_LENGTH = 1;
	private static final int STAN_REQUEST_INDEX_LENGTH = 5;
	private static final int RRN_MJD_LENGTH = 5;

	/**
	 * ��������������� ����������.
	 */
	public static final class RrnInfo {
		public final String rrn;
		public final String stan;
		public final long mjd;

		RrnInfo(long mjd, String stan, String rrn)
		{
			this.mjd = mjd;
			this.rrn = rrn;
			this.stan = stan;
		}
	}

	private static RrnGenerator it = new RrnGenerator();
	private RrnGenerator(){};

	/**
	 * ������������� RRN, STAN � MJD ��� �������� ��� "������" ����.
	 * @param date ���� ���������.
	 * @return ��������������� ����������.
	 * @throws GateException
	 */
	public static RrnInfo generateLightESB(Calendar date) throws GateException
	{
		return it.generate(date, REQUEST_INDEX_COUNTER);
	}

	/**
	 * ������������� RRN, STAN � MJD ��� �������� �������� �� �� P2P
	 * @param date ���� ���������.
	 * @return ��������������� ����������.
	 * @throws GateException
	 */
	public static RrnInfo generateAutopayment(Calendar date) throws GateException
	{
		return it.generate(date, AUTOPAYMENT_REQUEST_INDEX_COUNTER);
	}

	private RrnInfo generate(Calendar date, Counter counter) throws GateException
	{
		String stan = generateStan(counter);
		long mjd = DateHelper.getMJD(date);
		String rrn = generateRrn(mjd, stan);
		return new RrnInfo(mjd, stan, rrn);
	}

	private Long getRequestIndex(Counter counter) throws GateException
	{
		try
		{
			return counterService.getNext(counter);
		}
		catch (CounterException e)
		{
			throw new GateException("������ ��������� ������ �������.", e);
		}
	}

	private Long getNodeNumber()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
	}

	private String format(Long value, int length, String fieldName) throws GateException
	{
		String stringValue = String.valueOf(value);
		if (stringValue.length() > length)
			throw new GateException("������ ������������ ���� \"" + fieldName + "\" �������� �������� \"" + stringValue + "\" ������������ ����� �� ������ ��������� " + length);
		return StringHelper.addLeadingZeros(stringValue, length);
	}

	private String generateStan(Counter counter) throws GateException
	{
		return format(getNodeNumber(), STAN_NODE_NUMBER_LENGTH, "����� ����� ��� STAN") + format(getRequestIndex(counter), STAN_REQUEST_INDEX_LENGTH, "����� ������� ��� STAN");
	}


	private String generateRrn(long mjd, String stan) throws GateException
	{
		return "E" + format(mjd, RRN_MJD_LENGTH, "MJD ��� RRN") + stan;
	}
}
