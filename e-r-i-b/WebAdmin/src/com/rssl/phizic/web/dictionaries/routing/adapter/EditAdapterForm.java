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
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "������������ ������ ���� �� ����� 128 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����");
		fieldBuilder.setName("nodeType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ����� ����");
		fieldBuilder.setName("adapterType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new EnumFieldValidator<AdapterType>(AdapterType.class, "��� �������� �� ������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.adapterType == 'NONE'"));
		fieldBuilder.setName("nodeId");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setName("UUID");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,64}", "������������� ������ ���� �� ����� 64 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���-�������");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.nodeType == 'SOFIA' && form.isESB != true"));
		fieldBuilder.setName("addressWebService");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "����� ���-������� ������ ���� �� ����� 128 ��������.")
		);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������� ��������� �� ��");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.nodeType == 'COD' || form.nodeType == 'IQWAVE' || form.nodeType == 'SOFIA'"));
		fieldBuilder.setName("listenerUrl");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "����� ����������� ��������� �� �� ������ ���� �� ����� 128 ��������."),
				new RegexpFieldValidator("^.+:\\d{1,4}/.+$", "� ���� ����� ����������� ��������� �� �� ������� ������ � ������� <host>:<port>/<content_root>.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
