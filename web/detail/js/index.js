function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}

function load_edit() {
    window.location.href = "/editDetail"
}

function getInfo() {
    $.ajax({
        url: '/UserDetailsServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            $("#profile1").attr('src', data.profilePicture);
            $("#profilePicture").attr('src', data.profilePicture);
            $("#profilePictureEdit").attr('src', data.profilePicture);
            $("#nicknameLeft").html(data.nickname);
            $("#signatureLeft").html(data.signature);
            $("#attentionNum").html(data.subscribeNum);
            $("#fansNum").html(data.fansNum);
            $("#weiboNum").html(data.weiboNum);
            $("#loginId").html(data.loginId);
            $("#nicknameRight").html(data.nickname);
            $("#realName").html(data.realName);
            var gender = data.gender;
            switch (gender) {
                case 0:
                    $("#gender").html("保密");
                    break;
                case 1:
                    $("#gender").html("男");
                    break;
                case 2:
                    $("#gender").html("女");
                    break;
                default:

            }
            $("#location").html(data.location);
            var sexualOrientation = data.sexualOrientation;
            switch (sexualOrientation) {
                case 0:
                    $("#sexualOrientation").html("保密");
                    break;
                case 1:
                    $("#sexualOrientation").html("异性");
                    break;
                case 2:
                    $("#sexualOrientation").html("同性");
                    break;
                default:

            }
            var emotionalState = data.emotionalState;
            switch (emotionalState) {
                case 0:
                    $("#emotionalState").html("保密");
                    break;
                case 1:
                    $("#emotionalState").html("单身");
                    break;
                case 2:
                    $("#emotionalState").html("恋爱");
                    break;
                case 3:
                    $("#emotionalState").html("已婚");
                    break;
                default:

            }
            $("#birthday").html(data.formatBirthday);
            $("#signatureRight").html(data.signature);
            $("#registrationTime").html(data.formatRegistrationTime);
            $("#email").html(data.email);
            $("#qq").html(data.qq);
            $("#undergraduateSchool").html(data.undergraduateSchool);
            $("#graduateSchool").html(data.graduateSchool);

            // var tags = data.tag;
            // for (var i = 0; i < tags.length; ++i) {
            //     $("#tag").append('<p class="items">' + tags[i] + '</p>');
            // }
        },
        async: true
    });
}

function appear() {
    var personage = document.getElementById("personage_more");
    if (personage.style.display == "none") {
        personage.style.display = 'block';
    } else {
        personage.style.display = 'none';
    }
}

function change(){
    $.ajax({
        url: "/BackgroundServlet",
        type: "get",
        datatype: "json",
        data:{},
        success:function (data) {
            var text = eval(data);
            // var content = document.getElementById("content");
            // alert(content.style.backgroundImage);
            $(".main").css("background-image","url("+'"'+text.img+'"'+")");
            // var content = $("#content").attr("class");
        }

    })
}

window.onload = function () {
    getInfo();
    change();
}