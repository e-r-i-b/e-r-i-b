package com.rssl.phizic.web.ext.sbrf.persons.templates;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RegexpMoneyFieldValidator;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.web.actions.payments.forms.PaymentFormsComparator;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ author: Vagin
 * @ created: 05.04.2013
 * @ $Author
 * @ $Revision
 * ����� ������ �������� �������
 */
public class EmpolyeeListTemplatesForm extends ListFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static Form FILTER_FORM = createFilterForm();

	private Long person;
	private ActivePerson activePerson;
	private Boolean modified = false;
	//����� �������� ������
	private int searchPage;
	// �� ��������� 10 ������� �� ��������
	private int itemsPerPage = 10;

	private static final List<ConformityStates> conformityStates = new ArrayList<ConformityStates>();
	private static final List<FilterPaymentForm> formTypes = new ArrayList<FilterPaymentForm>();

	static
	{
		conformityStates.add(new ConformityStates(new State("TEMPLATE"), Arrays.asList(FormType.CONVERT_CURRENCY_TRANSFER, FormType.IMA_PAYMENT, FormType.INTERNAL_TRANSFER, FormType.LOAN_PAYMENT),
				StrutsUtils.getMessage("documet.state.WAIT_CONFIRM_TEMPLATE", "employeeTemplatesBundle")));
		conformityStates.add(new ConformityStates(new State("TEMPLATE"),
				Arrays.asList(FormType.INDIVIDUAL_TRANSFER, FormType.INDIVIDUAL_TRANSFER_NEW, FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER, FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER,
						FormType.JURIDICAL_TRANSFER, FormType.SECURITIES_TRANSFER_CLAIM),
				StrutsUtils.getMessage("documet.state.TEMPLATE", "employeeTemplatesBundle")));
		conformityStates.add(new ConformityStates(new State("WAIT_CONFIRM_TEMPLATE"), Arrays.asList(FormType.values()), StrutsUtils.getMessage("documet.state.WAIT_CONFIRM_TEMPLATE", "employeeTemplatesBundle")));
		conformityStates.add(new ConformityStates(new State("DRAFTTEMPLATE"), Arrays.asList(FormType.values()), StrutsUtils.getMessage("documet.state.DRAFTTEMPLATE", "employeeTemplatesBundle")));
		conformityStates.add(new ConformityStates(new State("SAVED_TEMPLATE"), Arrays.asList(FormType.values()), StrutsUtils.getMessage("documet.state.SAVED_TEMPLATE", "employeeTemplatesBundle")));

		formTypes.add(new FilterPaymentForm(FormType.INTERNAL_TRANSFER.getName(), FormType.INTERNAL_TRANSFER));
		formTypes.add(new FilterPaymentForm(FormType.IMA_PAYMENT.getName(), FormType.IMA_PAYMENT));
		formTypes.add(new FilterPaymentForm(FormType.LOAN_PAYMENT.getName(), FormType.LOAN_PAYMENT));
		formTypes.add(new FilterPaymentForm(FormType.INDIVIDUAL_TRANSFER.getName(), Arrays.asList(FormType.INDIVIDUAL_TRANSFER, FormType.INDIVIDUAL_TRANSFER_NEW)));
		formTypes.add(new FilterPaymentForm(FormType.JURIDICAL_TRANSFER.getName(), FormType.JURIDICAL_TRANSFER));
		formTypes.add(new FilterPaymentForm(FormType.SECURITIES_TRANSFER_CLAIM.getName(), FormType.SECURITIES_TRANSFER_CLAIM));
		formTypes.add(new FilterPaymentForm(FormType.CONVERT_CURRENCY_TRANSFER.getName(), FormType.CONVERT_CURRENCY_TRANSFER));
		formTypes.add(new FilterPaymentForm(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(), Arrays.asList(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER, FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER)));
		Collections.sort(formTypes, new PaymentFormsComparator(formTypes, "auditBundle"));
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public int getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(int searchPage)
	{
		this.searchPage = searchPage;
	}

	public int getItemsPerPage()
	{
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}

	private static Form createFilterForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("creationDateFrom");
		fb.setDescription("���� �������� �");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� �������� ������ ���� � ������� ��.��.����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("creationDateTo");
		fb.setDescription("���� �������� ��");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� �������� ������ ���� � ������� ��.��.����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("confirmDateFrom");
		fb.setDescription("���� ������������� �");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� ������������� ������ ���� � ������� ��.��.����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("confirmDateTo");
		fb.setDescription("���� ������������� ��");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� ������������� ������ ���� � ������� ��.��.����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("stateChangeDateFrom");
		fb.setDescription("��������� � ������������� �");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� �������� � ������������� ������ ������ ���� � ������� ��.��.����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("stateChangeDateTo");
		fb.setDescription("��������� � ������������� ��");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� �������� � ������������� ������ ������ ���� � ������� ��.��.����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("receiverName");
		fb.setDescription("����������");
		fb.addValidators(new RegexpFieldValidator(".{0,100}", "�������� ���� \"����������\" �� ������ ��������� ����� 100 ��������"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("templateName");
		fb.setDescription("�������� �������");
		fb.addValidators(new RegexpFieldValidator(".{0,100}", "�������� ���� \"�������� �������\" �� ������ ��������� ����� 100 ��������"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("templateNumber");
		fb.setDescription("�����");
		fb.addValidators(new RegexpFieldValidator("\\d*", "�������� ���� \"�����\" ������ ��������� ������ �����"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("createChannelType");
		fb.setDescription("����� ��������");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("confirmChannelType");
		fb.setDescription("����� �������������");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("employeeStateChange");
		fb.setDescription("������� ���������");
		fb.addValidators(new RegexpFieldValidator(".{0,50}", "�������� ���� \"������� ���������\" �� ������ ��������� ����� 50 ��������"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("amountFrom");
		fb.setDescription("����� �������� �");
        fb.setType(com.rssl.common.forms.types.MoneyType.INSTANCE.getName());
		fb.clearValidators();
		fb.addValidators(new RegexpMoneyFieldValidator("^[0-9\\.]{0,17}$","����������, ������� �������� � ���� \"����� �������� �\". ��������, 320.50"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("amountTo");
		fb.setDescription("����� �������� ��");
        fb.setType(com.rssl.common.forms.types.MoneyType.INSTANCE.getName());
		fb.clearValidators();
		fb.addValidators(new RegexpMoneyFieldValidator("^[0-9\\.]{0,17}$","����������, ������� �������� � ���� \"����� �������� ��\". ��������, 320.50"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("formType");
		fb.setDescription("��� ��������");
        fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());


		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("status");
		fb.setDescription("������");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(BooleanType.INSTANCE.getName());
		fb.setName("showDeleted");
		fb.setDescription("���������� ��������� ��������");
		formBuilder.addField(fb.build());

		// ���� ����������
		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "creationDateTo");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "creationDateFrom");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("���� � ���� \"���� �������� �\" ������ ���� ������ ���� � ���� \"���� �������� ��\"");
		formBuilder.addFormValidators(dateTimeValidator);

		dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "confirmDateTo");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "confirmDateFrom");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("���� � ���� \"���� ������������� �\" ������ ���� ������ ���� � ���� \"���� ������������� ��\"");
		formBuilder.addFormValidators(dateTimeValidator);

		dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "stateChangeDateTo");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "stateChangeDateFrom");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("���� � ���� \"��������� � ������������� �\" ������ ���� ������ ���� � ���� \"��������� � ������������� ��\"");
		formBuilder.addFormValidators(dateTimeValidator);

		return formBuilder.build();
	}

	/**
	 * ������ ����� �������� ��� �����������.
	 * @return ������ ���� ��� ����������
	 */
	public List<FilterPaymentForm> getFormTypes()
	{
		return Collections.unmodifiableList(formTypes);
	}

	/**
	 * @return ������ ������������ �������� �������� � ���� �������� c �� ���������.
	 */
	public List<ConformityStates> getTemplateConformityStates()
	{
		return Collections.unmodifiableList(conformityStates);
	}

	public static class ConformityStates
	{
		private State status;
		private String formTypesAsString;
		private String description;

		ConformityStates(State status, List<FormType> formTypes, String description)
		{
			this.status = status;
			StringBuilder sb = new StringBuilder();
			for(FormType type: formTypes)
			{
				if(StringHelper.isEmpty(sb.toString()))
					sb = sb.append(type);
				else
					sb = sb.append(",").append(type);
			}
			this.formTypesAsString = sb.toString();
			this.description = description;
		}

		public State getStatus()
		{
			return status;
		}

		public void setStatus(State status)
		{
			this.status = status;
		}

		public String getFormTypesAsString()
		{
			return formTypesAsString;
		}

		public void setFormTypes(String formTypes)
		{
			this.formTypesAsString = formTypes;
		}

		public String getDescription()
		{
			return description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}
	}
}
