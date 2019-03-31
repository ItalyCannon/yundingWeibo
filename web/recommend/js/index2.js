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

function showOtherUserInfo(userId) {
    $.ajax({
        url: '/OtherUserBaseInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {
            user: userId
        },
        success: function (data) {
            var text = eval(data);

            introduceName.innerHTML = text.nickname;
            switch (text.gender) {
                case 0:
                    introduceSex.innerHTML = "保密";
                    break;
                case 1:
                    introduceSex.innerHTML = "男";
                    break;
                case 2:
                    introduceSex.innerHTML = "女";
                    break;
                default:
            }

            var thisYear = new Date().toLocaleDateString().substr(0, 4);
            var birthday = new Date(text.birthday).toLocaleDateString();
            var birthdayYear = birthday.substr(0, 4);
            introduceAge.innerHTML = thisYear - birthdayYear;
            introduceBirthday.innerHTML = birthday;
            introducePlace.innerHTML = text.location;
        }
    })
}

$(function () {
    $("#return").click(function () {
        $("body,html").animate({
            scrollTop: 0
        }, 1000);
    });
});



