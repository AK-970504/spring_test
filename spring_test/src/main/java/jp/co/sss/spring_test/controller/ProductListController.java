package jp.co.sss.spring_test.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.spring_test.entity.ProductList;
import jp.co.sss.spring_test.service.ProductListService;

@Controller
public class ProductListController {
	@Autowired
	private ProductListService productListService;
	//商品トップ画面表示
	@RequestMapping(path = "/product/productList", method = RequestMethod.GET)
	public String showProductList(Model model) {
		List<ProductList> productList = productListService.findAll();
		model.addAttribute("products", productList);
		return "/product/productList";
	}
	//画像取得API(DVに保存されたBLOB画像を返す)
	@RequestMapping(path = "/product/image/{id}", method = RequestMethod.GET)	
	public ResponseEntity<InputStreamResource> getProductImage(@PathVariable Integer id) {
		ProductList product = productListService.findById(id);
		if (product != null && product.getImg_path() != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(product.getImg_path());
			return ResponseEntity
				.ok()
				.header("Content-Type", "image/jpeg")
				.body(new InputStreamResource(bis));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}