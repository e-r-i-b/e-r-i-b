package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardDiagramParameters;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей карт
 */

public class CardProcessor extends PFPProcessorBase<Card>
{
	@Override
	protected Class<Card> getEntityClass()
	{
		return Card.class;
	}

	@Override
	protected Card getNewEntity()
	{
		Card card = new Card();
		card.setDiagramParameters(new CardDiagramParameters());
		return card;
	}

	@Override
	protected void update(Card source, Card destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setProgrammType(source.getProgrammType());
		destination.setInputs(source.getInputs());
		destination.setBonus(source.getBonus());
		destination.setClause(source.getClause());
		destination.setCardIconId(mergeImage(source.getCardIconId(), destination.getCardIconId()));
		destination.setProgrammIconId(mergeImage(source.getProgrammIconId(), destination.getProgrammIconId()));
		destination.setDescription(source.getDescription());
		destination.setRecommendation(source.getRecommendation());
		destination.setShowAsDefault(source.isShowAsDefault());

		CardDiagramParameters sourceDiagramParameters = source.getDiagramParameters();
		CardDiagramParameters destinationDiagramParameters = destination.getDiagramParameters();
		destinationDiagramParameters.setUseImage(sourceDiagramParameters.isUseImage());
		destinationDiagramParameters.setColor(sourceDiagramParameters.getColor());
		destinationDiagramParameters.setImageId(mergeImage(sourceDiagramParameters.getImageId(), destinationDiagramParameters.getImageId()));
		destinationDiagramParameters.setUseNet(sourceDiagramParameters.isUseNet());
	}

	@Override
	protected void doRemove(Card localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getCardIconId());
		removeImage(localEntity.getProgrammIconId());
		removeImage(localEntity.getDiagramParameters().getImageId());
		super.doRemove(localEntity);
	}
}
