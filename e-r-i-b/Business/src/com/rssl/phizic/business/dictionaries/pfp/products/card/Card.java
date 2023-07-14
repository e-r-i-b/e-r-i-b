package com.rssl.phizic.business.dictionaries.pfp.products.card;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� �����
 */

public class Card extends PFPDictionaryRecord
{
	private Long id;
	private String name;
	private CardProgrammType programmType;
	private BigDecimal inputs;
	private BigDecimal bonus;
	private String clause;
	private Long cardIconId;
	private Long programmIconId;
	private String description;
	private String recommendation;
	private CardDiagramParameters diagramParameters;
	private boolean showAsDefault;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ��������� ������ ��� ����������
	 * @return ���� ������
	 */
	public Comparable getSynchKey()
	{
		return name;
	}

	/**
	 * �������� ���������� ������ �� ������
	 * @param that ������ �� ������� ���� ��������
	 */
	public void updateFrom(DictionaryRecord that)
	{
		setName(((Card) that).getName());
		setProgrammType(((Card) that).getProgrammType());
		setInputs(((Card) that).getInputs());
		setBonus(((Card) that).getBonus());
		setClause(((Card) that).getClause());
		setDescription(((Card) that).getDescription());
		setRecommendation(((Card) that).getRecommendation());
		setDiagramParameters(((Card) that).getDiagramParameters());
		setCardIconId(((Card) that).getCardIconId());
		setProgrammIconId(((Card) that).getProgrammIconId());
	}

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��� �������� ���������
	 */
	public CardProgrammType getProgrammType()
	{
		return programmType;
	}

	/**
	 * ������ ��� �������� ���������
	 * @param programmType ��� �������� ���������
	 */
	public void setProgrammType(CardProgrammType programmType)
	{
		this.programmType = programmType;
	}

	/**
	 * @return ����������� ��������
	 */
	public BigDecimal getInputs()
	{
		return inputs;
	}

	/**
	 * ������ ����������� ��������
	  * @param inputs ����������� ��������
	 */
	public void setInputs(BigDecimal inputs)
	{
		this.inputs = inputs;
	}

	/**
	 * @return �������� �����������
	 */
	public BigDecimal getBonus()
	{
		return bonus;
	}

	/**
	 * ������ ����������� ��������
	 * @param bonus �������� �����������
	 */
	public void setBonus(BigDecimal bonus)
	{
		this.bonus = bonus;
	}

	/**
	 * @return ������� �������� ���������
	 */
	public String getClause()
	{
		return clause;
	}

	/**
	 * ������ ������� �������� ���������
	 * @param clause ������� �������� ���������
	 */
	public void setClause(String clause)
	{
		this.clause = clause;
	}

	/**
	 * @return ������������� �������� �����
	 */
	public Long getCardIconId()
	{
		return cardIconId;
	}

	/**
	 * ������ ������������� �������� �����
	 * @param cardIconId ������������� �������� �����
	 */
	public void setCardIconId(Long cardIconId)
	{
		this.cardIconId = cardIconId;
	}

	/**
	 * @return ������������� �������� �������� ���������
	 */
	public Long getProgrammIconId()
	{
		return programmIconId;
	}

	/**
	 * ������ ������������� �������� �������� ���������
	 * @param programmIconId ������������� �������� �������� ���������
	 */
	public void setProgrammIconId(Long programmIconId)
	{
		this.programmIconId = programmIconId;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ������ ��������
	 * @param description ��������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ������������
	 */
	public String getRecommendation()
	{
		return recommendation;
	}

	/**
	 * ������ ������������
	 * @param recommendation ������������
	 */
	public void setRecommendation(String recommendation)
	{
		this.recommendation = recommendation;
	}

	/**
	 * @return ��������� �����������
	 */
	public CardDiagramParameters getDiagramParameters()
	{
		return diagramParameters;
	}

	/**
	 * ������ ��������� �����������
	 * @param diagramParameters ��������� �����������
	 */
	public void setDiagramParameters(CardDiagramParameters diagramParameters)
	{
		this.diagramParameters = diagramParameters;
	}

	/**
	 * @return ���������� ����� �� ���������
	 */
	public boolean isShowAsDefault()
	{
		return showAsDefault;
	}

	/**
	 * @param showAsDefault ���������� ����� �� ���������
	 */
	public void setShowAsDefault(boolean showAsDefault)
	{
		this.showAsDefault = showAsDefault;
	}
}
