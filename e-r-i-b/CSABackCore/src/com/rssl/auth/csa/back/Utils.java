package com.rssl.auth.csa.back;

import com.rssl.auth.csa.back.exceptions.ServiceUnavailableException;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * ����� ��������� ������� ���.
 */

public class Utils
{
	private static final Pattern IPAS_LOGIN_REGEXP = Pattern.compile("^\\d{10}$");
	private static final Pattern DIPOSABLE_LOGIN_REGEXP = Pattern.compile("^Z\\d{10}$");
	public static final String SERVICE_UNAVAILABLE_MESSAGE = "������� �������� ����������, ��������� ���� ����� 15 �����.";

	/**
	 * ��������� ������������� �� ������ ����������� ������ iPas
	 * @param login �����
	 * @return ������������� ��� ���
	 */
	public static boolean isIPasLogin(String login)
	{
		return !StringHelper.isEmpty(login) && IPAS_LOGIN_REGEXP.matcher(login).matches();
	}

	/**
	 * ��������� ������������� �� ������ ����������� ���������� ������
	 * @param login �����
	 * @return ������������� ��� ���
	 */
	public static boolean isDisposableLogin(String login)
	{
		return !StringHelper.isEmpty(login) && DIPOSABLE_LOGIN_REGEXP.matcher(login).matches();
	}

	/**
	 * ������������� ����� ����� �������� ����������� ������������
	 * @param cardNumber ����� �����
	 * @return ��������������� �����
	 */
	public static String maskCard(String cardNumber)
	{
		return MaskUtil.getCutCardNumberForLog(cardNumber);
	}

	/**
	 * @return ��������������� GUID
	 */
	public static String generateGUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * ����� ��������� ��
	 * @param tb ��
	 * @return ����� ��������� ��
	 */
	public static String getMainTB(String tb)
	{
		return ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonymAndIdentical(tb);
	}

	/**
	 * ����� ��������� ��
	 * @param cbCode CBCode
	 * @return ����� ��������� ��
	 */
	public static String getMainTBByCBCode(String cbCode)
	{
		return getMainTB(getCutTBByCBCode(cbCode));
	}

	/**
	 * �������� ����� �� �� CBCode
	 * @param cbCode CBCode
	 * @return ����� ��
	 */
	public static String getCutTBByCBCode(String cbCode)
	{
		return cbCode.substring(0, 2);
	}

	/**
	 * �������� ����� ��������� �� �� CB_CODE � ������ ���������.
	 * ������ ���� @CbCode �����:
	 * ��� �������� � 2 �������
	 * ��� ������������� ����� � 2 �������
	 * ��� ��� � 4 �������
	 * ��� ���� ����������� ������ ��� ��������, ��� ������������� � ����������� ������
	 * @param cbCode ������ ����
	 * @return ����� �������� TB � ����������� ������.
	 */
	public static String getTBByCbCode(String cbCode)
	{
		if (StringHelper.isEmpty(cbCode))
			return null;

		return StringHelper.addLeadingZeros(ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(getCutTBByCBCode(cbCode)), 2);
	}

