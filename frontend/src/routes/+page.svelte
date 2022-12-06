<script>
    import ImageCarousel from "../lib/components/ImageCarousel.svelte";
    import image1 from "../lib/assets/images/white-pillars.jpg";
    import image2 from "../lib/assets/images/blue-painting.jpg";
    import image3 from "../lib/assets/images/store-picture.jpg";
    import backgroundImg from '../lib/assets/images/pink-painting.jpg';

    function onGetStarted() {
        // Navigate to the register page
        window.location.href = '/login';
    }
</script>

<svelte:head>
    <title>Marwan's Museum</title>
</svelte:head>

<div id="landing-page">
    <link href="https://fonts.googleapis.com/css?family=Chau%20Philomene%20One" rel="stylesheet"> <!-- Font -->
    <div id="background-overlay" style="background-image: url('{backgroundImg}')"></div>

    <div id="page-title">
        <h1 class="animate-character">Marwan's Museum</h1>
        <h2>Proudly delivering the best of art since 2022</h2>
        <button on:click={onGetStarted}>Get Started</button>
    </div>

    <div id="navbar">
        <a href="/" class="navbar-title">M.M.</a>
        <a href="/login">Login</a>
        <a href="/register">Register</a>
        <a href="/artworks">Artworks</a>
    </div>
    
    <ImageCarousel images={[image1, image2, image3]} labels={["\"White Pillars\" by John Doe", "\"Blue Storm\" by Patrick Doe", "\"McDonalds Atwater\" by Joseph Saliba"]} time=4000 
        style="
            width: 30rem; 
            height: 20rem;
            margin-left: auto; 
            right: 300px;"/>
</div>

<style>
    #landing-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        /* Center the content */
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    #background-overlay {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-size: cover;
        background-position: center;
        /* Filter to make the image darker and blurry */
        filter: blur(5px) brightness(90%);
        /* Hide the white border created from the blur filter */
        transform: scale(1.1);
        /* Make sure the image is always behind the content */
        z-index: -1;
    }

    @keyframes
    slide-from-left {
        0% {
            transform: translateX(-200%);
        }
        100% {
            transform: translateX(0);
        }
    }

    @keyframes textclip {
        to {
            background-position: 200% center;
        }
    }

    .animate-character
    {
        /* Gradient color transition animation */
        text-transform: uppercase;
        background-image: linear-gradient(
            -225deg,
            #231557 0%,
            #44107a 20%,
            #ff1361 50%,
            rgb(212, 141, 153) 70%,
            #712abd 90%,
            #231557 100%
        );
        background-size: auto auto;
        background-clip: border-box;
        background-size: 200% auto;
        color: #fff;
        background-clip: text;
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        animation: textclip 2s linear infinite;
        display: inline-block;
    }

    #page-title {
        /* Transition when appearing */
        animation: slide-from-left 1s;
        /* Align to the left */
        position: absolute;
        left: 0;
        margin-left: 5%;
        /* Text properties */
        white-space: nowrap;
    }

    #page-title > h1 {
        /* Text styling */
        font-family: 'Chau Philomene One', sans-serif;
        font-weight: 400;
        font-size: 96px;
        line-height: 18px;
        color: #FFFFFF;
        text-shadow: 0px 4px 4px rgba(0, 82, 119, 0.5);
        margin-top: 0;
        margin-bottom: 0;
        padding-top: 30px;
        padding-bottom: 30px;
    }

    #page-title > h2 {
        /* Text styling */
        font-weight: 300;
        font-size: 30px;
        line-height: 35px;
        letter-spacing: 0.06em;
    }

    #page-title > button {
        /* Button styling */
        background: black;
        border-radius: 40px;
        padding: 24px 57px 24px 40px;
        color: white;
        font-size: 20px;
        border: none;
        cursor: pointer;
        transition: background-color 0.3s;
        /* Positioning */
        margin-top: 30px;
    }

    #page-title > button:hover {
        /* Button hover styling */
        cursor: pointer;
        background: rgb(78, 78, 78);
        border: none;
    }

    #navbar {
        /* Positioning */
        position: absolute;
        right: 0;
        top: 0;
        width: 100%;
        height: 70px;
        padding: 0 35px 0 35px;
        /* Styling */
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
        background-color: rgba(255, 255, 255, 0.3);
        /* Text properties */
        white-space: nowrap;
    }

    .navbar-title {
        flex: 1;
        font-family: 'Chau Philomene One', sans-serif;
        margin-left: 70px !important;
    }

    #navbar a {
        /* Text styling */
        font-weight: 300;
        font-size: 20px;
        line-height: 23px;
        letter-spacing: 0.06em;
        /* Styling */
        text-decoration: none;
        color: #FFFFFF;
        margin-left: 30px;
        text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;
    }
</style>

