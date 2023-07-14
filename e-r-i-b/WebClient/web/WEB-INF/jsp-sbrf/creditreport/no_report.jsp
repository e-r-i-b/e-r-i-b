<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/credit/report" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="creditHistory">
        <tiles:put name="pageTitle">
            <bean:message bundle="creditHistoryBundle" key="label.credit.history.title"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="creditHistoryBundle" key="label.credit.history.title"/>
                </tiles:put>
                <tiles:put name="data">
                    <div class="credit-history-box">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="description">
                                <p class="b-get-credit-history-text">
                                    <bean:message bundle="creditHistoryBundle" key="label.credit.history.description"/>
                                </p>
                            </tiles:put>
                        </tiles:insert>

                        <c:if test="${not form.bkiError}">
                            <c:choose>
                                <c:when test="${form.waitingNew}">
                                    <div class="we-are-preparing-cr-history">
                                        <img src="${globalUrl}/commonSkin/images/clock.png" alt="" />
                                        <div class="we-are-preparing-cr-history-text">
                                            �� ������� ���� ��������� �������<br />����������, ���������
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="b-get-credit-history">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.get.credit.history"/>
                                            <tiles:put name="commandHelpKey" value="button.get.credit.history"/>
                                            <tiles:put name="bundle" value="creditHistoryBundle"/>
                                            <tiles:put name="action" value="/private/payments/creditReportPayment/edit.do?recipient=${form.providerId}"/>
                                            <tiles:put name="isDefault" value="true"/>
                                        </tiles:insert>
                                        <div class="b-get-credit-history-cost-wrap">
                                            <div class="b-get-credit-history-cost-title">
                                                ���������<br />������
                                            </div>
                                            <span class="b-get-credit-history-cost-value">${form.cost}</span>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>

                        <div class="b-get-credit-history-bg"></div>
                        <div class="b-get-credit-history-example-content">
                            <div class="b-get-credit-history-zoom">
                                <div class="categoriesTitle b-get-credit-history-example-title">������ ��������� �������</div>
                                <p class="b-get-credit-history-example-text">��������� ����� �������� ���������<br /> ���������� � ����������� � ����� �����������<br /> ��������, � ����� � ��������, ������� ������<br /> ����� � ������ ����������� ��� �������� �����<br /> ��������� �������.</p>

                                <div class="b-get-credit-history-rating">
                                    <h3 class="b-get-credit-history-rating-title">��� ��������� �������</h3>
                                    <p>��������� ������� ���������� ��������� ������ ���� ��������� ������� � ��������� �� � ���������.</p>
                                    <p>���� �� ������� ��� ������� �� �������� ������� � �� ������ ������� ����� �������� ��������, �� ��� ��������� ������� ����� �������. ������� ��� ����� ������!</p>
                                </div>
                            </div>

                            <div class="b-get-credit-history-data">
                                <div class="b-get-credit-history-data-content">
                                    <h3>������ ������ �� ���� ����� �������� � ��������� ������</h3>
                                    <p>��� ���������� �� ���� ����� �������� ��� �� ������!</p>
                                    <p>�� ������� ������� �� ������� ��������� ������� �������� � ��������� ���������� ���������.</p>
                                </div>
                            </div>
                        </div>
                        <div class="b-get-credit-history-data-bottom-bg"></div>

                        <div class="b-get-credit-history-who-was-interesed">
                            <h3>��� ������������� ����� ��������� ��������</h3>
                            <p>�� �������, ��� � ����� �������� ���� ��������� �������. ��������, ����� � ����������� ������� ������ ������� ��� ������������ ��������� �� ������, ����� �������� ��� ��� �������.</p>
                        </div>

                        <c:if test="${not form.bkiError and not form.waitingNew}">
                            <div class="b-get-credit-history">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.get.credit.history"/>
                                    <tiles:put name="commandHelpKey" value="button.get.credit.history"/>
                                    <tiles:put name="bundle" value="creditHistoryBundle"/>
                                    <tiles:put name="action" value="/private/payments/creditReportPayment/edit.do?recipient=${form.providerId}"/>
                                    <tiles:put name="isDefault" value="true"/>
                                </tiles:insert>
                                <div class="b-get-credit-history-cost-wrap">
                                    <div class="b-get-credit-history-cost-title">
                                        ���������<br />������
                                    </div>
                                    <span class="b-get-credit-history-cost-value">${form.cost}</span>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="credit-history-right">
                        <div class="b-r-sidebar-okb">
                            <img src="${globalUrl}/commonSkin/images/okb-logo.gif" alt="" class="float"/>
                            <p>����� ������������ <a href="${phiz:getBkiOkbUrl()}" target="_blank">��� �����</a></p>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
