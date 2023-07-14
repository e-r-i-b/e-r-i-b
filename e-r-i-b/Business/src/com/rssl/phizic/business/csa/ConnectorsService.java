package com.rssl.phizic.business.csa;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.person.Person;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 27.03.2014
 * @ $Author$
 * @ $Revision$
 *  Сервис работы с коннекторами ЦСА.
 */

public class ConnectorsService
{
	/**
	 * Получить список незакрытых MAPI-коннекторов клиента
	 *
	 * @param person клиент
	 * @return список клиентов или пустой список
	 */
	public static List<ConnectorInfo> getClientMAPIConnectors(Person person) throws BusinessException, BusinessLogicException
	{
		return getClientMAPIConnectors(PersonHelper.buildUserInfo(person));
	}


	/**
	 * Получить список незакрытых MAPI-коннекторов клиента
	 *
	 * @param userInfo информация о пользователе
	 * @return список клиентов или пустой список
	 */
	public static List<ConnectorInfo> getClientMAPIConnectors(UserInfo userInfo) throws BusinessException, BusinessLogicException
	{
		try
		{
			return CSABackResponseSerializer.getConnectorInfos(CSABackRequestHelper.sendGetClientConnectorsRq(userInfo, ConnectorInfo.Type.MAPI));
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список незакрытых SocialAPI-коннекторов клиента
	 *
	 * @param person клиент
	 * @return список клиентов или пустой список
	 */
	public static List<ConnectorInfo> getClientSocialAPIConnectors(Person person) throws BusinessException, BusinessLogicException
	{
		try
		{
			UserInfo userInfo = PersonHelper.buildUserInfo(person);
			return CSABackResponseSerializer.getConnectorInfos(CSABackRequestHelper.sendGetClientConnectorsRq(userInfo, ConnectorInfo.Type.SOCIAL));
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список незакрытых MAPI-коннекторов клиента, идентифицируемого сессией.
	 *
	 * @param sid  идентифкатор сессии
	 * @return список клиентов или пустой список
	 */
	public static List<ConnectorInfo> getClientMAPIConnectors(String sid) throws BusinessLogicException, BusinessException
	{
		try
		{
			return CSABackResponseSerializer.getConnectorInfos(CSABackRequestHelper.sendGetClientConnectorsRq(sid, ConnectorInfo.Type.MAPI));
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список незакрытых SocialAPI-коннекторов клиента, идентифицируемого сессией.
	 *
	 * @param sid  идентифкатор сессии
	 * @return список клиентов или пустой список
	 */
	public static List<ConnectorInfo> getClientSocialAPIConnectors(String sid) throws BusinessLogicException, BusinessException
	{
		try
		{
			return CSABackResponseSerializer.getConnectorInfos(CSABackRequestHelper.sendGetClientConnectorsRq(sid, ConnectorInfo.Type.SOCIAL));
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Имеет ли клиент незакрытые MAPI-коннекторы
	 * @param person клиент
	 * @return да/нет
	 * @throws BusinessException
	 */
	public static boolean hasClientMAPIConnectors(Person person) throws BusinessException, BusinessLogicException
	{
		return CollectionUtils.isNotEmpty(getClientMAPIConnectors(person));
	}

	/**
	 * Отключить коннектор
	 * @param guid guid коннектора
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static void closeMobileConnector(String guid) throws BusinessLogicException, BusinessException
	{
		try
		{
			CSABackRequestHelper.sendCancelMobileRegistrationRq(guid);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

    /**
	 * Отключить коннектор
	 * @param guid guid коннектора
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static void closeSocialConnector(String guid) throws BusinessLogicException, BusinessException
	{
		try
		{
			CSABackRequestHelper.sendCancelSocialRegistrationRq(guid);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}
}
