<script>
  export let selectedEmployee;
  import {apiCall} from '$lib/scripts/restApi.js'

  $:firstName = selectedEmployee.firstName;
  $:lastName = selectedEmployee.lastName;
  $:email = selectedEmployee.email;
  $:salary = selectedEmployee.salary;
  $:active = selectedEmployee.active;
  //import notFound from '$lib/assets/images/not-found.jpg';

  //const imageNotFound = (e) => e.target.src = notFound;

  const deactivateEmployee = async () => {
    selectedEmployee.active = false;
    const res = await apiCall('PUT', `administrator/employee/deactivate/${selectedEmployee.id}`);
    //console.log(employee);
  }

  const reactivateEmployee = async () => {
    selectedEmployee.active = true;
    const res = await apiCall('PUT', `administrator/employee/reactivate/${selectedEmployee.id}`);
    //console.log(employee);
  }

  const editEmployee = async () => {
    //const res = await apiCall('PUT', `administrator/employee/edit/${selectedEmployee.id}`);

    email = document.getElementById('email-field').value;
    firstName = document.getElementById('firstname-field').value;
    lastName = document.getElementById('lastname-field').value;
    salary = document.getElementById('salary-field').value;
    const password = selectedEmployee.password;
    /*if (!isValidName(firstName)) { onFirstNameChange(); return; }
    if (!isValidName(lastName)) { onLastNameChange(); return; }
    if (!isValidEmail(email)) { onEmailChange(); return; }*/
    if (!isValidName(firstName)) { firstName = selectedEmployee.firstName; }
    if (!isValidName(lastName)) { lastName = selectedEmployee.lastName; }
    if (!isValidEmail(email)) { email = selectedEmployee.email; }
    if (salary == "") { salary = selectedEmployee.salary; }
        
    const loanRes = await apiCall('PUT', `administrator/employee/${selectedEmployee.id}`,
    {
      email: email,
      firstName: firstName,
      lastName: lastName,
      password: password,
      salary: salary,
    });
  }

    function isValidName(name) {
        return name.length > 0;
    }

    function isValidEmail(email) {
        return email.length != 0 && email.includes('@');
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

    //console.log(employee);
  

  
</script>


<div class="container">
  <h3>Email: {email}</h3>
  <h3>First Name: {firstName}</h3>
  <h3>Last Name: {lastName}</h3>
  <h3>Salary: {salary}</h3>
  <h3>Active: {active}</h3>

  <hr>
  <h2>Edit</h2>
  <div style="margin-bottom: 2rem;">
    <input id="firstname-field" type="text" placeholder={firstName} on:input={onFirstNameChange} />
    <input id="lastname-field" type="text" placeholder={lastName} on:input={onLastNameChange} />
    <input id="email-field" type="text" placeholder={email} on:input={onEmailChange} />
    <input id="salary-field" type="text" placeholder={salary} />
  </div>
  
  <button on:click={editEmployee}>Edit Employee</button>


  {#if selectedEmployee.active}
  <button id = "deactivate-button" on:click={deactivateEmployee}>Deactivate Employee</button>
  {:else}
  <button on:click={reactivateEmployee}>Reactivate Employee</button>
  {/if}
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

  #deactivate-button {
    background-color: #FF0000;
  }
</style>