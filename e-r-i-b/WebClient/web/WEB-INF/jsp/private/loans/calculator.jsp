<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/loans/calculator" onsubmit="return setEmptyAction(event)">
	<tiles:insert definition="loanMain">
		<tiles:put name="submenu" type="string" value="loanCalculator"/>
		<!-- ��������� -->
		<tiles:put name="pageTitle" type="string">
			<c:out value="����������� ��������� ���������"/>
		</tiles:put>

		<!--����-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.loanProductsList"/>
				<tiles:put name="commandHelpKey" value="button.loanProductsList.help"/>
				<tiles:put name="bundle" value="loansBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/private/loans/products/list.do"/>
			</tiles:insert>
        </tiles:put>

        <!-- ������  -->
     
        <!-- ������ -->
        <tiles:put name="data" type="string">
	        <tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="CreditCalculator"/>
				<tiles:put name="name" value="��������� �����������"/>
				<tiles:put name="description" value="����������� ��������� ����������� ��� ������� ����� ����������� ������ � �������� �� �������."/>
				<tiles:put name="data">
				<tr>
					<td>
						${LoanCalculatorForm.html}
					</td>
				</tr>
                </tiles:put>
		        <tiles:put name="alignTable" value="center"/>
	        </tiles:insert>
        </tiles:put>
	</tiles:insert>
</html:form>

