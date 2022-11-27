export const load = async ({ params }) => {
	const id = params.id;
	const res = await fetch(`http://localhost:8081/artworks/${id}`);
	const artwork = await res.json();
	return { artwork };
};
