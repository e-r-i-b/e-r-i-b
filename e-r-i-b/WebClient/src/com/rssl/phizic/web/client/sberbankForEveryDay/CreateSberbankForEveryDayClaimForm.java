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
 * ����� ��� ���������� ������ "�������� �� ������ ����"
 * @author shapin
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateSberbankForEveryDayClaimForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	private int stageNumber = 1;
	private int cardCount = 1; //���������� ����
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
	private boolean vipClient = false; //�������� �� ������ �����
	private String vipClientMessage;  //��������� ������� ���� �� �������� �����
	private String successConfirmMessage;    //��������� �� �������� �������� ������
	private boolean needEmptyDeptErrorMessage;  //�������, ������������ � ���, ��� ��������� �� �������
	private boolean isError; //������� ������ ��� ���������� ������
	private boolean isPreparedForConfirm; //������ ������ � �������������
	private boolean isFromHistory; // ������� �� �������� ������� ��������
	private String cardError; // ������ ��� �������������� ����� (� ����� ����)
	private String editCardNumber; // ����� �����, ��������� ��� ��������������
	private String maskedAfterCityResAddr; // ����� ���������� (������������� ����� ������ ����� ������)
	private String maskedAfterCityRegAddr; // ����� �������� (������������� ����� ������ ����� ������)

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
	 * @return ����� ���������� ����� �� ���������
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

		//���������� � ������
		for (int i =0; i<cardCount; i ++)
		{
			fb = new FieldBuilder();
			fb.setDescription("������ �����");
			fb.setName(CARD_CURRENCY_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��� �����");
			fb.setName(CARD_TYPE_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��� �����(��������)");
			fb.setName(CARD_TYPE_NUMBER_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��������� �������");
			fb.setName(CARD_CLIENT_CATEGORY_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("�������� �����");
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
			fb.setDescription("������� ������� ���-���������� �� �����");
			fb.setName(CARD_BIOAPPLET_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("������� ������ ����� � ���-���������");
			fb.setName(CARD_PINPACK_FIELD+i);
			fb.setType(Boolean.TYPE.toString());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��� �������� ���������");
			fb.setName(CARD_BONUS_FIELD+i);
			fb.setType(Boolean.TYPE.toString());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��������� ����� �����");
			fb.setName(CARD_CREDIT_LIMIT_FIELD+i);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());
		}

		if (isGuest)
		{
			//������� ���������� � ���
			fb = new FieldBuilder();
			fb.setDescription("�������");
			fb.setName(LAST_NAME_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator(".{0,60}", "�������� ���� \"�������\" �� ������ ��������� 60 ��������"),
					new RegexpFieldValidator("^[�-��-ߨ�]+$", "���� \"�������\" ������ ��������� ������ ����� �������� ��������"));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("���");
			fb.setName(FIRST_NAME_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(),
					new RegexpFieldValidator(".{0,30}", "�������� ���� \"���\" �� ������ ��������� 30 ��������"),
					new RegexpFieldValidator("^[�-��-ߨ�]+$", "���� \"���\" ������ ��������� ������ ����� �������� ��������"));
			formBuilder.addField(fb.build());

			if(!isPatrNameAbsent())
			{
				fb = new FieldBuilder();
				fb.setDescription("��������");
				fb.setName(PATR_NAME_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator(),
						new RegexpFieldValidator(".{0,30}", "�������� ���� \"��������\" �� ������ ��������� 30 ��������"),
						new RegexpFieldValidator("^[�-��-ߨ�]+$", "���� \"��������\" ������ ��������� ������ ����� �������� ��������"));
				formBuilder.addField(fb.build());
			}

			fb = new FieldBuilder();
			fb.setDescription("���");
			fb.setName(GENDER_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());
		}
		fb = new FieldBuilder();
		fb.setDescription("��� � ������� ���������");
		fb.setName(FIRST_NAME_AND_LAST_NAME_LATIN_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,19}", "�������� ���� \"��� � ������� ���������� �������\" �� ������ ��������� 19 ��������"),
				new RegexpFieldValidator("^([a-zA-Z]+)(\\.?)(\\s{1})([a-zA-Z]+)(\\.?)$", "�������� ���� \"��� � ������� ���������\" ��������� �����������. ��� � ������� ������ ���� ��������� �������� � ��������� ������ ��������� �����."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setDescription("E-mail ��� �����");
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
			//���������� � ������
			formBuilder = addCardFormBuilder();
		}

		if(getStageNumber() >= 2 && isGuest)
		{
			//���������� ������������ ������

			//�������������� ��������
			fb = new FieldBuilder();
			fb.setDescription("��� ���������");
			fb.setName(DOC_TYPE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			//��� ������� ���� ���� ������ ���� ����� �����
			buildDocFields(formBuilder);

			fb = new FieldBuilder();
			fb.setDescription("����� ��������");
			fb.setName(BIRTH_PLACE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,255}", "�������� ���� \"����� ��������\" �� ������ ��������� 255 ��������"));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("���� ��������");
			fb.setName(BIRTH_DATE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new DateNotInFutureValidator());
			formBuilder.addField(fb.build());

			if(!isRFCitizen())
			{
				fb = new FieldBuilder();
				fb.setDescription("�����������");
				fb.setName(NATIONALITY_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,30}", "�������� ���� \"�����������\" �� ������ ��������� 30 ��������"));
				formBuilder.addField(fb.build());
			}

			//����� ����������

			fb = new FieldBuilder();
			fb.setDescription("������");
			fb.setName(RESIDENTIAL_INDEX_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RegexpFieldValidator("[0-9]{0,6}", "�������� ���� \"������\" �� ������ ��������� 6 ��������"));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("�������/�����");
			fb.setName(RESIDENTIAL_REGION_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("���������� �����");
			fb.setName(RESIDENTIAL_LOCALITY_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("�����");
			fb.setName(RESIDENTIAL_STREET_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("���");
			fb.setName(RESIDENTIAL_BUILDING_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("������");
			fb.setName(RESIDENTIAL_CORPS_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��������");
			fb.setName(RESIDENTIAL_ROOM_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			if(!isLivingByRegistrationPlace())
			{
				//����� ��������
				fb = new FieldBuilder();
				fb.setDescription("������");
				fb.setName(REGISTRATION_INDEX_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RegexpFieldValidator("[0-9]{0,6}", "�������� ���� \"������\" �� ������ ��������� 6 ��������"));
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("�������/�����");
				fb.setName(REGISTRATION_REGION_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("���������� �����");
				fb.setName(REGISTRATION_LOCALITY_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("�����");
				fb.setName(REGISTRATION_STREET_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("���");
				fb.setName(REGISTRATION_BUILDING_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("������");
				fb.setName(REGISTRATION_CORPS_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("��������");
				fb.setName(REGISTRATION_ROOM_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				formBuilder.addField(fb.build());
			}

		}


		if(getStageNumber() >= 3)
		{
			//���������� �����

			//��������� ����
			fb = new FieldBuilder();
			fb.setDescription("����� ���������� �����");
			fb.setName(MOBILE_BANK_TARIFF_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			if(isNeedAutopayment())
			{
				//�����������
				fb = new FieldBuilder();
				fb.setDescription("��������� ��������");
				fb.setName(MOBILE_OPERATOR_FIELD);
				fb.setType(StringType.INSTANCE.getName());
				//CHG084412 �������� ������� ���� ��������� ��������
				//fb.addValidators(new RequiredFieldValidator());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("���� ������ ������");
				fb.setName(BALANCE_LESS_THAN_FIELD);
				fb.setType(LongType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				String maxSum = this.getField(MAX_SUM_FIELD).toString();
				String minSum = this.getField(MIN_SUM_FIELD).toString();

				fb = new FieldBuilder();
				fb.setDescription("��������� �� �����");
				fb.setName(REFILL_ON_SUM_FIELD);
				fb.setType(MoneyType.INSTANCE.getName());
				fb.addValidators(new RequiredFieldValidator(), new NumericRangeValidator(BigDecimal.valueOf(Long.parseLong(minSum)), BigDecimal.valueOf(Long.parseLong(maxSum)),
						"�������� ���� \"��������� �� �����\" ������ ���� � ��������� �� " + minSum + "�� " + String.valueOf(maxSum)));
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("����������� ����� �������");
				fb.setName(MIN_SUM_FIELD);
				fb.setType(LongType.INSTANCE.getName());
				formBuilder.addField(fb.build());

				fb = new FieldBuilder();
				fb.setDescription("������������ ����� �������");
				fb.setName(MAX_SUM_FIELD);
				fb.setType(LongType.INSTANCE.getName());
				formBuilder.addField(fb.build());

			}
		}

		if (isGuest)
		{
			fb = new FieldBuilder();
			fb.setDescription("������");
			fb.setName(REGION_ID_FIELD);
			fb.setType(LongType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("�������� �������");
			fb.setName(REGION_NAME_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��");
			fb.setName(PERSON_TB_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new AllowedTbForSBNKDValidator(CreateSberbankForEveryDayClaimAction.TB_NOT_ALLOWED_ERROR_MESSAGE));
			formBuilder.addField(fb.build());
		}

		//����� ��������� ��� ��������� �����
		if(getStageNumber() >= 4)
		{
			fb = new FieldBuilder();
			fb.setDescription("������������ ���������");
			fb.setName(CREDIT_CARD_OFFICE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("��");
			fb.setName(TB_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new AllowedTbForSBNKDValidator(CreateSberbankForEveryDayClaimAction.TB_NOT_ALLOWED_ERROR_MESSAGE));
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("���");
			fb.setName(OSB_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fb.build());

			fb = new FieldBuilder();
			fb.setDescription("���");
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
		fb.setDescription("����� � �����");
		fb.setName(SERIES_AND_NUMBER_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "�������� ���� \"����� � �����\" �� ������ ��������� 50 ��������")
		);
		formBuilder.addField(fb.build());

		DocumentSeriesAndNumberValidator documentValidator = new DocumentSeriesAndNumberValidator();
		documentValidator.setBinding("documentType", DOC_TYPE_FIELD);
		documentValidator.setBinding("documentSeriesAndNumber", SERIES_AND_NUMBER_FIELD);
		formBuilder.addFormValidators(documentValidator);

		if(doc.equals(ClientDocumentType.REGULAR_PASSPORT_RF))
		{
			fb = new FieldBuilder();
			fb.setDescription("��� �������������");
			fb.setName(OFFICE_CODE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RegexpFieldValidator(".{0,10}", "�������� ���� \"��� �������������\" �� ������ ��������� 10 ��������"));
			formBuilder.addField(fb.build());
		}

		if(!doc.equals(ClientDocumentType.FOREIGN_PASSPORT))
		{
			fb = new FieldBuilder();
			fb.setDescription("��� �����");
			fb.setName(ISSUED_BY_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,100}", "�������� ���� \"��� �����\" �� ������ ��������� 100 ��������"));
			formBuilder.addField(fb.build());
		}

		fb = new FieldBuilder();
		fb.setDescription("���� ������");
		fb.setName(ISSUE_DATE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(), new DateNotInFutureValidator());
		formBuilder.addField(fb.build());

		if(doc.equals(ClientDocumentType.RESIDENTIAL_PERMIT_RF) || doc.equals(ClientDocumentType.TEMPORARY_PERMIT))
		{
			fb = new FieldBuilder();
			fb.setDescription("���� ��������");
			fb.setName(EXPIRE_DATE_FIELD);
			fb.setType(StringType.INSTANCE.getName());
			fb.addValidators(new RequiredFieldValidator(), new DateNotInPastValidator(DATE_FORMAT, "���� �������� ��������� ������ ���� ������ ���� ����� ������� ��������� ����."));
			formBuilder.addField(fb.build());
		}
	}
}
