package com.rssl.phizic.operations.mail.area;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.mail.area.ContactCenterAreaService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Редактирует площадку КЦ
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditAreaOperation  extends EditDictionaryEntityOperationBase<ContactCenterArea, Restriction>
{
	private static final String DEPARTMENT_CONSTRAINT = "I_C_CENTER_AREAS_DEPARTMENTS";
	private static final String NAME_CONSTRAINT       = "I_CONTACT_CENTER_AREAS_NAME";
	private ContactCenterArea area;
	private ContactCenterAreaService service = new ContactCenterAreaService();

	/**
	 * Инициализирует сущность площадка КЦ
	 */
	public void initialize() throws BusinessException
	{
		area = new ContactCenterArea();
	}

	/**
	 * Инициализирует сущность площадка КЦ по идентификатору
	 * @param id идентификатор
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		area = service.getById(id, getInstanceName());
		if(area == null)
			throw new BusinessLogicException("Площадка контактного центра с id="+id+" не найдена.");

		if(!AllowedDepartmentsUtil.isAllowedTerbanksNumbers(area.getDepartments()))
        {
		   throw new AccessException("Вы не можете отредактировать данную площадку,"+
				     " так как не имеете доступа ко всем подразделениям банка, для которых она была создана.");
	    }
	}

	/**
	 * Поиск тербанков для которых уже созданы Площадки КЦ.
	 * @return Тербанки
	 * @throws BusinessException
	 */
	private String getDublicateTRB() throws BusinessException
	{
		List<String> departmentsWithPanels = service.findTRBWithCCA(getInstanceName());
		StringBuilder s = new StringBuilder((departmentsWithPanels.size() > 1 ? "ов": "а"));
		boolean first = true;
		for(String department : departmentsWithPanels)
		{
			s.append((first ? " " : ", "));
			s.append(department);
			first = false;
		}
		return s.toString();
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			service.addOrUpdate(area, getInstanceName());
		}
		catch(ConstraintViolationException cve)
		{
			if(cve.getCause().getMessage().toUpperCase().contains(DEPARTMENT_CONSTRAINT))
			{
				throw new BusinessLogicException("Площадка для тербанк"+ getDublicateTRB() +" уже назначена. Пожалуйста, измените список тербанков.", cve);
			}

			if(cve.getCause().getMessage().toUpperCase().contains(NAME_CONSTRAINT))
			{
				throw new BusinessLogicException("Площадка КЦ с таким названием уже существует. Пожалуйста, введите другое название.", cve);
			}
		}

	}

	public ContactCenterArea getEntity() throws BusinessException, BusinessLogicException
	{
		return area;
	}

	/**
	 * Устанавливает депертаменты по departmentIds
	 * @param departmentTbs номера департаментов
	 * @throws BusinessException
	 */
	public void setDepartments(String[] departmentTbs) throws BusinessException
	{
		area.setDepartments(new HashSet<String>(Arrays.asList(departmentTbs)));
	}
}
