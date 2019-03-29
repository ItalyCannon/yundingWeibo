var portrait = document.getElementById("main");
var background = document.getElementById("bg");
var introduce = document.getElementById("introduce");

function seeMore() {
    background.style.display = "block";
    introduce.style.display = "block";
}

function disappear() {
    background.style.display = "none";
    introduce.style.display = "none";
}

var introduceName = document.getElementById("introduceName");
var introduceAge = document.getElementById("introduceAge");
var introduceSex = document.getElementById("introduceSex");
var introduceBirthday = document.getElementById("introduceBirthday");
var introducePlace = document.getElementById("introducePlace");

$("#portrait").click(function () {
    $.ajax({
        url: '',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function(data){
            var text = eval(data);
            introduceName.innerHTML = text.XXX;
            introduceSex.innerHTML = text.XXX;
            introduceAge.innerHTML = text.XXX;
            introduceBirthday.innerHTML = text.XXX;
            introducePlace.innerHTML = text.XXX;
        }
    })
})

$(function () {
    $("#return").click(function () {
        $("body,html").animate({
            scrollTop: 0
        },1000);
    });
});

//加载时遮罩层
// document.onreadystatechange = function () {
//     if(document.readyState=="complete") {
//         document.getElementById('bg').style.display='none';
//     }
// }


