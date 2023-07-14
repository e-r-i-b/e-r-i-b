package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionSyncUtil;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.profile.ProfileConfig;

/**
 * ����� ��� �������������� ���������� ���������� �������
 * @author miklyaev
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketRequisitesForm extends EditPersonForm
{
	public static final Form EDIT_BASKET_REQUISITES_FORM = createBasketRequisitesLogicalForm();
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	public static final String SERIES_FIELD_DL = InvoiceSubscriptionSyncUtil.SERIES_FIELD_DL;
	public static final String NUMBER_FIELD_DL = InvoiceSubscriptionSyncUtil.NUMBER_FIELD_DL;
	public static final String ISSUE_DATE_FIELD_DL = InvoiceSubscriptionSyncUtil.ISSUE_DATE_FIELD_DL;
	public static final String EXPIRE_DATE_FIELD_Dl = InvoiceSubscriptionSyncUtil.EXPIRE_DATE_FIELD_Dl;
	public static final String ISSUE_BY_FIELD_DL = InvoiceSubscriptionSyncUtil.ISSUE_BY_FIELD_DL;

	public static final String SERIES_FIELD_RC = InvoiceSubscriptionSyncUtil.SERIES_FIELD_RC;
	public static final String NUMBER_FIELD_RC = InvoiceSubscriptionSyncUtil.NUMBER_FIELD_RC;

	private ActivePerson activePerson;
	private boolean modified = false;
	private boolean accessDL;
	private boolean accessRC;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	/**
	 * ���������� �������� �������
	 * @param activePerson - ������� ������
	 */
	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	/**
	 * @return true, ���� ����� ������ ������� - shadow
	 */
	public boolean isModified()
	{
		return modified;
	}

	/**
	 * ���������� ������� �������� ������ ������ �������
	 * @param modified - �������
	 */
	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	/**
	 * @return ������� ����������� ������������� ������������� ��� �������������� �������
	 */
	public boolean isAccessDL()
	{
		return accessDL;
	}

	/**
	 * @return ������� ������������� � ����������� ������������� �������� ��� �������������� �������
	 */
	public boolean isAccessRC()
	{
		return accessRC;
	}

	/**
	 * ���������� ������� ����������� ������������� ������������� ��� �������������� �������
	 * @param accessDL - ������� �����������
	 */
	public void setAccessDL(boolean accessDL)
	{
		this.accessDL = accessDL;
	}


	/**
	 * ���������� ������� ����������� ������������� � ����������� ������������� ��������  ��� �������������� �������
	 * @param accessRC - ������� �����������
	 */
	public void setAccessRC(boolean accessRC)
	{
		this.accessRC = accessRC;
	}

	/**
	 * @return ���������� �����
	 */
	private static Form createBasketRequisitesLogicalForm()
	{
		ProfileConfig config = ConfigFactory.getConfig(ProfileConfig.class);
		int maxLengthForReq;
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		//����� ������������� �������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUMBER_FIELD_DL);
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("numberDL");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{" + maxLengthForReq +"}$",
				"����� �� ������ ��������� " + maxLengthForReq + " ����"));
		formBuilder.addField(fieldBuilder.build());

		//����� ������������� �������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SERIES_FIELD_DL);
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("seriesDL");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^\\d{" + maxLengthForReq + "}$",
                  "���� ����� �� ������ ��������� " + maxLengthForReq + " �������"));
		formBuilder.addField(fieldBuilder.build());

		DateParser dataParser = new DateParser(DATE_FORMAT);

		//���� ������ ������������� �������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(ISSUE_DATE_FIELD_DL);
		fieldBuilder.setParser(dataParser);
		fieldBuilder.setDescription("���� ������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new DateFieldValidator("dd.MM.yyyy", "����������, ������� ���� ������ � ������� ��.��.����."));
		formBuilder.addField(fieldBuilder.build());

		//����� ��������� �������� ������������� �������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(EXPIRE_DATE_FIELD_Dl);
		fieldBuilder.setParser(dataParser);
		fieldBuilder.setDescription("��������� ��");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new DateFieldValidator("dd.MM.yyyy", "����������, ������� �������� ���� ��������� �� � ������� ��.��.����."));
		formBuilder.addField(fieldBuilder.build());

		//��� ������ ������������� �������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ISSUE_BY_FIELD_DL);
		fieldBuilder.setDescription("��� �����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("issueOrgDL");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0," + maxLengthForReq + "}",
				"���� ��� ����� ������� ��������� �� ����� " + maxLengthForReq + " ��������"));
		formBuilder.addField(fieldBuilder.build());

		//����� ������������� � ����������� ������������� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUMBER_FIELD_RC);
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("numberRC");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{" + maxLengthForReq +"}$",
				"����� ������������� � ����������� ������������� �������� ������ ��������� " + maxLengthForReq + " ����"));
		formBuilder.addField(fieldBuilder.build());

		//����� ������������� � ����������� ������������� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SERIES_FIELD_RC);
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		maxLengthForReq = config.getMaxLengthForReq("seriesRC");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^\\d{" + maxLengthForReq + "}$",
				"���� ����� ������������� � ����������� ������������� �������� ������ ��������� " + maxLengthForReq + " �������"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
