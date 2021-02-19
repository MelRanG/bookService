document.getElementById("userId").addEventListener("blur", doubleCheck);
document.getElementById("pwCheck").addEventListener("blur", checkPw);

function doubleCheck(){
    const req = new XMLHttpRequest();
    req.onreadystatechange = function(){
        if(req.readyState === 4){
            if(req.status === 200){
                checkId(req.responseText);
            } else if(req.status === 500){
                console.log("500에러: " + req.responseText);
            } else {
                console.log("그외에러: " + req.responseText);
            }
        }
    }
    req.open('GET', 'register/idCheck?userId=' + userId.value);
    req.send();
}

function checkId(data){
    console.log(data);
    let idCheck = document.getElementById("idCheck");

    if(data === "1"){
        idCheck.innerHTML = "사용중인 아이디입니다.";
        idCheck.style.color = "red";
        document.getElementById("register").disabled = true;
    } else{
        idCheck.innerHTML = "사용가능한 아이디입니다.";
        idCheck.style.color = "red";
        document.getElementById("register").disabled = false;
    }
    if(userId.value === "" || userId.value.search(/\s/) != -1){
        idCheck.innerHTML = "아이디를 입력해주세요.";
        idCheck.style.color = "red";
        document.getElementById("register").disabled = true;
    }
}

function checkPw(){
    let pw = document.getElementById("pw");
    let pwCheckText = document.getElementById("pwCheckText");

    if(pw.value === pwCheck.value){
        //아이디를 공백처리 한 후 비밀번호를 맞게 한 경우
        doubleCheck();

        pwCheckText.innerHTML = "사용 가능합니다.";
        pwCheckText.style.color = "red";
        document.getElementById("register").disabled = false;
    } else{
        pwCheckText.innerHTML = "비밀번호가 다릅니다.";
        pwCheckText.style.color = "red";
        document.getElementById("register").disabled = true;
    }
}