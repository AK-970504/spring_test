function updateProductList(products) {
	const list = document.querySelector(".product_list");
	list.innerHTML = "";
	products.forEach(p => {
		const html = `
		<section>
			<div>
				<a href="/spring_test/product/productDetail/${p.product_id}">
					<img src="/spring_test${p.img_path}" alt="商品画像">
				</a>
			</div>
			<div class="product_name">
				<label>${p.product_name}</label>
			</div>
			<div class="company_name">
				<label>${p.company_name}</label>
			</div>
			<div class="product_price">
				<p>¥</p><label>${p.price}</label>
			</div>
			<div class="product_tax_price">
				<p>¥</p><label>${p.tax_price}</label>
			</div>
		</section>
		`;
		list.insertAdjacentHTML("beforeend", html);
	});
}