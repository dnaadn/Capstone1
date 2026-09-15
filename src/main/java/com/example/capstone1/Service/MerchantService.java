package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean addMerchant(Merchant merchant){
        for(Merchant merchant1 : merchants){
            if(merchant1.getId().equals(merchant.getId())){
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(String id, Merchant updateMerchant){
        for(int i=0; i<merchants.size(); i++){
            if(merchants.get(i).getId().equals(id)){
                updateMerchant.setId(id);
                merchants.set(i,updateMerchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        for(int i=0; i<merchants.size(); i++){
            if(merchants.get(i).getId().equals(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}
