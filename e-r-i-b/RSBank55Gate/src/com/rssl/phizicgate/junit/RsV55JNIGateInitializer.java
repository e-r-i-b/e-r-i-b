package com.rssl.phizicgate.junit;

import com.rssl.phizic.utils.test.JUnitConfigurator;
import com.rssl.phizgate.common.messaging.retail.jni.RetailJNIStartServiceImpl;

/**
 * @author hudyakov
 * @ created 25.08.2009
 * @ $Author$
 * @ $Revision$
 */

public class RsV55JNIGateInitializer implements JUnitConfigurator
{

   public void configure()
   {
      initRitail();
   }

   private void initRitail()
   {  
      try
      {
         RetailJNIStartServiceImpl retailJNIService = new RetailJNIStartServiceImpl();
	     //retailJNIService.start();
	      //todo ENH009881  Модифицировать существующие тесты.
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
}
