<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/payments/loan_card_product" onsubmit="return setEmptyAction()">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="definition" value="loanClaim"/>
    <c:if test="${person == null}">
        <c:set var="definition" value="guestMain"/>
    </c:if>

    <tiles:insert definition="${definition}">
        <tiles:importAttribute/>
        <c:if test="${person != null}">
            <tiles:put name="title"                  value="������� �� ��������� ������"/>
            <tiles:put name="emptyMessage"           value="��� ���������� ���� ������ ������ ���� �� ������ ��������� ����"/>
            <tiles:put name="dataSize"               value="${not empty form.data}"/>
            <tiles:put name="imageId"                value="cardCondition"/>
            <tiles:put name="backToIncome"           value="${not empty param.income}"/>
            <tiles:put name="loanCardClaimAvailable" value="${form.loanCardClaimAvailable}"/>
            <tiles:put name="hideLinkBackTo"         value="true"/>

            <tiles:put name="description">
                <c:set var="bankCardLoanLink" value="${phiz:getBankCardLoanLink()}"/>
                ��� ���� ����� �������� ������ �� ��������� �����, ������������ � ��������� �������������� ��������� ����.
                <br/>
                ��������� ������������ � ��������� ����� �� ����� �����, ������� �� ������ <a
                    href="${bankCardLoanLink}" target="_blank">�������� �� ��������� ������</a>.<br/>
                ����� �������� ���������� ��������� �����, �������� �� � ������ � ������� �� ������ ������������.
            </tiles:put>

            <tiles:put name="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                    <tiles:put name="action" value="/private/payments.do"/>
                </tiles:insert>
                <c:if test="${not empty param.income}">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" value="����� ������"/>
                        <tiles:put name="action" value="/private/payments/income_level.do"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name" value="������� �� ��������� ������"/>
                    <tiles:put name="last" value="true"/>
                </tiles:insert>
            </tiles:put>
        </c:if>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function changeDateSelector(el)
                {
                    document.getElementById("changeDate").value = el;
                }

                function checkCardProduct(name)
                {
                    var list = document.getElementsByName(name);
                    if (list != undefined && list.length > 0)
                    {
                        var cnt = getSelectedQnt(name);
                        if (cnt == 0)
                        {
                            list[0].checked = true;
                            list[0].onclick();
                        }
                    }
                }

                doOnLoad(function ()
                {
                    //������ ���� ������� ������ ���� ������
                    checkCardProduct("loanId");
                });
            </script>
            <c:set var="name" value=""/>
            <c:set var="terms" value=""/>
            <c:set var="include" value="0"/>
            <div class="loanTitle">
            <input type="hidden" name="changeDate" id="changeDate" value="">
            <c:forEach items="${form.data}" var="form_data">
                <c:set var="product" value="${form_data}"/>

                <c:if test="${name != product.productId}">
                    <c:if test="${name ne ''}">
                        </table></div>
                        <c:if test="${include == 1}">
                            <div class="loanTitle include">* - �� ������������</div>
                            <c:set var="include" value="0"/>
                        </c:if>
                        <c:if test="${not empty terms}">
                            <div class="loanTitle">
                                <a onclick="showOrHideItems('terms_${product.id}')">�������������� �������</a>

                                <div id="terms_${product.id}" class="loanTitle hiddenDiv word-wrap">
                                        ${terms}
                                </div>
                            </div>
                        </c:if>
                        <br/><br/>
                    </c:if>
                    <c:set var="terms" value="${product.additionalTerms}"/>

                    <div class="loanTitle kind"><c:out value="${product.name}"/></div>
                    <c:if test="${product.allowGracePeriod == true}">
                        <div class="loanTitle">
                            <span>�������� ������ ��:&nbsp;</span>
                            <span class="bold">${product.gracePeriodDuration} ����������� ����</span>
                        </div>
                        <div>
                            <span>���������� ������ � �������� ������:&nbsp;</span>
                            <span class="bold">${product.gracePeriodInterestRate}%</span>
                        </div>
                    </c:if>
                    <div class="noPadding">
                    <table class="tblInf shadow" width="100%">
                    <tr>
                        <th></th>
                        <th>��������� ��������� �����</th>
                        <th>���������� ������</th>
                        <th>������� ������������<br/>������/������.����</th>
                    </tr>

                    <c:set var="name" value="${product.productId}"/>
                </c:if>
                <tr class="ListLine0">
                    <td><input type="radio" name="loanId" value="${product.id}" onclick="changeDateSelector(${product.changeDate.timeInMillis});"/></td>
                    <td>
                        �� <c:out value="${phiz:formatAmount(product.minCreditLimit)}"/>
                        �� <c:out value="${phiz:formatAmount(product.maxCreditLimit)}"/>
                        <c:if test="${product.maxCreditLimitInclude == false}">
                            *
                            <c:set var="include" value="1"/>
                        </c:if>
                    </td>
                    <td>${product.interestRate}%</td>
                    <td>
                            ${phiz:formatAmount(product.firstYearPayment)} /
                            ${phiz:formatAmount(product.nextYearPayment)}
                    </td>
                </tr>

            </c:forEach>
            </table></div>
            <c:if test="${include == 1}">
                <div class="loanTitle include">* - �� ������������</div>
            </c:if>
            <c:if test="${not empty terms}">
                <div class="loanTitle">
                    <a onclick="showOrHideItems('terms_')">�������������� �������</a>

                    <div id="terms_" class="loanTitle hiddenDiv word-wrap">
                            ${terms}
                    </div>
                </div>
            </c:if>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>