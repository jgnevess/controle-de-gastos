function clearInput() {
  document.getElementById("btnNewCategoryReg").className = "d-none";
  document.getElementById("filein").value = "";
  document.getElementById("description").value = "";
  document.getElementById("value").value = "";
  document.getElementById("newCategory").className = "d-none";
  document.getElementById("category").className = "form-control";
  document.getElementById("date").value = setDate();
  document.getElementById("category").value = "";
  document.getElementById("newCategory").value = "";
  document.getElementById("date").disabled = false;
  document.getElementById("btnNewCategory").className =
    "btn btn-success ps-3 pe-3";
  document.getElementById("btnDeleteCategory").disabled = true;
  document.getElementById("btnDeleteCategory").disabled = true;
  document.getElementById("btnDeleteCategory").className =
    "btn btn-danger ps-3 pe-3";
  document.getElementById("btnclearCategory").className = "d-none";
}

function restartModal() {
  document.getElementById("newDebitModalLabel").textContent = "Nova despesa";
  document.getElementById("register").className = "btn btn-primary";
  document.getElementById("updater").className = "btn btn-primary d-none";
  clearInput();
}

function setDate() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");
  const hour = String(now.getHours()).padStart(2, "0");
  const minute = String(now.getMinutes()).padStart(2, "0");
  const datetimeValue = `${year}-${month}-${day}T${hour}:${minute}`;

  return datetimeValue;
}

function showPassword() {
  document.getElementById("password").type = "text";
}

function closePassword() {
  document.getElementById("password").type = "password";
}

function showToast(msg, header, classNameDiv) {
  var toastEl = document.getElementById("liveToast");
  var toast = new bootstrap.Toast(toastEl);
  document.getElementById("toast-text").innerHTML = msg;
  document.getElementById("toast-header").innerHTML = header;
  document.getElementById("toast-header-div").className = classNameDiv;
  toast.show();
}

function getParams() {
  const params = new URLSearchParams(window.location.search);
  const log = params.get("log");
  if (log == "false") {
    showToast(
      "Faça login para continuar",
      "Alerta",
      "toast-header text-bg-warning"
    );
  }
}

window.onload = getParams();
