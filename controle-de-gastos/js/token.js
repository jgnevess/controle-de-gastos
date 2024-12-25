$(document).ready(function () {
  validateToken();
});

function validateToken() {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "index.html?log=false";
    return;
  }
  
  $.ajax({
    url: `${api}/auth/valid?token=${token}`,
    method: "GET",
    success: function (response) {
      if (!response) {
        localStorage.clear();
        window.location.href = "index.html?err=true";
      } else {
        window.location.href = "debits.html";
      }
    },
    error: function (err) {
      localStorage.clear();
      window.location.href = "index.html?err=true";
    },
  });
}
