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
 * —ущность кредитной карты
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
	 * @return идентифиактор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентифиактор
	 * @param id идентифиактор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * необходим только при репликации
	 * @return ключ записи
	 */
	public Comparable getSynchKey()
	{
		return name;
	}

	/**
	 * ќбновить содержимое записи из другой
	 * @param that запись из которой надо обновить
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
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return тип бонусной программы
	 */
	public CardProgrammType getProgrammType()
	{
		return programmType;
	}

	/**
	 * задать тип бонусной программы
	 * @param programmType тип бонусной программы
	 */
	public void setProgrammType(CardProgrammType programmType)
	{
		this.programmType = programmType;
	}

	/**
	 * @return потраченные средства
	 */
	public BigDecimal getInputs()
	{
		return inputs;
	}

	/**
	 * задать потраченные средства
	  * @param inputs потраченные средства
	 */
	public void setInputs(BigDecimal inputs)
	{
		this.inputs = inputs;
	}

	/**
	 * @return средства вернувшиес€
	 */
	public BigDecimal getBonus()
	{
		return bonus;
	}

	/**
	 * задать вернувшиес€ средства
	 * @param bonus средства вернувшиес€
	 */
	public void setBonus(BigDecimal bonus)
	{
		this.bonus = bonus;
	}

	/**
	 * @return условие бонусной программы
	 */
	public String getClause()
	{
		return clause;
	}

	/**
	 * задать условие бонусной программы
	 * @param clause условие бонусной программы
	 */
	public void setClause(String clause)
	{
		this.clause = clause;
	}

	/**
	 * @return идентифиактор картинки карты
	 */
	public Long getCardIconId()
	{
		return cardIconId;
	}

	/**
	 * задать идентифиактор картинки карты
	 * @param cardIconId идентифиактор картинки карты
	 */
	public void setCardIconId(Long cardIconId)
	{
		this.cardIconId = cardIconId;
	}

	/**
	 * @return идентифиактор картинки бонусной программы
	 */
	public Long getProgrammIconId()
	{
		return programmIconId;
	}

	/**
	 * задать идентифиактор картинки бонусной программы
	 * @param programmIconId идентифиактор картинки бонусной программы
	 */
	public void setProgrammIconId(Long programmIconId)
	{
		this.programmIconId = programmIconId;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * задать описание
	 * @param description описание
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return рекомендации
	 */
	public String getRecommendation()
	{
		return recommendation;
	}

	/**
	 * задать рекомендации
	 * @param recommendation рекомендации
	 */
	public void setRecommendation(String recommendation)
	{
		this.recommendation = recommendation;
	}

	/**
	 * @return параметры отображени€
	 */
	public CardDiagramParameters getDiagramParameters()
	{
		return diagramParameters;
	}

	/**
	 * задать параметры отображени€
	 * @param diagramParameters параметры отображени€
	 */
	public void setDiagramParameters(CardDiagramParameters diagramParameters)
	{
		this.diagramParameters = diagramParameters;
	}

	/**
	 * @return предлагать карту по умолчанию
	 */
	public boolean isShowAsDefault()
	{
		return showAsDefault;
	}

	/**
	 * @param showAsDefault предлагать карту по умолчанию
	 */
	public void setShowAsDefault(boolean showAsDefault)
	{
		this.showAsDefault = showAsDefault;
	}
}
