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
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author sergunin
 * @ created: 26.01.2015
 * @ $Author$
 * @ $Revision$
 * Форма для списка единых суточных комулятивных лимитов
 */
public class ListOverallLimitsForm extends ListFormBase<Limit>
{
    public static final Form FILTER_FORM = createForm();
    private String[] selectedObstructionIds;
    //Для борьбы с черной магией пагинации
    //Номер идет в том порядке, как списки лимитов расположены на странице (сверху вниз)
    //Заградительные лимиты
    private Integer pagination_offset0;//Смещение
    private Integer pagination_size0;//Количество отображаемых записей

    private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fb = new FieldBuilder();
        fb.setType(DateType.INSTANCE.getName());
        fb.setName("fromCreationDate");
        fb.setDescription("Период с");
        fb.clearValidators();
        fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
        formBuilder.addField(fb.build());

        fb = new FieldBuilder();
        fb.setType(DateType.INSTANCE.getName());
        fb.setName("toCreationDate");
        fb.setDescription("Период по");
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
        fb.setDescription("Статус");
        fb.setName("status");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        formBuilder.setFormValidators(
                createCompareValidator("toCreationDate", "fromCreationDate", "Дата конца периода должена быть больше либо равена дате начала!")
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

    public String[] getSelectedIds()
    {
        return ArraysHelper.join(selectedObstructionIds);
    }

    public String[] getSelectedObstructionIds()
    {
        return selectedObstructionIds;
    }

    public void setSelectedObstructionIds(String[] selectedObstructionIds)
    {
        this.selectedObstructionIds = selectedObstructionIds;
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
}
