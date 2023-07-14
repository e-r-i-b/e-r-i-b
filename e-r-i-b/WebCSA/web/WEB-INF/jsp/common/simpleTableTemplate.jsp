<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%--
  Шаблон для простой таблички

  id            - id таблицы, для того, чтобы скрыть в случае отсутсвия информации
  grid          - содержание таблицы (данные передаются с помощью грида)
  emptyMessage  - сообщение, которое выводится в случае isEmpty = true
  isEmpty       - признак отсутсвия информации в таблице для того, чтобы ее скрыть. По умолчанию показывается.
  hideable      - если true то список можно скрывать (по умолчанию false)
  productType   - тип продукта (для реализации скрывать/отображать операции)
  ajaxDataURL   - url адресс с данными которые необходимо загрузить
  show          - признак скрывать/отображать операции
--%>
<c:if test="${not empty show and not show}">
    <c:set var="style" value="display: none"/>
</c:if>

<c:if test="${hideable}">
    <c:set var="productId" value="${id}"/>
    <c:set var="id" value="${productType}_${productId}"/>
</c:if>

<%-- Счетчик компоненты --%>
<c:if test="${empty simpleTableCounter}">
    <c:set var="simpleTableCounter" value="0" scope="request"/>
</c:if>

<%-- Непорядок если отсутствует id. При необходимости генерируем --%>
<c:if test="${empty id || id == ''}">
    <c:set var="id" value="simpleTable${simpleTableCounter}"/>
     <c:set var="simpleTableCounter" value="${simpleTableCounter+1}" scope="request"/>
</c:if>

<c:set var="gridData">
    <div id="${id}" class="simpleTable" style = "${style}">
        <c:choose>
            <c:when test="${isEmpty}">
                <div class="emptyText">
                    <tiles:insert definition="roundBorderLight" flush="false">
                        <tiles:put name="color" value="greenBold"/>
                        <tiles:put name="data">
                            ${emptyMessage}
                        </tiles:put>
                    </tiles:insert>
                </div>

            </c:when>
            <c:otherwise>
                <div class="grid">
                    <c:choose>
                        <c:when test="${(empty grid || grid == '') && ajaxDataURL != ''}">
                            <img src="${imagePath}/ajaxLoader.gif" alt="Loading..." title="Loading..." class="abstractLoader"/>
                        </c:when>
                        <c:otherwise>${grid}</c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</c:set>

<%-- Функция корая загрузит данные в таблицу --%>
<c:set var = "ajaxFunction" value = ""/>
<c:if test="${not empty ajaxDataURL && ajaxDataURL != ''}">
    <c:set var = "ajaxFunction">loadAjaxDataOnce('${ajaxDataURL}', '${id}')</c:set>
</c:if>

<c:choose>
    <c:when test="${hideable}">
                ${gridData}

                    <c:set var="url" value="${csa:calculateActionURL(pageContext,'/private/async/product/display/change')}"/>
                    <c:set var="relText" value="Свернуть"/>
                    <c:set var="mainText" value="Развернуть"/>
                    <c:set var="aditionalClass" value=""/>
                    <c:if test="${empty show or show}">
                        <c:set var="relText" value="Развернуть"/>
                        <c:set var="mainText" value="Свернуть"/>
                        <c:set var="aditionalClass" value="hide"/>
                    </c:if>
                <%-- класс hideable-element необходим для работы скриптов. Которые по данному признаку находят элемент. --%>
                <a class="hide-text text-gray hideable-element ${aditionalClass}" onclick="showHideOperations(this, '${productId}', '${productType}', '${url}', function(){${ajaxFunction}; return false;}); cancelBubbling(event); return false;" rel="${relText}">${mainText}</a>
    </c:when>
    <c:otherwise>
        ${gridData}
    </c:otherwise>
</c:choose>

<c:if test="${ajaxFunction != '' && (!hideable || hideable && show)}">
    <script type="text/javascript">
        doOnLoad (function(){ ${ajaxFunction}});
    </script>
</c:if>