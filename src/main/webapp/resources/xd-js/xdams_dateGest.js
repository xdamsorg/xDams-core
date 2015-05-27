/*function estremiCronologici(theArch, numDoc){percorso = "estremiCronologici.jsp?physDoc="+numDoc+"&theDb="+theArch;window.open(percorso,'gestioneXdams','status=no,resizable=no,scrollbars=auto,width=260, height=240');return false;}*/

/* ---- */
function chkLength(theInput, confronto){
	var ret = false;
	var confrontoArr = confronto.split(' ');
	var operatore = confrontoArr[0];
	var _length = parseInt(confrontoArr[1]);
	switch (operatore){
		case 'l': if ( theInput.value.length < _length ) ret = true;
			else isNumericMSG(notLessLen, _length -1);
			break;
		case 'g': if ( theInput.value.length > _length ) ret = true;
			else isNumericMSG(notLesslLen, _length + 1);
			break;
		case 'e': if ( theInput.value.length == _length ) ret = true;
			else isNumericMSG(notEqualLen, _length);
			break;
		case 'le': if ( theInput.value.length <= _length ) ret = true;
			else isNumericMSG(notGreatLen, _length);
			break;
		case 'ge': if ( theInput.value.length >= _length ) ret = true;
			else isNumericMSG(notGreatLen, _length);
			break;
		case 'ne': if ( theInput.value.length != _length ) ret = true;
			else isNumericMSG(notDiffLen, _length);
			break;
	}
	return ret;
}

/* ---- */
function IsNumeric(FieldToCheck, Mandatory, verbose){
 var Decimali = 9999;
 var DecimaliEsatti = false;
	if ( typeof(MAX_DECIMALI) != 'undefined' )  {
		if ( MAX_DECIMALI < 0 )  {
			Decimali = -MAX_DECIMALI;
			DecimaliEsatti = true;
		}
		else
			Decimali = MAX_DECIMALI;
	}
	if ( Mandatory && IsEmptyField(FieldToCheck) )  {
		return false;
	}
	var value = trim(FieldToCheck.value);
	if ( !value.length )
		return true;
	var chr;
 	var Virgola = false;
	if ( value != FieldToCheck.value )
		FieldToCheck.value = value;
	for ( var i = 0; i < value.length; ++i )  {
		chr = value.charAt(i);
		if ( chr == "-" || chr == "+" )  {
			if ( i != 0 )  {
				if ( verbose == true ) isNumericMSG(signBadPosition);
				FieldToCheck.focus();
				return false;
			}
		}
		else if ( chr == "," )  {
			if ( Virgola )  {
				if ( verbose == true ) isNumericMSG(errNumericAttr);
				FieldToCheck.focus();
				return false;
			}
			else if ( Decimali <= 0 )  {
				if ( verbose == true ) isNumericMSG(errDecimals);
				FieldToCheck.focus();
				return false;
			}
			else
				Virgola = true;
		}
		else if ( chr < "0" || chr > "9" )  {
			if ( verbose == true ) isNumericMSG(errNumericAttr);
			FieldToCheck.focus();
			return false;
		}
		else if ( Virgola )  {
			--Decimali;
			if ( Decimali < 0 )  {
				if ( verbose == true ) isNumericMSG(errDecimals);
				FieldToCheck.focus();
				return false;
			}
		}
	}
		if ( DecimaliEsatti )  {
	   if ( Decimali == -MAX_DECIMALI )  {
   		if ( !Virgola )
   			FieldToCheck.value += ',';
	   	for ( ; Decimali > 0; --Decimali )  {
	   		FieldToCheck.value += '0';
	   	}
	   }
	   else if ( Decimali )  {
			if ( verbose == true ) isNumericMSG(errDecimalsMAX_DECIMALI, -MAX_DECIMALI);
			FieldToCheck.focus();
			return false;
		}
	}
	return true;
}

/* ---- */
function calcolaMese(ilMese){
	meseCalcolato = "";
	if(ilMese == "01"){meseCalcolato = "gennaio"}
	if(ilMese == "02"){meseCalcolato = "febbraio"}
	if(ilMese == "03"){meseCalcolato = "marzo"}
	if(ilMese == "04"){meseCalcolato = "aprile"}
	if(ilMese == "05"){meseCalcolato = "maggio"}
	if(ilMese == "06"){meseCalcolato = "giugno"}
	if(ilMese == "07"){meseCalcolato = "luglio"}
	if(ilMese == "08"){meseCalcolato = "agosto"}
	if(ilMese == "09"){meseCalcolato = "settembre"}
	if(ilMese == "10"){meseCalcolato = "ottobre"}
	if(ilMese == "11"){meseCalcolato = "novembre"}
	if(ilMese == "12"){meseCalcolato = "dicembre"}
return meseCalcolato;
}

function calcolaMeseContr(ilMese){
	meseCalcolato = "";
	if(ilMese == "01"){meseCalcolato = "gen."}
	if(ilMese == "02"){meseCalcolato = "feb."}
	if(ilMese == "03"){meseCalcolato = "mar."}
	if(ilMese == "04"){meseCalcolato = "apr."}
	if(ilMese == "05"){meseCalcolato = "mag."}
	if(ilMese == "06"){meseCalcolato = "giu."}
	if(ilMese == "07"){meseCalcolato = "lug."}
	if(ilMese == "08"){meseCalcolato = "ago."}
	if(ilMese == "09"){meseCalcolato = "set."}
	if(ilMese == "10"){meseCalcolato = "ott."}
	if(ilMese == "11"){meseCalcolato = "nov."}
	if(ilMese == "12"){meseCalcolato = "dic."}
return meseCalcolato;
}

