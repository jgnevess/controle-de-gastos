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

  if (document.getElementById("searchBtn").className === "btn btn-dark w-100") {
    getDebits();
  } else {
    searchPeriod();
  }
}

async function startApp() {
  const token = localStorage.getItem("token");
  const response = await fetch(`${api}/debit/date`, {
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

  document.getElementById("filein").value = "";

  document.getElementById("btnDeleteCategory").disabled = true;

  var path = "imgs/" + localStorage.getItem("userId") + ".png";

  validationToken();
  checkImageExistence(path);
  getCategories();
  getDebits();
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

async function getDebits() {
  const token = localStorage.getItem("token");
  const response = await fetch(
    `${api}/debit/${localStorage.getItem("userId")}?sortBy=${sortBy}`,
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
    document.getElementById("searchBtn").className = "btn btn-dark w-100";
    document.getElementById("cancelBtn").className =
      "btn btn-danger w-100 d-none";
    setTables(data);
  }
}

async function searchPeriod() {
  const token = localStorage.getItem("token");
  const response = await fetch(
    `${api}/debit/period/${localStorage.getItem("userId")}?sortBy=${sortBy}`,
    {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        start: document.getElementById("start").value,
        end: document.getElementById("end").value,
      }),
    }
  );

  if (response.ok) {
    const data = await response.json();
    document.getElementById("searchBtn").className =
      "btn btn-dark w-100 d-none";
    document.getElementById("cancelBtn").className = "btn btn-danger w-100";
    setTables(data);
  }
}

function setTables(data) {
  let table = document.getElementById("debit-body");
  table.innerHTML = "";
  let total = document.getElementById("total");
  total.innerHTML = "TOTAL: R$ ";
  let debitsList = data.debitOuts;
  total.innerHTML += data.total.toFixed(2).replace(".", ",");
  debitsList.forEach((element) => {
    let date = element.dueDate.split("T")[0];
    date =
      date.split("-")[2] + "/" + date.split("-")[1] + "/" + date.split("-")[0];
    table.innerHTML += `
      <tr>
          <td class="truncate-cell pt-3 text-center">${element.description}</td>
          <td class="d-none d-md-block p-md-3 text-center">${date}</td>
          <td class="pt-3 text-center">R$ ${element.value.toFixed(2).replace(".", ",")}</td>
          <td class="d-none d-md-block p-md-3 text-center">${element.category ? element.category : 'Sem categoria'}</td>
          <td class="text-center">
              <button class="btn btn-primary me-1" 
                      onclick="findDebitId(${element.id})"
                      data-bs-toggle="modal"
                      data-bs-target="#newDebitModal">
                          <i class="bi bi-pencil-square"></i>
              </button>
              <button 
                      class="btn btn-danger"
                      onclick="findDebitId(${element.id})"
                      data-bs-toggle="modal"
                      data-bs-target="#deleteModal">
                          <i class="bi bi-trash-fill"></i>
              </button>
          </td>
      </tr>
    `;
  });
}

async function registerDebit() {
  const token = localStorage.getItem("token");
  const category = localStorage.getItem("category");
  console.log(category);
  const response = await fetch(`${api}/debit/create`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      description: document.getElementById("description").value,
      value: document.getElementById("value").value.replace(",", "."),
      date: document.getElementById("date").value,
      dueDate: document.getElementById("due_date").value,
      category: document.getElementById("category").value,
      userId: localStorage.getItem("userId"),
    }),
  });

  if (response.ok) {
    const data = await response.json();
    location.href = "debits.html";
  }
}

async function findDebitId(id) {
  localStorage.setItem("id", id);
  const token = localStorage.getItem("token");
  const response = await fetch(`${api}/debit/id/${id}`, {
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
    document.getElementById("due_date").value = data.dueDate;
    document.getElementById("category").value = data.category;
    document.getElementById("register").className = "btn btn-primary d-none";
    document.getElementById("updater").className = "btn btn-primary";
    document.getElementById("newDebitModalLabel").textContent =
      "Atualizar despesa";

    document.getElementById("span-description").innerHTML = data.description;
    document.getElementById("span-value").innerHTML = data.value
      .toFixed(2)
      .replace(".", ",");
  }
}

async function deleteDebit() {
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("id");
  const userId = localStorage.getItem("userId");
  localStorage.removeItem("id");
  const response = await fetch(
    `${api}/debit/delete?id=${id}&userId=${userId}`,
    {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );

  if (response.ok) {
    location.href = "debits.html";
  }
}

async function updateDebit() {
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("id");
  const userId = localStorage.getItem("userId");
  localStorage.removeItem("id");
  const response = await fetch(
    `${api}/debit/update?id=${id}&userId=${userId}`,
    {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        description: document.getElementById("description").value,
        value: document.getElementById("value").value.replace(",", "."),
        dueDate: document.getElementById("due_date").value,
        category: document.getElementById("category").value,
        userId: localStorage.getItem("userId"),
      }),
    }
  );

  if (response.ok) {
    const data = await response.json();
    location.href = "debits.html";
  }
}

