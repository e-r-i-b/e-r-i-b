package com.rssl.phizic.operations.dictionaries.pfp.products.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardDiagramParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования карты
 */

public class EditCardOperation extends EditDictionaryEntityWithImageOperationBase
{
	private static final String DEFAULT_CARD_CONSTRAINT_NAME = "I_PFP_CARDS_DEFAULT_CARD";
	public static final String CARD_IMAGE       = "CardImage";
	public static final String PROGRAMM_IMAGE   = "ProgrammImage";
	public static final String DIAGRAM_IMAGE    = "DiagramImage";

	private static final CardService service = new CardService();

	private Card card;

	/**
	 * инициализация операции
	 * @param id идентификатор
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			card = service.getNewCard();
		else
			card = service.findById(id, getInstanceName());

		if (card == null)
			throw new ResourceNotFoundBusinessException("Карта c id=" + id + " не доступна для редактирования.", Card.class);

		addImage(CARD_IMAGE,     card.getCardIconId());
		addImage(PROGRAMM_IMAGE, card.getProgrammIconId());
		addImage(DIAGRAM_IMAGE,  card.getDiagramParameters().getImageId());
	}

	public Card getEntity()
	{
		return card;
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Image image = saveImage(CARD_IMAGE);
					Long imageId = image != null ? image.getId() : null;
					card.setCardIconId(imageId);
					image = saveImage(PROGRAMM_IMAGE);
					card.setProgrammIconId(image != null ? image.getId() : null);
					CardDiagramParameters diagramParameters = card.getDiagramParameters();
					if (diagramParameters.isUseImage())
					{
						image = saveImage(DIAGRAM_IMAGE);
						diagramParameters.setImageId(image != null ? image.getId() : null);
					}
					else
					{
						removeImage(DIAGRAM_IMAGE);
						diagramParameters.setImageId(null);
					}
					service.addOrUpdate(card, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			if(cve.getCause().getMessage().contains(DEFAULT_CARD_CONSTRAINT_NAME))
			{
				Card defaultCard = service.findDefaultCard(getInstanceName());
				throw new BusinessLogicException("Вы не можете присвоить карте признак «предлагать по умолчанию». Данный признак присвоен карте "+defaultCard.getName()+".",cve);
			}
			throw new BusinessLogicException("Кредитная карта с таким названием уже существует в системе. Пожалуйста, укажите другое название кредитной карты.", cve);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
