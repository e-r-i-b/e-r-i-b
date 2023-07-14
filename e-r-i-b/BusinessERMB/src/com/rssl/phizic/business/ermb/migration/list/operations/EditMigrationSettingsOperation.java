package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.onthefly.fpp.DepartmentIdentity;
import com.rssl.phizic.business.ermb.migration.onthefly.fpp.FPPMigrationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Nady
 * @ created 16.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция редактирования настроек миграции
 */
public class EditMigrationSettingsOperation extends OperationBase
{
	private static final String ERROR_SYMBOL_MESSAGE="Не правильный код подразделения в позиции: %s";
	private static final String CODE_DELIMITER = "-";

	private final FPPMigrationConfig config = (FPPMigrationConfig) ConfigFactory.getConfig(FPPMigrationConfig.class);

	/**
	 * Получить список подразделений
	 * @return Список подразделений в формате ТБ-ОСБ-ВСП
	 */
	public List<String> getListDepartments()
	{
		return listDeparmentsToListString(config.getDepartments());
	}

	/**
	 * Устанавливает в конфиг список департаментов по входной строке. Для сохранения, необходимо вызвать метод save операции
	 * @param list - строка с перечисленными через запятую департаментами а формате ТБ-ОСБ-ВСП
	 * @throws BusinessLogicException
	 */
	public void setListDepartments(String list) throws BusinessLogicException
	{
		config.setDepartments(parse(list));
	}

	/**
	 * Сохраняет список подразделений  в конфиг
	 */
	public void save()
	{
		config.save();
	}

	private List<DepartmentIdentity> parse(String depts) throws BusinessLogicException
	{
		List<DepartmentIdentity> departmentIdentities = new ArrayList<DepartmentIdentity>();
		String[] departments = depts.split(",");
		for (String department: departments)
		{
			String[] codes = department.split("-");
			if (codes.length != 3)
				throw new BusinessLogicException(
					"String.format(ERROR_SYMBOL_MESSAGE, depts.indexOf(department))"
				);
			String tb = codes[0].replace(" ","");
			String osb = codes[1].replace(" ","");
			String vsp = codes[2].replace(" ","");
			if (StringUtils.isEmpty(tb) || !(Pattern.matches("\\d+||\\*", tb)))
				throw new BusinessLogicException(
						String.format(ERROR_SYMBOL_MESSAGE, depts.indexOf(tb))
				);
			if (StringUtils.isEmpty(osb) || !(Pattern.matches("\\d+||\\*", osb)))
				throw new BusinessLogicException(
						String.format(ERROR_SYMBOL_MESSAGE, depts.indexOf(osb))
				);
			if (StringUtils.isEmpty(vsp) || !(Pattern.matches("\\d+||\\*", vsp)))
				throw new BusinessLogicException(
						String.format(ERROR_SYMBOL_MESSAGE, depts.indexOf(vsp))
				);
			DepartmentIdentity di = new DepartmentIdentity();

			di.setTb(tb);
			di.setOsb(osb);
			di.setVsp(vsp);
			departmentIdentities.add(di);
		}
		return departmentIdentities;
	}

	private List<String> listDeparmentsToListString(List<DepartmentIdentity> departmentIdentities)
	{
		List<String> result = new ArrayList<String>(departmentIdentities.size());
		for(DepartmentIdentity department : departmentIdentities)
		{
			result.add(department.getTb() + CODE_DELIMITER + department.getOsb() + CODE_DELIMITER + department.getVsp());
		}
		return result;
	}
}
