<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="claimList">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

  <tiles:put name="mainmenu" value="Claims"/>
  <!-- заголовок -->
  <tiles:put name="pageTitle" type="string">Заявки</tiles:put>
  <tiles:put name="data" type="string">

    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='titleWorkspace'>Заявки</td></tr>
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
					 <phiz:link action="/private/claims/claim"  serviceId="InternalTransferClaim">
                         <phiz:param name="form" value="InternalTransferClaim"/>
						<img src="${imagePath}/InternalTransferClaim.gif" border="0"/>
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
    <tr><td colspan="2" class='titleWorkspace'><br>Пластиковые карты</td></tr>
    <tr>
       <td>
       <table cellspacing="0" cellpadding="0" border="0">
	   <tr>
		   <td>
			   <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			   <tr>
				  <td class="listPayImg" align="center" rowspan="2">
					 <phiz:link action="/private/claims/claim" serviceId="UnblockingCardClaim">
                         <phiz:param name="form" value="UnblockingCardClaim"/>
						<img src="${imagePath}/UnblockingCardClaim.gif" border="0"/>
					 </phiz:link>
				  </td>
				  <td valign="top" width="300px;">
					 <phiz:link action="/private/claims/claim" serviceId="UnblockingCardClaim">
                        <phiz:param name="form" value="UnblockingCardClaim"/>
						<span class="listPayTitle">Разблокировка карты</span>
					 </phiz:link><br>
						 Подача в банк заявки на разблокировку пластиковой карты.<br>
				  </td>
			   </tr>
			   <tr><td><phiz:menuLink action="/private/claims/claims" param="name=UnblockingCardClaim" serviceId="UnblockingCardClaim" id="m7" align="right">История операций</phiz:menuLink></td></tr>
			   </table>
		   </td>
		   <td>
			   <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			   <tr>
				  <td class="listPayImg" align="center" rowspan="2">
					 <phiz:link action="/private/claims/claim" serviceId="SMSInformationClaim">
                         <phiz:param name="form" value="SMSInformationClaim"/>
						<img src="${imagePath}/SMSInformationClaim.gif" border="0"/>
					 </phiz:link>
				  </td>
				  <td valign="top" width="300px;">
					 <phiz:link action="/private/claims/claim" serviceId="SMSInformationClaim">
                        <phiz:param name="form" value="SMSInformationClaim"/>
						<span class="listPayTitle">SMS - информирование</span>
					 </phiz:link><br>
						 Подача в банк заявки на SMS-информирование по операциям с пластиковой картой.<br>
				  </td>
			   </tr>
			   <tr><td><phiz:menuLink action="/private/claims/claims" param="name=SMSInformationClaim" serviceId="SMSInformationClaim" id="m8" align="right">История операций</phiz:menuLink></td></tr>
			   </table>
		   </td>
	   </tr>
	   <tr>
			<td>
				<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			   <tr>
				  <td class="listPayImg" align="center" rowspan="2">
					 <phiz:link action="/private/claims/claim" serviceId="NotReIssueCardClaim">
                         <phiz:param name="form" value="NotReIssueCardClaim"/>
						<img src="${imagePath}/NotReIssueCardClaim.gif" border="0"/>
					 </phiz:link>
				  </td>
				  <td valign="top" width="300px;">
					 <phiz:link action="/private/claims/claim" serviceId="NotReIssueCardClaim">
                         <phiz:param name="form" value="NotReIssueCardClaim"/>
						<span class="listPayTitle">Отказ от перевыпуска карты</span>
					 </phiz:link><br>
						 Подача в банк заявки об отказе от перевыпуска пластиковой карты.<br>
				  </td>
			   </tr>
			   <tr><td><phiz:menuLink action="/private/claims/claims" param="name=NotReIssueCardClaim" serviceId="NotReIssueCardClaim" id="m9" align="right">История операций</phiz:menuLink></td></tr>
			   </table>
			</td>
			<td>
			  <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			   <tr>
				  <td class="listPayImg" align="center" rowspan="2">
					 <phiz:link action="/private/claims/claim" serviceId="ReIssueCardClaim">
                         <phiz:param name="form" value="ReIssueCardClaim"/>
						<img src="${imagePath}/ReIssueCardClaim.gif" border="0"/>
					 </phiz:link>
				  </td>
				  <td valign="top" width="300px;">
					 <phiz:link action="/private/claims/claim" serviceId="ReIssueCardClaim">
                         <phiz:param name="form" value="ReIssueCardClaim"/>
						<span class="listPayTitle">Перевыпуск карты</span>
					 </phiz:link><br>
						 Подача в банк заявки на перевыпуск пластиковой карты.<br>
				  </td>
			   </tr>
			   <tr><td><phiz:menuLink action="/private/claims/claims" param="name=ReIssueCardClaim" serviceId="ReIssueCardClaim" id="m10" align="right">История операций</phiz:menuLink></td></tr>
			   </table>
			</td>
	  </tr>
	  <tr>
		<td>
		    <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="CardChargeLimitClaim">
                    <phiz:param name="form" value="CardChargeLimitClaim"/>
                    <img src="${imagePath}/CardChargeLimitClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="CardChargeLimitClaim">
                     <phiz:param name="form" value="CardChargeLimitClaim"/>
                    <span class="listPayTitle">Установка лимита для доп.карт</span>
                 </phiz:link><br>
                     Заявка на установку лимита расходования средств для дополнительных карт клиента.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=CardChargeLimitClaim" serviceId="CardChargeLimitClaim" id="m11" align="right">История операций</phiz:menuLink></td></tr>
           </table>
		</td>
		<td>
		    <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="IssueCardClaim">
                     <phiz:param name="form" value="IssueCardClaim"/>
                    <img src="${imagePath}/IssueCardClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="IssueCardClaim">
                    <phiz:param name="form" value="IssueCardClaim"/>
                    <span class="listPayTitle">Выпуск пластиковой карты</span>
                 </phiz:link><br>
                     Подача в банк заявки на выпуск пластиковой карты.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=IssueCardClaim" serviceId="IssueCardClaim" id="m12" align="right">История операций</phiz:menuLink></td></tr>
           </table>
		</td>
	  </tr>
      <tr>
		<td>
		    <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="BlockingCardClaim">
                     <phiz:param name="form" value="BlockingCardClaim"/>
                    <img src="${imagePath}/BlockingCardClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="BlockingCardClaim">
                     <phiz:param name="form" value="BlockingCardClaim"/>
                    <span class="listPayTitle">Блокирование карты</span>
                 </phiz:link><br>
                     Подача в банк заявки на блокирование пластиковой карты.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=BlockingCardClaim" serviceId="BlockingCardClaim" id="m13" align="right">История операций</phiz:menuLink></td></tr>
           </table>
		</td>
		<td><table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="IssueAdditionalCardClaim">
                     <phiz:param name="form" value="IssueAdditionalCardClaim"/>
                    <img src="${imagePath}/IssueAdditionalCardClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="IssueAdditionalCardClaim">
                    <phiz:param name="form" value="IssueAdditionalCardClaim"/>
                    <span class="listPayTitle">Выпуск дополнительной карты</span>
                 </phiz:link><br>
                     Подача в банк заявки на выпуск дополнительной пластиковой карты.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=IssueAdditionalCardClaim" serviceId="IssueAdditionalCardClaim" id="m14" align="right">История операций</phiz:menuLink></td></tr>
           </table>
		</td>
	  </tr>
	  <tr>
		<td>
			<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
			<td class="listPayImg" align="center" rowspan="2">
				<phiz:link action="/private/claims/claim" serviceId="StopListCardClaim">
                    <phiz:param name="form" value="StopListCardClaim"/>
					<img src="${imagePath}/.gif" border="0" width="50px" height="50px"  style="margin-bottom:10px"/>
				</phiz:link>
			</td>
			<td valign="top" width="300px;">
				<phiz:link action="/private/claims/claim" serviceId="StopListCardClaim">
                    <phiz:param name="form" value="StopListCardClaim"/>
					<span class="listPayTitle">Постановка карты в СТОП-ЛИСТ</span>
				</phiz:link><br>
					Заявление на постановку карты в стоп-лист.<br/>
				</td>
			</tr>
			<tr><td><phiz:menuLink action="/private/claims/claims" param="name=StopListCardClaim" serviceId="StopListCardClaim" id="m15" align="right">История операций</phiz:menuLink></td></tr>
			</table>
		</td>
		<td>
			<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
			<td class="listPayImg" align="center" rowspan="2">
				<phiz:link action="/private/claims/claim" serviceId="CardMootTransClaim">
                    <phiz:param name="form" value="CardMootTransClaim"/>
					<img src="${imagePath}/.gif" border="0" width="50px" height="50px"/>
				</phiz:link>
			</td>
			<td valign="top" width="300px;">
				<phiz:link action="/private/claims/claim" serviceId="CardMootTransClaim">
                    <phiz:param name="form" value="CardMootTransClaim"/>
					<span class="listPayTitle">Спорная транзакция</span>
				</phiz:link><br>
					Заявление на расследование спорных транзакций по пластиковой карте.<br/>
				</td>
			</tr>
			<tr><td><phiz:menuLink action="/private/claims/claims" param="name=CardMootTransClaim" serviceId="CardMootTransClaim" id="m16" align="right">История операций</phiz:menuLink></td></tr>
			</table>
		</td>
	  </tr>
      </table>
    </td>
    </tr>
    </table>
  </tiles:put>
</tiles:insert>
