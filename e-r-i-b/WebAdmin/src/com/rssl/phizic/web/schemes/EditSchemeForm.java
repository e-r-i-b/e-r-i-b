package com.rssl.phizic.web.schemes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.LogicOperatorValidator;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.operations.scheme.SchemeOperationHelper;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"ReturnOfCollectionOrArrayField", "JavaDoc", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class EditSchemeForm extends EditFormBase
{
    //*********************************************************************//
    //**************************  INSTANCE MEMBERS  ***********************//
    //*********************************************************************//

	public static final Form EDIT_FORM = createForm();

	public static final String CA_ADMIN_SCHEME = "CAAdminScheme";
	public static final String VSP_EMPLOYEE_SCHEME = "VSPEmployeeScheme";

	private String scope;
    private String category;
    private String name;
	private boolean allowEditCASchemes;
	private boolean vspEmployee;

	private List<SchemeOperationHelper> helpers;
	private Map<Service, List<OperationDescriptor>> allServices;

    private String[] selectedServices = new String[0];

	public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        if(name != null && name.length() == 0)
        {
            errors.add("name", new ActionMessage("com.rssl.phizic.com.rssl.phizic.web.schemes.enterName"));
        }

        return errors;
    }

    public Map<Service, List<OperationDescriptor>> getAllServices()
    {
        return allServices;
    }

    public void setAllServices(Map<Service, List<OperationDescriptor>> allServices)
    {
		this.allServices = allServices;
    }

    public String[] getSelectedServices()
    {
        return selectedServices;
    }

    public void setSelectedServices(String[] selectedOperations)
    {
        this.selectedServices = selectedOperations;
    }

	public String getScope()
	{
		return scope;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}

	public List<SchemeOperationHelper> getHelpers()
	{
		return helpers;
	}

	public void setHelpers(List<SchemeOperationHelper> helpers)
	{
		this.helpers = helpers;
	}

	public Comparator<Service> getServicesComparator()
	{
		return SchemeUtils.SERVICES_COMPARATOR;
	}

	public boolean isAllowEditCASchemes()
	{
		return allowEditCASchemes;
	}

	public void setAllowEditCASchemes(boolean allowEditCASchemes)
	{
		this.allowEditCASchemes = allowEditCASchemes;
	}

	/**
	 * @return текущий сотрудник с пометкой Сотрудник ВСП.
	 */
	public boolean isVspEmployee()
	{
		return vspEmployee;
	}

	/**
	 * @param vspEmployee текущий сотрудник с пометкой Сотрудник ВСП.
	 */
	public void setVspEmployee(boolean vspEmployee)
	{
		this.vspEmployee = vspEmployee;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CA_ADMIN_SCHEME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Доступно только администраторам ЦА");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(VSP_EMPLOYEE_SCHEME);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Доступно сотрудникам ВСП");
		formBuilder.addField(fieldBuilder.build());

		LogicOperatorValidator operatorValidator = new LogicOperatorValidator();
		operatorValidator.setParameter(LogicOperatorValidator.TRUE_OPERATOR_TRUE, "false");
		operatorValidator.setParameter(LogicOperatorValidator.TRUE_OPERATOR_FALSE, "true");
		operatorValidator.setParameter(LogicOperatorValidator.FALSE_OPERATOR_TRUE, "true");
		operatorValidator.setParameter(LogicOperatorValidator.FALSE_OPERATOR_FALSE, "true");
		operatorValidator.setBinding(LogicOperatorValidator.FIELD_VALUE1, CA_ADMIN_SCHEME);
		operatorValidator.setBinding(LogicOperatorValidator.FIELD_VALUE2, VSP_EMPLOYEE_SCHEME);
		operatorValidator.setMessage("Поля \"Доступно только сотрудникам ЦА\" и \"Доступно сотрудникам ВСП\" не могут быть выбраны одновременно");
		formBuilder.addFormValidators(operatorValidator);

		return formBuilder.build();
	}
}
