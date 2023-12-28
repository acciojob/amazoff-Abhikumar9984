package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String , Order> orderDb  = new HashMap<>();
    HashMap<String , DeliveryPartner> partnerDb  = new HashMap<>();
    HashMap<String , String> pairDb1  = new HashMap<>();
    HashMap<String , String> pairDb2  = new HashMap<>();

    public void addOrderToOrderDb(Order order){
        orderDb.put(order.getId() , order);
    }

    public void addPartnerToPartnerDb(String id){
        DeliveryPartner np = new DeliveryPartner(id);
        partnerDb.put(id , np );
    }

    public void addPairToPairDb(String orderId , String PartnerId){
        pairDb1.put(orderId ,PartnerId);
        pairDb2.put(PartnerId , orderId);
    }

    public List<Order> getAllOrder(){
        return orderDb.values().stream().toList();
    }

    public List<DeliveryPartner> getAllPartner(){
        return partnerDb.values().stream().toList();
    }

    public Order getOrderByPartnerId(String id){
         String orderId  = pairDb2.get(id);
         return orderDb.get(orderId);
    }

    public List<Order> getAllUnassignedOrder(){
        List<Order> temp  = getAllOrder();
        List<Order> pans  = new ArrayList<>();
        for(Order order : temp){
            if(!pairDb1.containsKey(order.getId())){
                pans.add(order);
            }
        }
        return pans;
    }

    public List<Order> getAllOrderWhichAreLeft(String partnerId){
        List<String> orderIds  = new ArrayList<>();
        for(String p : pairDb2.keySet()){
            if(partnerId.equals(p)){
                orderIds.add(pairDb2.get(p));
            }
        }
        List<Order> orders  = new ArrayList<>();
        for(String s : orderDb.keySet()){
            if(orderIds.contains(s)){
                orders.add(orderDb.get(s));
            }
        }
        return orders;
    }

    public void getAllOrderForDeletePartner(String partner){
         List<Order> pans  = new ArrayList<>();
        for(String s1 : pairDb2.keySet()){
            if(s1.equals(partner)){
                pans.add(orderDb.get(pairDb2.get(s1)));
                pairDb2.remove(s1);
            }
        }
        for(String s2 : pairDb1.keySet()){
             String s3  = pairDb1.get(s2);
             if(s3.equals(partner)){
                 pans.add(orderDb.get(s2));
                 pairDb1.remove(s2);
             }
        }

        for(Order order : pans){
            orderDb.put(order.getId(),order);
        }
    }

   public void deleteOrderByOrderId(String orderId){
       for(String s1 : pairDb1.keySet()){
           if(s1.equals(orderId)){
               pairDb1.remove(s1);
           }
       }
       for(String s2 : pairDb2.keySet()){
           String s3  = pairDb2.get(s2);
           if(s3.equals(orderId)){
               pairDb2.remove(s2);
           }
       }

   }
}
