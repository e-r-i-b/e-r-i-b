package com.rssl.phizic.web.ermb.migration.list.client;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Arrays;
import java.util.List;

/**
 * Форма просмотра списка клиентов при миграции
 * @author Puzikov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ListClientsMigrationForm extends ListFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	public static final Form FILTER_FORM = createForm();

	private List<Segment> segments;

	public List<Segment> getSegments()
	{
		return segments;
	}

	public void setSegments(List<Segment> segments)
	{
		this.segments = segments;
	}

	public List<MigrationStatus> getStatuses()
	{
		return Arrays.asList(MigrationStatus.values());
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser dateParser = new DateParser(DATE_FORMAT);
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия, имя и отчество");
		fieldBuilder.setName("fio");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName("docNumber");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d*", "Поле номер документа должно содержать только цифры"),
				new RegexpFieldValidator(".{0,16}", "Поле номер документа не должно превышать 16 цифр")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия документа");
		fieldBuilder.setName("docSeries");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "Поле серия документа не должно превышать 16 символов"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","Поле серия документа не должно содержать спецсимволов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName("birthday");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(new DateFieldValidator(DATE_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подразделение");
		fieldBuilder.setName("department");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Телефон");
		fieldBuilder.setName("phone");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "Поле 'Телефон' должно содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Результат миграции");
		fieldBuilder.setName("status");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сегмент клиента");
		fieldBuilder.setName("segment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Признак вип");
		fieldBuilder.setName("vip");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
