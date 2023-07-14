package com.rssl.phizic.monitoring.fraud;

import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * ����������� ������������� ���������� ���������� �� ��
 * ������� ��: ��������� ������������ + "|" + groupId ����� + "|" + nodeId ����� + "|" CSAProfileId + "|" + nodeProfileId + "|" + nodeLoginId;
 *
 * @author khudyakov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientTransactionCompositeId
{
	private static final String DELIMITER                                   = "\\|";

	private String groupId;
	private Long nodeId;
	private Long CSAProfileId;
	private Long nodeProfileId;
	private Long nodeLoginId;

	public ClientTransactionCompositeId(String transactionId)
	{
		if (StringHelper.isEmpty(transactionId))
		{
			throw new IllegalArgumentException("�������� ClientTransactionId �� ����� ���� ����.");
		}

		String[] parameters = transactionId.split(DELIMITER);
		groupId         = parameters.length > 1 && StringHelper.isNotEmpty(parameters[1]) ? parameters[1] : null;
		nodeId          = parameters.length > 2 && StringHelper.isNotEmpty(parameters[2]) ? Long.valueOf(parameters[2]) : null;
		CSAProfileId    = parameters.length > 3 && StringHelper.isNotEmpty(parameters[3]) ? Long.valueOf(parameters[3]) : null;
		nodeProfileId   = parameters.length > 4 && StringHelper.isNotEmpty(parameters[4]) ? Long.valueOf(parameters[4]) : null;
		nodeLoginId     = parameters.length > 5 && StringHelper.isNotEmpty(parameters[5]) ? Long.valueOf(parameters[5]) : null;
	}

	public ClientTransactionCompositeId(String groupId, Long nodeId, Long CSAProfileId, Long nodeProfileId, Long nodeLoginId)
	{
		this.groupId        = groupId;
		this.nodeId         = nodeId;
		this.CSAProfileId   = CSAProfileId;
		this.nodeProfileId  = nodeProfileId;
		this.nodeLoginId    = nodeLoginId;
	}

	/**
	 * @return groupId �����
	 */
	public String getGroupId()
	{
		return groupId;
	}

	/**
	 * @return ������������� �����
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @return profileId � ���
	 */
	public Long getCSAProfileId()
	{
		return CSAProfileId;
	}

	/**
	 * @return profileId � ����� (������� USERS)
	 */
	public Long getNodeProfileId()
	{
		return nodeProfileId;
	}

	/**
	 * @return loginId � �����
	 */
	public Long getNodeLoginId()
	{
		return nodeLoginId;
	}

	public String toString()
	{
		String salt = RandomHelper.rand(20, RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS);
		return salt
				+ "|" + StringHelper.getEmptyIfNull(groupId)
				+ "|" + StringHelper.getEmptyIfNull(nodeId)
				+ "|" + StringHelper.getEmptyIfNull(CSAProfileId)
				+ "|" + StringHelper.getEmptyIfNull(nodeProfileId)
				+ "|" + StringHelper.getEmptyIfNull(nodeLoginId);
	}
}
