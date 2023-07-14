package com.rssl.phizic.web.dictionaries.routing.node;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.NodeType;

import java.util.List;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditNodeForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();
	private List<NodeType> nodeTypes;

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Наименование должно быть не более 128 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес");
		fieldBuilder.setName("URL");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Адрес должен быть не более 128 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип шлюза");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,20}", "Тип шлюза должен быть не более 20 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.type == 'SOFIA'"));
		fieldBuilder.setDescription("Префикс настроек шлюза Софии");
		fieldBuilder.setName("prefix");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Префикс настроек шлюза Софии должен быть не более 128 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public List<NodeType> getNodeTypes()
	{
		return nodeTypes;
	}

	public void setNodeTypes(List<NodeType> nodeTypes)
	{
		this.nodeTypes = nodeTypes;
	}
}
