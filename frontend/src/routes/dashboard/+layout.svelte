<script>
	import Navbar from "../../lib/components/dashboard/Navbar.svelte";

</script>
<div class = "container">
  <div class="navbar-container">
    <Navbar/>
  </div>
  <div class="slot-container">
    <slot></slot>
  </div>
</div>

<style>
  .container {
    display: flex;
  }

  .slot-container {
    width: 100%;
    background-color: #F3F5FA;
    padding: 2rem;
  }
</style>