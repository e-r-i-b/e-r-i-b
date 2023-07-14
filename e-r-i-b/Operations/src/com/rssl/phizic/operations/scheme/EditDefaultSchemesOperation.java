	package com.rssl.phizic.operations.scheme;

	import com.rssl.phizic.auth.SecurityService;
	import com.rssl.phizic.business.BusinessException;
	import com.rssl.phizic.business.departments.Department;
	import com.rssl.phizic.business.operations.Transactional;
	import com.rssl.phizic.business.schemes.DefaultAccessScheme;
	import com.rssl.phizic.business.schemes.DefaultAccessSchemeService;
	import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
	import com.rssl.phizic.common.types.client.DefaultSchemeType;
	import com.rssl.phizic.dataaccess.DataAccessException;
	import com.rssl.phizic.dataaccess.query.Query;
	import com.rssl.phizic.operations.OperationBase;
	import com.rssl.phizic.operations.access.AssignAccessHelper;
	import com.rssl.phizic.utils.StringHelper;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

/**
 * @author gladishev
 * @ created 07.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditDefaultSchemesOperation extends OperationBase
{
	private static final DefaultAccessSchemeService defSchemeService = new DefaultAccessSchemeService();

	private List<AssignAccessHelper> helpers;
	private List<Department> terbanks;
	private Map<String, String> schemesSBOL = new HashMap<String, String>();
	private Map<String, String> schemesUDBO = new HashMap<String, String>();

	private String schemeCARD;
	private String schemeUDBO_TEMPORARY;
	private String schemeCARD_TEMPORARY;
	private String schemeGUEST;

	/**
	 * Инициализация операции текущими значениями
	 */
	public void initializeNew() throws BusinessException
	{
		helpers  = AccessHelper.createAssignAccessHelpers(SecurityService.SCOPE_CLIENT, false);
		terbanks = AllowedDepartmentsUtil.getAllowedTerbanks();
		initSchemes();
	}

	/**
	 * Инициализация операции значениями прав, выбранными администратором
	 * @param schemesSBOL - Мап<id департамента, id схемы прав доступа> для СБОЛ-клиентов
	 * @param schemesUDBO - Мап<id департамента, id схемы прав доступа> для УДБО-клиентов
	 * @param schemeCARD - id схемы прав доступа для карточных клиентов
	 * @param schemeCARD_TEMPORARY - id схемы прав доступа для карточных клиентов из резервного блока
	 * @param schemeUDBO_TEMPORARY - id схемы прав доступа для УДБО клиентов из резервного блока
	 * @param schemeGUEST_LOAN id схемы прав гостевого входа для оформления заявки на кредит, кредитную карту
	 * @throws BusinessException
	 */
	public void initialize(Map<String, String> schemesSBOL, Map<String, String> schemesUDBO, String schemeCARD, String schemeCARD_TEMPORARY, String schemeUDBO_TEMPORARY, String schemeGUEST_LOAN)
			throws BusinessException
	{
		this.schemesSBOL = new HashMap<String, String>(schemesSBOL);
		this.schemesUDBO = new HashMap<String, String>(schemesUDBO);
		this.schemeCARD  = schemeCARD;
		this.schemeCARD_TEMPORARY = schemeCARD_TEMPORARY;
		this.schemeUDBO_TEMPORARY = schemeUDBO_TEMPORARY;
		this.schemeGUEST = schemeGUEST_LOAN;
		terbanks = AllowedDepartmentsUtil.getAllowedTerbanks();
	}

	private List<DefaultAccessScheme> getDefaultSchemes() throws BusinessException
	{
		List<String> tbs = new ArrayList<String>();
		for (Department dep: terbanks)
		{
			tbs.add(dep.getRegion());
		}

		Query query = createQuery("getSchemesByEmployee");
		query.setParameter("tbs", tbs);
		try
		{
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	private void initSchemes() throws BusinessException
	{
		for (DefaultAccessScheme scheme : getDefaultSchemes())
		{
			String tb = scheme.getDepartmentTb();

			DefaultSchemeType creationType = scheme.getCreationType();
			switch (creationType)
			{
				case SBOL:
					schemesSBOL.put(tb, String.valueOf(scheme.getAccessSchemeId()));
					break;
				case UDBO:
					schemesUDBO.put(tb, String.valueOf(scheme.getAccessSchemeId()));
					break;
				case CARD:
					schemeCARD = String.valueOf(scheme.getAccessSchemeId());
					break;
				case CARD_TEMPORARY:
					schemeCARD_TEMPORARY = String.valueOf(scheme.getAccessSchemeId());
					break;
				case UDBO_TEMPORARY:
					schemeUDBO_TEMPORARY = String.valueOf(scheme.getAccessSchemeId());
					break;
				case GUEST:
					schemeGUEST = String.valueOf(scheme.getAccessSchemeId());
					break;
			   default: throw new BusinessException("Недопустимое значение типа создания (CreationType)");
			}
		}
	}

	/**
	 * @return список хелперов для установки схемы прав
	 */
	public List<AssignAccessHelper> getHelpers()
	{
		return helpers;
	}

	/**
	 * @return список ТБ
	 */
	public List<Department> getTerbanks()
	{
		return terbanks;
	}

	/**
	 * @return Мап<id департамента, id схемы прав доступа> для СБОЛ-клиентов
	 */
	public Map<String, String> getSchemesSBOL()
	{
		return schemesSBOL;
	}

	/**
	 * @return Мап<id департамента, id схемы прав доступа> для УДБО-клиентов
	 */
	public Map<String, String> getSchemesUDBO()
	{
		return schemesUDBO;
	}

	/**
	 * @return id схемы прав доступа для карточных клиентов
	 */
	public String getSchemeCARD()
	{
		return schemeCARD;
	}

	/**
	 * @return id схемы прав доступа для карточных клиентов в резервном блоке
	 */
	public String getSchemeCARD_TEMPORARY()
	{
		return schemeCARD_TEMPORARY;
	}

	/**
	 * @return id схемы прав доступа для УДБО клиентов в резервном блоке
	 */
	public String getSchemeUDBO_TEMPORARY()
	{
		return schemeUDBO_TEMPORARY;
	}

	/**
	 * @return id схемы прав гостевого входа для оформления заявки на кредит, кредитную карту
	 */
	public String getSchemeGUEST()
	{
		return schemeGUEST;
	}

	/**
	 * сохранение установленных схем прав доступа
	 */
	@Transactional
	public void save() throws BusinessException
	{
		List<DefaultAccessScheme> defaultSchemes = getDefaultSchemes();
		boolean schemesCARDUpdated = false;
		boolean schemeCARD_TEMPORARYUpdated = false;
		boolean schemeUDBO_TEMPORARYUpdated = false;
		boolean schemeGUEST_LOANUpdated = false;

		//обновляем находящиеся в базе записи новыми значениями
		for (DefaultAccessScheme scheme : defaultSchemes)
		{
			String tb = scheme.getDepartmentTb();
			DefaultSchemeType creationType = scheme.getCreationType();

			switch (creationType)
			{
				case SBOL:
					updateScheme(scheme, schemesSBOL.get(tb));
					schemesSBOL.remove(tb);
					break;
				case UDBO:
					updateScheme(scheme, schemesUDBO.get(tb));
					schemesUDBO.remove(tb);
					break;
				case CARD:
					updateScheme(scheme, schemeCARD);
					schemesCARDUpdated = true;
					break;
				case CARD_TEMPORARY:
					updateScheme(scheme, schemeCARD_TEMPORARY);
					schemeCARD_TEMPORARYUpdated = true;
					break;
				case UDBO_TEMPORARY:
					updateScheme(scheme, schemeUDBO_TEMPORARY);
					schemeUDBO_TEMPORARYUpdated = true;
					break;
				case GUEST:
					updateScheme(scheme, schemeGUEST);
					schemeGUEST_LOANUpdated = true;
					break;
			   default: throw new BusinessException("Недопустимое значение типа создания (CreationType)");
			}
		}

		//добавляем оставшиеся схемы
		addSchemes(schemesSBOL, DefaultSchemeType.SBOL);
		addSchemes(schemesUDBO, DefaultSchemeType.UDBO);

		if (!schemesCARDUpdated)
			addScheme(parseSchemeId(schemeCARD), DefaultSchemeType.CARD, null);

		if (!schemeCARD_TEMPORARYUpdated)
			addScheme(parseSchemeId(schemeCARD_TEMPORARY), DefaultSchemeType.CARD_TEMPORARY, null);

		if (!schemeUDBO_TEMPORARYUpdated)
			addScheme(parseSchemeId(schemeUDBO_TEMPORARY), DefaultSchemeType.UDBO_TEMPORARY, null);

		if (!schemeGUEST_LOANUpdated)
			addScheme(parseSchemeId(schemeGUEST), DefaultSchemeType.GUEST, null);
	}

	private void addSchemes(Map<String, String> schemes, DefaultSchemeType creationType) throws BusinessException
	{
		if (schemes.isEmpty())
			return;

		for (Map.Entry<String, String> entry : schemes.entrySet())
		{
			if (StringHelper.isEmpty(entry.getValue()))
				continue;

			addScheme(parseSchemeId(entry.getValue()), creationType, entry.getKey());
		}
	}

	private void addScheme(Long schemeId, DefaultSchemeType creationType, String tb) throws BusinessException
	{
		DefaultAccessScheme scheme = new DefaultAccessScheme();
		scheme.setCreationType(creationType);
		scheme.setDepartmentTb(tb);
		save(scheme, schemeId);
	}

	private void updateScheme(DefaultAccessScheme scheme, String newSchemeId) throws BusinessException
	{
		Long schemeId = parseSchemeId(newSchemeId);
		if (scheme.getAccessSchemeId().equals(schemeId))
			return;

		if (schemeId == null)
			remove(scheme);
		else
			save(scheme, schemeId);
	}

	private void save(DefaultAccessScheme scheme, Long schemeId) throws BusinessException
	{
		scheme.setAccessSchemeId(schemeId);
		defSchemeService.addOrUpdate(scheme);
	}

	private void remove(DefaultAccessScheme scheme) throws BusinessException
	{
		defSchemeService.remove(scheme);
	}

	private Long parseSchemeId(String schemeId)
	{
		if (StringHelper.isEmpty(schemeId))
			return null;

		return Long.parseLong(schemeId);
	}
}
