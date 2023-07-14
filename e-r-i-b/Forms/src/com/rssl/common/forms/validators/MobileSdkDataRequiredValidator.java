package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Map;

/**
 * @author vagin
 * @ created 10.03.15
 * @ $Author$
 * @ $Revision$
 * ќб€зательность заполнени€ mobileSdkData только с версии 9.1
 */
public class MobileSdkDataRequiredValidator extends MultiFieldsValidatorBase
{
	public static final String VERSION_FIELD = "version";
	public static final String MOBILE_SDK_DATA_FIELD = "mobileSdkData";
	public static final VersionNumber V9_1 = new VersionNumber(9, 1);

	private static final String MESSAGE = "Ќе указан об€зательный параметр mobileSdkData";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String version = (String) retrieveFieldValue(VERSION_FIELD, values);
		String mobileSdkData = (String) retrieveFieldValue(MOBILE_SDK_DATA_FIELD, values);
		if(StringHelper.isEmpty(mobileSdkData))
		{
			try
			{
				VersionNumber vNumber = VersionNumber.fromString(version);
				return !vNumber.ge(V9_1);
			}
			catch (MalformedVersionFormatException e)
			{
				throw new TemporalDocumentException(e);
			}
		}
		return true;
	}

	public String getMessage()
    {
        return MESSAGE;
    }
}
