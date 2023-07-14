package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.Session;

/**
 * Действие инкапсулирует работу с данными.
 */
public  interface HibernateAction<R>
{
    /**
     * Метод вызвываемый HibernateExecutor для выполнения действия
     * @param session Примечание для реализаторов : НИКОГДА НЕ ЗАКРЫВАЙТЕ ПЕРЕДАННУЮ СЕССИЮ И НЕ НАЧИНАЙТЕ ТРАНЗАКЦИЙ!!!
     */
    R run(Session session) throws Exception;
}
