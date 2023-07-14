package com.rssl.phizic.business.ermb.sms.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Бин для XML-маппинга поставщика оплаты мобильного телефона
 * @author Rtischeva
 * @created 10.09.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "RechargePhoneProvider")
@XmlAccessorType(XmlAccessType.NONE)
public class RechargePhoneProviderBean
{
	@XmlElement(name = "code", required = true)
    private String code;

    public String getCode()
    {
        return code;
    }

	public void setRechargePhoneProviderCode(String code)
	{
		this.code = code;
	}

	@XmlElement(name = "codeService", required = true)
    private String codeService;

    public String getCodeService()
    {
        return codeService;
    }

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	@XmlElement(name = "phone-field-name", required = true)
    private String phoneFieldName;

    public String getPhoneFieldName()
    {
        return phoneFieldName;
    }

	public void setPhoneFieldName(String phoneFieldName)
	{
		this.phoneFieldName = phoneFieldName;
	}
}