/* ---- */
function Trim(strText) //Ver 1.0
{
	var s=strText;
	if(s.length != 0){
		while ((s.indexOf(" ")==0) && (s.length > 0)) s=s.slice(1);
		if(s.length != 0){
			while (s.lastIndexOf(" ")==s.length-1) s=s.slice(0,s.length-1);
		}
	}
	return s;
 }



function myReplaceAll(str,replaceWhat,replaceTo){
    replaceWhat = replaceWhat.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
    var re = new RegExp(replaceWhat, 'g');
    return str.replace(re,replaceTo);
}

function dataNormalMulti(thisObj, nomeForm, riempi, giorno_I, mese_I, anno_I, giorno_F, mese_F, anno_F, riempiTEXT, campoSEC, ilFormatoString) //Ver 1.3
{
ilFormato = "gg.mm.aaaa" //default
//ilFormato = "aaaa mese gg" //default
var indexToChange = $(thisObj).attr('name').substring($(thisObj).attr('name').lastIndexOf('['),$(thisObj).attr('name').lastIndexOf(']')+1);
var lastIndexRec = giorno_I.substring(giorno_I.lastIndexOf('['),giorno_I.lastIndexOf(']')+1);
riempi =myReplaceAll( riempi, lastIndexRec, indexToChange);
giorno_I =myReplaceAll( giorno_I, lastIndexRec, indexToChange);
mese_I =myReplaceAll( mese_I, lastIndexRec, indexToChange);
anno_I =myReplaceAll( anno_I, lastIndexRec, indexToChange);
giorno_F =myReplaceAll( giorno_F, lastIndexRec, indexToChange);
mese_F =myReplaceAll( mese_F, lastIndexRec, indexToChange);
anno_F =myReplaceAll( anno_F, lastIndexRec, indexToChange);
riempiTEXT = myReplaceAll(riempiTEXT,lastIndexRec, indexToChange);
campoSEC =myReplaceAll(campoSEC, lastIndexRec, indexToChange);

var gg = ""
var mm = ""
var aaaa=""
var ggF = ""
var mmF = ""
var aaaaF=""
eval('ilForm = document.'+nomeForm+'')

riempiInput = ilForm[riempi]
 if(riempiInput==null){
 riempiInput = riempi;
 }

if(ilForm[giorno_I] != null && giorno_I.length != 2){
// alert(ilForm[giorno_I].name.length)
giorno_i = Trim(ilForm[giorno_I].value)
}else{
giorno_i = giorno_I

}
if(ilForm[mese_I] != null && mese_I.length != 2){
	mese_i = Trim(ilForm[mese_I].value)
}else{
	mese_i = mese_I
}
if(ilForm[anno_I] != null && anno_I.length != 4){
	anno_i = Trim(ilForm[anno_I].value)
}else{
	anno_i = anno_I
}


giorno_f = "";
mese_f = "";
anno_f = "";

if(giorno_F == null){
	giorno_F = giorno_I
	giorno_f = ""
	ilFormatoString = campoSEC //i parametri slittano in caso di data singola
}else{
	if(ilForm[giorno_F] != null && giorno_F.length != 2){
		giorno_f = Trim(ilForm[giorno_F].value)
	}else{
		giorno_f = giorno_F
	}		
	
}
if(mese_F == null){
	mese_F = mese_I
	mese_f = ""
	ilFormatoString = campoSEC //i parametri slittano in caso di data singola
}else{ 
	if(ilForm[mese_F] != null && mese_F.length != 2){
		mese_f = Trim(ilForm[mese_F].value)
	}else{
		mese_f = mese_F
	}		
}
if(anno_F == null){
	anno_F = anno_I
	anno_f = ""		
	ilFormatoString = campoSEC //i parametri slittano in caso di data singola
}else{		
	if(ilForm[anno_F] != null && anno_F.length != 4){
		anno_f = Trim(ilForm[anno_F].value)
	}else{
		anno_f = anno_F
	}	
}
if(ilFormatoString != "" && ilFormatoString != "undefined" && ilFormatoString != null && typeof(ilFormatoString) != "undefined"){
	ilFormato = ilFormatoString
}

giorno_i_num=parseInt(giorno_i,10)
giorno_f_num=parseInt(giorno_f,10)
anno_i_num=parseInt(anno_i,10)
anno_f_num=parseInt(anno_f,10)
mese_i_num=parseInt(mese_i,10)
mese_f_num=parseInt(mese_f,10)
var bissInizio = false;
monthstart=mese_i
daystart=giorno_i
bisstart=anno_i/4
if(bisstart!=parseInt(bisstart,10)){
   bissInizio = true;
}
if(daystart>28 && monthstart==2 && bisstart!=parseInt(bisstart,10))
{alert(getLocalizedString('Attenzione_anno_inizio_bisestile','Attenzione anno inizio bisestile'))
ilForm[giorno_I].focus()
return}
var bissFine = false;
monthend=mese_f
dayend=giorno_f
bisend=anno_f/4
if(bisend!=parseInt(bisend,10)){
   bissFine = true;
}
if(dayend>28 && monthend==2 && bisend!=parseInt(bisend,10))
{alert(getLocalizedString('Attenzione_anno_fine_bisestile','Attenzione anno fine bisestile'))
ilForm[giorno_F].focus()
return}
if((giorno_i.length == 1)||(giorno_f.length == 1))
	{
   	   if(giorno_i.length == 1){
   	   	giorno_i = '0'+giorno_i
   	    }
	   if(giorno_f.length == 1){
	   	giorno_f = '0'+giorno_f
   	   }
	}

if((giorno_i > 31)||(giorno_f > 31))
	{
   	   alert(getLocalizedString('inserire_un_giorno_compreso_tra_1_e_31_a_seconda_del_mese','inserire un giorno compreso tra 1 e 31 a seconda del mese')+giorno_i+'\n'+giorno_f)
   	  
   	  return false
   	}
if((mese_i.length == 1)||(mese_f.length == 1))
	{
   	   if(mese_i.length == 1)
   	   	mese_i = '0'+mese_i
	   if(mese_f.length == 1)
   	   	mese_f = '0'+mese_f
	}
if((mese_i > 12)||(mese_f > 12))
	{
		 
	//  alert('inserire un mese compreso tra 1 e 12')
	   return false
	}
if((anno_i.length == 1)||(anno_f.length == 1)||(anno_i.length == 2)||(anno_f.length == 2)||(anno_i.length == 3)||(anno_f.length == 3))
	{
	   alert(getLocalizedString('comporre_lanno_nella_forma_aaaa','comporre l\'anno nella forma aaaa'))
	   return false
	}
//fine test di corretto inserimento
//alert("anno_i "+anno_i+"\n"+"mese_i "+mese_i+"\n"+"giorno_i "+giorno_i+"\n"+"anno_f "+anno_f+"\n"+"mese_f "+mese_f+"\n"+"giorno_f "+giorno_f+"\n")
if(anno_i != "")
   {
	aaaa = anno_i
	if(mese_i >0)
	   {
	      mm = mese_i
	      if( (mese_f=="" && giorno_f=="" && anno_f=="") ){
	      		if((mese_i!="" && giorno_i!="" && anno_i!="")){
	      		ggF = giorno_i;
		      	mmF = mm;
		      	aaaaF = aaaa;
	      		}else{
	      		ggF = "31";
		      	mmF = mm;
		      	aaaaF = aaaa;
	      		}
	        }
	      if((giorno_i != "")&&(giorno_i != " ")){
	      	gg = giorno_i
	       }
	      else{
	   	gg = "01"
	      }
	   }
	else{
	    mm = "01"
	    gg = "01"
	}
	if((mese_f=="" && giorno_f=="" && anno_f=="") && mese_i==""){
	      	ggF = "31";
	      	mmF = "12";
	      	aaaaF = aaaa;
	  }
if(anno_f != "")
   {
	aaaaF = anno_f
	if((mese_f != "")&&(giorno_f != " ") && (mmF!="" && ggF!=""))
	 {
	   mmF = mese_f
	   if((giorno_f != "")&&(giorno_f != " ")){
	      ggF = giorno_f
	   }
	   else{
	      if((mmF == "11")||(mmF == "04")||(mmF == "06")||(mmF == "09")){
	      	ggF = "30"
	      }
	      else
	      	{
	      	   if(mmF == "02"){
	      	   	if(!bissFine){
	      	   	  ggF = "29"
	      	   	}else{
	      	   	  ggF = "28"
	      	   	 }
	      	   }else{
	      	   	ggF = "31"
	      	    }
	      	}
	    }
	}
	else
	 {
	 if((mese_f!="" && giorno_f!="")){
	   mmF = mese_f
	   ggF = giorno_f
	 }else{
	 if((mese_f!="" && giorno_f=="")){
	   mmF=mese_f
	   ggF = "31"
	 }else{
	   mmF = "12"
	   ggF = "31"
	}
	 }
	 }
	 if((mese_i=="" && giorno_i=="" && anno_i=="")){
	      	gg = ggF;
	      	mm = mmF;
	      	aaaa = aaaaF;
	  }
   }
if(giorno_f!="" && mese_f!="" && anno_f!=""){
inDay=parseInt(giorno_i,10);
fDay=parseInt(giorno_f,10);
inMonth=mese_i;
fMonth=parseInt(mese_f,10);
inYear  = parseInt(anno_i,10);
fYear  = parseInt(anno_f,10);
var ok = true;
if (inYear>fYear){
   ok=false;
if(ilForm[anno_F]!=null){
	ilForm[anno_F].value  ="";
	ilForm[anno_F].focus()
}
}
else if (inYear<fYear) {
	ok=true;
}
else{
	if (inMonth > fMonth) {
		ok=false;
			ilForm[mese_F].value  ="";
			ilForm[mese_F].focus()
	}
	else if (inMonth < fMonth) {ok=true;}
		else{
			if (inDay > fDay) {
			   ok=false;
			ilForm[giorno_F].value  ="";
			ilForm[giorno_F].focus()
			}
			else ok=true;
		}
	}
	if (!ok) {
		//alert("La data fine deve essere successiva alla data inizio.");
		aaaaF="";
		mmF="";
		ggF="";
	}
}
   	/*
	alert("giorno "+giorno_i+" --> "+giorno_f)
	alert("mese "+mese_i+" --> "+mese_f)
   	alert("anno "+anno_i+" --> "+anno_f)
   	*/
   /*if((aaaaF!="" && mmF=="12" && ggF=="31") && (aaaa=="" && mm=="" && gg=="")){
   	aaaa=aaaaF;
	mm="01";
	gg="01";
   }*/
   if((anno_F=="" && mese_F=="" && giorno_F=="") && (aaaa!="" && mm!="" && gg!="")){
        aaaaF=aaaa;
	mmF=mm;
	ggF=gg;
   }
   dataIniziale = aaaa+mm+gg;
   dataFinale = aaaaF+mmF+ggF;
   dataDefinitiva = "";
   
    if(dataIniziale.length == 8)
    {
   	dataDefinitiva = dataIniziale
   	if(dataIniziale.length == 8)
   	{
   		dataDefinitiva = dataIniziale
		if(dataFinale.length == 8)
		{
			dataDefinitiva += "-"+dataFinale
		}
		else
			dataDefinitiva += "-"+dataIniziale
   	}
			riempiInput.value  =dataDefinitiva;
    }
    else
    	riempiInput.value  ="";
    if(riempiTEXT != null){
   	if(giorno_i_num == giorno_f_num && mese_i_num == mese_f_num && anno_i_num == anno_f_num)
		{
			newData="";
			newData = calcolaDataSingola(giorno_i,mese_i,anno_i,ilFormato,gg,mm,aaaa);
			try{
 				ilForm[riempiTEXT].value  = newData;
 			}catch(e){
 			riempiTEXT.value  = newData;
 			}
		}
   	else
   		{
			newDataIni="";
			newDataFin="";
   			//alert('data doppia')
			if(anno_i>0)
				{
					newDataIni = calcolaDataSingola(giorno_i,mese_i,anno_i,ilFormato,gg,mm,aaaa);
					if(giorno_f>0 || mese_f>0 || anno_f>0)
					{
						newDataFin = calcolaDataSingola(giorno_f,mese_f,anno_f,ilFormato,ggF,mmF,aaaaF)
					}
					newData = newDataIni;
					if(newDataFin != "")
						newData = newData + ' - ' + newDataFin
						try{
							ilForm[riempiTEXT].value  = newData;
						}catch(e){
						riempiTEXT.value  = newData;
						}					//	alert(eval('document.'+nomeForm+'[\''+riempiTEXT+'\'].name'))
				}
			else{
			try{
 				ilForm[riempiTEXT].value  = "";
 			}catch(e){
 			riempiTEXT.value  = "";
 			}
				//alert(eval('document.'+nomeForm+'[\''+riempiTEXT+'\'].name') )
			}
   		}
	}
	}
	else
	{
			try{
 				ilForm[riempiTEXT].value  = "";
 			}catch(e){
 			riempiTEXT.value  = "";
 			}
		riempiInput.value  ="";
	if(ilForm[campoSEC] != null)
	{
		secolo = ilForm[campoSEC].value ;
		secoloVal = ""
		if(secolo == "XXI")
			secoloVal = '20000101-20991231'
		if(secolo == "XX")
			secoloVal = '19000101-19991231'
		if(secolo == "XIX")
			secoloVal = '18000101-18991231'
		if(secolo == "XVIII")
			secoloVal = '17000101-17991231'
		if(secolo == "XVII")
			secoloVal = '16000101-16991231'
		if(secolo == "XVI")
			secoloVal = '15000101-15991231'
		if(secolo == "XV")
			secoloVal = '14000101-14991231'
		if(secolo == "XIV")
			secoloVal = '13000101-13991231'
		if(secolo == "XIII")
			secoloVal = '12000101-12991231'
		if(secolo == "XII")
			secoloVal = '11000101-11991231'
		if(secolo == "XI")
			secoloVal = '10000101-10991231'
		if(secolo == "X")
			secoloVal = '09000101-09991231'
		riempiInput.value =secoloVal;
	}
	}
return true
}

