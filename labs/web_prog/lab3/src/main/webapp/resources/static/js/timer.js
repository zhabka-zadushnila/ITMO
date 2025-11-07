
function countdown(){
    let countdownCounter = 8;
    const timer = document.getElementById("back-timer");

    timer.innerText = countdownCounter;

    let thatOneInterval = setInterval(()=> {
        countdownCounter--;
        
        if(countdownCounter == 0){
            clearInterval(thatOneInterval);
        }
        timer.innerText = countdownCounter;
    }, 1000);
}

function updateTimer(){
    let timer = document.getElementById("timer");
    let current = new Date();
    let newTime = "";
    if (current.getHours()/10 < 1 ){
        newTime += "0"
    }
    newTime += current.getHours() + ":";
    if (current.getMinutes()/10 < 1 ){
        newTime += "0"
    }
    newTime += current.getMinutes() + ":";
    if (current.getSeconds()/10 < 1 ){
        newTime += "0"
    }
    newTime += current.getSeconds() ;
    timer.innerText = newTime;
    countdown();
}

document.addEventListener("DOMContentLoaded", (event) => {
    updateTimer();
    let timetInterval = setInterval(updateTimer, 8000);


    const cat = document.getElementById("cat");
    let position = 0;
    let direction = 2;

    function animate() {
        position += direction;
        cat.style.left = position + 'px';
      
        if (position > window.innerWidth - 280 || position < -20) {
            direction *= -1;
            if(direction < 0){
                cat.classList.add("rotate");
            }else{
                cat.classList.remove('rotate');
            }
        }
      
        requestAnimationFrame(animate);
    }
    animate();

});
