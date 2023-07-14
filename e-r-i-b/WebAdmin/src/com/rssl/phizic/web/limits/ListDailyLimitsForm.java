package com.rssl.phizic.web.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.utils.ArraysHelper;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: vagin
 * @ created: 26.01.2012
 * @ $Author$
 * @ $Revision$
 * ����� ��� ������ �������� ������������ �������
 */
public class ListDailyLimitsForm extends ListFormBase<Limit>
{
	public static final Form FILTER_FORM = createForm();

	private Long departmentId;
	private String[] selectedObstructionIds;
	private String[] selectedIMSIIds;
	private String[] selectedMobileExtendedCardIds;
	private String[] selectedERMBExternalTelephoneIds;
	private String[] selectedERMBExternalCardIds;
	private List<Limit> imsiLimit = new ArrayList<Limit>();
	private List<Limit> mobileExternalCardLimit = new ArrayList<Limit>();
	private List<Limit> ermbExternalTelephone = new ArrayList<Limit>();
	private List<Limit> ermbExternalCard = new ArrayList<Limit>();

	//��� ������ � ������ ������ ���������
	//����� ���� � ��� �������, ��� ������ ������� ����������� �� �������� (������ ����)
	//�������������� ������
	private Integer pagination_offset0;//��������
	private Integer pagination_size0;//���������� ������������ �������
	//IMSI
	private Integer pagination_offset1;//��������
	private Integer pagination_size1;//���������� ������������ �������
	//��� ���������� ������ EXTERNAL_CARD ��� ���� EXTERNAL_PHONE
	private Integer pagination_offset2;//��������
	private Integer pagination_size2;//���������� ������������ �������
	//���� EXTERNAL_CARD
	private Integer pagination_offset3;//��������
	private Integer pagination_size3;//���������� ������������ �������

	public String[] getSelectedIds()
	{
		return ArraysHelper.join(selectedObstructionIds, selectedIMSIIds, selectedERMBExternalTelephoneIds, selectedERMBExternalCardIds, selectedMobileExtendedCardIds);
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long id)
	{
		this.departmentId = id;
	}

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

	    FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromCreationDate");
		fb.setDescription("���� ����� �");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toCreationDate");
		fb.setDescription("���� ����� ��");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromStartDate");
		fb.setDescription("���� ������ �������� ������ �");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toStartDate");
		fb.setDescription("���� ������ �������� ������ ��");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		rangeValidator.setMessage("������� ����� ������");

		fb = new FieldBuilder();
		fb.setDescription("�������� ��������� ������������� ������");
		fb.setName("amount");
		fb.setType(StringType.INSTANCE.getName());
		fb.clearValidators();
		fb.setParser(new BigDecimalParser());
	    fb.clearValidators();
		fb.addValidators(new RegexpFieldValidator("^\\d{1,7}+((\\.|,)\\d{0,2})?$",
				"������� ����� ������ � ���������� �������: #######.##"), rangeValidator);
	    formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setName("limitType");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("��� ������");
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
	    fb.setDescription("������");
	    fb.setName("status");
	    fb.setType(StringType.INSTANCE.getName());
	    formBuilder.addField(fb.build());

	    formBuilder.setFormValidators(
			createCompareValidator("toCreationDate", "fromCreationDate", "�������� ���� ����� ������ ���� ������ ���� ����� ���������!"),
			createCompareValidator("toStartDate", "fromStartDate","�������� ���� ������ �������� ������ ���� ������ ���� ����� ���������!")
	    );

