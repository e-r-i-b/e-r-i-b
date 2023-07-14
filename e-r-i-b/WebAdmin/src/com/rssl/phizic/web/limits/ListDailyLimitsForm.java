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
 * Форма для списка суточных комулятивных лимитов
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

	//Для борьбы с черной магией пагинации
	//Номер идет в том порядке, как списки лимитов расположены на странице (сверху вниз)
	//Заградительные лимиты
	private Integer pagination_offset0;//Смещение
	private Integer pagination_size0;//Количество отображаемых записей
	//IMSI
	private Integer pagination_offset1;//Смещение
	private Integer pagination_size1;//Количество отображаемых записей
	//для мобильного канала EXTERNAL_CARD или ермб EXTERNAL_PHONE
	private Integer pagination_offset2;//Смещение
	private Integer pagination_size2;//Количество отображаемых записей
	//ермб EXTERNAL_CARD
	private Integer pagination_offset3;//Смещение
	private Integer pagination_size3;//Количество отображаемых записей

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
		fb.setDescription("дата ввода с");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toCreationDate");
		fb.setDescription("дата ввода по");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromStartDate");
		fb.setDescription("дата начала действия лимита с");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toStartDate");
		fb.setDescription("дата начала действия лимита по");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		rangeValidator.setMessage("Укажите сумму лимита");

		fb = new FieldBuilder();
		fb.setDescription("Величина суточного кумулятивного лимита");
		fb.setName("amount");
		fb.setType(StringType.INSTANCE.getName());
		fb.clearValidators();
		fb.setParser(new BigDecimalParser());
	    fb.clearValidators();
		fb.addValidators(new RegexpFieldValidator("^\\d{1,7}+((\\.|,)\\d{0,2})?$",
				"Укажите сумму лимита в правильном формате: #######.##"), rangeValidator);
	    formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setName("limitType");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Тип лимита");
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
	    fb.setDescription("Статус");
	    fb.setName("status");
	    fb.setType(StringType.INSTANCE.getName());
	    formBuilder.addField(fb.build());

	    formBuilder.setFormValidators(
			createCompareValidator("toCreationDate", "fromCreationDate", "Конечная дата ввода должна быть больше либо равна начальной!"),
			createCompareValidator("toStartDate", "fromStartDate","Конечная дата начала действия должна быть больше либо равна начальной!")
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

	//Set'еры имеют такие названия т.к. смещения и количество записей хранятся
	// в полях с названиями $$pagination_offset0, $$pagination_offset1...  и $$pagination_size0, $$pagination_size1...
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
