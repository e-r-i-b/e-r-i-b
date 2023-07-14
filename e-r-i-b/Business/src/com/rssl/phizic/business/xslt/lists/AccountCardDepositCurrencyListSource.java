package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DepositConfig;

import java.util.Collections;
import java.util.Map;

/**
 * ���������� ����� ������, ���� � ���
 * @ author: filimonova
 * @ created: 10.02.2011
 * @ $Author$
 * @ $Revision$ 
 */
public class AccountCardDepositCurrencyListSource extends CurrencyListSourceBase
{
	private static final DepositProductService depositService = new DepositProductService();
	private static final String DEPOSIT_PRODUCT_ID = "depositId";
	public AccountCardDepositCurrencyListSource(EntityListDefinition definition, String nationCurrency) throws GateException, GateLogicException
	{
		super(definition, nationCurrency);
	}

	public AccountCardDepositCurrencyListSource(EntityListDefinition definition, Map parameters) throws GateException, GateLogicException
	{
		super(definition, parameters);
	}

	/**
	 * �������� ������� �� ������� ����� ��������� ���������� �����.
	 * @param params - ��������� �����������
	 * @return ClientCurrencyProductBundle - �������� ������ ���������, ������� ���������� ��� ���������� ����������� �����
	 * @throws BusinessException
	 */
	protected ClientCurrencyProductBundle buildCurrencyProducts(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		ClientCurrencyProductBundle clientCurrencyProductBundle = new ClientCurrencyProductBundle();
		//�����
		clientCurrencyProductBundle.setAccounts(getAccounts());

		long depositProductId = Long.parseLong(params.get(DEPOSIT_PRODUCT_ID));
		//���������� �������
		if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
		{
			clientCurrencyProductBundle.setDepositType(depositProductId);
		}
		else
		{
			DepositProduct depositProduct = depositService.findByProductId(depositProductId);
			if (depositProduct == null)
				throw new NotFoundException("���������� ������� id=" + depositProductId + " �� ������.");

			clientCurrencyProductBundle.setDepositProducts(Collections.singletonList(depositProduct));
		}
		return clientCurrencyProductBundle;
	}
}
