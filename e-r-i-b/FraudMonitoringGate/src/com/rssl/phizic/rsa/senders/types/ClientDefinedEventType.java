package com.rssl.phizic.rsa.senders.types;

/**
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum ClientDefinedEventType
{
	INTERNAL_PAYMENT("INTERNALPAYMENT",                                 "Перевод между своими счетами и картами"),
	RUR_PAYMENT("RURPAYMENT",                                           "Перевод частному лицу"),
	RUR_PAY_JUR_SB("RURPAYJURSB",                                       "Оплата услуг"),
	EXTERNAL_PROVIDER_PAYMENT("EXTERNALPROVIDERPAYMENT",                "Оплата с внешней ссылки"),
	CREATE_AUTO_PAYMENT("CREATEAUTOPAYMENTPAYMENT",                     "Создание автоплатежа (регулярной операции)"),
	EDIT_AUTO_PAYMENT("EDITAUTOPAYMENTPAYMENT",                         "Редактирование автоплатежа (регулярной операции)"),
	LOAN_PAYMENT("LOANPAYMENT",                                         "Погашение кредита"),
	CLOSE_ACCOUNT("CLOSE_ACCOUNT",                                      "Закрытие счета"),
	CHANGE_PASSWORD("CHANGE",                                           "Изменение пароля клиентом"),
	RECOVER_PASSWORD("RECOVER",                                         "Восстановление пароля клиентом"),
	RECOVER_BY_CALL("RECOVER_BY_CALL",                                  "Восстановление пароля клиентом через контакт центр"),
	ISSUE_CARD("CARD",                                                  "Обычная карта"),
	ISSUE_VIRTUAL_CARD("VIRTUAL_PREPAID_CARD",                          "Виртуальная карта"),
	REISSUE_CARD("CARD",                                                "Обычная карта"),
	REISSUE_VIRTUAL_CARD("VIRTUAL_PREPAID_CARD",                        "Виртуальная карта"),
	WITH_OTP("WITH_OTP",                                                "Вход в систему с одноразовым паролем"),
	WITHOUT_OTP("WITHOUT_OTP",                                          "Вход в систему без одноразового пароля"),
	UNIVERSAL_AGREEMENT("UNIVERSAL_AGREEMENT",                          "Подключение УДБО"),
	NEW_MOB_APPL("NEW_MOB_APPL",                                        "Клиент регистрирует новое мобильное приложение");


	private String type;
	private String description;

	private ClientDefinedEventType(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	/**
	 * @return тип документа
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}
}

