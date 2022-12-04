<script>
    import {fade} from 'svelte/transition';
    import nextBtnImg from "../assets/icons/right-arrow.svg";
    import prevBtnImg from "../assets/icons/left-arrow.svg";
    
    export let images = [];
    export let labels = [];
    export let style = "";
    export let time = 0;

    let index = 0;

    const next = () => {
        index = (index + 1) % images.length;
    };

    const prev = () => {
        index = (index - 1 + images.length) % images.length;
    };

    // If time is set, automatically change the image every time seconds
    if (time > 0) {
        setInterval(() => {
            next();
        }, time);
    }

</script>

    <div id="image-frame" style="{style}">

        {#each [images[index]] as src (index)}
                <img class="slide-img" transition:fade {src} alt=""/>
                {#if labels[index]}
                    <div class="slide-label">{labels[index]}</div>
                {/if}
        {/each}
        
        <img id="next-button" src={nextBtnImg} alt="Next" on:click={next} on:keypress={()=>{}}/>
        <img id="prev-button" src={prevBtnImg} alt="Previous" on:click={prev} on:keypress={()=>{}}/>

        <div id="counter">
            {#each images as src, i}
                <div class="dot" style="background-color: {i === index ? "lightgrey" : "black"}"></div>
            {/each}
        </div>
    </div>


<style>

    #image-frame {
        position: relative;
    }

    #image-frame .slide-img {
        position: absolute;
        width: 100%;
        height: 100%;
        border-radius: 20px;
        box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.5);
    }

    #counter {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        transform: translateY(120%);
    }

    .dot {
        height: 10px;
        width: 10px;
        margin: 0 2px;
        border-radius: 50%;
        display: inline-block;
    }

    .slide-label {
        position: absolute;
        bottom: 10px;
        left: 0;
        width: 100%;
        height: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        color: white;
        font-size: 1.0em;
        text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;
    }

    #next-button {
        width: 30px !important;
        height: 30px !important;
        position: absolute;
        top: 50%;
        right: 0;
        color: black;
        transform: translate(calc(100% + 10px), -50%);
        cursor: pointer;
    }

    #prev-button {
        width: 30px !important;
        height: 30px !important;
        position: absolute;
        top: 50%;
        left: 0;
        color: black;
        transform: translate(calc(100% - 70px), -50%);
        cursor: pointer;
    }
</style>