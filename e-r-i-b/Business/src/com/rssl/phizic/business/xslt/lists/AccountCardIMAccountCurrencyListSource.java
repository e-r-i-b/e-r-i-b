package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * ���������� ����� ������ � ���
 * @author Gololobov
 * @ created 29.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountCardIMAccountCurrencyListSource extends CurrencyListSourceBase
{
	public AccountCardIMAccountCurrencyListSource(EntityListDefinition definition, String nationCurrency) throws GateException, GateLogicException
	{
		super(definition, nationCurrency);
	}

	public AccountCardIMAccountCurrencyListSource(EntityListDefinition definition, Map parameters) throws GateException, GateLogicException
	{
		super(definition, parameters);
	}

	/**
	 * �������� ������� �� ������� ����� ��������� ���������� �����
	 * @param params - ��������� �����������
	 * @return ClientCurrencyProductBundle - �������� ������ ���������, ������� ���������� ��� ���������� ����������� �����
	 * @throws BusinessException
	 */
	protected ClientCurrencyProductBundle buildCurrencyProducts(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		ClientCurrencyProductBundle clientCurrencyProductBundle = new ClientCurrencyProductBundle();
		//�����
		clientCurrencyProductBundle.setAccounts(getAccounts());
		//�����
		clientCurrencyProductBundle.setCards(getCards());
		//���
		clientCurrencyProductBundle.setImAccounts(getIMAccounts());

		return clientCurrencyProductBundle;
	}

	protected boolean skipStoredResource(Object object)
	{
		return true;
	}
}