/* ---- */
function dataNormal(nomeForm, riempi, giorno_I, mese_I, anno_I, giorno_F, mese_F, anno_F, riempiTEXT, campoSEC, ilFormatoString) //Ver 1.3
    {
	ilFormato = "gg.mm.aaaa" //default
	//ilFormato = "aaaa mese gg" //default
	
	// alert('nomeForm '+nomeForm+'\nriempi '+riempi+'\ngiorno_I '+giorno_I+'\nmese_I '+mese_I+'\nanno_I '+anno_I+'\ngiorno_F '+giorno_F+'\nmese_F '+mese_F+'\nanno_F '+anno_F+'\nriempiTEXT '+riempiTEXT+'\ncampoSEC '+campoSEC+'\nilFormatoString '+ilFormatoString)
   	var gg = ""
	var mm = ""
	var aaaa=""
	var ggF = ""
	var mmF = ""
	var aaaaF=""
	eval('ilForm = document.'+nomeForm+'')
 	
 	riempiInput = ilForm[riempi]
 
	 if(riempiInput==null){
	 riempiInput = riempi;
	 }
	
if(ilForm[giorno_I] != null && giorno_I.length != 2){
	// alert(ilForm[giorno_I].name.length)
	giorno_i = Trim(ilForm[giorno_I].value)
 }else{
	giorno_i = giorno_I
 
}
if(ilForm[mese_I] != null && mese_I.length != 2){
 	mese_i = Trim(ilForm[mese_I].value)
 }else{
 	mese_i = mese_I
 }
if(ilForm[anno_I] != null && anno_I.length != 4){
 	anno_i = Trim(ilForm[anno_I].value)
}else{
  	anno_i = anno_I
}
 
	
	giorno_f = ""
	mese_f = ""
	anno_f = ""
	
	if(giorno_F == null){
		giorno_F = giorno_I
		giorno_f = ""
		ilFormatoString = campoSEC //i parametri slittano in caso di data singola
	}else{
		if(ilForm[giorno_F] != null && giorno_F.length != 2){
			giorno_f = Trim(ilForm[giorno_F].value)
		}else{
			giorno_f = giorno_F
		}		
		
	}
	if(mese_F == null){
		mese_F = mese_I
		mese_f = ""
		ilFormatoString = campoSEC //i parametri slittano in caso di data singola
	}else{ 
		if(ilForm[mese_F] != null && mese_F.length != 2){
			mese_f = Trim(ilForm[mese_F].value)
		}else{
			mese_f = mese_F
		}		
	}
	if(anno_F == null){
		anno_F = anno_I
		anno_f = ""		
		ilFormatoString = campoSEC //i parametri slittano in caso di data singola
	}else{		
		if(ilForm[anno_F] != null && anno_F.length != 4){
			anno_f = Trim(ilForm[anno_F].value)
		}else{
			anno_f = anno_F
		}	
	}
	if(ilFormatoString != "" && ilFormatoString != "undefined" && ilFormatoString != null && typeof(ilFormatoString) != "undefined"){
		ilFormato = ilFormatoString
	}
	
	giorno_i_num=parseInt(giorno_i,10)
	giorno_f_num=parseInt(giorno_f,10)
	anno_i_num=parseInt(anno_i,10)
	anno_f_num=parseInt(anno_f,10)
	mese_i_num=parseInt(mese_i,10)
	mese_f_num=parseInt(mese_f,10)
	var bissInizio = false;
	monthstart=mese_i
	daystart=giorno_i
	bisstart=anno_i/4
	if(bisstart!=parseInt(bisstart,10)){
	   bissInizio = true;
	}
	if(daystart>28 && monthstart==2 && bisstart!=parseInt(bisstart,10))
	{alert(getLocalizedString('Attenzione_anno_inizio_bisestile','Attenzione anno inizio bisestile'))
	ilForm[giorno_I].focus()
	return}
	var bissFine = false;
	monthend=mese_f
	dayend=giorno_f
	bisend=anno_f/4
	if(bisend!=parseInt(bisend,10)){
	   bissFine = true;
	}
	if(dayend>28 && monthend==2 && bisend!=parseInt(bisend,10))
	{alert(getLocalizedString('Attenzione_anno_fine_bisestile','Attenzione anno fine bisestile'))
	ilForm[giorno_F].focus()
	return}
	if((giorno_i.length == 1)||(giorno_f.length == 1))
		{
	   	   if(giorno_i.length == 1){
	   	   	giorno_i = '0'+giorno_i
	   	    }
		   if(giorno_f.length == 1){
		   	giorno_f = '0'+giorno_f
	   	   }
		}
 
	if((giorno_i > 31)||(giorno_f > 31))
		{
	   	   alert(getLocalizedString('inserire_un_giorno_compreso_tra_1_e_31_a_seconda_del_mese','inserire un giorno compreso tra 1 e 31 a seconda del mese')+giorno_i+'\n'+giorno_f)
	   	  
	   	  return false
	   	}
	if((mese_i.length == 1)||(mese_f.length == 1))
		{
	   	   if(mese_i.length == 1)
	   	   	mese_i = '0'+mese_i
		   if(mese_f.length == 1)
	   	   	mese_f = '0'+mese_f
		}
	if((mese_i > 12)||(mese_f > 12))
		{
			 
		//  alert('inserire un mese compreso tra 1 e 12')
		   return false
		}
	if((anno_i.length == 1)||(anno_f.length == 1)||(anno_i.length == 2)||(anno_f.length == 2)||(anno_i.length == 3)||(anno_f.length == 3))
		{
		   alert((getLocalizedString('comporre_lanno_nella_forma_aaaa','comporre l\'anno nella forma aaaa')))
		   return false
		}
// fine test di corretto inserimento
	//alert("anno_i "+anno_i+"\n"+"mese_i "+mese_i+"\n"+"giorno_i "+giorno_i+"\n"+"anno_f "+anno_f+"\n"+"mese_f "+mese_f+"\n"+"giorno_f "+giorno_f+"\n")
	if(anno_i != "")
	   {
		aaaa = anno_i
		if(mese_i >0)
		   {
		      mm = mese_i
		      if( (mese_f=="" && giorno_f=="" && anno_f=="") ){
		      		if((mese_i!="" && giorno_i!="" && anno_i!="")){
		      		ggF = giorno_i;
			      	mmF = mm;
			      	aaaaF = aaaa;
		      		}else{
		      		ggF = "31";
			      	mmF = mm;
			      	aaaaF = aaaa;
		      		}
		        }
		      if((giorno_i != "")&&(giorno_i != " ")){
		      	gg = giorno_i
		       }
		      else{
		   	gg = "01"
		      }
		   }
		else{
		    mm = "01"
		    gg = "01"
		}
		if((mese_f=="" && giorno_f=="" && anno_f=="") && mese_i==""){
		      	ggF = "31";
		      	mmF = "12";
		      	aaaaF = aaaa;
		  }
	if(anno_f != "")
	   {
		aaaaF = anno_f
		if((mese_f != "")&&(giorno_f != " ") && (mmF!="" && ggF!=""))
		 {
		   mmF = mese_f
		   if((giorno_f != "")&&(giorno_f != " ")){
		      ggF = giorno_f
		   }
		   else{
		      if((mmF == "11")||(mmF == "04")||(mmF == "06")||(mmF == "09")){
		      	ggF = "30"
		      }
		      else
		      	{
		      	   if(mmF == "02"){
		      	   	if(!bissFine){
		      	   	  ggF = "29"
		      	   	}else{
		      	   	  ggF = "28"
		      	   	 }
		      	   }else{
		      	   	ggF = "31"
		      	    }
		      	}
		    }
		}
		else
		 {
		 if((mese_f!="" && giorno_f!="")){
		   mmF = mese_f
		   ggF = giorno_f
		 }else{
		 if((mese_f!="" && giorno_f=="")){
		   mmF=mese_f
		   ggF = "31"
		 }else{
		   mmF = "12"
		   ggF = "31"
		}
		 }
		 }
		 if((mese_i=="" && giorno_i=="" && anno_i=="")){
		      	gg = ggF;
		      	mm = mmF;
		      	aaaa = aaaaF;
		  }
	   }
	if(giorno_f!="" && mese_f!="" && anno_f!=""){
	inDay=parseInt(giorno_i,10);
	fDay=parseInt(giorno_f,10);
	inMonth=mese_i;
	fMonth=parseInt(mese_f,10);
	inYear  = parseInt(anno_i,10);
	fYear  = parseInt(anno_f,10);
	var ok = true;
	if (inYear>fYear){
	   ok=false;
	if(ilForm[anno_F]!=null){
		ilForm[anno_F].value  ="";
		ilForm[anno_F].focus()
	}
	}
	else if (inYear<fYear) {
		ok=true;
	}
	else{
		if (inMonth > fMonth) {
			ok=false;
				ilForm[mese_F].value  ="";
				ilForm[mese_F].focus()
		}
		else if (inMonth < fMonth) {ok=true;}
			else{
				if (inDay > fDay) {
				   ok=false;
				ilForm[giorno_F].value  ="";
				ilForm[giorno_F].focus()
				}
				else ok=true;
			}
		}
		if (!ok) {
			//alert("La data fine deve essere successiva alla data inizio.");
			aaaaF="";
			mmF="";
			ggF="";
		}
	}
	   	/*
		alert("giorno "+giorno_i+" --> "+giorno_f)
		alert("mese "+mese_i+" --> "+mese_f)
	   	alert("anno "+anno_i+" --> "+anno_f)
	   	*/
	   /*if((aaaaF!="" && mmF=="12" && ggF=="31") && (aaaa=="" && mm=="" && gg=="")){
	   	aaaa=aaaaF;
		mm="01";
		gg="01";
	   }*/
	   if((anno_F=="" && mese_F=="" && giorno_F=="") && (aaaa!="" && mm!="" && gg!="")){
	        aaaaF=aaaa;
		mmF=mm;
		ggF=gg;
	   }
	   dataIniziale = aaaa+mm+gg;
	   dataFinale = aaaaF+mmF+ggF;
	   dataDefinitiva = "";
	    if(dataIniziale.length == 8)
	    {
	   	dataDefinitiva = dataIniziale
	   	if(dataIniziale.length == 8)
	   	{
	   		dataDefinitiva = dataIniziale
			if(dataFinale.length == 8)
			{
				dataDefinitiva += "-"+dataFinale
			}
			else
				dataDefinitiva += "-"+dataIniziale
	   	}
				riempiInput.value  =dataDefinitiva;
	    }
	    else
	    	riempiInput.value  ="";
	    if(riempiTEXT != null){
	   	if(giorno_i_num == giorno_f_num && mese_i_num == mese_f_num && anno_i_num == anno_f_num)
			{
				newData="";
				newData = calcolaDataSingola(giorno_i,mese_i,anno_i,ilFormato,gg,mm,aaaa);
				try{
	 				ilForm[riempiTEXT].value  = newData;
	 			}catch(e){
	 			riempiTEXT.value  = newData;
	 			}
			}
	   	else
	   		{
				newDataIni="";
				newDataFin="";
	   			//alert('data doppia')
				if(anno_i>0)
					{
						newDataIni = calcolaDataSingola(giorno_i,mese_i,anno_i,ilFormato,gg,mm,aaaa);
						if(giorno_f>0 || mese_f>0 || anno_f>0)
						{
							newDataFin = calcolaDataSingola(giorno_f,mese_f,anno_f,ilFormato,ggF,mmF,aaaaF)
						}
						newData = newDataIni;
						if(newDataFin != "")
							newData = newData + ' - ' + newDataFin
							try{
								ilForm[riempiTEXT].value  = newData;
							}catch(e){
							riempiTEXT.value  = newData;
							}					//	alert(eval('document.'+nomeForm+'[\''+riempiTEXT+'\'].name'))
					}
				else{
				try{
	 				ilForm[riempiTEXT].value  = "";
	 			}catch(e){
	 			riempiTEXT.value  = "";
	 			}
					//alert(eval('document.'+nomeForm+'[\''+riempiTEXT+'\'].name') )
				}
	   		}
		}
   	}
   	else
   	{
				try{
	 				ilForm[riempiTEXT].value  = "";
	 			}catch(e){
	 			riempiTEXT.value  = "";
	 			}
   		riempiInput.value  ="";
		if(ilForm[campoSEC] != null)
		{
			secolo = ilForm[campoSEC].value ;
			secoloVal = ""
			if(secolo == "XXI")
				secoloVal = '20000101-20991231'
			if(secolo == "XX")
				secoloVal = '19000101-19991231'
			if(secolo == "XIX")
				secoloVal = '18000101-18991231'
			if(secolo == "XVIII")
				secoloVal = '17000101-17991231'
			if(secolo == "XVII")
				secoloVal = '16000101-16991231'
			if(secolo == "XVI")
				secoloVal = '15000101-15991231'
			if(secolo == "XV")
				secoloVal = '14000101-14991231'
			if(secolo == "XIV")
				secoloVal = '13000101-13991231'
			if(secolo == "XIII")
				secoloVal = '12000101-12991231'
			if(secolo == "XII")
				secoloVal = '11000101-11991231'
			if(secolo == "XI")
				secoloVal = '10000101-10991231'
			if(secolo == "X")
				secoloVal = '09000101-09991231'
			riempiInput.value =secoloVal;
		}
   	}
   return true
   }

