package com.rssl.phizic.business.csa;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhonesService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.RemoteIncognitoService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 26.03.2014
 * @ $Author$
 * @ $Revision$
 * ������, ��������������� ������ � ���������� ��������� � ���.
 */

public class IncognitoService
{
	private static final RemoteIncognitoService incognitoService = new RemoteIncognitoService();
	private static final IncognitoPhonesService incognitoInnerService = new IncognitoPhonesService();

	/**
	 * ���������� ������ ������� ���������.
	 *
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static void updateIncognitoPhones() throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		//���� � ������� ����������� ������� ��������� � �� ��� �� ��������� ���������� � ��� �����, �� ���� ��������� ������ ���������.
		if (personData.isIncognito() && !ConfigFactory.getConfig(UserPropertiesConfig.class).isIncognitoActual())
		{
			incognitoService.addPhones(personData.getPhones(true), ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			ConfigFactory.getConfig(UserPropertiesConfig.class).setIncognitoActual(true);
		}
	}

	private static void actualize(boolean incognito) throws BusinessException, BusinessLogicException
	{
		if (PersonContext.isAvailable())
			PersonContext.getPersonDataProvider().getPersonData().setIncognito(incognito);

		if (incognito)
		{
			ConfigFactory.getConfig(UserPropertiesConfig.class).setIncognitoActual(false);
			updateIncognitoPhones();
			for (String phone : PersonContext.getPersonDataProvider().getPersonData().getPhones(true))
				incognitoInnerService.add(phone, PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
		}
		else
		{
			incognitoService.deletePhones(PersonContext.getPersonDataProvider().getPersonData().getPhones(true), ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			incognitoInnerService.delete(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
		}
	}

	/**
	 * ������� ������� ��������� ��� �������
	 * @param sid ������������ ������ �������
	 * @param incognito ������� ���������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static void changeIncognitoSetting(String sid, boolean incognito) throws BusinessLogicException, BusinessException
	{
		try
		{
			CSABackRequestHelper.sendChangeIncognitoSettingRq(sid, incognito);
			actualize(incognito);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ������� ��������� ��� �������
	 * @param person ������
	 * @param incognito ������� ���������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static void changeIncognitoSetting(Person person, IncognitoState incognito) throws BusinessLogicException, BusinessException
	{
		try
		{
			UserInfo userInfo = PersonHelper.buildUserInfo(person);
			CSABackRequestHelper.sendChangeIncognitoSettingRq(userInfo, incognito);
			actualize(incognito == IncognitoState.incognito);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������� ��������� ��� �������.
	 * @param person ������
	 * @return �������
	 */

	public static boolean getIncognitoSetting(Person person) throws BusinessLogicException, BusinessException
	{
		try
		{
			UserInfo userInfo = PersonHelper.buildUserInfo(person);
			Document response = CSABackRequestHelper.sendGetIncognitoSettingRq(userInfo);
			return Boolean.parseBoolean(XmlHelper.getSimpleElementValue(response.getDocumentElement(), RequestConstants.INCOGNITO_SETTING_PARAM_NAME));
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������� ��������� ��� �������, ����������������� �������.
	 * @param sid  ������������ ������
	 * @return ������� ���������.
	 */
	public static boolean getIncognitoSettings(String sid) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document response = CSABackRequestHelper.sendGetIncognitoSettingRq(sid);
			return Boolean.parseBoolean(XmlHelper.getSimpleElementValue(response.getDocumentElement(), RequestConstants.INCOGNITO_SETTING_PARAM_NAME));
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}
}