	/**
	 * �������� ������� ��������(��������� ��� �����) ������ ������� � �����
	 * ���� ����� ���, ��� ����� ���� �������.
	 * @param profileId ������������� �������
	 * @param createProfileNodeMode ������� �������� �������� ������� � �����
	 * @return ����� ������� � �����
	 * @throws Exception
	 */
	public static ProfileNode getActiveProfileNode(final Long profileId, final CreateProfileNodeMode createProfileNodeMode) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}

		return ActiveRecord.executeAtomic(new HibernateAction<ProfileNode>()
		{
			public ProfileNode run(Session session) throws Exception
			{
				Profile profile = Profile.findById(profileId, LockMode.UPGRADE_NOWAIT);
				List<ProfileNode> nodes = ProfileNode.getByProfile(profile);
				if (nodes.isEmpty())
				{
					if (CreateProfileNodeMode.CREATION_DENIED == createProfileNodeMode)
						return null;

					//�� ����� ���, �������� �����
					return ProfileNode.create(profile);
				}

				//�����-�� ����� ����. ���� ���������� �����
				ProfileNode mainProfileNode = getMainProfileNode(nodes);
				if (mainProfileNode == null)
				{
					throw new IllegalStateException("���������� ��������� �������: ������� " + profile.getId() + " ����� ����� � �������, �� �� ���� �� ��� �� ������ ��������� �������");
				}

				//��� 1: ���� ��������������� ����� ��������� ������� � ������������� ������
				if (mainProfileNode.getNode().isExistingUsersAllowed() && ProfileNode.State.ACTIVE == mainProfileNode.getState())
				{
					//����� ��������������� ����� ��������� ������� � ������������� ������. ��� ��� � ����. �������� ����.
					return mainProfileNode;
				}

				//��� 2: ���� � �������� ���� �� ��������� �������� ����������. ������� ��������� �� ������� �� ��������� �����?
				if (!mainProfileNode.getNode().isUsersTransferAllowed())
				{
					//�������� ��������� �������� ���� ����. ���������� � ������������
					throw new ServiceUnavailableException(SERVICE_UNAVAILABLE_MESSAGE);
				}

				//��� 3: ���� ���������� ��������� ���� �� ������������ ������
				boolean hasTemporaryNode = false; //������ ������ ����� �� ��������� �����.
				for (ProfileNode profileNode : nodes)
				{
					hasTemporaryNode |= profileNode.getProfileType() == ProfileType.TEMPORARY;

					if (!profileNode.getNode().isTemporaryUsersAllowed())
					{
						continue; //���������� ����������� �����, ���������� ��������� �������
					}
					if (ProfileNode.State.PROCESS_MIGRATION != profileNode.getState())
					{
						//����� ��������������� ����� ���������� ������� � ������������� ������.
						//������������� ������ "�������� ��������"
						profileNode.setState(ProfileNode.State.WAIT_MIGRATION);
						profileNode.save();
						return profileNode;
					}
				}
				if (hasTemporaryNode)
				{
					//������� ��������� ����, �� ����, ������������� ���, ��������. ��������� ���� �������, ���� "�� �����������" ��������� ������� �� ������(��� ��� �����)
					throw new ServiceUnavailableException(SERVICE_UNAVAILABLE_MESSAGE);
				}

				//��� 4: ������� ��������� ������� � ��������� ����.
				Node tempNode = getTempNode(nodes);
				if (tempNode == null)
				{
					//��� ����������� ���������� ���� ��� �������.
					throw new ServiceUnavailableException(SERVICE_UNAVAILABLE_MESSAGE);
				}

				if (CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES == createProfileNodeMode)
					//������� ����� �� ��������� ������
					return ProfileNode.create(profile, tempNode, ProfileType.TEMPORARY);

				return null;
			}
		});
	}

	/**
	 * ��������� �����, ������������ �� ������������ ��������� ��������, ������� �� ���������� � ��� ����������� ������ (nodes) �������.
	 * @param profileNodes ������ ������������ ������ ������� ��������
	 * @return ���� �� ������������ ��������� �������� ��� null
	 * @throws Exception
	 */
	private static Node getTempNode(List<ProfileNode> profileNodes) throws Exception
	{
		if (profileNodes == null)
		{
			throw new IllegalArgumentException("������ ������ �� ����� ���� null");
		}
		List<Node> temporaryNodes = Node.getTemporaryNodes();
		outerLoop: for (Node temporaryNode : temporaryNodes)
		{
			for (ProfileNode profileNode : profileNodes)
			{
				if (profileNode.getNode().getId().equals(temporaryNode.getId()))
				{
					continue outerLoop;
				}
			}
			return temporaryNode;
		}
		return null;
	}

	/**
	 * �� ������� ����� �������� ���� (First) ������� � ��������� (Second) � ������� �� ��������
	 * @param profile ������� �������
	 * @return ����� �������
	 * @throws Exception
	 */
	public static Pair<ProfileNode, ProfileNode> getFullNodeInfo(Profile profile) throws Exception
	{
		Pair<ProfileNode, ProfileNode> result = new Pair<ProfileNode, ProfileNode>();
		List<ProfileNode> nodes = ProfileNode.getByProfile(profile);
		result.setFirst(getMainProfileNode(nodes));
		result.setSecond(getWaitMigrationNodeProfileNode(nodes));
		return result;
	}

	private static ProfileNode getMainProfileNode(List<ProfileNode> nodes)
	{
		if (nodes == null)
		{
			throw new IllegalArgumentException("������ ������ �� ����� ���� null");
		}
		for (ProfileNode profileNode : nodes)
		{
			if (profileNode.getProfileType() == ProfileType.MAIN)
			{
				return profileNode;
			}
		}
		return null;
	}

	private static ProfileNode getWaitMigrationNodeProfileNode(List<ProfileNode> nodes)
	{
		if (nodes == null)
		{
			throw new IllegalArgumentException("������ ������ �� ����� ���� null");
		}
		for (ProfileNode profileNode : nodes)
		{
			if (profileNode.getState() == ProfileNode.State.WAIT_MIGRATION)
			{
				return profileNode;
			}
		}
		return null;
	}
}
