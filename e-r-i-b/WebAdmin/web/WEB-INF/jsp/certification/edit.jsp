<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 20.11.2006
  Time: 17:21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/certification/edit" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="editCertDemand.title" bundle="certificationBundle"/>
    </tiles:put>

		<c:set var="form" value="${EditCertDemandForm}"/>
		<c:set var="refresh" value="${form.refresh}"/>
	    <bean:define name="form" property="field(status)" id="status"/>
	<tiles:put name="menu" type="string">
        <nobr>
			<tiles:insert definition="clientButton" flush="false">
				 <tiles:put name="commandTextKey"     value="button.cancel"/>
				 <tiles:put name="commandHelpKey" value="button.cancel.help"/>
				 <tiles:put name="bundle"         value="certificationBundle"/>
				 <tiles:put name="action"         value="/certification/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		<nobr>
	 </tiles:put>
      <tiles:put name="data" type="string">
		<script type="text/javascript">
				var refresh = ${refresh};
				if(refresh)
					new CommandButton('button.download').click();
		</script>
		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="certificationClaim"/>
			<tiles:put name="name" value="Просмотр информации о запросе"/>
			<tiles:put name="description" value="Информация по запросу."/>
			<tiles:put name="data">
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.id" bundle="certificationBundle"/></b>
					</td>
					<td>
						<bean:write name="form"  property="field(id)"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.FIO" bundle="certificationBundle"/></b>
					</td>
					<td><bean:write name="form" property="field(surName)"/>&nbsp;
						<bean:write name="form" property="field(firstName)"/>&nbsp;
						<bean:write name="form" property="field(patrName)"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.date" bundle="certificationBundle"/></b>
					</td>
					<td>
						<bean:write name="form" property="field(date)" format="dd.MM.yyyy HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.status" bundle="certificationBundle"/></b>
					</td>
					<td>&nbsp;
						<c:choose>
							<c:when test="${status=='S'}">Отправлен</c:when>
							<c:when test="${status=='P'}">Обрабатывается</c:when>
							<c:when test="${status=='G'}">Сертификат выдан</c:when>
							<c:when test="${status=='R'}">Отказан</c:when>
							<c:when test="${status=='I'}">Сертификат установлен</c:when>
							<c:when test="${status=='D'}">Сертификат удален</c:when>
						</c:choose>
					</td>
				</tr>
				<c:if test="${status=='R'}">
					<tr>
						<td class="Width120 LabelAll" nowrap="true"><b><bean:message key="label.reason" bundle="certificationBundle"/></b></td>
						<td><bean:write name="form" property="field(refuseReason)"/>&nbsp;
						</td>
					</tr>
				</c:if>
				</tiles:put>
				<tiles:put name="buttons">
                     <c:if test="${status=='P' || status=='S'}">
                     <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.upload"/>
                            <tiles:put name="commandHelpKey" value="button.upload.help"/>
                            <tiles:put name="bundle"         value="certificationBundle"/>
                            <tiles:put name="action"         value="/certification/load.do?id=${form.id}"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                             <tiles:put name="commandKey"     value="button.download"/>
                             <tiles:put name="commandHelpKey" value="button.download.help"/>
                             <tiles:put name="bundle"         value="certificationBundle"/>
                        </tiles:insert>
                    </c:if>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
 </tiles:put>
 </tiles:insert>
</html:form>