/* ---- */
function dataNormale(nomeForm,riempi,data_ini,data_fine,variante,visualizzata) //Ver 1.1
   {
   	var gg = ""
	var mm = ""
	var aaaa=""
	var ggF = ""
	var mmF = ""
	var aaaaF=""
	
/*
	alert(data_ini);
	alert(data_fine);
	alert(riempi.name);
	alert(visualizzata.name);
*/

if(variante != 1){
	eval('dataini = Trim(document.'+nomeForm+'[\''+data_ini+'\'].value)')
	eval('datafine = Trim(document.'+nomeForm+'[\''+data_fine+'\'].value)')
}else{
	dataini = Trim(data_ini.value)
	datafine = Trim(data_fine.value)
}
	dataini = dataini.split('/');
	if (dataini.length == 3){
		gg = dataini[0];
		mm = dataini[1];
		aaaa = dataini[2];
	}
	//alert(dataini)
	datafine = datafine.split('/');
	
	if (datafine.length == 3){
		ggF = datafine[0];
		mmF = datafine[1];
		aaaaF = datafine[2];
	}else{
		ggF = gg
		mmF = mm
		aaaaF = aaaa
	}
	//alert(datafine)
if(aaaa.length==4 && mm.length==2 && gg.length==2 && aaaaF.length==4 && mmF.length==2 && ggF.length==2){
	if(variante != 1){	            
		//eval('document.'+nomeForm+'[\''+riempi+'\'].value = \''+aaaa+mm+gg+'-'+aaaaF+mmF+ggF+'\'')
	}
	else{
		//riempi.value = aaaa+mm+gg+'-'+aaaaF+mmF+ggF
	}
	// alert("QUIIII"+visualizzata);	
	if(visualizzata!=null && (aaaa+mm+gg+'-'+aaaaF+mmF+ggF)!='00000000-00000000'){

		dataNormal(nomeForm,riempi,gg,mm,aaaa,ggF,mmF,aaaaF,visualizzata,null,"gg.mm.aaaa") //Ver 1.3
	}	
	
}
else
{
	if(variante != 1)	
	{	            
		eval('document.'+nomeForm+'[\''+riempi+'\'].value = \'\'')
	}
	else
	{
	
	if((riempi.value !='') && aaaa.length==0 && mm.length==0 && gg.length==0 && aaaaF.length==0 && mmF.length==0 && ggF.length==0 && riempi.value.length == '17'){
			//sono in modifica
			laData = riempi.value.split("-")
			laDataIniziale = laData[0].substring(6,8)+"/"+laData[0].substring(4,6)+"/"+laData[0].substring(0,4)	
			laDataFinale = laData[1].substring(6,8)+"/"+laData[1].substring(4,6)+"/"+laData[1].substring(0,4)	
			
			//alert(laDataIniziale)
			//alert(laDataFinale)
			data_ini.value = laDataIniziale
			data_fine.value = laDataFinale
				
		}
	else
		{
			 riempi.value = '';
			 visualizzata.value='';			
		}
	}
}
	
	
	
	
	return true
	   
   }
   

