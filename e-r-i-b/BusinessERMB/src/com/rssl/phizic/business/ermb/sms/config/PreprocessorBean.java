package com.rssl.phizic.business.ermb.sms.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Бин для XML-маппинга фильтров препроцессора смс-сообщений 
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "Preprocessor")
@XmlAccessorType(XmlAccessType.NONE)
class PreprocessorBean
{
	@XmlElement(name = "replace-filter")
	private List<ReplacementFilterBean> replacementFilters;

	@XmlElement(name = "send-sms-filter")
	private List<SendSmsFilterBean> sendSmsFilters;

	//////////////////////////////////////////////////

	List<ReplacementFilterBean> getReplacementFilters()
	{
		return replacementFilters;
	}

	void setReplacementFilters(List<ReplacementFilterBean> replacementFilters)
	{
		this.replacementFilters = replacementFilters;
	}

	List<SendSmsFilterBean> getSendSmsFilters()
	{
		return sendSmsFilters;
	}

	void setSendSmsFilters(List<SendSmsFilterBean> sendSmsFilters)
	{
		this.sendSmsFilters = sendSmsFilters;
	}
}
