package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 * @author Kidyaev
 * @ created 14.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountCurrencyValidator extends FieldValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    public AccountCurrencyValidator()
    {
        this("Валюта счета отсутствует в справочнике валют");
    }

    public AccountCurrencyValidator(String message)
    {
        setMessage(message);
    }

    public boolean validate(String value) throws TemporalDocumentException
    {
        if ( isValueEmpty(value) )
            return true;

        CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
        String          code            = value.substring(5, 8);

        //Патч - затычка по случаю смены кода валюты у России
        code = code.equals("810") ? "643" : code;

        try
        {
            Currency currency = currencyService.findByNumericCode(code);
            return currency != null;
        }
        catch (IKFLException ex)
        {
	        String message1 = "Ошибка при получении валюты с кодом" + code;
	        log.error(message1,ex);
	        throw new TemporalDocumentException(message1,ex);
        }
    }
}
