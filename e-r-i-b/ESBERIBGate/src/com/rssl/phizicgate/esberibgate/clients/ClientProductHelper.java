package com.rssl.phizicgate.esberibgate.clients;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author khudyakov
 * @ created 13.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ClientProductHelper
{
	/**
	 * ��������� ���������� ������� � �������� � ������� ������ ������� ��������
	 * @param userProducts ������ ����������� ���������
	 * @param office �������������
	 * @return ProductContainer
	 */
	public static ProductContainer collectClientsProducts(Office office, BankProductType ... userProducts) throws GateException
	{
		if (ArrayUtils.isEmpty(userProducts))
			throw new IllegalArgumentException("������ ��������� �� ������ ���� ����.");

		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		List<BankProductType> errorsProducts = new ArrayList<BankProductType>();
		Map<BankProductType, String> errorsMessages = new HashMap<BankProductType, String>();

		//��������� �������� �� ���������� ������� ������
		for (BankProductType product : userProducts)
		{
			try
			{
				ExternalSystemHelper.check(externalSystemGateService.findByProduct(office, product));
			}
			catch (InactiveExternalSystemException e)
			{
				errorsProducts.add(product);
				errorsMessages.put(product, e.getMessage());
			}
		}

		//noinspection unchecked
		return new ProductContainer(ListUtils.subtract(Arrays.asList(userProducts), errorsProducts), errorsMessages);
	}
}
