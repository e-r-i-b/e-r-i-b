package com.rssl.phizic.test.webgate.shop.generated.mock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.test.webgate.shop.generated.*;
import com.rssl.phizic.test.wsgateclient.shop.ShopSystemName;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author gulov
 * @ created 14.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Создает и заполняет объект заказа
 */
@SuppressWarnings({"MagicNumber"})
public class Document
{
	/**
	 * Наименование товара и Текстовое описание товара
	 */
	private static final String[][] TITLES =
		{{"Java сервлеты и JSP. Сборник рецептов", "Книга про Java"},
		 {"Java 2. Библиотека профессионала. Том 1. Основы", "Книга про Java"},
		 {"Java. Руководство для начинающих", "Книга про Java"},
		 {"Java SOAP для профессионалов", "Книга про Java"},
		 {"Java. Эффективное программирование", "Книга про Java"}};

	/**
	 * Валюты
	 */
	private static final CurrencyType[] CURRENCIES = {CurrencyType.RUB, CurrencyType.EUR, CurrencyType.USD};

	/**
	 * Макисмальное количество товаров входящих в заказ
	 */
	private static final int MAX_ITEMS = 5;

	/**
	 * Максимальное рандомное количество единиц товаров входящих в заказ
	 */
	private static final int MAX_COUNTS = 4;

	/**
	 * Описание документа
	 */
	private static final String DESCRIPTION = "Описание документа для отображения";
	private static final String PRINT_DESCRIPTION = "Описание документа для печатной формы";

	/**
	 * Длина корреспондирующего счета
	 */
	private static final int CORRESPONDENT_LENGTH = 35;

	/**
	 * Длина БИКа банка получателя
	 */
	private static final int BIC_LENGTH = 9;

	/**
	 * Длина ИНН получателя
	 */
	private static final int INN_LENGTH = 12;

	/**
	 * Длина КПП получателя
	 */
	private static final int KPP_LENGTH = 9;

	/**
	 * Шкала для округления
	 */
	private static final int SCALE = 4;

	// Кол-во вариантов пассажиров должно соответствовать кол-ву вариантов рейсов (см. ниже)
	private static final PassengerType[][] PASSENGER_LIST_VARIANTS = {
			new PassengerType[] {
					new PassengerType("IVANOV", "IVAN", "Adult"),
					new PassengerType("PETROVA", "INNA", "Adult"),
					new PassengerType("IVANOV", "Petr", "Infant Seat"),
			},
			new PassengerType[] {
					new PassengerType("VORONTZOV", "MAXIM", "Youth"),
					new PassengerType("VORONTZOVA", "OKSANA", "Youth"),
					new PassengerType("EMELIANOVA", "DANA", "Youth"),
					new PassengerType("PAVLOV", "DENIS", "Youth"),
			},
			new PassengerType[] {
					new PassengerType("NIKOLAY", "MELNIKOV", "Adult"),
					new PassengerType("LOBANOV", "VALERIY", "Infant Seat"),
					new PassengerType("GAVRIKOVA", "ELENA", "Infant Seat"),
					new PassengerType("SOLOVIEV", "LEONID", "Infant No Seat"),
					new PassengerType("TARASOV", "BORIS", "Infant No Seat"),
			},
	};

	private static final DepartureType DEPARTURE_VARIANTS[] = {
			new DepartureType("SVO", "Москва", "SVO1", "2012-08-01T10:00:00+04:00", "255"),

			new DepartureType("SVO", null, "SVO2", "2012-10-15T22:30:00+04:00", "107"),
			new DepartureType("GAO", "Афины", "GAOX", "2012-10-16T02:00:00+03:00", "A99"),

			new DepartureType("SVO", "Москва", null, "2012-07-25T17:00:00+04:00", "201"),
	};

	private static final ArrivalType ARRIVAL_VARIANTS[] = {
			new ArrivalType("IRO", "Рим", "IRO1", "2012-08-01T12:00:00+02:00", "255"),

			new ArrivalType("SVO", "Афины", "GAOX", "2012-10-16T01:00:00+03:00", "107"),
			new ArrivalType("SMO", "Мадрид", null, "2012-10-16T02:30:00+00:00", "A99"),

			new ArrivalType("TSL", null, "TSL", "2012-07-25T20:00:00+04:00", "201"),
	};

