package com.rssl.phizic.operations.test.mobile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;

/**
 * @author serkin
 * @ created 03.11.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отправка (http) запроса к мобильному приложению (WebMobile v2.x)
 */
public class SendMobileRequestOperation extends SendAbstractRequestOperation
{
	private String version;

	private static final String JAXB_MOBILE5_PACKAGE = "com.rssl.phizic.business.test.mobile5.generated";
	private static final String JAXB_MOBILE6_PACKAGE = "com.rssl.phizic.business.test.mobile6.generated";
	private static final String JAXB_MOBILE7_PACKAGE = "com.rssl.phizic.business.test.mobile7.generated";
	private static final String JAXB_MOBILE8_PACKAGE = "com.rssl.phizic.business.test.mobile8.generated";
	private static final String JAXB_MOBILE9_PACKAGE = "com.rssl.phizic.business.test.mobile9.generated";

    private static final String MAPI_5_SCHEMA = "com/rssl/phizic/operations/test/mobile/mobile5.xsd";
    private static final String MAPI_6_SCHEMA = "com/rssl/phizic/operations/test/mobile/mobile6.xsd";
    private static final String MAPI_7_SCHEMA = "com/rssl/phizic/operations/test/mobile/mobile7.xsd";
	private static final String MAPI_8_SCHEMA = "com/rssl/phizic/operations/test/mobile/mobile8.xsd";
	private static final String MAPI_9_SCHEMA = "com/rssl/phizic/operations/test/mobile/mobile9.xsd";

	protected String getJaxbContextPath() throws BusinessException
	{
		VersionNumber versionNumber = getVersionNumber();

		if (versionNumber.ge(MobileAPIVersions.V5_00) && versionNumber.lt(MobileAPIVersions.V6_00))
			return JAXB_MOBILE5_PACKAGE;
        else if (versionNumber.ge(MobileAPIVersions.V6_00) && versionNumber.lt(MobileAPIVersions.V7_00))
            return JAXB_MOBILE6_PACKAGE;
		else if (versionNumber.ge(MobileAPIVersions.V7_00) && versionNumber.lt(MobileAPIVersions.V8_00))
			return JAXB_MOBILE7_PACKAGE;
		else if (versionNumber.ge(MobileAPIVersions.V8_00) && versionNumber.lt(MobileAPIVersions.V9_00))
			return JAXB_MOBILE8_PACKAGE;
		else if (versionNumber.ge(MobileAPIVersions.V9_00))
			return JAXB_MOBILE9_PACKAGE;
		else
			throw new BusinessException("Недопустимый номер версии: " + version);
	}

	private  VersionNumber getVersionNumber() throws BusinessException
	{
		try
		{
			return VersionNumber.fromString(version);
		}
		catch (MalformedVersionFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	protected void updateVersion(String key, Object value)
	{
		if("version".equals(key))
			version = (String)value;	
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getVersion()
	{
		return version;
	}

    protected Schema getSchema() throws BusinessException
    {
        VersionNumber versionNumber = getVersionNumber();

        if (versionNumber.lt(MobileAPIVersions.V5_00))
            return null;

        try
        {
	        if (versionNumber.ge(MobileAPIVersions.V5_00) && versionNumber.lt(MobileAPIVersions.V6_00))
		        return XmlHelper.schemaByFileName(MAPI_5_SCHEMA);
	        else if (versionNumber.ge(MobileAPIVersions.V6_00) && versionNumber.lt(MobileAPIVersions.V7_00))
                return XmlHelper.schemaByFileName(MAPI_6_SCHEMA);
	        else if (versionNumber.ge(MobileAPIVersions.V7_00) && versionNumber.lt(MobileAPIVersions.V8_00))
		        return XmlHelper.schemaByFileName(MAPI_7_SCHEMA);
	        else if (versionNumber.ge(MobileAPIVersions.V8_00) && versionNumber.lt(MobileAPIVersions.V9_00))
		        return XmlHelper.schemaByFileName(MAPI_8_SCHEMA);
	        else if (versionNumber.ge(MobileAPIVersions.V9_00))
		        return XmlHelper.schemaByFileName(MAPI_9_SCHEMA);
        }
        catch (SAXException e)
        {
            throw new BusinessException(e);
        }

        throw new BusinessException("Недопустимый номер версии: " + version);
    }
}
