package com.example.pradhuman.utils;

import com.example.pradhuman.entities.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jutil {

    public static boolean isMobileValid(String customerMobile) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(customerMobile);
        boolean result = m.matches();
        return result;
    }

    public static boolean isPincodeValid(String pincode) {
        Pattern p = Pattern.compile("[0-9]{6}");
        Matcher m = p.matcher(pincode);
        boolean result = m.matches();
        return result;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().equals("") || s.trim().equalsIgnoreCase("null");
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        if (isNullOrEmpty(email)) {
            return false;
        }
        String expression = "^(.+)@(.+)$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isPriceValid(Order order){
        double totalAmount = 0;
        if(order.getItems().size() > 0){
            for(Item i : order.getItems()){
                if(i.getPrice() > 0){
                    totalAmount += i.getPrice() * i.getQuantity();
                }else {
                    return false;
                }
            }
            order.setTotalAmount(Jutil.formatDouble(totalAmount));
            return true;
        }else {
            return false;
        }

    }

    public static String generateRandomWord(int lengthOfWord) {
        if (lengthOfWord < 1) {
            return "";
        }
        Random random = new Random();
        char[] word = new char[lengthOfWord];
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return String.valueOf(word);
    }

    public static String generateRandomNumber(int size) {
        return (int) (Math.pow(10, size - 1) + Math.random() * 9 * Math.pow(10, size - 1)) + "";
    }

    public static Double formatDouble(double d) {
        try {
            DecimalFormat format = new DecimalFormat("##.##");
            return Double.parseDouble(format.format(d));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static int getRandomIntegerBetween(int min, int max) {
        Random rand = new Random();
        int random = min;
        try {
            random = rand.nextInt(max - min + 1) + min;
        } catch (Exception e) {
        }
        return random;
    }

    public static void validateUser(User user) throws ValidationException {
        if(!isEmailValid(user.getEmail()))
            throw new ValidationException("please enter a valid email THANK YOU");
        if(!isMobileValid(user.getMobile()))
            throw new ValidationException("please enter a valid phone THANK YOU");
        if(!isPincodeValid(user.getAddress().getPincode()))
            throw new ValidationException("please enter a valid pin code THANK YOU");
        if(user.getPassword().length() < 7)
            throw new ValidationException("Password length should be greater than 6");
    }

    /**
     * @param oldUser user in db
     * @param user  update user data
     */
    public static User getUpdatedUser(User oldUser, User user){
        oldUser.setEmail(user.getEmail());
        oldUser.setDisabled(user.isDisabled());
        oldUser.setMobile(user.getMobile());
        oldUser.setPassword(user.getPassword());
        Address updatedAddress = Address.builder().pincode(user.getAddress().getPincode()).state(user.getAddress()
                .getState()).city(user.getAddress().getCity()).build();
        oldUser.setAddress(updatedAddress);
        return oldUser;
    }

    public static Order getUpdatedOrder(Order oldOrder, Order order){
        oldOrder.setUserId(order.getUserId());
        oldOrder.setDeleted(order.isDeleted());
        for (Item i : order.getItems()){
            List<Item> items = new ArrayList<>();
            items.add(Item.builder().price(i.getPrice()).category(i.getCategory()).quantity(i.getQuantity()).build());
            oldOrder.setItems(items);
        }
        if (isPriceValid(oldOrder)){
            oldOrder.setStatus(OrderStatus.SUCCESS.getStatus());
        }else {
            oldOrder.setStatus(OrderStatus.FAIL.getStatus());
        }
        return oldOrder;
    }

    public static String maskString(String strText, int start, int end, char maskChar) throws Exception {
        if (strText == null || strText.equals(""))
            return "";

        if (start < 0)
            start = 0;

        if (end > strText.length())
            end = strText.length();

        if (start > end)
            throw new RuntimeException("End index cannot be greater than start index");

        int maskLength = end - start;

        if (maskLength == 0)
            return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start) + sbMaskString.toString() + strText.substring(start + maskLength);
    }

}
