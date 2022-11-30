<script>
  let roles = ["OWNER", "ADMINISTRATOR"] // OWNER, EMPLOYEE, ADMINISTRATOR, VISITOR, USER
</script>

<div class = "container">
  {#if roles.includes("ADMINISTRATOR")}
    <p>Manage artworks</p>
    <p>Manage requests</p>
  {/if}
  {#if roles.includes("OWNER")}
  <p>Manage schedule</p>
  <p>Manage employees</p>
  {/if}
  {#if roles.includes("EMPLOYEE")}
  <p>View schedule</p>
  {/if}
  {#if roles.includes("VISITOR")}
  <p>Purchase a pass</p>
  <p>Browse artworks</p>
  <p>View my loans</p>
  <p>Donate an artwork</p>
  {/if}
</div>

<style>

  
  .container {
    height: 100vh;
  }
</style>