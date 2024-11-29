$(document).ready(function () {
  const api = "http://localhost:8080";
  // const api = 'http://192.168.15.90:8080'
  validateToken();
});

function validateToken() {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "index.html?log=false";
    return;
  }

  console.log(token)

  $.ajax({
    url: `${api}/auth/valid?token=${token}`,
    method: "GET",
    success: function (response) {
      if (!response) {
        localStorage.clear();
        window.location.href = "index.html?err=true";
        console.log("respnse " + response)
      } else {
        window.location.href = "home.html";
      }
      console.log(response)
    },
    error: function (err) {
      localStorage.clear();
      window.location.href = "index.html?err=true";
    },
  });
}
