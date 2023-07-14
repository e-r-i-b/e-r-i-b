package com.rssl.phizic.gate.clients;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientProductsCacheComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @ author: filimonova
 * @ created: 09.09.2010
 * @ $Author$
 * @ $Revision$
 */
public interface ClientProductsService extends Service
{
   /**
    * ������ ��������� �������
    *
    * @param client - ������
    * @return ������ ���������
    */
    //todo ��� �������� ������ ��� ������, ��� � ����. ��������� ������ ���� �� ������, ��-�� �� ���������� ������ � GFL � ��������� ����������. ��. BUG030707: ����������� ����� ������������ � ������� - ��������� ����������� GFL
   //todo ��� ��������� ���������� ������ CacheCallback, ���� �������� �� ������� ���������
   //������� ���� ������� ����� ���������, �.�. ��� ������� ���� �� ������ � ���
	@Cachable(keyResolver= ClientProductsCacheComposer.class, name = "ClientProducts.getProducts")
	public GroupResult<Class, List<Pair<Object, AdditionalProductData>>> getClientProducts(Client client, Class... clazz);

	/**
	 * ������ ���� ������� �� ������� � �������� ����������� (���, ��, ���� ��������, ��������). �� ����������.
	 * @param client - ������
	 * @return - ������ ����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<Pair<Card, AdditionalProductData>> getLightClientCards(Client client) throws GateLogicException, GateException;

	/**
	 * ��������� ��������� ���������
	 * �� ������ ������ ����������� ������ ��� ������ ����� ����
	 * @param client - ������
	 * @param pairs - ������ ���<�������, ���������>
	 * @return ��������� ��������� ��������� ��� ������� ��������
	 */
	GroupResult<Object, Boolean> updateProductPermission(Client client, List<Pair<Object, ProductPermission>> pairs) throws GateException, GateLogicException;

	/**
	 * ���������(������) ����������� �� ������ � ������������� ����������� ������� 
	 * @param client - ������
	 * @param pairs - ������ ���<�������, ���������>
	 * @return ��������� ��������� ����������� ��� ������� ��������
	 */
	GroupResult<Object, Boolean> updateOTPRestriction(Client client, List<Pair<Object, OTPRestriction>> pairs) throws GateException, GateLogicException;
}
