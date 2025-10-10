function addCookie(cname, cvalue, exdays) {
  const d = new Date();
  d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
  let expires = "expires="+d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
  let name = cname + "=";
  let ca = document.cookie.split(';');
  for(let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}
document.addEventListener('DOMContentLoaded', function() {
    let x = null;
    const canvas = document.getElementById('main-canvas');
    const ctx = canvas.getContext('2d');
    const container = canvas.parentElement;

    function drawCanvas() {
        canvas.width = container.clientWidth - 5;
        canvas.height = container.clientHeight -9;

        r = document.querySelector('input[name="r"]:checked');
        
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const gridStep = 50;
        

        ctx.fillStyle = "white";
        ctx.fillRect(0,0,canvas.width, canvas.height);

        drawGridAndAxes(centerX, centerY, gridStep);
        if(r!=null){
            r=r.value;
            const shapeCanvas = drawShape(centerX, centerY, gridStep, r);
            ctx.drawImage(shapeCanvas,0,0); 
        }

        

        drawDots();
    }

    function drawGridAndAxes(centerX, centerY, gridStep) {
        const amount = 40;
        const gridColor = '#aaa';
        const axisColor = '#333';

        ctx.strokeStyle = gridColor;
        ctx.lineWidth = 1;

        for (let x = centerX % gridStep - gridStep; x < canvas.width + gridStep; x += gridStep) {
            ctx.beginPath();
            ctx.moveTo(x, 0);
            ctx.lineTo(x, canvas.height);
            ctx.stroke();
        }

        for (let y = centerY % gridStep - gridStep; y < canvas.height + gridStep; y += gridStep) {
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
        const shapeCanvas = document.createElement('canvas');
        shapeCanvas.width = canvas.width;
        shapeCanvas.height = canvas.height;
        const shapeCtx = shapeCanvas.getContext('2d');

        //const r = 5;

        shapeCtx.fillStyle = 'rgba(0, 100, 255, 0.7)'; 

        shapeCtx.save();

        shapeCtx.beginPath();
        shapeCtx.ellipse(
            centerX,                  
            centerY,                  
            r * gridStep,             
            (r / 2) * gridStep,       
            0,                        
            0,                        
            2 * Math.PI               
        );
        shapeCtx.clip();

        shapeCtx.fillRect(0, 0, shapeCanvas.width, shapeCanvas.height);

        shapeCtx.globalCompositeOperation = 'destination-out';

        const drawHole = (x, y, rx, ry) => {
            shapeCtx.fillStyle = 'rgba(12, 100, 255, 1)';
            shapeCtx.beginPath();
            shapeCtx.ellipse(
                centerX + (x * gridStep),
                centerY - (y * gridStep),
                rx * gridStep,
                ry * gridStep,
                0, 0, 2 * Math.PI
            );
            shapeCtx.fill();
        };

        drawHole(r / 2, -r / 2, r / 8, r / 4);
        drawHole(-r / 2, -r / 2, r / 8, r / 4);
        drawHole(r / 4, -(3 * r) / 5, r / 8, r / 4);
        drawHole(-r / 4, -(3 * r) / 5, r / 8, r / 4);
        drawHole(r / 3, r / 2, r / 8, r / 3);
        drawHole(-r / 3, r / 2, r / 8, r / 3);

        const rectX = centerX - (r / 6) * gridStep;
        const rectWidth = (r / 3) * gridStep;
        const rectY = 0; 
        const rectHeight = centerY - (r/3) * gridStep;
        shapeCtx.fillRect(rectX, rectY, rectWidth, rectHeight);

        shapeCtx.restore();

        return shapeCanvas;
    }
    

    function drawDots(){
        const table = document.getElementById("results-table-body");
        const rows = table.querySelectorAll("tr");
      //const r = document.querySelector('input[name="r"]:checked').value;.
        
        rows.forEach((row) => {
            const cells = row.querySelectorAll("td");
            if (cells.length >= 4) {
            const x = parseFloat(cells[0].textContent.trim());
            const y = parseFloat(cells[1].textContent.trim());
            const isHit = cells[3].textContent.trim() === "Да";

            const canvasX = x * 50 + canvas.width / 2;
            const canvasY = -   y * 50 + canvas.height / 2;
            ctx.beginPath();
            ctx.arc(canvasX, canvasY, 3, 0, 2 * Math.PI);
            ctx.fillStyle = isHit ? "green" : "red";
            ctx.fill();
            ctx.closePath();
            }
        });
    }

    function getCursorPosition(canvas, event) {
        const rect = canvas.getBoundingClientRect();
        
        const MULTIPLIER = 50;
        var x = event.clientX - rect.left;
        var y = event.clientY - rect.top;
        x = (x - canvas.width / 2) / MULTIPLIER;
        y = -1 * (y - canvas.height / 2) / MULTIPLIER;
        const radiusInput = document.querySelector('input[name="r"]:checked');
        const radiusError = document.getElementById("r-error");
        radiusError.textContent = "";
        if(radiusInput == null){
            radiusError.textContent = "Радиус спросить забыли :/";
            return;
        }
        const form = document.getElementById("point-form");
        const yInput = document.getElementById("y");

        console.log(y);
        if(y > 5){
            y = 5;
        }
        if(y < -3){
            y = -3;
        }

        yInput.value = y.toFixed(2);
    
        var discreteLogicalX = Math.round(x)
        if(discreteLogicalX > 3){
            discreteLogicalX = 3
        }
        if(discreteLogicalX < -5){
            discreteLogicalX = -5
        }



        x = discreteLogicalX;
        document.getElementById("x-picked").innerText = x;
        document.getElementById("x-val-input").value = x;

        form.submit();
    
        //console.log("x: " + x + " y: " + y);
      }


    drawCanvas();
    window.addEventListener('resize', drawCanvas);
    const r_radios = Array.from(document.getElementsByName("r"));

    if(getCookie("r") != ""){
        r_radios[Number(getCookie("r"))-1].checked = true;
        drawCanvas();
    }
    r_radios.forEach((r_radio) => {
        r_radio.addEventListener("change", function() {
            addCookie("r", r_radio.value, 1);
            drawCanvas();
            
        });
    });
    canvas.addEventListener("mousedown", function (e) {
        getCursorPosition(canvas, e);
    });
    const buttons = Array.from(document.getElementsByClassName("x-pick-button"));

    buttons.forEach((button) => {
        button.addEventListener("click", function() {
            x = button.innerText;
            document.getElementById("x-picked").innerText = x;
            document.getElementById("x-val-input").value = x;          
        });
    });

});