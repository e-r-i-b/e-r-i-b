package com.rssl.phizic.web.configure.policy;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.web.access.AssignAccessSubForm;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Evgrafov
 * @ created 18.04.2007
 * @ $Author: tisov $
 * @ $Revision: 72752 $
 */

@SuppressWarnings({"JavaDoc"})
public class EditPolicyTemplateForm extends EditFormBase
{
	private AssignAccessSubForm simple = new AssignAccessSubForm();
	private AssignAccessSubForm secure = new AssignAccessSubForm();

	private List<Department> terbanks;
	private List<AssignAccessHelper> helpers;
	private Map<String,String> schemesSBOL = new HashMap<String, String>();
	private Map<String,String> schemesUDBO = new HashMap<String, String>();

	private String schemeCARD;
	private String schemeCARD_TEMPORARY;
	private String schemeUDBO_TEMPORARY;
	private String schemeGUEST;

	public AssignAccessSubForm getSecureAccess()
	{
		return secure;
	}

	public AssignAccessSubForm getSimpleAccess()
	{
		return simple;
	}

	/**
	 * @return список головных подразделений
	 */
	public List<Department> getTerbanks()
	{
		return terbanks;
	}

	/**
	 * Установить список головнх подразделений
	 * @param terbanks список головных подразделений
	 */
	public void setTerbanks(List<Department> terbanks)
	{
		this.terbanks = terbanks;
	}

	/**
	 * @return список хелперов для установки схемы прав
	 */
	public List<AssignAccessHelper> getHelpers()
	{
		return helpers;
	}

	/**
	 * Установить список хелперов для установки схемы прав
	 * @param helpers список хелперов
	 */
	public void setHelpers(List<AssignAccessHelper> helpers)
	{
		this.helpers = helpers;
	}

	/**
	 * @return Мап<id департамента, id схемы прав доступа> для СБОЛ-клиентов
	 */
	public Map<String, String> getSchemesSBOL()
	{
		return schemesSBOL;
	}

	/**
	 * Установить схемы доступа для СБОЛ-клиентов
	 * @param schemesSBOL Мап<id департамента, id схемы прав доступа>
	 */
	public void setSchemesSBOL(Map<String, String> schemesSBOL)
	{
		this.schemesSBOL = schemesSBOL;
	}

	/**
	 * @return Мап<id департамента, id схемы прав доступа> для УДБО-клиентов
	 */
	public Map<String, String> getSchemesUDBO()
	{
		return schemesUDBO;
	}

	/**
	 * Установить схемы доступа для УДБО-клиентов
	 * @param schemesUDBO Мап<id департамента, id схемы прав доступа>
	 */
	public void setSchemesUDBO(Map<String, String> schemesUDBO)
	{
		this.schemesUDBO = schemesUDBO;
	}

	/**
	 * @return id схемы прав доступа для карточных клиентов
	 */
	public String getSchemeCARD()
	{
		return schemeCARD;
	}

	/**
	 * Установить схему прав доступа для карточных клиентов
	 * @param schemeCARD
	 */
	public void setSchemeCARD(String schemeCARD)
	{
		this.schemeCARD = schemeCARD;
	}

	/**
	 * @return идентификатор схемы прав доступа карточных клиентов из резервного блока
	 */
	public String getSchemeCARD_TEMPORARY()
	{
		return schemeCARD_TEMPORARY;
	}

	/**
	 * задать идентификатор схемы прав доступа карточных клиентов из резервного блока
	 * @param schemeCARD_TEMPORARY идентификатор
	 */
	public void setSchemeCARD_TEMPORARY(String schemeCARD_TEMPORARY)
	{
		this.schemeCARD_TEMPORARY = schemeCARD_TEMPORARY;
	}

	/**
	 * @return идентификатор схемы прав доступа УДБО клиентов из резервного блока
	 */
	public String getSchemeUDBO_TEMPORARY()
	{
		return schemeUDBO_TEMPORARY;
	}

	/**
	 * задать идентификатор схемы прав доступа УДБО клиентов из резервного блока
	 * @param schemeUDBO_TEMPORARY идентификатор
	 */
	public void setSchemeUDBO_TEMPORARY(String schemeUDBO_TEMPORARY)
	{
		this.schemeUDBO_TEMPORARY = schemeUDBO_TEMPORARY;
	}

	/**
	 * @return идентификатор схемы прав гостевого входа для оформления заявки на кредит, кредитную карту
	 */
	public String getSchemeGUEST()
	{
		return schemeGUEST;
	}

	/**
	 * задать идентификатор схемы прав гостевого входа для оформления заявки на кредит, кредитную карту
	 * @param schemeGUEST идентификатор
	 */
	public void setSchemeGUEST(String schemeGUEST)
	{
		this.schemeGUEST = schemeGUEST;
	}
}