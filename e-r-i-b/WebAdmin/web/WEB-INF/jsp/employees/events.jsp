<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/employees/events">
	<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>

<% ServletRequest context = pageContext.getRequest();
	context.setAttribute("userMode", "NotifyEvents"); %>

<tiles:insert definition="employeesEventsEdit">

<tiles:put name="submenu" type="string" value="Notification"/>

<tiles:put name="pageTitle" type="string">Настройка оповещений. Подписка на сообщения о событиях</tiles:put>

<!--меню -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush='false'>
		<tiles:put name="commandTextKey" value="button.clear"/>
		<tiles:put name="commandHelpKey" value="button.clear.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="onclick" value="javascript:resetForm(event);"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<!-- данные -->
<tiles:put name="data" type="string">
<input type="hidden" name="parametersNeeded" id="parametersNeeded" value="false"/>
<html:hidden property="employeeId"/>    

<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="certList"/>
    <tiles:put name="buttons">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="bundle" value="commonBundle"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="validationFunction">checkParameters();</tiles:put>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="settingsBeforeInf">
        <table cellpadding="0" cellspacing="0">
        <tr>
            <td class="paperMenu" nowrap="true">
                Присылать сообщения по электронной почте на адрес (E-mail):
            </td>
            <td width="100%">
                <html:text property="emailAddress" size="39"/>
            </td>
        </tr>
        <tr>
            <td class="paperMenu" nowrap="true">Присылать SMS на мобильный телефон номер:</td>
            <td width="100%">
                <html:text property="mobilePhone" size="17" maxlength="16"/>
            </td>
        </tr>
        <tr>
            <td class="paperMenu" nowrap="true">Формат сообщений SMS:</td>
            <td width="100%">
                <html:checkbox property="smsTranslit" value="true" style="border:0"/>
                транслитерация
            </td>
        </tr>
        </table>
    </tiles:put>
    <tiles:put name="head">
            <td width="50%">События</td>
            <td width="60px" nowrap="true">E-mail</td>
            <td width="60px" nowrap="true">SMS</td>
            <td width="50%" nowrap="true">Параметры</td>
    </tiles:put>
	<tiles:put name="data">
	<c:forEach var="distribution" items="${form.distributions}">
		<c:set var="schedulesCount" value="${phiz:size(distribution.schedules)}"/>
		<c:if test="${schedulesCount>0}">
			<tr>
				<c:set var="emailBox" value="false"/>
				<c:set var="smsBox" value="false"/>
				<c:forEach var="template" items="${distribution.templates}">
					<c:set var="channel" value="${template.channel}"/>
					<c:if test="${channel == 'email' || channel == 'emailAttach'}">
						<c:set var="emailBox" value="true"/>
					</c:if>
					<c:if test="${channel == 'sms'}">
						<c:set var="smsBox" value="true"/>
					</c:if>
				</c:forEach>
				<td class="ListItem">${distribution.description}</td>
				<td class="ListItem" align="center">
					<c:if test="${emailBox}">
						<html:checkbox property="emlChecked(${distribution.id})" value="true"
							   style="border:0" onclick="refreshParameters();"/>
					</c:if>
				</td>
				<td class="ListItem" align="center">
                    <c:choose>
					<c:when test="${smsBox}">
						<html:checkbox property="smsChecked(${distribution.id})" value="true"
							   style="border:0" onclick="refreshParameters();"/>
					</c:when>
                    <c:otherwise>
                        &nbsp;
                    </c:otherwise>
                    </c:choose>
				</td>
				<td class="ListItem">
					<!-- distributionId должен быть строковым -->
					<c:set var="distributionId">${distribution.id}</c:set>
					<c:set var="distributionTypeKey" value="${form.typeKeys[distributionId]}"/>
					<c:choose>
						<c:when test="${distributionTypeKey=='schedule'}">
							<html:select property="parameter(${distribution.id})">
								<html:optionsCollection name="distribution" property="schedules"
														value="id" label="description"/>
							</html:select>
						</c:when>
						<c:when test="${distributionTypeKey=='daysTo'}">
							Оповещать за
							<html:text property="parameter(${distribution.id})" size="3" maxlength="3"
									   style="text-align:right"/>
							дней до окончания
						</c:when>
						<c:when test="${distributionTypeKey=='logLevel'}">
							<html:select property="parameter(${distribution.id})">
								<html:option value="1">Критические ошибки</html:option>
								<html:option value="2">Ошибки</html:option>
								<html:option value="3">Предупреждения</html:option>
							</html:select>
						</c:when>
						<c:when test="${distributionTypeKey=='paymentsAndClaims'}">

							<input type="hidden" name="distributionId" id="distributionId" value="(${distribution.id})"/>
							<script type="text/javascript">
								function openPaymentsAndClaimsDictionary()
								{
                                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'private/forms.do')}"/>
									window.open('${url}',
											'PaymentsAndClaims', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
								}
								function getPaymentsAndClaimsInfo()
								{
									var element = document.getElementsByName("parameter(${distribution.id})")[0];
									return element.value;
								}
								function setPaymentsAndClaimsInfo(str)
								{
									setElement("parameter(${distribution.id})", str)
								}
							</script>
							<html:hidden property="parameter(${distribution.id})"/>

							<tiles:insert definition="clientButton" flush="false">
								<tiles:put name="commandTextKey" value="button.call"/>
								<tiles:put name="commandHelpKey" value="button.call.help"/>
								<tiles:put name="bundle" value="notificationsBundle"/>
								<tiles:put name="onclick" value="openPaymentsAndClaimsDictionary()"/>
							</tiles:insert>
						</c:when>
					</c:choose>&nbsp;
				</td>
			</tr>
		</c:if>
	</c:forEach>
</tiles:put>
</tiles:insert>

<script type="text/javascript">

	/*проверим, выбирал ли пользователь параметры для платежей*/
	function checkParameters()
	{
		var id = document.getElementById("distributionId");
		var parameters = document.getElementById("parameter"+id.value.toString());
		var paramsNeeded = document.getElementById("parametersNeeded");
		if (paramsNeeded != null && (paramsNeeded.value == 'true') && (parameters.value == ""))
		{
			alert("Необходимо указать параметры!");
			return false;
		}
		return true;


	}
	function refreshParameters()
	{
		var frm = document.forms.item(0);
		var savedDistributionId = document.getElementById("distributionId").value;
		for (var i = 0; i < frm.elements.length; i++)
		{
			var item = frm.elements.item(i);
			if (item.tagName == "INPUT" && item.type == "checkbox" && (item.name.match("emlChecked") || item.name.match("smsChecked")))
			{
				var distributionId = item.name.replace("emlChecked", "");
				if (document.getElementsByName("parameter" + distributionId).length != 0)
					if (document.getElementsByName("emlChecked" + distributionId)[0].checked ||
					    document.getElementsByName("smsChecked" + distributionId)[0].checked)
					{
						document.getElementsByName("parameter" + distributionId)[0].disabled = false;
						if (savedDistributionId == distributionId)
						{
							var paramNeeded = document.getElementById("parametersNeeded");
							paramNeeded.value = true;
						}
					}
					else
					{
						document.getElementsByName("parameter" + distributionId)[0].disabled = true;
						if (savedDistributionId == distributionId)
						{
							var paramNeeded = document.getElementById("parametersNeeded");
							paramNeeded.value = false;
						}
					}
			}
		}
	}
	refreshParameters();
</script>
</tiles:put>

</tiles:insert>

</html:form>
