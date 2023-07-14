<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/receivers/edit">

<tiles:insert definition="paymentMain">
	<tiles:put name="mainmenu" value="Info"/>
    <tiles:put name="submenu" value="PaymentReceiverList" />
	<!-- заголовок -->
	<tiles:put name="pageTitle" type="string" value="Справочник получателей. Ввод/редактирование получателя"/>

	<!-- меню -->
	<tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.cancel"/>
			<tiles:put name="commandHelpKey"     value="button.cancel"/>
			<tiles:put name="bundle"             value="commonBundle"/>
			<tiles:put name="image"              value=""/>
			<tiles:put name="action"             value="/private/receivers/list.do?kind=PJB"/>
		</tiles:insert>
	</tiles:put>

	<!-- собственно данные -->
	<tiles:put name="data" type="string">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="isNew" value="${form.id == null}"/>
    <c:set var="hasBillingAcccess" value="${phiz:impliesService('BillingServiceProvidersManagment')}"/>
		<script type="text/javascript">
			function setBankInfo(bankInfo)
			{
				setElement('field(bankName)', bankInfo["name"]);
				setElement('field(bankCode)',bankInfo["BIC"]);
				setElement('field(correspondentAccount)', bankInfo["account"]);
			}

            function setReadOnly(bool)
            {
                document.getElementById('field(name)').readOnly = bool;
                document.getElementById('receiverINN').readOnly = bool;
                document.getElementById('field(account)').readOnly = bool;
                document.getElementById('field(bankName)').readOnly = bool;
                document.getElementById('field(bankCode)').readOnly = bool;
                document.getElementById('field(correspondentAccount)').readOnly = bool;
                if (bool)
                {
                    document.getElementById("bankButton").style.display = "none";
                }
                else
                {
                    document.getElementById("bankButton").style.display = "inline";
                }
            }
         <c:if test="${hasBillingAcccess}">
            function setProviderInfo(providerInfo)
            {
                setElement('field(name)',                   providerInfo["name"]);
                setElement('field(INN)',                    providerInfo["INN"]);
                setElement('field(account)',                providerInfo["account"]);
                setElement('field(bankName)',               providerInfo["bankName"]);
                setElement('field(bankCode)',               providerInfo["bankCode"]);
                setElement('field(correspondentAccount)',   providerInfo["correspondentAccount"]);
                document.getElementById('receiverKind').value = 'B';
                document.getElementById('receiverServiceProviderKey').value = providerInfo["externalId"];
                setReadOnly(true);
            }
         </c:if>
			function selectTypePerson(type)
			{
				var inn = document.getElementById("receiverINN").value;
				if(type == 'J')
				{
					if (inn.length > 12)
				    document.getElementById("receiverINN").value=inn.substring(0,12);
					document.getElementById("receiverINN").maxLength = "12";
                    <c:if test="${hasBillingAcccess}">
                        document.getElementById("providerButton").style.display = "inline";
                        document.getElementById('receiverServiceProviderKey').value = "";
                    </c:if>
                    document.getElementById("receiverKind").value = 'J';
                    setReadOnly(false);
				}
            <c:if test="${hasBillingAcccess}">
                if(type == 'B')
                {
                    document.getElementById("providerButton").style.display = "inline";
                    document.getElementById("receiverKind").value = 'B';
                    setReadOnly(true);
                }
            </c:if>
				if(type == 'P')
				{
	                document.getElementById("receiverINN").maxLength = "12";
                    <c:if test="${hasBillingAcccess}">
                        document.getElementById("providerButton").style.display = "none";
                        document.getElementById('receiverServiceProviderKey').value = "";
                    </c:if>
                    document.getElementById("receiverKind").value = 'P';
                    setReadOnly(false);
				}
			}
		</script>
		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="EditPaymentReceiver"/>
			<tiles:put name="name" value="Ввод/редактирование получателя"/>
			<tiles:put name="description" value="Ввод/редактирование получателя"/>
			<tiles:put name="buttons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"         value="button.save"/>
				<tiles:put name="commandHelpKey"     value="button.save"/>
				<tiles:put name="bundle"             value="commonBundle"/>
				<tiles:put name="image"              value="iconSm_save.gif"/>
				<tiles:put name="isDefault"            value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
		</tiles:put>
			<tiles:put name="data">
			<tr>
				<td class="Width120 LabelAll">Тип получателя<span class="asterisk">*</span>
				</td>
				<td>
					<c:choose>
						<c:when test="${isNew}">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
                                        <input type="radio" checked="${form.kind=='J' || form.kind=='B'}" name="kindRadio" onclick="selectTypePerson('J')">
                                            Юридическое лицо
                                        </input>
                                    </td>
                                    <td>
                                        <input type="radio" checked="${form.kind=='P'}" name="kindRadio" onclick="selectTypePerson('P')">
                                            Физическое лицо
                                        </input>
									</td>
								</tr>
							</table>
						</c:when>
						<c:when test="${form.kind=='J'}">
							Юридическое лицо
						</c:when>
                        <c:when test="${form.kind=='P'}">
                            Физическое лицо
                        </c:when>
 					</c:choose>
					&nbsp;
                    <html:hidden property="kind" styleId="receiverKind"/>
                    <c:if test="${hasBillingAcccess}">
                        <html:hidden property="field(serviceProviderKey)" styleId="receiverServiceProviderKey"/>
                    </c:if>
				</td>
			</tr>
		    <tr style="height:20px">
		        <td class="Width120 LabelAll">
                   <bean:message key="label.receiver.name" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
			    <td>
				    <html:text property="field(name)" styleId="field(name)" size="80"/>
                    <c:if test="${hasBillingAcccess}">
                        <input id="providerButton" type="button" class="buttWhite smButt" style="height:18px;width:18;display:none;" onclick="javascript:openProvidersDictionary(setProviderInfo);" value="..."/>
 			        </c:if>
                </td>
			</tr>
            <tr style="height:20px">
		        <td class="Width120 LabelAll">
                   <bean:message key="label.receiver.alias" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
			    <td>
				    <html:text property="field(alias)" size="80"/>
			    </td>
			</tr>
			<tr>
			    <td class="Width120 LabelAll">
                    <bean:message key="label.receiver.inn" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
				<td>
					<html:text property="field(INN)" styleId="receiverINN" size="24" maxlength="12"/>
				</td>
			</tr>
			<tr>
		        <td class="Width120 LabelAll">
                   <bean:message key="label.receiver.account" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
				<td>
					<html:text property="field(account)" styleId="field(account)" size="24" maxlength="20"/>
				</td>
			</tr>
            <tr>
                <td class="Width120 LabelAll">Статус</td>
                <td>
                    <c:choose>
                        <c:when test="${form.state=='INITAL'}">
                            Введен
                        </c:when>
                        <c:when test="${form.state=='ACTIVE'}">
                            Активный
                        </c:when>
                    </c:choose>                    
                </td>
            </tr>
			<tr>
		     <td colspan="2" style="text-align:left;" class="pmntInfAreaTitle">
               &nbsp;<bean:message key="label.receiver.bank.title" bundle="paymentsBundle"/><span class="asterisk">*</span>
            </td>
			</tr>
			<tr>
		        <td class="Width120 LabelAll">
                    <bean:message key="label.receiver.bank.name" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
				<td>
					<html:text property="field(bankName)" size="80" styleId="field(bankName)"/>
					<input id="bankButton" type="button" class="buttWhite smButt" style="height:18px;width:18;" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('field(bankName)'),getFieldValue('field(bankCode)'));" value="..."/>
				</td>
			</tr>
			<tr>
			    <td class="Width120 LabelAll">
                    <bean:message key="label.receiver.bank.bic" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
				<td>
					<html:text property="field(bankCode)" styleId="field(bankCode)" size="24" maxlength="9"/>
				</td>
			</tr>
			<tr>
			    <td class="Width120 LabelAll">
                  <bean:message key="label.receiver.bank.corAccount" bundle="paymentsBundle"/><span class="asterisk">*</span>
                </td>
				<td>
					<html:text property="field(correspondentAccount)" styleId="field(correspondentAccount)" size="24" maxlength="20"/>
				</td>
		    </tr>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>
	<c:if test="${!isNew}">
		<html:hidden property="id"/>
	</c:if>
	</tiles:put>

</tiles:insert>
</html:form>