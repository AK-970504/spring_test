package jp.co.sss.spring_test.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
	//cartIdからproductIdとquantityを取得
	public Map<String, Object> findCartDetailByCartId(Integer cartId) {
		Optional<Carts> cartOpt = cartsRepository.findById(cartId);
		if (cartOpt.isEmpty()) return null;
		//cart から product_id と quantity を取得
		Carts cart = cartOpt.get();
		Products product = productDetailRepository.findById(cart.getProduct().getProduct_id()).orElse(null);
		if (product == null) return null;
		//合計金額を計算
		Integer taxPrice = product.getTax_price();
		Integer quantity = cart.getQuantity();
		Integer totalPrice = taxPrice * quantity;
		//結果をMapに格納
		Map<String, Object> result = new HashMap<>();
		result.put("product", product);
		result.put("quantity", quantity);
		result.put("totalPrice", totalPrice);
		return result;
	}
	public Integer addToCart(Users user, Integer productId, Integer quantity) {
		//ProductsEntityを取得
		Products product = productDetailRepository.findById(productId).orElse(null);
		if (product == null) return null;
		//既に同じproduct_idとuser_idが存在しているか確認
		Optional<Carts> existingCartOpt = cartsRepository.findByUserAndProduct(user, product);
		Carts carts;
		if (existingCartOpt.isPresent()) {
			//既存→数量を加算
			carts = existingCartOpt.get();
			carts.setQuantity(carts.getQuantity() + quantity);
			carts.setUpdated_at(LocalDateTime.now());
		} else {
			//新規→新しく作成
			carts = new Carts();
			carts.setUser(user);
			carts.setProduct(product);
			carts.setQuantity(quantity);
			carts.setCreated_at(LocalDateTime.now());
			carts.setUpdated_at(LocalDateTime.now());
		}
		//保存
		Carts savedCart = cartsRepository.save(carts);
		//登録された cart_id を返す
		return savedCart.getCart_id();
	}
	public void reduceQuantity(Users user, Integer productId, Integer removeQuantity) {
		Optional<Carts> cartOpt = cartsRepository.findByUserAndProductId(user, productId);
		if(cartOpt.isPresent()) {
			Carts cart = cartOpt.get();
			int newQuantity = cart.getQuantity() - removeQuantity;
			if(newQuantity <= 0) {
				cartsRepository.delete(cart);
			} else {
				cart.setQuantity(newQuantity);
				cartsRepository.save(cart);
			}
		}
	}
	public Map<String, Object> findCartDetailByUserAndProductId(Users user, Integer productId) {
		Optional<Carts> cartOpt = cartsRepository.findByUserAndProductId(user, productId);
		if (cartOpt.isEmpty()) return null;
		Carts cart = cartOpt.get();
		Products product = productDetailRepository.findById(cart.getProduct().getProduct_id()).orElse(null);
		if (product == null) return null;
		Integer quantity = cart.getQuantity();
		Integer totalPrice = product.getTax_price() * quantity;
		Map<String, Object> result = new HashMap<>();
		result.put("product", product);
		result.put("quantity", quantity);
		result.put("totalPrice", totalPrice);
		return result;
	}
}