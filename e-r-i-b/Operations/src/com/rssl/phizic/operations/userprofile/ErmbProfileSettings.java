package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Oбъект для подтверждения изменения настроек подключения ЕРМБ
 * @author EgorovaA
 * @ created 02.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ErmbProfileSettings extends Settings
{
	private Map<String, String> ermbSettings;
	private Map<String, Object> changedParams;

	public static final String TARIF = "tarif";
	public static final String MAIN_CARD = "mainCard";
	public static final String MAIN_PHONE = "mainPhoneNumber";
	public static final String PHONES = "phones";
	public static final String START_TIME = "ntfStartTime";
	public static final String END_TIME = "ntfEndTime";
	public static final String TIME_ZONE = "timeZone";
	public static final String DAYS = "days";
	public static final String DEPOSIT_TRANSFER = "depositsTransfer";
	public static final String FAST_SERVICE_AVAILABLE = "fastServiceAvailable";

	public ErmbProfileSettings(Map<String, Object> fields)
	{
		this.changedParams = fields;
		ermbSettings = new HashMap<String, String>();
		for(String key: fields.keySet())
		{
			String value = StringHelper.getEmptyIfNull(fields.get(key));
			ermbSettings.put(key, value);
		}
	}

	protected Map<String, String> getSettings()
	{
		return ermbSettings;
	}

	public boolean getCardChanged()
	{
		return changedParams.containsKey(MAIN_CARD);
	}

	public boolean getDaysChanged()
	{
		return changedParams.containsKey(DAYS);
	}

	public CardLink getMainCard()
	{
		return (CardLink) changedParams.get(MAIN_CARD);
	}

	public Set<String> getPhones()
	{
		return (Set<String>) changedParams.get(PHONES);
	}

	public ErmbTariff getTarif()
	{
		return (ErmbTariff) changedParams.get(TARIF);
	}

	public String getMainPhone()
	{
		return (String) changedParams.get(MAIN_PHONE);
	}

	public Time getNtfStartTime()
	{
		return (Time) changedParams.get(START_TIME);
	}

	public Time getNtfEndTime()
	{
		return (Time) changedParams.get(END_TIME);
	}

	public TimeZone getTimeZone()
	{
		return (TimeZone) changedParams.get(TIME_ZONE);
	}

	public String getDays()
	{
		return StringUtils.join((String[]) changedParams.get(DAYS), ", ");
	}

	public Boolean getDepositsTransfer()
	{
		return (Boolean) changedParams.get(DEPOSIT_TRANSFER);
	}

	public Boolean getFastServiceAvailable()
	{
		return (Boolean) changedParams.get(FAST_SERVICE_AVAILABLE);
	}
}
