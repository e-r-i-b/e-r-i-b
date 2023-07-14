<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/autopayment/payment/info/print" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="payment" value="${form.payment}"/>
<c:set var="bank" value="${payment.receiverBank}"/>
<c:set var="person" value="${form.activePerson}"/>
<tiles:insert definition="check">
    <tiles:put name="data" type="string">
    <style>
    .checkSize
    {
        width:60mm;
        font-family:Arial;
        font-size:8px;
        word-wrap:break-word;
        text-transform:uppercase;
    }
    .title
    {
        font-weight:bold;
        text-align: center;
    }
    .titleAdditional
    {
        text-align: center;
    }
    .stamp
    {
        border-width:2px;
        font-family:Arial;
        font-size:9px;
        border-style:solid;
        border-color:#025CA2;
        text-align:center;
        font-weight: bold;
        color:#025CA2;
        padding:2mm;
        width:57mm;
    }

    .stamp img
    {
        margin-left: -2mm;
    }

    .mainDiv
    {
       word-wrap:break-all;
       border-width:1px;
       border-style:solid;
       border-color:black;
       width:60mm;
       marign:5mm;
       padding:0mm 3mm 0mm 3mm;
    }
    </style>

    <div id="checkId" class="mainDiv">
        <div class="checkSize title">�������� ������ ���</div><br/>
        <div class="checkSize titleAdditional">��� �� ��������<br/>
        �� ������ ����������<br/><br/>����������� ������ �����</div>
        <br/>
        
        <div class="checkSize">���� ��������:   <bean:write name="form" property="datePayment" format="dd.MM.yy"/></div>
        <div class="checkSize">����� �������� (���):  <bean:write name="form" property="datePayment" format="HH:mm:ss"/></div>
        <div class="checkSize">������������� ��������:
        ${payment.idFromPaymentSystem}</div>
        <br/>
        <div class="checkSize">�����:  <c:out value="${phiz:getCutCardNumber(payment.chargeOffCard)}"/></div>
        <br/>
        <div class="checkSize">����� ��������:  <c:out value="${phiz:formatAmount(payment.amount)}"/></div>
        <div class="checkSize">��������:
            <c:choose>
                <c:when test="${ not empty payment.commission}">
                     <c:out value="${phiz:formatAmount(payment.commission)}"/>
                </c:when>
                <c:otherwise>
                   0,00 ���.
                </c:otherwise>
            </c:choose>
        </div>
        <div class="checkSize">��� �����������:  ${payment.authorizeCode} </div>
        <br/>
        <div class="checkSize">��������� �����������:
        <c:if test="${ not empty person}">
           <br/>${phiz:getFormattedPersonName(person.firstName, person.surName, person.patrName)}
        </c:if>
        </div>
        <c:if test="${ not empty payment.extendedFields}">
            <c:forEach items="${payment.extendedFields}" var="field">
                <c:if test="${field.key}">
                    <c:choose>
                        <c:when test="${not empty field.value}">
                            <c:set var="fieldValue" value="${field.value}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="fieldValue" value="${field.defaultValue}"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="checkSize">${field.name}:
                        <c:choose>
                            <c:when test="${field.type == 'money'}">
                                <span class="bold"><c:out value="${fieldValue}"/>&nbsp;���.</span>
                            </c:when>
                            <c:when test="${field.type == 'set'}">
                                <c:forTokens delims="@" var="value" items="${fieldValue}">
                                    <span class="bold"><c:out value="${value}"/></span>
                                    <br/>
                                </c:forTokens>
                            </c:when>
                            <c:otherwise>
                               <span class="bold"><c:out value="${fieldValue}"/></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>

        <div class="checkSize">��������� �������:</div>
        <c:if test="${ not empty payment.extendedFields}">
            <c:forEach items="${payment.extendedFields}" var="field">
                 <c:if test="${(not field.key) && field.requiredForBill}">
                     <c:choose>
                        <c:when test="${not empty field.value}">
                            <c:set var="fieldValue" value="${field.value}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="fieldValue" value="${field.defaultValue}"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="checkSize">${field.name}:
                        <c:choose>
                            <c:when test="${field.type == 'money'}">
                                <span class="bold"><c:out value="${fieldValue}"/>&nbsp;���.</span>
                            </c:when>
                            <c:when test="${field.type == 'set'}">
                                <c:forTokens delims="@" var="value" items="${fieldValue}">
                                    <span class="bold"><c:out value="${value}"/></span>
                                    <br/>
                                </c:forTokens>
                            </c:when>
                            <c:otherwise>
                               <span class="bold"><c:out value="${fieldValue}"/></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
        <br/><br/><br/>
        <div class="checkSize">
            ���������� �������:<br/>
            <c:choose>
                <c:when test="${not empty payment.receiverNameForBill}">
                    ${payment.receiverNameForBill}
                </c:when>
                <c:otherwise>
                    ${payment.receiverName}
                </c:otherwise>
            </c:choose>
        </div>
        <br/>
        <div class="checkSize">��������� ����������:</div>
        <c:if test="${ not empty bank}">
            <div class="checkSize">���: ${bank.BIC} </div>
        </c:if>
        <c:if test="${ not empty payment.receiverINN}">
            <div class="checkSize">���: ${payment.receiverINN} </div>
        </c:if>
        <c:if test="${ not empty payment.receiverAccount}">
            <div class="checkSize">����: ${payment.receiverAccount} </div>
        </c:if>
        <c:if test="${ not empty bank}">
            <div class="checkSize">����. ����: ${bank.account} </div>
        </c:if>
        <br/>
        <div id="stamp" class="stamp title">
                ����������� ������������ ��������������<br/> ���� ���������� ��������� (��������<br/> ����������� ��������)
            <br/>
            <img width="137px" src="${imagePath}/stampDispatched_blue.gif">
        </div>

        <br/><br/><br/><br/>
        <div id="standartInfo" class="checkSize titleAdditional">
            �� ����������, ��������� �� ��������� ������� �� �����, �� ������
            ��������� ��������� �� ����������� �����
            <br/>(�������������� ������ �������� ����� �� ����� �����)
            �� ������� �������������� ������ ����������� � ���������� �������
            <c:if test="${not empty payment.receiverPhone}">
                <br/>�� ��������: ${payment.receiverPhone}
            </c:if>
        </div>
    </div>

    <script type="text/javascript">
        if (navigator.appName == 'Opera') //����� � ����� ������������ ������� ������
	    {
            var stringLength = 25;     // ���������� ��������� ��������
            var checkDiv = document.getElementById("checkId");
            if (checkDiv != null)
            {
                var divs = checkDiv.getElementsByTagName("div");       // �������� ��� div'�, � ���. �������� ����
                for (var i = 0; i < divs.length; i++)
                {
                    var div = divs[i];
                    if (div.id == "stamp") <%--� URL ������ ��������� ������� �� �����--%>
                        continue;
                    var contentDiv = div.innerHTML;
                    var lengthContentDiv = div.innerHTML.length;
                    if (lengthContentDiv > stringLength)              // ���� ����� �������, ����� ���������� � ��� ������� (���� �� ��� ���)
                    {
                        var newContent = "";
                        for (var j = 0; j < lengthContentDiv; j = j + stringLength)
                        {
                            var partContentDiv = contentDiv.substr(j, stringLength);   // �������� ����� ������
                            newContent = newContent + partContentDiv;

                            // ���� � ����� ��� ��������, �� ������ � ����� ������ (����� ����� �������������� ������� ������)
                            if (!partContentDiv.match(/\s/))
                                newContent = newContent + " ";
                        }
                        div.innerHTML = newContent;
                    }
                }
            }
	    }

        var additionalInfoDiv = document.getElementById("additionalInfo");
        if (additionalInfoDiv != null)
        {
            var standartInfoDiv = document.getElementById("standartInfo");
            var tempInfoHTML  = additionalInfoDiv.innerHTML;              // ��������� ����� � ����� div
            additionalInfoDiv.innerHTML = standartInfoDiv.innerHTML;      // ������ ����� � div'��
            standartInfoDiv.innerHTML = tempInfoHTML;

        }
    </script>
    </tiles:put>
</tiles:insert>
</html:form>
