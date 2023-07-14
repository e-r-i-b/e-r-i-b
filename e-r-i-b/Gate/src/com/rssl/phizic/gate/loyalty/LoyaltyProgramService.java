package com.rssl.phizic.gate.loyalty;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.LoyaltyProgramCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LoyaltyProgramCardCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LoyaltyProgramIdCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ������ ��� ��������� ���������� �� ��������� ����������
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgramService extends Service
{
	/**
	 * ��������� ���������� �� ��������� ���������� �� ����.
	 * @param externalId - ���
	 * @return ���������� �� ��������� ���������� �������
	 */
	@Cachable(keyResolver = LoyaltyProgramIdCacheKeyComposer.class, name = "LoyaltyProgram.clientLoyaltyProgramByExternalId")
	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException;

	/**
	 * ��������� ������ �������� �� ��������� ����������.
	 * (�� ������������ ����� ������������ �� 200 ��������)
	 * @param loyaltyProgram - ��������� ����������
	 * @return ������ �������� �� ��������� ����������
	 */
	@Cachable(keyResolver = LoyaltyProgramCacheKeyComposer.class, name = "LoyaltyProgram.loyaltyOperationInfo")
	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException;

	/**
	 * ��������� ������ ���������������.
	 * @param loyaltyProgram - ��������� ����������
	 * @return ������ ���������������
	 */
	@Cachable(keyResolver = LoyaltyProgramCacheKeyComposer.class, name = "LoyaltyProgram.loyaltyOffers")
	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException;
}