/* ---- */
function calcolaDataSingola(giorno_i,mese_i,anno_i,ilFormato,gg,mm,aaaa){
	newData = "";
if(anno_i != ''){
	if(ilFormato == 'gg.mm.aaaa'){
	//alert('data singola')
	if(giorno_i>0)
		newData += gg+'.'
	if(mese_i>0)
		newData += mm+'.'
	if(anno_i>0)
		newData += aaaa
	}else if(ilFormato == 'gg/mm/aaaa'){
		//alert('data singola')
		if(giorno_i>0)
			newData += gg+'/'
		if(mese_i>0)
			newData += mm+'/'
		if(anno_i>0)
			newData += aaaa
	} else{
	if(ilFormato == 'aaaa mese gg'){
		if(anno_i>0)
			newData += aaaa+' '
		if(mese_i>0)
			newData += calcolaMese(mese_i)+' '
		if(giorno_i>0)
			newData += gg
		}
		else{
		if(ilFormato == 'gg mese aaaa'){
			if(giorno_i>0)
				newData += gg+' '
			if(mese_i>0)
				newData += calcolaMese(mese_i)+' '
			if(anno_i>0)
				newData += aaaa
			}else{
			
			if(ilFormato == 'aaaa mese g'){
				if(anno_i>0)
					newData += aaaa+' '
				if(mese_i>0)
					newData += calcolaMese(mese_i)+' '
				if(giorno_i>0)
					if(gg!='')
						newData += parseInt(gg,10)
					else
						newData += gg
				}else{
				
				if(ilFormato == 'aaaa mm. g'){
				if(anno_i>0)
					newData += aaaa+' '
				if(mese_i>0)
					newData += calcolaMeseContr(mese_i)+' '
				if(giorno_i>0)
					if(gg!='')
						newData += parseInt(gg,10)
					else
						newData += gg
				}else{
				if(ilFormato == 'aaaa mm. gg'){
						if(anno_i>0)
							newData += aaaa+' '
						if(mese_i>0)
							newData += calcolaMese(mese_i).substring(0,3)+'. '
						if(giorno_i>0)
							if(gg!='')
								newData += gg
					}else{
						if(ilFormato == 'aaaa/mm/gg'){
							if(anno_i>0)
								newData += aaaa+''
							if(mese_i>0){
								if(anno_i>0){
								newData += '/'
								}
								newData += mese_i
								if(giorno_i>0){
								newData += '/'
								}
							}	
							if(giorno_i>0)
								if(gg!='')
									newData += gg
						}
					
					}	
				
				
				}
				}		
			
			}
		}

	}
  }
	return newData;
}

