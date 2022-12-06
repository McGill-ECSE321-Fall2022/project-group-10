<script>
    import Employee from "../../../../lib/components/dashboard/(owner)/employees/Employee.svelte";
    import EmployeeModal from "../../../../lib/components/dashboard/(owner)/employees/EmployeeModal.svelte";
    import {apiCall} from '$lib/scripts/restApi.js'

    $:employees = [];
    $:selectedEmployee = null;
    let showEmployeeModal = false;

    const loadEmployees = async () => {
        //setCredentials('admin@mail.museum.com', 'admin')
        const employeesRes = await apiCall('GET','administrator/employee');
        // api call with authentication
        //const employeesRes = await apiCall('GET','administrator/employee',{email: 'admin@mail.museum.com', password: 'admin'});
        employees = employeesRes.data;
        //return employees;
    };

    const handleSelectedEmployee = (event) => {
        selectedEmployee = event.detail.selectedEmployee;
        showEmployeeModal = true;
    };

    const handleCloseEmployeeModal = () => {
    showEmployeeModal = false;
  }
    
</script>

<h1>Manage Employees</h1>
{#if showEmployeeModal}
    <div class="overlay" on:click={handleCloseEmployeeModal} on:keydown={null}>
    </div>
    <EmployeeModal {selectedEmployee} />
{/if}




<div style="z-index: -5;">
    {#await loadEmployees()}
    <p>Loading</p>
    {:then}
      <div class="employees">
        {#if employees.length === 0}
          <p>No employees yet</p>
        {/if}
        {#each employees as employee}
        <Employee {employee} on:selectEmployee={handleSelectedEmployee}/>
        {/each}
      </div>
    {/await}  
</div>

<style>
  .overlay {
    height: 100%;
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.2);
    z-index: 0;
  }

  .container {
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }
  .employees {
    display: flex;
    flex-wrap: wrap;
    gap: 8rem;
    }

  .employees {
    background-color: #ECE9FE;
    padding: 3rem;
    border-radius: 14px;
  }
</style>
