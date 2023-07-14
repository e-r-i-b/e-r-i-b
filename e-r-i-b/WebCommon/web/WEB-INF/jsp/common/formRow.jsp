<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>

<%--
  ������ ��� ����������� ����� � ����������� � ������ ���������
  title - ������������ ����
  isNecessary - ������� ������������� ���� (��� ����������� ����� * ����� � title)
  needMark - ������� ����, ����� �� �������� ������ ��� ����� �� ��. true (�����������) ��������, false - �� ��������
  data - ��������������� ������ � �������, �������� � ��
  description -  �������� ��������
  detail - ��������� ��������
  clazz - ����� (���� ���������� �������������� �������� ���������� � �������� �����)
  useInPFR - �� ������������ ���������, ��������� �� ������������� � ���. �� ��������� false
  ------ �����, ����������� ��������������� ����������� ------
  fieldName - ��� ���� �������������� ����, ��� ����� � data, ���� �� �������� ���������� �� ������� �� ����������
  minValue - ����������� ��������
  maxValue - ������������ ��������
  regexp - ����� ��������, �������� ������ ������������ ��������
  dataType - ��� ��������, ����������� � ���� (�������������)
--%>

<c:if test="${!empty clazz}"><div class="${clazz}"></c:if>

<c:choose>
    <c:when test="${useInPFR}">
        <div class="form-row <c:if test="${needMark == 'false'}">notMark</c:if> pfrFormRow" <c:if test="${not empty dataType}">data-type="${dataType}"</c:if> >
    </c:when>
    <c:otherwise>
        <div class="form-row <c:if test="${needMark == 'false'}">notMark</c:if>" <c:if test="${not empty dataType}">data-type="${dataType}"</c:if>>
    </c:otherwise>
</c:choose>
    <c:if test="${not empty title or isNecessary}">
        <div class="paymentLabel"  <c:if test="${useInPFR}">style="width:184px"</c:if>>
            <span class="paymentTextLabel">${title}</span>
            <c:if test="${isNecessary}">
                <span class="asterisk">*</span>
            </c:if>
        </div>
    </c:if>
    <div class="paymentValue" <c:if test="${useInPFR}">style="width:400px"</c:if>>
        <div class="paymentInputDiv">
            ${data}
        </div>
        <%--����� ������� �������� ��������. ���� ��� ���, �� description = ""--%>
        <div style="display: none" class="description">${description}
            <%-- ����� ������������� ��������, ����� ���� ��������� �������� --%>
            <c:if test="${!empty detail}">
                <%-- ���� ��������� �������� ���������, � �������� ���, �� ���������� ������ "���������"
                ����� �������� ������������ �����: "��� ��������� ��� ����?" --%>
                <c:choose>
                    <c:when test="${empty description}">
                        <a href="#" onclick="payInput.openDetailClick(this); return false;">��� ��������� ���
                            ����?</a>
                    </c:when>
                    <c:otherwise>
                        <a href="#" onclick="payInput.openDetailClick(this); return false;">���������.</a>
                    </c:otherwise>
                </c:choose>
                <div class="detail" style="display: none">
                        ${detail}
                </div>
            </c:if>

        </div>
        <div style="display: none;" class="preventErrorDiv">
            <div class="preventErrorDivText"></div>
        </div>
        <div style="display: none;" class="errorDiv"></div>
    </div>
    <div class="clear"></div>
    <c:if test="${not empty additionalData}">
        <div class="">${additionalData}</div>
    </c:if>
</div>
<c:if test="${!empty clazz}"></div></c:if>

<c:if test="${not empty fieldName and (not empty minValue or not empty maxValue or not empty regexp)}">
    <script type="text/javascript">

        payInput.addValidators("${fieldName}", function(obj){
            var minValue = undefined;
            var maxValue = undefined;
            var regexp = undefined;

            <c:if test="${not empty minValue}">minValue = ${minValue};</c:if>
            <c:if test="${not empty maxValue}">maxValue = ${maxValue};</c:if>
            <c:if test="${not empty regexp}">regexp = ${regexp};</c:if>

            <c:choose>
                <c:when test="${not empty validateMethod}">
                    return ${validateMethod}
                </c:when>
                <c:otherwise>
                    return validate
                </c:otherwise>
            </c:choose>
            (obj, {VALIDATE_MIN_LETTERS_NAME : minValue, VALIDATE_MAX_LETTERS_NAME : maxValue, VALIDATE_REGEXP_LETTERS_NAME : regexp });
            }
        );
    </script>
</c:if>