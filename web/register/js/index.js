var phonenumber = document.getElementById('phonenumber');
var phonenumber_collect = document.getElementById('phonenumber_collect');
if('oninput' in phonenumber){ 
    phonenumber.addEventListener("input",getWord,false); 
}else{ 
    phonenumber.onpropertychange = getWord; 
}


var pic_verification = document.getElementById('pic_verification');
var pic_verification_collect = document.getElementById('pic_verification_collect');
if('oninput' in pic_verification){
	pic_verification.addEventListener("input",getWord,false);
}else{
	pic_verification.onpropertychange = getWord;
}

var message_verification = document.getElementById('message_verification');
var message_verification_collect = document.getElementById('message_verification_collect');
if('oninput' in message_verification){
	message_verification.addEventListener("input",getWord,false);
}else{
	message_verification.onpropertychange = getWord;
}

var password = document.getElementById('password');
var password_collect = document.getElementById('password_collect');
if('oninput' in password){
	password.addEventListener("input",getWord,false);
}else{
	password.onpropertychange = getWord;
}

var password_again = document.getElementById('password_again');
var password_again_collect = document.getElementById('password_again_collect');
if('oninput' in password_again){
	password_again.addEventListener("input",getWord,false);
}else{
	password_again.onpropertychange = getWord;
}


function getWord(){
    phonenumber_collect.innerHTML = phonenumber.value;
    pic_verification_collect.innerHTML = pic_verification.value;
    message_verification_collect.innerHTML = message_verification.value;
    password_collect.innerHTML = password.value;
    password_again_collect.innerHTML = password_again.value;
}


