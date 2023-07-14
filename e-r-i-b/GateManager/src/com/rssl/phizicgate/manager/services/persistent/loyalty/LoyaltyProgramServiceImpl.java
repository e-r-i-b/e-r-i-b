package com.rssl.phizicgate.manager.services.persistent.loyalty;

import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.List;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramServiceImpl extends PersistentServiceBase<LoyaltyProgramService> implements LoyaltyProgramService
{
	public LoyaltyProgramServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ��������� ���������� �� ��������� ���������� �� �������������� �����.
	 * @param externalId - ������������� �����
	 * @return ���������� �� ��������� ���������� �������
	 */
	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException
	{
		return storeRouteInfo(delegate.getClientLoyaltyProgram(removeRouteInfo(externalId)));
	}

	/**
	 * ��������� ������ �������� �� ��������� ����������.
	 * (���� �� ������������ ���� ����� ������������ ���� 10 ��������� ��������.)
	 * @param loyaltyProgram - ��������� ����������
	 * @return ������ �������� �� ��������� ����������
	 */
	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		return delegate.getLoyaltyOperationInfo(removeRouteInfo(loyaltyProgram));
	}

	/**
	 * ��������� ������ ���������������.
	 * @param loyaltyProgram - ��������� ����������
	 * @return ������ ���������������
	 */
	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		return delegate.getLoyaltyOffers(removeRouteInfo(loyaltyProgram));
	}
}
