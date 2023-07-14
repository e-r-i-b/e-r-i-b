package com.rssl.phizic.common.types.limits;

/**
 * Канал учета лимитов
 *
 * @author khudyakov
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public enum ChannelType
{
	MOBILE_API("Мобильные приложения"),                 //мобильные приложения
	INTERNET_CLIENT("Интернет клиент"),                 //интернет клиент
	VSP("ВСП"),                                         //ВСП
	CALL_CENTR("КЦ"),                                   //КЦ
	SELF_SERVICE_DEVICE("Устройство самообслуживания"), //устройство самообслуживания
	ERMB_SMS("Смс-канал мобильного банка"),             //ЕРМБ
    SOCIAL_API("Социальные приложения"),                //социальные приложения
    ALL("Все каналы");                                  //все каналы

	private String description;

	ChannelType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
