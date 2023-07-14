package com.rssl.phizic.csaadmin.operation;

import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.business.session.Session;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 27.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ����� ����� ����������
 */
public class ChangeNodeOperation extends AuthenticationOperationBase
{

	/**
	 * ������������� ��������
	 * @param session - ������ � ������ ������� ���������� ����� �����
	 * @param action - ����� �������� � ����� �����
	 * @param parameters - ��������� ������ �������� � ����� �����
	 * @throws AdminException
	 */
	public void initialize(Session session, String action, Map<String,String> parameters) throws AdminException
	{
		this.session = session;
		this.action = action;
		this.parameters = parameters;
	}

	/**
	 * ������� ���� ����������
	 * @param nodeId - ������������� ������ �����
	 * @throws AdminLogicException
	 */
	public void changeNode(Long nodeId) throws AdminException, AdminLogicException
	{
		Login login = session.getLogin();
		NodeInfoConfig nodeConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		currentNode = nodeConfig.getNode(nodeId);
		if(currentNode == null)
			throw new AdminLogicException("���� � ��������������� " + nodeId + " �� ������");
		loginService.changeNode(login,currentNode.getId());
	}
}
