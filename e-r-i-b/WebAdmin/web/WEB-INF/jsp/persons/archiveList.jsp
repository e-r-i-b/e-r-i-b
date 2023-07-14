<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 27.10.2006
  Time: 9:08:28
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

<% pageContext.getRequest().setAttribute("mode","Users");%>
<html:form action="/persons/archiveList">
<tiles:insert definition="personList">
	<tiles:put name="submenu" type="string" value="PersonArchive"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listArchive.title" bundle="personsBundle"/>
    </tiles:put>
<tiles:put name="filter" type="string">
   <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.surName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="surName"/>
	</tiles:insert>
   <tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.documentType"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(documentType)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="REGULAR_PASSPORT_RF">Общегражданский паспорт РФ</html:option>
					<html:option value="MILITARY_IDCARD">Удостоверение личности военнослужащего</html:option>
					<html:option value="SEAMEN_PASSPORT">Паспорт моряка</html:option>
					<html:option value="RESIDENTIAL_PERMIT_RF">Вид на жительство РФ</html:option>
					<html:option value="FOREIGN_PASSPORT_RF">Заграничный паспорт РФ</html:option>
					<html:option value="OTHER">Иной документ</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
  <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.pinEnvelopeNumber"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
	        <tiles:put name="name" value="pinEnvelopeNumber"/>
	        <tiles:put name="maxlength" value="16"/>
	</tiles:insert>
   <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="firstName"/>
	</tiles:insert>
   <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.documentSeries"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
	        <tiles:put name="name" value="documentSeries"/>
	        <tiles:put name="maxlength" value="16"/>
	</tiles:insert>
   <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.agreementNumber"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="agreementNumber"/>
	        <tiles:put name="maxlength" value="16"/>
	</tiles:insert>
   <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
	        <tiles:put name="name" value="patrName"/>
	</tiles:insert>
   <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.documentNumber"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="documentNumber"/>
	        <tiles:put name="maxlength" value="16"/>
	</tiles:insert>
        <script type="text/javascript">
	function initTemplates()
	{
        //setInputTemplate('passportSeries', PASSPORT_SERIES_TEMPLATE);
        //setInputTemplate('passportNumber', PASSPORT_NUMBER_TEMPLATE);
      }

	function submit(event){
		var frm = window.opener.document.forms.item(0);
		frm.action = '';
		frm.submit();
	}
      initTemplates();
     </script>
   </tiles:put>
       <tiles:put name="data" type="string">
       <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="archivPersonList"/>
		<tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" bundle="personsBundle">
                <c:set var="person" value="${listElement[2]}" />
			    <c:set var="login"  value="${listElement[3]}" />
		    	<c:set var="simpleScheme" value="${listElement[4]}"/>
			    <c:set var="secureScheme" value="${listElement[4]}"/>
			    <c:set var="activeDocument" value="${listElement[6]}"/>
			    <c:set var="status" value="${person.status}"/>

                <sl:collectionItem title="label.FIO">
                    <c:if test="${not empty person}">
                        <bean:write name="person" property="surName"/>&nbsp;
                        <bean:write name="person" property="firstName"/>&nbsp;
					    <bean:write name="person" property="patrName"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="ПИН-конверт" name="login" property="userId"/>
                <sl:collectionItem title="Номер договора" name="person" property="agreementNumber"/>
                <sl:collectionItem title="Документ">
                    <nobr>
                        <c:if test="${not empty(activeDocument)}">
                            <c:choose>
                                <c:when  test="${(activeDocument.documentType == 'REGULAR_PASSPORT_RF')}">
                                    <nobr>Общегражданский паспорт РФ</nobr>
                                </c:when>
                                <c:when  test="${(activeDocument.documentType == 'MILITARY_IDCARD')}">
                                    <nobr>Удостоверение личности военнослужащего</nobr>
                                </c:when>
                                <c:when  test="${(activeDocument.documentType == 'SEAMEN_PASSPORT')}">
                                    <nobr>Паспорт моряка</nobr>
                                </c:when>
                                <c:when  test="${(activeDocument.documentType == 'RESIDENTIAL_PERMIT_RF')}">
                                    <nobr>Вид на жительство РФ</nobr>
                                </c:when>
                                <c:when  test="${(activeDocument.documentType == 'FOREIGN_PASSPORT_RF')}">
                                    <nobr>Заграничный паспорт РФ</nobr>
                                </c:when>
                                <c:when  test="${(activeDocument.documentType == 'OTHER')}">
                                   <nobr>
                                       <c:if test="${activeDocument.documentType!=null}">
                                           <bean:write name="activeDocument" property="documentType"/>
                                       </c:if>
                                   </nobr>
                                </c:when>
                           </c:choose>
                           &nbsp;
                        </c:if>
                        <c:if test="${activeDocument.documentSeries!=null}">
                            <bean:write name="activeDocument" property="documentSeries"/>&nbsp;
                        </c:if>
                        <c:if test="${activeDocument.documentNumber!=null}">
                            <bean:write name="activeDocument" property="documentNumber"/>&nbsp;
                        </c:if>
                    </nobr>
                </sl:collectionItem>
                <sl:collectionItem title="Схема прав доступа" styleClass="errorText" >
                    <c:choose>
						<c:when test="${empty simpleScheme}">
							<span class="errorText">Нет&nbsp;схемы&nbsp;прав</span>
						</c:when>
						<c:when test="${simpleScheme.name=='personal'}">
							<span class="errorText">Индивидуальные&nbsp;права</span>
						</c:when>
						<c:otherwise>
							<nobr><bean:write name="simpleScheme" property="name"/></nobr>
						</c:otherwise>
					</c:choose>
					<br/>
					<c:choose>
					    <c:when test="${empty secureScheme}">
							<span class="errorText">Нет&nbsp;схемы&nbsp;прав</span>
						</c:when>
						<c:when test="${secureScheme.name=='personal'}">
							<span class="errorText">Индивидуальные&nbsp;права</span>
						</c:when>
						<c:otherwise>
							<nobr><bean:write name="secureScheme" property="name"/></nobr>
						</c:otherwise>
					</c:choose>
                </sl:collectionItem>                   
            </sl:collection>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty ListPersonsForm.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!"/>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
