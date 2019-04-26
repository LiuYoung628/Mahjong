package com.aplid.mahjong;

import java.util.List;

public class MahjongUtil {

    public static String changeToChar(String code) {
        String result = "";

        switch (code){
            case "1m":result="一\n萬";break;
            case "2m":result="二\n萬";break;
            case "3m":result="三\n萬";break;
            case "4m":result="四\n萬";break;
            case "5m":result="五\n萬";break;
            case "6m":result="六\n萬";break;
            case "7m":result="七\n萬";break;
            case "8m":result="八\n萬";break;
            case "9m":result="九\n萬";break;
            case "1p":result="一\n餅";break;
            case "2p":result="二\n餅";break;
            case "3p":result="三\n餅";break;
            case "4p":result="四\n餅";break;
            case "5p":result="五\n餅";break;
            case "6p":result="六\n餅";break;
            case "7p":result="七\n餅";break;
            case "8p":result="八\n餅";break;
            case "9p":result="九\n餅";break;
            case "1s":result="一\n索";break;
            case "2s":result="二\n索";break;
            case "3s":result="三\n索";break;
            case "4s":result="四\n索";break;
            case "5s":result="五\n索";break;
            case "6s":result="六\n索";break;
            case "7s":result="七\n索";break;
            case "8s":result="八\n索";break;
            case "9s":result="九\n索";break;
            case "1z":result="東\n ";break;
            case "2z":result="南\n ";break;
            case "3z":result="西\n ";break;
            case "4z":result="北\n ";break;
            case "5z":result="中\n ";break;
            case "6z":result="白\n ";break;
            case "7z":result="發\n ";break;

            default:break;
        }

        return result;
    }

    public static final  String[] order = new String[]{
            "1m",
            "2m",
            "3m",
            "4m",
            "5m",
            "6m",
            "7m",
            "8m",
            "9m",
            "1p",
            "2p",
            "3p",
            "4p",
            "5p",
            "6p",
            "7p",
            "8p",
            "9p",
            "1s",
            "2s",
            "3s",
            "4s",
            "5s",
            "6s",
            "7s",
            "8s",
            "9s",

    };


    public static final String[] ziOrder = new String[]{

            "1z",
            "2z",
            "3z",
            "4z",
            "5z",
            "6z",
            "7z",
    };


    public static int getIndex(String value) {

        String[] arr = order;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }


    public static List<String> getOrder(List<String> hand) {

        String[] order = new String[]{
                "1m",
                "2m",
                "3m",
                "4m",
                "5m",
                "6m",
                "7m",
                "8m",
                "9m",
                "1p",
                "2p",
                "3p",
                "4p",
                "5p",
                "6p",
                "7p",
                "8p",
                "9p",
                "1s",
                "2s",
                "3s",
                "4s",
                "5s",
                "6s",
                "7s",
                "8s",
                "9s",
                "1z",
                "2z",
                "3z",
                "4z",
                "5z",
                "6z",
                "7z",
        };

        for(int i=0;i<hand.size()-1;i++)
        {
            for(int j=i;j<hand.size();j++)
            {
                int qian = 0;
                int hou = 0;
                for(int k=0;k<order.length;k++)
                {
                    if(hand.get(i).equals(order[k]))
                    {
                        qian = k;
                    }
                    if(hand.get(j).equals(order[k]))
                    {
                        hou = k;
                    }
                }
                if(hou <= qian)
                {
                    String lin = hand.get(i);
                    hand.set(i,hand.get(j));
                    hand.set(j,lin);
                }
            }
        }

        return hand;
    }



}
