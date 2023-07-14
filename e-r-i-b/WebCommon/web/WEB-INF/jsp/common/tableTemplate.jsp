<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 16.09.2008
  Time: 17:04:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%--
Шаблон для таблички.

	id            - id таблицы, для того, чтобы скрыть в случае отсутсвия информации
	text          - название таблицы
	buttons       - кнопки таблицы
	grid          - содержание таблицы (данные передаются с помощью грида)
	head          - заголовок таблицы (в случае, когда данные прописываются вручную)
	data          - содержание таблицы (данные прописываются вручную)
	emptyMessage  - сообщение, которое выводится в случае isEmpty = true
	searchMessage - сообщение, которые выводится, если записей в списке больше установленного лимита для данного списка.
	buttonsPos    - месторасположение кнопок (по умолчанию top; реализованы позиции top, infBottom)
	settingsBeforeInf - данные, отображающиеся до основной таблицы под заголовком 
	createEmpty   - создавать ли пустую таблицу
    showButtons - показывать ли кнопки, если в таблице ничего нет
	убрать isEmpty       - признак отсутсвия информации в таблице для того, чтобы ее скрыть. По умолчанию показывается.

	<%-- TODO <-- после реализации запроса 15941 убрать buttonsPos, cleanText--%>
    <%-- TODO <-- повесить зависимость данных элементов на настройки скина --%>
    <%-- TODO <-- сделать облегченную версию helpInf, btmHelp --%>

<%-- TODO <-- убрать isEmpty, возникающие проблемы вывода пустых xslt-списков решать в xslt --%>
        <c:set var="trm" value="${fn:trim(grid)}"/>
        <c:set var="trmData" value="${fn:trim(data)}"/>

<c:set var="showTable" value="${!isEmpty and (not empty trm or not empty trmData)}"/>

<c:set var="tableData">
    <div class="tblNeedTableStyle" <c:if test="${!showTable and createEmpty and !showButtons}">style="display:none"</c:if>>
        <c:choose>
            <c:when test="${oldDesign}">
                <div>
                    <div>
                        <span class="tblTitleText">&laquo;${text}&raquo;</span>
                    </div>
                    <div style="float:right;height:auto;width:auto;overflow:hidden;">
                            ${buttons}
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${buttonsPos == 'top'}">
                    <div>
                        <div class="textTitle">
                            <c:choose>
                                <c:when test="${not empty text}">
                                    ${text}
                                </c:when>
                                <c:otherwise>
                                    &nbsp;
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div style="float:right;">
                            <div class="otherButtonsArea inlineButtonsArea">
                                    ${buttons}
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty description}"><span class="tblDescription">${description}</c:if>
        <div class="clear"></div>
        <div class="autoScroll">
            <table cellpadding="0" cellspacing="0" width="100%" class="tblInfGlobal">
                <c:if test="${not empty settingsBeforeInf}">
                    <tr>
                        <td colspan="4" class="tblSettingsBeforeInf">
                            ${settingsBeforeInf}
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="4">
                        <c:choose>
                            <c:when test="${empty grid}">
                                <table cellpadding="0" cellspacing="0" width="100%" class="standartTable" id="${id}">
                                    <c:if test="${not empty head}">
                                        <tr class="tblInfHeader">
                                                ${head}
                                        </tr>
                                    </c:if>
                                        ${data}
                                </table>
                            </c:when>
                            <c:otherwise>
                                ${grid}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

            <%-- TODO ---> убрать после перевода всех списков на гриды --%>
                <c:if test="${!searchMessage and not empty searchMessage}">
                    <tr>
                        <td colspan="4" class="tblSearchMsg">${searchMessage}</td>
                    </tr>
                </c:if>

                <c:if test="${buttonsPos == 'infBottom'}">
                    <tr>
                        <td colspan="4" class="tblBttnInfBtm">
                            <div style="float:right;">
                                    ${buttons}
                            </div>
                        </td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
</c:set>

<c:set var="messageData">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="redBlock"/>
        <tiles:put name="data">
            <c:choose>
                <c:when test="${not empty emptyMessage}">${emptyMessage}</c:when>
                <c:otherwise>
                    Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;документа, соответствующего&nbsp;заданному&nbsp;фильтру!
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</c:set>

<c:choose>
    <c:when test="${showTable}">
        ${tableData}
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${createEmpty || showButtons}">
                ${tableData}
                ${messageData}
            </c:when>
            <c:otherwise>
                ${messageData}
                <!--Походу в данной реализации этого не надо т.к. таблица вообще не рисуется-->
                <script type="text/javascript">hideTitle("${id}");</script>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
