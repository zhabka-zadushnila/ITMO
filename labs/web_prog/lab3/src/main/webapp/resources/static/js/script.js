
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("point-form");
        const checkboxes = Array.from(document.getElementsByName("r"));
        const yInput = document.getElementById("y");
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
          x_num = document.getElementById("x-picked");
          r_num = document.querySelector('input[name="r"]:checked');
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
            if (numberOfDigits >= 6) {
              yError.textContent = "Y должен содержать менее 6 знаков.";
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
          //console.log(y_num);
          if (formCorrect) {
            x_num = x_num.value;
            r_num = r_num.value;

            form.submit();
          }
          const xButtons = document.querySelectorAll('.x-button');
          const hiddenXInput = document.getElementById('x-value-hidden');
          xButtons.forEach(button => {
              button.addEventListener('click', function () {
                  xButtons.forEach(btn => btn.classList.remove('active'));
                  this.classList.add('active');
                  hiddenXInput.value = this.dataset.value;
                  const xError = document.getElementById('x-error');
                  if (xError) {
                      xError.textContent = '';
                  }
              });
          });
        });



      });
