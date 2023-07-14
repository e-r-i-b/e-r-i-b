package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsCachePackEntryBase;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsPack3CacheEntry;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsPackCacheEntry;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mobile.GetRegistrationType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.LogThreadContext;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Jatsky
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 */

public class GetRegistrationsPackJDBCAction extends CachedJDBCAction<String, GetRegistrationsCachePackEntryBase>
{
	private static final String UNKNOWN_SP_TYPE = "Неизвестный тип получения регистраций МБК";
	private final String cards;
	private GetRegistrationType getRegistrationType = GetRegistrationType.FIRST;

	/**
	 * Конструктор
	 * @param cards - номера карт, по которой получаем регистрации
	 * @param getRegistrationType - тип хранимой процедуры
	 */
	GetRegistrationsPackJDBCAction(List<String> cards, GetRegistrationType getRegistrationType) throws SystemException
	{
		Collections.sort(cards, new Comparator<String>()
		{
			public int compare(String o1, String o2)
			{
				return o1.compareTo(o2);
			}
		});
		this.cards = marshalRequest(new RegistrationCards(cards));
		this.getRegistrationType = getRegistrationType;
	}

	public GetRegistrationsCachePackEntryBase doExecute(Connection con) throws SQLException, GateException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + getRegistrationType.getDecription() + " " +
						"(@cards = ?, " +
						"@retVal = ?) }");
		LogThreadContext.setProcName(getRegistrationType.getDecription());
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, cards);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			LogThreadContext.setProcName(getRegistrationType.getDecription());
			cstmt.execute();

			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			testReturnCode(rc);

			GetRegistrationsCachePackEntryBase result;
			switch (getRegistrationType)
			{
				case FIRST_PACK:
					result = new GetRegistrationsPackCacheEntry();
					break;
				case THIRD_PACK:
					result = new GetRegistrationsPack3CacheEntry();
					break;
				default:
					throw new IllegalArgumentException(UNKNOWN_SP_TYPE);
			}
			result.setStrCards(cards);
			result.setStrRetVal(cstmt.getString(3));
			return result;
		}
		finally
		{
			if (cstmt != null)
				try
				{
					cstmt.close();
				}
				catch (SQLException ignored)
				{
				}
		}
	}

	@Override
	protected String processResult(GetRegistrationsCachePackEntryBase value) throws SystemException
	{
		return value.getStrRetVal();
	}

	@Override
	protected GetRegistrationsCachePackEntryBase getDbCached() throws SystemException
	{
		switch (getRegistrationType)
		{
			case FIRST_PACK:
				return cacheService.getRegistrationsPackCacheEntry(cards);
			case THIRD_PACK:
				D:
				return cacheService.getRegistrationsPack3CacheEntry(cards);
			default:
				throw new IllegalArgumentException(UNKNOWN_SP_TYPE);
		}
	}

	@Override
	protected GetRegistrationsCachePackEntryBase getAppServerCached() throws SystemException
	{
		String cacheName;
		switch (getRegistrationType)
		{
			case FIRST_PACK:
				cacheName = GetRegistrationsPackCacheEntry.CACHE_NAME;
				break;
			case THIRD_PACK:
				cacheName = GetRegistrationsPack3CacheEntry.CACHE_NAME;
				break;
			default:
				throw new IllegalArgumentException(UNKNOWN_SP_TYPE);
		}
		Cache cache = CacheProvider.getCache(cacheName);
		Element element = cache.get(cards);
		if (element == null)
		{
			return null;
		}
		else
		{
			return (GetRegistrationsCachePackEntryBase) element.getObjectValue();
		}
	}

	private void testReturnCode(int rc) throws SQLException
	{
		StandInUtils.checkStandInAndThrow(rc);
		switch (rc)
		{
			case 0: // all correct
				break;

			case 50001:
				throw new SQLException("не указан или указан заведомо неверный номер карты");

			case -5:
				throw new SQLException("номер карты содержит не только цифры");

			default:
				if (rc < 0)
					throw new SQLException("ошибка в БД. Причина по коду не определяется (" + rc + ")");
				throw new SQLException("неизвестный код возврата (" + rc + ")");
		}
	}

	private String marshalRequest(RegistrationCards registrationCards) throws SystemException
	{
		StringWriter writer = new StringWriter();

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(RegistrationCards.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler()
			{
				public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException
				{
					out.write(ch, start, length);
				}
			});
			marshaller.marshal(registrationCards, writer);
		}
		catch (JAXBException e)
		{
			throw new SystemException(e);
		}

		return writer.toString();
	}
}

