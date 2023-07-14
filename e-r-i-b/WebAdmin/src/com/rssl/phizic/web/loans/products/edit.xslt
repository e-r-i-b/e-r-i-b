<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:set="http://exslt.org/sets">
	<xsl:output method="html"/>

	<xsl:variable name="loanTypes" select="document('list-loan-types.xml')"/>
	<xsl:variable name="loanOffices" select="document('loan-offices.xml')"/>
	<xsl:variable name="loanKinds" select="document('loan-product-kinds.xml')"/>
	<xsl:variable name="templates" select="document('department-package-templates.xml')"/>
	<xsl:variable name="product" select="."/>

	<xsl:key name="allowedOffices" match="loan-type" use="allowedOffice"/>

	<xsl:variable name="usedDepositsList" select="."/>
	<xsl:template match="/">
			<tr>
				<td class="Width120 LabelAll">Имя кредитного продукта<span class="asterisk">*</span></td>
				<td><input name="field(name)" size="100" maxlength="256" styleClass="contactInput" value="{/loan-product/@name}"/>&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Вид кредита<span class="asterisk">*</span></td>
				<td>
					<xsl:choose>
						<xsl:when test="$loanKinds//loan-kind">
							<select name="field(loanKind)">
								<xsl:for-each select="$loanKinds//loan-kind">
									<xsl:variable name="id" select="@id"/>
									<option value="{$id}">
										<xsl:if test="$product/loan-product/@loan-kind=$id">
											<xsl:attribute name="selected"/>
										</xsl:if>
										<xsl:value-of select="@name"/>
									</option>
								</xsl:for-each>
							</select>
						</xsl:when>
						<xsl:otherwise>Нет видов кредитов</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Анонимная заявка<span class="asterisk">*</span></td>
				<td><select name="field(anonymousClaim)" styleClass="select">
						<option value="false">
							<xsl:if test="$product/loan-product/@anonymous='false'">
								<xsl:attribute name="selected"/>
							</xsl:if>
							Недоступен
						</option>
						<option value="true">
							<xsl:if test="$product/loan-product/@anonymous='true'">
								<xsl:attribute name="selected"/>
							</xsl:if>
							Доступен
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Пакет документов<span class="asterisk">*</span></td>
				<td>
					<xsl:choose>
						<xsl:when test="$templates//entity">
							<select name="field(packageTemplate)">
								<xsl:for-each select="$templates//entity">
									<xsl:variable name="id" select="@key"/>
									<option value="{$id}">
										<xsl:if test="$product/loan-product/@package-template=$id">
											<xsl:attribute name="selected"/>
										</xsl:if>
										<xsl:value-of select="./field[@name='name']/text()"/>
									</option>
								</xsl:for-each>
							</select>
						</xsl:when>
						<xsl:otherwise>Нет пакетов документов</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Описание</td>
				<td><input name="field(description)" size="100" maxlength="256" styleClass="contactInput" value="{/loan-product/@description}"/>&nbsp;</td>
			</tr>
			<xsl:call-template name="javascript"/>
            <tr>
                <td colspan="5">
                    <table cellspacing="0" cellpadding="0" style="margin-bottom:2px" class="tblInf"
                           id="LoanOfficesTable" width="100%">
                        <tr class="tblInfHeader" align="center" style="background-color:#ffffff;">
                            <td width="25%">Наименование офиса</td>
                            <td width="15%">Валюта кредита</td>
                            <td width="27%">Вид кредита</td>
                            <td width="8%">Идентификатор</td>
                            <td width="25%">Сроки кредита</td>
                        </tr>
                        <xsl:call-template name="rows"/>
                    </table>
                </td>
            </tr>
	</xsl:template>

	<xsl:template name="rows">
		<xsl:for-each select="$loanOffices/loan-offices/loan-office">
			<xsl:variable name="officeId" select="@id"/>
			<xsl:variable name="currencies"
			              select="set:distinct($loanTypes//currency[../allowedOffice/text()=$officeId and not(starts-with(../name/text(),'#'))])"/>
			<xsl:choose>
				<xsl:when test="count($currencies)=0">
					<tr class="listLine{position() mod 2}">
						<td class="listItem"><xsl:value-of select="$loanOffices/loan-offices/loan-office[@id=$officeId]/@name"/></td>
						<td class="listItem" width="auto" align="center" colspan="4"> Недоступен </td>
					</tr>
				</xsl:when>
				<xsl:otherwise>

			<xsl:for-each select="$currencies">
				<xsl:variable name="currency" select="text()"/>
				<xsl:variable name="types" select="$loanTypes//loan-type[allowedOffice/text()=$officeId and currency/text()=$currency and not(starts-with(name/text(),'#'))]"/>
				<tr class="listLine{position() mod 2}">
					<xsl:if test="position() = 1">
						<td class="listItem" rowspan="{count($currencies)}">
							<xsl:value-of select="$loanOffices/loan-offices/loan-office[@id=$officeId]/@name"/>
						</td>
					</xsl:if>
					<td class="listItem">
						<xsl:value-of select="$currency"/>
					</td>
					<td class="listItem">
                        <select name="selectedLoanTypes" onchange="onLoanTypeSelected('loanTypeId{concat($officeId,$currency)}','loanDuration{concat($officeId,$currency)}',this.value)">
						    <option value="">Недоступен</option>
							<xsl:for-each select="$types">
								<xsl:sort select="name"/>
								<xsl:variable name="conditionId" select="id"/>
								<option value="{concat($officeId,concat('-',$conditionId))}">
									<xsl:if test="$product//condition[./value[@name='conditionId' and text()=$conditionId] and ./value[@name='selected-office' and text()=$officeId]]">
										<xsl:attribute name="selected"/>
									</xsl:if>
									<xsl:value-of select="name"/>
								</option>
							</xsl:for-each>
						</select>
					</td>
					<xsl:variable name="conditionId" select="$product//condition[./value[@name='currency' and text()=$currency] and ./value[@name='selected-office' and text()=$officeId]]/value[@name='conditionId']/text()"/>
					<td class="listItem" id="loanTypeId{concat($officeId,$currency)}">
						<xsl:value-of select="$conditionId"/>&nbsp;
					</td>
					<td class="listItem" id="loanDuration{concat($officeId,$currency)}">
						<xsl:if test="$conditionId">
							<script>
								document.write(loanTypes['<xsl:value-of select="$conditionId"/>'].getDurationsString());
							</script>
						</xsl:if>
						&nbsp;
					</td>
				</tr>
			</xsl:for-each>

				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="javascript">
		<script>
			function durationsComparator(a,b){
				return a-b;
			}

			function loanType(id, currency, guaranteeCount){
				this.id = id;
				this.currency = currency;
				this.guaranteeCount = guaranteeCount;
				this.durations = new Array();

				this.getCurrency = function(){
					return this.currency;
				}

				this.getId = function(){
					return this.id;
				}

				this.getDurations = function(){
					return this.durations;
				}

				this.getGuaranteeCount = function(){
					if (this.guaranteeCount==""){
				    	return 0;
					}
					return this.guaranteeCount;
				}
			    this.getDurationsString = function(){
					if (this.durations.length==0){
				        return "";
					}
					this.durations.sort(durationsComparator);
					var result = this.durations[0]
					if (this.durations.length ==1){
						return result +" "+ selectSklonenie(result, months);
					}
					for (var i = 1; i &lt; this.durations.length; i++)
					{
						result += ", " + this.durations[i];
					}
					return result + " месяцев";
				}

				this.addDuration = function(duration){
					return this.durations[this.durations.length]=duration.substr(2);
				}
			}
			var loanTypes = new Array();
			<xsl:for-each select="$loanTypes//loan-type">
				type = new loanType('<xsl:value-of select="id"/>','<xsl:value-of select="currency"/>','<xsl:value-of select="guaranteeCount"/>');
				<xsl:for-each select="set:distinct(./duration)">
					type.addDuration('<xsl:value-of select="text()"/>');
				</xsl:for-each>
				loanTypes['<xsl:value-of select="id"/>']=type;
			</xsl:for-each>

			function onLoanTypeSelected(elementID, elementDuration, typeId){
				var idElement = document.getElementById(elementID);
				var durationElement = document.getElementById(elementDuration);
				if (typeId != ""){
					var type = loanTypes[typeId.substr(typeId.indexOf("-")+1)];
					idElement.innerHTML= type.getId();
					durationElement.innerHTML= type.getDurationsString();
				}else{
					idElement.innerHTML="&nbsp;";
					durationElement.innerHTML="&nbsp;";
				}
			}

			function checkField(fieldname, message){
			   var element = document.getElementsByName(fieldname)[0];

			   if (element == null || element.value==null|| element.value.length==0){
			      alert(message);
			      return false;
			   }
			   return true;
			}
			function checkFields()
			{
			   if (!checkField("field(name)", "Не заполнено поле [Имя кредитного продукта]")){
			      return false;
			   }
			   if (!checkField("field(loanKind)", "Не заполнено поле [Вид кредита]")){
			      return false;
			   }
			   if (!checkField("field(packageTemplate)", "Не заполнено поле [Пакет документов]")){
			      return false;
			   }
			   return checkSelectedLoanTypes();
			}

			function checkSelectedLoanTypes(){
			   var inputs=document.getElementsByName("selectedLoanTypes");
			   var selected=false;
			   var guaranteeCount = null;
			   var curentGuaranteeCount;
			   for (var i=0; i&lt;inputs.length;i++){
			      var typeId = inputs[i].value;
			      if (typeId!=""){
			         selected=true;
			         curentGuaranteeCount=loanTypes[typeId.substr(typeId.indexOf("-")+1)].getGuaranteeCount();
			         if (guaranteeCount == null){
			            guaranteeCount=curentGuaranteeCount;
			         }
			         if (guaranteeCount!=curentGuaranteeCount){
			            alert("Кредитный продукт содержит виды кредитов с разным количеством поручителей");
			            return false;
			         }
			      }
			   }
			   if (!selected){
			      alert("Выберите вид(ы) кредита");
			      return false;
			     }
			     return true;
			}

			function check(){
				return checkFields();
			}			
		</script>
	</xsl:template>
</xsl:stylesheet>
