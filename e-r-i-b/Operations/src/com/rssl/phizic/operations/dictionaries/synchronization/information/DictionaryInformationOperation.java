package com.rssl.phizic.operations.dictionaries.synchronization.information;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryInformation;
import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryInformationService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ��������� ������������ � ����� � ������� �������������
 */

public class DictionaryInformationOperation extends OperationBase
{
	private static final DictionaryInformationService service = new DictionaryInformationService();
	private final Map<Long, String> nodeInfo = new HashMap<Long, String>();

	/**
	 * ������������������� ��������
	 */
	public void initialize()
	{
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		for (NodeInfo node : nodeInfoConfig.getNodes())
			nodeInfo.put(node.getId(), node.getName());
	}

	/**
	 * @return ��������� ������������
	 * @throws BusinessException
	 */
	public List<DictionaryInformationWrapper> getInformation() throws BusinessException
	{
		List<DictionaryInformation> dictionaryInformation = service.getDictionaryInformation();

		ArrayList<DictionaryInformationWrapper> result = new ArrayList<DictionaryInformationWrapper>(dictionaryInformation.size() + 1);

		result.add(new DictionaryInformationWrapper("��� �����", service.getMainDictionaryInformation()));

		for (DictionaryInformation information : dictionaryInformation)
		{
			String nodeName = nodeInfo.remove(information.getNodeId());
			result.add(new DictionaryInformationWrapper(nodeName, information));
		}

		for (String nodeName : nodeInfo.values())
		{
			result.add(new DictionaryInformationWrapper(nodeName));
		}

		return result;
	}

	/**
	 * ������������ ������ ������������ ������������� ������������
	 * @throws BusinessException
	 */
	public void sendSynchronizationNotification() throws BusinessException
	{
		service.sendSynchronizationNotification();
	}

	/**
	 * ������������ ������ ������������ ������������� ������������ � ������� �����
	 * @throws BusinessException
	 */
	public void startCurrentBlockSynchronization() throws BusinessException
	{
		service.startCurrentBlockSynchronization();
	}
}
