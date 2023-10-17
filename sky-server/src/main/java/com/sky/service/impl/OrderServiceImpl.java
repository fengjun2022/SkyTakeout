package com.sky.service.impl;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.mapper.OderMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OderMapper oderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Override
    public OrderSubmitVO sibmitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        return null;
    }
}
