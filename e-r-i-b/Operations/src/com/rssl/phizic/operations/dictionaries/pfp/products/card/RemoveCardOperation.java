package com.rssl.phizic.operations.dictionaries.pfp.products.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления карты
 */

public class RemoveCardOperation extends RemoveDictionaryEntityWithImageOperationBase
{
	private static final CardService service = new CardService();

	private Card card;

	/**
	 * инициализация операции
	 * @param id идентификатор
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			card = service.getNewCard();
		else
			card = service.findById(id, getInstanceName());

		if (card == null)
			throw new ResourceNotFoundBusinessException("Карта c id=" + id + " не доступна для редактирования.", Card.class);

		addImage(EditCardOperation.CARD_IMAGE,     card.getCardIconId());
		addImage(EditCardOperation.PROGRAMM_IMAGE, card.getProgrammIconId());
		addImage(EditCardOperation.DIAGRAM_IMAGE,  card.getDiagramParameters().getImageId());
	}

	public Card getEntity()
	{
		return card;
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					removeImage(EditCardOperation.CARD_IMAGE);
					removeImage(EditCardOperation.PROGRAMM_IMAGE);
					removeImage(EditCardOperation.DIAGRAM_IMAGE);
					service.remove(card, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить карту, она используется в настройках.", e);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
