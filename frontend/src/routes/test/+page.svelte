<script>
	import ImageCarousel from "../../lib/components/ImageCarousel.svelte";
    import image1 from "../../lib/assets/images/white-pillars.jpg";
    import image2 from "../../lib/assets/images/blue-painting.jpg";
    import image3 from "../../lib/assets/images/pink-painting.jpg";
</script>

<!-- Centered div -->
<div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
    <ImageCarousel images={[image1, image2, image3]} labels={["Hello world 1", "Hello world 2", "Hello world 3"]} time=3000 style="width: 500px; height: 350px;"/>
</div>
