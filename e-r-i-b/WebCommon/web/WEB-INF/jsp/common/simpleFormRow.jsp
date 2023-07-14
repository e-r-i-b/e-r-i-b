<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>

<%--
  ������ ��� ����������� ����� � ����������� � ������ ���������
  title - ������������ ����
  isNecessary - ������� ������������� ���� (��� ����������� ����� * ����� � title)
  data - ��������������� ������ � �������, �������� � ��
  description - ��������� � ����
  needMargin -  div ��� ��������������� �������
  necessaryClassName - �������������� �����. ����������� ��� ������ ��� ������� *
--%>

<c:set var="descriptionText">
    <c:if test="${not empty description}">
        <div class="clear"></div>
        <div class="descriptionBlock">
            ${description}
        </div>
    </c:if>
</c:set>

<div class="form-row">
    <div class="paymentLabel">
        <c:choose>
            <c:when test="${empty title}">
                &nbsp;
            </c:when>
            <c:otherwise>
                ${title}
                <c:if test="${isNecessary}">
                    <span class="asterisk <c:if test="${not empty necessaryClassName}">${necessaryClassName}</c:if>">*</span>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="paymentValue">
        <c:choose>
            <c:when test="${needMargin}">
                <div class="paymentInputDiv">
                    ${data}
                    ${descriptionText}
                </div>
            </c:when>
            <c:otherwise>
                ${data}
                ${descriptionText}
            </c:otherwise>
        </c:choose>
    </div>
    <div class="clear"></div>
</div>
