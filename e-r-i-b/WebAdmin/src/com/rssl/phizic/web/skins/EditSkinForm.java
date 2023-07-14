package com.rssl.phizic.web.skins;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author egorova
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditSkinForm extends EditFormBase
{
	public static final Form FORM = createForm();

	/**
	 * √руппы, из которых можно выбрать
	 */
	private List<Group> groups;

	/**
	 * ID групп, у которых стиль по-умолчанию есть редактируемый стиль 
	 */
	private Long[] skinGroupIds;

	private boolean changeAdminSkinAllowed;

	/**
	 * ≈сли скин с именем mobile, то запрещаем редактирование
	 * @return - true - редактирование запрещено, false - разрешено
	 */
	private boolean readonly;

	///////////////////////////////////////////////////////////////////////////

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		skinGroupIds = new Long[0];
	}

	public List<Group> getGroups()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return groups;
	}

	public void setGroups(List<Group> groups)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.groups = groups;
	}

	public Long[] getSkinGroupIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return skinGroupIds;
	}

	public void setSkinGroupIds(Long[] skinGroupIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.skinGroupIds = skinGroupIds;
	}

	public boolean isChangeAdminSkinAllowed()
	{
		return changeAdminSkinAllowed;
	}

	public void setChangeAdminSkinAllowed(boolean changeAdminSkinAllowed)
	{
		this.changeAdminSkinAllowed = changeAdminSkinAllowed;
	}

	public void setReadonly(boolean readonly)
	{
		this.readonly = readonly;
	}

	/**
	 * ≈сли скин с именем mobile, то запрещаем редактирование
	 * @return - true - редактирование запрещено, false - разрешено
	 */
	public boolean isReadonly()
	{
		return readonly;
	}

	///////////////////////////////////////////////////////////////////////////

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		// название стил€
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Ќазвание");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		// путь к каталогу стил€
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("path");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("ѕуть к каталогу стилю");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		// приложение сотрудника
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("admin");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("ѕриложение сотрудника");
		formBuilder.addField(fieldBuilder.build());

		// приложение клиента
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("client");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("ѕриложение клиента");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("common");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("ƒоступен");
		formBuilder.addField(fieldBuilder.build());

		//ѕроверка выбора объекта применени€ стил€ 
		IsCheckedMultiFieldValidator logTypeIsCheckedValidator = new IsCheckedMultiFieldValidator();
		logTypeIsCheckedValidator.setBinding("admin", "admin");
	    logTypeIsCheckedValidator.setBinding("client", "client");
		logTypeIsCheckedValidator.setMessage("¬ыберите приложение, к которому примен€етс€ стиль");
	    formBuilder.addFormValidators(logTypeIsCheckedValidator);

		return formBuilder.build();
	}
}
