package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.web.common.ListFormBase;
import java.util.Map;

/**
 * @author usachev
 * @ created 12.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ��������� ������ �������� �������� �� ���� �������
 */

public class ListFundSenderResponseForm extends ListFormBase<FundSenderResponse>
{

	public static final Form FORM = create();
	private static final String FROM_DATE = "from_date";
	private Map<String, String> avatarsMap;

	/**
	 * ��������� ���� ������� � ������� ������ ������� �������
	 * @param fromDate ���� ������ �������
	 */
	public void setFromDate(String fromDate)
	{
		getFields().put(FROM_DATE,fromDate);
	}

	private static Form create(){
		FieldBuilder field = new FieldBuilder();
		field.setName(FROM_DATE);
		field.setType("date");
		field.setDescription("���� ������ �������");
		FormBuilder form = new FormBuilder();
		form.addField(field.build());
		return form.build();
	}

	/**
	 * ��������� Map'� �������� ����������� �������
	 * @return Map'� �������� ����������� �������
	 */
	public Map<String, String> getAvatarsMap()
	{
		return avatarsMap;
	}

	/**
	 * ��������� Map'� �������� ����������� �������
	 * @param avatarsMap Map'� �������� ����������� �������
	 */
	public void setAvatarsMap(Map<String, String> avatarsMap)
	{
		this.avatarsMap = avatarsMap;
	}
}
