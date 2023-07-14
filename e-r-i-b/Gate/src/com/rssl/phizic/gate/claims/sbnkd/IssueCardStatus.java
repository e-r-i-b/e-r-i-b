package com.rssl.phizic.gate.claims.sbnkd;

import java.util.*;

/**
 * ��������� ��������� �� ������ ����� ��� ����������� ����
 *
 * @author bogdanov
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 */

public enum IssueCardStatus
{
	/**
	 * �������������, ��������������� ��� ������ ��� ��������� ������.
	 */
	INIT,
	/**
	 * ������ VIP, �������������� � ������, ���� ����� ������� GetPrivateClient �������� VIP-������.(��  ������� INIT ��� )
	 */
	VIP_CLIENT,
	/**
	 * ������ �� VIP, ����� ��  GetPrivateClient ������� �� �������������
	 */
	INIT_NO_VIP,
	/**
	 * �������������, ������������� ����� ��������� ��������� � ���, ������� ��� ������ VIP �� �����, ����� �� GetPrivateClient �� ������.
	 */
	CONFIRM,
	/**
	 * ������, ��������������� ��� ������, ������� ������ �������� �� ���, ��� ��� �������� �� �����.
	 */
	CONFIRM_NO_VIP,

	/**
	 * ��������� ���������� � �������. ��������������� �� GetPrivateClient.
	 */
	CLIENT_GETTING,

	/**
	 * ������ �������, ��������������� ����� ���������� ������� GetPrivateClient.
	 */
	CLIENT_GET,

	/**
	 * ������� �� ������������ ������ ����� ������, ��������������� ����� ��������� ���������� ������� ������� CreateCardContract.
	 */
	FIRST_CARD_CONTRACT_CREATED,
	/**
	 * ���� ���������, ������������� � ������ ��������� ��������� ������� ConcludeEDBO.
	 */
	EDBO_CONCLUDED,

	/**
	 * ������ ����� ��������, ��������������� � ������ ��������� ���������� ������� IssueCard ��� ������ �����.
	 */
	FIRST_CARD_CREATED,

	/**
	 * ���������, ��������������� ����� ���������� ������ �� ������ ���� ����.
	 */
	EXECUTED,

	/**
	 * ������, �������������� � ������ ������ �� ����� ����. ���������� ������ � ���������� ����������.
	 */
	ERROR;

	private static final Map<IssueCardStatus, Set<IssueCardStatus>> nextState = new HashMap<IssueCardStatus, Set<IssueCardStatus>>();

	private static Set<IssueCardStatus> statuses(IssueCardStatus ... statuses)
	{
		Set<IssueCardStatus> s = new HashSet<IssueCardStatus>();
		s.addAll(Arrays.asList(statuses));
		return s;
	}

	static
	{
		nextState.put(INIT, statuses(INIT_NO_VIP, CONFIRM, VIP_CLIENT, ERROR));
		nextState.put(INIT_NO_VIP, statuses(CONFIRM_NO_VIP, ERROR));
		nextState.put(CONFIRM, statuses(CONFIRM_NO_VIP, VIP_CLIENT, ERROR));
		nextState.put(CONFIRM_NO_VIP, statuses(CLIENT_GETTING, CLIENT_GET, ERROR, EDBO_CONCLUDED));
		nextState.put(CLIENT_GETTING, statuses(CLIENT_GET, VIP_CLIENT, ERROR));
		nextState.put(CLIENT_GET, statuses(FIRST_CARD_CONTRACT_CREATED, FIRST_CARD_CREATED, ERROR));
		nextState.put(VIP_CLIENT, statuses());
		nextState.put(FIRST_CARD_CONTRACT_CREATED, statuses(FIRST_CARD_CREATED, EDBO_CONCLUDED, ERROR));
		nextState.put(EDBO_CONCLUDED, statuses(FIRST_CARD_CREATED, EXECUTED, ERROR));
		nextState.put(FIRST_CARD_CREATED, statuses(EXECUTED, FIRST_CARD_CREATED, ERROR));
		nextState.put(EXECUTED, statuses());
		nextState.put(ERROR, statuses());
	}

	/**
	 * ����������� �������� � ��������� ���������.
	 * @param nextState ��������� ���������.
	 * @return  �������� �� �������.
	 */
	public boolean canGoToState(IssueCardStatus nextState)
	{
		return this.nextState.get(this).contains(nextState);
	}
}
