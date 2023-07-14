<%@ page import="com.rssl.phizic.business.documents.BusinessDocumentService" %>
<%@ page import="com.rssl.phizic.business.documents.PFRStatementClaim" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.pfr.PFRDoneServiceWrapper" %>
<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Enumeration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    boolean isConfirm = false;

    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();)
    {
        if (params.nextElement().toString().equals("confirm"))
        {
            isConfirm = true;
            break;
        }
    }

    if (!StringHelper.isEmpty(request.getParameter("operationId")))
    {
        PFRDoneServiceWrapper service = new PFRDoneServiceWrapper(request.getParameter("url"));

        if (isConfirm)
            service.pfrDone(request.getParameter("operationId"), false);
        else
            service.pfrDone(request.getParameter("operationId"), true);
    }
    
    BusinessDocumentService documentService = new BusinessDocumentService();

    List<PFRStatementClaim> claims = documentService.findPFRClaimByState("DISPATCHED",0,20);
%>

<html>
    <head><title>PFR test</title></head>
    <body>
        <table>
            <tr>
                <td>ExternalId</td>
                <td>ФИО</td>
                <td>СНИЛС</td>
                <td>Номер документа</td>
                <td>Серия</td>
            </tr>
            <c:forEach items="<%=claims%>" var="claim">
                <tr>
                    <form action="" method="POST">
                    <td>
                        ${claim.externalId}
                    </td>
                    <td>
                        ${claim.surName} ${claim.firstName} ${claim.patrName}
                    </td>
                    <td>
                        ${claim.SNILS}
                    </td>
                    <td>
                        ${claim.docNumber}
                    </td>
                    <td>
                        ${claim.docSeries}
                    </td>
                    <td>
                        <input type="hidden" name="operationId" value="${claim.externalId}"/><br/>
                    </td>
                    <td>
                        <input type="submit" name="confirm" value="Подтвердить"/><br/>
                    </td>
                    <td>
                        <input type="submit" name="cancel" value="Отказать"/><br/>
                    </td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>