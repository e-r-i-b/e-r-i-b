package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.StatelessSession;

/**
 * @author Omeliyanchuk
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Инкапсулирует работы с БД, исопльзует сессию без состояния- напрямую запросы к бд.
 */
public interface HibernateActionStateless<R>
{
    /**
     * Метод вызвываемый HibernateExecutor для выполнения действия
     * @param session Примечание для реализаторов : НИКОГДА НЕ ЗАКРЫВАЙТЕ ПЕРЕДАННУЮ СЕССИЮ И НЕ НАЧИНАЙТЕ ТРАНЗАКЦИЙ!!!
     */
    R run(StatelessSession session) throws Exception;
}
