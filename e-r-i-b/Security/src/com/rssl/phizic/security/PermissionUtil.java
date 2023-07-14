package com.rssl.phizic.security;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.permissions.AccessSchemePermission;
import com.rssl.phizic.security.permissions.OperationPermission;
import com.rssl.phizic.security.permissions.ServicePermission;

/**
 * @author Dorzhinov
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class PermissionUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Имеет ли текущий пользователь доступ к услуге
	 * Проверяется доступ, полученный как непосредственно, так и через распространение (spread) операций
	 * @param serviceKey ключ услуги
	 * @return true если имеет
	 */
	public static boolean impliesService(String serviceKey)
	{
		return impliesService(serviceKey, false);
	}

	/**
	 * Имеет ли текущий пользователь доступ к услуге
	 * Проверяется доступ, полученный непосредственно
	 * @param serviceKey ключ услуги
	 * @return true если имеет
	 */
	public static boolean impliesServiceRigid(String serviceKey)
	{
		return impliesService(serviceKey, true);
	}

	private static boolean impliesService(String serviceKey, boolean rigid)
	{
		try
		{
			if (AuthModule.getAuthModule() == null)
				return false;
			return AuthModule.getAuthModule().implies(new ServicePermission(serviceKey, rigid));
		}
		catch (Exception e)
		{
			log.error("Ошибка получения доступа к сервису", e);
			return false;
		}
	}

	/**
	 * Имеет ли текущий пользователь доступ к операции редактирования шаблона
	 * @param service ключ сервиса
	 * @return true если имеет
	 */
	public static boolean impliesTemplateOperation(String service)
	{
		if (service.equals(FormType.JURIDICAL_TRANSFER.getName()))
		{
			return impliesOperation("EditJurPaymentTemplateOperation", service);
		}
		if (service.equals(FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER.getName()))
		{
			return impliesOperation("EditServicePaymentTemplateOperation", service);
		}
		return impliesOperation("EditTemplateOperation", service);
	}

	/**
	 * Имеет ли текущий пользователь доступ к операции
	 * @param key ключ операции
	 * @param service ключ сервиса
	 * @return true если имеет
	 */
	public static boolean impliesOperation(String key, String service)
	{
		try
		{
			if (AuthModule.getAuthModule() == null)
				return false;
			return AuthModule.getAuthModule().implies(new OperationPermission(key, service));
		}
		catch (Exception e)
		{
			log.error("Ошибка получения доступа к операции", e);
			return false;
		}
	}

	/**
	 * Имеет ли текущий пользователь доступ
	 * хотя бы к одной услуге входящей в схему
	 * @param schemeKey ключ схемы доступа
	 * @return true если имеет
	 */
	public static boolean impliesAccessScheme(String schemeKey)
	{
		try
		{
			if (AuthModule.getAuthModule() == null)
				return false;
			return AuthModule.getAuthModule().implies(new AccessSchemePermission(schemeKey));
		}
		catch (Exception e)
		{
			log.error("Ошибка получения доступа к схеме", e);
			return false;
		}
	}
}
