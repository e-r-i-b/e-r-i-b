SBRF.dll симулирует работу с UPOS-терминалом, при выполненеии стратегии подтверждени€ через пластиковую карту клиента.

перед работой ќЅя«ј“≈Ћ№Ќќ необходимо зарегистрировать dll с помощью команды
regsvr32 "путь\им€.dll" 

возвращаетс€ карта номер 3008619728719560, если нужен другой номер карты
то нужно положить файл card_number.txt c номером карты в корень диска D

пример использовани€(JavaScript):

var activeXObject;
try
{
   activeXObject = createActvieXObject("SBRF.Server");
   activeXObject.Clear();
   if (activeXObject.NFun(5004) == "0")
   {
      getElement("field(cardNumber)").value = activeXObject.GParamString("ClientCard");
      activeXObject.Clear();
      createCommandButton('button.search', 'Ќайти').click('', false);
   }
   else
   {
      activeXObject.Clear();
      alert("");
   }
}
finally
{
                        
}
