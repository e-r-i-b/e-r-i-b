<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%--
  ������������ ������ � tablePropertiesConfigEdit. ��� ����� ������ � java-script ��� ���������� �������.

  bundle  - bundle ��� ������ ��������/�������
  tableHead  - ��������� �������
  addScript  - �������, ����������� ������
  editScript - ������� �������������� ������
  removeScript - �������, ��������� ������
  propertyId   - id �������
  tableTitle - �������� �������
  showCheckbox - ���������� �� checkbox ����� � ���������� �� �������� ���������� (������ ��� �������������� ������� �� ������������)
  fieldValue - ���� ���� � ����������
--%>
<div>
    <c:if test="${!showCheckbox}">
        <div id="${propertyId}_ErrorsContainer" class="displayNone" style="width:100%;">
            <div class="floatLeft Width20"><img src="${imagePath}/tabPropertyError.gif"/></div>
            <div id="${propertyId}_Errors"></div>
        </div>
        <div>
            <input type="button"
                   class="buttWhite buttonGrayNew floatRight"
                   id="${propertyId}_RemoveButton"
                    <c:choose>
                        <c:when test="${not empty removeScript}"> onclick="${removeScript}" </c:when>
                        <c:otherwise> onclick="tablePropertiesFactory.getHelper('${propertyId}').remove()" </c:otherwise>
                    </c:choose>
                   value="<bean:message bundle='${bundle}' key='button.remove'/>"/>

            <input type="button"
                   class="buttWhite buttonGrayNew floatRight"
                   id="${propertyId}_EditButton"
                    <c:choose>
                        <c:when test="${not empty editScript}"> onclick="${editScript}" </c:when>
                        <c:otherwise> onclick="tablePropertiesFactory.getHelper('${propertyId}').editRow()" </c:otherwise>
                    </c:choose>
                   value="<bean:message bundle='${bundle}' key='button.edit2'/>"/>

            <input type="button"
                   class="buttWhite buttonGrayNew floatRight "
                   id="${propertyId}_AddButton"
                    <c:choose>
                        <c:when test="${not empty addScript}"> onclick="${addScript}" </c:when>
                        <c:otherwise> onclick="tablePropertiesFactory.getHelper('${propertyId}').createRow()" </c:otherwise>
                    </c:choose>
                   value="<bean:message bundle='${bundle}' key='button.add'/>"/>
        </div>
    </c:if>
    <div class="clear"></div>
    <c:if test="${showCheckbox}">
        <html:checkbox property="selectedProperties" value="${fieldValue}" styleId="replicationSelect"/>
        <label for="replicationSelect" id="replicationSelectLabel"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
    </c:if>
    <div class="smallDynamicGrid" id="${propertyId}_Div" style="display:none;">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="${propertyId}_Table"/>
            <tiles:put name="description" >
                ${tableTitle}
            </tiles:put>
            <tiles:put name="head">
                ${tableHead}
            </tiles:put>
            <tiles:put name="data">
                <div></div>
            </tiles:put>
        </tiles:insert>
    </div>
</div>
