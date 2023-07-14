package com.rssl.phizic.web.passwordcards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.passwordcards.RequestPasswordCardsOperation;
import com.rssl.phizic.operations.passwordcards.MockRequestProcessor;
import com.rssl.phizic.operations.passwordcards.AddPasswordCardHashesOperation;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roshka
 * @ created 29.09.2005
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class RequestPasswordCardsAction extends OperationalActionBase
{
    private static final String FORWARD_START = "Start";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.createCards",      "create");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    RequestPasswordCardsOperation operation = createOperation(RequestPasswordCardsOperation.class);
	    
	    FilterActionForm frm = (FilterActionForm) form;
	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

        frm.setField( "cardsCount", String.valueOf(securityConfig.getCardsCount()) );
        frm.setField( "keysCount",  String.valueOf(securityConfig.getCardPasswords()));

        return mapping.findForward(FORWARD_START);
    }

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FilterActionForm frm = (FilterActionForm) form;

		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());

		Form filterForm = createForm();

		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, filterForm);

		if (formProcessor.process())
		{
			RequestPasswordCardsOperation operation = createOperation(RequestPasswordCardsOperation.class);
			operation.initialize();

			// создадим запрос
			Map<String, Object> result = formProcessor.getResult();
			BigInteger cardsCount = (BigInteger) result.get("cardsCount");
			BigInteger keysCount = (BigInteger) result.get("keysCount");
			operation.setCardsCount(cardsCount.intValue());
			operation.setKeysCount(keysCount.intValue());
			ByteArrayOutputStream arrStream = new ByteArrayOutputStream();
			operation.request(arrStream);


			// сами же обработаем см. BUG001263, CHG001232
			ByteArrayInputStream is = new ByteArrayInputStream(arrStream.toByteArray());
			ByteArrayOutputStream passOs = new ByteArrayOutputStream();
			ByteArrayOutputStream hashOs = new ByteArrayOutputStream();
			MockRequestProcessor mockRequestProcessor = new MockRequestProcessor(is, passOs, hashOs);
			mockRequestProcessor.process();

			// ответ с хешами паролей - сохраним в БД
			AddPasswordCardHashesOperation addHashesOperation = createOperation(AddPasswordCardHashesOperation.class);
			addHashesOperation.initialize(new ByteArrayInputStream(hashOs.toByteArray()));
			addHashesOperation.add();

			// ответ с паролями в открытом виде - отдадим в response
			response.setContentType("application/octetstream");
			response.setHeader("Content-Disposition", "attachment; filename=response.xml");
			ServletOutputStream os = response.getOutputStream();
			os.write(passOs.toByteArray());
			os.flush();
			os.close();
			return null;
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

	}

    private Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

        //поля для ввода кол-ва карт и кол-ва паролей
        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("cardsCount");
	    fieldBuilder.setType("integer");
        RegexpFieldValidator regexpFieldValidatorCardsCount = new RegexpFieldValidator("\\d{1,3}");
        regexpFieldValidatorCardsCount.setMessage("Неверный формат данных в поле: количество карт, введите числовое значение в диапазоне 1...100.");

        NumericRangeValidator numericRangeValidatorCardsCount = new NumericRangeValidator();
        numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
        numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
        numericRangeValidatorCardsCount.setMessage("Неверный формат данных в поле: количество карт, введите числовое значение в диапазоне 1...100.");

        fieldBuilder.setValidators(regexpFieldValidatorCardsCount, numericRangeValidatorCardsCount);
        fieldBuilder.setDescription("Количество карт");
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("keysCount");
	    fieldBuilder.setType("integer");
        RegexpFieldValidator regexpFieldValidatorKeysCount = new RegexpFieldValidator("\\d{1,3}");
        regexpFieldValidatorKeysCount.setMessage("Неверный формат данных в поле: количество ключей, введите числовое значение в диапазоне 1...500.");

        NumericRangeValidator numericRangeValidatorKeysCount = new NumericRangeValidator();
        numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
        numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "500");
        numericRangeValidatorKeysCount.setMessage("Неверный формат данных в поле: количество ключей, введите числовое значение в диапазоне 1...500.");

        fieldBuilder.setValidators(regexpFieldValidatorKeysCount, numericRangeValidatorKeysCount);
        fieldBuilder.setDescription("Количество ключей");
	    formBuilder.addField(fieldBuilder.build());

        return formBuilder.build();
    }
}