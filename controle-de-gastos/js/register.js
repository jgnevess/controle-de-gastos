const formRegister = document.getElementById("formRegister")

formRegister.addEventListener('submit', e => {
  e.preventDefault();
  register()
});

async function register() {
    const username = document.getElementById("usernameRegister").value;
    const fullname = document.getElementById("fullname").value;
    const password = document.getElementById("passwordRegister").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if(username == '' || fullname == '' || password == '' || confirmPassword == '') {
        document.getElementById('invalidMessageRegister').className = 'd-flex justify-content-center text-danger'
        return;
    }
  
    const response = await fetch(`${api}/auth/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        fullName: fullname,
        username: username,
        password: password,
        confirmPassword: confirmPassword,
      }),
    });
  
    if (response.ok) {
      const data = await response.json();
      window.location.href = "index.html";
    } else {
      document.getElementById("usernameRegister").value = "";
    //   document.getElementById("fullname").value = "";
      document.getElementById("passwordRegister").value = "";
      document.getElementById("confirmPassword").value = "";
      document.getElementById('invalidMessageRegister').className = "input-field text-center text-danger d-block";
    }
  }