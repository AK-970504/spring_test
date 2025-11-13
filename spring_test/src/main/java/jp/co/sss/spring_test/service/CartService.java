package jp.co.sss.spring_test.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;
import jp.co.sss.spring_test.repository.ProductDetailRepository;

@Service
public class CartService {
	@Autowired
	private CartsRepository cartsRepository;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	public Integer addToCart(Users user, Integer productId, Integer quantity) {
		//ProductsEntityを取得
		Products product = productDetailRepository.findById(productId).orElse(null);
		if (product == null) return null;
		//既に同じproduct_idとuser_idが存在しているか確認
		Optional<Carts> existingCartOpt = cartsRepository.findByUserAndProduct(user, product);
		Carts carts;
		if (existingCartOpt.isPresent()) {
			// 既存 → 数量を加算
			carts = existingCartOpt.get();
			carts.setQuantity(carts.getQuantity() + quantity);
			carts.setUpdated_at(LocalDateTime.now());
		} else {
			// 新規 → 新しく作成
			carts = new Carts();
			carts.setUser(user);
			carts.setProduct(product);
			carts.setQuantity(quantity);
			carts.setCreated_at(LocalDateTime.now());
			carts.setUpdated_at(LocalDateTime.now());
		}
		//保存
		Carts savedCart = cartsRepository.save(carts);
		//登録され屋cart_idを返す
		return savedCart.getCart_id();
	}
	public Products findProductByCartId(Integer cartId) {
		return cartsRepository.findById(cartId)
				.map(Carts::getProduct)
				.orElse(null);
	}
}