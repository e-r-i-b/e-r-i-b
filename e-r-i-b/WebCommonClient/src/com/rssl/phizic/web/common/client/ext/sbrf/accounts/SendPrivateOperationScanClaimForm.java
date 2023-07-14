package com.rssl.phizic.web.common.client.ext.sbrf.accounts;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import static com.rssl.phizic.operations.claims.operation.scan.claim.Constants.*;

/**
 * Форма отправки запроса на получение электронного документа на  электронную почту
 * @author komarov
 * @ created 15.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class SendPrivateOperationScanClaimForm extends EditFormBase
{
	public static Form OPERATION_SCAN_CLAIM_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("E-mail получателя");
		fieldBuilder.setName(E_MAIL_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new EmailFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата");
		fieldBuilder.setName(DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма операции ДТ");
		fieldBuilder.setName(DEBIT_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма операции KT");
		fieldBuilder.setName(CREDIT_FIELD_NAME);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код авторизации");
		fieldBuilder.setName(AUTH_CODE_FIELD_NAME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер счёта/карты");
		fieldBuilder.setName(ACCOUNT_NUMBER_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());


		return fb.build();
	}
}
