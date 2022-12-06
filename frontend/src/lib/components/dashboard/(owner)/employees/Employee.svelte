<script>
  //import notFound from '$lib/assets/images/not-found.jpg';
	import { createEventDispatcher } from 'svelte';
  export let employee;
  export let expandable = false;

  $:firstName = employee.firstName;
  $:lastName = employee.lastName;
  $:email = employee.email;

  //const imageNotFound = (e) => e.target.src = notFound;


  const dispatch = createEventDispatcher();

  const openModal = (employee) => {
    dispatch('selectEmployee', {
      selectedEmployee : employee
    });
  };
</script>

<div class="container" on:click={() => openModal(employee)} on:keydown={null}>
      <!-- on:error={imageNotFound} -->
  <div class="info {expandable ? "expandableInfo" : ""}">
    <p class="info-text">{email}</p>
    <p class="info-text">{firstName} {lastName}</p>

  </div>
</div>

<style>
  .container {
    width: fit-content;
    height: fit-content;
    background-color: rgba(63,76,139,.2);
    border-radius: 14px;
  }



  .info {
    height: 7rem;
    overflow: hidden;
    border-radius: 14px;
    padding: 0.5rem;
    margin-top: -1rem;
    transition: all ease-in-out 0.3s;
    margin-bottom: -2.5rem;
  }


  .info-text {
    color: #29346F;
  }

  .click-text {
    cursor: pointer;
    color: #454545;
    font-size: 0.8rem;
    text-align: right;
    margin-top: 2rem;
  }

</style>