	    return formBuilder.build();
    }

	private static DateTimeCompareValidator createCompareValidator(String fieldFromDate, String fieldToDate, String message)
	{
		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, fieldFromDate);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, fieldFromDate);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, fieldToDate);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, fieldToDate);
		dateTimeCompareValidator.setMessage(message);

		return dateTimeCompareValidator;
	}

	public List<Limit> getImsiLimit()
	{
		return imsiLimit;
	}

	public void setImsiLimit(List<Limit> imsiLimit)
	{
		this.imsiLimit = imsiLimit;
	}

	public List<Limit> getMobileExternalCardLimit()
	{
		return mobileExternalCardLimit;
	}

	public void setMobileExternalCardLimit(List<Limit> mobileExternalCardLimit)
	{
		this.mobileExternalCardLimit = mobileExternalCardLimit;
	}

	public List<Limit> getErmbExternalTelephone()
	{
		return ermbExternalTelephone;
	}

	public void setErmbExternalTelephone(List<Limit> ermbExternalTelephone)
	{
		this.ermbExternalTelephone = ermbExternalTelephone;
	}

	public List<Limit> getErmbExternalCard()
	{
		return ermbExternalCard;
	}

	public void setErmbExternalCard(List<Limit> ermbExternalCard)
	{
		this.ermbExternalCard = ermbExternalCard;
	}

	public String[] getSelectedObstructionIds()
	{
		return selectedObstructionIds;
	}

	public void setSelectedObstructionIds(String[] selectedObstructionIds)
	{
		this.selectedObstructionIds = selectedObstructionIds;
	}

	public String[] getSelectedIMSIIds()
	{
		return selectedIMSIIds;
	}

	public void setSelectedIMSIIds(String[] selectedIMSIIds)
	{
		this.selectedIMSIIds = selectedIMSIIds;
	}

	public String[] getSelectedMobileExtendedCardIds()
	{
		return selectedMobileExtendedCardIds;
	}

	public void setSelectedMobileExtendedCardIds(String[] selectedMobileExtendedCardIds)
	{
		this.selectedMobileExtendedCardIds = selectedMobileExtendedCardIds;
	}

	public String[] getSelectedERMBExternalTelephoneIds()
	{
		return selectedERMBExternalTelephoneIds;
	}

	public void setSelectedERMBExternalTelephoneIds(String[] selectedERMBExternalTelephoneIds)
	{
		this.selectedERMBExternalTelephoneIds = selectedERMBExternalTelephoneIds;
	}

	public String[] getSelectedERMBExternalCardIds()
	{
		return selectedERMBExternalCardIds;
	}

	public void setSelectedERMBExternalCardIds(String[] selectedERMBExternalCardIds)
	{
		this.selectedERMBExternalCardIds = selectedERMBExternalCardIds;
	}

	//Set'��� ����� ����� �������� �.�. �������� � ���������� ������� ��������
	// � ����� � ���������� $$pagination_offset0, $$pagination_offset1...  � $$pagination_size0, $$pagination_size1...
	public Integer getPagination_offset0()
	{
		return pagination_offset0;
	}

	public void set$$pagination_offset0(Integer pagination_offset0)
	{
		this.pagination_offset0 = pagination_offset0;
	}

	public Integer getPagination_size0()
	{
		return pagination_size0;
	}

	public void set$$pagination_size0(Integer pagination_size0)
	{
		this.pagination_size0 = pagination_size0;
	}

	public Integer getPagination_offset1()
	{
		return pagination_offset1;
	}

	public void set$$pagination_offset1(Integer pagination_offset1)
	{
		this.pagination_offset1 = pagination_offset1;
	}

	public Integer getPagination_size1()
	{
		return pagination_size1;
	}

	public void set$$pagination_size1(Integer pagination_size1)
	{
		this.pagination_size1 = pagination_size1;
	}

	public Integer getPagination_offset2()
	{
		return pagination_offset2;
	}

	public void set$$pagination_offset2(Integer pagination_offset2)
	{
		this.pagination_offset2 = pagination_offset2;
	}

	public Integer getPagination_size2()
	{
		return pagination_size2;
	}

	public void set$$pagination_size2(Integer pagination_size2)
	{
		this.pagination_size2 = pagination_size2;
	}

	public Integer getPagination_offset3()
	{
		return pagination_offset3;
	}

	public void set$$pagination_offset3(Integer pagination_offset3)
	{
		this.pagination_offset3 = pagination_offset3;
	}

	public Integer getPagination_size3()
	{
		return pagination_size3;
	}

	public void set$$pagination_size3(Integer pagination_size3)
	{
		this.pagination_size3 = pagination_size3;
	}
}
