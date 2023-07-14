package com.rssl.phizic.business.operations;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.dataaccess.query.Query;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 21.10.2005
 * Time: 16:47:09
 */
public interface Operation<R extends Restriction> extends Serializable
{
    //void initialize() throws BusinessException, BusinessLogicException;

    /**
     * —оздать новый запрос <br>
     * <em>ѕример:</em>
     * <br>
     * «апрос -
     * <pre>select cat from test.Cat as cat where cat.color = :color and cat.sex = :extra_sex</pre>
     * <br>
     * √енерируемый запрос -
     * <pre>
     * SELECT {cat.*}
	 * FROM CATS cat
     * WHERE cat.NAME = 'TOM'
     * <#if color?has_content>
	 *	  AND cat.COLOR = :color
     * </#if>
	 * <#if sex?has_content>
	 * 	  AND cat.SEX = :extra_sex
	 * </#if>
     * </pre>
     * <em>¬ызов -</em>
     * <pre>someOperation
     *  .createQuery("query_name")    - создать запрос
     *  .setParameter("sex", "male")  - установить значение доп. параметра
     *  .executeList();               - получить список</pre>
     *
     * ѕараметры :color и <#if color ... > заполнютс€ значением метода someOperation.getColor()
     * getColor() должен быть помечен аннотацией QueryParameter
     * ѕараметры :extra_sex и <#if sex ... > заполн€ютс€ значени€ми доп. параметра

     * @param name им€ запроса, должны быть описаны в hbm файле,
     * и иметь им€ "operation_class_name.query_name" где query_name есть этот параметр
     * @return запрос
     */
    Query createQuery(String name);

	/**
	 * ѕолучить ограничение операции.
	 */
	R getRestriction();

	/**
	 * ”становить ограничение дл€ операции.
	 * @param restriction
	 */
	void setRestriction(R restriction);

	/**
	 * ѕолучить стратегию подтверждени€ операции.
	 */
	/**
	 * ѕолучить стратегию подтверждени€ операции.
	 * @return специфична€ стратеги€ операции, если задана дл€ операции либо null.
	 * ѕо сути возвращает фильтр стратегии подтверждени€.
	 */
	ConfirmStrategySource getStrategy();

	/**
	 * ”становить стратегию дл€ операции.
	 * @param strategy - стратеги€ подтверждени€ю.
	 */
	void setStrategy(ConfirmStrategySource strategy);

	MessageCollector getMessageCollector();


	StateMachineEvent getStateMachineEvent();
}