/* ---- */
function allowOnly() {
       var keycode = event.keyCode;
       var realkey = String.fromCharCode(event.keyCode);
		ritorno = true;
	for(i=0;i<arguments.length;i++){
		if(realkey == arguments[i]){
			ritorno = false;
			}
	}
	if (ritorno)
		  event.keyCode=0;
}

/* ---- */
function allowOnlyNumbers() {
        var keycode = event.keyCode;
        var realkey = String.fromCharCode(event.keyCode);
	if (!isFinite(parseInt(realkey)) ) {
	  event.keyCode=0;
	}
}


/* ---- */
function oraNormal(campoIni,campoFine) {
 	 //campoImpValue = campoImp.value;
 	 //alert("campoIni "+campoIni);
 	 //alert("campoFine "+campoFine);
		 campoINIValue = campoIni.value;
		 campoFINValue = campoFine.value;
		oraini = campoINIValue.split(':');
		oraIniziale="";
		if (oraini.length == 2){
			//hh = oraini[0];
			//mm = oraini[1];
			oraIniziale = oraini[0]+""+oraini[1];
		}
		oraFine = campoFINValue.split(':');
		oraFinale="";
		if (oraFine.length == 2){
			//hh = oraFine[0];
			//mm = oraFine[1];
			oraFinale = oraFine[0]+""+oraFine[1];
		} 	 	
 
		//alert((findInput(0,campoIni,2)).value);
		//alert()
		
		if((findInput(0,campoIni,2)).value==""){
			(findInput(0,campoIni,2)).value = oraIniziale +"-"+ oraFinale;
		}
		if((oraIniziale!="" && oraFinale!="" && (findInput(0,campoIni,2)).value!=oraIniziale +"-"+ oraFinale)){
			(findInput(0,campoIni,2)).value = oraIniziale +"-"+ oraFinale;
		}
		
		
		 
}

