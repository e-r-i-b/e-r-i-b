<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 27.03.2007
  Time: 9:44:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/certification/certificate/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="CertificateList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listCert.title" bundle="certificationBundle"/>
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
			<tiles:put name="label" value="label.startDate"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="startDate"/>
			<tiles:put name="template" value="DATE_TEMPLATE"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.endDate"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="endDate"/>
			<tiles:put name="template" value="DATE_TEMPLATE"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="firstName"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.pinNumber"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="pinNumber"/>
		</tiles:insert>
		<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.status"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(status)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="A">Активный</html:option>
					<html:option value="N">Неактивный</html:option>
					<html:option value="E">Истек</html:option>
					<html:option value="B">Заблокирован</html:option>
				</html:select>
			</tiles:put>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="patrName"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="menu" type="string"></tiles:put>
    <tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="CertificationList"/>
        <tiles:put name="buttons">
            <tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.activate"/>
				<tiles:put name="commandHelpKey" value="button.activate.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
 				<tiles:put name="validationFunction">
			        function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите сертификаты для активации');
                    }
				</tiles:put>
				<tiles:put name="confirmText"    value="Активировать выбранные сертификаты?"/>
			</tiles:insert>
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.block"/>
				<tiles:put name="commandHelpKey" value="button.block.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
 				<tiles:put name="validationFunction">
                     function()
                     {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите сертификаты для блокирования');
                     }
				</tiles:put>
				<tiles:put name="confirmText"    value="Заблокировать выбранные сертификаты?"/>
			</tiles:insert>
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.remove"/>
				<tiles:put name="commandHelpKey" value="button.remove.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
 				<tiles:put name="validationFunction">
                     function()
                     {
                         checkIfOneItem("selectedIds");
                         return checkSelection('selectedIds', 'Выберите сертификаты для удаления');
                     }
				</tiles:put>
				<tiles:put name="confirmText"    value="Удалить выбранные сертификаты?"/>
			</tiles:insert>
        </tiles:put>
		<tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" bundle="certificationBundle">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="certOwn" value="${listElement}" />
	            <c:set var="status" value="${certOwn.status}" />
	            <c:set var="login" value="${certOwn.owner}" />
	            <c:set var="cert" value="${certOwn.certificate}" />

                <sl:collectionItem title="label.id" name="cert" property="id">
                    <sl:collectionItemParam
                            id="action"
                            value="/certification/certificate/edit.do?id=${certOwn.id}"
                            condition="${phiz:impliesService('CertDemandControl')}"/>
                </sl:collectionItem>
                <sl:collectionItem title="label.FIO" name="cert" property="name"/>
                <sl:collectionItem title="label.startDate">
                    <sl:collectionItemParam id="value" condition="${not empty(certOwn.startDate)}">
                        <c:if test="${not empty certOwn}">
                            &nbsp;<bean:write name="certOwn" property="startDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                        </c:if>
                    </sl:collectionItemParam>
                </sl:collectionItem>
                <sl:collectionItem title="label.endDate">
                    <c:if test="${not empty certOwn}">
                        &nbsp;<bean:write name="certOwn" property="endDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.pinNumber" name="login" property="userId"/>
                <sl:collectionItem title="label.status">
                    <sl:collectionItemParam id="value" value="Истек"  condition="${status=='E'}"/>
                    <sl:collectionItemParam id="value" value="Активный" condition="${status=='A'}"/>
                    <sl:collectionItemParam id="value" value="Неактивный" condition="${status=='N'}"/>
                    <sl:collectionItemParam id="value" value="Заблокирован" condition="${status=='B'}"/>
                </sl:collectionItem>
            </sl:collection>
		</tiles:put>
        <tiles:put name="isEmpty" value="${empty ListCertificateForm.data}"/>
        <tiles:put name="emptyMessage" value="Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!"/>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>