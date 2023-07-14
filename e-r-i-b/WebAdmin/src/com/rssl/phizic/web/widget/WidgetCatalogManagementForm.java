package com.rssl.phizic.web.widget;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * User: moshenko
 * Date: 05.12.2012
 * Time: 11:25:47
 * ����� ���������� ��������� ��������
 */
public class WidgetCatalogManagementForm extends EditFormBase
{
	public static final Form FORM = createForm();

	/**
	 * ������ ���� ����������� ��������
	 */
	private List<WidgetDefinition> widgetDefList;
	/**
	 * ������ ������������� ��������� ��������
	 */
	private String[] selectedWidget = new String[]{};
	/**
	 * ������� ���������� �������
	 */
	private String[] sortWidget = new String[]{};

	///////////////////////////////////////////////////////////////////////////
	public List<WidgetDefinition> getWidgetDefList()
	{
		return widgetDefList;
	}

	public void setWidgetDefList(List<WidgetDefinition> widgetDefList)
	{
		this.widgetDefList = widgetDefList;
	}

	public String[] getSelectedWidget()
	{
		return selectedWidget;
	}

	public void setSelectedWidget(String[] selectedWidget)
	{
		this.selectedWidget = selectedWidget;
	}

	public String[] getSortWidget()
	{
		return sortWidget;
	}

	public void setSortWidget(String[] sortWidget)
	{
		this.sortWidget = sortWidget;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		// twitterId
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("twitterID");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("ID ��� ������� Twitter");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
