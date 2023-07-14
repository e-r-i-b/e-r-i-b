package com.rssl.phizic.business.persons.forms;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Проверяет, что если Способ доставки оповещений = "sms-сообщения", то установлен Формат SMS сообщений.
 * @author Dorzhinov
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class SmsFormatValidator extends MultiFieldsValidatorBase
{
    private static final String FIELD_MESSAGE_SERVICE = "messageService";
    private static final String FIELD_SMS_FORMAT = "SMSFormat";

    public boolean validate(Map values) throws TemporalDocumentException
    {
        String messageService = (String) retrieveFieldValue(FIELD_MESSAGE_SERVICE, values);
        String smsFormat = (String) retrieveFieldValue(FIELD_SMS_FORMAT, values);

        if("sms".equals(messageService))
            return StringHelper.isNotEmpty(smsFormat);
        return true;
    }
}
