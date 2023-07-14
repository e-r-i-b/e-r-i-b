package com.rssl.phizic.web.quick.pay;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.image.configure.ImagesSettingsService;
import com.rssl.phizic.business.quick.pay.Constants;
import com.rssl.phizic.business.quick.pay.PanelBlock;
import com.rssl.phizic.business.quick.pay.ProviderSummValidator;
import com.rssl.phizic.operations.quick.pay.EditQuickPaymentPanelOperation;
import com.rssl.phizic.web.image.ImageEditFormBase;
import com.rssl.phizic.web.validators.CompositeFileValidator;
import com.rssl.phizic.web.validators.FileValidator;
import com.rssl.phizic.web.validators.ImageSizeValidator;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author komarov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditQuickPaymentPanelForm extends ImageEditFormBase
{
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";

	private static final String START_DATE_MESSAGE = "������� ���������� ���� ������ ����������� � ������� ��.��.����.";
	private static final String CANCEL_DATE_MESSAGE = "������� ���������� ���� ��������� ����������� � ������� ��.��.����.";

	private static final String PROVIDER_NAME_SHOW = "providerNameShow";
	public static final String USE_DEFAULT_PROVIDER_IMAGE = "isDefaultImage";

	private static final ImageSizeValidator imageValidator55 = new ImageSizeValidator(55, 55, CompareValidator.EQUAL, CompareValidator.EQUAL);
	private static final ImageSizeValidator imageValidator40 = new ImageSizeValidator(55, 40, CompareValidator.EQUAL, CompareValidator.EQUAL);

	private String[] selectedIds = new String[]{};
	private Set<String> departments; //������ �� ������� ����� �������� �� �����

	private List<PanelBlock> panelBlocks; // ����� ����������� �� ������ ������� ������.

	private Long[] orderIds = new Long[]{}; //

	private Long[] providerIds = new Long[]{};

	private static final FormBuilder formBuilder = createFormBuilder();

	public Long[] getProviderIds()
	{
		return providerIds;
	}

	public void setProviderIds(Long[] providerIds)
	{
		this.providerIds = providerIds;
	}

	public Long[] getOrderIds()
	{
		return orderIds;
	}

	public void setOrderIds(Long[] orderIds)
	{
		this.orderIds = orderIds;
	}

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public Set<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(Set<String> departments)
	{
		this.departments = departments;
	}

	public List<PanelBlock> getPanelBlocks()
	{
		return panelBlocks;
	}

	public void setPanelBlocks(List<PanelBlock> panelBlocks)
	{
		this.panelBlocks = panelBlocks;
	}

	public List<String> getImageIds()
	{
		List<String> imageIds = new ArrayList<String>();
		for(int i = 0; i < orderIds.length; i++)
			imageIds.add(EditQuickPaymentPanelOperation.IMAGE_ID_PREFIX + orderIds[i]);
		return imageIds;
	}

	protected FileValidator getImageFileValidator(String imageId)
	{
		String num = imageId.replace(EditQuickPaymentPanelOperation.IMAGE_ID_PREFIX, "");

		boolean useDefaultProviderImage = BooleanUtils.toBoolean((String) getField(USE_DEFAULT_PROVIDER_IMAGE + num));
		if(useDefaultProviderImage)
			return null;

		boolean show = BooleanUtils.toBoolean((String)getField(PROVIDER_NAME_SHOW + num));
		if(show)
			return new CompositeFileValidator(getDefaultImageFileSizeLimitValidator(ImagesSettingsService.PROVIDER_PANEL_PARAMETER_NAME), imageValidator40);
		return new CompositeFileValidator(getDefaultImageFileSizeLimitValidator(ImagesSettingsService.PROVIDER_PANEL_PARAMETER_NAME), imageValidator55);
	}

	private static FormBuilder createFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������");
		fieldBuilder.setName("state");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators (
				new RequiredFieldValidator("�������� ������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators (
				new RequiredFieldValidator("����������, ������� �������� � ���� \"�������� ������\""),
				new RegexpFieldValidator(".{0,100}", "�������� ������ ������ ��������� �� ����� 100 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �");
		fieldBuilder.setName("periodFrom");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("����������, ������� ���� ������ ��� ����������� ������ ������� ������."),
				new DateFieldValidator(DATESTAMP_FORMAT, START_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", START_DATE_MESSAGE)
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �����������");
		fieldBuilder.setName("blocksCount");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		NumericRangeValidator numericValidator= new NumericRangeValidator();
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, String.valueOf(Constants.MAX_NUM_OF_PROVIDERS));
		numericValidator.setMessage("����������, �������� ����������, ������ �������� ����� ������������ �� ������!");
		fieldBuilder.addValidators (new RegexpFieldValidator("\\d", "���������� ������� ������ ���� �� 1 �� 5."), numericValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("periodTo");
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.clearValidators();		
		fieldBuilder.addValidators(
				new DateFieldValidator(DATESTAMP_FORMAT, CANCEL_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", CANCEL_DATE_MESSAGE)
		);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "periodFrom");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "periodTo");
	    compareValidator.setMessage("���� ��������� ������ ���� ������ ���� ������ ������� ����������. ����������, ������� ������ ����.");
	    formBuilder.setFormValidators(compareValidator);

		formBuilder.build();
		return formBuilder;
	}

	public Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		fb.addFields(formBuilder.getFields());
		fb.addFields(getImageFields(getImageIds()));
		fb.addFormValidators(formBuilder.getFormValidators());

		FieldBuilder fieldBuilder = new FieldBuilder();

		for(int i = 0;  i < orderIds.length; i++)
		{
			int num = orderIds[i].intValue();
			RequiredFieldValidator rValidator = new RequiredFieldValidator();
			rValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null && form.providerId"+ num +"!=''"));

			RegexpFieldValidator regExpValidator = new RegexpFieldValidator(".{0,14}", "���� H������� ������ ��������� �� ����� 14 ��������.");
			regExpValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null"));

			RequiredFieldValidator rFieldValidator = new RequiredFieldValidator();
			rFieldValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null && form.providerId"+num+"!=''  &&"+num+" == 0"));

			RegexpMoneyFieldValidator regExpSummFieldValidator = new RegexpMoneyFieldValidator("^\\d{1,7}((\\.|,)\\d{0,2})?$", "����� ������ ���� � ������� #######.##");
			rFieldValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null && form.providerId"+num+"!=''  &&"+num+" == 0"));

			RequiredFieldValidator rPhoneFieldValidator = new RequiredFieldValidator("����������, ������� �������� � \"���� ��� ������ ��������\".");
			rPhoneFieldValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null && form.providerId"+num+"!=''  &&"+num+" == 0"));

			RequiredFieldValidator rSummFieldValidator = new RequiredFieldValidator("����������, ������� �������� � \"���� ��� ����� ������\".");
			rSummFieldValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null && form.providerId"+num+"!=''  &&"+num+" == 0"));

			RegexpFieldValidator regExpFieldValidator = new RegexpFieldValidator(".{0,40}", "���� �������� ������ ��������� �� ����� 40 ��������.");
			regExpFieldValidator.setEnabledExpression(new RhinoExpression("form.providerId" + num + " != null && "+num+" == 0"));

			RequiredFieldValidator rProviderValidator = new RequiredFieldValidator();
			rProviderValidator.setEnabledExpression(new RhinoExpression("form.providerShow" + num + " == true || "+num+" != 0"));

			
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("���������� ����");
			fieldBuilder.setName("providerShow"+num);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("���������");
			fieldBuilder.setName("providerId"+num);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators (rProviderValidator);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("������� �����������");
			fieldBuilder.setName("orderIndex"+num);
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.addValidators (rValidator);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("���������� ��������");
			fieldBuilder.setName(PROVIDER_NAME_SHOW + num);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��������");
			fieldBuilder.setName("providerAlias"+num);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators (regExpValidator);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��� �������");
			fieldBuilder.setName(USE_DEFAULT_PROVIDER_IMAGE + num);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("���� ��� ����� ��������");
			fieldBuilder.setName("providerField"+num);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators (rPhoneFieldValidator, regExpFieldValidator);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("���� ��� ����� �����");
			fieldBuilder.setName("providerFieldSumm"+num);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators (rSummFieldValidator, regExpFieldValidator);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("�����");
			fieldBuilder.setName("summ"+num);
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.addValidators (rFieldValidator, regExpSummFieldValidator);
			fb.addField(fieldBuilder.build());

		}

		ProviderSummValidator summValidator = new ProviderSummValidator();
		summValidator.setBinding(ProviderSummValidator.PROVIDER_ID, "providerId0");
		summValidator.setBinding(ProviderSummValidator.SUMM, "summ0");
		summValidator.setMessage("�������� ���� ����� ������ ����");
		summValidator.setEnabledExpression(new RhinoExpression("(form.providerId0 != null) && (form.providerShow0 == true)"));
		fb.addFormValidators(summValidator);


		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.NOT_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "providerField0");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "providerFieldSumm0");
		compareValidator.setMessage("���� ��� ����� ������ �������� � ����� �� ������ ���������!");
		compareValidator.setEnabledExpression(new RhinoExpression("(form.providerId0 != null) && (form.providerShow0 == true)"));

		fb.addFormValidators(compareValidator);

		return fb.build();
	}
}
