package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

/**
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class RequestConstants
{
	public static final String LOGIN_OPERATION = "loginblock";
	public static final String LOGOFF_OPERATION = "LOGOFF";
	public static final String PRODUCT_LIST_OPERATION = "PRODUCT_LIST";
	public static final String CARD_DETAIL_OPERATION = "CARD_DETAIL";
	public static final String CARD_ABSTRACT_OPERATION = "CARD_ABSTRACT";
	public static final String ACCOUNT_DETAIL_OPERATION = "ACCOUNT_DETAIL";
	public static final String ACCOUNT_ABSTRACT_OPERATION = "ACCOUNT_ABSTRACT";
	public static final String CHECK_SESSION_OPERATION = "checksession";

	public static final String MESSAGE_TAG = "message";
	public static final String AUTH_TOKEN_TAG = "authToken";
	public static final String IS_AUTHENTICATION_COMPLETED_TAG = "isAuthenticationCompleted";
	public static final String IP_TAG = "ip";
	public static final String OPERATION_TAG = "operation";
	public static final String STATUS_TAG = "status";
	public static final String STATUS_CODE_TAG = "code";
	public static final String STATUS_DESCRIPTION_TAG = "description";
	public static final String PRODUCT_TYPE_TAG = "productType";
	public static final String ID_TAG = "id";
	public static final String FROM_TAG = "from";
	public static final String TO_TAG = "to";
	public static final String COUNT_TAG = "count";
	public static final String NAME_TAG = "name";

	/* Наполнение визуального интерфейса */
	// Операции
	public static final String MENU_OPERATION                = "menucontainer.content";
	public static final String MOBILE_BANK_HIDE              = "menucontainer.content.mobilebank.hide";
	public static final String AUTOPAYMENTS_OPERATION        = "menucontainer.content.autopayments";
	public static final String FAST_PAYMENT_PANEL_OPERATION  = "panelfastpayment";
	// Дополнительные параметры запроса
	public static final String MENU_EXCLUDE_ELEMENT_TAG      = "exclude";
	public static final String MENU_EXCLUDE_ELEMENT_ROOT_TAG = "excludedVisualElements";

	/* АЛФ */
	// Операции
	public static final String ADD_ALF_OPERATION             = "alf.operations.add";
	public static final String ALF_HISTORY_OPERATION         = "alf.operations.list.get";
	public static final String ALF_SERVICESTATUS_STATUS      = "alf.servicestatus.do";
	public static final String ALF_CONNECT                   = "alf.serviceconnect.do";
	public static final String ALF_CATEGORY_LIST             = "alf.category.list.do";
	public static final String ALF_EDIT_OPERATION            = "alf.operations.edit";
	public static final String ALF_EDIT_GROUP_OPERATION      = "alf.operations.edit.group";
	public static final String ALF_GET_GRAPHIC_DATA          = "alf.graphics.do";
	public static final String ALF_CATEGORY_EDIT             = "alf.category.edit";
	public static final String ALF_CATEGORY_FILTER           = "alf.category.filter";
	public static final String ALF_CATEGORY_BUDGETSET        = "alf.category.budgetset";
	public static final String ALF_CATEGORY_BUDGET_REMOVE    = "alf.category.budget.remove";
	public static final String ALF_FINANCECONTENT            = "alf.financecontent";
	// Дополнительные параметры запроса
	public static final String INCOME_TYPE_TAG               = "incomeType";
	public static final String PAGINATION_SIZE_TAG           = "paginationSize";
	public static final String PAGINATION_OFFSET_TAG         = "paginationOffset";
	public static final String OPERATION_TYPE_TAG            = "operationType";
}
