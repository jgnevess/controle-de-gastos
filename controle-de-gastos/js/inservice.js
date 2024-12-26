const sortByList = [
  "date-up",
  "date-down",
  "description-up",
  "description-down",
  "value-up",
  "value-down",
];

var sortBy = sortByList[0];

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

function setSortBy(sort) {
  if (sort === 0) {
    if (sortBy === sortByList[0]) {
      sortBy = sortByList[1];
    } else {
      sortBy = sortByList[0];
    }
  }

  if (sort === 2) {
    if (sortBy === sortByList[2]) {
      sortBy = sortByList[3];
    } else {
      sortBy = sortByList[2];
    }
  }

  if (sort === 4) {
    if (sortBy === sortByList[4]) {
      sortBy = sortByList[5];
    } else {
      sortBy = sortByList[4];
    }
  }
}

async function startAppIn() {
  const token = localStorage.getItem("token");
  const response = await fetch(`${api}/debit/date`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
  var path = "imgs/" + localStorage.getItem("userId") + ".png";
  validationToken();
  checkImageExistence(path);
  getIn();
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

async function getIn() {
  const token = localStorage.getItem("token");
  const response = await fetch(
    `${api}/money?userId=${localStorage.getItem("userId")}`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );
  
  if (response.ok) {
    const data = await response.json();
    setTablesIn(data);
  }
}

function setTablesIn(data) {
  let table = document.getElementById("debit-body");
  table.innerHTML = "";
  let total = document.getElementById("total");
  total.innerHTML = "TOTAL: R$ ";
  total.innerHTML += data.total.toFixed(2).replace(".", ",");
  data.outs.forEach((element) => {
    let date = element.date.split("T")[0];
    date =
      date.split("-")[2] + "/" + date.split("-")[1] + "/" + date.split("-")[0];
    table.innerHTML += `
      <tr>
          <td class="truncate-cell pt-3 text-center">${element.description}</td>
          <td class="d-none d-md-block p-md-3 text-center">${date}</td>
          <td class="pt-3 text-center">R$ ${element.value.toFixed(2).replace(".", ",")}</td>
          <td class="text-center">
              <button class="btn btn-primary me-1" 
                      onclick="findInId(${element.id})"
                      data-bs-toggle="modal"
                      data-bs-target="#newDebitModal">
                          <i class="bi bi-pencil-square"></i>
              </button>
              <button 
                      class="btn btn-danger"
                      onclick="findInId(${element.id})"
                      data-bs-toggle="modal"
                      data-bs-target="#deleteModal">
                          <i class="bi bi-trash-fill"></i>
              </button>
          </td>
      </tr>
    `;
  });
}

async function registerIn() {
  const token = localStorage.getItem("token");
  const response = await fetch(`${api}/money`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      description: document.getElementById("description").value,
      value: document.getElementById("value").value.replace(",", "."),
      date: document.getElementById("date").value,
      userId: localStorage.getItem("userId"),
    }),
  });

  if (response.ok) {
    const data = await response.json();
    location.href = "in.html";
  }
}

async function findInId(id) {
  localStorage.setItem("id", id);
  const token = localStorage.getItem("token");
  const response = await fetch(`${api}/money/find/${id}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (response.ok) {
    const data = await response.json();
    (document.getElementById("description").value = data.description),
      (document.getElementById("value").value = data.value
        .toFixed(2)
        .replace(".", ",")),
      (document.getElementById("date").disabled = true);
    document.getElementById("date").value = data.date;
    document.getElementById("register").className = "btn btn-primary d-none";
    document.getElementById("updater").className = "btn btn-primary";
    document.getElementById("newDebitModalLabel").textContent =
      "Atualizar entrada";

    document.getElementById("span-description").innerHTML = data.description;
    document.getElementById("span-value").innerHTML = data.value
      .toFixed(2)
      .replace(".", ",");
  }
}

async function deleteEntry() {
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("id");
  const userId = localStorage.getItem("userId");
  localStorage.removeItem("id");
  const response = await fetch(
    `${api}/money/${id}?userId=${userId}`,
    {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );

  if (response.ok) {
    location.href = "in.html";
  }
}

async function updateEntry() {
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("id");
  const userId = localStorage.getItem("userId");
  localStorage.removeItem("id");
  const response = await fetch(
    `${api}/money/${id}?userId=${userId}`,
    {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        description: document.getElementById("description").value,
        value: document.getElementById("value").value.replace(",", ".")
      }),
    }
  );

  if (response.ok) {
    const data = await response.json();
    location.href = "in.html";
  }
}

window.onload = startAppIn;
