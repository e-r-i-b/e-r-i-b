package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.ermb.*;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.StringHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Erkin
 * @ created 06.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class MBKRegistrationParser
{
	private static final int MIN_CARD_NUMBER_LENGTH = 15;

	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Читает связки из резальт-сета
	 * @param rs - резальт-сет (never null)
	 * @return список успешно разобранных и неформатных связок (never null can be empty)
	 */
	public List<MBKRegistration> parseResultSet(ResultSet rs) throws SQLException
	{
		List<MBKRegistration> registrations = new LinkedList<MBKRegistration>();

		while (rs.next())
		{
			long id = 0;

			try
			{
				id = rs.getLong("ID");

				MBKRegistration registration = parseRegistration(rs);

				registrations.add(registration);
			}
			catch (SQLException e)
			{
				log.error("Сбой на разборе связки " + id, e);
				if (id != 0)
					registrations.add(makeInvalidRegistration(id, e.getMessage()));
			}
			catch (RuntimeException e)
			{
				log.error("Сбой на валидации связки " + id, e);
				if (id != 0)
					registrations.add(makeInvalidRegistration(id, e.getMessage()));
			}
		}

		return registrations;
	}

	private MBKRegistration parseRegistration(ResultSet rs) throws SQLException
	{
		MBKRegistration registration = new MBKRegistration();

		registration.setId(readMandatoryLong(rs, "ID"));
		registration.setRegTranType(readMandatoryEnum(rs, RegTranType.class, "RegTranType"));
		registration.setRegAction(readMandatoryEnum(rs, RegAction.class, "RegAction"));
		registration.setFiltrationReason(parseFiltrationReason(rs));
		registration.setOfficeCode(parseOfficeCode(rs));
		registration.setPhoneNumber(parsePhoneNumber(rs));
		registration.setPaymentCardNumber(readMandatoryCardNumber(rs, "PaymentCardNumber"));

		switch (registration.getRegTranType())
		{
			case MOBI:
				registration.setTariff(parseTariff(rs));
				registration.setActiveCards(parseActiveCards(rs));
				break;

			case MOPS:
				registration.setPp(readMandatoryString(rs, "PP"));
				registration.setIpList(parseIPList(rs));
				break;

			default:
				throw new UnsupportedOperationException("Неожиданный тип транзакции связки: " + registration.getRegTranType());
		}

		registration.setWellParsed(true);
		return registration;
	}

	private FiltrationReason parseFiltrationReason(ResultSet rs) throws SQLException
	{
		String reasonAsString = readMandatoryString(rs, "FiltrationReasonName");
		return FiltrationReason.fromMBK(reasonAsString);
	}

	private PhoneNumber parsePhoneNumber(ResultSet rs) throws SQLException
	{
		String phoneAsString = readMandatoryString(rs, "PhoneNumber");
		return PhoneNumber.fromString(phoneAsString);
	}

	private Code parseOfficeCode(ResultSet rs) throws SQLException
	{
		String bank = readMandatoryString(rs, "Bank").trim();
		String branch = readMandatoryString(rs, "Branch").trim();
		String agency = readMandatoryString(rs, "Agency").trim();
		return new CodeImpl(bank, branch, agency);
	}

	private MbkTariff parseTariff(ResultSet rs) throws SQLException
	{
		int tarifCode = readMandatoryInt(rs, "TarifType");
		return MbkTariff.fromMbkCode(tarifCode);
	}

	private List<String> parseActiveCards(ResultSet rs) throws SQLException
	{
		List<String> activeCards = new ArrayList<String>();
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard1"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard2"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard3"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard4"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard5"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard6"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard7"));
		ListUtil.addIfNotEmpty(activeCards, readCardNumber(rs, "ActiveCard8"));
		return activeCards;
	}

	private List<String> parseIPList(ResultSet rs) throws SQLException
	{
		List<String> ipList = new ArrayList<String>();
		ListUtil.addIfNotEmpty(ipList, rs.getString("IP1"));
		ListUtil.addIfNotEmpty(ipList, rs.getString("IP2"));
		ListUtil.addIfNotEmpty(ipList, rs.getString("IP3"));
		ListUtil.addIfNotEmpty(ipList, rs.getString("IP4"));
		ListUtil.addIfNotEmpty(ipList, rs.getString("IP5"));
		return ipList;
	}

	private int readMandatoryInt(ResultSet rs, String columnName) throws SQLException
	{
		int columnValue = rs.getInt(columnName);
		if (rs.wasNull())
			throw new IllegalArgumentException("Не заполнено обязательное поле " + columnName);
		return columnValue;
	}

	private long readMandatoryLong(ResultSet rs, String columnName) throws SQLException
	{
		long columnValue = rs.getLong(columnName);
		if (rs.wasNull())
			throw new IllegalArgumentException("Не заполнено обязательное поле " + columnName);
		return columnValue;
	}

	private String readMandatoryString(ResultSet rs, String columnName) throws SQLException
	{
		String columnValue = rs.getString(columnName);
		if (rs.wasNull() || StringHelper.isEmpty(columnValue))
			throw new IllegalArgumentException("Не заполнено обязательное поле " + columnName);
		return columnValue;
	}

	private <T extends Enum<T>> T readMandatoryEnum(ResultSet rs, Class<T> enumClass, String columnName) throws SQLException
	{
		String columnValue = readMandatoryString(rs, columnName);
		return Enum.valueOf(enumClass, columnValue);
	}

	private String readCardNumber(ResultSet rs, String cardFieldName) throws SQLException
	{
		String cardNumber = rs.getString(cardFieldName);
		if (rs.wasNull() || StringHelper.isEmpty(cardNumber))
			return null;

		if (cardNumber.length() < MIN_CARD_NUMBER_LENGTH)
			throw new IllegalArgumentException("Недопустимый номер карты в поле " + cardFieldName + ": " + cardNumber);

		return cardNumber;
	}

	private String readMandatoryCardNumber(ResultSet rs, String cardFieldName) throws SQLException
	{
		String cardNumber = readCardNumber(rs, cardFieldName);
		if (StringHelper.isEmpty(cardNumber))
			throw new IllegalArgumentException("Не заполнено обязательное поле " + cardFieldName);
		return cardNumber;
	}

	private MBKRegistration makeInvalidRegistration(long id, String error)
	{
		MBKRegistration invalidRegistration = new MBKRegistration();
		invalidRegistration.setId(id);
		invalidRegistration.setWellParsed(false);
		invalidRegistration.setResultCode(MBKRegistrationResultCode.ERROR_NOT_REG);
		invalidRegistration.setErrorDescr(error);
		return invalidRegistration;
	}
}
