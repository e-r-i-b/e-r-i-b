<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <p>�������, ��������������� ���������� ������, - ��� ����������� ������ � �� ��������
            �������� �������� ������ �� ����� �����.
        </p>
        <c:set var="isUsedNewAlgorithm" value="${phiz:isUseNewLoanClaimAlgorithm()}"/>
        <c:set var="takeCreditLinkAvailable" value="${phiz:takeCreditLinkAvailable(true)}"/>
        <c:choose>
            <c:when test="${phiz:impliesService('LoanProduct') && param['emptyLoanOffer'] == false && not isUsedNewAlgorithm}">
                <p>���� � ��� ��� �� ������ �������. �� ������ �������� ������ �� ������,
                    ������� �� ������
                    <html:link action="/private/payments/loan_product" styleClass="text-green orangeText">
                        <span>��������� ������ �� ������. </span>
                    </html:link>
                </p>
            </c:when>
            <c:when test="${takeCreditLinkAvailable && phiz:isExtendedLoanClaimAvailable() && (phiz:impliesService('ExtendedLoanClaim') || phiz:impliesService('ShortLoanClaim')) && param['emptyLoanOffer'] == false && isUsedNewAlgorithm}">
                <p>���� � ��� ��� �� ������ �������. �� ������ �������� ������ �� ������,
                    ������� �� ������
                    <html:link action="/private/payments/payment.do?form=ExtendedLoanClaim" styleClass="text-green orangeText">
                        <span>��������� ������ �� ������.</span>
                    </html:link>
                </p>
            </c:when>
            <c:otherwise>
                <p>���� � ��� ��� �� ������ �������.
                    ��� ���������� ������������� ��� ������� ������ ���������� � ��������� �����.
                    ���� ���������� ����� ���� ��� ������!
                </p>

                <p>������ ����� ��������� ���������� ����� ��
                    <html:link href="http://www.sberbank.ru/ru/person/credits"
                               target="_blank"
                               styleClass="text-green orangeText">
                        <span>������.</span>
                    </html:link>
                </p>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>