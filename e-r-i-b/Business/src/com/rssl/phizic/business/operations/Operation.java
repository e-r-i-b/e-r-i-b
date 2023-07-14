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
     * ������� ����� ������ <br>
     * <em>������:</em>
     * <br>
     * ������ -
     * <pre>select cat from test.Cat as cat where cat.color = :color and cat.sex = :extra_sex</pre>
     * <br>
     * ������������ ������ -
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
     * <em>����� -</em>
     * <pre>someOperation
     *  .createQuery("query_name")    - ������� ������
     *  .setParameter("sex", "male")  - ���������� �������� ���. ���������
     *  .executeList();               - �������� ������</pre>
     *
     * ��������� :color � <#if color ... > ���������� ��������� ������ someOperation.getColor()
     * getColor() ������ ���� ������� ���������� QueryParameter
     * ��������� :extra_sex � <#if sex ... > ����������� ���������� ���. ���������

     * @param name ��� �������, ������ ���� ������� � hbm �����,
     * � ����� ��� "operation_class_name.query_name" ��� query_name ���� ���� ��������
     * @return ������
     */
    Query createQuery(String name);

	/**
	 * �������� ����������� ��������.
	 */
	R getRestriction();

	/**
	 * ���������� ����������� ��� ��������.
	 * @param restriction
	 */
	void setRestriction(R restriction);

	/**
	 * �������� ��������� ������������� ��������.
	 */
	/**
	 * �������� ��������� ������������� ��������.
	 * @return ����������� ��������� ��������, ���� ������ ��� �������� ���� null.
	 * �� ���� ���������� ������ ��������� �������������.
	 */
	ConfirmStrategySource getStrategy();

	/**
	 * ���������� ��������� ��� ��������.
	 * @param strategy - ��������� ��������������.
	 */
	void setStrategy(ConfirmStrategySource strategy);

	MessageCollector getMessageCollector();


	StateMachineEvent getStateMachineEvent();
}
