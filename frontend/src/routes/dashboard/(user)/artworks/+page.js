export const load = async () => {
	const res = await fetch('http://localhost:8081/artworks');
	const artworks = await res.json();
	return { artworks };
};
