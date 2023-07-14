package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @author Dorzhinov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class GenericWidgetAccessor implements WidgetAccessor
{
	public final boolean access(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException
	{
		boolean rc = widgetDefinition.isAvailability();
		rc = rc && impliesWidgetPermission(widgetDefinition);
		rc = rc && impliesMainPermission(widgetDefinition);
		rc = rc && checkData(widgetDefinition);
		return rc;
	}

	/**
	 * Проверяет наличие доступа к виджет-операции
	 * @param widgetDefinition - виджет-дефиниция
	 * @return true, если есть доступ к виджет-операции
	 */
	private boolean impliesWidgetPermission(WidgetDefinition widgetDefinition)
	{
		return PermissionUtil.impliesOperation(widgetDefinition.getOperation(), null);
	}

	/**
	 * Проверяет наличие доступа к функциональности, задействованной в виджете
	 * @param widgetDefinition - виджет-дефиниция
	 * @return true, если есть доступ к основной функциональности
	 */
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		return true;
	}

	/**
	 * Проверяет наличие ресурсов для виджета
	 * @param widgetDefinition - виджет-дефиниция
	 * @return true, если для виджета есть необходимые данные
	 */
	protected boolean checkData(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException
	{
		return true;
	}
}
