package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.configure.exceptions.MobilePlatformFormValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * ����� �������� mAPI � ������� ��������: ��������������
 * @author sergunin
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */

public class SocialPlatformConfigEditForm extends MobilePlatformConfigEditForm
{
	private static final List<String> imageIds;
	private static final String ICON_IMAGE_ID = "Icon";
	private Long imageID;

	static
	{
		imageIds = new ArrayList<String>();
		imageIds.add(ICON_IMAGE_ID);
	}

	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(getImageFields(imageIds));
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��");
		fieldBuilder.setName("id");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platformName");
		fieldBuilder.setDescription("�������� ���������");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,100}", "�������� ������ ���� �� ����� 100 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platformId");
		fieldBuilder.setDescription("ID ���������, ����������� ��������� �����������");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,10}", "ID ������ ���� �� ����� 10 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("social");
		fieldBuilder.setDescription("��� ���������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("scheme");
		fieldBuilder.setDescription("����� ������������� ����������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platform_icon");
		fieldBuilder.setDescription("������ ���������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("downloadFromSBRF");
		fieldBuilder.setDescription("������� �������� � ����� ���������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("bankURL");
		fieldBuilder.setDescription("URL �� ����� �����");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "URL �� ����� ����� ������ ���� �� ����� 100 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("externalURL");
		fieldBuilder.setDescription("URL �� ����� ������������");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "URL �� ����� ������������ ������ ���� �� ����� 100 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isQR");
		fieldBuilder.setDescription("������������ QR-���");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isShownInApps");
		fieldBuilder.setDescription("���������� � ������� ���������� ����������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("QRName");
		fieldBuilder.setDescription("�������� ����� � ������������ QR-����");
		formBuilder.addField(fieldBuilder.build());
		formBuilder.addFormValidators(new MobilePlatformFormValidator());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isPasswordConfirm");
		fieldBuilder.setDescription("������������� ����������� �������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isShowSbAttribute");
		fieldBuilder.setDescription("���������� ������� ������� ���������");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