async function getCategory() {
  const token = localStorage.getItem("token");
  const catId = localStorage.getItem("catId");
  localStorage.removeItem("catId");
  const response = await fetch(
    `${api}/category/${catId}?userId=${localStorage.getItem("userId")}`,
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
    localStorage.setItem("category", data.Response);
  }
}

async function getCategories() {
  const token = localStorage.getItem("token");
  const response = await fetch(
    `${api}/category?userId=${localStorage.getItem("userId")}`,
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
    setCategories(data);
  }
}

function setCategories(data) {
  var categories = data;
  document.getElementById("category").innerHTML =
    "<option disabled>Escolha uma opção</option>";
  data.forEach((c) => {
    document.getElementById(
      "category"
    ).innerHTML += `<option data-id='${c.id}' value='${c.category}'>${c.category}</option>`;
  });
}

// Alterar botões

document.getElementById("category").addEventListener("change", function () {
  const selectedOption = this.options[this.selectedIndex];
  const id = selectedOption.getAttribute("data-id");
  document.getElementById("btnDeleteCategory").disabled = false;
  localStorage.setItem("catId", id);
});

document.getElementById("newCategory").addEventListener("keyup", function () {
  if(document.getElementById("newCategory").value.trim().length === 0) {
    document.getElementById("btnDeleteCategory").disabled = true;
    document.getElementById("btnDeleteCategory").className = 'btn btn-danger ps-3 pe-3';
    document.getElementById("btnclearCategory").className = 'd-none';
  }
  else {
    document.getElementById("btnclearCategory").className = 'btn btn-danger ps-3 pe-3';
    document.getElementById("btnDeleteCategory").className = 'd-none';
  }
});

function clearCategory() {
  document.getElementById("newCategory").value = ''
  document.getElementById("newCategory").focus()
  if(document.getElementById("newCategory").value.trim().length === 0) {
    document.getElementById("btnDeleteCategory").disabled = true;
    document.getElementById("btnDeleteCategory").className = 'btn btn-danger ps-3 pe-3';
    document.getElementById("btnclearCategory").className = 'd-none';
  }
}

async function deleteCategory() {
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("catId");
  const userId = localStorage.getItem("userId");
  localStorage.removeItem("catId");
  const response = await fetch(`${api}/category/${userId}?id=${id}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });

  if (response.ok) {
    resetCategory();
    getCategories();
  }
}

async function registerCategory() {
  const token = localStorage.getItem("token");
  const category = document.getElementById("newCategory").value;
  if (category.trim().length === 0) {
    resetCategory();
    getCategories();
  } else {
    const response = await fetch(`${api}/category`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        category: category,
        userId: localStorage.getItem("userId"),
      }),
    });

    if (response.ok) {
      const data = await response.json();
    }

    resetCategory();
    getCategories();
  }
}

function newCategory() {
  document.getElementById("category").className = "d-none";
  document.getElementById("newCategory").className = "form-control";
  document.getElementById("categoryLabel").className = "d-none";
  document.getElementById("btnNewCategory").className = "d-none";
  document.getElementById("btnNewCategoryReg").className =
    "btn btn-success ps-3 pe-3";
  document.getElementById("newCategory").focus();
  document.getElementById("btnDeleteCategory").disabled = true;
}

function editCategory() {
  document.getElementById("btnEditCategory").className = "d-none";
  document.getElementById("btnNewCategory").className = "d-none";
  document.getElementById("newCategory").value =
    document.getElementById("category").value;
  document.getElementById("newCategory").className = "form-control";
  document.getElementById("categoryLabel").className = "d-none";
  document.getElementById("category").className = "d-none";
  document.getElementById("btnNewCategory").value = "newCategory";
  document.getElementById("btnEditCategoryReg").className =
    "btn btn-success ps-3 pe-3";
  document.getElementById("newCategory").focus();
}

function resetCategory() {
  document.getElementById("category").className = "form-control";
  document.getElementById("newCategory").className = "d-none";
  document.getElementById("btnNewCategory").className =
    "btn btn-success ps-3 pe-3";
  document.getElementById("categoryLabel").className = "d-none";
  document.getElementById("btnNewCategoryReg").className = "d-none";
  document.getElementById("newCategory").value = "";
  document.getElementById("category").value = "";
  document.getElementById("btnDeleteCategory").disabled = false;
}

window.onload = startApp;
