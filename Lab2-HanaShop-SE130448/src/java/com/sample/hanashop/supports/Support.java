/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.supports;

import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.Part;

/**
 *
 * @author sonho
 */
public class Support {

    public static String get7DayNext(String currDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, +6);
        String result = sdf.format(c.getTime());
        return result;
    }

    public static String get7DayBefore(String currDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, -6);
        String result = sdf.format(c.getTime());
        return result;
    }

    public static String getYesterday(String currDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, -1);
        String result = sdf.format(c.getTime());
        return result;
    }

    public static String getTomorrowDay(String currDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            //Setting the date to the given date
            c.setTime(sdf.parse(currDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, +1);
        String result = sdf.format(c.getTime());
        return result;
    }

    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public static String convertTime(String time) {
        if (time == null || time.equals("")) {
            return null;
        }
        String result = null;
        String[] tmp = time.split(":");
        result = tmp[0];
        return result;
    }

    public static String convertDatetoDateAndMonth(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        String result = null;
        String[] tmp = date.split("-");
        result = tmp[2] + "-" + tmp[1];
        return result;
    }

    public static String convertDate(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        String result = null;
        String[] tmp = date.split("\\.");
        for (int i = 2; i >= 0; i--) {
            if (i == 2) {
                result = tmp[i];
            } else {
                result = result + "-" + tmp[i];
            }
        }
        return result;
    }

    public static List<String> addIdFoodToListIdFoodsRecent(String idFood, List<String> listIdFoods) {
        if (listIdFoods == null) {
            listIdFoods = new ArrayList<>();
        }
        if (listIdFoods.contains(idFood)) {
            listIdFoods.remove(idFood);
        }
        listIdFoods.add(idFood);
        return listIdFoods;
    }

    public static String convertToStringIdFoodsRecent(List<String> listIdFoods) {
        if (listIdFoods == null || listIdFoods.isEmpty()) {
            return null;
        }
        String result = null;
        for (int i = 0; i < listIdFoods.size(); i++) {
            if (i == 0) {
                result = listIdFoods.get(i);
            } else {
                result = result + "-" + listIdFoods.get(i);
            }
        }
        return result;
    }

    public static List<String> convertToListIdFoodsRecent(String idFoods) {
        if (idFoods == null || idFoods.equals("")) {
            return null;
        }
        List<String> result = new ArrayList<>();
        if (idFoods.contains("-")) {
            String[] list = idFoods.split("-");
            for (String x : list) {
                result.add(x);
            }
        } else {
            result.add(idFoods);
        }
        return result;
    }

    public static void sortListFoodOrderTime(List<FoodDTO> listOrderedFood) {
        for (int i = 0; i < listOrderedFood.size() - 1; i++) {
            for (int j = 1; j < listOrderedFood.size(); j++) {
                if (listOrderedFood.get(i).getOrderTime() < listOrderedFood.get(j).getOrderTime()) {
                    FoodDTO foodDTO = listOrderedFood.get(i);
                    listOrderedFood.set(i, listOrderedFood.get(j));
                    listOrderedFood.set(j, foodDTO);
                }
            }
        }
    }

    public static String getShortDescription(String description) {
        String result = null;
        if (description.length() > 80) {
            result = description.substring(0, 80) + " ...";
        } else {
            result = description;
        }
        return result;
    }

    public static String getSQLListFood(int size) {
        String result = "( ";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                result = result + "IdFood = ? OR ";
            } else {
                result = result + "IdFood = ? ) ";
            }
        }
        return result;
    }

    public static String getSQLListBill(int size) {
        String result = "( ";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                result = result + "IdBill = ? OR ";
            } else {
                result = result + "IdBill = ? ) ";
            }
        }
        return result;
    }

    public static String getSQLlistCategory(List<CategoryDTO> listCategory) {
        String result = "( ";
        for (int i = 0; i < listCategory.size(); i++) {
            if (i != listCategory.size() - 1) {
                result = result + "CategoryID = ? OR ";
            } else {
                result = result + "CategoryID = ? )";
            }
        }
        return result;
    }

    public static String getTime(String postingDate) {
        Date dt = new Date();
        SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        String dayMonYearPostingDate = postingDate.split(" ")[0];
        String dayMonYearCurrTime = currentTime.split(" ")[0];
        if (dayMonYearPostingDate.equals(dayMonYearCurrTime)) {
            String hourMinSecPostingDate = postingDate.split(" ")[1];
            String hourMinSecCurrTime = currentTime.split(" ")[1];
            String hourPostingDate = hourMinSecPostingDate.split(":")[0];
            String hourCurrTime = hourMinSecCurrTime.split(":")[0];
            if (!hourPostingDate.equals(hourCurrTime)) {
                int hourPost = Integer.parseInt(hourPostingDate);
                int hourCurr = Integer.parseInt(hourCurrTime);
                return (hourCurr - hourPost) + " hours ago";
            } else {
                String minPostingDate = hourMinSecPostingDate.split(":")[1];
                String minCurrTime = hourMinSecCurrTime.split(":")[1];
                if (!minPostingDate.equals(minCurrTime)) {
                    int minPost = Integer.parseInt(minPostingDate);
                    int minCurr = Integer.parseInt(minCurrTime);
                    return (minCurr - minPost) + " minutes ago";
                } else {
                    String secPostingDate = hourMinSecPostingDate.split(":")[2];
                    String secCurrTime = hourMinSecCurrTime.split(":")[2];
                    if (secPostingDate.contains(".")) {
                        secPostingDate = secPostingDate.split("[.]")[0];
                    }
                    if (secCurrTime.contains(".")) {
                        secCurrTime = secCurrTime.split("[.]")[0];
                    }
                    int secPost = Integer.parseInt(secPostingDate);
                    int secCurr = Integer.parseInt(secCurrTime);
                    return (secCurr - secPost) + " seconds ago";
                }
            }
        } else {
            String yearPostingDate = dayMonYearPostingDate.split("-")[0];
            String yearCurrTime = dayMonYearCurrTime.split("-")[0];
            if (!yearPostingDate.equals(yearCurrTime)) {
                int yearPost = Integer.parseInt(yearPostingDate);
                int yearCurr = Integer.parseInt(yearCurrTime);
                return (yearCurr - yearPost) + " years ago";
            } else {
                String monthPostingDate = dayMonYearPostingDate.split("-")[1];
                String monthCurrTime = dayMonYearCurrTime.split("-")[1];
                if (!monthPostingDate.equals(monthCurrTime)) {
                    int monthPost = Integer.parseInt(monthPostingDate);
                    int monthCurr = Integer.parseInt(monthCurrTime);
                    return (monthCurr - monthPost) + " months ago";
                } else {
                    String dayPostingDate = dayMonYearPostingDate.split("-")[2];
                    String dayCurrTime = dayMonYearCurrTime.split("-")[2];
                    int dayPost = Integer.parseInt(dayPostingDate);
                    int dayCurr = Integer.parseInt(dayCurrTime);
                    return (dayCurr - dayPost) + " days ago";
                }
            }
        }
    }

    public static int calculateNumPageBill(int numBill) {
        if (numBill % 4 == 0) {
            return numBill / 4;
        } else {
            return (numBill / 4 + 1);
        }
    }

    public static int calculateNumPage(int numFood) {
        if (numFood % 20 == 0) {
            return numFood / 20;
        } else {
            return (numFood / 20 + 1);
        }
    }

    public static String getCode() {
        Random r = new Random();
        String code = (r.nextInt(10000) + 1000) + "";
        return code;
    }

    /*SHA-256*/
    public static String encryptedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
