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
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="personList">
	<tiles:put name="submenu" type="string" value="PersonArchive"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listArchive.title" bundle="personsBundle"/>
    </tiles:put>
<tiles:put name="filter" type="string">
    <c:set var="colCount" value="3" scope="request"/>

    <%--  row 1 --%>
    <tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="fio"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
	</tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.personId"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="loginId"/>
        <tiles:put name="maxlength" value="16"/>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.agreementNumber"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="agreementNumber"/>
        <tiles:put name="maxlength" value="16"/>
    </tiles:insert>

    <%-- row 2 --%>
    <tiles:insert definition="filter2TextField" flush="false">
        <tiles:put name="label" value="label.document"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name"   value="documentSeries"/>
        <tiles:put name="size"   value="5"/>
        <tiles:put name="maxlength"  value="16"/>
        <tiles:put name="isDefault" value="Серия"/>
        <tiles:put name="name2"   value="documentNumber"/>
        <tiles:put name="size2"   value="10"/>
        <tiles:put name="maxlength2"  value="16"/>
        <tiles:put name="default2" value="Номер"/>
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
           <script type="text/javascript">
               <c:if test="${form.fromStart}">
                  //показываем фильтр при старте
                  switchFilter(this);
               </c:if>
           </script>
       <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="archivPersonList"/>
		<tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" bundle="personsBundle">
                <c:set var="person" value="${listElement[0]}" />
			    <c:set var="login"  value="${listElement[1]}" />
		    	<c:set var="simpleScheme" value="${listElement[2]}"/>
			    <c:set var="activeDocument" value="${listElement[3]}"/>
			    <c:set var="status" value="${person.status}"/>

                <sl:collectionItem title="label.FIO">
                    <c:if test="${not empty person}">
                        <bean:write name="person" property="surName"/>&nbsp;
                        <bean:write name="person" property="firstName"/>&nbsp;
					    <bean:write name="person" property="patrName"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="Логин" name="login" property="userId"/>
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
                </sl:collectionItem>
            </sl:collection>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty ListPersonsForm.data}"/>
        <tiles:put name="emptyMessage">
            <c:choose>
                <c:when test="${form.fromStart}">
                    Для поиска клиентов в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                </c:when>
                <c:otherwise>
                    Не найдено ни одного клиента, соответствующего заданному фильтру!
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
