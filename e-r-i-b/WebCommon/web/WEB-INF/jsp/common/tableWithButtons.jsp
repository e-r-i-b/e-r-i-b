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
  data    - данные таблицы
  bundle  - bundle для кнопок добавить/удалить
  tableHead  - заголовок таблицы
  addScript  - функция, добавляющая строки
  removeScript - фукнция, удаляющая строки
  tableId   - id таблицы
  tableTitle - название таблицы
  divId   - id дива с таблицей
  rowCount - количество строк
  rowCountField - поле, для хранения количества строк
--%>
<c:if test="${rowCount <= 0}"><c:set var="display" value="style='display:none'"/> </c:if>
<tiles:insert definition="clientButton" flush="false">
    <tiles:put name="commandTextKey" value="button.add"/>
    <tiles:put name="commandHelpKey" value="button.add"/>
    <tiles:put name="bundle" value="${bundle}"/>
    <tiles:put name="onclick" value="${addScript}"/>
    <tiles:put name="viewType" value="buttonGrayNew"/>
</tiles:insert>
<div id="${divId}Button" ${display}>
<tiles:insert definition="clientButton" flush="false">
    <tiles:put name="commandTextKey" value="button.remove"/>
    <tiles:put name="commandHelpKey" value="button.remove"/>
    <tiles:put name="bundle" value="${bundle}"/>
    <tiles:put name="onclick" value="${removeScript}"/>
    <tiles:put name="viewType" value="buttonGrayNew"/>
</tiles:insert>
</div>

<div class="smallDynamicGrid" ${display} id="${divId}">
<input type="hidden"  name="fields(${rowCountField})" value="${rowCount}"/>
<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="${tableId}"/>
    <tiles:put name="description" >${tableTitle}</tiles:put>
    <tiles:put name="head">
        ${tableHead}
    </tiles:put>
    <tiles:put name="data">
        ${data}
        <div/>
    </tiles:put>
</tiles:insert>
</div>