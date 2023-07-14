<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>


<html:form action="/messageTranslate/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="logMain">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:choose>
        <c:when test="${form.isCSA}">
            <c:set var="submenu" value="CSAMessageDictionary"/>
        </c:when>
        <c:otherwise>
            <c:set var="submenu" value="SettingMessageTranslate"/>
        </c:otherwise>
    </c:choose>
	<tiles:put name="submenu" type="string" value="${submenu}"/>
    <tiles:put name="pageTitle" type="string"><bean:message key="label.form.list.name" bundle="messagetranslateBundle"/></tiles:put>
    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.add"/>
			<tiles:put name="commandHelpKey" value="button.add.help"/>
			<tiles:put name="bundle"  value="messagetranslateBundle"/>
			<tiles:put name="image"   value=""/>
	        <tiles:put name="action"  value="/messageTranslate/edit.do?isCSA=${form.isCSA}"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </tiles:put>

    <tiles:put name="filter" type="string">
        <c:set var="colCount" value="3" scope="request"/>
        <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.code"/>
                <tiles:put name="bundle" value="messagetranslateBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="code"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="256"/>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.isNew"/>
            <tiles:put name="bundle" value="messagetranslateBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <html:checkbox property="filter(isNew)"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.type"/>
                <tiles:put name="bundle" value="messagetranslateBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(type)" styleClass="select">
                        <html:option value="">Все</html:option>
                        <html:option value="R">Запрос</html:option>
                        <html:option value="A">Ответ</html:option>
                    </html:select>
                </tiles:put>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
          <script type="text/javascript">
              function edit()
              {
                    checkIfOneItem("selectedIds");
                    if (!checkOneSelection("selectedIds", "Выберите одно сообщение"))
			            return;

                    var addUrl = "${phiz:calculateActionURL(pageContext,'/messageTranslate/edit')}";
			        var id = getRadioValue(document.getElementsByName("selectedIds"));
			        window.location = addUrl + "?id=" + id + "&isCSA=${form.isCSA}";
              }
          </script>
          <tiles:insert definition="tableTemplate" flush="false">
               <tiles:put name="id" value="messageTranslateList"/>
               <tiles:put name="buttons">
                   <tiles:insert definition="clientButton" flush="false">
                       <tiles:put name="commandTextKey" value="button.edit"/>
                       <tiles:put name="commandHelpKey" value="button.edit.help"/>
                       <tiles:put name="bundle" value="messagetranslateBundle"/>
                       <tiles:put name="onclick" value='edit();'/>
                   </tiles:insert>
	               <tiles:insert definition="commandButton" flush="false">
	               	    <tiles:put name="commandKey"     value="button.remove"/>
	               	    <tiles:put name="commandHelpKey" value="button.remove.help"/>
	               	    <tiles:put name="bundle"         value="messagetranslateBundle"/>
	               	    <tiles:put name="validationFunction">
                           function()
                           {
                               checkIfOneItem("selectedIds");
                               return checkSelection("selectedIds", "Выберите сообщения");
                           }
	               	    </tiles:put>
	               	    <tiles:put name="confirmText"    value="Удалить выбранные сообщения?"/>
	               </tiles:insert>
               </tiles:put>
               <tiles:put name="grid">
                   <sl:collection id="listElement" model="list" property="data">
                       <sl:collectionParam id="selectType" value="checkbox"/>
                       <sl:collectionParam id="selectName" value="selectedIds"/>
                       <sl:collectionParam id="selectProperty" value="id"/>

                       <sl:collectionItem title="Название запроса/ответа" name="listElement" property="code"/>
                       <sl:collectionItem title="Комментарий" name="listElement" property="translate"/>
                       <sl:collectionItem title="Тип сообщения">
                           <c:set var="typeMessage">${listElement.type}</c:set>
                           <c:choose>
                               <c:when test="${typeMessage eq 'ANSWER' || typeMessage eq 'A'}">
                                    Ответ
                               </c:when>
                               <c:when test="${typeMessage eq 'REQUEST' || typeMessage eq 'R'}">
                                   Запрос
                               </c:when>
                               <c:otherwise>
                                   &nbsp;
                               </c:otherwise>
                           </c:choose>
                       </sl:collectionItem>

                       <c:set var="isNewLabel"><bean:message key="label.isNew" bundle="messagetranslateBundle"/></c:set>
                       <sl:collectionItem title="${isNewLabel}">
                           <c:set var="isNew" value="${listElement.isNew}"/>
                           <c:if test="${isNew eq true}">
                               <div style="text-align: center"><bean:message key="label.isNew.symbolic" bundle="messagetranslateBundle"/></div>
                           </c:if>
                       </sl:collectionItem>

                       <c:set var="logTypeLabel"><bean:message key="label.logType" bundle="messagetranslateBundle"/></c:set>
                       <sl:collectionItem title="${logTypeLabel}">
                           <c:set var="logType">${listElement.logType}</c:set>
                           <c:choose>
                               <c:when test="${logType eq 'financial'}">
                                   <bean:message key="label.financial" bundle="messagetranslateBundle"/>
                               </c:when>
                               <c:otherwise>
                                   <bean:message key="label.nonFinancial" bundle="messagetranslateBundle"/>
                               </c:otherwise>
                           </c:choose>
                       </sl:collectionItem>
                   </sl:collection>
               </tiles:put>

               <tiles:put name="isEmpty" value="${empty MessageTranslateListForm.data}"/>
               <tiles:put name="emptyMessage" value="Не найдено ни одного сообщения!"/>
           </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>    