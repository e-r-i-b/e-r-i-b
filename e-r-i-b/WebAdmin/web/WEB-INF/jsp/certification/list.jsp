<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 20.11.2006
  Time: 15:26:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/certification/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listCertDemand.title" bundle="certificationBundle"/>
    </tiles:put>
  	<tiles:put name="menu" type="string">
	</tiles:put>
	<!-- фильтр   -->
	<tiles:put name="filter" type="string">
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.surName"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="surName"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="firstName"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="patrName"/>
		</tiles:insert>
		<tiles:insert definition="filterDataSpan" flush="false">
			<tiles:put name="label" value="label.date"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="Date"/>
			<tiles:put name="template" value="DATE_TEMPLATE"/>
		</tiles:insert>
		<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.status"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(status)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="S">Отправлен</html:option>
					<html:option value="P">Обрабатывается</html:option>
					<html:option value="G">Сертификат выдан</html:option>
					<html:option value="R">Отказан</html:option>
					<html:option value="I">Сертификат установлен</html:option>
					<html:option value="D">Сертификат удален</html:option>
				</html:select>
			</tiles:put>
		</tiles:insert>
		<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.signed"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(signed)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="1">Да</html:option>
					<html:option value="0">Нет</html:option>
				</html:select>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
    <tiles:put name="data" type="string">

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="certList"/>
        <tiles:put name="buttons">
             <script type="text/javascript">
                var addUrl = "${phiz:calculateActionURL(pageContext,'/certification/refuse')}";
                function doRefuse()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkOneSelection("selectedIds", "Выберите один календарь!"))
                        return;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = addUrl + "?id=" + id;
                }
             </script>
             <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.refuse"/>
				<tiles:put name="commandHelpKey" value="button.refuse.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
                <tiles:put name="onclick"        value="doRefuse();"/>
			</tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" bundle="certificationBundle"selectBean="demand">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="demand" value="${listElement[0]}" />
                <c:set var="person" value="${listElement[1]}" />
                <c:set var="status" value="${demand.status}" />

                <sl:collectionItem title="label.id" value="${demand.certDemandCryptoId}">
                    <c:if test="${demand.signed}">
						<img valign="center" src="${imagePath}/certificationSignedsm.gif" width="14px" height="14px" alt="" border="0"/>
					</c:if>
                    <sl:collectionItemParam
                            id="action"
                            value="/certification/edit.do?id=${demand.id}"
                            />
                </sl:collectionItem>
                <sl:collectionItem title="label.FIO">
                    <c:if test="${not empty person}">
                        &nbsp;<bean:write name="person" property="surName"/>
                        &nbsp;<bean:write name="person" property="firstName"/>
                        &nbsp;<bean:write name="person" property="patrName"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.date">
                    <c:if test="${not empty demand}">
                        &nbsp;<bean:write name="demand" property="issueDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.status">
                    <sl:collectionItemParam id="value" value="Отправлен" condition="${status=='S'}"/>
                    <sl:collectionItemParam id="value" value="Обрабатывается" condition="${status=='P'}"/>
                    <sl:collectionItemParam id="value" value="Сертификат выдан" condition="${status=='G'}"/>
                    <sl:collectionItemParam id="value" value="Отказан" condition="${status=='R'}"/>
                    <sl:collectionItemParam id="value" value="Сертификат установлен" condition="${status=='I'}"/>
                    <sl:collectionItemParam id="value" value="Сертификат удален" condition="${status=='D'}"/>
                </sl:collectionItem>
            </sl:collection>
    </tiles:put>

	<tiles:put name="isEmpty" value="${empty ShowCertDemandForm.data}"/>
	<tiles:put name="emptyMessage" value="Нет ни одного запроса."/>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>