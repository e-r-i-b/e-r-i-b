package com.rssl.phizic.web.promo;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.promoters.PromoChannel;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.OneFromRegexpListFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author: Gololobov
 * @ created: 10.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginPromoForm extends FilterActionForm
{
	//������ �������
	private List<PromoChannel> promoChannels;
	//������ ���������
	private List<Department> promoTbList;
	//���������� ����� ����. �������� �� ����� ��-�� ����, ��� DataPower �� ��������� ���������� 2 ����. CHG054739
	private String tempCookie;

	private static final String[] OR_RUCHAR_OR_NUMBER_REGEXPS_LIST = new String[]{"[�-��-��-]+", "\\d+"};
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("channelId");
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(new RequiredFieldValidator("�������� �����"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("tb");
		fieldBuilder.setDescription("�������");
		fieldBuilder.addValidators(new RequiredFieldValidator("�������� �������"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("osb");
		fieldBuilder.setDescription("���");
		fieldBuilder.addValidators(new RequiredFieldValidator("������� ��� ���"));
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","������� �������� ��� ���"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("vsp");
		fieldBuilder.setDescription("���");
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","������� �������� ��� ���"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("promoId");
		fieldBuilder.setDescription("������������� ����������");

		//���� ����� ��������� ������ ����� ��� ������ ��������� ����� �������� ��������
		fieldBuilder.addValidators(new RequiredFieldValidator("������� ������������� ����������"));
		fieldBuilder.addValidators(new OneFromRegexpListFieldValidator(OR_RUCHAR_OR_NUMBER_REGEXPS_LIST,
				"������� �������������, ���������� ����� ��� ��������� ����� �������� ��������."));
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}

	public List<PromoChannel> getPromoChannels()
	{
		return promoChannels;
	}

	public void setPromoChannels(List<PromoChannel> promoChannels)
	{
		this.promoChannels = promoChannels;
	}

	public List<Department> getPromoTbList()
	{
		return promoTbList;
	}

	public void setPromoTbList(List<Department> promoTbList)
	{
		this.promoTbList = promoTbList;
	}

	public String getTempCookie()
	{
		return tempCookie;
	}

	public void setTempCookie(String tempCookie)
	{
		this.tempCookie = tempCookie;
	}
}
