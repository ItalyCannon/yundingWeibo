var countdown = 60;

function settime(button) {
    if (countdown == 0) {
        button.removeAttribute("disabled");
        button.value = "获取验证码";
        document.getElementById("input_q").style.backgroundColor = "#224584";
        countdown = 60;
        return;
    } else {
        button.setAttribute("disabled", true);
        document.getElementById("input_q").style.backgroundColor = "#DCDBDB";
        // document.getElementById("input_q").style.color = "#DCDBDB";
        button.value = "重新发送(" + countdown + ")";
        countdown--;
    }

    setTimeout(function () {
        settime(button)
    }, 1000)
}


function chakan_1() {
    var password = document.getElementById("password");
    var mima = document.getElementById("chakanmima_1");
    if (password.type == "password") {
        mima.innerHTML = "&#xe6a1;";
        password.setAttribute("type", "text");
    } else {
        mima.innerHTML = "&#xe601;";
        password.setAttribute("type", "password");
    }
    // alert(mima.style.color);
}

function chakan_2() {
    var password = document.getElementById("password_again");
    var mima = document.getElementById("chakanmima_2");
    if (password.type == "password") {
        mima.innerHTML = "&#xe6a1;";
        password.setAttribute("type", "text");
    } else {
        mima.innerHTML = "&#xe601;";
        password.setAttribute("type", "password");
    }
}