	private static final RouteType[][] ROUTE_LIST_VARIANTS = new RouteType[][] {
	        new RouteType[] {
			        new RouteType("Aeroflot", DEPARTURE_VARIANTS[0], ARRIVAL_VARIANTS[0]),
	        },
			new RouteType[] {
					new RouteType("Aeroflot", DEPARTURE_VARIANTS[1], ARRIVAL_VARIANTS[1]),
					new RouteType("Transaero", DEPARTURE_VARIANTS[2], ARRIVAL_VARIANTS[2]),
			},
			new RouteType[] {
					new RouteType("Aeroflot", DEPARTURE_VARIANTS[3], ARRIVAL_VARIANTS[3]),
			},
	};

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Сервис регистрации заказов
	 */
	private final DateFormat dateFormat = ShopConstants.getDateFormat();

	private final Random random = new Random();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Создать и заполнить заказ
	 * @param eribUID
	 * @return - заказ
	 */
	public RegRqDocumentType getDocument(String eribUID) throws BusinessException
	{
		RegRqDocumentType result = new RegRqDocumentType();

		//описание датасорса БД Einvoicing в основном приложении ЕРИБ нет, поэтому вытащить заказ из БД нет возможности, заполняем значения systemName и externalId вручную
		String systemName = ShopSystemName.OZON.name();
		String externalId = "fullf2767be1b7e0e7d10e18b96735ba3f18";

		result.setId(externalId);
		result.setAmount(getRandomTotalAmount());
		result.setAmountCur(CurrencyType.RUB);
		result.setDesc(DESCRIPTION);
		result.setAccount(getPersonalAccount());
		result.setCorrespondent(getRandomNumericText(CORRESPONDENT_LENGTH));
		result.setRecipient(getRandomNumericText(BIC_LENGTH));
		result.setTaxId(getRandomNumericText(INN_LENGTH));
		result.setKPP(getRandomNumericText(KPP_LENGTH));
		result.setRecipientName(systemName);
		result.setFields(getAdditionalFields(systemName));
		result.setPrintDesc(PRINT_DESCRIPTION);

		return result;
	}

	private FieldsType getAdditionalFields(String systemName)
	{
		FieldsType result = new FieldsType();

		if (ShopSystemName.AEROFLOT.toString().equals(systemName))
			result.setAirlineReservation(generateAirlineReservation());

		else
		{
			ItemType[] items = new ItemType[MAX_ITEMS];
			for (int i = 0; i < items.length; i++)
			{
				ItemType it = new ItemType();

				String[] title = getTitle();
				it.setName(title[0]);
				it.setItemDesc(title[1]);
				it.setPrice(getRandomItemAmount());
				it.setCount(getCount());
				it.setPriceCur(getCurrency());

				items[i] = it;
			}
			result.setShop(items);
		}

		return result;
	}

	private AirlineReservationType generateAirlineReservation()
	{
		AirlineReservationType reservation = new AirlineReservationType();
		reservation.setReservId("AirlineReservID-" + getRandomNumericText(32));

		// Вариант ответа
		int variant = random.nextInt(PASSENGER_LIST_VARIANTS.length);

		// (2) Указываем срок истечения от 20 до 100 секунд, либо вовсе не указываем
		if (random.nextInt(10) > 3) {
			Date expiration = new Date();
			expiration = DateUtils.addSeconds(expiration, 20 + random.nextInt(80));
			reservation.setReservExpiration(dateFormat.format(expiration));
		}

		// 3. Список пассажиров
		reservation.setPassengersList(PASSENGER_LIST_VARIANTS[variant]);

		// 4. Список рейсов
		reservation.setRoutesList(ROUTE_LIST_VARIANTS[variant]);

		return reservation;
	}

	private BigDecimal getRandomTotalAmount()
	{
		return new BigDecimal(10000 * random.nextDouble()).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}

	private BigDecimal getRandomItemAmount()
	{
		return new BigDecimal(500 * random.nextDouble()).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}

	private int getRandomInt(int max)
	{
		return random.nextInt(max);
	}

	private String[] getTitle()
	{
		return TITLES[getRandomInt(TITLES.length)];
	}

	private Long getCount()
	{
		return Long.valueOf(getRandomInt(MAX_COUNTS));
	}

	private CurrencyType getCurrency()
	{
		return CURRENCIES[getRandomInt(CURRENCIES.length)];
	}

	private String getPersonalAccount()
	{
		String text1 = getRandomNumericText(5);
		String text2 = getRandomNumericText(16);
		return text1 + "810" + text2;

	}

	private String getRandomNumericText(int max)
	{
		StringBuilder result = new StringBuilder(max);

		for (int i = 0; i < max; i++)
		{
			result.append(getRandomInt(9));
		}

		return result.toString();
	}
}
