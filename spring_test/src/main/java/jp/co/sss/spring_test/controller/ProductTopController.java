package jp.co.sss.spring_test.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.service.ProductTopService;

@Controller
public class ProductTopController {
	@Autowired                                                                                                                                      
	private ProductTopService productTopService;
	//商品トップ画面表示
	@GetMapping("/product/productTop")
	public String showProductTop(Model model) {
		List<Products> productList = productTopService.findAll();
		model.addAttribute("products", productList);
		return "product/productTop";
	}
	//画像取得API(DVに保存されたBLOB画像を返す)
	@GetMapping("/product/image/{id}")	
	public ResponseEntity<InputStreamResource> getProductImage(@PathVariable Integer id) {
		Products product = productTopService.findById(id);
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