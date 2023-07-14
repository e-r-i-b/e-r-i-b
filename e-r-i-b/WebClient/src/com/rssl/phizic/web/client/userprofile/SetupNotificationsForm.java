package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.operations.userprofile.*;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 09.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class SetupNotificationsForm extends EditFormBase
{
	public static final String EMAIL_FIELD_NAME = "email";
	public static final String EMAIL_FORMAT_FIELD_NAME = "emailFormat";
	public static final String PHONE_FIELD_NAME = "phone";
	public static final String PUSH_FIELD_NAME = "push";
	public static final String MOBILE_DEVICES_FIELD_NAME = "mobileDevices";
	public static final String PHONE_FOR_MAIL_FIELD_NAME = "phoneForMail";

	public static final Form EDIT_FORM = createForm(UserNotificationType.loginNotification.name(), PHONE_FIELD_NAME, new String[] { "sms", "email"});
	public static final Form EDIT_FORM_WITH_PUSH = createForm(UserNotificationType.loginNotification.name(), PHONE_FIELD_NAME, new String[]{"sms", "email", "push"});
	public static final Form EDIT_MAIL_NOTIFICATION_SETTINGS_FORM =
			createForm(UserNotificationType.mailNotification.name(), PHONE_FOR_MAIL_FIELD_NAME, new String[]{"sms", "email", "none"});
	public static final Form EDIT_MAIL_NOTIFICATION_SETTINGS_FORM_WITH_PUSH =
			createForm(UserNotificationType.mailNotification.name(), PHONE_FOR_MAIL_FIELD_NAME, new String[]{"sms", "email", "push", "none"});
	public static final Form EDIT_DELIVERY_NOTIFICATION_SETTINGS_FORM =
			createForm(UserNotificationType.operationNotification.name(), PHONE_FOR_MAIL_FIELD_NAME, new String[]{"sms", "email", "none"});
	public static final Form EDIT_DELIVERY_NOTIFICATION_SETTINGS_FORM_WITH_PUSH =
			createForm(UserNotificationType.operationNotification.name(), PHONE_FOR_MAIL_FIELD_NAME, new String[]{"sms", "email", "push", "none"});
	public static final Form EDIT_BANK_NEWS_NOTIFICATION_SETTINGS_FORM =
			createForm(UserNotificationType.newsNotification.name(), PHONE_FOR_MAIL_FIELD_NAME, new String[]{"email", "none"});
	private ConfirmableObject confirmableObject;
	private ConfirmableObject productsSmsNotificationConfirmableObject;
	private ConfirmStrategy notificationConfirmStrategy;
	private String confirmedType;
	private ConfirmStrategy productsSmsNotificationConfirmStrategy;

	private List<CardLink> cards = new ArrayList<CardLink>();
	private String[] selectedCardIds = new String[0];
	private List<AccountLink> accounts = new ArrayList<AccountLink>();
	private String[] selectedAccountIds = new String[0];
	private List<LoanLink> loans = new ArrayList<LoanLink>();
	private String[] selectedLoanIds = new String[0];
	private boolean newProductsNotification;

	private boolean showResourcesSmsNotificationBlock;
	private boolean isTariffAllowCardSmsNotification;
	private boolean isTariffAllowAccountSmsNotification;

	public ConfirmStrategy getNotificationConfirmStrategy()
	{
		return notificationConfirmStrategy;
	}

	public void setNotificationConfirmStrategy(ConfirmStrategy notificationConfirmStrategy)
	{
		this.notificationConfirmStrategy = notificationConfirmStrategy;
	}

	public String getConfirmedType()
	{
		return confirmedType;
	}

	public void setConfirmedType(String confirmedType)
	{
		this.confirmedType = confirmedType;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmableObject getProductsSmsNotificationConfirmableObject()
	{
		return productsSmsNotificationConfirmableObject;
	}

	public void setProductsSmsNotificationConfirmableObject(ConfirmableObject productsSmsNotificationConfirmableObject)
	{
		this.productsSmsNotificationConfirmableObject = productsSmsNotificationConfirmableObject;
	}

	public ConfirmStrategy getProductsSmsNotificationConfirmStrategy()
	{
		return productsSmsNotificationConfirmStrategy;
	}

	public void setProductsSmsNotificationConfirmStrategy(ConfirmStrategy productsSmsNotificationConfirmStrategy)
	{
		this.productsSmsNotificationConfirmStrategy = productsSmsNotificationConfirmStrategy;
	}

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public String[] getSelectedCardIds()
	{
		return selectedCardIds;
	}

	public void setSelectedCardIds(String[] selectedCardIds)
	{
		this.selectedCardIds = selectedCardIds;
	}

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	public String[] getSelectedAccountIds()
	{
		return selectedAccountIds;
	}

	public void setSelectedAccountIds(String[] selectedAccountIds)
	{
		this.selectedAccountIds = selectedAccountIds;
	}

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public String[] getSelectedLoanIds()
	{
		return selectedLoanIds;
	}

	public void setSelectedLoanIds(String[] selectedLoanIds)
	{
		this.selectedLoanIds = selectedLoanIds;
	}

	private static Form createForm(String fieldName, String phoneType, String[] chooseValues)
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();
		fb.setName(fieldName);
		fb.setDescription("Способ оповещения");
		fb.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(chooseValues), "Данный тип оповещения не поддерживается.")
		);
		formBuilder.addField(fb.build());

		formBuilder = createNotificationSettingsFormBase(fieldName, phoneType, formBuilder);

		return formBuilder.build();
	}

	private static FormBuilder createNotificationSettingsFormBase(String fieldName, String phoneType, FormBuilder formBuilder)
	{
		FieldBuilder fb = new FieldBuilder();
		fb.setName(EMAIL_FIELD_NAME);
		fb.setDescription("Электронный адрес");
		FieldValidator reqEmailFieldValidator = new RequiredFieldValidator("В персональной информации не указан Ваш E-mail для отправки уведомлений. Пожалуйста, обратитесь в банк.");
		reqEmailFieldValidator.setEnabledExpression(new RhinoExpression("form." + fieldName + " == 'email'"));
		fb.addValidators(reqEmailFieldValidator);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(EMAIL_FORMAT_FIELD_NAME);
		fb.setDescription("Формат отправки сообщений клиенту на электронную почту");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(phoneType);
		fb.setDescription("Номер телефона");
		FieldValidator reqPhoneFieldValidator = new RequiredFieldValidator("В персональной информации не указан номер Вашего мобильного телефона для отправки уведомлений, или не подключена услуга \"Мобильный банк\". Пожалуйста, обратитесь в банк.");
		reqPhoneFieldValidator.setEnabledExpression(new RhinoExpression("form." + fieldName + " == 'sms'"));
		fb.addValidators(reqPhoneFieldValidator);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(MOBILE_DEVICES_FIELD_NAME);
		fb.setDescription("Мобильные приложения");
		FieldValidator reqMobileDeviceFieldValidator = new RequiredFieldValidator("Для отправки push-уведомлений на мобильные устройства Вам необходимо установить и зарегистрировать мобильное приложение «Сбербанк Онлайн».");
		reqMobileDeviceFieldValidator.setEnabledExpression(new RhinoExpression("form." + fieldName + " == 'push'"));
		fb.addValidators(reqMobileDeviceFieldValidator);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(PUSH_FIELD_NAME);
		fb.setDescription("Мобильные приложения с подключенными push-уведомлениями");
		FieldValidator reqPushFieldValidator = new RequiredFieldValidator("Получение уведомлений по Push вам пока недоступно. Мы постепенно подключаем новых клиентов к услуге. Благодарим за терпение.");
		reqPushFieldValidator.setEnabledExpression(new RhinoExpression("form." + fieldName + " == 'push'"));
		fb.addValidators(reqPushFieldValidator);
		formBuilder.addField(fb.build());

		return formBuilder;
	}

	/**
	 * сэтим нужный ConfirmableObject
	 * @param operation операция
	 */
	public void setCurrentConfirmableObject(SetupNotificationOperation operation)
	{
		this.confirmableObject = operation.getConfirmableObject();
	}

	public void setCurrentResourcesSmsNotificationConfirmableObject(SetupResourcesSmsNotificationOperation operation)
	{
		this.productsSmsNotificationConfirmableObject = operation.getConfirmableObject();
	}

	public boolean isShowResourcesSmsNotificationBlock()
	{
		return showResourcesSmsNotificationBlock;
	}

	public void setShowResourcesSmsNotificationBlock(boolean showResourcesSmsNotificationBlock)
	{
		this.showResourcesSmsNotificationBlock = showResourcesSmsNotificationBlock;
	}

	public boolean isTariffAllowCardSmsNotification()
	{
		return isTariffAllowCardSmsNotification;
	}

	public void setTariffAllowCardSmsNotification(boolean tariffAllowCardSmsNotification)
	{
		isTariffAllowCardSmsNotification = tariffAllowCardSmsNotification;
	}

	public boolean isTariffAllowAccountSmsNotification()
	{
		return isTariffAllowAccountSmsNotification;
	}

	public void setTariffAllowAccountSmsNotification(boolean tariffAllowAccountSmsNotification)
	{
		isTariffAllowAccountSmsNotification = tariffAllowAccountSmsNotification;
	}

	public boolean isNewProductsNotification()
	{
		return newProductsNotification;
	}

	public void setNewProductsNotification(boolean newProductsNotification)
	{
		this.newProductsNotification = newProductsNotification;
	}
}
