package com.rssl.auth.csa.back.integration.erib;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.erib.generated.ClientTemplate;
import com.rssl.auth.csa.back.integration.erib.types.ClientInformation;
import com.rssl.auth.csa.back.integration.erib.types.ConfirmationInformation;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.gate.ermb.ErmbInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;

import java.util.*;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� � ������
 */

public class ERIBService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private Node getCurrentNode(Profile profile) throws Exception
	{
		ProfileNode activeProfileNode = Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_DENIED);
		if (activeProfileNode != null)
			return activeProfileNode.getNode();
		return null;
	}

	/**
	 * ��������� ���������� � ������������� �������� ��������.
	 * @param profile  ������� �������
	 * @return ���������� � �������������
	 */
	public ConfirmationInformation getConfirmationInformation(Profile profile)
	{
		try
		{
			ClientTemplate template = createClientTemplate(profile);
			Node currentNode = getCurrentNode(profile);
			if (currentNode == null)
				return null;
			return BeanHelper.copyObject(StubTimeoutIntegratedGateWrapper.getStub(currentNode).getConfirmationInformation(template), TypesCorrelation.getTypes());
		}
		catch (Exception e)
		{
			log.error("������ ��������� ���������� � ������� ������������� �������� �� ����.", e);
			return null;
		}
	}

	private Node getAnyNode() throws Exception
	{
		return Node.getFilling();
	}

	/**
	 * ��������� ��������� ���������� � �������
	 * @param profile  ������� �������
	 * @return ��������� ���������� � �������
	 */
	public ClientInformation getClientInformation(Profile profile) throws Exception
	{
		ClientTemplate template = createClientTemplate(profile);
		Node currentNode = getCurrentNode(profile);
		if (currentNode == null)
			currentNode = getAnyNode();
		return BeanHelper.copyObject(StubTimeoutIntegratedGateWrapper.getStub(currentNode).getClientInformation(template), TypesCorrelation.getTypes());
	}

	/**
	 * �������� �������������� ����� ��������������� ����������� ��� �������
	 * @param profile  ������� �������
	 * @return �����
	 */
	public UserRegistrationMode getUserRegistrationMode(Profile profile) throws Exception
	{
		ClientTemplate template = createClientTemplate(profile);
		Node currentNode = getCurrentNode(profile);
		if (currentNode == null)
			currentNode = getAnyNode();
		String response =  StubTimeoutIntegratedGateWrapper.getStub(currentNode).getUserRegistrationMode(template);
		if (response == null)
			return null;
		return UserRegistrationMode.valueOf(response);
	}

	/**
	 * ������� ����� �������� ���� ������� � �����
	 * @param profile ������� ������� � ���
	 * @param phoneNumber ����� ��������
	 * @throws Exception
	 */
	public void removeErmbPhone(Profile profile, String phoneNumber) throws Exception
	{
		ClientTemplate template = createClientTemplate(profile);
		Node currentNode = getCurrentNode(profile);

		if (currentNode == null)
		{
			currentNode = getAnyNode();
		}

		StubTimeoutIntegratedGateWrapper.getStub(currentNode).removeErmbPhone(template, phoneNumber);
	}

	private ClientTemplate createClientTemplate(Profile profile)
	{
		ClientTemplate template = new ClientTemplate();

		template.setFirstName(profile.getFirstname());
		template.setLastName(profile.getSurname());
		template.setMiddleName(profile.getPatrname());
		template.setPassport(profile.getPassport());
		template.setBirthDate(profile.getBirthdate());
		template.setTb(profile.getTb());

		return template;
	}

	private List<ClientTemplate> createClientTemplate(Collection<Profile> profiles)
	{
		List<ClientTemplate> templates = new ArrayList<ClientTemplate>(profiles.size());
		for (Profile profile : profiles)
		{
			ClientTemplate template = createClientTemplate(profile);
			templates.add(template);
		}
		return templates;
	}

	/**
	 * �������� ���������� �� ��������� ������ ����
	 * @param profiles ������ � ���
	 * @param commonNode ����� ���� ��� �������� �� ������
	 * @return ���� �� ����
	 */
	public Collection<ErmbInfo> getErmbInformation(Collection<Profile> profiles, Node commonNode) throws Exception
	{
		List<ClientTemplate> templates = createClientTemplate(profiles);
		List<ClientTemplate> profileList = new ArrayList<ClientTemplate>();

		BeanHelper.copyPropertiesWithDifferentTypes(profileList, templates, TypesCorrelation.getTypes());
		return BeanHelper.copyObject(StubTimeoutIntegratedGateWrapper.getStub(commonNode).getErmbInformationList(profileList), TypesCorrelation.getTypes());
	}
}
