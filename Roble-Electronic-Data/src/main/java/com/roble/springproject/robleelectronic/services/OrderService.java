package com.roble.springproject.RobleElectronic.services;

import com.roble.springproject.RobleElectronic.models.Order;
import com.roble.springproject.RobleElectronic.models.User;

public interface OrderService {

    Order save(User user);
}