/* ---- */
function normalizzaOra(campoImp) {
	campoImpValue = campoImp.value;
	if(campoImpValue.length==11){
		
		ore = campoImpValue.substring(0,2);
		minuti = campoImpValue.substring(3,5);
		secondi = campoImpValue.substring(6,7);
		
		oreInt = parseInt(ore,10) * 60;
		minutiInt = parseInt(minuti,10);
		secondiInt =  parseInt(secondi,10);
		if(minutiInt>59){
			alert(getLocalizedString('Attenzione_minuti_non_validi','Attenzione: "minuti" non validi')+"!")
		}else{
			if(secondiInt>59){
				alert(getLocalizedString('Attenzione_secondi_non_validi','Attenzione: "secondi" non validi')+"!")
			}else{
				tot=oreInt+minutiInt;
				(findInput(1,campoImp,6)).value=tot;
			}
		}
	}
}

/* ---- */
function numericoData() {
 	var keycode = event.keyCode;
        var realkey = String.fromCharCode(event.keyCode);
        if (isNaN(realkey) || realkey ==" " ) {
	  event.keyCode=0;
	}	 		
}

/* ---- */
function oraSep(campoImp) {
	if(!(campoImp.value===0)){
           	campoImpValue = campoImp.value;
           
           	while(campoImpValue.indexOf(":")>0){
           		campoImpValue = campoImpValue.substring(0,campoImpValue.indexOf(":"))+campoImpValue.substring(campoImpValue.indexOf(":")+1);
           	}
           
		var campo = FormatOra(campoImpValue,campoImp);
		campoImp.value =campo;
		
	}	
}

