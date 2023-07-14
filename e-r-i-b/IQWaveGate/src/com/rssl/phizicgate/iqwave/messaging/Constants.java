package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.common.forms.PaymentFieldKeys;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String OUTGOING_MESSAGES_CONFIG = "com/rssl/phizicgate/iqwave/messaging/iqwave-messaging-config.cfg.xml";
	public static final String INCOMING_MESSAGES_CONFIG = "com/rssl/phizicgate/iqwave/messaging/iqwave-listener-messaging-config.cfg.xml";

	public static final String ABONENT_SBOL = "RSTYLE";
	public static final String NO_ERROR = "0";
	public static final String CARDS_TRANSFER_REQUEST =  "CardToCardRequest";
	public static final String SIMPLE_PAYMENT_REQUEST = "SimplePaymentRequest";
	public static final String SIMPLE_PAYMENT_ECOMMERCE_REQUEST = "SimplePaymentEcommerceRequest";
	public static final String PAYMENT_DEBTS_FNS_REQUEST = "PaymentDebtsFnsRequest";
	public static final String PAYMENT_FNS_REQUEST ="PaymentFnsRequest";
	public static final String PAYMENT_DEBTS_MGTS_REQUEST ="PaymentDebtsMgtsRequest";
	public static final String PAYMENT_MGTS_REQUEST = "PaymentMgtsRequest";
	public static final String PAYMENT_DEBTS_GKH_REQUEST = "PaymentDebtsGkhRequest";
	public static final String PAYMENT_GKH_REQUEST = "PaymentGkhRequest";
	public static final String PAYMENT_MOSENERGO_REQUEST = "PaymentMosenergoRequest";
	public static final String PAYMENT_ROSTELECOM_REQUEST = "PaymentRostelecomRequest";
	public static final String PAYMENT_DEBTS_ROSTELECOM_REQUEST = "PaymentDebtsRostelecomRequest";

	//loyalty
	public static final String LOYALTY_GET_BALANCE_REQUEST = "LoyaltyGetBalanceRequest";
	public static final String LOYALTY_REGISTER_REQUEST = "LoyaltyRegisterRequest";
	public static final String LOYALTY_GET_STATEMENT_REPORT_REQUEST = "LoyaltyGetStatementReportRequest";
	public static final String LOYALTY_GET_OFFERS_REQUEST = "LoyaltyGetOffersRequest";

	public static final String AUTO_PAY_LIST_REQUEST = "AutoPayListRequest";
	public static final String AUTO_PAY_REPORT_REQUEST = "AutoPayReportRequest";
	public static final String AUTO_PAY_STATE_REQUEST = "AutoPayStateRequest";

	public static final String FNS_FREE_PAYMENT_REQUEST = "FreePaymentRequest";

	public static final String OPER_STATUS_REQUEST = "OperStatusRequest";

	public static final String CARD_TO_CARD_INFO_REQUEST = "CardToCardInfoRequest";

	//������ ������ ������
	public static final String REVERSAL_SIMPLE_PAYMENT_REQUEST = "ReversalSimplePaymentEcommerceRequest";
	//������ ������� ������
	public static final String REFUND_SIMPLE_PAYMENT_REQUEST = "RefundSimplePaymentEcommerceRequest";

	public static final String PAYMENT_CHECK_RES_REQUEST = "PaymentCheckRESRequest"; // ������ ���������� � ����� ���������
	public static final String PAYMENT_RES_REQUEST = "PaymentRESRequest"; // ������ �� ���������� ������� ������ ����� ���������
	public static final String PAYMENT_RES_RESPONSE = "PaymentRESResponse"; // ����� ������������ ������� ������ ����� ���������

	public static final String SIMPLE_PAYMENT_RESPONSE = "SimplePaymentResponse";
	public static final String SIMPLE_PAYMENT_ECOMMERCE_RESPONSE = "SimplePaymentEcommerceResponse";
	public static final String PAYMENT_MOSENERGO_RESPONSE = "PaymentMosenergoResponse";
	public static final String PAYMENT_GKH_RESPONSE = "PaymentGkhResponse";
	public static final String PAYMENT_MGTS_RESPONSE = "PaymentMgtsResponse";
	public static final String PAYMENT_ROSTELECOM_RESPONSE = "PaymentRostelecomResponse";
	public static final String PAYMENT_FNS_RESPONSE = "PaymentFnsResponse";
	public static final String OFFLINE_TICKET = "OfflineTicket";
	public static final String FNS_FREE_PAYMENT_RESPONSE = "FreePaymentResponse";

	//����� ������ ������
	public static final String REVERSAL_SIMPLE_PAYMENT_RESPONSE = "ReversalSimplePaymentEcommerceResponse";
	//����� ������� ������
	public static final String REFUND_SIMPLE_PAYMENT_RESPONSE = "RefundSimplePaymentEcommerceResponse";

	// ������� � ������������
	public static final String AEROEXPRESS_SCHEDULE_REQUEST = "AeroexpressScheduleRequest";
	public static final String AEROEXPRESS_SCHEDULE_RESPONSE = "AeroexpressScheduleResponse";
	public static final String AEROEXPRESS_PRICELIST_REQUEST = "AeroexpressPricelistRequest";
	public static final String AEROEXPRESS_PRICELIST_RESPONSE = "AeroexpressPricelistResponse";
	public static final String AEROEXPRESS_FREE_SEATS_GLOBAL_REQUEST  = "AeroexpressFreeSeatsGlobalRequest";
	public static final String AEROEXPRESS_FREE_SEATS_GLOBAL_RESPONSE = "AeroexpressFreeSeatsGlobalResponse";
	public static final String AEROEXPRESS_FREE_SEATS_DETAIL_REQUEST = "AeroexpressFreeSeatsDetailRequest";
	public static final String AEROEXPRESS_FREE_SEATS_DETAIL_RESPONSE = "AeroexpressFreeSeatsDetailResponse";
	public static final String AEROEXPRESS_GET_TICKETS_REQUEST = "AeroexpressGetTicketRequest";
	public static final String AEROEXPRESS_GET_TICKETS_RESPONSE = "AeroexpressGetTicketResponse";
	public static final String AEROEXPRESS_PAY_TICKETS_REQUEST = "AeroexpressPayTicketRequest";
	public static final String AEROEXPRESS_PAY_TICKETS_RESPONSE = "AeroexpressPayTicketResponse";

	//loyalty
	public static final String LOYALTY_GET_BALANCE_RESPONSE = "LoyaltyGetBalanceResponse";
	public static final String LOYALTY_REGISTER_RESPONSE = "LoyaltyRegisterResponse";
	public static final String LOYALTY_GET_STATEMENT_REPORT_RESPONSE = "LoyaltyGetStatementReportResponse";
	public static final String LOYALTY_GET_OFFERS_RESPONSE = "LoyaltyGetOffersResponse";

	public static final String REC_IDENTIFIER_FIELD_NAME = "RecIdentifier";//������������ �����������
	public static final String FLAT_NUMBER_FIELD_NAME ="FlatNumber";//����� ��������
	public static final String PERIOD_FIELD_NAME ="Period";//������ ������
	public static final String AUTHORIZE_CODE_FIELD_NAME ="AuthorizeCode";//������ ������
	public static final String COMISSION_FIELD_NAME ="Comission";//������ ������
	public static final String OPERATIOIN_IDENTIFIER = "OperationIdentifier";// ������������� �������� SVFE
	public static final String R192025125 = PaymentFieldKeys.SUBSERVICE_CODE;//����������� ������� ����, ��� �������� ���� ���������
	public static final String REC_IDENTIFIER_SUFFIX_FIELD_NAME = "RecIdentifierPostfix";//����� �������������� �����������, ��������, ��������� ������ ��������� ("/NIC-D") ��� �������� 
	public static final String LAST_INDICATION_FIELD_NAME = "LastIndication";//���������� ��������� ��������
	public static final String CURRENT_INDICATION_FIELD_NAME = "CurrentIndication";//������� ��������� ��������
	public static final String TARIFF_NUMBER_FIELD_NAME = "TariffNumber";//����� ������
	public static final String TARIFF_VAR_FIELD_NAME = "TariffVar";//������� ������
	public static final String TARIFF_ZONE_FIELD_NAME = "TariffZone";//���� �����
	public static final String AMOUNT_FIELD_NAME = "amount";//����� �������
	public static final String ID_FROM_PAYMENT_SYSTEM_FIELD_NAME = "PaymentSystemId";//������������� ������� �� ������� �������
	public static final String DEBT_FIELD_NAME = "DebtsAccNumber";//�������������
	public static final String DEBT_ROW_FIELD_NAME = "DebtsCaseNumber";//�������� ������
	public static final String AGREEMENT_FIELD_NAME = "Agreement"; // ����������
	public static final String MB_OPER_CODE_FIELD_NAME = "MBOperCode";//��� ���������� �����

	public static final String TARIFF_VAR_ONETARIFF                 = "������������";
	public static final String TARIFF_VAR_TWOTARIFF                 = "������������";
	public static final String TARIFF_VAR_MANYTARIFF                = "�������������";

	public static final String TARIFF_ZONA_DAY                      = "����";
	public static final String TARIFF_ZONA_NIGHT                    = "����";
	public static final String TARIFF_ZONA_PEAK                     = "���";
	public static final String TARIFF_ZONA_HALFPEAK                 = "�������";

	public static final String HEAD_TAG          = "Head";
	public static final String BODY_TAG          = "Body";
	public static final String MESSAGE_UID_TAG   = "MessUID";
	public static final String MESSAGE_ID_TAG    = "MessageId";
	public static final String MESSAGE_DATE_TAG  = "MessageDate";
	public static final String FROM_ABONENT_TAG  = "FromAbonent";
	public static final String MESSAGE_TYPE_TAG  = "MessType";
	public static final String ERROR_TAG         = "Error";
	public static final String ERROR_CODE_TAG    = "ErrCode";
	public static final String ERROR_MESSAGE_TAG = "ErrMes";
	public static final String PARENT_ID_TAG     = "parentId";
	public static final String ROUTE_TAG_NAME = "Route";
	public static final String VERSION_TAG = "Version";

	public static final String REC_CHEQUE_TAG = "RecCheque";
	public static final String REC_ACC_TAG = "RecAcc";
	public static final String REC_CORR_ACC_TAG ="RecCorrAcc";
	public static final String REC_BIC_TAG ="RecBic";
	public static final String REC_COMP_NAME_TAG = "RecCompName";
	public static final String REC_INN_TAG ="RecInn";

	public static final String AUTO_PAY_ITEM_TEG =             "AutoPayItem";
	public static final String AUTO_PAY_CARD_NO_TEG =          "AutoPayCardNo";
	public static final String AUTO_PAY_TEL_NO_TEG =           "AutoPayTelNo";
	public static final String AUTO_PAY_PROVIDER_ID_TEG =      "AutoPayProviderID";
	public static final String AUTO_PAY_TYPE_TEG =             "AutoPayType";
	public static final String AUTO_PAY_AMOUNT_TEG =           "AutoPayAmount";
	public static final String AUTO_PAY_FLOOR_LIMIT_TEG =      "AutoPayFloorLimit";
	public static final String AUTO_PAY_MAX_SUM_TEG =          "AutoPayMaxSum";
	public static final String AUTO_PAY_CYCLE_DAY_TEG =        "AutoPayCycleDay";
	public static final String AUTO_PAY_CYCLE_DATE_TEG =       "AutoPayCycleDate";
	public static final String AUTO_PAY_CYCLE_PERIOD_TEG =     "AutoPayCyclePeriod";
	public static final String AUTO_PAY_FRIENDLY_NAME_TEG =    "AutoPayFriendlyName";
	public static final String AUTO_PAY_REPORT_STATUS_TEG =    "AutoPayReportStatus";
	public static final String AUTO_PAY_CREATE_DATE_TEG =      "AutoPayCreateDate";
	public static final String AUTO_PAY_REPORT_PAYMENT_TYPE =  "AutoPayReportPaymentType";
	public static final String AUTO_PAYMENTS_TAG =             "AutoPayments";
	public static final String AUTO_PAY_TOTAL_AMOUNT_TAG =     "AutoPayLimitAmount";
	public static final String AUTO_PAY_TOTAL_PERIOD_TAG =     "AutoPayLimitPeriod";

	public static final String AUTO_PAY_REGISTER_REQUEST = "AutoPayRegisterRequest"; // ������ �� �������� �����������
	public static final String AUTO_PAY_REGISTER_RESPONSE = "AutoPayRegisterResponse"; // ����� �� ����������  �������� �����������
	public static final String AUTO_PAY_CORRECTION_REQUEST = "AutoPayCorrectionRequest"; // ������ �� �������������� �����������
	public static final String AUTO_PAY_CORRECTION_RESPONSE = "AutoPayCorrectionResponse"; // ����� �� ����������  �������������� �����������
	public static final String AUTO_PAY_CANCEL_REQUEST = "AutoPayCancelRequest"; // ������ �� ������ �����������
	public static final String AUTO_PAY_CANCEL_RESPONSE = "AutoPayCancelResponse"; // ���� �� ���������� ������ �����������
	public static final String AUTO_PAY_GET_CLIENT_TYPE_REQUEST = "AutoPayGetClientTypeRequest"; // ������ �� ��������� ��������� ����� ������������

	public static final String PAYMENT_DATE_TAG = "PaymentDate";
	public static final String PAYMENT_AMOUNT_TAG = "PaymentAmount";
	public static final String PAYMENT_STATUS_TAG = "PaymentStatus";
	public static final String DIG_CODE_TEG = "DigCode";
	public static final String SUMMA_TEG =      "Summa";
	public static final String CURR_ISO_TEG =   "CurrIso";

	public static final String DELIMITER = "/";
	public static final String EMPTY_VALUE = "";

	// ��� ������� � ������������� �����������
	public static final String PARAMETERS_TAG = "Parameters";
	public static final String PARAMETER_TAG = "Parameter";
	public static final String SHORT_NAME_TAG = "Shortname";
	public static final String FULL_NAME_TAG = "Fullname";
	public static final String TYPE_TAG = "Type";
	public static final String I_VALUE_TAG = "Ivalue";
	public static final String D_VALUE_TAG = "Dvalue";
	public static final String C_VALUE_TAG = "Cvalue";
	public static final String VALUE_TAG = "Value";

	public static final String RECEIVER_NUMBER_SHORT = "recipcode";
	public static final String RECEIVER_BANK_SHORT = "recipbank";

	// ���� ��� ���������
	public static final String RESERVATION_ID = "ReservationID";
	public static final String CARD_PRODUCT_TYPE = "CardProductType";
	public static final String RESERV_EXPIRATION = "ReservExpiration";
	public static final String PASSENGER_NUMBER = "PassengerNumber";
	public static final String PASSENGER_NUMBERS = "PassengerNumbers";
	public static final String ROUTES_NUMBER = "RoutesNumber";
	public static final String ROUTES_NUMBERS = "RoutesNumbers";
	public static final String PASSENGER_LIST = "PassengersList";
	public static final String PASSENGER_FIRST_NAME = "PassengerFirstName";
	public static final String PASSENGER_LAST_NAME = "PassengerLastName";
	public static final String PASSENGER_FULL_NAME = "Passenger";
	public static final String PASSENGER_TYPE = "PassengerType";
	public static final String ROUTES_LIST = "RoutesList";
	public static final String ROUTES_DEP_FLIGHT = "RoutesDepFlight";
	public static final String ROUTES_DEP_DATETIME = "RoutesDepDateTime";
	public static final String ROUTES_DEP_AIRPORT = "RoutesDepAirport";
	public static final String ROUTES_DEP_CITY = "RoutesDepCity";
	public static final String ROUTES_ARRIVAL_CITY = "RoutesArrivalCity";
	public static final String ROUTES_ARRIVAL_DATETIME = "RoutesArrivalDateTime";
	public static final String ROUTES_ARRIVAL_AIRPORT = "RoutesArrivalAirport";
	public static final String RESERV_SUMMA = "ReservSumma";
	public static final String USER_MESSAGE = "UserMessage";
	public static final String RESEVRV_FULL_NUMBER = "ReservFullNumber";
	public static final String RESV_FULL_NUMBER = "ResvFullNumber";
	public static final String CURR_CODE = "CurrCode";
	public static final String RESERV_CURR_CODE = "ReservCurrCode";
	public static final String TICKET_NUMBER = "TicketNumber";
	public static final String TICKETS_NUMBERS = "TicketsNumbers";
	public static final String TICKETS_STATUS = "TicketsStatus";
	public static final String TICKETS_LIST = "TicketsList";
	public static final String ITINERARY_URL = "ItineraryUrl";

	public static final String ROUTES_INFO_GROUP_NAME = "RouteInfo";
	public static final String INFO_BOOKING_GROUP_NAME = "InfoBooking";
	public static final String PASSENGERS_GROUP_NAME = "Passengers";
	public static final String TICKETS_GROUP_NAME = "TicketsInfo";
	public static final String ITINERARY_URL_GROUP_NAME = "ItineraryUrlInfo";
	public static final String AIRLINE_IDENTIFY_FIELD = "AirLineIdentifyField";

	public static final String CREDIT_CARD_TAG_NAME = "CreditCard";
	public static final String CARD_NUMBER_TAG_NAME = "CardNumber";
	public static final String END_DATE_TAG_NAME = "EndDate";

	public static final String CODE_TAG_NAME = "Code";
	public static final String DMESSAGE_TAG_NAME = "DMessage";
	public static final String CMESSAGE_TAG_NAME = "CMessage";
	public static final String OMESSAGE_TAG_NAME = "OMessage";
	public static final String EMESSAGE_TAG_NAME = "EMessage";


	public static final String LOY_BNS_TAG_NAME = "LoyBNS";
	public static final String LOY_TEL_TAG_NAME = "LoyTel";
	public static final String LOY_EMAIL_TAG_NAME = "LoyEMail";
	public static final String LOY_STATUS_TAG_NAME = "LoyStatus";
	public static final String LOY_TRANSACTIONS_TAG_NAME = "LoyTransactions";
	public static final String LOY_TRANSACTION_TAG_NAME = "LoyTransactions";
	public static final String DATETIME_TAG_NAME = "DateTime";
	public static final String OPERATION_KIND_TAG_NAME = "OperationKind";
	public static final String BNS_TAG_NAME = "BNS";
	public static final String CASH_TAG_NAME = "CASH";
	public static final String PARTNER_TAG_NAME = "PartnerName";
	public static final String LOY_OFFERS_TAG_NAME = "LoyOffers";
	public static final String ACCEPT_TAG_NAME = "Accept";
	public static final String HASH_TAG_NAME = "Hash";
	public static final String PAN_TAG_NAME = "PAN";
	public static final String LOY_KIND_1 = "������ �������";
	public static final String LOY_KIND_2 = "���������� ������";
	public static final String LOY_KIND_3 = "������� �� ������";
	public static final String LOY_KIND_4 = "������� �� ����������";

}
