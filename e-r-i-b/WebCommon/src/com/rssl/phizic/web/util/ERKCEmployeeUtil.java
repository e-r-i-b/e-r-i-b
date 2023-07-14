package com.rssl.phizic.web.util;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.csa.ProfileWithFullNodeInfo;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeContext;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeData;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCEmployeeUtil
{
	/**
	 * ¬озвращает дополнительные параметры
	 * @return параметры фильтрации
	 */
	public static Map<String, Object> getUserInfo()
	{
		ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
		if (erkcEmployeeData == null || erkcEmployeeData.getUserInfo() == null)
			return Collections.emptyMap();

		return new HashMap<String, Object>(erkcEmployeeData.getUserInfo());
	}

	/**
	 * ¬озвращает идентификатор логина клиента
	 * @return идентификатор логина клиента
	 */
	public static Long getUserLoginId()
	{
		ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
		if (erkcEmployeeData == null || erkcEmployeeData.getUserInfo() == null)
			return null;

		return erkcEmployeeData.getUserLoginId();
	}

	/**
	 * добавл€ем на форму данные о клиенте (дл€ построени€ левого меню)
	 * @param form форма на которую добавл€ем данные
	 * @param source источник данных
	 */
	public static void addUserDataForERKC(FilterActionForm form, Map<String, Object> source)
	{
		form.setField("personId",       source.get("personId"));
		form.setField("personFullName", source.get("personFullName"));
		form.setField("personStatus",   source.get("personStatus"));
		form.setField("creationType",   source.get("creationType"));
		form.setField("loginBlock",     source.get("loginBlock"));
	}

	/**
	 * @return информаци€ о других посещаемых клиентом блоках
	 */
	public static NodeInfo getClientAnotherNodeInfo()
	{
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
		if (erkcEmployeeData == null)
			return null;

		ProfileWithFullNodeInfo csaPersonInfo = erkcEmployeeData.getCsaPersonInfo();
		if (csaPersonInfo == null)
			return null;

		if (!NodeUtil.getCurrentNode().getId().equals(csaPersonInfo.getNodeId()))
			return nodeInfoConfig.getNode(csaPersonInfo.getNodeId());

		if (csaPersonInfo.getWaitMigrationNodeId() != null)
			return nodeInfoConfig.getNode(csaPersonInfo.getWaitMigrationNodeId());

		return null;
	}

	/**
	 * обновить данные о текущем функционале
	 * @param functional текущий функционал
	 */
	public static void setCurrentFunctionalInfo(Functional functional)
	{
		ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
		if (erkcEmployeeData == null)
			return;

		erkcEmployeeData.setCurrentFunctional(functional);
	}
}
