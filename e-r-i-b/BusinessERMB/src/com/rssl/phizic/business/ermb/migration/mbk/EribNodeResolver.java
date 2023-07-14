package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.auth.csa.wsclient.exceptions.UserNotFoundException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.w3c.dom.Document;

/**
 * @author Erkin
 * @ created 10.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� ����� ����
 */
class EribNodeResolver
{
	/**
	 * ���������� ����, � ������� ��������� ��������� ��������
	 * @return ����������� ���� (never null)
	 */
	NodeInfo getNewUserAllowedNode() throws Exception
	{
		for (NodeInfo node: CSAResponseUtils.getNodesInfo())
		{
			if (node.isNewUsersAllowed())
				return node;
		}

		throw new ConfigurationException("�� ������ ����������� ���� ����");
	}

	/**
	 * ���� ���� �� ������ ��������
	 * @param phoneNumber - ����� �������� (never null)
	 * @return ���� ��� null, ���� ���� �� ������
	 */
	NodeInfo getNodeByPhone(PhoneNumber phoneNumber) throws Exception
	{
		if (phoneNumber == null)
			throw new IllegalArgumentException("�� ������ phoneNumber");

		try
		{
			String phoneAsString = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phoneNumber);
			Document response = CSABackRequestHelper.sendFindProfileNodeByPhoneRq(phoneAsString);
			return CSAResponseUtils.createNodeInfo(response.getDocumentElement());
		}
		catch (FailureIdentificationException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
		catch (AuthenticationFailedException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
		catch (UserNotFoundException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
		catch (ProfileNotFoundException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
	}

	/**
	 * ���� ���� �� ��� ��� ��
	 * @param userInfo - ��� ��� �� ������� (never null)
	 * @return ���� ��� null, ���� ���� �� ������
	 */
	NodeInfo getNodeByUserInfo(UserInfo userInfo) throws Exception
	{
		if (userInfo == null)
			throw new IllegalArgumentException("�� ������ userInfo");

		try
		{
			Document response = CSABackRequestHelper.sendFindProfileNodeByUserInfoRq(userInfo, false, CreateProfileNodeMode.CREATION_DENIED);
			return CSAResponseUtils.createNodeInfo(response.getDocumentElement());
		}
		catch (FailureIdentificationException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
		catch (AuthenticationFailedException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
		catch (UserNotFoundException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
		catch (ProfileNotFoundException ignored)
		{
			// ���������� - ������� ����, ��� ������ �� ������ � ���, ������� ����������
			return null;
		}
	}
}
