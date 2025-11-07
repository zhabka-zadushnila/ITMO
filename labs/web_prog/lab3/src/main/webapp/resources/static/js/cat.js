
document.addEventListener("DOMContentLoaded", (event) => {
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
