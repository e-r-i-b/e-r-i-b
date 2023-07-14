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
 * Вычислитель блока ЕРИБ
 */
class EribNodeResolver
{
	/**
	 * Возвращает блок, в который разрешено добавлять клиентов
	 * @return наполняемый блок (never null)
	 */
	NodeInfo getNewUserAllowedNode() throws Exception
	{
		for (NodeInfo node: CSAResponseUtils.getNodesInfo())
		{
			if (node.isNewUsersAllowed())
				return node;
		}

		throw new ConfigurationException("Не найден наполняемый блок ЕРИБ");
	}

	/**
	 * Ищет блок по номеру телефона
	 * @param phoneNumber - номер телефона (never null)
	 * @return блок или null, если блок не найден
	 */
	NodeInfo getNodeByPhone(PhoneNumber phoneNumber) throws Exception
	{
		if (phoneNumber == null)
			throw new IllegalArgumentException("Не указан phoneNumber");

		try
		{
			String phoneAsString = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phoneNumber);
			Document response = CSABackRequestHelper.sendFindProfileNodeByPhoneRq(phoneAsString);
			return CSAResponseUtils.createNodeInfo(response.getDocumentElement());
		}
		catch (FailureIdentificationException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
		catch (AuthenticationFailedException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
		catch (UserNotFoundException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
		catch (ProfileNotFoundException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
	}

	/**
	 * Ищет блок по ФИО ДУЛ ДР
	 * @param userInfo - ФИО ДУЛ ДР клиента (never null)
	 * @return блок или null, если блок не найден
	 */
	NodeInfo getNodeByUserInfo(UserInfo userInfo) throws Exception
	{
		if (userInfo == null)
			throw new IllegalArgumentException("Не указан userInfo");

		try
		{
			Document response = CSABackRequestHelper.sendFindProfileNodeByUserInfoRq(userInfo, false, CreateProfileNodeMode.CREATION_DENIED);
			return CSAResponseUtils.createNodeInfo(response.getDocumentElement());
		}
		catch (FailureIdentificationException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
		catch (AuthenticationFailedException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
		catch (UserNotFoundException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
		catch (ProfileNotFoundException ignored)
		{
			// Исключение - признак того, что клиент не найден в ЦСА, поэтому игнорируем
			return null;
		}
	}
}
