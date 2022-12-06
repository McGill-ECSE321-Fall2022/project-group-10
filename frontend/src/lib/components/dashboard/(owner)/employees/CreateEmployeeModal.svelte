<script>
  //export let selectedEmployee;
  import {apiCall} from '$lib/scripts/restApi.js'
  import { createEventDispatcher } from 'svelte';

  /*$:firstName = selectedEmployee.firstName;
  $:lastName = selectedEmployee.lastName;
  $:email = selectedEmployee.email;
  $:salary = selectedEmployee.salary;
  $:active = selectedEmployee.active;*/
  //import notFound from '$lib/assets/images/not-found.jpg';

  //const imageNotFound = (e) => e.target.src = notFound;

  const dispatch = createEventDispatcher();

  const createEmployee = async () => {
    //const res = await apiCall('PUT', `administrator/employee/edit/${selectedEmployee.id}`);

    const email = document.getElementById('email-field').value;
    const firstName = document.getElementById('firstname-field').value;
    const lastName = document.getElementById('lastname-field').value;
    const salary = document.getElementById('salary-field').value;
    const password = document.getElementById('password-field').value;
    /*if (!isValidName(firstName)) { onFirstNameChange(); return; }
    if (!isValidName(lastName)) { onLastNameChange(); return; }
    if (!isValidEmail(email)) { onEmailChange(); return; }*/
    if (!isValidName(firstName)) { onFirstNameChange(); return; }
    if (!isValidName(lastName)) { onLastNameChange(); return; }
    if (!isValidEmail(email)) { onEmailChange(); return; }
    if (!isValidPassword(password)) { onPasswordChange(); return; }

    const loanRes = await apiCall('POST', 'administrator/employee',
    {
      email: email,
      firstName: firstName,
      lastName: lastName,
      password: password,
      salary: salary,
    });

    dispatch('exit', {});
  }

    function isValidName(name) {
        return name.length > 0;
    }

    function isValidEmail(email) {
        return email.length != 0 && email.includes('@mail.museum.com');
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

    function isValidPassword(password) {
        // A minimum of 8 characters, at least one uppercase letter, one lowercase letter, and one number
        // Cannot be longer than 32 characters
        return password.length >= 8 && password.length <= 32 && password.match(/[a-z]/) && password.match(/[A-Z]/) && password.match(/[0-9]/);
    }

</script>


<div class="container">

  <h2>Create</h2>
  <div style="margin-bottom: 2rem; width:13rem">
    <input id="firstname-field" type="text" placeholder="First name" on:input={onFirstNameChange} />
    <input id="lastname-field" type="text" placeholder="Last name" on:input={onLastNameChange} />
    <input id="email-field" type="text" placeholder="Email" on:input={onEmailChange} />
    <input id="password-field" type="password" placeholder="Password" on:input={onPasswordChange} />
    <input id="salary-field" type="text" placeholder="Salary" />
  </div>
  
  <button on:click={createEmployee}>Create</button>
</div>

<style>
  .container {
    border-radius: 14px;
    position: fixed;
    top: 50%;
    left: 50%;
    background-color: white;
    transform: translate(-50%, -50%);
    padding: 1rem 3rem;
  }

  button {
    background-color: #4E36FC;
    color: white;
    border-style: none;
    padding: 0.5rem 1rem;
    border-radius: 14px; 
  }

  input[type=text], input[type=password] {
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

    input[type=text]:focus, input[type=password]:focus {
        outline: 0;
        border-bottom: 2px solid #555;
    }

</style>