package com.rssl.phizic.business.ermb.migration.onthefly.fpp;

import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Erkin
 * @ created 27.08.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг миграции на-лету Файла Прямого Потока (ФПП)
 */
public class FPPMigrationConfig extends BeanConfigBase<FPPMigrationConfigBean>
{
	private static final String CODENAME = "ErmbFPPMigrationConfig";

	private List<DepartmentIdentity> departments;
	private BigDecimal templateAmount;

	/**
	 * конструктор
	 */
	public FPPMigrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Содержит список подразделений, входящих в пилотную зону
	 * @return пилотная зона
	 */
	public List<DepartmentIdentity> getDepartments()
	{
		return departments;
	}

	/**
	 * @return ТБ пилотной зоны
	 */
	public String getPilotTb()
	{
		Set<String> tbs = new HashSet<String>();
		for (DepartmentIdentity department : departments)
		{
			tbs.add(department.getTb());
		}
		if (tbs.size() != 1)
			throw new ConfigurationException("Количество тербанков пилотной зоны должно быть равно 1");

		return tbs.iterator().next();
	}

	/**
	 * Устанавливает список подразделений, входящих в пилотную зону
	 * @param departments - список подразделений
	 */
	public void setDepartments(List<DepartmentIdentity> departments){
		this.departments = departments;
	}

	/**
	 * @return дефолтная сумма шаблона при конвертации МБК->ЕРИБ
	 */
	public BigDecimal getTemplateAmount()
	{
		return templateAmount;
	}

	@Override
	protected String getCodename()
	{
		return CODENAME;
	}

	@Override
	protected Class getConfigDataClass()
	{
		return FPPMigrationConfigBean.class;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		FPPMigrationConfigBean configBean =  getConfigData();
		departments = configBean.getDepartments();
		templateAmount = configBean.getTemplateAmount();
	}

	@Override
	protected <T> T doSave()
	{
		FPPMigrationConfigBean configBean =  getConfigData();

		configBean.setDepartments(departments);
		configBean.setTemplateAmount(templateAmount);

		return (T) configBean;
	}
}
