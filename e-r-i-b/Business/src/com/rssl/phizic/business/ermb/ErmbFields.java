package com.rssl.phizic.business.ermb;

/**
 * User: Moshenko
 * Date: 11.10.13
 * Time: 14:32
 * Констарны по ЕМРБ.
 */
public enum  ErmbFields
{
    //Названия полей профиля ЕРМБ
    SUPPRESS_ADVERTISING("Признак запрета рекламных рассылок."),
    CLIENT_PHONES("Телефоны клиента."),
    INTERNET_CLIENT_SERVICE("Продукты, доступные в интернет-приложении."),
    MOBILE_CLIENT_SERVICE("Продукты, доступные в мобильном приложении."),
    ATM_CLIENT_SERVICE("Продукты, доступные в устройствах самообслуживания."),
    SMS_CLIENT_SERVICE("Продукты, доступные в СМС-канале."),
    INFORM_RESOURCES("Продукты клиента, на которые должны отправляться оповещения."),
    SERVICE_STATUS("Состояние услуги"),
    CLIENT_BLOCKED("Клиентская блокировака"),
    PAYMENT_BLOCKED("Блокировака по не оплате"),
    START_SERVICE_DATE("Дата первого подключения услуги."),
    END_SERVICE_DATE("Дата окончания предоставления услуги."),
    TARIFF("Тарифный план услуги."),
    QUICK_SERVICES("Признак включения для клиента возможности оплаты чужого телефона и переводов по номеру телефона."),
    ACTIVE_PHONE("Телефон клиента, на который должны отправляться оповещения."),
    CHARHE_CARD("Номер приоритетной карты для списания абонентской платы."),
    NEW_PRODUCT_NOTIFICATION("Флажок «отправлять уведомления по вновь добавленному продукту»/."),
	NEW_PRODUCT_SHOW_IN_SMS("Флажок «видимость продукта в смс-канале»"),
    INFORM_PERION("Временные интервалы, в которые разрешено отправлять уведомления."),
    INFORM_PERION_BEGIN("Время начала периода уведомления"),
    INFORM_PERION_END("Время окончания периода уведомления"),
    INFORM_PERION_DAY("День недели периода уведомления"),
    INFORM_PERION_TIME_ZONE("Часовая зона периода");

    private String value;

    ErmbFields (String value)
    {
        this.value = value;
    }

    public String toValue() { return value; }

}
