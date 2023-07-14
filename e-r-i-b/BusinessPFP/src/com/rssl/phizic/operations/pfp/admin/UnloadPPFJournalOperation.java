package com.rssl.phizic.operations.pfp.admin;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;

/**
 * @author komarov
 * @ created 25.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция для формирования данных о планированиях
 */

public class UnloadPPFJournalOperation extends ListPFPPassingJournalOperation implements ListEntitiesOperation
{
	private static final String DELIMITER = ";";
	private static final String STRING_DELIMITER = "\n";
	private static final String SPACE = " ";

	private byte[] data;

	/**
	 * инициализация операции по квере (генерит данные)
	 * @param query кверя
	 * @throws BusinessException
	 */
	public void initialize(Query query) throws BusinessException
	{
		Iterator<UnloadPFPJournalEntity> it = null;
		try
		{
			it = query.executeIterator();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Не удалось получить данные для выгрузки" ,e);
		}

		StringBuilder builder = new StringBuilder();
		addHeader(builder);
		for(;it.hasNext();)
			addRow(builder, it.next());

		data = builder.toString().getBytes();
	}

	private void addHeader(StringBuilder builder)
	{
		addCell(builder, "№ ТБ");
		addCell(builder, "ОСБ");
		addCell(builder, "ВСП");
		addCell(builder, "Начало");
		addCell(builder, "Окончание");
		addCell(builder, "Время");
		addCell(builder, "Клиент");
		addCell(builder, "Дата рождения");
		addCell(builder, "Документ");
		addCell(builder, "Цели");
		addCell(builder, "Виртуальный баланс");
		addCell(builder, "Денег в других банках");
		addCell(builder, "Наличных");
		addCell(builder, "Итог по инвестиционным продуктам");
		addCell(builder, "Наличие кредитной карты в Сбербанке");
		addCell(builder, "Риск-профиль");
		addCell(builder, "Менеджер");
		addCell(builder, "ID менеджера");
		addCell(builder, "Канал");
		addCell(builder, "E-mail клиента");
		addCell(builder, "Мобильный телефон клиента");
		addCell(builder, "Статус");
		builder.append(STRING_DELIMITER);
	}

	private void addRow(StringBuilder builder, UnloadPFPJournalEntity data)
	{
		addCell(builder, data.getUserTB());
		addCell(builder, data.getManagerOSB());
		addCell(builder, data.getManagerVSP());

		addCell(builder, DateHelper.formatDateToStringWithSlash2(data.getCreationDate()));
		addCell(builder, DateHelper.formatDateToStringWithSlash2(data.getExecutionDate()));

		addTimeOfPlaning(builder, data.getCreationDate(), data.getExecutionDate());

		addFormattedPersonName(builder, data.getUserSurName(), data.getUserFirstName(), data.getUserPatrName());
		addCell(builder, DateHelper.formatDateToStringWithPoint(data.getUserBirthday()));

		//ДУЛ
		builder.append(StringHelper.getEmptyIfNull(data.getUserDocumentSeries()));
		builder.append(SPACE);
		builder.append(StringHelper.getEmptyIfNull(data.getUserDocumentNumber()));
		builder.append(DELIMITER);

		addCell(builder, data.getTargets());

		if (BigDecimal.ZERO.compareTo(data.getUserVirtualBalance()) < 0)
			builder.append(StringHelper.getEmptyIfNull(data.getUserVirtualBalance()));
		builder.append(DELIMITER);

		if (BigDecimal.ZERO.compareTo(data.getUserBalanceOtherBanks()) < 0)
			builder.append(StringHelper.getEmptyIfNull(data.getUserBalanceOtherBanks()));
		builder.append(DELIMITER);

		if (BigDecimal.ZERO.compareTo(data.getUserBalanceCash()) < 0)
			builder.append(StringHelper.getEmptyIfNull(data.getUserBalanceCash()));
		builder.append(DELIMITER);

		if (BigDecimal.ZERO.compareTo(data.getTotalBalanceInvestments()) < 0)
			builder.append(StringHelper.getEmptyIfNull(data.getTotalBalanceInvestments()));
		builder.append(DELIMITER);

		addCell(builder, data.getUserCreditCardType());

		addCell(builder, data.getRiskProfileName());
		addCell(builder, data.getManagerFIO());
		addCell(builder, data.getManagerId());
		addCell(builder, data.getChannelName());
		addCell(builder, data.getUserEmail());
		addCell(builder, data.getUserMobilePhone());
		addCell(builder, data.getStateDescription());
		builder.append(STRING_DELIMITER);

	}

	private void addCell(StringBuilder builder, Object value)
	{
		builder.append(StringHelper.getEmptyIfNull(value)).append(DELIMITER);
	}

	private void addTimeOfPlaning(StringBuilder builder, Calendar startDate, Calendar endDate)
	{
		Long time = DateHelper.diff(endDate, startDate);
		if (time == null)
		{
			builder.append(DELIMITER);
			return;
		}

		long hours = time / DateHelper.MILLISECONDS_IN_HOUR;
		builder.append(hours);
		builder.append(":");
		long minutes = (time % DateHelper.MILLISECONDS_IN_HOUR ) / DateHelper.MILLISECONDS_IN_MINUTE;
		builder.append(minutes);
		builder.append(":");
		long seconds = (time % DateHelper.MILLISECONDS_IN_MINUTE ) / DateHelper.MILLISECONDS_IN_SECOND;
		builder.append(seconds);
		builder.append(DELIMITER);
	}

	//маскируем ФИО клиента (Иванов Иван Иванович - И***** И*** И*******)
	private void addFormattedPersonName(StringBuilder builder, String surName, String firstName, String patrName)
	{
		boolean notEmptySurName = StringHelper.isNotEmpty(surName);
		boolean notEmptyFirstName = StringHelper.isNotEmpty(firstName);
		boolean notEmptyPatrName = StringHelper.isNotEmpty(patrName);
		if (notEmptySurName)
		{
			addMaskedString(builder, surName);
			if (notEmptyFirstName || notEmptyPatrName)
				builder.append(SPACE);
		}
		if (notEmptyFirstName)
		{
			addMaskedString(builder, firstName);
			if (notEmptyPatrName)
				builder.append(SPACE);
		}
		if (notEmptyPatrName)
		{
			addMaskedString(builder, patrName);
		}
		builder.append(DELIMITER);
	}

	private void addMaskedString(StringBuilder builder, String value)
	{
		if (StringHelper.isEmpty(value))
			return;

		builder.append(value.charAt(0));
		for (int i = 1; i < value.length(); i++)
			builder.append("*");
	}

	/**
	 * @return данные о планированиях
	 */
	public byte[] getEntity()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

	public Query createQuery(String name)
    {
	    return new BeanQuery(this, ListPFPPassingJournalOperation.class.getName() +"."+ name, getInstanceName() );
    }
}
