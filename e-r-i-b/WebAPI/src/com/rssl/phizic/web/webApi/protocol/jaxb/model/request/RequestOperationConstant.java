package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Список поддерживаемых операций
 *
 * @author Balovtsev
 * @since 24.04.14
 */
@XmlEnum(String.class)
public enum RequestOperationConstant
{
	@XmlEnumValue("menucontainer.content")
	MENU_OPERATION,

	@XmlEnumValue("menucontainer.content.autopayments")
	AUTOPAYMENTS_OPERATION,

	@XmlEnumValue("menucontainer.content.mobilebank.hide")
	MOBILE_BANK_HIDE,

	@XmlEnumValue("panelfastpayment")
	FAST_PAYMENT_PANEL_OPERATION,

	@XmlEnumValue("loginblock")
	LOGIN_OPERATION,

	@XmlEnumValue("PRODUCT_LIST")
	PRODUCT_LIST,

	@XmlEnumValue("CARD_DETAIL")
	CARD_DETAIL,

	@XmlEnumValue("ACCOUNT_DETAIL")
	ACCOUNT_DETAIL,

	@XmlEnumValue("CARD_ABSTRACT")
	CARD_ABSTRACT,

	@XmlEnumValue("ACCOUNT_ABSTRACT")
	ACCOUNT_ABSTRACT,

	@XmlEnumValue("checksession")
	CHECK_SESSION,

	@XmlEnumValue("LOGOFF")
	LOGOFF,

	@XmlEnumValue("alf.operations.list.get")
	ALF_HISTORY_OPERATION,

	@XmlEnumValue("alf.servicestatus.do")
	ALF_SERVICE_STATUS,

	@XmlEnumValue("alf.serviceconnect.do")
	ALF_CONNECT,

	@XmlEnumValue("alf.category.list.do")
	ALF_CATEGORY_LIST,

	@XmlEnumValue("alf.operations.add")
	ADD_ALF_OPERATION,

	@XmlEnumValue("alf.operations.edit")
	ALF_EDIT_OPERATION,

	@XmlEnumValue("alf.graphics.do")
	ALF_GRAPHIC_DATA_REQUEST_OPERATION,

	@XmlEnumValue("alf.category.edit")
	ALF_CATEGORY_EDIT,

	@XmlEnumValue("alf.category.filter")
	ALF_CATEGORY_FILTER,

	@XmlEnumValue("alf.category.budgetset")
	ALF_CATEGORY_BUDGETSET,

	@XmlEnumValue("alf.category.budget.remove")
	ALF_CATEGORY_BUDGET_REMOVE,

	@XmlEnumValue("alf.financecontent")
	ALF_FINANCECONTENT,

	@XmlEnumValue("alf.operations.edit.group")
	ALF_EDIT_GROUP_OPERATION;
}
