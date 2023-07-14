package com.rssl.phizic.common.types.csa;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Тип идентификации пльзователя
 */
public enum IdentificationType
{
	cardNumber("карта"),                //по номеру карты
	login("логин"),                     //по логину
	phoneNumber("телефон"),             //по номеру телефона
	sessionId("идентификатор сессии"),  //по идентифиатору сессии
	authToken("токен"),                 //по AuthToken
	OUID("guid операции"),              //по идентификатору операции
	UID("уникальный идентификатор"),    //по уникальному идентификатору ФИО+ДУЛ+ДР(передается в теле запроса).
	connector("коннектор"),             //по идентификатору коннектора
	guestLogin("гостевой логин");       //по гостевому логину


	private String description;

	IdentificationType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
