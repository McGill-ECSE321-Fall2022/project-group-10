<script>
    import backgroundImg from '$lib/assets/images/blue-painting.jpg';
    import registerImg from '$lib/assets/images/landscape-painting.png';
    import { apiCall, isLoggedIn, setCredentials, clearCredentials } from '$lib/scripts/restApi';
    import { onMount } from 'svelte';
    
    function onRegister() {
        // Get the values from the form
        const email = document.getElementById('email-field').value;
        const password = document.getElementById('password-field').value;
        const firstName = document.getElementById('firstname-field').value;
        const lastName = document.getElementById('lastname-field').value;

        if (!isValidName(firstName)) { onFirstNameChange(); return; }
        if (!isValidName(lastName)) { onLastNameChange(); return; }
        if (!isValidEmail(email)) { onEmailChange(); return; }
        if (!isValidPassword(password)) { onPasswordChange(); return; }

        // Send a request to the backend to register
        apiCall("POST", 'visitor', {email, password, firstName, lastName}).then((response) => {
            if (response.error == null) {
                setCredentials(email, password);
                window.location.href = '/login';
            } else {
                alert('Unable to register. Please verify the input and try again later.');
            }
        }).catch((error) => {
            alert('Unable to connect to the server.');
        });
    }

    function isValidName(name) {
        return name.length > 0;
    }

    function isValidEmail(email) {
        return email.length != 0 && email.includes('@');
    }

    function isValidPassword(password) {
        // A minimum of 8 characters, at least one uppercase letter, one lowercase letter, and one number
        // Cannot be longer than 32 characters
        return password.length >= 8 && password.length <= 32 && password.match(/[a-z]/) && password.match(/[A-Z]/) && password.match(/[0-9]/);
    }

    function onEmailChange() {
        var emailField = document.getElementById('email-field');
        const email = emailField.value;
        
        // Check if the email is valid
        if (!isValidEmail(email)) {
            // Invalid email
            emailField.style.borderBottom = '2px solid red';
            emailField.style.color = 'red';

        } else {
            // Valid email
            emailField.style.borderBottom = '2px solid #ccc';
            emailField.style.color = 'black';
        }
    }

    function onFirstNameChange() {
        var firstNameField = document.getElementById('firstname-field');
        const firstName = firstNameField.value;
        
        // Check if the first name is valid
        if (!isValidName(firstName)) {
            // Invalid first name
            firstNameField.style.borderBottom = '2px solid red';
            firstNameField.style.color = 'red';

        } else {
            // Valid first name
            firstNameField.style.borderBottom = '2px solid #ccc';
            firstNameField.style.color = 'black';
        }
    }

    function onLastNameChange() {
        var lastNameField = document.getElementById('lastname-field');
        const lastName = lastNameField.value;
        
        // Check if the last name is valid
        if (!isValidName(lastName)) {
            // Invalid last name
            lastNameField.style.borderBottom = '2px solid red';
            lastNameField.style.color = 'red';

        } else {
            // Valid last name
            lastNameField.style.borderBottom = '2px solid #ccc';
            lastNameField.style.color = 'black';
        }
    }

    function onPasswordChange() {
        var passwordField = document.getElementById('password-field');
        const password = passwordField.value;
        
        // Check if the password is valid
        if (!isValidPassword(password)) {
            // Invalid password
            passwordField.style.borderBottom = '2px solid red';
            passwordField.style.color = 'red';

        } else {
            // Valid password
            passwordField.style.borderBottom = '2px solid #ccc';
            passwordField.style.color = 'black';
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
    <title>Register</title>
</svelte:head>

<div id="register-page">
    <div id="background-overlay" style="background-image: url('{backgroundImg}')"></div>
    <div id="register-panel">
        <div id="register-form">
            <h1 class="noselect">Create an account</h1>
            <span style="color: gray; margin-bottom: 30px;" class="noselect">Please enter your details below</span>
            <input id="firstname-field" type="text" placeholder="First name" on:input={onFirstNameChange} />
            <input id="lastname-field" type="text" placeholder="Last name" on:input={onLastNameChange} />
            <input id="email-field" type="text" placeholder="E-mail" on:input={onEmailChange} />
            <input id="password-field" type="password" placeholder="Password" on:input={onPasswordChange} />
            <button id="register-button" on:click={onRegister}><strong>Create</strong></button>
            <span style="color: gray; margin-top: 15px;">Already have an account? <a href="login">Log in instead</a></span>
        </div>
        <img src={registerImg} alt="Museum">
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

    #register-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        /* Center the content */
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #register-panel {
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

    #register-panel > img {
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

    #register-form {
        flex-grow: 3;
        flex-basis: 300px;
        width: 100%;
        /* Center the content */
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    #register-form input[type=text], input[type=password] {
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

    #register-form input[type=text]:focus, input[type=password]:focus {
        outline: 0;
        border-bottom: 2px solid #555;
    }
    #register-button {
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

    #register-button:hover {
        background-color: rgb(0, 128, 0); /* Dark green */
    }

    #register-form a {
        color: black;
        text-decoration: none;
    }
</style>