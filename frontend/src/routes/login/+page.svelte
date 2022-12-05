<script>
    import backgroundImg from '$lib/assets/images/blue-painting.jpg';
    import loginImg from '$lib/assets/images/landscape-painting.png';
    import { onMount } from 'svelte';
    import {apiCall, getCredentials, setCredentials, clearCredentials, isLoggedIn } from '$lib/scripts/restApi.js';
    
    async function onLogin() {
        // Get the username and password from the input fields

        const email = document.getElementById("email-field").value;
        const password = document.getElementById("password-field").value;

        // Set the credentials
        setCredentials(email, password);

        // Send a request to the backend to login
        if (await isLoggedIn()) {
            window.location.href = '/dashboard';
        } else {
            clearCredentials();
            alert('Invalid email or password.');
        }
    }

    onMount(async () => {
        if (await isLoggedIn()) {
            window.location.href = '/dashboard';
        } else {
            clearCredentials();
        }
    });

</script>

<svelte:head>
    <title>Log In</title>
</svelte:head>

<div id="login-page">
    <div id="background-overlay" style="background-image: url('{backgroundImg}')"></div>
    <div id="login-panel">
        <div id="login-form">
            <h1 class="noselect">Sign In</h1>
            <span style="color: gray; margin-bottom: 30px;" class="noselect">Please enter your details below</span>
            <input id="email-field" type="text" placeholder="E-mail" />
            <input id="password-field" type="password" placeholder="Password" />
            <button id="login-button" on:click={onLogin}><strong>Log In</strong></button>
            <span style="color: gray; margin-top: 15px;">Don't have an account? <a href="register">Sign up for free</a></span>
        </div>
        <img src={loginImg} alt="Museum">
    </div>
</div>

<style>

    /* Animation */
    @keyframes fade-in {
        from {
            opacity: 0;
            transform: scale(0.1);
        }
        to {
            opacity: 1;
        }
    }

    .noselect {
        -webkit-touch-callout: none; /* iOS Safari */
        -webkit-user-select: none; /* Safari */
        -khtml-user-select: none; /* Konqueror HTML */
        -moz-user-select: none; /* Old versions of Firefox */
        -ms-user-select: none; /* Internet Explorer/Edge */
        user-select: none; /* Non-prefixed version, currently supported by Chrome, Edge, Opera and Firefox */
    }

    #login-page { /* refers to id */
        /* Fill the screen */
        width: 100%;
        height: 100%;
        /* Center the content */
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #login-panel {
        min-width: 800px;
        min-height: 450px;
        width: 50%;
        height: 65%;
        background-color: white;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
        /* Animation when the page loads */
        animation: fade-in 1s;
    }

    #login-panel > img {
        width: 60%;
        /* Round right side border to match panel with image */
        border-top-right-radius: 20px;
        border-bottom-right-radius: 20px;
    }

    #background-overlay {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-size: cover;
        /* Filter to make the image darker and blurry */
        filter: blur(5px) brightness(50%);
        /* Hide the white border created from the blur filter */
        transform: scale(1.1);
        /* Make sure the image is always behind the content */
        z-index: -1;
    }

    #login-form {
        flex-grow: 3;
        flex-basis: 300px;
        width: 100%;
        /* Center the content */
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    #login-form input[type=text], input[type=password] {
        width: 80%;
        padding: 6px 5px;
        margin: 8px 0;
        display: inline-block;
        /* Bottom border only */
        border: none;
        border-bottom: 2px solid #ccc;
        box-sizing: border-box;
        /* Fade transition when focused */
        transition: border-bottom 0.3s;
    }

    #login-form input[type=text]:focus, input[type=password]:focus {
        outline: 0;
        border-bottom: 2px solid #555;
    }

    #login-form label {
        display: flex;
        align-items: center;
        justify-content: flex-start;
        width: 80%;
        margin: 8px 0;
    }

    #login-form label > input[type=checkbox] {
        /* Remember me checkbox */
        margin: 5px 10px 5px 0;
        height: 15px;
        width: 15px;
        color: #555;
    }

    #login-button {
        width: 80%;
        background-color: rgb(44, 173, 44); /* Dark lime green */
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    #login-button:hover {
        background-color: rgb(0, 128, 0); /* Dark green */
    }

    #login-form a {
        color: black;
        text-decoration: none;
    }
</style>