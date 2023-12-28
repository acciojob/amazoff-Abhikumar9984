package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired OrderRepository obj1;
    public Order getOrderByOrderId(String id){
        List<Order> temp  = obj1.getAllOrder();
        Order order  = null;
        for(Order o : temp){

            if(o.getId().equals(id)){
                order  = o;
                break;
            }
        }
        return order;
    }

    public DeliveryPartner getPartnerByPartnerId(String id){
        List<DeliveryPartner> temp  = obj1.getAllPartner();
        DeliveryPartner partner  = null;
        for(DeliveryPartner d: temp){

            if(d.getId().equals(id)){
                partner  = d;
                break;
            }
        }
        return partner;
    }

    public int getOrderCountByPartnerId(String id){
        List<DeliveryPartner> temp  = obj1.getAllPartner();
        int count  = 0;

        for(DeliveryPartner d : temp){

            if(d.getId().equals(id)){
                count  = d.getNumberOfOrders();
                break;
            }
        }
        return count;
    }

    public List<String> getOrderByPartnerId(String id){
        List<DeliveryPartner> temp  = obj1.getAllPartner();
        List<String> pans  = new ArrayList<>();
        for(DeliveryPartner p : temp){

            if(p.getId().equals(id)){
                pans.add(obj1.getOrderByPartnerId(p.getId()).getId());
            }
        }
        return pans;
    }

    public List<String> getAllOrdersName(){
        List<Order> temp  = obj1.getAllOrder();
        List<String> pans  = new ArrayList<>();
        for(Order order : temp){
            pans.add(order.getId());
        }
        return pans;
    }

    public int getCountOfUnsignedOrder(){
        List<Order> temp  = obj1.getAllUnassignedOrder();
        return temp.size();
    }

    public int getCountOfLeftOrders(String time , String partner){
        List<Order> temp  = obj1.getAllOrderWhichAreLeft(partner);
        int a  = Integer.parseInt(time.substring(0,2));
        int b  = Integer.parseInt(time.substring(3));
        int t  = a*60+b;
        int count  = 0;
        for(Order order : temp){
            int deliveryTime  = order.getDeliveryTime();
            if(deliveryTime<t) count++;
        }
        return count;
    }

    public String getLastOrderTime(String partnerId){
        List<Order> temp  = obj1.getAllOrderWhichAreLeft(partnerId);
        String ans  = "";
        int time  = 0;
        for(Order order : temp){
            int a  = order.getDeliveryTime();
            if(time<a){
                time  = a;
                 String s1  = a/60+"";
                 s1 += a%60;
            }
        }
        return ans;
    }
}
