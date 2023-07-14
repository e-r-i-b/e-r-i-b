package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.UserInfoProvider;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.hibernate.LockMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 * ��������� ����� ��� ������������� ��� � �� ��� ��������� ������ � �������.
 */
public class SyncUtil
{

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * �������� ���������� ���������� � ������������ ��� ������ �������������.
	 * @param cardNumber ����� ����� �������, �� �������� ��������� �������� ������
	 * @return ���������� � ������������ ��� null
	 * @throws SystemException
	 */
	public static CSAUserInfo getActualUserInfoByCardNumber(String cardNumber) throws SystemException, GateLogicException
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
		if (userInfo == null)
		{
			return null;
		}
		return new CSAUserInfo(userInfo, CSAUserInfo.Source.WAY4U);
	}

	/**
	 * �������� ���������� � ������������ �� ������ �����.
	 * �� ����������� ������������ ���������� ������ �� ��( ��� ������������ ��������� com.rssl.auth.csa.back.config.common.additional.request.for.synchronization.allowed).
	 * ���� ������ �� �������� �� ��, ���������� � ������������ �������� �� ������ ������ �� �� ��� (�������� ������������)
	 * @param cardNumber ����� ����� �������, �� �������� ��������� �������� ������
	 * @return ���������� � ������������ ��� null
	 */
	public static CSAUserInfo getUserInfoByCardNumber(String cardNumber) throws Exception
	{
		//���������� ������������ �������� �������������� �������� ��� �������������
		if (ConfigFactory.getConfig(Config.class).isAdditionalRequestForSyncAllowed())
		{
			//�� ���������. ������� �������� ���������� ���� �� ��.
			try
			{
				return getActualUserInfoByCardNumber(cardNumber);
			}
			catch (Exception e)
			{
				//��� �� ����. �� �����. ���������� � ����� ��������� ��������� �������� ���� �� ������ ������ ���.
				log.error("������ ��������� ���������� � ������������ �� ������ ����� " + Utils.maskCard(cardNumber), e);

				CSAUserInfo userInfo = getUserInfoByCardNumberFromDB(cardNumber);
				if (userInfo != null)
				{
					return  userInfo;
				}
				//��������� �� ����������. ������ ����������.
				throw e;
			}
		}
		//���� ���������� �� ���, ����������� �������... ����� ��������� �������� ���������...
		CSAUserInfo userInfo = getUserInfoByCardNumberFromDB(cardNumber);
		if (userInfo != null)
		{
			return  userInfo;
		}
		//��������� �� ����������... ���� ����� �� ��.
		return getActualUserInfoByCardNumber(cardNumber);
	}

	private static CSAUserInfo getUserInfoByCardNumberFromDB(String cardNumber) throws Exception
	{
		List<Connector> connectors = Connector.findByCardNumber(cardNumber);
		Connector connector = getMoreActualConnector(connectors);
		if (connector != null)
		{
			return connector.asUserInfo();
		}
		return null;
	}

	/**
	 * �������� ���������� ���������� � ������������ ��� ������ �������������.
	 * @param userId ����� iPas �������, �� �������� ��������� �������� ������
	 * @return ���������� � ������������ ��� null
	 * @throws SystemException
	 */
	public static CSAUserInfo getActualUserInfoByUserId(String userId) throws SystemException, GateLogicException
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByUserId(userId);
		if (userInfo == null)
		{
			return null;
		}
		return new CSAUserInfo(userInfo, CSAUserInfo.Source.WAY4U);
	}

	/**
	 * �������� ���������� � ������������ �� ������ iPas (���������� ������ ������ ������ � ���, �.�. ������ ipas ����� ������������ ������ ��������).
	 * @param userId ����� iPas �������, �� �������� ��������� �������� ������
	 * @return ���������� � ������������ ��� null
	 */
	public static CSAUserInfo getUserInfoByUserId(String userId) throws Exception
	{
		return getActualUserInfoByUserId(userId);
	}

	/**
	 * �������� �������� ���������� ��������� �� ���������� ������.
	 * ����� ���������� ��������� ��������������� ��������� � ���� ����� ��������
	 * @param connectors ������ ����������
	 * @return ����� ���������� �������� ��� nul.
	 */
	public static Connector getMoreActualConnector(List<Connector> connectors)
	{
		if (connectors == null)
		{
			return null;
		}
		Connector actual = null;
		for (Connector connector : connectors)
		{
			if (connector.isMigrated())
			{
				continue; //������������� ��-������ �� ���� �� ��������.
			}
			//������������. ������� ���������� ���� ��������
			if (actual == null || actual.getCreationDate().before(connector.getCreationDate()))
			{
				actual = connector;
			}
		}
		return actual;
	}

	/**
	 * ���������������� ������� �� ���������� � ��������� ���������� � ������������.
	 * @param userInfo ���� � �����������
	 * @return ������� - ���� �������������
	 * @throws Exception
	 */
	public static Profile synchronize(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}
		if (userInfo.getSource() == CSAUserInfo.Source.CSA)
		{
			log.trace("���������� �������������, �.�. ������ �������� �� ���.");
			return Profile.getByUserInfo(userInfo, false);
		}
		if (StringHelper.isEmpty(userInfo.getUserId()))
		{
			throw new IllegalArgumentException("����� iPas �� ����� ���� null");
		}
		if (StringHelper.isEmpty(userInfo.getCardNumber()))
		{
			throw new IllegalArgumentException("����� ����� �� ����� ���� null");
		}

		return ActiveRecord.executeAtomic(new HibernateAction<Profile>()
		{
			public Profile run(org.hibernate.Session session) throws Exception
			{
				actualizeConnectorsInfo(userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCbCode());

				//�������� ��� �������, � ������� ���� ��������� c �������� ������
				Set<Profile> profiles = new HashSet<Profile>();
				//profiles.addAll(Profile.findByUserId(userInfo.getUserId()));//����� ipas ������������ ������ ��� ������������� �������.
				profiles.addAll(Profile.findByCardNumber(userInfo.getCardNumber()));

				log.trace("�������� ������� �� ���������� � ������������");
				Profile actualProfile = Profile.getByUserInfo(userInfo, true);
				if (actualProfile == null)
				{
					log.trace("�������������� � �� ���. ��������� ������� �������, ���������� � ������� ipas " + userInfo.getUserId() + " ��� ������ " + Utils.maskCard(userInfo.getCardNumber()));
					if (profiles.isEmpty())
					{
						log.trace("��� �� ������ ������� � ����������� ������� " + userInfo.getUserId() + " ��� ������ " + Utils.maskCard(userInfo.getCardNumber()) + ". ������� �������.");
						actualProfile = Profile.create(userInfo);
					}
					else
					{
						actualProfile = profiles.iterator().next();// ����� ������ ���������� ������� ��� ����������
						session.lock(actualProfile, LockMode.UPGRADE_NOWAIT);
						log.trace("��������� ������������ ������� " + actualProfile.getId() + " ������ ������� � ������������");
						actualProfile.update(userInfo);
					}
				}
				//���� �� ��������� ������� ������������ ����������� (���� ����������� ������� ��������, ���� �������� ������� ������������ ����������� ������� �� �����������)
				boolean needConnectorsSecurityTypeUpdate = false;
				//�������� ������������ ����������� � ������ ��� ������� ������� �� ������ � �����������
				for (Profile profile : profiles)
				{
					if (profile.getId().equals(actualProfile.getId()))
					{
						//���� � ���� �� ����� ���������������.
						continue;
					}
					//������� ����, ����������� � �������
					ProfileNode.removeProfileNodes(profile);
					//��������� ��� ��������� � ������������ �������� � ����� � ������� ������
					profile.delete(actualProfile);
					needConnectorsSecurityTypeUpdate = true;
				}

				//���� ����������� ������� ��������, ���������� �������� ������� ������������ �� �����������
				if (needConnectorsSecurityTypeUpdate)
				{
					//������������� ������� ������������ ����������� ������� ���� ���������� ��� � ���� ����������� ����������� �������
					CSAConnector.setSecurityTypeToNotClosed(actualProfile);
					MAPIConnector.setSecurityTypeToNotClosed(actualProfile.getId(), actualProfile.getSecurityType());
					//�������� ������� ������������ ����������� �������
					actualProfile.setSecurityType(null);
					actualProfile.save();
				}

				log.trace("��������� ������� TERMINAL-���������� ��� ������ " + userInfo.getUserId());
				TerminalConnector terminalConnectorByUserId = TerminalConnector.findNotClosedByUserId(userInfo.getUserId());
				if (terminalConnectorByUserId == null)
				{
					TerminalConnector result = new TerminalConnector(userInfo.getUserId(), userInfo.getCbCode(), userInfo.getCardNumber(), actualProfile);
					result.save();
					log.trace("������������� ������ TERMINAL-��������� " + result.getGuid() + " �� ������ � ������������� �� ������ " + result.getUserId());
				}

				return actualProfile;
			}
		});
	}

	private static void actualizeConnectorsInfo(String cardNumber, String userId, String cbCode) throws Exception
	{
		log.trace("�������� ������������ ������������� ����������� � �������� ����������� ��� ������ iPas " + userId);
		TerminalConnector terminalConnector = TerminalConnector.findNotClosedByUserId(userId);
		if (terminalConnector != null)
		{
			if (terminalConnector.isMigrated())
			{
				log.info("������������� � ������������� ���������� " + terminalConnector.getGuid() + " ���������� � ����� � ������������� �� ��������");
				terminalConnector.setCardNumber(cardNumber);
				terminalConnector.setCbCode(cbCode);
				terminalConnector.save();
			}
			else if (!terminalConnector.getCardNumber().equals(cardNumber))
			{
				log.info("��� ������ iPas " + userId + " �������� ����� �����(" + terminalConnector.getCardNumber() + "->" + cardNumber + "). ��������� ��������� " + terminalConnector.getGuid());
				terminalConnector.close();
			}
		}

		log.trace("�������� ������������ ������� iPas ��� ����� " + Utils.maskCard(cardNumber));
		for (Connector connector : Connector.findByCardNumber(cardNumber))
		{
			//���������� ��� userId �� ������ �������� � ��� �������(BUG087098). �� �� ������ ������ �� ���������.
			if (connector.getUserId() != null && !connector.getUserId().equals(userId))
			{
				log.info("������������� � ���������� " + connector.getGuid() + " ���������� � ������ iPas. �������� userId c " + connector.getUserId() + " �� " + userId);
				connector.setUserId(userId);
				connector.save();
			}
		}
	}
}
