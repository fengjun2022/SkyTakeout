package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishItemVO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前商品是否存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // 如果存在，只需要将数量加1
if (list != null && list.size() >0){
    ShoppingCart cart = list.get(0);
    cart.setNumber(cart.getNumber()+1);
    shoppingCartMapper.updateNumberById(cart);
}else {
    // 如果不存在，则需要插入一条数据
    Long dishId = shoppingCartDTO.getDishId();
    Long setmealId = shoppingCartDTO.getSetmealId();

    if (dishId!=null){
        // 添加到购物车的是菜品
        Dish dish = dishMapper.getById(dishId);
        shoppingCart.setName(dish.getName());
        shoppingCart.setImage(dish.getImage());
        shoppingCart.setAmount(dish.getPrice());
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());



    }
    if (setmealId!=null){
        Setmeal setmeal = new Setmeal();
        setmeal.setId(setmealId);
        List<Setmeal> list1 = setmealMapper.list(setmeal);
    }


}
        System.out.println(shoppingCart);

        shoppingCartMapper.insert(shoppingCart);



    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long currentId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(currentId);
        List<ShoppingCart> list =  shoppingCartMapper.list(shoppingCart);
        return list;
    }
}
