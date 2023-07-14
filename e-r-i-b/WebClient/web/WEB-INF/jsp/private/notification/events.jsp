<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/notification/events">
<tiles:insert definition="notifications">
<tiles:put name="submenu" type="string" value="Events"/>
<tiles:put name="pageTitle" type="string">Подписка на оповещения</tiles:put>
<!--меню -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.save"/>
		<tiles:put name="commandHelpKey" value="button.save.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="isDefault" value="true"/>
		<tiles:put name="image" value=""/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false" >
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="onclick" value="resetForm(event)"/>
	</tiles:insert>
</tiles:put>
<!-- данные -->
<tiles:put name="data" type="string">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table cellspacing="4" cellpadding="2" width="100%">
	<tr>
		<td class="paperMenu" nowrap="true">
			Присылать сообщения по электронной почте на адрес (E-mail):     
		</td>
		<td width="100%">
			<html:text property="emailAddress" size="39"/>
		</td>
	</tr>
	<tr>
		<td class="paperMenu" nowrap="true">Присылать сообщения SMS на мобильный телефон номер:</td>
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

<c:set var="lineCount" value="0"/>
<c:forEach var="distribution" items="${form.distributions}">
	<c:forEach var="schedules" items="${distribution.schedules}">
		<c:set var="lineCount" value="${lineCount+1}"/>
	</c:forEach>
</c:forEach>

<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="tableCertClaims"/>
	<tiles:put name="image" value="iconMid_notificationSettings.gif"/>
	<tiles:put name="text"value="Параметры оповещения"/>
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
					<c:choose>
						<c:when test="${emailBox}">
							<html:checkbox property="emlChecked(${distribution.id})" value="true"
								   style="border:0" onchange="refreshParameters()"/>
						</c:when>
						<c:otherwise>&nbsp;</c:otherwise>
					</c:choose>
				</td>
				<td class="ListItem" align="center">
					<c:choose>
						<c:when test="${smsBox}">
							<html:checkbox property="smsChecked(${distribution.id})" value="true"
								   style="border:0" onchange="refreshParameters()"/>
						</c:when>
						<c:otherwise>&nbsp;</c:otherwise>
					</c:choose>
				</td>
				<td class="ListItem" align="center">
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
						<c:when test="${distributionTypeKey=='limit'}">
							<script type="text/javascript">
								function openLimitDictionary()
								{
									var limitValue = document.getElementsByName("parameter(${distribution.id})")[0];
									window.open('../notification/limits.do?paramString='+limitValue.value, 'AccountsCardsLimit',
											"resizable=1,menubar=1,toolbar=1,scrollbars=1");
								}
								function setLimitInfo(str)
								{
									setElement("parameter(${distribution.id})", str);
								}
							</script>
							<html:hidden property="parameter(${distribution.id})"/>
							<tiles:insert definition="clientButton" flush="false">
								<tiles:put name="commandTextKey" value="button.callDictionary"/>
								<tiles:put name="commandHelpKey" value="button.callDictionary"/>
								<tiles:put name="bundle" value="notificationsBundle"/>
								<tiles:put name="onclick" value="openLimitDictionary()"/>
							</tiles:insert>
						</c:when>
						<c:when test="${distributionTypeKey=='AccountsCards'}">
							<script type="text/javascript">
								function openAccountCardsDictionary()
								{
									var hid = document.getElementsByName("parameter(${distribution.id})")[0];
									win=window.open('../accountCardsDict.do?paramString=' + hid.value, 'AccountsCards',
											"resizable=1,menubar=0,toolbar=0,scrollbars=1, height=450, width=820");
									win.moveTo(screen.width/2-410, screen.height/2-225);
								}
								function setAccountCardsInfo(str)
								{
									setElement("parameter(${distribution.id})", str)
								}
							</script>
							<html:hidden property="parameter(${distribution.id})"/>
							<tiles:insert definition="clientButton" flush="false">
								<tiles:put name="commandTextKey" value="button.callDictionary"/>
								<tiles:put name="commandHelpKey" value="button.callDictionary"/>
								<tiles:put name="bundle" value="notificationsBundle"/>
								<tiles:put name="onclick" value="openAccountCardsDictionary()"/>
							</tiles:insert>
						</c:when>
						<c:when test="${distributionTypeKey=='accounts'}">
							<script type="text/javascript">
								function openAccountDictionary()
								{
									var hid = document.getElementsByName("parameter(${distribution.id})")[0];
									window.open('../accountDict.do' + parseAccounts(hid.value), 'Accounts',
											"resizable=1,menubar=1,toolbar=1,scrollbars=1");
								}
								function setAccountInfo(str)
								{
									setElement("parameter(${distribution.id})", str)
								}
								function parseAccounts(str)
								{
									var curPos = 0;
									var oldPos = 0;
									var res = "";
									if (str == null)return "";
									while ((curPos = str.indexOf(';', oldPos)) != -1)
									{
										var account = str.substr(oldPos, curPos - oldPos);
										if (account == "*")
											return '?selectedIds=*'
										if (oldPos == 0)
											res = '?selectedIds=' + account;
										else
											res += ('&selectedIds=' + account);
										oldPos = curPos + 1;
									}
									return res;
								}
							</script>
							<html:hidden property="parameter(${distribution.id})"/>
							<tiles:insert definition="clientButton" flush="false">
								<tiles:put name="commandTextKey" value="button.callDictionary"/>
								<tiles:put name="commandHelpKey" value="button.callDictionary"/>
								<tiles:put name="bundle" value="notificationsBundle"/>
								<tiles:put name="onclick" value="openAccountDictionary()"/>
							</tiles:insert>								
						</c:when>
						<c:otherwise>&nbsp;</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</tiles:put>
	<tiles:put name="isEmpty" value="${lineCount lt 0}"/>
	<tiles:put name="emptyMessage" value="Нет событий для рассылки оповещений!"/>
</tiles:insert>

<script type="text/javascript">
	function refreshParameters()
	{
		var frm = document.forms.item(0);
		for (var i = 0; i < frm.elements.length; i++)
		{
			var item = frm.elements.item(i);
			if (item.tagName == "INPUT" && item.type == "checkbox" && item.name.match("emlChecked"))
			{
				var distributionId = item.name.replace("emlChecked", "");

				if (document.getElementsByName("parameter" + distributionId).length != 0)
					if (document.getElementsByName("emlChecked" + distributionId)[0].checked ||
					   (document.getElementsByName("smsChecked" + distributionId)[0]!=null &&
					    document.getElementsByName("smsChecked" + distributionId)[0].checked))
						document.getElementsByName("parameter" + distributionId)[0].disabled = false;
					else
						document.getElementsByName("parameter" + distributionId)[0].disabled = true;
			}
		}
	}
	refreshParameters();
</script>
</tiles:put>
</tiles:insert>
</html:form>
