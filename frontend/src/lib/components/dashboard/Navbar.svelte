<script>
  import {page} from '$app/stores';
  import { goto } from '$app/navigation';
  import {getCredentials, clearCredentials, isLoggedIn} from '$lib/scripts/restApi.js';
  import { onMount } from 'svelte';
  import {apiCall} from '$lib/scripts/restApi.js'

  let roles = [] // OWNER, EMPLOYEE, ADMINISTRATOR, VISITOR, USER

  let logout = () => {
    clearCredentials();
    goto('/');
  }

  $: thisRole = ""

  onMount(async () => {
    // Check if the user is logged in
    if (!(await isLoggedIn())) {
      // Not logged in
      window.location.href = '/login';
    } else {
      // Logged in
      let credentials = await getCredentials();
      
      const res = await apiCall('GET',`user/role/`)
      roles = res.data;
    }
  });

</script>

<div class = "container">
  <div>
    <h1 class="title"><a href="/" class="title">Marwan's Museum</a></h1>
    <em style="color: gray;">Logged in as {roles}</em>
  </div>

  <div class="links">
    {#if roles.includes("OWNER") || roles.includes("EMPLOYEE")}
      <div class="link {$page.url.pathname === '/dashboard/artworks' ? 'selected' : '' }">
        <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M4,4H11V2H4A2,2 0 0,0 2,4V11H4V4M10,13L6,18H18L15,14L12.97,16.71L10,13M17,8.5A1.5,1.5 0 0,0 15.5,7A1.5,1.5 0 0,0 14,8.5A1.5,1.5 0 0,0 15.5,10A1.5,1.5 0 0,0 17,8.5M20,2H13V4H20V11H22V4A2,2 0 0,0 20,2M20,20H13V22H20A2,2 0 0,0 22,20V13H20V20M4,13H2V20A2,2 0 0,0 4,22H11V20H4V13Z" /></svg>
        <p><a href="/dashboard/artworks">Manage artworks</a></p>
      </div>

      <div class="link {$page.url.pathname === '/dashboard/requests' ? 'selected' : '' }" >
        <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M18.13 12L19.39 10.74C19.83 10.3 20.39 10.06 21 10V9L15 3H5C3.89 3 3 3.89 3 5V19C3 20.1 3.89 21 5 21H11V19.13L11.13 19H5V5H12V12H18.13M14 4.5L19.5 10H14V4.5M19.13 13.83L21.17 15.87L15.04 22H13V19.96L19.13 13.83M22.85 14.19L21.87 15.17L19.83 13.13L20.81 12.15C21 11.95 21.33 11.95 21.53 12.15L22.85 13.47C23.05 13.67 23.05 14 22.85 14.19Z" /></svg>
        <p><a href="/dashboard/requests">Manage requests</a></p>
      </div>
    {/if}
    {#if roles.includes("OWNER")}
    <div class="link {$page.url.pathname === '/dashboard/hours' ? 'selected' : '' }">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M19,19H5V8H19M16,1V3H8V1H6V3H5C3.89,3 3,3.89 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5C21,3.89 20.1,3 19,3H18V1M17,12H12V17H17V12Z" /></svg>
      <p><a href="/dashboard/hours">Manage schedule</a></p>
    </div>
    <div class="link {$page.url.pathname === '/dashboard/employees' ? 'selected' : '' }">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M13.07 10.41A5 5 0 0 0 13.07 4.59A3.39 3.39 0 0 1 15 4A3.5 3.5 0 0 1 15 11A3.39 3.39 0 0 1 13.07 10.41M5.5 7.5A3.5 3.5 0 1 1 9 11A3.5 3.5 0 0 1 5.5 7.5M7.5 7.5A1.5 1.5 0 1 0 9 6A1.5 1.5 0 0 0 7.5 7.5M16 17V19H2V17S2 13 9 13 16 17 16 17M14 17C13.86 16.22 12.67 15 9 15S4.07 16.31 4 17M15.95 13A5.32 5.32 0 0 1 18 17V19H22V17S22 13.37 15.94 13Z" /></svg>
      <p><a href="/dashboard/employees">Manage employees</a></p>
    </div>
    {/if}
    {#if roles.includes("EMPLOYEE")}
    <div class="link {$page.url.pathname === '/dashboard/schedule' ? 'selected' : '' }">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M4,4H11V2H4A2,2 0 0,0 2,4V11H4V4M10,13L6,18H18L15,14L12.97,16.71L10,13M17,8.5A1.5,1.5 0 0,0 15.5,7A1.5,1.5 0 0,0 14,8.5A1.5,1.5 0 0,0 15.5,10A1.5,1.5 0 0,0 17,8.5M20,2H13V4H20V11H22V4A2,2 0 0,0 20,2M20,20H13V22H20A2,2 0 0,0 22,20V13H20V20M4,13H2V20A2,2 0 0,0 4,22H11V20H4V13Z" /></svg>
      <p><a href="/dashboard/schedule">View schedule</a></p>
    </div>
    {/if}
    {#if roles.includes("VISITOR")}
    <div class="link {$page.url.pathname === '/dashboard/passes' ? 'selected' : '' }">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M4,4A2,2 0 0,0 2,6V10A2,2 0 0,1 4,12A2,2 0 0,1 2,14V18A2,2 0 0,0 4,20H20A2,2 0 0,0 22,18V14A2,2 0 0,1 20,12A2,2 0 0,1 22,10V6A2,2 0 0,0 20,4H4M4,6H20V8.54C18.76,9.25 18,10.57 18,12C18,13.43 18.76,14.75 20,15.46V18H4V15.46C5.24,14.75 6,13.43 6,12C6,10.57 5.24,9.25 4,8.54V6Z" /></svg>
      <p><a href="/dashboard/passes">Purchase a pass</a></p>
    </div>
    <div class="link {$page.url.pathname === '/dashboard/gallery' ? 'selected' : '' }">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M18.13 12L19.39 10.74C19.83 10.3 20.39 10.06 21 10V9L15 3H5C3.89 3 3 3.89 3 5V19C3 20.1 3.89 21 5 21H11V19.13L11.13 19H5V5H12V12H18.13M14 4.5L19.5 10H14V4.5M19.13 13.83L21.17 15.87L15.04 22H13V19.96L19.13 13.83M22.85 14.19L21.87 15.17L19.83 13.13L20.81 12.15C21 11.95 21.33 11.95 21.53 12.15L22.85 13.47C23.05 13.67 23.05 14 22.85 14.19Z" /></svg>
      <p><a href="/dashboard/gallery">Browse artworks</p>
    </div>
    <div class="link {$page.url.pathname === '/dashboard/loans' ? 'selected' : '' }">
      <svg class ="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M20,18H4V8H20M20,6H12L10,4H4C2.89,4 2,4.89 2,6V18A2,2 0 0,0 4,20H20A2,2 0 0,0 22,18V8C22,6.89 21.1,6 20,6Z" /></svg>
      <p><a href="/dashboard/loans">View my loans</a></p>
    </div>
    <div class="link {$page.url.pathname === '/dashboard/donate' ? 'selected' : '' }">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M22,12V20A2,2 0 0,1 20,22H4A2,2 0 0,1 2,20V12A1,1 0 0,1 1,11V8A2,2 0 0,1 3,6H6.17C6.06,5.69 6,5.35 6,5A3,3 0 0,1 9,2C10,2 10.88,2.5 11.43,3.24V3.23L12,4L12.57,3.23V3.24C13.12,2.5 14,2 15,2A3,3 0 0,1 18,5C18,5.35 17.94,5.69 17.83,6H21A2,2 0 0,1 23,8V11A1,1 0 0,1 22,12M4,20H11V12H4V20M20,20V12H13V20H20M9,4A1,1 0 0,0 8,5A1,1 0 0,0 9,6A1,1 0 0,0 10,5A1,1 0 0,0 9,4M15,4A1,1 0 0,0 14,5A1,1 0 0,0 15,6A1,1 0 0,0 16,5A1,1 0 0,0 15,4M3,8V10H11V8H3M13,8V10H21V8H13Z" /></svg>
      <p><a href="/dashboard/donate">Donate an artwork</a></p>
    </div>
    {/if}
    <div class="logout-link link">
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M14.08,15.59L16.67,13H7V11H16.67L14.08,8.41L15.5,7L20.5,12L15.5,17L14.08,15.59M19,3A2,2 0 0,1 21,5V9.67L19,7.67V5H5V19H19V16.33L21,14.33V19A2,2 0 0,1 19,21H5C3.89,21 3,20.1 3,19V5C3,3.89 3.89,3 5,3H19Z" /></svg>
      <p class="logout" on:click="{logout}" on:keydown={() => {}}>Log out</p>
    </div>
  </div>
</div>

<style>
  .title {
    text-decoration: none;
  }

  .logout-link {
    color: #F44336;
    fill:  #F44336;
  }

  .logout {
    cursor: pointer;
  }

  .title {
    font-size: 1rem;
    color: #4E36FC;
  }
  
  .container {
    height: 100vh;
    width: 18rem;
    display: flex;
    flex-direction: column;
    padding: 3rem;
    box-sizing: border-box;
    gap: 3rem;
    position: sticky;
    top: 0;
  }

  .icon {
    width: 1rem;
  }

  .link {
    display: flex;
    gap: 0.5rem;
  }

  .link a {
    text-decoration: none;
  }

  .link a:visited {
    color: black
  }

  .selected * {
    color: #4E36FC !important;
    fill: #4E36FC !important;
  }
</style>