<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>���� ��������� ������</title></head>
    <body>
        <h3>���� ��������� ������</h3>
        <html:form action="/crm/loanclaim/messages" show="true">
            <ul>
                <li><a href="${phiz:calculateActionURL(pageContext,'/credit/loanclaim')}">���� ����� ������� ����������� ������ �� ������</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/credit/crm/loanclaim')}">��������� CRM</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/initiationNew')}">�������� ������ �� CRM ����� ���� � ETSM � ������� ������</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/searchApplicationRs')}">����� �� ������ ������ ��������� �����������(SearchApplicationRs)</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/initiateConsumerProductOfferRq')}">������ �������� �� ������(InitiateConsumerProductOfferRq)</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/offerResultTicket')}">���������, ������������ � ����� �� ������ ������������� ������(OfferResultTicket)</a></li>
            </ul>
        </html:form>
        <a href="${phiz:calculateActionURL(pageContext,'/index')}">�� �������</a>
    </body>
</html:html>
