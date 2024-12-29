async function startApp() {
  const token = localStorage.getItem("token");
  const response = await fetch(`${api}/debit/month`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (response.ok) {
    const data = await response.json();
    document.getElementById("start").value = data.start;
    document.getElementById("end").value = data.end;
  }

  document.getElementById("searchBtn").className = "btn btn-dark w-100";
  document.getElementById("cancelBtn").className =
    "btn btn-danger w-100 d-none";

  var path = "imgs/" + localStorage.getItem("userId") + ".png";

  validationToken();
  checkImageExistence(path);
  getTotal();
}

async function getTotal() {
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId");
  const response = await fetch(`${api}/home?userId=${userId}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      start: document.getElementById("start").value,
      end: document.getElementById("end").value,
    }),
  });

  if (response.ok) {
    const data = await response.json();
    setMonth(document.getElementById("start").value.split("-")[1]);
    setItems(data);
  }
}

async function getTotalMonth() {
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId");
  const response = await fetch(`${api}/home?userId=${userId}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      start: document.getElementById("start").value,
      end: document.getElementById("end").value,
    }),
  });

  if (response.ok) {
    const data = await response.json();
    setMonth(document.getElementById("start").value.split("-")[1]);
    setItems(data);
    document.getElementById("searchBtn").className =
      "btn btn-dark w-100 d-none";
    document.getElementById("cancelBtn").className = "btn btn-danger w-100";
  }
}

function setItems(data) {
  document.getElementById("ganho").innerHTML = "";
  document.getElementById("gasto").innerHTML = "";
  document.getElementById("sobra").innerHTML = "";
  document.getElementById("ganho").innerHTML += data.Entrada.toFixed(2);
  document.getElementById("ganho").className = "text-success";
  document.getElementById("gasto").innerHTML += data.Gasto.toFixed(2);
  document.getElementById("gasto").className = "text-danger";
  document.getElementById("sobra").innerHTML += data.Total.toFixed(2);
  document.getElementById("sobra").className =
    data.Total > 0 ? "text-success" : "text-danger";
}

function setMonth(month) {
  let m = document.getElementById("month");
  m.innerHTML = "";
  switch (month) {
    case "01":
      m.innerHTML += "janeiro";
      break;
    case "02":
      m.innerHTML += "fevereiro";
      break;
    case "03":
      m.innerHTML += "março";
      break;
    case "04":
      m.innerHTML += "Abril";
      break;
    case "05":
      m.innerHTML += "maio";
      break;
    case "06":
      m.innerHTML += "junho";
      break;
    case "07":
      m.innerHTML += "julho";
      break;
    case "08":
      m.innerHTML += "agosto";
      break;
    case "09":
      m.innerHTML += "setembro";
      break;
    case "10":
      m.innerHTML += "outubro";
      break;
    case "11":
      m.innerHTML += "novembro";
      break;
    case "12":
      m.innerHTML += "dezembro";
      break;
    default:
      break;
  }
}

function checkImageExistence(imgPath) {
  fetch(imgPath, { method: "HEAD" })
    .then((response) => {
      if (response.ok) {
        document.getElementById("userPic").src = imgPath;
      } else {
        document.getElementById("userPic").src = "imgs/default-image.png";
      }
    })
    .catch(() => {
      document.getElementById("userPic").src = "imgs/default-image.png";
    });
}

async function validationToken() {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "loading.html";
    return;
  }

  const response = await fetch(`${api}/auth/valid?token=${token}`, {
    method: "GET",
  });

  if (!response.ok) {
    localStorage.clear();
    window.location.href = "loading.html";
  }
}

document.getElementById("start").addEventListener("change", (e) => {
  document.getElementById("searchBtn").className = "btn btn-dark w-100";
  document.getElementById("cancelBtn").className =
    "btn btn-danger w-100 d-none";
});

window.onload = startApp;
