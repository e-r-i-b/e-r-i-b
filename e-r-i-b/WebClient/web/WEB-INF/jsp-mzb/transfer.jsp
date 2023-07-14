<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 01.10.2007
  Time: 12:05:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="transferList">
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">Переводы</tiles:put>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="data" type="string">

    <table cellspacing="0" cellpadding="0" width="100%" border="0">
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
               <td valign="top" width="300px;" height="55px">
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
    <tr><td colspan="2" class='titleWorkspace'>Переводы</td></tr>
    <tr>
       <td align="center">
       <table cellspacing="0" cellpadding="0">
     <tr>
	    <td class="listPaymentBg">
	       <table cellspacing="3" cellpadding="3" class="elListPayment">
		       <tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/payment"
					           serviceId="CardReplenishmentPayment">
                        <phiz:param name="form" value="CardReplenishmentPayment"/>
						<img src="${imagePath}/CardReplenishmentPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;" height="55px">
					<phiz:link action="/private/payments/payment"
					           serviceId="CardReplenishmentPayment">
                        <phiz:param name="form" value="CardReplenishmentPayment"/>
						<span class="listPayTitle">Пополнение пластиковой карты</span>
					</phiz:link>
				</td>
			</tr>
			<tr>
				<td style="padding-right:15px;padding-bottom:15px;">
					<phiz:menuLink action="/private/payments/payments" param="name=CardReplenishmentPayment"
					               serviceId="CardReplenishmentPayment" id="m1" align="right">История операций
					</phiz:menuLink>
				</td>
			</tr>
	       </table>
		</td>
    <td class="listPaymentBg">
			<table cellspacing="3" cellpadding="3" class="elListPayment">
		       <tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/payment"
					           serviceId="InternalPayment">
                        <phiz:param name="form" value="InternalPayment"/>
						<img src="${imagePath}/InternalPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;" height="55px">
					<phiz:link action="/private/payments/payment"
					           serviceId="InternalPayment">
                        <phiz:param name="form" value="InternalPayment"/>
						<span class="listPayTitle">Перевод между<br/>счетами</span>
					</phiz:link>
				</td>
			</tr>
			<tr>
				<td style="padding-right:15px;padding-bottom:15px;">
					<phiz:menuLink action="/private/payments/payments" param="name=InternalPayment"
					               serviceId="InternalPayment" id="m2" align="right">История операций
					</phiz:menuLink>
				</td>
			</tr>
	       </table>
		</td>
     </tr>
	 <tr>
       <td class="listPaymentBg">
	       <table cellspacing="3" cellpadding="3" class="elListPayment">
		       <tr>
				<td class="listPayImg" rowspan="2" class="imgListPayment">
					<phiz:link action="/private/payments/payment"
					           serviceId="RurPayment">
                        <phiz:param name="form" value="RurPayment"/>
						<img src="${imagePath}/RurPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;" height="55px">
					<phiz:link action="/private/payments/payment"
					           serviceId="RurPayment">
                        <phiz:param name="form" value="RurPayment"/>
						<span class="listPayTitle">Перевод рублей РФ</span>
					</phiz:link>
				</td>
			</tr>
			<tr>
				<td style="padding-right:15px;padding-bottom:15px;">
					<phiz:menuLink action="/private/payments/payments" param="name=RurPayment"
					               serviceId="RurPayment" id="m4" align="right">История операций
					</phiz:menuLink>
				</td>
			</tr>
	       </table>
	    </td>
		<td class="listPaymentBg">
	       <table cellspacing="3" cellpadding="3" class="elListPayment">
		       <tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/payment"
					           serviceId="TaxPayment">
                        <phiz:param name="form" value="TaxPayment"/>
						<img src="${imagePath}/TaxPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;" height="55px">
					<phiz:link action="/private/payments/payment"
					           serviceId="TaxPayment">
                        <phiz:param name="form" value="TaxPayment"/>
						<span class="listPayTitle">Оплата налогов</span>
					</phiz:link>
				</td>
			</tr>
			<tr>
				<td style="padding-right:15px;padding-bottom:15px;">
					<phiz:menuLink action="/private/payments/payments" param="name=TaxPayment"
					               serviceId="TaxPayment" id="m5" align="right">История операций
					</phiz:menuLink>
				</td>
			</tr>
	       </table>
	    </td>
     </tr>
     <tr>
       <td class="listPaymentBg">
	       <table cellspacing="3" cellpadding="3" class="elListPayment">
		       <tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/payment" 
					           serviceId="CurrencyPayment">
                        <phiz:param name="form" value="CurrencyPayment"/>
						<img src="${imagePath}/CurrencyPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;" height="55px">
					<phiz:link action="/private/payments/payment"
					           serviceId="CurrencyPayment">
                        <phiz:param name="form" value="CurrencyPayment"/>
						<span class="listPayTitle">Перевод иностранной валюты</span>
					</phiz:link>
				</td>
			</tr>
			<tr>
				<td style="padding-right:15px;padding-bottom:15px;">
					<phiz:menuLink action="/private/payments/payments" param="name=CurrencyPayment"
					               serviceId="CurrencyPayment" id="m6" align="right">История операций
					</phiz:menuLink>
				</td>
			</tr>
	       </table>
	    </td>
		<td class="listPaymentBg">
			<table cellspacing="3" cellpadding="3" class="elListPayment">
		       <tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/payment"
					           serviceId="PurchaseSaleCurrencyPayment">
                        <phiz:param name="form" value="PurchaseSaleCurrencyPayment"/>
						<img src="${imagePath}/PCPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;" height="55px">
					<phiz:link action="/private/payments/payment"
					           serviceId="PurchaseSaleCurrencyPayment">
                        <phiz:param name="form" value="PurchaseSaleCurrencyPayment"/>
						<span class="listPayTitle">Покупка/продажа<br/>валюты</span>
					</phiz:link>
				</td>
			</tr>
			<tr>
				<td style="padding-right:15px;padding-bottom:15px;">
					<phiz:menuLink action="/private/payments/payments" param="name=PurchaseSaleCurrencyPayment"
					               serviceId="PurchaseSaleCurrencyPayment" id="m7" align="right">История операций
					</phiz:menuLink>
				</td>
			</tr>
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
               <td valign="top" width="300px;" height="55px">
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
              <td valign="top" width="300px;" height="55px">
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
    <tr><td colspan="2" class='titleWorkspace'>Справочник получателей</td></tr>
    <tr>
       <td align="center">
       <table cellspacing="0" cellpadding="0">
	   <tr>
       <td class="listPaymentBg">
            <table cellspacing="3" cellpadding="3" class="elListPayment">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/receivers/list" serviceId="PaymentReceiverList">
                     <img src="${imagePath}/HandBooksPh.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;" height="55px">
                   <phiz:link action="/private/receivers/list" serviceId="PaymentReceiverList">
						<span class="listPayTitle">Справочник получателей</span>
					</phiz:link>
               </td>
            </tr>
            </table>
       </td>
       <td class="listPaymentBg">
           <table cellspacing="3" cellpadding="3" class="elListPayment">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/dictionary/banks/national" serviceId="BankList">
                    <img src="${imagePath}/HandBooksBanks.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;" height="55px">
	             <phiz:link action="/private/dictionary/banks/national" serviceId="BankList">
					<span class="listPayTitle">Справочник банков</span>
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