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
������ ��� ��������.

	id            - id �������, ��� ����, ����� ������ � ������ ��������� ����������
	text          - �������� �������
	buttons       - ������ �������
	grid          - ���������� ������� (������ ���������� � ������� �����)
	head          - ��������� ������� (� ������, ����� ������ ������������� �������)
	data          - ���������� ������� (������ ������������� �������)
	emptyMessage  - ���������, ������� ��������� � ������ isEmpty = true
	searchMessage - ���������, ������� ���������, ���� ������� � ������ ������ �������������� ������ ��� ������� ������.
	buttonsPos    - ����������������� ������ (�� ��������� top; ����������� ������� top, infBottom)
	settingsBeforeInf - ������, �������������� �� �������� ������� ��� ���������� 
	createEmpty   - ��������� �� ������ �������
    showButtons - ���������� �� ������, ���� � ������� ������ ���
	������ isEmpty       - ������� ��������� ���������� � ������� ��� ����, ����� �� ������. �� ��������� ������������.

	<%-- TODO <-- ����� ���������� ������� 15941 ������ buttonsPos, cleanText--%>
    <%-- TODO <-- �������� ����������� ������ ��������� �� ��������� ����� --%>
    <%-- TODO <-- ������� ����������� ������ helpInf, btmHelp --%>

<%-- TODO <-- ������ isEmpty, ����������� �������� ������ ������ xslt-������� ������ � xslt --%>
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

            <%-- TODO ---> ������ ����� �������� ���� ������� �� ����� --%>
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
                    ��&nbsp;�������&nbsp;��&nbsp;������&nbsp;���������, ����������������&nbsp;���������&nbsp;�������!
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
                <!--������ � ������ ���������� ����� �� ���� �.�. ������� ������ �� ��������-->
                <script type="text/javascript">hideTitle("${id}");</script>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
