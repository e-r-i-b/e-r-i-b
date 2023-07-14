package com.rssl.phizic.operations.messageTranslate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.translate.MessageTranslateService;
import com.rssl.phizic.logging.translateMessages.MessageTranslate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateEditOperation  extends OperationBase implements EditEntityOperation
{
		private static final MessageTranslateService service = new MessageTranslateService();
		private MessageTranslate messageTranslate = new MessageTranslate();;

	/**
		 * Cохранить сущность.
		 * @throws com.rssl.phizic.business.BusinessException
		 */
		public void save() throws BusinessException
	{
			service.save(messageTranslate, getInstanceName());
		}

		/**
		 * Получить просматриваемую/редактируемую сущность
		 * @return просматриваемая/редактируемая сущность.
		 */
		public MessageTranslate getEntity() throws BusinessException, BusinessLogicException
		{
			return messageTranslate;
		}

		public void initialize(Long id) throws BusinessException
		{
		    if (id == null)
		        initialize();
			else
			    messageTranslate = service.findById(id, getInstanceName());
		}

		public void initialize()
		{
			messageTranslate = new MessageTranslate();
			messageTranslate.setIsNew(true);
		}

		protected String getInstanceName()
		{
			return Constants.DB_INSTANCE_NAME;
		}

}
