package jp.co.sss.spring_test.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
	// cartIdからproductIdとquantityを取得
	public Map<String, Object> findCartDetailByCartId(Integer cartId) {
		Optional<Carts> cartOpt = cartsRepository.findById(cartId);
		if (cartOpt.isEmpty()) return null;
		Carts cart = cartOpt.get();
		// product が取得できない場合は null を返す
		Products product = productDetailRepository.findById(cart.getProduct().getProduct_id()).orElse(null);
		if (product == null) return null;
		Integer taxPrice = product.getTax_price();
		Integer quantity = cart.getQuantity();
		Integer totalPrice = taxPrice * quantity;
		Map<String, Object> result = new HashMap<>();
		result.put("product", product);
		result.put("quantity", quantity);
		result.put("totalPrice", totalPrice);
		return result;
	}
	public Integer addToCart(Users user, Integer productId, Integer quantity) {
		Products product = productDetailRepository.findById(productId).orElse(null);
		if (product == null) return null;
		List<Carts> existingCartList = cartsRepository.findByUserAndProduct(user, product);
		Carts carts;
		// ===== FIX: 条件を正しくする =====
		if (!existingCartList.isEmpty()) {
			// 既存がある -> 数量を加算
			carts = existingCartList.get(0);
			carts.setQuantity(carts.getQuantity() + quantity);
			carts.setUpdated_at(LocalDateTime.now());
		} else {
			// 新規 -> 新しく作成
			carts = new Carts();
			carts.setUser(user);
			carts.setProduct(product);
			carts.setQuantity(quantity);
			carts.setCreated_at(LocalDateTime.now());
			carts.setUpdated_at(LocalDateTime.now());
		}
		Carts savedCart = cartsRepository.save(carts);
		return savedCart.getCart_id();
	}
	public void reduceQuantity(Users user, Integer productId, Integer removeQuantity) {
		// safety: null or non-positive removeQuantity は無視
		if (removeQuantity == null || removeQuantity <= 0) return;
		List<Carts> cartList = cartsRepository.findByUserAndProductId(user, productId);
		if (cartList == null || cartList.isEmpty()) {
			// 対象カートが見つからなければ何もしない
			return;
		}
		Carts cart = cartList.get(0);
		int newQuantity = cart.getQuantity() - removeQuantity;
		if (newQuantity <= 0) {
			// 完全に削除
			cartsRepository.delete(cart);
		} else {
			cart.setQuantity(newQuantity);
			cart.setUpdated_at(LocalDateTime.now());
			cartsRepository.save(cart);
		}
	}
	public Map<String, Object> findCartDetailByUserAndProductId(Users user, Integer productId) {
		List<Carts> cartOpt = cartsRepository.findByUserAndProductId(user, productId);
		if (cartOpt.isEmpty()) return null;
		Carts cart = cartOpt.get(0);
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