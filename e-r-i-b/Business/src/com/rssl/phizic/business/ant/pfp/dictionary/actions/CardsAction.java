package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardDiagramParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardProgrammType;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Экшен для загрузки Карт из xml
 * @author koptyaev
 * @ created 26.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class CardsAction extends DictionaryRecordsActionBase<Card>
{
	private static final BigDecimalParser parser = new BigDecimalParser();

	public void execute(Element element) throws Exception
	{
		Map<String, Image> images = new HashMap<String,Image>();
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		CardProgrammType programmType = CardProgrammType.valueOf(XmlHelper.getSimpleElementValue(element, "programmType"));
		BigDecimal inputs = parser.parse(XmlHelper.getSimpleElementValue(element, "inputs"));
		BigDecimal bonus = parser.parse(XmlHelper.getSimpleElementValue(element, "bonus"));
		String clause = XmlHelper.getSimpleElementValue(element, "clause");
		String cardIconUrl = XmlHelper.getSimpleElementValue(element, "cardIconUrl");
		String programmIconUrl = XmlHelper.getSimpleElementValue(element, "programmIconUrl");
		String description = XmlHelper.getSimpleElementValue(element, "description");
		String recommendation = XmlHelper.getSimpleElementValue(element, "recommendation");
		//параметры диаграммы
		Element elDiagramParameters = XmlHelper.selectSingleNode(element, "diagramParameters");
		boolean useImage = getBooleanValue(XmlHelper.getSimpleElementValue(elDiagramParameters, "useImage"));
		String color = XmlHelper.getSimpleElementValue(elDiagramParameters, "color");
		String imageUrl = XmlHelper.getSimpleElementValue(elDiagramParameters, "imageUrl");
		boolean useNet = getBooleanValue(XmlHelper.getSimpleElementValue(elDiagramParameters, "useNet"));

		//сборка мапы с картинками
		images.put("cardIconUrl", getImageValue(cardIconUrl));
		images.put("programmIconUrl", getImageValue(programmIconUrl));
		images.put("imageUrl", getImageValue(imageUrl));
		
		CardDiagramParameters params = new CardDiagramParameters();
		params.setUseImage(useImage);
		params.setColor(color);
		params.setUseNet(useNet);

		Card card = new Card();
		card.setName(name);
		card.setProgrammType(programmType);
		card.setInputs(inputs);
		card.setBonus(bonus);
		card.setClause(clause);
		card.setDescription(description);
		card.setRecommendation(recommendation);
		card.setDiagramParameters(params);

		addRecord(key,card,images);
	}
}
