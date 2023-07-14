package com.rssl.phizic.web.client.deposits;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.*;

/**
 * User: IIvanova
 * Date: 16.05.2006
 * Time: 12:57:54
 */
public class DepositsForm  extends ListLimitActionForm
{
	  private Set<String> depositKinds = new HashSet<String>();

	private ActivePerson user;

	public Set<String> getDepositKinds()
	{
		return depositKinds;
	}

	public void setDepositKinds(Set<String> depositKinds)
	{
		this.depositKinds = depositKinds;
	}

    public ActivePerson getUser() {
        return user;
    }

    public void setUser(ActivePerson user) {
        this.user = user;
    }

	public static Form FILTER_FORM = createFilterForm();

    private static Form createFilterForm()
        {
            FormBuilder  formBuilder  = new FormBuilder();
            Field[]      fields       = new Field[10];
            FieldBuilder fieldBuilder;

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("state");
            fieldBuilder.setDescription("Статус");
            fields[0] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("depositType");
            fieldBuilder.setDescription("Вид вклада");
            fields[1] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("fromOpenDate");
            fieldBuilder.setDescription("Дата открытия (с)");
            fieldBuilder.setValidators(new DateFieldValidator());
            fieldBuilder.setParser(new DateParser());
            fields[2] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("toOpenDate");
            fieldBuilder.setDescription("Дата открытия (по)");
            fieldBuilder.setValidators(new DateFieldValidator());
            fieldBuilder.setParser(new DateParser());
            fields[3] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("fromCloseDate");
            fieldBuilder.setDescription("Дата закрытия (с)");
            fieldBuilder.setValidators(new DateFieldValidator());
            fieldBuilder.setParser(new DateParser());
            fields[4] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("toCloseDate");
            fieldBuilder.setDescription("Дата закрытия (по)");
            fieldBuilder.setValidators(new DateFieldValidator());
            fieldBuilder.setParser(new DateParser());
            fields[5] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("fromSum");
            fieldBuilder.setDescription("Сумма (с)");
            fieldBuilder.setValidators(new MoneyFieldValidator());
            fieldBuilder.setParser(new BigDecimalParser());
            fields[6] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("toSum");
            fieldBuilder.setDescription("Сумма (по)");
            fieldBuilder.setValidators(new MoneyFieldValidator());
            fieldBuilder.setParser(new BigDecimalParser());
            fields[7] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("sumCurrency");
            fieldBuilder.setDescription("Валюта");
            fields[8] = fieldBuilder.build();

            fieldBuilder = new FieldBuilder();
            fieldBuilder.setName("duration");
            fieldBuilder.setDescription("Срок");
            fields[9] = fieldBuilder.build();

            formBuilder.setFields(fields);
            MultiFieldsValidator[] validators= new MultiFieldsValidator[3];

            CompareValidator compareValidator1 = new CompareValidator();
            compareValidator1.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
            compareValidator1.setBinding(CompareValidator.FIELD_O1, "fromOpenDate");
            compareValidator1.setBinding(CompareValidator.FIELD_O2, "toOpenDate");
            compareValidator1.setMessage("Конечная дата открытия депозита должна быть больше либо равна начальной!");
            validators[0] = compareValidator1;

            CompareValidator compareValidator2 = new CompareValidator();
            compareValidator2.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
            compareValidator2.setBinding(CompareValidator.FIELD_O1, "fromCloseDate");
            compareValidator2.setBinding(CompareValidator.FIELD_O2, "toCloseDate");
            compareValidator2.setMessage("Конечная дата закрытия депозита должна быть больше либо равна начальной!");
            validators[1] = compareValidator2;

            CompareValidator compareValidator3 = new CompareValidator();
            compareValidator3.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
            compareValidator3.setBinding(CompareValidator.FIELD_O1, "fromSum");
            compareValidator3.setBinding(CompareValidator.FIELD_O2, "toSum");
            compareValidator3.setMessage("Неверный диапазон сумм. Конечная сумма должна быть больше либо равна начальной!");
            validators[2] = compareValidator3;

            formBuilder.setFormValidators(validators);
            return formBuilder.build();
        }
}

