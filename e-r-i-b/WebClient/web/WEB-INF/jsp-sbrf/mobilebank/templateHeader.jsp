<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<tiles:importAttribute/>
<c:url value="/private/mobilebank/payments/select-category-provider.do" var="goBackURL">
  <c:param name="phoneCode" value="${frm.phoneCode}"/>
  <c:param name="cardCode" value="${frm.cardCode}"/>
</c:url>
<script type="text/javascript">

    function goback()
    {
        loadNewAction('', '');
        window.location = "${goBackURL}";
    }
</script>

<tiles:insert definition="formHeader" flush="false">
    <tiles:put name="image" value="${imagePath}/icon_SmsTemplate.png"/>
    <c:choose>
        <c:when test="${firstRegistrationForm == 'true'}">
            <tiles:put name="description">
                �� ������ ������� SMS-������ �� ������ ������� �������, ������ ��� �� ������, ���� ������ �� ������
                &laquo;������� ����� SMS-������&raquo;.
            </tiles:put>
        </c:when>
        <c:otherwise>
            <tiles:put name="description">
                �������� ����������, � ����� �������� �� ������ ���������� ������ �� �������.
            </tiles:put>
        </c:otherwise>
    </c:choose>
</tiles:insert>
<div class="clear"></div>

<div id="paymentStripe">
   <tiles:insert definition="stripe" flush="false">
       <tiles:put name="name" value="�������� SMS-�������"/>
        <tiles:put name="current" value="true"/>
   </tiles:insert>
   <tiles:insert definition="stripe" flush="false">
       <tiles:put name="name" value="���������� ����������"/>
   </tiles:insert>
   <tiles:insert definition="stripe" flush="false">
       <tiles:put name="name" value="�������������"/>
   </tiles:insert>
   <tiles:insert definition="stripe" flush="false">
       <tiles:put name="name" value="��������"/>
   </tiles:insert>
   <div class="clear"></div>
</div>