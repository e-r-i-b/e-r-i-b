package com.rssl.phizic.business.ips;

import com.rssl.phizic.gate.ips.IPSCardOperation;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lepihina
 * @ created 18.06.14
 * $Author$
 * $Revision$
 */
public class CardOperationHelper
{
	private static final int MAX_DESCRIPTION_SIZE = 100;
	private static final int MAX_DEVICE_NUMBER_SIZE = 10;

	/**
	 * @param operation операция, пришедшая из IPS
	 * @return описание операции
	 */
	public static String getOperationDescription(IPSCardOperation operation)
	{
		return StringHelper.truncate(operation.getDescription(), MAX_DESCRIPTION_SIZE);
	}

	/**
	 * @param operation операция, пришедшая из IPS
	 * @return номер устройства
	 */
	public static String getDeviceNumber(IPSCardOperation operation)
	{
		return StringHelper.truncate(operation.getShopInfo(), MAX_DEVICE_NUMBER_SIZE);
	}
}
