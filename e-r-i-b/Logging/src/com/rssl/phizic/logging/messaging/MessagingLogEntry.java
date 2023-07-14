package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.common.types.Application;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Kosyakova
 * @ created 28.09.2006
 * @ $Author: vagin $
 * @ $Revision: 74474 $
 */

public class MessagingLogEntry extends MessagingLogEntryBase implements Serializable
{
	// идентификатор промоутера
	protected String promoterId;
	//mGUID
	protected String mGUID;
	//ОСБ
	protected String osb;
	//ВСП
	protected String vsp;

	public String getPromoterId()
	{
		return promoterId;
	}

	public void setPromoterId(String promoterId)
	{
		this.promoterId = promoterId;
	}

	public String getmGUID()
	{
		return mGUID;
	}

	public void setmGUID(String mGUID)
	{
		this.mGUID = mGUID;
	}

	/**
	 * @return осб
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @param osb осб
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return всп
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @param vsp всп
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("promoterId", promoterId)
				.append("mGUID", mGUID)
				.append("osb", osb)
				.append("vsp", vsp)
				.append(super.toString())
				.toString();
	}
}
