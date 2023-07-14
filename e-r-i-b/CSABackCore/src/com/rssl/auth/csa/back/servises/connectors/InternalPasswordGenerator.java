package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import org.apache.commons.logging.Log;
import org.hibernate.Session;

import java.util.Set;

/**
 * @author krenev
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 * ��������� ������ �� ������ ��������� ���������� �������� � �����.
 * ��������������� ������ ���������������� � ���������� � �������� ��������� ���������� � ����������� � ���.
 *
 * �� ������ �����������(������� �.�.):
 * ��������� �������� �������������, ��� ����������� � ����������� ���������������:
 * 1)	��������� ������ ������
 * 2)	��������� ������� ������ �� ����� � ����������� � ��
 * 3)	�������� ������ ������ �� �������� �������
 * ��� ���� �� ����� �������� �� ����� �������� �� ������ ������� ��� �������, �� ����� �� ������.
 */
public class InternalPasswordGenerator implements PasswordGenerator
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	//������������ ���������� ������� ������������� ������, ��������������� ����������� ������������. ���� ���������� - ����������(������ �� ������������)
	private static final int MAX_GENERATE_PASSWORDS_AATEMPTS_LIMIT = 10000;

	private String allowedChars;
	private int length;
	private PasswordBasedConnector[] connectors;
	private Set<String> excludedPhones;

	public InternalPasswordGenerator(String allowedChars, int length, PasswordBasedConnector... connectors)
	{
		this.allowedChars = allowedChars;
		this.length = length;
		this.connectors = connectors;
	}

	public InternalPasswordGenerator(String allowedChars, int length, Set<String> excludedPhones, PasswordBasedConnector... connectors)
	{
		this(allowedChars, length, connectors);
		this.excludedPhones = excludedPhones;
	}

	public void generatePassword() throws Exception
	{
		for (int i = 0; i < MAX_GENERATE_PASSWORDS_AATEMPTS_LIMIT; i++)
		{
			final String password = RandomHelper.rand(length, allowedChars);
			try
			{
				ActiveRecord.executeAtomic(new HibernateAction<Void>()
				{

					public Void run(Session session) throws Exception
					{
						for (PasswordBasedConnector connector : connectors)
						{
							connector.setPassword(password);
						}
						return null;
					}
				});
			}
			catch (RestrictionException ignored)
			{
				continue;
			}

			sendGeneratedPassword(password, excludedPhones);
			return;
		}
		throw new ConfigurationException("�� ������� �������������� ������. ��������� ��������� ��������� ������ �� ������������ ������ ������������");
	}

	public String generateDisposablePassword(boolean sendSMS) throws Exception
	{
		for (int i = 0; i < MAX_GENERATE_PASSWORDS_AATEMPTS_LIMIT; i++)
		{
			final String password = RandomHelper.rand(length, allowedChars);
			try
			{
				ActiveRecord.executeAtomic(new HibernateAction<Void>()
				{

					public Void run(Session session) throws Exception
					{
						for (PasswordBasedConnector connector : connectors)
						{
							connector.setPassword(password);
							((DisposableConnector) connector).setDisposablePass(password);
						}
						return null;
					}
				});
			}
			catch (RestrictionException ignored)
			{
				continue;
			}

			if (sendSMS)
				sendDisposablePassword(password);
			return password;
		}
		throw new ConfigurationException("�� ������� �������������� ������. ��������� ��������� ��������� ������ �� ������������ ������ ������������");
	}

	public void generateDisposableLogin() throws Exception
	{
		for (int i = 0; i < MAX_GENERATE_PASSWORDS_AATEMPTS_LIMIT; i++)
		{
			final String login = "Z" + RandomHelper.rand(length, allowedChars);
			try
			{
				ActiveRecord.executeAtomic(new HibernateAction<Void>()
				{

					public Void run(Session session) throws Exception
					{
						for (PasswordBasedConnector connector : connectors)
						{
							String connectorLogin = connector.getLogin();
							// �� ������������ ������ �� ��������� ���� ������ ��-��������
							if (connectorLogin == null)
								Login.createDisposableLogin(login, connector.getId());
							else
							{
								Login.updateLogin(login, connectorLogin);
								connector.clearStoredLogin();
							}
						}

						return null;
					}
				});
			}
			catch (RestrictionException ignored)
			{
				continue;
			}

			return;
		}
		throw new ConfigurationException("�� ������� �������������� �����. ��������� ��������� ��������� ������ �� ������������ ������ ������������");
	}

	public DisposableConnector generateDisposableConnector(final String userId, final String cbCode, final String cardNumber, final Profile profile, final RegistrationType registrationType, final boolean sendSMS) throws Exception
	{
		for (int i = 0; i < MAX_GENERATE_PASSWORDS_AATEMPTS_LIMIT; i++)
		{
			try
			{
				return ActiveRecord.executeAtomic(new HibernateAction<DisposableConnector>()
				{

					public DisposableConnector run(Session session) throws Exception
					{

						DisposableConnector connector = new DisposableConnector(userId, cbCode, cardNumber, profile, registrationType);
						connector.save();
                        session.flush();
						connector.generateDisposableLogin();
						connector.generateDisposablePassword(sendSMS);
						return connector;
					}
				});
			}
			catch (RestrictionException ignored)
			{
				continue;
			}
		}
		throw new ConfigurationException("�� ������� �������������� �����. ��������� ��������� ��������� ������ �� ������������ ������ ������������");
	}

	private void sendGeneratedPassword(String password, Set<String> excludedPhones) throws Exception
	{
		String smsTextKey = "com.rssl.auth.csa.back.sms.password.generated";

		String text       = CSASmsResourcesOperation.getFormattedSmsResourcesText(smsTextKey, password);
		String textToLog  = CSASmsResourcesOperation.getFormattedSmsResourcesText(smsTextKey, CSASmsResourcesOperation.PASSWORD_MASK);

		for (PasswordBasedConnector connector : connectors)
		{
			String cardNumber = connector.getCardNumber();
			try
			{
				String stubText = CSASmsResourcesOperation.getFormattedSmsResourcesText(getClass().getName() + ".stubText");
				SendMessageInfo sendMessageInfo = new SendMessageInfo(connector.getProfile().getId(), cardNumber, new MessageInfoImpl(text, textToLog, stubText), true, GetRegistrationMode.BOTH, excludedPhones);

				SendMessageRouter.getInstance().sendMessage(sendMessageInfo);
			}
			catch (Exception e)
			{
				log.error("������ �������� ��� � ����� ��������������� ������� �� ����� �������� �� ����� " + Utils.maskCard(cardNumber), e);
			}
		}
	}

	private void sendDisposablePassword(String password) throws Exception
	{
		String smsTextKey = "com.rssl.auth.csa.back.sms.disposablePassword.generated";

		String text = CSASmsResourcesOperation.getFormattedSmsResourcesText(smsTextKey, password);
		String textToLog = CSASmsResourcesOperation.getFormattedSmsResourcesText(smsTextKey, CSASmsResourcesOperation.PASSWORD_MASK);

		for (PasswordBasedConnector connector : connectors)
		{
			String cardNumber = connector.getCardNumber();
			try
			{
				String stubText = CSASmsResourcesOperation.getFormattedSmsResourcesText(getClass().getName() + ".stubText");
				SendMessageInfo sendMessageInfo = new SendMessageInfo(connector.getProfile().getId(), cardNumber, new MessageInfoImpl(text, textToLog, stubText), true, GetRegistrationMode.BOTH);

				SendMessageRouter.getInstance().sendMessage(sendMessageInfo);
			}
			catch (MobileBankRegistrationNotFoundException e)
			{
				log.error("������ �������� ��� � ����� ��������������� ������� �� ����� �������� �� ����� " + Utils.maskCard(cardNumber), e);
				throw new MobileBankRegistrationNotFoundException(e.getMessage());
			}
			catch (Exception e)
			{
				log.error("������ �������� ��� � ����� ��������������� ������� �� ����� �������� �� ����� " + Utils.maskCard(cardNumber), e);
			}
		}
	}
}
