document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("point-form");
  const checkboxes = Array.from(document.getElementsByName("r-num"));
  const yInput = document.getElementById("y-cord");
  const xError = document.getElementById("x-error");
  const yError = document.getElementById("y-error");
  const radiusError = document.getElementById("r-error");
  const responseError = document.getElementById("response-error");
  const table = document.getElementById("results-table-body");

  form.addEventListener("submit", (event) => {
    event.preventDefault();
    var formCorrect = true;
    yError.textContent = "";
    xError.textContent = "";
    radiusError.textContent = "";
    responseError.textContent = "";
    x_num = document.querySelector('input[name="x-cord"]:checked');
    r_num = document.querySelector('input[name="r-num"]:checked');
    if (yInput.value == "" || yInput == null) {
      yError.textContent = "Ну напиши хоть что-нибудь";
      formCorrect = false;
    }
    if (x_num == null) {
      xError.textContent = "Выбрать x так-то надо :/";
      formCorrect = false;
    }
    if (r_num == null) {
      radiusError.textContent = "Радиус спросить забыли :/";
      formCorrect = false;
    }
    const yValue = yInput.value.trim();

    if (yValue === "") {
      yError.textContent = "Ну напиши хоть что-нибудь";
      formCorrect = false;
    } else if (!/^-?\d*\.?\d*$/.test(yValue.replace(",", "."))) {
      yError.textContent = "Ты, по моему, перепутал...";
      formCorrect = false;
    } else {
      const numberOfDigits = yValue.length;
      if (numberOfDigits >= 8) {
        yError.textContent = "Y должен содержать менее 8 знаков.";
        formCorrect = false;
      } else {
        var y_num = parseFloat(yValue.replace(",", "."));
        if (isNaN(y_num)) {
          yError.textContent = "Y должен быть числом.";
          formCorrect = false;
        } else if (y_num < -5 || y_num > 5) {
          yError.textContent = "Вы вышли за пределы. Нужно число от -5 до 5.";
          formCorrect = false;
        }
      }
    }
    console.log(y_num);
    if (formCorrect) {
      x_num = x_num.value;
      r_num = r_num.value;

      //const url = "127.0.0.1:6554/fcgi-bin/lab1_web.jar?x=${encodeURIComponent(x_num)}$&y=${encodeURIComponent(y_num)}$&r=${encodeURIComponent(r_num)}$";

      const url = `/fcgi-bin/server.jar?x=${encodeURIComponent(x_num)}&y=${encodeURIComponent(y_num)}&r=${encodeURIComponent(r_num)}`;
      console.log("Отправка запроса на URL:", url);
      fetch(url)
        .then((response) => {
          if (!response.ok) {
            console.error(response.statusText);
          }
          return response.text();
        })
        .then((data) => {
          console.log(data);
          const JSONdata = JSON.parse(data);
          const newRow = document.createElement("tr");
          newRow.innerHTML = `
                  <td>${JSONdata.x}</td>
                  <td>${JSONdata.y}</td>
                  <td>${JSONdata.r}</td>
                  <td>${JSONdata.isHit ? "Да" : "Нет"}</td>
                  <td>${JSONdata.curTime}</td>
                  <td>${JSONdata.execTime} нс</td>
              `;
          table.prepend(newRow);
        })
        .catch((error) => {
          console.error(error);
          responseError.innerText = error;
        });
    }
  });

  checkboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", function () {
      if (this.checked) {
        checkboxes.forEach((cb) => {
          if (cb !== this) cb.disabled = true;
        });
      } else {
        checkboxes.forEach((cb) => (cb.disabled = false));
      }
    });
  });
});
