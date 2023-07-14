package com.rssl.phizic.web.dictionaries.routing.adapter;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.AdapterType;
import com.rssl.phizicgate.manager.routing.Node;

import java.util.List;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditAdapterForm extends EditFormBase
{
	private List<Node> allNodesList;
	public static final Form EDIT_FORM = createForm();

	public List<Node> getAllNodesList()
	{
		return allNodesList;
	}

	public void setAllNodesList(List<Node> nodesList)
	{
		this.allNodesList = nodesList;
	}

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
		fieldBuilder.setDescription("Тип узла");
		fieldBuilder.setName("nodeType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Взаимодействие через шину");
		fieldBuilder.setName("adapterType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new EnumFieldValidator<AdapterType>(AdapterType.class, "Тип адаптера не найден."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Узел");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.adapterType == 'NONE'"));
		fieldBuilder.setName("nodeId");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор");
		fieldBuilder.setName("UUID");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,64}", "Идентификатор должен быть не более 64 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес веб-сервиса");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.nodeType == 'SOFIA' && form.isESB != true"));
		fieldBuilder.setName("addressWebService");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Адрес веб-сервиса должен быть не более 128 символов.")
		);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес обработчика сообщений от ВС");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.nodeType == 'COD' || form.nodeType == 'IQWAVE' || form.nodeType == 'SOFIA'"));
		fieldBuilder.setName("listenerUrl");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Адрес обработчика сообщений от ВС должен быть не более 128 символов."),
				new RegexpFieldValidator("^.+:\\d{1,4}/.+$", "В поле Адрес обработчика сообщений от ВС введите строку в формате <host>:<port>/<content_root>.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
