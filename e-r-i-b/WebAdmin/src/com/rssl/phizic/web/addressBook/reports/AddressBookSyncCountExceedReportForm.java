package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.phizic.logging.contact.synchronization.ContactSyncCountExceedLog;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author mihaylov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма для построения отчета "Оповещения о превышении порога обращения к сервису"
 */
public class AddressBookSyncCountExceedReportForm extends ListFormBase<ContactSyncCountExceedLog>
{
	public static final Form FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Начальная дата");
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Конечная дата");
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
