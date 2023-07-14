<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="claimList">
  <!-- заголовок -->
  <tiles:put name="pageTitle" type="string">Заявки</tiles:put>
  <tiles:put name="data" type="string">
  <tiles:importAttribute/>
  <c:set var="globalImagePath" value="${globalUrl}/images"/>
  <c:set var="imagePath" value="${skinUrl}/images"/>

  <table cellpadding="0" cellspacing="0" class="MaxSize">
  <tr>
    <td height="100%">
    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='listPayPart'>Заявки</td></tr>
    <tr>
       <td>
       <table cellspacing="0" cellpadding="0" border="0">
	   <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="DepositOpeningClaim">
                     <phiz:param name="form" value="DepositOpeningClaim"/>
                    <img src="${imagePath}/DepositOpeningClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="DepositOpeningClaim">
                     <phiz:param name="form" value="DepositOpeningClaim"/>
                    <span class="listPayTitle">Открытие счета/вклада</span>
                 </phiz:link><br>
                     Подача в банк заявки на открытие текущего счета или срочного вклада.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=DepositOpeningClaim" serviceId="DepositOpeningClaim" id="m1" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="DepositClosingClaim">
                     <phiz:param name="form" value="DepositClosingClaim"/>
                    <img src="${imagePath}/DepositClosingClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="DepositClosingClaim">
                     <phiz:param name="form" value="DepositClosingClaim"/>
                    <span class="listPayTitle">Закрытие счета/вклада</span>
                 </phiz:link><br>
                    Подача в банк заявки на закрытие текущего счета или досрочное закрытие срочного вклада.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=DepositClosingClaim" serviceId="DepositClosingClaim" id="m2" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
     </tr>
	 <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="DepositReplenishmentClaim">
                     <phiz:param name="form" value="DepositReplenishmentClaim"/>
                    <img src="${imagePath}/DepositReplenishmentClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="DepositReplenishmentClaim">
                     <phiz:param name="form" value="DepositReplenishmentClaim"/>
                    <span class="listPayTitle">Пополнить вклад</span>
                 </phiz:link><br>
                    Перевод денежных средств с вашего счета для пополнения срочного вклада.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=DepositReplenishmentClaim" serviceId="DepositReplenishmentClaim" id="m4" align="right">История операций</phiz:menuLink></td></tr>
           </table>
       </td>
       
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="InternalTransferClaim">
                     <phiz:param name="form" value="InternalTransferClaim"/>
                    <img src="${imagePath}/DepositReplenishmentClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="InternalTransferClaim">
                     <phiz:param name="form" value="InternalTransferClaim"/>
                    <span class="listPayTitle">Списать средства со вклада</span>
                 </phiz:link><br>
                    Перевод денежных средств со вклада на ваш счет.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=InternalTransferClaim" serviceId="InternalTransferClaim" id="m5" align="right">История операций</phiz:menuLink></td></tr>
           </table>
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