/* ---- */
function dataSep(campoImp) {
	//alert();
	if(!(campoImp.value==0))
	{
               
		var campo;
		campo = FormatData(dataEvaluate(campoImp.value,"/","/"),"/","/");
		
			//inizio verifica correttezza data
			anno = campo.substr(6,4)
			mese = campo.substr(3,2)
			giorno = campo.substr(0,2)
			maxGiorno = 31
			
			if((mese > 12 || 1 > mese) && mese.length == 2)
			{
				alert(getLocalizedString('inserire_un_mese_compreso_tra_1_e_12','inserire un mese compreso tra 1 e 12')+"!")
				campoImp.value=campo.substr(0,campo.length-2);
			}
			else
			{
				if(mese == 2 &&  mese.length == 2) { maxGiorno = 29 }
				if((mese == 4 || mese == 6 || mese == 9 || mese == 11) &&  mese.length == 2) { maxGiorno = 30 }
				
				if((giorno > maxGiorno || 1 > giorno) && giorno.length == 2)
				{
					alert(getLocalizedString('inserire_un_giorno_compreso_tra_1_e','inserire un giorno compreso tra 1 e ')+maxGiorno+'!')
					campoImp.value=campo.substr(0,1);
		
				}
				else
				{
					campoImp.value=campo;
				}
			}
	}	
}
//Richiamata da dataSep

/* ---- */
function FormatData(n,sepdec,sepgrp)
{
	var posp,str,strtmp;
	if (n.length>4){
		
		str=n.substr(0,2)+"/"+ n.substr(2,2) + "/" +n.substr(4);
	
	}
	else{
		if (n.length>2){
			str=n.substr(0,2)+"/"+n.substr(2);
		}
		else{
			str=n;
		}
	}
	return str;		
}

/* ---- */
function FormatOra(n,elemnt)
{
	var  str;
	str = n;
	if (n.length>2){
		str=n.substr(0,2)+":";
		if (n.length>3 && elemnt.maxLength>5){
			str += n.substr(2,2) + ":";
			if (n.length>5 ){
				str += n.substr(4,2)+":";
				if (n.length>7){
					str +=n.substr(6,2);
					}
				else{
				str+=n.substr(6);
				}						
			}else{
			str+=n.substr(4);
			}			
			
		}else{
			str+=n.substr(2);
		}
	}
	
	return str;		
}
//Richiamata da dataSep

/* ---- */
function dataEvaluate(n,sepdec,sepgrp)
{
	var s="",i,u=n.charAt(n.length-1);
	for(i=0;i<n.length;i++)
		if (n.charAt(i)!=sepgrp) s+=n.charAt(i);
	if (s!="") s=s+(u=="."?sepdec:"");
	return s;
}
 