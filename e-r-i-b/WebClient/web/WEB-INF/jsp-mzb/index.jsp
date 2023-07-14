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
    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='titleWorkspace'>Шаблоны</td></tr>
    <tr>
       <td align="center">
       <table cellspacing="0" cellpadding="0">
	   <tr>
       <td class="listPaymentBg">
            <table cellspacing="3" cellpadding="3" class="elListPayment">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/templates" serviceId="ClientTemplatesManagement">
                     <img src="${imagePath}/templatesPayments.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                   <phiz:link action="/private/templates" serviceId="ClientTemplatesManagement">
						<span class="listPayTitle">Шаблоны платежей</span>
					</phiz:link>
               </td>
            </tr>
            </table>
	   </td>
	   <td width="250px;">&nbsp;</td>
     </tr>
     </table>
     </td>
    </tr>
	<tr><td colspan="2" class='titleWorkspace'><bean:message key="label.payments.services" bundle="commonBundle"/></td></tr>
    <tr>
       <td align="center">
       <table cellspacing="0" cellpadding="0">
	   <tr>
       <td class="listPaymentBg">
            <table cellspacing="3" cellpadding="3" class="elListPayment">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="cellular-communication"/>
                     <img src="${imagePath}/MobilePayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px" height="55px">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="cellular-communication"/>
                     <span class="listPayTitle">Оплата сотовой связи</span>
                  </phiz:link>
               </td>
            </tr>
	        <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m5" align="right">История операций</phiz:menuLink></td></tr>
            </table>
       </td>
       <td class="listPaymentBg">
           <table cellspacing="3" cellpadding="3" class="elListPayment">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="telephony"/>
                    <img src="${imagePath}/Telephony.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px" height="55px">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="telephony"/>
                    <span class="listPayTitle">Услуги телефонии</span>
                 </phiz:link>
              </td>
           </tr>
	       <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m7" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
     </tr>
	 <tr>
       <td class="listPaymentBg">
            <table cellspacing="3" cellpadding="3" class="elListPayment">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="ip-telephony"/>
                     <img src="${imagePath}/IPTelephony.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px" height="55px">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="ip-telephony"/>
                     <span class="listPayTitle">Интернет и <br>IP-телефония</span>
                  </phiz:link>
               </td>
            </tr>
	        <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m8" align="right">История операций</phiz:menuLink></td></tr>
            </table>
       </td>
       <td class="listPaymentBg">
           <table cellspacing="3" cellpadding="3" class="elListPayment">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="satellite-connection"/>
                    <img src="${imagePath}/SatelliteConnection.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px" height="55px">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="satellite-connection"/>
                    <span class="listPayTitle">Коммерческое телевидение</span>
                 </phiz:link>
              </td>
           </tr>
	       <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m9" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
     </tr>
     <tr>
       <td class="listPaymentBg">
            <table cellspacing="3" cellpadding="3" class="elListPayment">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment"  serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="credit-repayment"/>
                     <img src="${imagePath}/CreditRepaymentaymanet.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px" height="55px">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="credit-repayment"/>
                     <span class="listPayTitle">Погашение кредитов</span>
                  </phiz:link>
               </td>
            </tr>
	        <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m10" align="right">История операций</phiz:menuLink></td></tr>
            </table>
        </td>
        <td class="listPaymentBg">
           <table cellspacing="3" cellpadding="3" class="elListPayment">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="gkh-payment"/>
                    <img src="${imagePath}/GKHPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px" height="55px">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="gkh-payment"/>
                    <span class="listPayTitle">Оплата услуг ЖКХ для г.Москва</span>
                 </phiz:link>
           </tr>
	       <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m12" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
     </tr>
	 <tr>
		<td class="listPaymentBg">
           <table cellspacing="3" cellpadding="3" class="elListPayment">
	           <tr>
				  <td class="listPayImg" align="center" rowspan="2">
					 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                         <phiz:param name="form" value="GoodsAndServicesPayment"/>
                         <phiz:param name="appointment" value="electric-payment"/>
						<img src="${imagePath}/ElectricPayment.gif" border="0"/>
					 </phiz:link>
				  </td>
				  <td valign="top" width="300px" height="55px">
					 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                        <phiz:param name="form" value="GoodsAndServicesPayment"/>
                         <phiz:param name="appointment" value="electric-payment"/>
						<span class="listPayTitle">Оплата электроэнергии</span>
					 </phiz:link>
			   </tr>
	       <tr><td style="padding-right:15px;padding-bottom:15px;"><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m13" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
     </tr>
     </table>
     </td>
    </tr>
    <tr><td colspan="2" class='titleWorkspace'>Журнал платежей</td></tr>
    <tr>
       <td align="center">
       <table cellspacing="0" cellpadding="0">
	   <tr>
       <td class="listPaymentBg">
            <table cellspacing="3" cellpadding="3" class="elListPayment">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/common" serviceId="PaymentList">
                     <phiz:param name="status" value="all"/>
                     <img src="${imagePath}/AllPayments.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px" height="55px">
                   <phiz:link action="/private/payments/common"
					    serviceId="PaymentList">
                        <phiz:param name="status" value="all"/>
						<span class="listPayTitle">Все платежи</span>
					</phiz:link>
               </td>
            </tr>
            </table>
       </td>
       <td class="listPaymentBg">
           <table cellspacing="3" cellpadding="3" class="elListPayment">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/common" serviceId="PaymentList">
                    <phiz:param name="status" value="uncompleted"/>
                    <img src="${imagePath}/UncompletedPayments.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px" height="55px">
	             <phiz:link action="/private/payments/common"
				    serviceId="PaymentList">
                     <phiz:param name="status" value="uncompleted"/>
					<span class="listPayTitle">Незавершенные платежи</span>
				</phiz:link>
              </td>
           </tr>
           </table>
       </td>
     </tr>
     </table>
     </td>
    </tr>
    </table>

 </tiles:put>
</tiles:insert>
