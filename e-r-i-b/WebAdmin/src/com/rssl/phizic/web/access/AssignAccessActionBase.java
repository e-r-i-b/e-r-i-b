package com.rssl.phizic.web.access;

import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.PersonalAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.access.AssignAccessOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: basharin $
 * @ $Revision: 58022 $
 */

@SuppressWarnings({"JavaDoc"})
public abstract class AssignAccessActionBase extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Start";

	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.save", "save");
		return map;
	}

	/**
	 * сохранение значений установленных пользователем.
	 * @param frm
	 * @param operation
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void doOperation(AssignAccessSubForm frm, AssignAccessOperation operation) throws BusinessException, BusinessLogicException
	{
		String schemeIdStr = frm.getAccessSchemeId();
		AccessScheme currentAccessScheme = operation.getCurrentAccessScheme();

		if(schemeIdStr == null || schemeIdStr.equals(""))
		{
			operation.setNewAccessSchemeId(null);
		}
		else if(schemeIdStr.startsWith("personal"))
		{
			operation.setNewCategory(frm.getCategory());
			operation.setPersonalScheme(frm.getSelectedServicesList());
		}
		else
		{
			Long schemeId = Long.valueOf(schemeIdStr);
			operation.setNewAccessSchemeId(schemeId);
		}

		boolean enabled = frm.getEnabled();

		operation.setNewAccessTypeAllowed(enabled);
		if(enabled)
		{
			Set<Map.Entry<String,Object>> entries = frm.getProperties().entrySet();
			for (Map.Entry<String, Object> entry : entries)
			{
				operation.setNewProperty(entry.getKey(), (String) entry.getValue());
			}
		}

		operation.assign();

		addLogParametersAfterSave(operation, currentAccessScheme);
	}

	/**
	 * Заполнение формы в соотвествии с существующими настройками.
	 * @param frm
	 * @param person
	 * @param operation
	 * @throws BusinessException
	 */
	public void updateForm(AssignPersonAccessForm frm, ActivePerson person, AssignAccessOperation<?> operation) throws BusinessException
	{
		AssignAccessSubForm subForm = frm.getSimpleAccess();
		subForm.setHelpers(operation.getHelpers());
		subForm.setOperationsByServiceMap(operation.getServicesTuple());
		subForm.setPolicy(operation.getPolicy());

		AccessScheme userScheme = operation.getCurrentAccessScheme();
		if (userScheme != null)
		{
			if (userScheme instanceof PersonalAccessScheme)
			{
				subForm.setAccessSchemeId("personal" + userScheme.getCategory());
			}
			else
			{
				subForm.setAccessSchemeId(userScheme.getId().toString());
			}

			List<Service> availableServices = userScheme.getServices();

			Long[] temp = new Long[availableServices.size()];
			Long[] caAdminTemp = new Long[availableServices.size()];
			int i = 0;
			int j = 0;
			for (Service s : availableServices)
			{
				temp[i++] = s.getId();
				if (s.isCaAdminService())
					caAdminTemp[j++] = s.getId();
			}
			subForm.setSelectedServices(temp);
			subForm.setCaadminServices(caAdminTemp);
		}
		else
		{
			subForm.setSelectedServices(new Long[0]);
			subForm.setCaadminServices(new Long[0]);
		}

		subForm.setEnabled(operation.getAccessTypeAllowed());
		subForm.setProperties((Map<String, Object>) operation.getProperties().clone());
		subForm.setCategory(operation.getCategory());
		if (person.getUserRegistrationMode() == null)
			frm.setUserRegistrationMode(UserRegistrationMode.DEFAULT.toString());
		else
			frm.setUserRegistrationMode(person.getUserRegistrationMode().toString());

		addLogParametersBeforeSave(operation);
	}

	private void addLogParametersBeforeSave(AssignAccessOperation<?> operation) throws BusinessException
	{
		addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Политика доступа", operation.getPolicy()),
					new SimpleLogParametersReader("Состояние политики", operation.getAccessTypeAllowed() == true ? "Разрешена" : "Запрещена")
				));

		AccessScheme accessScheme = operation.getCurrentAccessScheme();

		if (accessScheme == null)
		{
			addLogParameters(new SimpleLogParametersReader("Схема прав", "Нет схемы прав"));
		}
		else
		{
			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Схема прав", accessScheme),
					new CollectionLogParametersReader("Разрешенные сервисы" , accessScheme.getServices())
				));
		}
	}

	private void addLogParametersAfterSave(AssignAccessOperation operation, AccessScheme currentAccessScheme) throws BusinessException
	{
		if (operation.getPolicy()!=null)
		{
			addLogParameters(new CompositeLogParametersReader(
						new BeanLogParemetersReader("Политика доступа", operation.getPolicy()),
						new SimpleLogParametersReader("Состояние политики", operation.getAccessTypeAllowed() == true ? "Разрешена" : "Запрещена")
					));
		}
		else
		{
			addLogParameters(new SimpleLogParametersReader(("Нет активной политики доступа")));
		}

		if (currentAccessScheme == null)
		{
			addLogParameters(new SimpleLogParametersReader("Первоначальная схема прав", "Нет схемы прав"));
		}
		else
		{
			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Первоначальная схема прав", currentAccessScheme),
					new CollectionLogParametersReader("Разрешенные сервисы", currentAccessScheme.getServices())
				));
		}

		AccessScheme accessScheme = operation.getNewAccessScheme();

		if (accessScheme == null)
		{
			addLogParameters(new SimpleLogParametersReader("Новая схема прав", "Нет схемы прав"));
		}
		else
		{
			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Новая схема прав", accessScheme),
					new CollectionLogParametersReader("Разрешенные сервисы", accessScheme.getServices())
				));
		}
	}
}
