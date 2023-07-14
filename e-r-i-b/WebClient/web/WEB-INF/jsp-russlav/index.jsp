<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="paymentMain">  
  <!-- заголовок -->
  <tiles:put name="pageTitle" type="string">Платежи</tiles:put>
  <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
  <!--История операций всех платежей-->
  <tiles:put name="data" type="string">
  <table cellpadding="0" cellspacing="0" class="MaxSize">
  <tr>
    <td height="100%">
    <div class="MaxSize">
    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='listPayPart'>Перевод средств</td></tr>
    <tr>
       <td>
       <table cellspacing="0" cellpadding="0">
	   <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="InternalPayment">
                    <phiz:param name="form" value="InternalPayment"/>
                    <img src="${imagePath}/InternalPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="InternalPayment">
                     <phiz:param name="form" value="InternalPayment"/>
                    <span class="listPayTitle">Перевод по счетам в системе</span>
                 </phiz:link><br>
                    Перевод денежных средств между вашими счетами.<br><br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=InternalPayment" serviceId="InternalPayment" id="m1" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
       <td width="50%">
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="PurchaseSaleCurrencyPayment">
                     <phiz:param name="form" value="PurchaseSaleCurrencyPayment"/>
                    <img src="${imagePath}/PCPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="PurchaseSaleCurrencyPayment">
                    <phiz:param name="form" value="PurchaseSaleCurrencyPayment"/>
                    <span class="listPayTitle">Покупка/продажа иностранной валюты</span>
                 </phiz:link><br>
                    Покупка и продажа иностранной валюты за рубли.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=PurchaseSaleCurrencyPayment" serviceId="PurchaseSaleCurrencyPayment" id="m2" align="right">История операций</phiz:menuLink></td></tr>
           </table>
        </td>
     </tr>
     <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="RurPayment">
                     <phiz:param name="form" value="RurPayment"/>
                    <img src="${imagePath}/RurPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="RurPayment">
                    <phiz:param name="form" value="RurPayment"/>
                    <span class="listPayTitle">Перевод рублей РФ</span>
                 </phiz:link><br>
                    Перевод денежных средств с вашего счета<br>
	                на счет физического лица или юридического лица.
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=RurPayment" serviceId="RurPayment" id="m4" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
	     <td>
	       <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	       <tr>
	           <td class="listPayImg" align="center" rowspan="2">
	             <phiz:link action="/private/payments/payment" serviceId="TaxPayment">
                     <phiz:param name="form" value="TaxPayment"/>
	                 <img src="${imagePath}/TaxPayment.gif" border="0"/>
	             </phiz:link>
	           </td>
	           <td valign="top" width="300px;">
	             <phiz:link action="/private/payments/payment" serviceId="TaxPayment">
                     <phiz:param name="form" value="TaxPayment"/>
	                 <span class="listPayTitle">Оплата налогов</span>
	             </phiz:link><br>
	                 Перечисление денежных средств с вашего<br>счета в счет уплаты налогов и сборов.<br>
	           </td>
	       </tr>
		   <tr><td><phiz:menuLink action="/private/payments/payments" param="name=TaxPayment" serviceId="TaxPayment" id="m5" align="right">История операций</phiz:menuLink></td></tr>
	       </table>
	   </td>
     </tr>
	 <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="CurrencyPayment">
                     <phiz:param name="form" value="CurrencyPayment"/>
                    <img src="${imagePath}/CurrencyPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="CurrencyPayment">
                    <phiz:param name="form" value="CurrencyPayment"/>
                    <span class="listPayTitle">Перевод иностранной валюты</span>
                 </phiz:link><br>
	                Перечисление денежных средств с вашего<br>
	                счета  на счет получателя в иностранной валюте.
                   <br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=CurrencyPayment" serviceId="CurrencyPayment" id="m6" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
		 <td>
	      <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	      <tr>
	         <td class="listPayImg" align="center" rowspan="2">
		        <phiz:link action="/private/payments/payment" serviceId="ContactPayment">
                    <phiz:param name="form" value="ContactPayment"/>
	                <img src="${imagePath}/RurPaymentPh.gif" border="0"/>
	            </phiz:link>
	         </td>
	         <td valign="top" width="300px;">
		        <phiz:link action="/private/payments/payment" serviceId="ContactPayment">
                    <phiz:param name="form" value="ContactPayment"/>
	                <span class="listPayTitle">Перевод по сети CONTACT</span>
	            </phiz:link><br>
	                Быстрый перевод денежных средств с<br>
		            вашего счета в пользу физических лиц<br>
		            без открытия счета получателя.
	         </td>
	      </tr>
		  <tr><td><phiz:menuLink action="/private/payments/payments" param="name=ContactPayment" serviceId="ContactPayment" id="m7" align="right">История операций</phiz:menuLink></td></tr>
	      </table>
	   </td>
     </tr>
     </table>
     </td>
    </tr>
	
    <tr><td colspan="2" class='listPayPart'><br><bean:message key="label.payments.services" bundle="commonBundle"/></td></tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0" rowspan="2">
        <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="GKHPayment">
                     <phiz:param name="form" value="GKHPayment"/>
                    <img src="${imagePath}/GKHPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="GKHPayment">
                     <phiz:param name="form" value="GKHPayment"/>
                    <span class="listPayTitle">Оплата услуг ЖКХ</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета
	                для оплаты жилищно-коммунальных услуг  по
	                форме единого платежного документа.
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GKHPayment" serviceId="GKHPayment" id="m8" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="cellular-communication"/>
                     <img src="${imagePath}/MobilePayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="cellular-communication"/>
                     <span class="listPayTitle">Оплата сотовой связи</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счетадля оплаты услуг  сотовой связи.<br><br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m9" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="credit-repayment"/>
                     <img src="${imagePath}/CreditRepaymentaymanet.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="MobilePGoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="credit-repaymentayment"/>
                     <span class="listPayTitle">Погашение кредита</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счета для погашения кредита и процентов по нему. <br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m10" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="inet-connection"/>
                    <img src="${imagePath}/InternetPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="inet-connection"/>
                    <span class="listPayTitle">Интернет</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты услуг Интернет-провайдеров.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m11" align="right">История операций</phiz:menuLink></td></tr>
           </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="satellite-connection"/>
                    <img src="${imagePath}/SatelliteConnection.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="satellite-connection"/>
                    <span class="listPayTitle">Спутниковая связь/ТВ</span>
                 </phiz:link><br>
                    Перечисление денежных средств для пополнения счета  операторов спутниковой связи и ТВ.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m12" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="ip-telephony"/>
                     <img src="${imagePath}/IPTelephony.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="ip-telephony"/>
                     <span class="listPayTitle">IP-телефония</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счета для оплаты услуг  операторов IP-телефонии.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m13" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="telephony"/>
                    <img src="${imagePath}/Telephony.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="telephony"/>
                    <span class="listPayTitle">Услуги телефонии</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты услуг  операторов телефонной связи.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m14" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="game-portals"/>
                     <img src="${imagePath}/GamePortals.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="game-portals"/>
                     <span class="listPayTitle">Игровые порталы</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счета для пополнения счетов игровых порталов.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m15" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment"  serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="inet-shops"/>
                    <img src="${imagePath}/InetShops.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="inet-shops"/>
                    <span class="listPayTitle">Интернет-магазины</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты товаров и услуг, заказанных в Интернет-магазинах.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m16" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="payment-system"/>
                     <img src="${imagePath}/PaymentSystem.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="payment-system"/>
                     <span class="listPayTitle">Платежные системы</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счета для пополнения счета (электронного кошелька) платежных систем.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m17" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="air-ticket"/>
                    <img src="${imagePath}/AirTicket.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="air-ticket"/>
                    <span class="listPayTitle">Авиабилеты</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты заказанных авиабилетов.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m18" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="travel-agency"/>
                     <img src="${imagePath}/TravelAgency.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="travel-agency"/>
                     <span class="listPayTitle">Турпоездки</span>
                  </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты туристического пакета.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m19" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
	    <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="hotel-payment"/>
                    <img src="${imagePath}/HotelPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="hotel-payment"/>
                    <span class="listPayTitle">Гостиницы</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты забронированного номера в гостинице.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m20" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="policy-payment"/>
                     <img src="${imagePath}/PolicyPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="policy-payment"/>
                     <span class="listPayTitle">Страховой полис</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счета для оплаты страхового полиса.<br><br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m21" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
	    <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="realty-operation"/>
                    <img src="${imagePath}/RealtyOperation.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="realty-operation"/>
                    <span class="listPayTitle">Недвижимость</span>
                 </phiz:link><br>
                    Перечисление денежных средств с вашего счета для оплаты операций с недвижимостью.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m22" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="other-payment"/>
                     <img src="${imagePath}/OtherPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="other-payment"/>
                     <span class="listPayTitle">Прочее</span>
                  </phiz:link><br>
                     Перечисление денежных средств с вашего счета для оплаты прочих товаров и услуг. <br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m23" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    </table>
    </div>
   </td>
</tr>
</table>

 </tiles:put>
</tiles:insert>
