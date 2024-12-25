const api = "http://localhost:8080";
$(document).ready(function () {});

const formLogin = document.getElementById("formLogin");

formLogin.addEventListener("submit", (e) => {
  e.preventDefault();
  login();
});

function login() {
  const usuario = document.getElementById("username").value;
  const senha = document.getElementById("password").value;
  $.ajax({
    url: `${api}/auth/login`,
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: JSON.stringify({
      username: usuario,
      password: senha,
    }),
    success: function (response) {
      localStorage.setItem("token", response.token);
      localStorage.setItem("userId", response.userId);
      window.location.href = "loading.html";
    },
    error: function () {
      document.getElementById("username").value = "";
      document.getElementById("password").value = "";
      getLoginParams();
      showToast(
        "Usuário ou senha incorreta",
        "Erro",
        "toast-header text-bg-danger"
      );
    },
  });
}

function sair() {
  localStorage.clear();
  window.location.href = "loading.html";
}

function imgLoad() {
  const token = localStorage.getItem("token");
  const fileUp = document.getElementById("filein").files[0];
  const formData = new FormData();
  formData.append("file", fileUp);
  formData.append("userId", localStorage.getItem("userId"));

  $.ajax({
    url: `${api}/auth/image`,
    method: "POST",
    headers: {
      //"Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    processData: false,
    contentType: false,
    data: formData,
    success: function () {
      location.href = 'debits.html'
    },
    error: function () {
      // implementar erro
    },
  });
}

/*
async function imgLoad() {
  const token = localStorage.getItem("token");
  const fileUp = document.getElementById("filein").files[0];
  const formData = new FormData();
  formData.append("file", fileUp);
  formData.append("userId", localStorage.getItem("userId"));

  const response = await fetch(`${api}/auth/image`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  if (response.ok) {
    window.location.href = "debits.html";
  }
}
*/

async function removeImg() {
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId");

  const response = await fetch(`${api}/auth/image?userId=${userId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (response.ok) {
    location.href = "debits.html";
  }
}

function getLoginParams() {
  const params = new URLSearchParams(window.location.search);
  const log = params.get("log");
  if (log == "false") {
    console.log("entrou aqui");
    location.href = "index.html";
  }
}

function showToast(msg, header) {
  var toastEl = document.getElementById("liveToast");
  var toast = new bootstrap.Toast(toastEl);
  document.getElementById("toast-text").innerHTML = msg;
  document.getElementById("toast-header").innerHTML = header;
  toast.show();
}
