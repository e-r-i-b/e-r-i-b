/***********************************************************************
 * Module:  CommissionOptions.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface CommissionOptions
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

/**
 * Способ удержания комиссии
 */
public interface CommissionOptions
{
   /**
    * Откуда удерживать комиссию
    *
    * @return откуда
    */
   CommissionTarget getTarget();
   /**
    * Счет с которого удерживать комиссию. Обязателен для заполнения в случае getTarget == other.
    * В остальных случаях значение свойства игнорируется.
    * 
    * Счет должен принадлежать (быть зарегистрированным в ИКФЛ)  на клиента, совершебщего платеж.
    * Domain: AccountNumber
    */
   String getAccount();

}