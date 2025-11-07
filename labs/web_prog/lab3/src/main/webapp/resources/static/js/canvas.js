var canvas = null;
var ctx = null;
var container = null;
var x = null;
document.addEventListener("DOMContentLoaded", function () {

  canvas = document.getElementById("main-canvas");
  ctx = canvas.getContext("2d");
  container = canvas.parentElement;
  drawCanvas();
  window.addEventListener("resize", drawCanvas);

  canvas.addEventListener("mousedown", function (e) {
    getCursorPosition(canvas, e);
  });

});


function addCookie(cname, cvalue, exdays) {
  const d = new Date();
  d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
  let expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
  let name = cname + "=";
  let ca = document.cookie.split(";");
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}
function getSelectedRValue() {
  const rRadioGroup = document.querySelectorAll('input[name*="r-selector"]');
  if (rRadioGroup.length === 0) {
    console.error("Не найдены радиокнопки для R.");
    return null;
  }

  const checkedRadio = document.querySelector(
    'input[name="' + rRadioGroup[0].name + '"]:checked',
  );

  if (checkedRadio) {
    return parseFloat(checkedRadio.value);
  }

  return null;
}



function drawCanvas() {
  canvas.width = container.clientWidth - 5;
  canvas.height = container.clientHeight - 9;

  r = getSelectedRValue();

  const centerX = canvas.width / 2;
  const centerY = canvas.height / 2;
  const gridStep = 100;

  ctx.fillStyle = "white";
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  drawGridAndAxes(centerX, centerY, gridStep);
  if (r != null) {
    const shapeCanvas = drawShape(centerX, centerY, gridStep, r);
    ctx.drawImage(shapeCanvas, 0, 0);
  }

  drawDots();
}

function drawGridAndAxes(centerX, centerY, gridStep) {
  const amount = 40;
  const gridColor = "#aaa";
  const axisColor = "#333";

  ctx.strokeStyle = gridColor;
  ctx.lineWidth = 1;

  for (
    let x = (centerX % gridStep) - gridStep;
    x < canvas.width + gridStep;
    x += gridStep
  ) {
    ctx.beginPath();
    ctx.moveTo(x, 0);
    ctx.lineTo(x, canvas.height);
    ctx.stroke();
  }

  for (
    let y = (centerY % gridStep) - gridStep;
    y < canvas.height + gridStep;
    y += gridStep
  ) {
    ctx.beginPath();
    ctx.moveTo(0, y);
    ctx.lineTo(canvas.width, y);
    ctx.stroke();
  }

  ctx.strokeStyle = axisColor;
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(centerX, 0);
  ctx.lineTo(centerX, canvas.height);
  ctx.stroke();
  ctx.beginPath();
  ctx.moveTo(0, centerY);
  ctx.lineTo(canvas.width, centerY);
  ctx.stroke();
}

function drawShape(centerX, centerY, gridStep, r) {
  const shapeCanvas = document.createElement("canvas");
  shapeCanvas.width = canvas.width;
  shapeCanvas.height = canvas.height;
  const shapeCtx = shapeCanvas.getContext("2d");

  //const r = 5;

  shapeCtx.fillStyle = "rgba(0, 100, 255, 0.7)";

  shapeCtx.save();
  const r_px = r * gridStep;
  const r_half_px = (r / 2) * gridStep;
  shapeCtx.beginPath();
  shapeCtx.moveTo(centerX - r_px, centerY - r_half_px);
  shapeCtx.lineTo(centerX, centerY - r_half_px);
  shapeCtx.arc(centerX, centerY, r_half_px, -Math.PI / 2, 0);
  shapeCtx.lineTo(centerX, centerY + r_half_px);
  shapeCtx.lineTo(centerX, centerY);
  shapeCtx.lineTo(centerX - r_px, centerY);
  shapeCtx.closePath();

  shapeCtx.fill();

  shapeCtx.restore();

  return shapeCanvas;
}

function drawDots() {
    if (typeof allHits === 'undefined' || allHits.length === 0) {
        return;
    }

    const scale = 100;
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;

    allHits.forEach((dot) => {
        const x = dot.x;
        const y = dot.y;
        const isHit = dot.hit;

        const canvasX = centerX + x * scale;
        const canvasY = centerY - y * scale;

        ctx.beginPath();
        ctx.arc(canvasX, canvasY, 4, 0, 2 * Math.PI);
        ctx.fillStyle = isHit ? "green" : "red";
        ctx.fill();
        ctx.closePath();
    });
}

function getCursorPosition(canvas, event) {
    const rValue = getSelectedRValue();
    if (rValue === null) {
        alert("Пожалуйста, выберите значение R перед тем, как отмечать точку.");
        return;
    }

    const rect = canvas.getBoundingClientRect();
    const scale = 100;

    let logicalX = (event.clientX - rect.left - canvas.width / 2) / scale;
    let logicalY = (canvas.height / 2 - (event.clientY - rect.top)) / scale;

    if(logicalX> 5){
        logicalX = 5;
    }
    if (logicalX < -5){
        logicalX = -5
    }
    if(logicalY> 5){
        logicalY = 5;
    }
    if (logicalY < -5){
        logicalY = -5
    }

    const form = document.querySelector('form');
    const xInput = form.querySelector("input[id*=':x']");
    const yInput = form.querySelector("input[id*=':y_input']");

    if (!xInput || !yInput) {
        console.error("Не удалось найти поля ввода X или Y! Проверьте JSF ID.");
        return;
    }

    xInput.value = Math.round(logicalX);
    if (typeof PF('yInputWidget') !== 'undefined') {
        PF('yInputWidget').setValue(logicalY.toFixed(2));
    } else {
        console.error("Не удалось найти виджет PrimeFaces 'yInputWidget'!");
        return;
    }
    sendCanvasClick();
}


