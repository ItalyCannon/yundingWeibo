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

//这里是将本应该传送到后台的数据显示在控制台上
// function fun(){
// 	console.log(JSON.stringify(phonenumber_collect.innerText));
// 	console.log(JSON.stringify(pic_verification_collect.innerText));
// 	console.log(JSON.stringify(message_verification_collect.innerText));
// 	console.log(JSON.stringify(password_collect.innerText));
// 	console.log(JSON.stringify(password_again_collect.innerText));	
// }
// 
// var part = phonenumber_collect.innerText;


//这里是ajax，给后台的数据应该在data里面
$("#button").click(function(){
	$.ajax({
		url: 'demo.php',
		type: 'post',
		contentType: "application/json",//如果想以json格式把数据提交到后台的话，这个必须有，否则只会当做表单提交
        // data: {}，
        	// JSON.stringify({"name":,"age":"12"}),//JSON.stringify()必须有,否则只会当做表单的格式提交
        	// JSON.stringify(phonenumber_collect.innerText),
        	// {"pList": JSON.stringify(phonenumber_collect)},
		dataType: 'json', //期待返回的数值类型
		data: {
			// phonenumber: "phonenumber_collect.innerText"
			"phonenumber": JSON.stringify(phonenumber_collect.innerText),
			"pic_verification": JSON.stringify(pic_verification_collect.innerText),
			"message_verification": JSON.stringify(message_verification_collect.innerText),
			"password": JSON.stringify(password_collect.innerText),
			"ppassword_again": JSON.stringify(password_again_collect.innerText)
		},
		success: function (text) {
			// console.log("success");
			alert("success");
			// var part = eval(text);
			// alert(part.name);
		},
	});
});
