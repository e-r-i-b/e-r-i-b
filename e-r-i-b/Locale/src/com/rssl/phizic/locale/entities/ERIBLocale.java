package com.rssl.phizic.locale.entities;


import com.rssl.phizic.common.types.Application;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Класс - локаль
 * @author koptyaev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class ERIBLocale implements Serializable
{
	private String id;//Короткое наименование языка
	private String name; //Название языка
	private Long imageId; //картинка с флагом
	private boolean eribAvailable; //флаг доступности локали в Ериб
	private boolean mapiAvailable; //флаг доступности локали в мАпи
	private boolean atmApiAvailable; //флаг доступности локали в АТМ
	private boolean webApiAvailable; //флаг доступности локали в вебАпи
	private boolean ermbAvailable; //флаг доступности локали в Ермб
	private LocaleState state; //статус
	private Calendar actualDate;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public boolean isEribAvailable()
	{
		return eribAvailable;
	}

	public void setEribAvailable(boolean eribAvailable)
	{
		this.eribAvailable = eribAvailable;
	}

	public boolean isMapiAvailable()
	{
		return mapiAvailable;
	}

	public void setMapiAvailable(boolean mapiAvailable)
	{
		this.mapiAvailable = mapiAvailable;
	}

	public boolean isAtmApiAvailable()
	{
		return atmApiAvailable;
	}

	public void setAtmApiAvailable(boolean atmApiAvailable)
	{
		this.atmApiAvailable = atmApiAvailable;
	}

	public boolean isWebApiAvailable()
	{
		return webApiAvailable;
	}

	public void setWebApiAvailable(boolean webApiAvailable)
	{
		this.webApiAvailable = webApiAvailable;
	}

	public boolean isErmbAvailable()
	{
		return ermbAvailable;
	}

	public void setErmbAvailable(boolean ermbAvailable)
	{
		this.ermbAvailable = ermbAvailable;
	}

	public LocaleState getState()
	{
		return state;
	}

	public void setState(LocaleState state)
	{
		this.state = state;
	}

	/**
	 * @return дата актуальности текстовок локали
	 */
	public Calendar getActualDate()
	{
		return actualDate;
	}

	/**
	 * задать дату актуальности текстовок локали
	 * @param actualDate дата
	 */
	public void setActualDate(Calendar actualDate)
	{
		this.actualDate = actualDate;
	}

	public void setAvailabilityAll(boolean available)
	{
		setEribAvailable(available);
		setAtmApiAvailable(available);
		setMapiAvailable(available);
		setWebApiAvailable(available);
		setErmbAvailable(available);
	}

	public boolean applicationAllowed(Application application)
	{
		MultiLanguageApplications app = MultiLanguageApplications.fromApplication(application);
		switch (app)
		{
			case atmApi : return isAtmApiAvailable();
			case ERIB   : return isEribAvailable();
			case ERMB   : return isErmbAvailable();
			case mApi   : return isMapiAvailable();
			case webApi : return isWebApiAvailable();
			case csa: return true;
		}
		return false;
	}
}
