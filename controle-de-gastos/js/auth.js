$(document).ready(function () {
  const api = "http://localhost:8080";
  // const api = 'http://192.168.15.90:8080'
});

const formLogin = document.getElementById("formLogin")

formLogin.addEventListener('submit', e => {
  e.preventDefault();
  login();
});

async function login() {
  const usuario = document.getElementById("username").value;
  const senha = document.getElementById("password").value;
  const invalidMessage = document.getElementById("invalidMessage")

  const response = await fetch(`${api}/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: usuario,
      password: senha,
    }),
  });

  if (response.ok) {
    const data = await response.json();
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.userId);
    localStorage.setItem("img", data.img);
    window.location.href = "loading.html";
  } else {
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    getLoginParams();
    showToast("Usuário ou senha incorreta", "Erro", "toast-header text-bg-danger");

  }
}

function sair() {
  localStorage.clear();
  window.location.href = "loading.html"
}

async function imgLoad() {
  const token = localStorage.getItem("token");
  const fileUp = document.getElementById('filein').files[0];
  const formData = new FormData();
  formData.append('file', fileUp);
  formData.append('userId', localStorage.getItem("userId"))

  const response = await fetch(`${api}/auth/image`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData
  });

  if (response.ok) {
    window.location.href = "home.html";
  }
}

async function removeImg() {
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId");

  const response = await fetch(
    `${api}/auth/image?userId=${userId}`,
    {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );

  if (response.ok) {
    location.href = "home.html";
  }
}

function getLoginParams() {
  const params = new URLSearchParams(window.location.search);
  const log = params.get("log");
  if (log == "false") {
    console.log('entrou aqui')
    location.href = 'index.html'
  }
}

function showToast(msg, header) {
  var toastEl = document.getElementById("liveToast");
  var toast = new bootstrap.Toast(toastEl);
  document.getElementById("toast-text").innerHTML = msg;
  document.getElementById("toast-header").innerHTML = header;
  toast.show();
}