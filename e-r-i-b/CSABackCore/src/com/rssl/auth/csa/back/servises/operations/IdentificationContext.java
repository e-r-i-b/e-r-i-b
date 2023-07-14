package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;

import java.util.List;

/**
 * @author krenev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 * �������� ������������.
 * ���������� �� ��������� ������� = ������������ ������������.
 */

public class IdentificationContext
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private Profile profile;
	private String cbCode;
	private String cardNumber;
	private String userId;
	private List<String> cards;

	private IdentificationContext(Profile profile, String cbCode)
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		this.profile = profile;
		this.cbCode = cbCode;
		fillLogThreadContext(profile);
	}

	private IdentificationContext(Profile profile, String cbCode, String cardNumber, String userId)
	{
		this(profile, cbCode);
		this.cardNumber = cardNumber;
		this.userId = userId;
	}

	private IdentificationContext(Profile profile, String cbCode, String cardNumber, String userId, List<String> cards)
	{
		this(profile, cbCode, cardNumber, userId);
		this.cards = cards;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public String getUserId()
	{
		return userId;
	}

	/**
	 * @return ������ ���� ������������������� ������������. ����������� �� �����������.
	 * � ������ null, ����� �� ���� ��������(�������� �������� ��� ���������� � ��)
	 */
	public List<String> getCards()
	{
		return cards;
	}
	/**
	 * ������� �������� ������������ �� ������ �����
	 * @param cardNumber ����� �����
	 * @return �������� ������������ � ������ ��������� ����������� ��������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByCardNumber(String cardNumber) throws Exception
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("����� ����� ������ ���� �����");
		}
		log.trace("�������� ������������� ������������ �� ������ ����� " + Utils.maskCard(cardNumber));
		//�������� �� ����������� ���������� ���������� � ������������ �� ������ �����
		CSAUserInfo userInfo = SyncUtil.getUserInfoByCardNumber(cardNumber);
		if (userInfo == null)
		{
			throw new IdentificationFailedException("�� ������� ������ � ������������ �� ����� " + Utils.maskCard(cardNumber));
		}
		Profile profile = SyncUtil.synchronize(userInfo);
		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("������������ ��������������� profileId=" + context.getProfile().getId());
		return context;
	}


	/**
	 * ������� �������� ������������ �� ������ � ���������������� ���������� ������ �� ��.
	 * @param login �����
	 * @return �������� ������������ � ������ ��������� ����������� ��������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByLoginDirect(String login)  throws Exception
	{
		if (StringHelper.isEmpty(login))
		{
			throw new IllegalArgumentException("����� ������ ���� �����");
		}
		log.trace("�������� ������������� ������������ �� ������ " + login);

		CSAUserInfo userInfo;
		if (Utils.isIPasLogin(login))
		{
			userInfo = SyncUtil.getActualUserInfoByUserId(login);
		}
		else
		{
			Connector connector = Connector.findByAlias(login);
			if (connector == null)
			{
				throw new IdentificationFailedException("����� " + login + " �� ������� � �� ��� � �� �������� ������� iPas");
			}
			userInfo = connector.isMigrated() ? SyncUtil.getActualUserInfoByUserId(connector.getUserId()) : SyncUtil.getActualUserInfoByCardNumber(connector.getCardNumber());
		}

		if (userInfo == null)
		{
			throw new IdentificationFailedException("�� ������� ������ � ������������ �� ������ " + login);
		}
		Profile profile = SyncUtil.synchronize(userInfo);

		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("������������ ��������������� profileId = " + context.getProfile().getId());
		return context;
	}

	/**
	 * ������� �������� ������������ �� ������
	 * @param login �����
	 * @param skipSyncForIPasConnector ���������� �� ������������� ��� ipas ������.
	 * @return �������� ������������ � ������ ��������� ����������� ��������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByLogin(String login, boolean skipSyncForIPasConnector) throws Exception
	{
		if (StringHelper.isEmpty(login))
		{
			throw new IllegalArgumentException("����� ������ ���� �����");
		}
		log.trace("�������� ������������� ������������ �� ������ " + login);

		if (Utils.isIPasLogin(login))
		{
			return createByIPasLogin(login, skipSyncForIPasConnector);
		}

		Connector connector = Connector.findByAlias(login);
		if (connector == null)
		{
			throw new IdentificationFailedException("����� " + login + " �� ������� � �� ��� � �� �������� ������� iPas");
		}

		if (connector instanceof TerminalConnector)
		{
			return createByIPasLogin(connector.getUserId(), skipSyncForIPasConnector);
		}

		//�������� �� ����������� ���������� ���������� � ������������ �� ������ �����(������ ipas �� �� ����� � ������ ��� �� ����������)
		CSAUserInfo userInfo = SyncUtil.getUserInfoByCardNumber(connector.getCardNumber());
		if (userInfo == null)
		{
			throw new IdentificationFailedException("�� ������� ������ � ������������ �� ������ " + login);
		}
		Profile profile = SyncUtil.synchronize(userInfo);

		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("������������ ��������������� profileId = " + context.getProfile().getId());
		return context;
	}

	private static IdentificationContext createByIPasLogin(String iPasLogin, boolean skipSync) throws Exception
	{
		//��� ������������ ipas ������ (��� ������ � ����) �� ����� ������ ������ ipas ������, � �� ������������ � ��� �����.
		//� ������, ����� ����� ����� � ���, ����� ���������� ��������� ���������, ��... ������ + ��� = ������.
		CSAUserInfo userInfo;
		Profile profile;
		if (!skipSync)
		{
			userInfo = SyncUtil.getUserInfoByUserId(iPasLogin);
			profile = SyncUtil.synchronize(userInfo);
		}
		else
		{
			Connector moreActualConnector = SyncUtil.getMoreActualConnector(Connector.findByUserId(iPasLogin));
			userInfo = moreActualConnector == null ? SyncUtil.getUserInfoByUserId(iPasLogin) : moreActualConnector.asUserInfo();
			profile = moreActualConnector == null ? SyncUtil.synchronize(userInfo) : moreActualConnector.getProfile();
		}

		if (userInfo == null)
		{
			throw new IdentificationFailedException("�� ������� ������ � ������������ �� ������ " + iPasLogin);
		}

		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("������������ ��������������� profileId = " + context.getProfile().getId());
		return context;
	}

	/**
	 * ������� �������� ������������� �� ������ ��������
	 * @param phoneNumber ����� ��������
	 * @return �������� �������������
	 * @throws Exception
	 */
	public static IdentificationContext createByPhoneNumber(String phoneNumber) throws Exception
	{
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new IllegalArgumentException("����� �������� ������ ���� �����");
		}

		log.trace("�������� ������������� ������������ �� �������� " + phoneNumber);
		Connector connector = Connector.findByPhoneNumber(phoneNumber);

		if (connector == null)
		{
			throw new IdentificationFailedException("������� " + phoneNumber + " �� ��������������� ��� ������ � ����");
		}

		IdentificationContext identificationContext = new IdentificationContext(connector.getProfile(), connector.getCbCode());
		log.trace("������������ ��������������� profileId=" + connector.getProfile().getId());
		return identificationContext;
	}

	/**
	 * ������� �������� ������������� �� ������������� ������.
	 * ������������� ���������� �� ������ ������
	 * @param ouid ������������ ������
	 * @return �������� �������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByOperationUID(String ouid) throws Exception
	{
		if (StringHelper.isEmpty(ouid))
		{
			throw new IllegalArgumentException("������������� ������ ������ ���� �����");
		}
		log.trace("�������� ������������� ������������ �� ������������� �������� " + ouid);
		Operation operation = Operation.findByOUID(ouid);
		if (operation == null)
		{
			throw new IdentificationFailedException("�� ������� ������ � �������������� " + ouid);
		}
		IdentificationContext context = new IdentificationContext(operation.getProfile(), operation.getCbCode(), null, null);
		log.trace("������������ ��������������� profileId=" + context.getProfile().getId());
		return context;
	}

	/**
	 * ������� �������� ������������� �� ������������� ����������
	 * @param guid ������������� ����������
	 * @return �������� �������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByConnectorUID(String guid) throws Exception
	{
		return createByConnectorUID(guid, false);
	}

	/**
	 * ������� �������� ������������� �� ������������� ����������
	 * @param guid ������������� ����������
	 * @param withoutSync ��������� ��� ��� ������� ������������� ��� �������������
	 * @return �������� �������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByConnectorUID(String guid, boolean withoutSync) throws Exception
	{
		if (StringHelper.isEmpty(guid))
		{
			throw new IllegalArgumentException("������������� ���������� ������ ���� �����");
		}
		log.trace("�������� ������������ ������������ �� �������������� ���������� " + guid);
		Connector connector = Connector.findByGUID(guid);
		if (connector == null)
		{
			throw new IdentificationFailedException("�� ������ ��������� � ��������������� " + guid);
		}
		return createByConnector(connector, withoutSync);
	}

	/**
	 * ������� �������� ������������� �� ������������� ������
	 * @param sid ������������� ����������
	 * @return �������� �������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createBySessionId(String sid) throws Exception
	{
		if (StringHelper.isEmpty(sid))
		{
			throw new IllegalArgumentException("������������� ������ ������ ���� �����");
		}
		log.trace("�������� ������������ ������������ �� �������������� ������ " + sid);
		Session session = Session.findBySid(sid);
		if (session == null)
		{
			throw new IdentificationFailedException("�� ������� ������ " + sid);
		}
		return createByConnectorUID(session.getConnectorGuid(), true);
	}

	/**
	 * ������� ��������� ������������ �� ���, ���, ��, ��
	 * ���� ������� �� ������ - �������
	 * @param userInfo ���������� � ������������
	 * @param securityType ������� ������������ ��� ������ �������� ������ �������
	 * @return ��������� ��������������
	 * @throws Exception
	 */
	public static IdentificationContext createByUserInfo(CSAUserInfo userInfo, SecurityType securityType) throws Exception
	{
		Profile profile = Profile.getByUserInfo(userInfo, true);

		return new IdentificationContext(profile == null ? Profile.create(userInfo, securityType) : profile, userInfo.getCbCode());
	}

	/**
	 * �������� �������� ������������� �� ������� �������.
	 * @param template - ������ �������
	 * @return �������� �������������
	 * @throws Exception
	 */
	public static IdentificationContext createByTemplateProfile(Profile template) throws Exception
	{
		Profile profile = Profile.getByTemplate(template, false);
		if (profile == null)
		{
			throw new IdentificationFailedException("�� ������� ���������� � ������������");
		}
		return new IdentificationContext(profile, null);
	}

	/**
	 * �������� ������������ �� ����������
	 * @param connector ���������, �� ����� ���� null
	 * @param withoutSync ��������� �� ������������ ����?
	 * @return �������� ������������
	 * @throws Exception
	 */
	private static IdentificationContext createByConnector(Connector connector, boolean withoutSync) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		//����� ��������� ��������� �������������?
		if (withoutSync)
		{
			// �� �����. ������ �������� ������������ �� ��������� ������.
			IdentificationContext context = new IdentificationContext(connector.getProfile(), connector.getCbCode(), connector.getCardNumber(), connector.getUserId());
			log.trace("������������ ��������������� profileId=" + context.getProfile().getId());
			return context;
		}
		//����� �����������������. ��������� ����������?
		if (connector.isMigrated())
		{
			//��. �� ����� ������, ��� �� ����� �� ����. �������������� � ������� �� ������ iPas.
			return createByLogin(connector.getUserId(), false);
		}
		//���. �� ����������. ����� ��������: � ������� �� ������ iPas �� �������� (�� ��������� �����.. �� ��� ������ ������ �������).
		return createByCardNumber(connector.getCardNumber());
	}

	/**
	 * ������������� LogThreadContext ������� ������������
	 * @param profile �������
	 */
	private void fillLogThreadContext(Profile profile)
	{
		LogThreadContext.setFirstName(profile.getFirstname());
		LogThreadContext.setPatrName(profile.getPatrname());
		LogThreadContext.setSurName(profile.getSurname());
		LogThreadContext.setBirthday(profile.getBirthdate());
		LogThreadContext.setNumber(profile.getPassport());
		LogThreadContext.setDepartmentCode(profile.getTb());
	}

	/**
	 * ����� �������� ��������� ���� �� ������������� � ��������� �������������� ������������. ����� ���������� �� ����� ����� ��������, ������� �� �������� ������� ����� lifeTime
	 * @param operationClass ����� ��������
	 * @param ouid ������������ ��������
	 * @param lifeTime ����� ����� ��������/������� ������ � ��������
	 * @return ����������� �������� .
	 * @throws com.rssl.auth.csa.back.exceptions.OperationNotFoundException ���� �������� �� �������.
	 */
	public <T extends Operation> T findOperation(Class<T> operationClass, String ouid, int lifeTime) throws Exception
	{
		log.trace("�������� ��������������� �������� " + operationClass + " �� ������������� " + ouid);
		T operation = Operation.findLifeByOUID(operationClass, ouid, lifeTime);
		if (!operation.getProfileId().equals(getProfile().getId()))
		{
			throw new OperationNotFoundException("�������� " + ouid + " ����������� ������� ������������");
		}
		return operation;
	}

	/**
	 * ������� �������� ������������� �� ������ ��������������.
	 * ������������� ���������� �� AuthenticationOperation
	 * @param authToken ����� ��������������
	 * @return �������� �������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public static IdentificationContext createByAuthToken(String authToken) throws Exception
	{
		if (StringHelper.isEmpty(authToken))
			throw new IllegalArgumentException("����� �������������� ������ ���� �����");

		log.trace("�������� ������������� ������������ �� ������ �������������� " + authToken);
		AuthenticationOperation operation = AuthenticationOperation.findByAuthToken(authToken);
		if (operation == null)
			throw new IdentificationFailedException("�� ������� ������ � ������� " + authToken);

		Connector connector = operation.getConnector();
		IdentificationContext context = new IdentificationContext(operation.getProfile(), operation.getCbCode(), connector.getCardNumber(), connector.getUserId());
		log.trace("������������ ��������������� profileId=" + context.getProfile().getId());
		return context;
	}

	/**
	 * ��������� ������������ ������ ��������������
	 * @param authToken ����� ��������������
	 * @throws Exception ���������� ������
	 * @throws IdentificationFailedException ��� ������������� ������������� ������������
	 */
	public void checkAuthToken(String authToken) throws Exception
	{
		log.trace("�������� ��������������� �������� " + AuthenticationOperation.class + " �� ������������� " + authToken);
		AuthenticationOperation operation = AuthenticationOperation.findLifeByAuthToken(authToken);
		if (!operation.getProfileId().equals(getProfile().getId()))
			throw new OperationNotFoundException("�������� " + authToken + " ����������� ������� ������������");
	}
}
