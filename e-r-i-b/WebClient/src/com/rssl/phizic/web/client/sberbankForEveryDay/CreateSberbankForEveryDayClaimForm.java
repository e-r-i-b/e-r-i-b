package com.rssl.phizic.web.client.sberbankForEveryDay;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.sbnkd.DocumentSeriesAndNumberValidator;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.List;

/**
 * Форма для оформления заявки "Сбербанк на каждый день"
 * @author shapin
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateSberbankForEveryDayClaimForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	private int stageNumber = 1;
	private int cardCount = 1; //количество карт
	private boolean patrNameAbsent = false;
	private boolean RFCitizen = true;
	private boolean livingByRegistrationPlace = true;
	private boolean needAutopayment = true;
	private List<CardInfo> cardList;
	private List<String> documentTypes;
	private boolean isGuest;
	private int editStageNumber = 1;
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;
	private IssueCardDocumentImpl issueCardDoc;
	private int removeCardNumber;
	private boolean vipClient = false; //является ли клиент випом
	private String vipClientMessage;  //сообщение клиенту если он является випом
	private String successConfirmMessage;    //сообщение об успешной отправке заявки
	private boolean needEmptyDeptErrorMessage;  //признак, утверждающий о том, что отделение не выбрано
	private boolean isError; //признак ошибки при заполнении заявки
	private boolean isPreparedForConfirm; //заявка готова к подтверждению
	private boolean isFromHistory; // переход со страницы история операций
	private String cardError; // ошибка при редактировании карты (в новом окне)
	private String editCardNumber; // номер карты, выбранной для редактирования
	private String maskedAfterCityResAddr; // адрес проживания (маскированная часть адреса после города)
	private String maskedAfterCityRegAddr; // адрес прописки (маскированная часть адреса после города)

	public static String CARD_CURRENCY_FIELD = "cardCurrency";
	public static String CARD_TYPE_FIELD =  "cardType";
	public static String CARD_TYPE_NUMBER_FIELD = "cardTypeNumber";
	public static String CARD_NAME_FIELD = "cardName";
	public static String CARD_CODEWAY_FIELD = "cardCODEWAY4";
	public static String CARD_CODEWAY_SHORT_FIELD = "cardCODEWAY4SHORT";
	public static String CARD_BIOAPPLET_FIELD = "cardBIOAPPLET";
	public static String CARD_PINPACK_FIELD = "cardPINPAC";
	public static String CARD_BONUS_FIELD = "cardBONUSCODE";
	public static String CARD_CREDIT_LIMIT_FIELD = "cardCreditLimit";
	public static String CARD_CLIENT_CATEGORY_FIELD = "cardClientCategory";
	public static String CARD_TYPE_SELECT = "cardTypeSelect";
	public static String CARD_CLIENT_AGREE_TICK = "agreeTick";
	public static String LAST_NAME_FIELD = "lastName";
	public static String FIRST_NAME_FIELD = "firstName";
	public static String PATR_NAME_FIELD = "patrName";
	public static String GENDER_FIELD = "gender";
	public static String FIRST_NAME_AND_LAST_NAME_LATIN_FIELD = "firstNameAndLastNameLatin";
	public static String EMAIL_FIELD = "email";
	public static String DOC_TYPE_FIELD = "docType";
	public static String SERIES_AND_NUMBER_FIELD = "seriesAndNumber";
	public static String OFFICE_CODE_FIELD = "officeCode";
	public static String ISSUED_BY_FIELD = "issuedBy";
	public static String ISSUE_DATE_FIELD = "issueDate";
	public static String EXPIRE_DATE_FIELD = "expireDate";
	public static String BIRTH_PLACE_FIELD = "birthPlace";
	public static String BIRTH_DATE_FIELD = "birthDate";
	public static String NATIONALITY_FIELD = "nationality";
	public static String RESIDENTIAL_INDEX_FIELD = "residentialIndex";
	public static String REGION_ID_FIELD = "regionId";
	public static String REGION_NAME_FIELD = "regionName";
	public static String RESIDENTIAL_REGION_FIELD = "residentialRegion";
	public static String RESIDENTIAL_LOCALITY_FIELD = "residentialLocality";
	public static String RESIDENTIAL_STREET_FIELD = "residentialStreet";
	public static String RESIDENTIAL_BUILDING_FIELD = "residentialBuilding";
	public static String RESIDENTIAL_CORPS_FIELD = "residentialCorps";
	public static String RESIDENTIAL_ROOM_FIELD = "residentialRoom";
	public static String REGISTRATION_INDEX_FIELD = "registrationIndex";
	public static String REGISTRATION_REGION_FIELD = "registrationRegion";
	public static String REGISTRATION_LOCALITY_FIELD = "registrationLocality";
	public static String REGISTRATION_STREET_FIELD = "registrationStreet";
	public static String REGISTRATION_BUILDING_FIELD = "registrationBuilding";
	public static String REGISTRATION_CORPS_FIELD = "registrationCorps";
	public static String REGISTRATION_ROOM_FIELD = "registrationRoom";
	public static String MOBILE_BANK_TARIFF_FIELD = "mobileBankTariff";
	public static String MOBILE_OPERATOR_FIELD = "mobileOperator";
	public static String BALANCE_LESS_THAN_FIELD = "balanceLessThan";
	public static String REFILL_ON_SUM_FIELD = "refillOnSum";
	public static String MIN_SUM_FIELD = "minSum";
	public static String MAX_SUM_FIELD = "maxSum";
	public static String AUTOPAYMENT_IS_AVAILABLE = "autopaymentIsAvailable";
	public static String CREDIT_CARD_OFFICE_FIELD = "credit-card-office";
	public static String PERSON_TB_FIELD = "personTB";
	public static String TB_FIELD = "tb";
	public static String OSB_FIELD = "osb";
	public static String VSP_FIELD = "vsp";

	public int getRemoveCardNumber()
	{
		return removeCardNumber;
	}

	public void setRemoveCardNumber(int removeCardNumber)
	{
		this.removeCardNumber = removeCardNumber;
	}

	public int getEditStageNumber()
	{
		return editStageNumber;
	}

	public void setEditStageNumber(int editStageNumber)
	{
		this.editStageNumber = editStageNumber;
	}

	public void setStageNumber(int stageNumber)
	{
		this.stageNumber = stageNumber;
	}

	public int getStageNumber()
	{
		return stageNumber;
	}

	public int getCardCount()
	{
		return cardCount;
	}

	public void setCardCount(int cardCount)
	{
		this.cardCount = cardCount;
	}

	public void setPatrNameAbsent(boolean patrNameAbsent)
	{
		this.patrNameAbsent = patrNameAbsent;
	}

	public boolean isPatrNameAbsent()
	{
		return patrNameAbsent;
	}

	public void setRFCitizen(boolean RFCitizen)
	{
		this.RFCitizen = RFCitizen;
	}

	public boolean isRFCitizen()
	{
		return RFCitizen;
	}

	public void setLivingByRegistrationPlace(boolean livingByRegistrationPlace)
	{
		this.livingByRegistrationPlace = livingByRegistrationPlace;
	}

	public boolean isLivingByRegistrationPlace()
	{
		return livingByRegistrationPlace;
	}

	public void setNeedAutopayment(boolean needAutopayment)
	{
		this.needAutopayment = needAutopayment;
	}

	public boolean isNeedAutopayment()
	{
		return needAutopayment;
	}

	public List<CardInfo> getCardList()
	{
		return cardList;
	}

	public void setCardList(List<CardInfo> cardList)
	{
		this.cardList = cardList;
	}

	public boolean isGuest()
	{
		return isGuest;
	}

	public void setGuest(boolean guest)
	{
		isGuest = guest;
	}

	public List<String> getDocumentTypes()
	{
		return documentTypes;
	}

	public void setDocumentTypes(List<String> documentTypes)
	{
		this.documentTypes = documentTypes;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public IssueCardDocumentImpl getIssueCardDoc()
	{
		return issueCardDoc;
	}

	public void setIssueCardDoc(IssueCardDocumentImpl issueCardDoc)
	{
		this.issueCardDoc = issueCardDoc;
	}

	public boolean isVipClient()
	{
		return vipClient;
	}

	public void setVipClient(boolean vipClient)
	{
		this.vipClient = vipClient;
	}

	public String getVipClientMessage()
	{
		return vipClientMessage;
	}

	public void setVipClientMessage(String vipClientMessage)
	{
		this.vipClientMessage = vipClientMessage;
	}

	public String getSuccessConfirmMessage()
	{
		return successConfirmMessage;
	}

	public void setSuccessConfirmMessage(String successConfirmMessage)
	{
		this.successConfirmMessage = successConfirmMessage;
	}

	public boolean isNeedEmptyDeptErrorMessage()
	{
		return needEmptyDeptErrorMessage;
	}

	public void setNeedEmptyDeptErrorMessage(boolean needEmptyDeptErrorMessage)
	{
		this.needEmptyDeptErrorMessage = needEmptyDeptErrorMessage;
	}

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}

	public boolean isPreparedForConfirm()
	{
		return isPreparedForConfirm;
	}

	public void setPreparedForConfirm(boolean preparedForConfirm)
	{
		isPreparedForConfirm = preparedForConfirm;
	}

	public boolean isFromHistory()
	{
		return isFromHistory;
	}

	public void setFromHistory(boolean fromHistory)
	{
		isFromHistory = fromHistory;
	}

	/**
	 * @return пакет мобильного банка по умолчанию
	 */
	public String getDefaultMobileBankTariff()
	{
		return ConfigFactory.getConfig(SBNKDConfig.class).getDefaultPackageMobileBank();
	}

	public String getCardError()
	{
		return cardError;
	}

	public void setCardError(String cardError)
	{
		this.cardError = cardError;
	}

	public String getEditCardNumber()
	{
		return editCardNumber;
	}

	public void setEditCardNumber(String editCardNumber)
	{
		this.editCardNumber = editCardNumber;
	}

	public String getMaskedAfterCityResAddr()
	{
		return maskedAfterCityResAddr;
	}

	public void setMaskedAfterCityResAddr(String maskedAfterCityResAddr)
	{
		this.maskedAfterCityResAddr = maskedAfterCityResAddr;
	}

	public String getMaskedAfterCityRegAddr()
	{
		return maskedAfterCityRegAddr;
	}

	public void setMaskedAfterCityRegAddr(String maskedAfterCityRegAddr)
	{
		this.maskedAfterCityRegAddr = maskedAfterCityRegAddr;
	}

	private FormBuilder addCardFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//информация о картах
		for (int i =0; i<cardCount; i ++)
		{
			fb = new FieldBuilder();
			fb.setDescription("Валюта карты");
			fb.setName(CARD_CURRENCY_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Тип карты");
			fb.setName(CARD_TYPE_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Тип карты(числовой)");
			fb.setName(CARD_TYPE_NUMBER_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Категория клиента");
			fb.setName(CARD_CLIENT_CATEGORY_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Название карты");
			fb.setName(CARD_NAME_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("CODEWAY4");
			fb.setName(CARD_CODEWAY_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("CODEWAY4SHORT");
			fb.setName(CARD_CODEWAY_SHORT_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Признак наличия БИО-приложения на карте");
			fb.setName(CARD_BIOAPPLET_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Признак выдачи карты с ПИН-конвертом");
			fb.setName(CARD_PINPACK_FIELD+i);
			fb.setType(Boolean.TYPE.toString());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Код бонусной программы");
			fb.setName(CARD_BONUS_FIELD+i);
			fb.setType(Boolean.TYPE.toString());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Кредитный лимит карты");
			fb.setName(CARD_CREDIT_LIMIT_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());
		}

		if (isGuest)
		{
			//Краткая информация о вас
			fb = new FieldBuilder();
			fb.setDescription("Фамилия");
			fb.setName(LAST_NAME_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator(".{0,60}", "Значение поля \"Фамилия\" не должно превышать 60 символов"),
					new RegexpFieldValidator("^[а-яА-ЯЁё]+$", "Поле \"Фамилия\" должно содержать только буквы русского алфавита"));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Имя");
			fb.setName(FIRST_NAME_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator(".{0,30}", "Значение поля \"Имя\" не должно превышать 30 символов"),
					new RegexpFieldValidator("^[а-яА-ЯЁё]+$", "Поле \"Имя\" должно содержать только буквы русского алфавита"));
			formBuilder.addField(fb.build());

			if(!isPatrNameAbsent())
			{
				fb = new FieldBuilder();
				fb.setDescription("Отчество");
				fb.setName(PATR_NAME_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator(),
						new RegexpFieldValidator(".{0,30}", "Значение поля \"Отчество\" не должно превышать 30 символов"),
						new RegexpFieldValidator("^[а-яА-ЯЁё]+$", "Поле \"Отчество\" должно содержать только буквы русского алфавита"));
				formBuilder.addField(fb.build());
			}

			fb = new FieldBuilder();
			fb.setDescription("Пол");
			fb.setName(GENDER_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());
		}
		fb = new FieldBuilder();
		fb.setDescription("Имя и фамилия латиницей");
		fb.setName(FIRST_NAME_AND_LAST_NAME_LATIN_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,19}", "Значение поля \"Имя и фамилия латинскими буквами\" не должно превышать 19 символов"),
				new RegexpFieldValidator("^([a-zA-Z]+)(\\.?)(\\s{1})([a-zA-Z]+)(\\.?)$", "Значение поля \"Имя и фамилия латиницей\" заполнено некорректно. Имя и фамилия должны быть разделены пробелом и содержать только латинские буквы."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setDescription("E-mail для связи");
		fb.setName(EMAIL_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new EmailFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder;
	}
	public Form createAddCardForm()
	{
		return addCardFormBuilder().build();
	}
	public Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
	    FieldBuilder fb = new FieldBuilder();

		if(getStageNumber() >= 1)
		{
			//информация о картах
			formBuilder = addCardFormBuilder();
		}

		if(getStageNumber() >= 2 && isGuest)
		{
			//Заполнение персональных данных

			//Удостоверяющий документ
			fb = new FieldBuilder();
			fb.setDescription("Тип документа");
			fb.setName(DOC_TYPE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			//для каждого типа ДУЛа строим свой набор полей
			buildDocFields(formBuilder);

			fb = new FieldBuilder();
			fb.setDescription("Место рождения");
			fb.setName(BIRTH_PLACE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,255}", "Значение поля \"Место рождения\" не должно превышать 255 символов"));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Дата рождения");
			fb.setName(BIRTH_DATE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new DateNotInFutureValidator());
			formBuilder.addField(fb.build());

			if(!isRFCitizen())
			{
				fb = new FieldBuilder();
				fb.setDescription("Гражданство");
				fb.setName(NATIONALITY_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,30}", "Значение поля \"Гражданство\" не должно превышать 30 символов"));
				formBuilder.addField(fb.build());
			}

			//Адрес проживания

			fb = new FieldBuilder();
			fb.setDescription("Индекс");
			fb.setName(RESIDENTIAL_INDEX_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RegexpFieldValidator("[0-9]{0,6}", "Значение поля \"Индекс\" не должно превышать 6 символов"));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Область/район");
			fb.setName(RESIDENTIAL_REGION_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Населенный пункт");
			fb.setName(RESIDENTIAL_LOCALITY_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Улица");
			fb.setName(RESIDENTIAL_STREET_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Дом");
			fb.setName(RESIDENTIAL_BUILDING_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Корпус");
			fb.setName(RESIDENTIAL_CORPS_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Квартира");
			fb.setName(RESIDENTIAL_ROOM_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			if(!isLivingByRegistrationPlace())
			{
				//Адрес прописки
				fb = new FieldBuilder();
				fb.setDescription("Индекс");
				fb.setName(REGISTRATION_INDEX_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RegexpFieldValidator("[0-9]{0,6}", "Значение поля \"Индекс\" не должно превышать 6 символов"));
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Область/район");
				fb.setName(REGISTRATION_REGION_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Населенный пункт");
				fb.setName(REGISTRATION_LOCALITY_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Улица");
				fb.setName(REGISTRATION_STREET_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Дом");
				fb.setName(REGISTRATION_BUILDING_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Корпус");
				fb.setName(REGISTRATION_CORPS_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Квартира");
				fb.setName(REGISTRATION_ROOM_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				formBuilder.addField(fb.build());
			}

		}


		if(getStageNumber() >= 3)
		{
			//Добавление услуг

			//Мобильный банк
			fb = new FieldBuilder();
			fb.setDescription("Тариф мобильного банка");
			fb.setName(MOBILE_BANK_TARIFF_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			if(isNeedAutopayment())
			{
				//Автоплатежи
				fb = new FieldBuilder();
				fb.setDescription("Мобильный оператор");
				fb.setName(MOBILE_OPERATOR_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				//CHG084412 временно убираем поле Мобильный оператор
				//fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Если баланс меньше");
				fb.setName(BALANCE_LESS_THAN_FIELD);
				fb.setType(LongType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				String maxSum = this.getField(MAX_SUM_FIELD).toString();
				String minSum = this.getField(MIN_SUM_FIELD).toString();

				fb = new FieldBuilder();
				fb.setDescription("Пополнять на сумму");
				fb.setName(REFILL_ON_SUM_FIELD);
				fb.setType(MoneyType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator(), new NumericRangeValidator(BigDecimal.valueOf(Long.parseLong(minSum)), BigDecimal.valueOf(Long.parseLong(maxSum)),
						"Значение поля \"Пополнять на сумму\" должно быть в диапазоне от " + minSum + "до " + String.valueOf(maxSum)));
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Минимальная сумма платежа");
				fb.setName(MIN_SUM_FIELD);
				fb.setType(LongType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("Максимальная сумма платежа");
				fb.setName(MAX_SUM_FIELD);
				fb.setType(LongType.INSTANCE.getName());
				formBuilder.addField(fb.build());

			}
		}

		if (isGuest)
		{
			fb = new FieldBuilder();
			fb.setDescription("Регион");
			fb.setName(REGION_ID_FIELD);
			fb.setType(LongType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("Название региона");
			fb.setName(REGION_NAME_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("ТБ");
			fb.setName(PERSON_TB_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new AllowedTbForSBNKDValidator(CreateSberbankForEveryDayClaimAction.TB_NOT_ALLOWED_ERROR_MESSAGE));
			formBuilder.addField(fb.build());
		}

		//Выбор отделения для получения карты
		if(getStageNumber() >= 4)
		{
			fb = new FieldBuilder();
			fb.setDescription("Наименование отделения");
			fb.setName(CREDIT_CARD_OFFICE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("ТБ");
			fb.setName(TB_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new AllowedTbForSBNKDValidator(CreateSberbankForEveryDayClaimAction.TB_NOT_ALLOWED_ERROR_MESSAGE));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("ОСБ");
			fb.setName(OSB_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("ВСП");
			fb.setName(VSP_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());
		}

		return formBuilder.build();
	}

	private void buildDocFields(FormBuilder formBuilder)
	{
		ClientDocumentType doc = ClientDocumentType.valueOf(this.getField(DOC_TYPE_FIELD).toString());
		FieldBuilder fb = new FieldBuilder();
		fb.setDescription("Серия и номер");
		fb.setName(SERIES_AND_NUMBER_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "Значение поля \"Серия и номер\" не должно превышать 50 символов")
		);
		formBuilder.addField(fb.build());

		DocumentSeriesAndNumberValidator documentValidator = new DocumentSeriesAndNumberValidator();
		documentValidator.setBinding("documentType", DOC_TYPE_FIELD);
		documentValidator.setBinding("documentSeriesAndNumber", SERIES_AND_NUMBER_FIELD);
		formBuilder.addFormValidators(documentValidator);

		if(doc.equals(ClientDocumentType.REGULAR_PASSPORT_RF))
		{
			fb = new FieldBuilder();
			fb.setDescription("Код подразделения");
			fb.setName(OFFICE_CODE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RegexpFieldValidator(".{0,10}", "Значение поля \"Код подразделения\" не должно превышать 10 символов"));
			formBuilder.addField(fb.build());
		}

		if(!doc.equals(ClientDocumentType.FOREIGN_PASSPORT))
		{
			fb = new FieldBuilder();
			fb.setDescription("Кем выдан");
			fb.setName(ISSUED_BY_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,100}", "Значение поля \"Кем выдан\" не должно превышать 100 символов"));
			formBuilder.addField(fb.build());
		}

		fb = new FieldBuilder();
		fb.setDescription("Дата выдачи");
		fb.setName(ISSUE_DATE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(), new DateNotInFutureValidator());
		formBuilder.addField(fb.build());

		if(doc.equals(ClientDocumentType.RESIDENTIAL_PERMIT_RF) || doc.equals(ClientDocumentType.TEMPORARY_PERMIT))
		{
			fb = new FieldBuilder();
			fb.setDescription("Срок действия");
			fb.setName(EXPIRE_DATE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new DateNotInPastValidator(DATE_FORMAT, "Срок действия документа должен быть больше либо равен текущей системной дате."));
			formBuilder.addField(fb.build());
		}
	}
}
