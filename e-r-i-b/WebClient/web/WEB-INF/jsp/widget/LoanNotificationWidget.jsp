<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="loanLink" value="${form.widgetLoanLink}"/>
<c:set var="loan" value="${loanLink.loan}"/>
<c:set var="shouldNotify" value="${form.alarm}"/>
<c:set var="loanPaymentUrl"
       value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=LoanPayment')}&loanAccountNumber=${loan.accountNumber}"/>
<c:set var="loanDetailUrl"
           value="${phiz:calculateActionURL(pageContext,'/private/loans/detail.do?id=')}${loanLink.id}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.LoanNotificationWidget"/>
    <tiles:put name="cssClassname" value="LoanNotificationWidget"/>
    <c:choose>
        <c:when test="${shouldNotify}">
            <tiles:put name="borderColor" value="orangeTop"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="borderColor" value="greenTop"/>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty loanLink}">
        <tiles:put name="title">
           <c:out value="${loanLink.name}"/> <b>(�������� ${form.beforeNextPaymentDaysCount} ����)</b>
        </tiles:put>
    </c:if>

    <%-- �����������  --%>
    <tiles:put name="viewPanel">
        <c:choose>
            <c:when test="${not empty loanLink}">
                <jsp:include page="/WEB-INF/jsp-sbrf/loans/annLoanMessageWindow.jsp" flush="false"/>
                <div class="loanProductCard">
                    <tiles:insert definition="productTemplate" flush="false">
                        <%--������������� ���� � �������� � ����������� �� ���� ���������--%>
                        <c:choose>
                            <c:when test="${loan.isAnnuity}">
                                <tiles:put name="img" value="${imagePath}/credit_type/icon_creditAnuitet.png"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="img" value="${imagePath}/credit_type/icon_creditDiffer.png"/>
                            </c:otherwise>
                        </c:choose>
                        <tiles:put name="alt" value="������"/>
                        <tiles:put name="title">
                            <span class="mainProductTitle"><bean:write name="loanLink" property="name"/></span>
                        </tiles:put>
                        <tiles:put name="needClick" value="false"/>
                        <c:if test="${not detailsPage  and loan.state != 'closed'}">
                            <tiles:put name="src" value="${loanDetailUrl}"/>
                        </c:if>
                        <tiles:put name="centerData">
                            <c:if test="${not empty loan.nextPaymentAmount and loan.state != 'overdue'}">
                                    ��������������� ������:
                                <tiles:insert definition="roundedPlate" flush="false">
                                    <tiles:put name="data">
                                        <div class="nextPaymentAmount">
                                            ${phiz:formatAmount(loan.nextPaymentAmount)}
                                        </div>
                                    </tiles:put>
                                    <tiles:put name="color" value="white"/>
                                    <c:if test="${loan.nextPaymentAmount.decimal<0}">
                                        <tiles:put name="color" value="orange"/>
                                    </c:if>
                                    <c:if test="${loan.state != 'closed'}">
                                        <tiles:put name="action">/private/loans/detail.do?id=${loanLink.id}</tiles:put>
                                    </c:if>
                                </tiles:insert>

                                <table class="productDetail">
                                        <tr>
                                            <td>��������:</td>
                                            <td class="value">
                                                <tiles:insert definition="roundedPlate" flush="false">
                                                    <tiles:put name="data">
                                                        ${form.beforeNextPaymentDaysCount}&nbsp;����
                                                    </tiles:put>
                                                    <c:choose>
                                                        <c:when test="${shouldNotify}">
                                                            <tiles:put name="color" value="orange"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tiles:put name="color" value="green"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tiles:insert>
                                            </td>
                                        </tr>
                                </table>
                            </c:if>
                        </tiles:put>
                        <tiles:put name="rightData">
                            <c:set var="loanPaymentUrl"
                                   value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=LoanPayment')}&loanAccountNumber=${loan.accountNumber}"/>
                            <c:set var="paymentUrl" value="${loan.state == 'closed' ? null : loanPaymentUrl}"/>
                            <c:set var="loanInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/loans/info.do?id=')}${loanLink.id}"/>
                            <c:set var="graphicUrl" value="${loan.state == 'closed' ? null : loanInfoUrl}"/>
                            <%--� ������ ������� ���������--%>
                            <div panel="wide" class="panelWide">
                                <tiles:insert definition="listOfOperation" flush="false">
                                    <c:choose>
                                        <c:when test="${loan.state == 'closed'}">
                                            <tiles:put name="isLock" value="true"/>
                                            <tiles:putList name="items">
                                                <%--<c:if test="${abstract.isAvailable}">--%>
                                                    <%--<tiles:add>������</tiles:add>--%>
                                                <%--</c:if>--%>
                                                <c:if test="${!loan.isAnnuity and phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment')}">
                                                    <tiles:add>������ ������</tiles:add>
                                                </c:if>
                                            </tiles:putList>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:putList name="items">
                                                <%--<c:if test="${abstract.isAvailable}">--%>
                                                    <%--<tiles:add><a href="${graphicUrl}">������</a></tiles:add>--%>
                                                <%--</c:if>--%>
                                                <c:if test="${loan.isAnnuity}">
                                                    <tiles:add><a href="#" onclick="win.open('annLoanMessage'); return false;">��� ��������?</a></tiles:add>
                                                </c:if>
                                                <c:if test="${!loan.isAnnuity and not empty loan.balanceAmount and phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment')}">
                                                    <tiles:add><a href="${paymentUrl}">������ ������</a></tiles:add>
                                                </c:if>
                                            </tiles:putList>
                                        </c:otherwise>
                                    </c:choose>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                        <tiles:put name="bottomData">
                            <div panel="compact" class="panelCompact">
                                <tiles:insert definition="listOfOperation" flush="false">
                                        <c:choose>
                                            <c:when test="${loan.state == 'closed'}">
                                                <tiles:put name="isLock" value="true"/>
                                                <tiles:putList name="items">
                                                    <%--<c:if test="${abstract.isAvailable}">--%>
                                                        <%--<tiles:add>������</tiles:add>--%>
                                                    <%--</c:if>--%>
                                                    <c:if test="${loan.isAnnuity}">
                                                        <tiles:add><a href="#" onclick="win.open('annLoanMessage');">��� ��������?</a></tiles:add>
                                                    </c:if>
                                                    <c:if test="${!loan.isAnnuity and phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment')}">
                                                        <tiles:add>������ ������</tiles:add>
                                                    </c:if>
                                                </tiles:putList>
                                            </c:when>
                                            <c:otherwise>
                                                <tiles:putList name="items">
                                                    <%--<c:if test="${abstract.isAvailable}">--%>
                                                        <%--<tiles:add><a href="${graphicUrl}">������</a></tiles:add>--%>
                                                    <%--</c:if>--%>
                                                    <c:if test="${loan.isAnnuity}">
                                                        <tiles:add><a href="#" onclick="win.open('annLoanMessage'); return false;">��� ��������?</a></tiles:add>
                                                    </c:if>
                                                    <c:if test="${!loan.isAnnuity and not empty loan.balanceAmount and phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment')}">
                                                        <tiles:add><a href="${paymentUrl}">������ ������</a></tiles:add>
                                                    </c:if>
                                                </tiles:putList>
                                            </c:otherwise>
                                        </c:choose>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
    </tiles:put>

    <%-- ���������  --%>
    <tiles:put name="editPanel">
        <%-- �� ����� �������, ������� ����� ����������� � ������ ��������������--%>
        <%-- ������ ������� --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">���:</tiles:put>
            <tiles:put name="data">
                <select field="size">
                    <option value="compact">����������</option>
                    <option value="wide">������</option>
                </select>
            </tiles:put>
        </tiles:insert>

        <%-- ������ ������� --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">������:</tiles:put>
            <tiles:put name="data">
                <select field="loanAccountNumber" id="loanAccountNumber">
                    <logic:iterate id="link" name="form" property="loans">
                        <option value="${link.number}">${link.name}</option>
                    </logic:iterate>
                </select>
            </tiles:put>
        </tiles:insert>

        <%-- �� ������� ���� ��������� --%>
        <div class="notifyDayCount">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">��������� ��:</tiles:put>
                <tiles:put name="data">
                    <select field="loanNotifyDayCount">
                        <c:forEach var="element" begin="0" end="9" varStatus="status">
                            <option value="${status.count}">${status.count}</option>
                        </c:forEach>
                    </select>
                </tiles:put>
            </tiles:insert>
           <div class="loanNotificationText">����</div>
       </div>
    </tiles:put>
</tiles:insert>