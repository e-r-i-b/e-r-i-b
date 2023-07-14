package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BooleanParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class SetupSecurityForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm(new String[]{"sms", "card"});
	public static final Form EDIT_FORM_WITH_PUSH = createForm(new String[]{"sms", "card", "push"});
	public static final Form GENERATE_PASSWORD_FORM = createGeneratePasswordForm();
	public static final Form CHANGE_LOGIN_FORM = createChangeLoginForm();
	public static final Form CHANGE_PASSWORD_FORM = createChangePasswordForm();
	public static final Form GENERATE_IPAS_PASSWORD_FORM = createIPasGeneratePasswordForm();
	public static final Form CHANGE_INCOGNITO_SETTING_FORM = changeIncognitoSettingForm();
	private static final String DELIVERY_TYPE_ERROR = "В персональной информации не указан %s для доставки подтверждений. Пожалуйста, обратитесь в банк.";
	//поля для Настройка видимости шаблонов
	public static final String CHANNEL_TYPE_NAME_FIELD = "channelType";
	public static final String CHANGED_IDS_NAME_FIELD = "changedIds";
	public static final Form FORM = createForm();

	private String needOpenTab;
	private String mobilePhone;
	private String confirmName;
	private String email;
	private ConfirmableObject confirmableObject;
    private ConfirmStrategy confirmStrategy;

	//поля для просмотра Настройка видимости продуктов
	private List<AccountLink> accounts = new ArrayList<AccountLink>();
	private String[] selectedAccountIds = new String[0];
	private String[] selectedAccountMobileIds = new String[0];
	private String[] selectedAccountSocialIds = new String[0];
	private String[] selectedAccountESIds = new String[0];
	private String[] selectedAccountSMSIds = new String[0];
	private List<CardLink> cards = new ArrayList<CardLink>();
	private String[] selectedCardIds = new String[0];
	private String[] selectedCardMobileIds = new String[0];
	private String[] selectedCardSocialIds = new String[0];
	private String[] selectedCardESIds = new String[0];
	private String[] selectedCardSMSIds = new String[0];
	private List<LoanLink> loans = new ArrayList<LoanLink>();
	private String[] selectedLoanIds = new String[0];
	private String[] selectedLoanMobileIds = new String[0];
	private String[] selectedLoanSocialIds = new String[0];
	private String[] selectedLoanSMSIds = new String[0];
	private String[] selectedLoanESIds = new String[0];
	private List<DepoAccountLink> depoAccounts = new ArrayList<DepoAccountLink>();
	private String[] selectedDepoAccountIds = new String[0];
	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();
	private String[] selectedIMAccountIds = new String[0];
	private String[] selectedIMAccountMobileIds = new String[0];
	private String[] selectedIMAccountSocialIds = new String[0];
	private String[] selectedIMAccountESIds = new String[0];
	private List<SecurityAccountLink> securityAccounts = new ArrayList<SecurityAccountLink>();
	private String[] selectedSecurityAccountIds = new String[0];
	private boolean newProductsShowInSms;

	private PFRLink pfrLink;
	private boolean isPfrLinkSelected;
	private String SNILS;
	private ActivePerson activePerson;
	private Boolean modified = false;
	private String currentPage;

	private Integer[] sortedCardIds = new Integer[0];
	private Integer[] sortedAccountIds = new Integer[0];
	private Integer[] sortedLoanIds = new Integer[0];
	private Integer[] sortedIMAccountIds = new Integer[0];
	private Integer[] sortedDepoAccountIds = new Integer[0];

	private List<TemplateDocument> templates;
	private int minLoginLength;

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	private static Form createForm(String[] chooseValues)
	{
		FormBuilder formBuilder = new FormBuilder();

		// noinspection TooBroadScope
		FieldBuilder fb;
		
		fb = new FieldBuilder();
		fb.setName("password");
	    fb.setDescription("Текущий пароль");
		fb.setType("string");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("oneTimePassword");
		fb.setDescription("Одноразовый пароль");
		fb.setParser(new BooleanParser());
		fb.setType("boolean");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("confirmType");
		fb.setDescription("Предпочтительный способ подтверждения");
		fb.setType("string");
		fb.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(chooseValues), "Данный тип подтверждения не поддерживается.")
		);
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	public static Form createGeneratePasswordForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb;
		fb = new FieldBuilder();
		fb.setName("password");
		fb.setDescription("Текущий пароль");
		fb.setType("string");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	public static Form createChangeLoginForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb;

		fb = new FieldBuilder();
		fb.setName("newLogin");
		fb.setDescription("Новый логин");
		fb.setType("string");
		fb.addValidators(
				new RequiredFieldValidator("Введите поле Логин"),
				new PasswordStrategyValidator("csa_client_login"));
		fb.addValidators();
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("newLoginReplay");
		fb.setDescription("Повтор нового логина");
		fb.setType("string");
		fb.addValidators(new RequiredFieldValidator());
		fb.addValidators(new RegexpFieldValidator(".{5,30}"));
		formBuilder.addField(fb.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "newLogin");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "newLoginReplay");
		compareValidator.setMessage("Логины не совпадают");
		formBuilder.setFormValidators(compareValidator);
		
		return formBuilder.build();
	}

	public static Form createChangePasswordForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb;

		fb = new FieldBuilder();
		fb.setName("oldPassword");
		fb.setDescription("Старый пароль");
		fb.setType("string");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("newPassword");
		fb.setDescription("Новый пароль");
		fb.setType("string");
		fb.addValidators(
				new RequiredFieldValidator(),
				new PasswordStrategyValidator("csa_client_password"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("newPasswordReplay");
		fb.setDescription("Повтор нового пароля");
		fb.setType("string");
		fb.addValidators(new RequiredFieldValidator());
		fb.addValidators(new RegexpFieldValidator(".{8,30}"));
		formBuilder.addField(fb.build());

		CompareStringValidator compareValidator = new CompareStringValidator(false, false);
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, "newPassword");
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, "newPasswordReplay");
		compareValidator.setMessage("Вы неправильно указали новый пароль. Введенный пароль и его повтор должны совпадать.");
		formBuilder.addFormValidators(compareValidator);

		compareValidator = new CompareStringValidator(true, false);
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, "oldPassword");
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, "newPassword");
		compareValidator.setMessage("Новый пароль совпадает со старым");
		formBuilder.addFormValidators(compareValidator);
		return formBuilder.build();
	}

	public static Form createIPasGeneratePasswordForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb;
		fb = new FieldBuilder();
		fb.setName("oldPassword");
		fb.setDescription("Текущий пароль");
		fb.setType("string");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	public static Form changeIncognitoSettingForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb;
		fb = new FieldBuilder();
		fb.setName("incognitoSetting");
		fb.setDescription("Текущий пароль");
		fb.setType("boolean");
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	public String getConfirmName()
	{
		return confirmName;
	}

	public void setConfirmName(String confirmName)
	{
		this.confirmName = confirmName;
	}

	public Integer[] getSortedCardIds()
	{
		return sortedCardIds;
	}

	public void setSortedCardIds(Integer[] sortedCardIds)
	{
		this.sortedCardIds = sortedCardIds;
	}

	public Integer[] getSortedAccountIds()
	{
		return sortedAccountIds;
	}

	public void setSortedAccountIds(Integer[] sortedAccountIds)
	{
		this.sortedAccountIds = sortedAccountIds;
	}

	public Integer[] getSortedLoanIds()
	{
		return sortedLoanIds;
	}

	public void setSortedLoanIds(Integer[] sortedLoanIds)
	{
		this.sortedLoanIds = sortedLoanIds;
	}

	public Integer[] getSortedIMAccountIds()
	{
		return sortedIMAccountIds;
	}

	public void setSortedIMAccountIds(Integer[] sortedIMAccountIds)
	{
		this.sortedIMAccountIds = sortedIMAccountIds;
	}

	public Integer[] getSortedDepoAccountIds()
	{
		return sortedDepoAccountIds;
	}

	public void setSortedDepoAccountIds(Integer[] sortedDepoAccountIds)
	{
		this.sortedDepoAccountIds = sortedDepoAccountIds;
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

	public String[] getSelectedAccountESIds()
	{
		return selectedAccountESIds;
	}

	public void setSelectedAccountESIds(String[] selectedAccountESIds)
	{
		this.selectedAccountESIds = selectedAccountESIds;
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

	public String[] getSelectedLoanIds()
	{
		return selectedLoanIds;
	}

	public void setSelectedLoanIds(String[] selectedLoanIds)
	{
		this.selectedLoanIds = selectedLoanIds;
	}

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public List<DepoAccountLink> getDepoAccounts()
	{
		return depoAccounts;
	}

	public void setDepoAccounts(List<DepoAccountLink> depoAccounts)
	{
		this.depoAccounts = depoAccounts;
	}

	public String[] getSelectedDepoAccountIds()
	{
		return selectedDepoAccountIds;
	}

	public void setSelectedDepoAccountIds(String[] selectedDepoAccountIds)
	{
		this.selectedDepoAccountIds = selectedDepoAccountIds;
	}

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	public String[] getSelectedIMAccountIds()
	{
		return selectedIMAccountIds;
	}

	public void setSelectedIMAccountIds(String[] selectedIMAccountIds)
	{
		this.selectedIMAccountIds = selectedIMAccountIds;
	}

	public String[] getSelectedAccountMobileIds()
	{
		return selectedAccountMobileIds;
	}

	public void setSelectedAccountMobileIds(String[] selectedAccountMobileIds)
	{
		this.selectedAccountMobileIds = selectedAccountMobileIds;
	}

	public String[] getSelectedCardMobileIds()
	{
		return selectedCardMobileIds;
	}

	public void setSelectedCardMobileIds(String[] selectedCardMobileIds)
	{
		this.selectedCardMobileIds = selectedCardMobileIds;
	}

	public String[] getSelectedLoanMobileIds()
	{
		return selectedLoanMobileIds;
	}

	public void setSelectedLoanMobileIds(String[] selectedLoanMobileIds)
	{
		this.selectedLoanMobileIds = selectedLoanMobileIds;
	}

	public String[] getSelectedIMAccountMobileIds()
	{
		return selectedIMAccountMobileIds;
	}

	public void setSelectedIMAccountMobileIds(String[] selectedIMAccountMobileIds)
	{
		this.selectedIMAccountMobileIds = selectedIMAccountMobileIds;
	}

    public String[] getSelectedAccountSocialIds()
    {
        return selectedAccountSocialIds;
    }

    public void setSelectedAccountSocialIds(String[] selectedAccountSocialIds)
    {
        this.selectedAccountSocialIds = selectedAccountSocialIds;
    }

    public String[] getSelectedCardSocialIds()
    {
        return selectedCardSocialIds;
    }

    public void setSelectedCardSocialIds(String[] selectedCardSocialIds)
    {
        this.selectedCardSocialIds = selectedCardSocialIds;
    }

    public String[] getSelectedLoanSocialIds()
    {
        return selectedLoanSocialIds;
    }

    public void setSelectedLoanSocialIds(String[] selectedLoanSocialIds)
    {
        this.selectedLoanSocialIds = selectedLoanSocialIds;
    }

    public String[] getSelectedIMAccountSocialIds()
    {
        return selectedIMAccountSocialIds;
    }

    public void setSelectedIMAccountSocialIds(String[] selectedIMAccountSocialIds)
    {
        this.selectedIMAccountSocialIds = selectedIMAccountSocialIds;
    }

    public List<SecurityAccountLink> getSecurityAccounts()
	{
		return securityAccounts;
	}

	public void setSecurityAccounts(List<SecurityAccountLink> securityAccounts)
	{
		this.securityAccounts = securityAccounts;
	}

	public String[] getSelectedSecurityAccountIds()
	{
		return selectedSecurityAccountIds;
	}

	public void setSelectedSecurityAccountIds(String[] selectedSecurityAccountIds)
	{
		this.selectedSecurityAccountIds = selectedSecurityAccountIds;
	}

	public String getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(String currentPage)
	{
		this.currentPage = currentPage;
	}

	public PFRLink getPfrLink()
	{
		return pfrLink;
	}

	public void setPfrLink(PFRLink pfrLink)
	{
		this.pfrLink = pfrLink;
	}

	public boolean getPfrLinkSelected()
	{
		return isPfrLinkSelected;
	}

	public void setPfrLinkSelected(boolean isPfrLinkSelected)
	{
		this.isPfrLinkSelected = isPfrLinkSelected;
	}

	public String getSNILS()
	{
		return SNILS;
	}

	public void setSNILS(String SNILS)
	{
		this.SNILS = SNILS;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public String[] getSelectedCardESIds()
	{
		return selectedCardESIds;
	}

	public void setSelectedCardESIds(String[] selectedCardESIds)
	{
		this.selectedCardESIds = selectedCardESIds;
	}

	public String[] getSelectedLoanESIds()
	{
		return selectedLoanESIds;
	}

	public void setSelectedLoanESIds(String[] selectedLoanESIds)
	{
		this.selectedLoanESIds = selectedLoanESIds;
	}

	public String[] getSelectedIMAccountESIds()
	{
		return selectedIMAccountESIds;
	}

	public void setSelectedIMAccountESIds(String[] selectedIMAccountESIds)
	{
		this.selectedIMAccountESIds = selectedIMAccountESIds;
	}

	public String[] getSelectedAccountSMSIds()
	{
		return selectedAccountSMSIds;
	}

	public void setSelectedAccountSMSIds(String[] selectedAccountSMSIds)
	{
		this.selectedAccountSMSIds = selectedAccountSMSIds;
	}

	public String[] getSelectedLoanSMSIds()
	{
		return selectedLoanSMSIds;
	}

	public void setSelectedLoanSMSIds(String[] selectedLoanSMSIds)
	{
		this.selectedLoanSMSIds = selectedLoanSMSIds;
	}

	public String[] getSelectedCardSMSIds()
	{
		return selectedCardSMSIds;
	}

	public void setSelectedCardSMSIds(String[] selectedCardSMSIds)
	{
		this.selectedCardSMSIds = selectedCardSMSIds;
	}

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	public void setMinLoginLength(int minLoginLength)
	{
		this.minLoginLength = minLoginLength;
	}

	public int getMinLoginLength()
	{
		return minLoginLength;
	}

	public String getNeedOpenTab()
	{
		return needOpenTab;
	}

	public void setNeedOpenTab(String needOpenTab)
	{
		this.needOpenTab = needOpenTab;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANNEL_TYPE_NAME_FIELD);
		fieldBuilder.setDescription("Тип канала");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("mobile|atm|sms|internet|social"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANGED_IDS_NAME_FIELD);
		fieldBuilder.setDescription("Список идентификаторов изменненых шаблонов");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public boolean isNewProductsShowInSms()
	{
		return newProductsShowInSms;
	}

	public void setNewProductsShowInSms(boolean newProductsShowInSms)
	{
		this.newProductsShowInSms = newProductsShowInSms;
	}
}
