package com.rssl.phizic.web.common.loanclaim;

/**
 * Описывает тип асинхронного запроса для получения данных живого поиска адресной информации пользователя
 *
 * @author Balovtsev
 * @since 27.05.2014
 */
public enum ClientAddressRequestType
{
    /**
     * Район/округ
     */
    AREA,

    /**
     * Город
     */
    CITY,

    /**
     * Населенный пункт
     */
    LOCALITY,

    /**
     * Улица
     */
    STREET,

    /**
     * Неизвестно что хотят получить
     */
    UNKNOWN;

    /**
     * Возвращает тип запроса. Если нет соответствия возвращает UNKNOWN
     *
     * @param value строковое представление типа запроса из формы
     * @return ClientAddressRequestType
     */
    public static ClientAddressRequestType getTypeByValue(final String value)
    {
        for (ClientAddressRequestType type : ClientAddressRequestType.values())
        {
            if (type.name().equals(value))
            {
                return type;
            }
        }

        return UNKNOWN;
    }
}
