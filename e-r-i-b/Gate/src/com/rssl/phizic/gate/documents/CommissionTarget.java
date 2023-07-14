/***********************************************************************
 * Module:  CommissionTarget.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface CommissionTarget
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

public enum CommissionTarget
{
   /**
    * Удерживать комиссию со счета платежа
    */
   MAIN,
   /**
    * Удерживать комиссию с другого счета
    */
   OTHER,
   /**
    * Удерживать комиссию с получателя
    */
   RECEIVER;

}