<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="paymentsTemplates">

	<tiles:put name="data" type="string">
	<c:set var="frm" value="${PrintLoanDocumentsForm}"/>
	<c:if test="${frm.claim != null}">		
		<c:set var="claim" value="${frm.claim}"/>
		<c:set var="claimAttributes" value="${claim.attributes}"/>
		<c:set var="loan" value="${frm.loan}"/>
		<c:set var="schedule" value="${frm.schedule}"/>
		<script type="text/javascript">

			var initialThread = parseFloat(${loan.loanAmount.decimal}); // сумма нулевого денежного потока (сумма кредита со знаком минус)  
			var loanDuration = monthsCount("${loan.termDuration}"); //срок кредита
			var CF = parseFloat(${schedule[0].totalAmount.decimal}); //ежемесячный платеж, с комиссиями и т.п.

			//вычисление эффективной процентной ставки
			function calculateIRR()
			{
				var a = 0, b = 100;

			    if (f(a) == 0) return a;
			    if (f(b) == 0) return b;

			    return recursion(a, b);
			}

			function recursion(a, b)
			{
				var c = (a+b)/2;
			    var fc = f(c);
			    if (fc == 0 || b-a < 0.00005) return c;

			    if (f(a)*fc < 0) return recursion(a, c);
			    else return recursion(c, b);
			}

			function f(x)
			{
				var result = initialThread;
			    for (var i=1; i<=loanDuration; i++)
			    {
				   result += CF/Math.pow(1 + x, 30*i/365);
			    }

			    return result;
			}

		    //количество месяцев в дате
			function monthsCount(date)
			{
				var year  = parseInt(date.substring(0,2));
				var month = parseInt(date.substring(3,5));
				var days  = parseInt(date.substring(6,date.length));

				return year*12 + month + days/30;
			}

			function replaseWord (App, Source, Target)
			{
				//var wdFindContinue = 1, wdReplaceAll   = 2;
				if (App != null)
					App.Selection.Find.Execute("{"+Source+"}", true, true, false, false, false, true,
											  1, false, Target, 2);
			}


			// Замена тегов в документе App соответствующими значениями
			function infillTemplate (App)
		    {
			    <logic:iterate id="contract" name="loan" property="guaranteeContractIterator">

			        // теги поручителя

	                <c:set var="guarantee" value="${contract.guarantee}"/>
					replaseWord (App, "CreditNum_por", "${contract.number}");

			        <c:if test="${guarantee != null}">

						replaseWord (App, "FIO_por",
									<c:choose>
										<c:when test="${not empty guarantee.fullName}">
											"${guarantee.fullName}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
		                        );

			            //серия паспорта
						replaseWord (App, "seria_por",
									<c:choose>
										<c:when test="${not empty guarantee.docSeries}">
											"${guarantee.docSeries}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
		                        );

						//номер паспорта
			            replaseWord (App, "number_por",
									<c:choose>
										<c:when test="${not empty guarantee.docNumber}">
											"${guarantee.docNumber}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
		                        );

			            //Кем и когда выдан паспорт поручителя
			            <c:set var="pasport">
			                <c:choose>
								<c:when test="${not empty guarantee.docIssueDate}">
									<bean:write name="guarantee" property="docIssueDate.time" format="dd.MM.yyyy"/>
								</c:when>
								<c:otherwise>''</c:otherwise>
							</c:choose>
			            </c:set>
			            <c:if test="${not empty guarantee.docIssueBy}">
							<c:set var="pasport">${pasport} ${guarantee.docIssueBy}</c:set>
						</c:if>
						replaseWord (App, "pasp_org_por", '${pasport}');

			            //адрес регистрации
			            var guaranteeAddress = '';
			            <c:if test="${guarantee.legalAddress != null}">
							<c:set var="tmp" value="${guarantee.legalAddress.postalCode}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += "Почтовый индекс-${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.legalAddress.province}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.legalAddress.district}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
		                    <c:set var="tmp" value="${guarantee.legalAddress.city}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.legalAddress.street}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.legalAddress.house}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.legalAddress.building}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " номер корпуса-${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.legalAddress.flat}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " квартира-${tmp}";</c:if>
						</c:if>
			            replaseWord (App, "AddressR_por", guaranteeAddress);

						//адрес проживания
			            guaranteeAddress = '';
			            <c:if test="${guarantee.realAddress != null}">
							<c:set var="tmp" value="${guarantee.realAddress.postalCode}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += "Почтовый индекс-${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.realAddress.province}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.realAddress.district}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
		                    <c:set var="tmp" value="${guarantee.realAddress.city}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.realAddress.street}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.realAddress.house}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " ${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.realAddress.building}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " номер корпуса-${tmp}";</c:if>
			                <c:set var="tmp" value="${guarantee.realAddress.flat}"/>
							<c:if test="${not empty tmp}">guaranteeAddress += " квартира-${tmp}";</c:if>
						</c:if>
			            replaseWord (App, "AddressF_por", guaranteeAddress);



				</c:if>
			    </logic:iterate>

						//остальные теги (не относящиеся к поручителям)


						replaseWord (App, "CreditNum",
									<c:choose>
										<c:when test="${not empty loan.agreementNumber}">
											'<bean:write name="loan" property="agreementNumber"/>'
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "CreditDate",
									<c:choose>
										<c:when test="${not empty loan.termStart}">
											'<bean:write name="loan" property="termStart.time" format="dd.MM.yyyy"/>'
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "FIO",
										"${claimAttributes['surname'].stringValue} ${claimAttributes['name'].stringValue} ${claimAttributes['patronymic'].stringValue}");

						<c:choose>
							<c:when test="${not empty loan.loanAmount}">
								replaseWord (App, "CreditSum", '<bean:write name="loan" property="loanAmount.decimal" format="0.00"/>');
								replaseWord (App, "CreditSum_Pr", "${phiz:sumInWords(loan.loanAmount.decimal, loan.loanAmount.currency.code)}");
							</c:when>
							<c:otherwise>
								replaseWord (App, "CreditSum",'');
								replaseWord (App, "CreditSum_Pr",'');
							</c:otherwise>
						</c:choose>



						replaseWord (App, "Duration",
									<c:choose>
										<c:when test="${not empty loan.termDuration}">formatPeriod("${loan.termDuration}")</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "CreditRate",
									<c:choose>
										<c:when test="${not empty loan.rate}">
											'<bean:write name="loan" property="rate"/>% годовых'
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );


						<c:set var="comissions" value=""/>
			            <c:if test="${not empty loan.commissionRate}">
							<c:set var="comissions"><bean:write name="loan" property="commissionRate"/>%</c:set>
				         </c:if>
			            <c:if test="${not empty loan.commissionBase}">
							<c:choose>
								<c:when test="${loan.commissionBase == 'loanAmount'}">
									<c:set var="comissions">${comissions} от суммы кредита</c:set>
		                        </c:when>
								<c:otherwise><c:set var="comissions">${comissions} от остатка ссудной задолженности</c:set></c:otherwise>
							</c:choose>
						</c:if>
						replaseWord (App, "Comissions", "${comissions}");

			            //процентная ставка
			            replaseWord (App, "Ef_stavka", calculateIRR());

						var pasport = "";
						<c:if test="${not empty claimAttributes['passport-series']}">
							pasport += "${claimAttributes['passport-series'].stringValue}";
						</c:if>
						<c:if test="${not empty claimAttributes['passport-number']}">
							pasport += " ${claimAttributes['passport-number'].stringValue}";
						</c:if>
						replaseWord (App, "pasp_seria", pasport);

						replaseWord (App, "pasp_org",
									<c:choose>
										<c:when test="${not empty claimAttributes['pasp_org']}">
											${claimAttributes['pasp_org'].stringValue}
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );


			            var address = "";
						<c:set var="tmp" value="${claimAttributes['residence-address-index']}"/>
						<c:if test="${not empty tmp}">address += "${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-region']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-sity']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-settlement']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-district']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-street']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-house']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-building']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						<c:set var="tmp" value="${claimAttributes['residence-address-flat']}"/>
						<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

						replaseWord (App, "AddressF", address);


						var equalsAddress = "${claimAttributes['residence-address-equals'].stringValue}";
						if (equalsAddress == '1) Да')
							replaseWord (App, "AddressR", address);
						else
						{
							address = "";
							<c:set var="tmp" value="${claimAttributes['registration-address-index']}"/>
							<c:if test="${not empty tmp}">address += "${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-region']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-sity']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-settlement']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-district']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-street']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-house']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-building']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							<c:set var="tmp" value="${claimAttributes['registration-address-flat']}"/>
							<c:if test="${not empty tmp}">address += " ${tmp.stringValue}";</c:if>

							replaseWord (App, "AddressR", address);
					    }

						<c:choose>
							<c:when test="${not empty loan.firstDelayPenalty}">
								replaseWord (App, "sum1", '<bean:write name="loan" property="firstDelayPenalty.decimal" format="0.00"/>');
								replaseWord (App, "sum1_pr", "${phiz:sumInWords(loan.firstDelayPenalty.decimal, loan.firstDelayPenalty.currency.code)}");
							</c:when>
							<c:otherwise>
								replaseWord (App, "sum1", '');
								replaseWord (App, "sum1_pr", '');
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${not empty loan.secondDelayPenalty}">
								replaseWord (App, "sum2", '<bean:write name="loan" property="secondDelayPenalty.decimal" format="0.00"/>');
								replaseWord (App, "sum2_pr", "${phiz:sumInWords(loan.secondDelayPenalty.decimal, loan.secondDelayPenalty.currency.code)}");
							</c:when>
							<c:otherwise>
								replaseWord (App, "sum2", '');
								replaseWord (App, "sum2_pr", '');
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${not empty loan.thirdDelayPenalty}">
								replaseWord (App, "sum3", '<bean:write name="loan" property="thirdDelayPenalty.decimal" format="0.00"/>');
								replaseWord (App, "sum3_pr", "${phiz:sumInWords(loan.thirdDelayPenalty.decimal, loan.thirdDelayPenalty.currency.code)}");
							</c:when>
							<c:otherwise>
								replaseWord (App, "sum3", '');
								replaseWord (App, "sum3_pr", '');
							</c:otherwise>
						</c:choose>


						replaseWord (App, "Marka1",
									<c:choose>
										<c:when test="${not empty claimAttributes['car-property-model']}">
											"${claimAttributes['car-property-model'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "MakeYear",
									<c:choose>
										<c:when test="${not empty claimAttributes['car-property-year-mark']}">
											"${claimAttributes['car-property-year-mark'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "VIN",
									<c:choose>
										<c:when test="${not empty claimAttributes['vin']}">
											"${claimAttributes['vin'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "EngNum",
									<c:choose>
										<c:when test="${not empty claimAttributes['eng-num']}">
											"${claimAttributes['eng-num'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "BodyNum",
									<c:choose>
										<c:when test="${not empty claimAttributes['body-num']}">
											"${claimAttributes['body-num'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Color",
									<c:choose>
										<c:when test="${not empty claimAttributes['color']}">
											"${claimAttributes['color'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "PTS",
									<c:choose>
										<c:when test="${not empty claimAttributes['PTS']}">
											"${claimAttributes['PTS'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "OrgName",
									<c:choose>
										<c:when test="${not empty claimAttributes['org-name']}">
											"${claimAttributes['org-name'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );


						<c:choose>
							<c:when test="${not empty claimAttributes['TotalPrice1']}">
								replaseWord (App, "TotalPrice1", "${claimAttributes['TotalPrice1'].stringValue}");
								replaseWord (App, "TotalPrice1_", "${phiz:sumInWords(claimAttributes['TotalPrice1'].stringValue, loan.loanAmount.currency.code)}");
							</c:when>
							<c:otherwise>
								replaseWord (App, "TotalPrice1", '');
								replaseWord (App, "TotalPrice1_", '');
							</c:otherwise>
						</c:choose>

						replaseWord (App, "Marka2",
									<c:choose>
										<c:when test="${not empty claimAttributes['Marka2']}">
											"${claimAttributes['Marka2'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Marka3",
									<c:choose>
										<c:when test="${not empty claimAttributes['Marka3']}">
											"${claimAttributes['Marka3'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Property",
									<c:choose>
										<c:when test="${not empty claimAttributes['property']}">
											"${claimAttributes['property'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "ProducerDocNum",
									<c:choose>
										<c:when test="${not empty claimAttributes['producer-doc-num']}">
											"${claimAttributes['producer-doc-num'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Producer",
									<c:choose>
										<c:when test="${not empty claimAttributes['producer']}">
											"${claimAttributes['producer'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "ChassisNum",
									<c:choose>
										<c:when test="${not empty claimAttributes['chassis-num']}">
											"${claimAttributes['chassis-num'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Name1",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods1-name']}">
											"${claimAttributes['goods1-name'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Price1",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods1-price']}">
											"${claimAttributes['goods1-price'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Number1",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods1-number']}">
											"${claimAttributes['goods1-number'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						var totalPrice = 0;
						replaseWord (App, "TotalPrice1",
									<c:choose>
										<c:when test="${not empty claimAttributes['total-price1']}">
											totalPrice += parseFloat(${claimAttributes['total-price1'].stringValue});
											"${claimAttributes['total-price1'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Name2",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods2-name']}">
											"${claimAttributes['goods2-name'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Price2",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods2-price']}">
											"${claimAttributes['goods2-price'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "TotalPrice2",
									<c:choose>
										<c:when test="${not empty claimAttributes['total-price2']}">
											totalPrice += parseFloat(${claimAttributes['total-price2'].stringValue});
											"${claimAttributes['total-price2'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Number2",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods2-number']}">
											"${claimAttributes['goods2-number'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Name3",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods3-name']}">
											"${claimAttributes['goods3-name'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Price3",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods3-price']}">
											"${claimAttributes['goods3-price'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "Number3",
									<c:choose>
										<c:when test="${not empty claimAttributes['goods3-number']}">
											"${claimAttributes['goods3-number'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "TotalPrice3",
									<c:choose>
										<c:when test="${not empty claimAttributes['total-price3']}">
											totalPrice += parseFloat(${claimAttributes['total-price3'].stringValue});
											"${claimAttributes['total-price3'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "TotalPrice", totalPrice);

						replaseWord (App, "TotalCashSum",
									<c:choose>
										<c:when test="${not empty claimAttributes['total-cash-sum']}">
											"${claimAttributes['total-cash-sum'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "OrgCodifCode",
									<c:choose>
										<c:when test="${not empty claimAttributes['org-codif-code']}">
											"${claimAttributes['org-codif-code'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "OrgAddress",
									<c:choose>
										<c:when test="${not empty claimAttributes['org-address']}">
											"${claimAttributes['org-address'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "OrgOfficerFIO",
									<c:choose>
										<c:when test="${not empty claimAttributes['org-officer-fio']}">
											"${claimAttributes['org-officer-fio'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "NewNameBank1",
									<c:choose>
										<c:when test="${not empty claimAttributes['new-name-bank1']}">
											"${claimAttributes['new-name-bank1'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );

						replaseWord (App, "NewNameBank2",
									<c:choose>
										<c:when test="${not empty claimAttributes['new-name-bank2']}">
											"${claimAttributes['new-name-bank2'].stringValue}"
										</c:when>
										<c:otherwise>''</c:otherwise>
									</c:choose>
							  );
		    }

			function printTemplateDocuments()
		    {
			   try
			   {
				  var wordObj = new ActiveXObject("Word.Application");
				  var documentObj;
				  if( wordObj != null )
				  {
					wordObj.Visible = false;
					<logic:iterate id="document" name="PrintLoanDocumentsForm" property="documents">
						documentObj= wordObj.Documents.Open("${phiz:calculateActionURL(pageContext, '/documents/dowloadtemplate.do')}?id=${document.id}");
						if (documentObj != null)
						{
						  infillTemplate (wordObj);
						  var activeDoc = wordObj.ActiveDocument
						  activeDoc.PrintOut();
						  activeDoc.Close(false);
						}
					</logic:iterate>
					wordObj.DisplayAlerts = false;
					wordObj.Quit();
				  }
			   }
		       catch(exception){alert(exception.description);}
		    }


			function createTitle(sheet)
			{
			   var cell = sheet.Cells(1,2);
			   cell.Font.Size = 11;
			   cell.Font.Bold = 11;
			   cell.Value = "График погашения";
			}

			function createTitleTableCell(cell, width, value)
			{
			   cell.Font.Size = 9;
			   cell.WrapText = true;
			   cell.ColumnWidth = width;
			   cell.RowHeight = 35;
			   cell.Borders.lineStyle = 1;
			   cell.HorizontalAlignment = 3;
			   cell.VerticalAlignment = 2;
			   cell.value = value;
			}

			function createTableField(cell, value)
			{
			   cell.HorizontalAlignment = 3;
			   cell.Borders.lineStyle = 1;
			   cell.Value = value;
			}

			function printSchedule()
			{
				<c:if test="${fn:length(frm.schedule) > 0}">
					try
					{
					   var excel = new ActiveXObject("Excel.Application");
					   var book = excel.Workbooks.Add();
					   var sheet = book.ActiveSheet;
					   excel.Visible = false;

					   createTitle(sheet);
					   createTitleTableCell(sheet.Cells(3, 1),  5, "№");
					   createTitleTableCell(sheet.Cells(3, 2), 12, "Дата погашения");
					   createTitleTableCell(sheet.Cells(3, 3), 17, "Выплата по основному долгу");
					   createTitleTableCell(sheet.Cells(3, 4), 14, "Выплата по процентам");
					   createTitleTableCell(sheet.Cells(3, 5), 12, "Комиссия");
					   createTitleTableCell(sheet.Cells(3, 6), 17, "Сумма ежемесячного платежа");
					   createTitleTableCell(sheet.Cells(3, 7), 17, "Сумма для досрочного погашения");

					   var num = 0;
					   var i = 4;
					   <c:forEach items="${schedule}" var="item">
							<c:set var="monthlyPayment" value="${item.principalAmount.decimal+item.interestsAmount.decimal+
												item.commissionAmount.decimal}"/>
							createTableField(sheet.Cells(i,1), ++num);
							createTableField(sheet.Cells(i,2), '<bean:write name="item" property="date.time" format="dd.MM.yyyy"/>');
							createTableField(sheet.Cells(i,3), '<bean:write name="item" property="principalAmount.decimal" format="0.00"/>');
							createTableField(sheet.Cells(i,4), '<bean:write name="item" property="interestsAmount.decimal" format="0.00"/>');
							createTableField(sheet.Cells(i,5), '<bean:write name="item" property="commissionAmount.decimal" format="0.00"/>');
							createTableField(sheet.Cells(i,6), '<bean:write name="item" property="commissionAmount.decimal" format="0.00"/>');
							createTableField(sheet.Cells(i,7), '<bean:write name="monthlyPayment" format="0.00"/>');
						  i++;
					   </c:forEach>
					   sheet.PageSetup.LeftMargin = 20;
					   sheet.PageSetup.RightMargin = 10;
					   sheet.PageSetup.TopMargin = 60;
					   sheet.PageSetup.BottomMargin = 30;
					   sheet.printOut();
					   excel.DisplayAlerts = false;
					   excel.Quit();
					}
					catch(exception){alert(exception.description);}
				</c:if>
			}

			function printDocuments()
			{
				var docList = document.getElementById("documentsList");
				docList.style.display = "none";
				var wait = document.getElementById("wait");
				wait.style.display = "";
				if (window.ActiveXObject)
			    {
					printTemplateDocuments();
					printSchedule();
				}
				else
				{
					alert("Данная функция не поддерживается вашим браузером. Для совершения операции воспользуйтесь Internet Explorer 6.0 и выше.");
				}
				window.close();
			}
		</script>

		<table id="documentsList" cellpadding="3" cellspacing="0" class="maxSize" align="center">
			<tr>
				<td class="filter">Будут распечатаны следующие документы:</td>
			</tr>
			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
						<!-- заголовок списка -->
						<tr class="titleTable">
							<td width="200px">Наименование</td>
							<td>Описание</td>
							<td width="100px">Дата обновления</td>
						</tr>
						<!-- строки списка -->
						<% int lineNumber = 0;%>
						<logic:iterate id="document" name="PrintLoanDocumentsForm" property="documents">
							<% lineNumber++;%>
							<tr class="ListLine<%=lineNumber%2%>">
								<td class="ListItem"><bean:write name="document" property="name"/>&nbsp;</td>
								<td class="ListItem"><bean:write name="document" property="description"/>&nbsp;</td>
								<td class="ListItem" align="center"><bean:write name="document" property="update.time" format="dd.MM.yyyy"/>&nbsp;</td>
							</tr>
						</logic:iterate>
						<!--документы из Лоанс-->
						<c:if test="${fn:length(frm.schedule) != 0}">
							<tr class="ListLine<%=++lineNumber%2%>">
								<td class="ListItem">График погашения</td>
								<td class="ListItem">График выплат по кредиту</td>
								<td class="ListItem">&nbsp;</td>
							</tr>
						</c:if>
					</table>
				</td>
			</tr>
			<c:if test="${fn:length(frm.schedule) == 0}">
				<tr>
					<td align="center">
						<font color="red">
							<br>Внимание! График погашения кредита не будет распечатан, т.к. не рассчитан в RS-Loans.
						</font>
					</td>
				</tr>
			</c:if>
			<tr class="maxSize"><td>&nbsp;</td></tr>
			<tr>
			   <td align="center" colspan="6" class="filterLineGray">
				<table height="100%" cellspacing="0" cellpadding="0">
					<tr align="center">
					  <td>
						<tiles:insert definition="clientButton" flush="false">
							<tiles:put name="commandTextKey" value="button.next"/>
							<tiles:put name="commandHelpKey" value="button.next.help"/>
							<tiles:put name="bundle"  value="loansBundle"/>
							<tiles:put name="image"   value="next.gif"/>
							<tiles:put name="onclick" value="printDocuments();"/>
						</tiles:insert>
					  </td>
					  <td>
						<tiles:insert definition="clientButton" flush="false">
							<tiles:put name="commandTextKey" value="button.cancel"/>
							<tiles:put name="commandHelpKey" value="button.cancel.help"/>
							<tiles:put name="bundle"  value="loansBundle"/>
							<tiles:put name="image"   value="back.gif"/>
							<tiles:put name="onclick" value="window.close();"/>
						</tiles:insert>
					   </td>
					</tr>
				</table>
			   </td>
		    </tr>
			<tr><td>&nbsp;</td></tr>
		</table>
		<table id="wait" class="maxSize" style="display:none">
			<tr><td>&nbsp;</td></tr>
			<tr><td class="filter">&nbsp;Идет печать документов. Ждите...</td></tr>
		</table>
	</c:if>
	<c:if test="${frm.claim == null}">
		<script type="text/javascript">
			window.resizeTo(400,150);
		</script>
		<table width="100%">
			<tr>
				<td class="messageTab" align="center">Пакет документов не может быть распечатан. Создайте кредитный договор по заявке в RS-Loans.</td>
			</tr>
		</table>				
	</c:if>
	</tiles:put>
</tiles:insert>