const idCheck = () => {
    const id = document.getElementById("memberId").value;
    const checkResult = document.getElementById("check-result");
    console.log("입력값: " + id);
    $.ajax({
        //요청방식 post ,url: "email-check",데이터:이메일
        type: "post",
        url: "/member/id-check",
        data: {
            "memberId": id
        },
        success: function (res) {
            console.log("요청성공", res)
            if (res == "ok") {
                console.log("사용가능한 아이디 입니다.");
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용가능한 아이디 입니다."
            } else {
                console.log("이미 사용중인 아이디 입니다.");
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 아이디 입니다."
            }

        },
        error: function (err) {
            console.log("에러발생", err);
        }

    });

}
const check_save=()=>{
    const username=document.getElementById("username").value;
    const password=document.getElementById("password").value;
    const name=document.getElementById("name").value;
    const useremail=document.getElementById("useremail").value;
    const phone=document.getElementById("phone").value;
    const carNum=document.getElementById("carNum").value;

    $.ajax({
        type:"post",
        url:"/member/saveProc",
        data:{
            username:username,
            password:password,
            name:name,
            useremail:useremail,
            phone:phone,
            carNum:carNum
        },
        dataType:"text",
        success:function (ck){
            console.log(ck)
            if(ck==='save_success'){
                alert("회원가입 실패")
                location.href="/member/join"

            }else {
                alert("회원가입 축하")
                location.href="/member/login"

            }
        },
        error:function (ck){
            console.log(ck);
        }
    })

}