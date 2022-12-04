<script>
	import Navbar from "../../lib/components/dashboard/Navbar.svelte";

</script>
<div class = "container">
<Navbar/>
<slot></slot>
</div>

<style>
  .container {
    display: flex;
  }
</style>